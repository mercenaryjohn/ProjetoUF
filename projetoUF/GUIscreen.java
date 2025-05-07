import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GUIscreen extends JPanel implements ActionListener, KeyListener
{
    private boolean classeFoiEscolhida = false;
    private boolean playerEstáVivo = true;
    private boolean menuAberto = false;
    private boolean inventárioAberto = false;
    private boolean statsAberto = false;
    private EscolherClasse player;
    private GUIescolhas objEscolhas;
    private InventarioClasse objInventario;
    char[][] mapa;

    Timer timer = new Timer(16, this); //60 FPS

    public GUIscreen (char[][] mapa)
    {
        this.mapa = mapa;

        setLayout(new BorderLayout());
        
        addKeyListener(this);
        setFocusable(true); // Permite o panel receber input
        requestFocusInWindow(); //Força foco quando a tela aparece

        timer.start();
    }

    public void actionPerformed(ActionEvent k)
    { repaint(); }

    public void setPlayer(EscolherClasse classeEscolhida)
    { player = classeEscolhida; }

    public void setEscolhas(GUIescolhas escolhas)
    { objEscolhas = escolhas; }

    public void setInventario(InventarioClasse Inventario)
    { objInventario = Inventario; }

    public void setClasseFoiEscolhida(boolean pronto) 
    {
        this.classeFoiEscolhida = pronto;
        repaint(); // força atualizar a tela
    }

    private String[] listaDeOpcoes;
    public void setListaDeOpcoes(String[] listaRecebida)
    { listaDeOpcoes = listaRecebida; }

    public EscolherClasse mostrarMenuInicial(String[] listaDeClasses) 
    {
        String[] lista = listaDeClasses;
        String escolha = (String) JOptionPane.showInputDialog
        (
            null,
            "Escolha seu sobrevivente:",
            "Menu Inicial",
            JOptionPane.PLAIN_MESSAGE,
            null,
            lista,
            lista[0]
        );

        if (escolha != null) 
        {
            EscolherClasse classeEscolhida = new EscolherClasse(escolha.toLowerCase());
            return classeEscolhida;
        } 
        else 
        {
            System.exit(0); // Sai se cancelar
            return null;
        }
    }

    public void escolherItemInventário()
    {
        Map<Item, Integer> mapaContadorLista = new HashMap<>();
        // Usar para contar os items da list Inventário

        for (Item item : player.getInventário()) // Coloca itens no HasMap
        {
            mapaContadorLista.put(item, mapaContadorLista.getOrDefault(item, 0) + 1);
        }
    
        // Para associar a string mostrada ao Item real
        Map<String, Item> opçõesMapaString = new LinkedHashMap<>();
    
        int index = 1;
        for (Map.Entry<Item, Integer> entry : mapaContadorLista.entrySet()) 
        {
            Item item = entry.getKey();
            int quantidade = entry.getValue();
    
            String descricao;
            if (item instanceof Alimento) 
            {
                Alimento alimento = (Alimento) item;
                descricao = index + " - " + item.getNome() + " x" + quantidade + " (Validade: " + alimento.getValidade() + ")";
            } 
            else 
            {
                descricao = index + " - " + item.getNome() + " x" + quantidade;
            }
    
            opçõesMapaString.put(descricao, item);
            index++;
        }

        // Array com as descrições para o dropdown
        String[] opcoes = opçõesMapaString.keySet().toArray(new String[0]);

        if (opcoes.length == 0) 
        {
            JOptionPane.showMessageDialog(null, "Inventário vazio.");
            return;
        }

        // Mostra o dropdown
        String escolha = (String) JOptionPane.showInputDialog
        (
            null,
            "Escolha um item:",
            "Inventário",
            JOptionPane.PLAIN_MESSAGE,
            null,
            opcoes,
            opcoes[0]
        );

        // Usa o Item correspondente
        Item usarItemInventário = opçõesMapaString.get(escolha);
        if (usarItemInventário != null) 
        {
            objInventario.usarItem(player, usarItemInventário);
            objInventario.removerItem(player, usarItemInventário);
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (classeFoiEscolhida == false) 
        {
            // Player ainda não escolheu
            return;
        }

        int chunkVisao = 5; //chunkVisao
        int mapaAltura = mapa.length;
        int mapaLargura = mapa[0].length;

        int tileTamanho = 30;

        // Posição do player
        double[] localização = player.getLocalização();
        int playerX = mapaLargura / 2 + (int)localização[0]; // Em X, tem que ser adição
        int playerY = (mapaAltura / 2) - (int)localização[1]; // Em Y, tem que ser subtração
        //System.out.println(playerX); //DEBUG
        //System.out.println(playerY);

        // Raio de 2 chunks (2 * 5 = 10)
        int raio = chunkVisao * 2;

        // Limites da janela
        int inicioY = Math.max(0, playerY - raio);
        int fimY = Math.min(mapaAltura, playerY + raio + 1);

        int inicioX = Math.max(0, playerX - raio);
        int fimX = Math.min(mapaLargura, playerX + raio + 1);

        // Dimensões da área visível
        int tamanhoTilesX = fimX - inicioX;
        int tamanhoTilesY = fimY - inicioY;

        // Para centralizar no painel
        int deslocaX = (getWidth() - tamanhoTilesX * tileTamanho) / 2;
        int deslocaY = (getHeight() - tamanhoTilesY * tileTamanho) / 2;

        //Cores
        Color jogadorCorEscura = new Color(1, 1, 1);
        Color jogadorCorClara = new Color(206, 206, 206);
        Color florestaVerde = new Color(74, 171, 93);
        Color montanhaBranco = new Color(243, 243, 243);
        Color cavernaCinza = new Color(54, 54, 54);
        Color aguaAzul = new Color(102, 153, 204);
        Color ruinaRoxo = new Color(120, 70, 180);
        Color planicieCor = new Color(154, 200, 120); //verde musgo
        
        for (int y = inicioY; y < fimY; y++) 
        {
            for (int x = inicioX; x < fimX; x++) 
            {
                if(y == playerY && x == playerX)
                {
                    if (mapa[y][x] == 'F')
                    {
                        g.setColor(jogadorCorEscura);
                    }
                    else if (mapa[y][x] == 'M') 
                    {
                        g.setColor(jogadorCorEscura);
                    }
                    else if (mapa[y][x] == 'C') 
                    {
                        g.setColor(jogadorCorClara);
                    }
                    else if (mapa[y][x] == '~') 
                    {
                        g.setColor(jogadorCorEscura);
                    }
                    else if (mapa[y][x] == 'R') 
                    {
                        g.setColor(jogadorCorEscura);
                    }
                    else if (mapa[y][x] == '_') 
                    {
                        g.setColor(jogadorCorEscura);
                    }
                    else
                    {
                        g.setColor(jogadorCorEscura);
                    }
                    //int screenX = (x - inicioX) * tileTamanho;
                    //int screenY = (y - inicioY) * tileTamanho;
                    //g.fillRect(screenX, screenY, tileTamanho, tileTamanho);
                    int screenX = deslocaX + (x - inicioX) * tileTamanho;
                    int screenY = deslocaY + (y - inicioY) * tileTamanho;
                    g.fillRect(screenX, screenY, tileTamanho, tileTamanho);
                }
                else
                {
                    if (mapa[y][x] == 'F')
                    {
                        //System.out.print(VERDE + mapa[y][x] + " " + RESET);
                        g.setColor(florestaVerde);
                    }
                    else if (mapa[y][x] == 'M') 
                    {
                        //System.out.print(BRANCO + mapa[y][x] + " " + RESET);
                        g.setColor(montanhaBranco);
                    }
                    else if (mapa[y][x] == 'C') 
                    {
                        //System.out.print(CINZA + mapa[y][x] + " " + RESET);
                        g.setColor(cavernaCinza);
                    }
                    else if (mapa[y][x] == '~') 
                    {
                        //System.out.print(AZUL + mapa[y][x] + " " + RESET);
                        g.setColor(aguaAzul);
                    }
                    else if (mapa[y][x] == 'R') 
                    {
                        //System.out.print(MAGENTA + mapa[y][x] + " " + RESET);
                        g.setColor(ruinaRoxo);
                    }
                    else if (mapa[y][x] == '_') 
                    {
                        //System.out.print(AMARELO + mapa[y][x] + " " + RESET);
                        g.setColor(planicieCor);
                    }
                    else
                    {
                        //System.out.print(mapa[y][x] + " ");
                        g.setColor(Color.RED);
                    }
                    //int screenX = (x - inicioX) * tileTamanho;
                    //int screenY = (y - inicioY) * tileTamanho;
                    //g.fillRect(screenX, screenY, tileTamanho, tileTamanho);
                    int screenX = deslocaX + (x - inicioX) * tileTamanho;
                    int screenY = deslocaY + (y - inicioY) * tileTamanho;
                    g.fillRect(screenX, screenY, tileTamanho, tileTamanho);
                }
            }
        }

        if (menuAberto) //Na frente do mapa
        {
            g.setColor(Color.BLACK);
            g.fillRect(40, 240, 400, 100); //Fundo do menu
    
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 16));
    
            for (int i = 0; i < listaDeOpcoes.length; i++) 
            {
                g.drawString(listaDeOpcoes[i], 50, 265 + i * 20);
            }
        }

        /*if (inventárioAberto) // Usando dropdown, não isso, deixar aqui caso precise
            g.setColor(Color.BLACK);
            g.fillRect(40, 100, 400, 200); //Fundo do menu
    
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 16));

            Map<Item, Integer> mapaContadorLista = new HashMap<>();
            // Usar para contar os items da list Inventário

            for (Item item : player.getInventário()) // Coloca itens no HasMap
            {
                mapaContadorLista.put(item, mapaContadorLista.getOrDefault(item, 0) + 1);
            }

            // Print cada item
            int index = 1;
            g.drawString("Inventário:", 50 , 270);
            g.drawString("________________________________________", 50, 275);

            int i = 1;
            for (Map.Entry<Item, Integer> entry : mapaContadorLista.entrySet())
            {
                Item item = entry.getKey();
                if (item instanceof Alimento)
                {
                    g.drawString(index + "- " + (entry.getKey()).getNome() + " x" + entry.getValue() 
                    + " (Validade: " + ((Alimento) item).getValidade() + ")", 50, 275 + i * 20);
                }
                else
                {
                    g.drawString(index + "- " + (entry.getKey()).getNome() + " x" + entry.getValue(), 50, 275 + i * 20);
                }
                index++;
                i++;
            }
            g.drawString("________________________________________", 50, 275 + i * 20);
        */

        if (statsAberto)
        {
            //double[] mostrarPosição = player.getLocalização();
            String[] statsArray;
            if (player.getNome().equals("Escoteiro"))
            {
                double[] mostrarPosição = player.getLocalização();
                String[] statsArrayA =
                {
                "________________________________________",
                "Classe: " + player.getNome(),
                "Vida: " + player.getVida(),
                "Fome: " + player.getFome(),
                "Sede: " + player.getSede(),
                "Energia: " + player.getEnergia(),
                "Sanidade: " + player.getSanidade(),
                "Posição (X,Y): " + mostrarPosição[0] +","+ mostrarPosição[1],
                "________________________________________",
                "- para voltar [e] -"
                };
                statsArray = statsArrayA;
            }
            else
            {
                    String[] statsArrayB = {
                    "________________________________________",
                    "Classe: " + player.getNome(),
                    "Vida: " + player.getVida(),
                    "Fome: " + player.getFome(),
                    "Sede: " + player.getSede(),
                    "Energia: " + player.getEnergia(),
                    "Sanidade: " + player.getSanidade(),
                    "________________________________________",
                    "- para voltar [e] -"
                    };
                    statsArray = statsArrayB;
            }
            g.setColor(Color.BLACK);
            g.fillRect(40, 240, 400, 220); //Fundo do menu
    
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 16));
    
            for (int i = 0; i < statsArray.length; i++) 
            {
                g.drawString(statsArray[i], 50, 255 + i * 20);
            }
        }

        if (playerEstáVivo == false)
        {
            g.setColor(Color.BLACK);
            g.fillRect(40, 240, 400, 200); //Fundo do menu
    
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            g.drawString("GAME OVER...", 50, 270);
        }
    }

    public void keyPressed(KeyEvent k) //TODO
    {
        if (playerEstáVivo)
        {
            switch(k.getKeyCode())
            {
                case KeyEvent.VK_E:
                    //System.out.println("Aberto");
                    boolean menuAbertoHolder = !menuAberto;
                    menuAberto = menuAbertoHolder;
                    statsAberto = false;
                    inventárioAberto = false;
                    repaint();
                    break;
                case KeyEvent.VK_D:
                    if (player.getEnergia() > 0)
                    {
                    double[] posiçãoXD = player.getLocalização();
                    posiçãoXD[0] = posiçãoXD[0] + 1;
                    player.setLocalização(posiçãoXD);
                    player.setEnergia(player.getEnergia() - 1);
                    }
                    menuAberto = false;
                    break;
                case KeyEvent.VK_S:
                    if (player.getEnergia() > 0)
                    {
                    double[] posiçãoYS = player.getLocalização();
                    posiçãoYS[1] = posiçãoYS[1] - 1;
                    player.setLocalização(posiçãoYS);
                    player.setEnergia(player.getEnergia() - 1);
                    }
                    menuAberto = false;
                    break;
                case KeyEvent.VK_A:
                    if (player.getEnergia() > 0)
                    {
                    double[] posiçãoXA = player.getLocalização();
                    posiçãoXA[0] = posiçãoXA[0] - 1;
                    player.setLocalização(posiçãoXA);
                    player.setEnergia(player.getEnergia() - 1);
                    }
                    menuAberto = false;
                    break;
                case KeyEvent.VK_W:
                    if (player.getEnergia() > 0)
                    {
                    double[] posiçãoYW = player.getLocalização();
                    posiçãoYW[1] = posiçãoYW[1] + 1;
                    player.setLocalização(posiçãoYW);
                    player.setEnergia(player.getEnergia() - 1);
                    }
                    menuAberto = false;
                    break;
                case KeyEvent.VK_1:
                    if (inventárioAberto)
                    {

                    }
                case KeyEvent.VK_2: //Descansar
                    if (menuAberto)
                    {
                        menuAberto = false;
                        objEscolhas.escolhas("2", player);
                    }
                    break;
                case KeyEvent.VK_3: //Inventário
                    if (menuAberto)
                    {
                        menuAberto = false;
                        inventárioAberto = true;
                        escolherItemInventário();
                    }
                case KeyEvent.VK_4: //Status
                    if (menuAberto)
                    {
                        menuAberto = false;
                        statsAberto = true;
                    }
                    break;
            }
        }
        repaint();
    }

    public void keyReleased(KeyEvent k) 
    {

    }

    public void keyTyped(KeyEvent k) 
    {

    }
}
