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

public class GUIscreen extends JPanel implements ActionListener, KeyListener
{
    private boolean classeFoiEscolhida = false;
    private boolean menuAberto = false;
    private String mensagemNaTela = "";
    private EscolherClasse player;
    char[][] mapa;

    //private GUIterminal terminal;

    Timer timer = new Timer(16, this); //60 FPS

    public GUIscreen (char[][] mapa)
    {
        this.mapa = mapa;

        setLayout(new BorderLayout());
        //terminal = new GUIterminal();
        //add(terminal, BorderLayout.SOUTH);
        
        addKeyListener(this);
        setFocusable(true); // Permite o panel receber input
        requestFocusInWindow(); //Força foco quando a tela aparece

        timer.start();
    }

    public void actionPerformed(ActionEvent k)
    {
        repaint();
    }

    public void setPlayer(EscolherClasse classeEscolhida)
    {
        player = classeEscolhida;
    }

    public void setClasseFoiEscolhida(boolean pronto) 
    {
        this.classeFoiEscolhida = pronto;
        repaint(); // força atualizar a tela
    }

    private String[] listaDeOpcoes;
    public void setListaDeOpcoes(String[] listaRecebida)
    {
        listaDeOpcoes = listaRecebida;
    }

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

    public void abrirMenuInterativo() //TODO
    {
        String[] opcoes = {"Descansar", "Inventário", "Status"};
        String escolha = (String) JOptionPane.showInputDialog
        (
            null,
            "Escolha uma ação:",
            "Menu do Jogo",
            JOptionPane.PLAIN_MESSAGE,
            null,
            opcoes,
            opcoes[0]
        );
    
        if (escolha != null) 
        {
            // Executar ação com base na escolha
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
            g.fillRect(40, 240, 400, 100); // background for menu options
    
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 16));
    
            for (int i = 0; i < listaDeOpcoes.length; i++) 
            {
                g.drawString(listaDeOpcoes[i], 50, 270 + i * 20);
            }
        }
    }

    Escolhas objEscolhas = new Escolhas();
    public void keyPressed(KeyEvent k) //TODO
    {
        switch(k.getKeyCode())
        {
            case KeyEvent.VK_E:
                if (!menuAberto) 
                {
                    System.out.println("Aberto");
                    menuAberto = true;
                    mensagemNaTela = "";
                    repaint();
                    break;
                } 
                else if (menuAberto) 
                {
                    System.out.println("Fechou");
                    String escolha = String.valueOf(k.getKeyChar());
                    objEscolhas.escolhas(escolha);
                    menuAberto = false;
                    repaint();
                    break;
                }
            case KeyEvent.VK_D:
                double[] posiçãoXD = player.getLocalização();
                posiçãoXD[0] = posiçãoXD[0] + 1;
                player.setLocalização(posiçãoXD);
                break;
            case KeyEvent.VK_S:
                double[] posiçãoYS = player.getLocalização();
                posiçãoYS[1] = posiçãoYS[1] - 1;
                player.setLocalização(posiçãoYS);
                break;
            case KeyEvent.VK_A:
                double[] posiçãoXA = player.getLocalização();
                posiçãoXA[0] = posiçãoXA[0] - 1;
                player.setLocalização(posiçãoXA);
                break;
            case KeyEvent.VK_W:
                double[] posiçãoYW = player.getLocalização();
                posiçãoYW[1] = posiçãoYW[1] + 1;
                player.setLocalização(posiçãoYW);
                break;
            
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
