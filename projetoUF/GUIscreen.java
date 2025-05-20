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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class GUIscreen extends JPanel implements ActionListener, KeyListener
{
    private boolean classeFoiEscolhida = false;
    private boolean playerEstáVivo = true;
    private boolean menuAberto = false;
    private boolean inventárioAberto = false;
    private boolean statsAberto = true;
    private boolean playerEmCombate = false;
    private boolean playerSeMovimentou = false;
    private EscolherClasse player;
    private InventarioClasse objInventario;

    private int vidaMáxima;
    private int sanidadeMáxima;
    private int energiaMáxima;
    private int sedeMáxima;
    private int fomeMáxima;

    private AmbienteCaverna objCaverna = new AmbienteCaverna();
    private AmbienteFloresta objFloresta = new AmbienteFloresta();
    private AmbienteLagoRio objLagoRio = new AmbienteLagoRio();
    private AmbienteMontanha objMontanha = new AmbienteMontanha();
    private AmbienteRuinas objRuinas = new AmbienteRuinas();
    private GerenciadorDeAmbientes objGerenciadorDeAmbientes = new GerenciadorDeAmbientes
                            (objCaverna, objFloresta, objLagoRio, objMontanha, objRuinas);
    private GerenciadorDeEventos objGerenciadorDeEventos = new GerenciadorDeEventos();

    private String últimoItemEncontrado = "";
    private String eventoAtual = "";
    private int turnoAtual = 0;
    private int diasSePassaram = 0;

    private char[][] mapa;
    private int mapaAltura;
    private int mapaLargura;

    Timer timer = new Timer(16, this); //60 FPS

    public GUIscreen (char[][] mapa)
    {
        this.mapa = mapa;
        this.mapaAltura = mapa.length;
        this.mapaLargura = mapa[0].length;

        setLayout(new BorderLayout());
        
        addKeyListener(this);
        setFocusable(true); // Permite o panel receber input
        requestFocusInWindow(); //Força foco quando a tela aparece

        timer.start();
    }

    public void actionPerformed(ActionEvent k)
    { repaint(); }

    public void setPlayer(EscolherClasse classeEscolhida)
    { 
        player = classeEscolhida; 
        
    }

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

        int tileTamanho = 30;

        // Posição atual do player
        double[] localização = player.getLocalização();
        int playerX = mapaLargura / 2 + (int)localização[0]; // Em X, tem que ser adição
        int playerY = (mapaAltura / 2) - (int)localização[1]; // Em Y, tem que ser subtração
        //System.out.println(playerX); //DEBUG
        //System.out.println(playerY);

        if (player.getEnergia() == 0)
        {
            if (mapa[playerY][playerX] == '~')
            {
                playerEstáVivo = false;
            }
        }

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
                if (true)
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
                    g.fillRect(screenX + 220, screenY, tileTamanho, tileTamanho);
                }
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
                    g.fillOval(screenX + 220, screenY, tileTamanho, tileTamanho);
                }
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
        if (menuAberto) //Na frente do mapa
        {
            g.setColor(Color.BLACK);
            g.fillRect(getWidth() / 2 + 20, (getHeight() / 2) - 80, 400, 120); //Fundo do menu
    
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 16));
    
            for (int i = 0; i < listaDeOpcoes.length; i++) 
            {
                g.drawString(listaDeOpcoes[i], getWidth() / 2 + 30, - 50 + (getHeight() / 2) + i * 20);
            }
        }

        if (statsAberto)
        {
            String ambienteAtualNome = objGerenciadorDeAmbientes.pegarNomeAmbiente(mapa[playerY][playerX]);
            String ambienteAtualDescrição = objGerenciadorDeAmbientes.pegarDescriçãoAmbiente(mapa[playerY][playerX]);
            String DescriçãoParte1 = ambienteAtualDescrição;
            String DescriçãoParte2 = "";
            String DescriçãoParte3 = "";
            String DescriçãoParte4 = "";
            if (ambienteAtualDescrição.length() > 50)
            {
                int indexDeDivisão = ambienteAtualDescrição.lastIndexOf(" ", 50);
                if (indexDeDivisão == -1)
                { indexDeDivisão = 50; }
                DescriçãoParte1 = ambienteAtualDescrição.substring(0, indexDeDivisão);
                DescriçãoParte2 = ambienteAtualDescrição.substring(indexDeDivisão);

                if (ambienteAtualDescrição.length() > 100)
                {
                    int indexDeDivisão2 = ambienteAtualDescrição.lastIndexOf(" ", 100);
                    if (indexDeDivisão2 == -1 || indexDeDivisão2 <= indexDeDivisão) 
                    { 
                        indexDeDivisão2 = ambienteAtualDescrição.indexOf(" ", indexDeDivisão + 1);
                        if (indexDeDivisão2 == -1) 
                        { indexDeDivisão2 = ambienteAtualDescrição.length(); }
                    }
                    DescriçãoParte2 = ambienteAtualDescrição.substring(indexDeDivisão, indexDeDivisão2);
                    DescriçãoParte3 = ambienteAtualDescrição.substring(indexDeDivisão2);
                    
                    if (ambienteAtualDescrição.length() > 150)
                    {
                        int indexDeDivisão3 = ambienteAtualDescrição.lastIndexOf(" ", 150);
                        DescriçãoParte3 = ambienteAtualDescrição.substring(indexDeDivisão2, indexDeDivisão3);
                        if (ambienteAtualDescrição.length() > 200)
                        {
                            DescriçãoParte4 = "...";
                        }
                    }
                }
            }

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
                "Ambiente: " + ambienteAtualNome,
                "   " + DescriçãoParte1, DescriçãoParte2, DescriçãoParte3 + DescriçãoParte4,
                "Turno: " + turnoAtual,
                "   ("+ diasSePassaram + ") dias se passaram",
                "Último recurso encontrado: " + últimoItemEncontrado,
                "Evento atual: " + eventoAtual
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
                    "Ambiente: " + ambienteAtualNome,
                    "   " + DescriçãoParte1, DescriçãoParte2, DescriçãoParte3 + DescriçãoParte4,
                    "Turno: " + turnoAtual,
                    "   ("+ diasSePassaram + ") dias se passaram",
                    "Último recurso encontrado: " + últimoItemEncontrado,
                    "Evento atual: " + eventoAtual
                    };
                    statsArray = statsArrayB;
            }
            g.setColor(Color.BLACK);
            g.fillRect(deslocaX - 220, deslocaY, 440, 630); //Fundo
    
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 16));
    
            for (int i = 0; i < statsArray.length; i++) 
            {
                g.drawString(statsArray[i], deslocaX - 210, 20 + deslocaY + i * 20);
            }
        }

        if (playerEmCombate)
        {

        }

        if (playerEstáVivo == false)
        {
            g.setColor(Color.BLACK);
            g.fillRect(deslocaX + 240, getHeight() / 2, 400, 50); //Fundo do menu
    
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            if (mapa[playerY][playerX] == '~' && player.getEnergia() == 0)
            {
                g.drawString("GAME OVER... Morreu por afogamento", deslocaX + 260, getHeight() / 2 + 30); 
            }
            else { g.drawString("GAME OVER...", deslocaX + 260, getHeight() / 2 + 30); }
        }
    }

    public void keyPressed(KeyEvent k) //TODO
    {
        if (playerEstáVivo)
        {
            if (!playerEmCombate)
            {
                switch(k.getKeyCode())
                {
                    case KeyEvent.VK_D:
                        if (player.getEnergia() > 0)
                        {
                        double[] posiçãoXD = player.getLocalização();
                        posiçãoXD[0] = posiçãoXD[0] + 1;
                        player.setLocalização(posiçãoXD);
                        player.setEnergia(player.getEnergia() - 1);
                        }
                        menuAberto = false;
                        playerSeMovimentou = true;
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
                        playerSeMovimentou = true;
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
                        playerSeMovimentou = true;
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
                        playerSeMovimentou = true;
                        break;
                    case KeyEvent.VK_1: //Vasculhar
                        if (menuAberto)
                        {
                            menuAberto = false;
                            double[] localização = player.getLocalização();
                            int playerX = mapaLargura / 2 + (int)localização[0]; // Em X, tem que ser adição
                            int playerY = (mapaAltura / 2) - (int)localização[1]; // Em Y, tem que ser subtração
                            String ambienteAtualNome = objGerenciadorDeAmbientes.pegarNomeAmbiente(mapa[playerY][playerX]);
                            Ambiente ambienteAtualObj = objGerenciadorDeAmbientes.pegarObjAmbiente(ambienteAtualNome);
                            
                            if (ambienteAtualObj != null)
                            {
                                Item itemRecurso = objGerenciadorDeAmbientes.vasculharAmbiente(ambienteAtualObj);
                                if (itemRecurso != null && player.getInventário().size() < player.getCapacidadeInventário() )
                                { 
                                    objInventario.adicionarItem(player, itemRecurso); 
                                    últimoItemEncontrado = itemRecurso.getNome();
                                }
                                else { últimoItemEncontrado = "(Nada)"; }
                                if ( player.getInventário().size() == player.getCapacidadeInventário() )
                                { últimoItemEncontrado = "inventário cheio " + "("+ player.getCapacidadeInventário() +")"; }
                            }
                            player.setEnergia(player.getEnergia() - 1);
                        }
                        turnoAtual++;
                        break;
                }
                if (playerSeMovimentou == true)
                {
                    double[] localização = player.getLocalização();
                    int playerX = mapaLargura / 2 + (int)localização[0]; // Em X, tem que ser adição
                    int playerY = (mapaAltura / 2) - (int)localização[1]; // Em Y, tem que ser subtração
                    String ambienteAtualNome = objGerenciadorDeAmbientes.pegarNomeAmbiente(mapa[playerY][playerX]);
                    Ambiente ambienteAtualObj = objGerenciadorDeAmbientes.pegarObjAmbiente(ambienteAtualNome);
                        if (ambienteAtualObj != null)
                        {
                            String eventoSorteado = objGerenciadorDeEventos.sortearEvento(ambienteAtualObj);
                            if (!eventoSorteado.equals(""))
                            {
                                eventoAtual = eventoSorteado;
                                objGerenciadorDeEventos.aplicarEventoAmbiente(player, ambienteAtualObj, eventoSorteado);
                            }
                        }
                    turnoAtual ++;
                    playerSeMovimentou = false;
                }
                switch  (k.getKeyCode())
                {
                    case KeyEvent.VK_E:
                        //System.out.println("Aberto");
                        boolean menuAbertoHolder = !menuAberto;
                        menuAberto = menuAbertoHolder;
                        inventárioAberto = false;
                        repaint();
                        break;
                    case KeyEvent.VK_2: //Descansar
                        if (menuAberto)
                        {
                            menuAberto = false;
                            player.setFome(player.getFome() - 20); //TODO   
                            player.setSede(player.getSede() - 20);
                            player.setEnergia(energiaMáxima);
                        }
                        break;
                    case KeyEvent.VK_3: //Inventário
                        if (menuAberto)
                        {
                            menuAberto = false;
                            inventárioAberto = true;
                            escolherItemInventário();
                        }
                        break;
                }
            }
            if (playerEmCombate)
            {

            }
            if (turnoAtual % 4 == 0)
            {
                player.setFome(player.getFome() - 1);
                player.setSede(player.getSede() - 1);
            }
            if (turnoAtual % 60 == 0)
            { 
                diasSePassaram++; 
                for (Item item : player.getInventário())
                {
                    if (item instanceof Alimento)
                    {
                        Alimento alimento = (Alimento) item;
                        alimento.setValidade(alimento.getValidade() - 1);
                        if (alimento.getValidade() < 1) //TODO apodrecimento
                        {
                            objInventario.removerItem(player, alimento);
                        }
                    }
                }
            }
            if (player.getFome() == 0 ||player.getSede() == 0)
            { playerEstáVivo = false; }
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
