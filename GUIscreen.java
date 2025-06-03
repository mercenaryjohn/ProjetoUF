
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
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import Player.*;
import Ambientes.*;
import Eventos.Combate;
import Itens.*;

public class GUIscreen extends JPanel implements ActionListener, KeyListener
{
    private boolean classeFoiEscolhida = false;
    private boolean playerEstáVivo = true;
    private boolean vitóriaYouWin = false;
    private boolean menuAberto = false;
    private boolean inventárioAberto = false;
    private boolean statsAberto = true;
    private EscolherClasse player;
    private InventarioClasse objInventario;
    private int itemSelecionadoInventário = 0;
    private Map<String, Integer> cópiaDeMapaContadorLista = new HashMap<>();

    @SuppressWarnings("unused")
    private long tempoAtual; // usado em keyPressed para limitar a velocidade de certas ações
    private long ultimaAcaoFeita = 0;
    private int delayEmMili = 100; // && tempoQuePassou >= delayEmMili

    private int vidaMáxima;
    private int sanidadeMáxima;
    private int energiaMáxima;
    private int sedeMáxima;
    private int fomeMáxima;

    private GerenciadorDeAmbientes objGerenciadorDeAmbientes = new GerenciadorDeAmbientes();
    private GerenciadorDeEventos objGerenciadorDeEventos = new GerenciadorDeEventos();
    private Combate objCombate = objGerenciadorDeEventos.getObjCombate();
    private boolean playerEmCombate = false;
    private int acaoEscolhidaCombate = 0;
    private boolean temArmaOuNão = false;
    private boolean temMunicaoOuNão = false;

    private String últimoItemEncontrado = "";
                //Tem que começar em 1, já que só aparece a partir do 2° item
    private int numeroDeItensEcontradosIguais = 1;
    private String eventoAtual = "";
    private int turnoAtual = 0;
    private int diasSePassaram = 0;
    private int quantosTurnosÉumDia = 24;
    private int diasSePassaramCondiçãoVitória = 40;
    
    private int custoDeDescansar = 20;

    private char[][] mapa;
    private int mapaAltura;
    private int mapaLargura;
    private int playerX;
    private int playerY;

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
        vidaMáxima = player.getVida();
        sanidadeMáxima = player.getSanidade();
        energiaMáxima = player.getEnergia();
        sedeMáxima = player.getSede();
        fomeMáxima = player.getFome();
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
            EscolherClasse classeEscolhida = new EscolherClasse(escolha);
            return classeEscolhida;
        } 
        else 
        {
            System.exit(0); // Sai se cancelar
            return null;
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
    public void escolherItemInventárioIngameUI(int indexEscolhido, String modo) 
    {
        List<String> chavesExibidas = new ArrayList<>(cópiaDeMapaContadorLista.keySet());
        Map<String, List<Item>> mapaItensPorChave = new LinkedHashMap<>();

        if (indexEscolhido >= 1 && indexEscolhido <= chavesExibidas.size()) 
        {
            String chaveEscolhida = chavesExibidas.get(indexEscolhido - 1);
            for (Item item : player.getInventário()) 
            {
                String chaveNome;

                if (item instanceof Alimento) 
                {
                    Alimento alimento = (Alimento) item;
                    chaveNome = alimento.getNome() + " (Validade: " + alimento.getValidade() + ")";
                } 
                else { chaveNome = item.getNome(); }
                // Os objetos
                mapaItensPorChave.putIfAbsent(chaveNome, new ArrayList<>());
                mapaItensPorChave.get(chaveNome).add(item);
            }

            List<Item> itensReais = mapaItensPorChave.get(chaveEscolhida);

            // modo ENTER, usar
            if (itensReais != null && !itensReais.isEmpty() && modo.equals("enter"))
            {
                Item itemEscolhido = itensReais.get(0); // Sempre o primeiro da lista

                objInventario.usarItem(player, itemEscolhido);
                objInventario.removerItem(player, itemEscolhido);
            
                    if (vidaMáxima < player.getVida()) // Para não passar do máximo
                    { player.setVida(vidaMáxima); }

                    if (sanidadeMáxima < player.getSanidade())
                    { player.setSanidade(sanidadeMáxima); }

                    if (energiaMáxima < player.getEnergia())
                    { player.setEnergia(vidaMáxima); }

                    if (sedeMáxima < player.getSede())
                    { player.setSede(sedeMáxima); }

                    if (fomeMáxima < player.getFome())
                    { player.setFome(fomeMáxima); }
            }
            // modo R, remover
            if (itensReais != null && !itensReais.isEmpty() && modo.equals("r"))
            {
                Item itemEscolhido = itensReais.get(0); // Sempre o primeiro da lista

                objInventario.removerItem(player, itemEscolhido);
            }
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
    private BufferedImage playerSprite;

    private BufferedImage cavernaSprite;
        private BufferedImage cavernaTeto;
    private BufferedImage florestaSprite;
        private BufferedImage florestaFolhas;
    private BufferedImage lagoRioSprite;
    private BufferedImage montanhaSprite;
    private BufferedImage ruinasSprite;
    private BufferedImage planicieSprite;

    private BufferedImage LoboSprite;
    private BufferedImage UrsoSprite;
    private BufferedImage ExploradorPerdidoSprite;
    private BufferedImage CriaturaSprite;
    private BufferedImage JacareSprite;
    private BufferedImage PiranhaSprite;
    private BufferedImage SobreviventeHostilSprite;

    public void setSprites() 
    {
        try 
        {
            if (player.getNome().equals("Escoteiro"))
                { playerSprite = ImageIO.read(getClass().getResource("/Sprites/escoteiro.png")); }
            else if (player.getNome().equals("Engenheiro"))
                { playerSprite = ImageIO.read(getClass().getResource("/Sprites/engenheiro.png")); }
            else if (player.getNome().equals("Veterinário"))
                { playerSprite = ImageIO.read(getClass().getResource("/Sprites/veterinario.png")); }
            else if (player.getNome().equals("Prisioneiro"))
                { playerSprite = ImageIO.read(getClass().getResource("/Sprites/prisioneiro.png")); }

            cavernaSprite = ImageIO.read(getClass().getResource("/Sprites/caverna.png"));
                cavernaTeto = ImageIO.read(getClass().getResource("/Sprites/cavernaTeto.png"));

            florestaSprite = ImageIO.read(getClass().getResource("/Sprites/floresta.png"));
                florestaFolhas = ImageIO.read(getClass().getResource("/Sprites/florestaFolhas.png"));

            lagoRioSprite = ImageIO.read(getClass().getResource("/Sprites/lagoRio.png"));
            montanhaSprite = ImageIO.read(getClass().getResource("/Sprites/montanha.png"));
            ruinasSprite = ImageIO.read(getClass().getResource("/Sprites/ruinas.png"));
            planicieSprite = ImageIO.read(getClass().getResource("/Sprites/planicie.png"));
            if (playerSprite == null)
               System.out.println("Imagem é null"); 
        } 
        catch (IOException | IllegalArgumentException e) 
        {
            System.err.println("Não conseguiu carregar um sprite [player/sprite]: " + e.getMessage()); //DEBUG
        }
        try
        {
            LoboSprite = ImageIO.read(getClass().getResource("/Sprites/lobo.png"));
            UrsoSprite = ImageIO.read(getClass().getResource("/Sprites/urso.png"));
            ExploradorPerdidoSprite = ImageIO.read(getClass().getResource("/Sprites/exploradorPerdido.png"));
            CriaturaSprite = ImageIO.read(getClass().getResource("/Sprites/criatura.png"));
            JacareSprite = ImageIO.read(getClass().getResource("/Sprites/jacare.png"));
            PiranhaSprite = ImageIO.read(getClass().getResource("/Sprites/piranha.png"));
            SobreviventeHostilSprite = ImageIO.read(getClass().getResource("/Sprites/sobreviventeHostil.png"));
        }
        catch (IOException | IllegalArgumentException e) 
        {
            System.err.println("Não conseguiu carregar um sprite [inimigos]: " + e.getMessage()); //DEBUG
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (classeFoiEscolhida == false) 
        {// Player ainda não escolheu
            return; 
        }

        int chunkVisao = 5; // 5
        int tileTamanho = 30; // 30
        int playerTamanho = tileTamanho + 30; // tileTamanho + 30

        // Posição atual do player
        double[] localização = player.getLocalização();
        playerX = mapaLargura / 2 + (int)localização[0]; // Em X, tem que ser adição
        playerY = (mapaAltura / 2) - (int)localização[1]; // Em Y, tem que ser subtração

        if (player.getEnergia() <= 0)
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
        
        int screenXPLAYER = 0;
        int screenYPLAYER = 0;

        for (int y = inicioY; y < fimY; y++) 
        {
            for (int x = inicioX; x < fimX; x++) 
            {
                int screenX = deslocaX + (x - inicioX) * tileTamanho;
                int screenY = deslocaY + (y - inicioY) * tileTamanho;
                if (true)
                {
                    if (mapa[y][x] == 'F')
                        { g.setColor(florestaVerde);
                            if (florestaSprite != null) 
                        g.drawImage(florestaSprite, screenX + 220, screenY, tileTamanho, tileTamanho, null); }
                    else if (mapa[y][x] == 'M') 
                        { g.setColor(montanhaBranco); 
                            if (montanhaSprite != null)
                        g.drawImage(montanhaSprite, screenX + 220, screenY, tileTamanho, tileTamanho, null); }
                    else if (mapa[y][x] == 'C') 
                        { g.setColor(cavernaCinza); 
                            if (cavernaSprite != null)
                        g.drawImage(cavernaSprite, screenX + 220, screenY, tileTamanho, tileTamanho, null); }
                    else if (mapa[y][x] == '~') 
                        { g.setColor(aguaAzul);
                            if (lagoRioSprite != null)
                        g.drawImage(lagoRioSprite, screenX + 220, screenY, tileTamanho, tileTamanho, null); }
                    else if (mapa[y][x] == 'R') 
                        { g.setColor(ruinaRoxo); 
                            if (ruinasSprite != null)
                        g.drawImage(ruinasSprite, screenX + 220, screenY, tileTamanho, tileTamanho, null); }
                    else if (mapa[y][x] == '_') 
                        { g.setColor(planicieCor);
                            if (planicieSprite != null)
                        g.drawImage(planicieSprite, screenX + 220, screenY, tileTamanho, tileTamanho, null); }
                    else { g.setColor(Color.RED); } // Por garantia

                    if (cavernaSprite == null || florestaSprite == null || lagoRioSprite == null || 
                    montanhaSprite == null || ruinasSprite == null || planicieSprite == null)
                    { g.fillRect(screenX + 220, screenY, tileTamanho, tileTamanho); }
                }
                if(y == playerY && x == playerX) //Fallback para playerSprite
                {
                    if (mapa[y][x] == 'F')
                        { g.setColor(jogadorCorEscura); }
                    else if (mapa[y][x] == 'M') 
                        { g.setColor(jogadorCorEscura); }
                    else if (mapa[y][x] == 'C') 
                        { g.setColor(jogadorCorClara); }
                    else if (mapa[y][x] == '~') 
                        { g.setColor(jogadorCorEscura); }
                    else if (mapa[y][x] == 'R') 
                        { g.setColor(jogadorCorEscura); }
                    else if (mapa[y][x] == '_') 
                        { g.setColor(jogadorCorEscura); }
                    else
                        { g.setColor(jogadorCorEscura);}
                    screenXPLAYER = screenX;
                    screenYPLAYER = screenY;

                    if (playerSprite == null)
                    {
                        g.fillOval(screenX + 220, screenY, tileTamanho, tileTamanho);
                    }
                }
            }
        }
        // Mostra o sprite do player por cima do mapa
        if(playerSprite != null)
        {
            g.drawImage(playerSprite, screenXPLAYER + 220 - (playerTamanho / 4),
             screenYPLAYER - (playerTamanho / 2) - 2,
             playerTamanho, playerTamanho, null);
        }
        // TETOS, aparecem por cima do player
        for (int y = inicioY; y < fimY; y++)
        {
            for (int x = inicioX; x < fimX; x++) 
            {
                int screenX = deslocaX + (x - inicioX) * tileTamanho;
                int screenY = deslocaY + (y - inicioY) * tileTamanho;
                if (mapa[y][x] == 'F' && florestaFolhas != null)
                    { g.drawImage(florestaFolhas, screenX + 220, screenY, tileTamanho, tileTamanho, null); }
                if (mapa[y][x] == 'C' && florestaFolhas != null)
                    { g.drawImage(cavernaTeto, screenX + 220, screenY, tileTamanho, tileTamanho, null); }
            }
        }
        //####################################################################################################
        //####################################################################################################
        if (menuAberto)
        {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(getWidth() / 2 + 10, (getHeight() / 2) - 90, 420, 140); //Fundo do fundo

            g.setColor(Color.BLACK);
            g.fillRect(getWidth() / 2 + 20, (getHeight() / 2) - 80, 400, 120); //Fundo do menu
    
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 16));
    
            for (int i = 0; i < listaDeOpcoes.length; i++) 
            {
                g.drawString(listaDeOpcoes[i], getWidth() / 2 + 30, (getHeight() / 2) - 50 + i * 20);
            }
        }
        //####################################################################################################
        //####################################################################################################
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
                "Vida: " + player.getVida() + " / " + vidaMáxima,
                "Fome: " + player.getFome() + " / " + fomeMáxima,
                "Sede: " + player.getSede() + " / " + sedeMáxima,
                "Energia: " + player.getEnergia() + " / " + energiaMáxima,
                "Sanidade: " + player.getSanidade() + " / " + sanidadeMáxima,
                "Posição (X,Y): " + mostrarPosição[0] +","+ mostrarPosição[1],
                "________________________________________",
                "Ambiente: " + ambienteAtualNome,
                "   " + DescriçãoParte1, DescriçãoParte2, DescriçãoParte3 + DescriçãoParte4,
                "Turno: " + turnoAtual,
                "   ("+ diasSePassaram + " / "+ diasSePassaramCondiçãoVitória + ") dias se passaram",
                "Último recurso encontrado: " + últimoItemEncontrado,
                "Evento atual: " + eventoAtual,
                "Número de inimigos derrotados: " + objCombate.getNumeroDeInimigosDerrotados(),
                "   ("+ objCombate.getCondiçãoDeVitóriaInimgosDerrotados() + ") para vencer"
                };
                statsArray = statsArrayA;
            }
            else
            {
                    String[] statsArrayB = {
                    "________________________________________",
                    "Classe: " + player.getNome(),
                    "Vida: " + player.getVida() + " / " + vidaMáxima,
                    "Fome: " + player.getFome() + " / " + fomeMáxima,
                    "Sede: " + player.getSede() + " / " + sedeMáxima,
                    "Energia: " + player.getEnergia() + " / " + energiaMáxima,
                    "Sanidade: " + player.getSanidade() + " / " + sanidadeMáxima,
                    "________________________________________",
                    "Ambiente: " + ambienteAtualNome,
                    "   " + DescriçãoParte1, DescriçãoParte2, DescriçãoParte3 + DescriçãoParte4,
                    "Turno: " + turnoAtual,
                    "   ("+ diasSePassaram + ") dias se passaram",
                    "Último recurso encontrado: " + últimoItemEncontrado,
                    "Evento atual: " + eventoAtual,
                    "Número de inimigos derrotados: " + objCombate.getNumeroDeInimigosDerrotados(),
                    "   ("+ objCombate.getCondiçãoDeVitóriaInimgosDerrotados() + ") para vencer"
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
        //####################################################################################################
        //####################################################################################################
        if (playerEmCombate)
        {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(deslocaX + 230, deslocaY + 10, 600, 600); //Fundo
            g.setColor(Color.BLACK);
            g.fillRect(deslocaX + 240, deslocaY + 20, 580, 580); //Fundo

            if (eventoAtual.equals("Lobo"))
                { g.drawImage(LoboSprite, deslocaX + 490, deslocaY + 80, 300, 300, null); }
            if (eventoAtual.equals("Urso"))
                { g.drawImage(UrsoSprite, deslocaX + 490, deslocaY + 80, 300, 300, null); }
            if (eventoAtual.equals("Explorador perdido"))
                { g.drawImage(ExploradorPerdidoSprite, deslocaX + 490, deslocaY + 80, 300, 300, null); }
            if (eventoAtual.equals("Criatura"))
                { g.drawImage(CriaturaSprite, deslocaX + 490, deslocaY + 80, 300, 300, null); }
            if (eventoAtual.equals("Jacaré"))
                { g.drawImage(JacareSprite, deslocaX + 490, deslocaY + 80, 300, 300, null); }
            if (eventoAtual.equals("Piranha"))
                { g.drawImage(PiranhaSprite, deslocaX + 490, deslocaY + 80, 300, 300, null); }
            if (eventoAtual.equals("Sobrevivente hostil"))
                { g.drawImage(SobreviventeHostilSprite, deslocaX + 490, deslocaY + 80, 300, 300, null); }


            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            if (objCombate.getNomeInimigo() != null)
                { 
                    g.drawString(objCombate.getNomeInimigo(), deslocaX + 245, deslocaY + 40); 
                    g.drawString("Vida: " + objCombate.getVidaInimigo() + "/" + objCombate.getVidaMáximaInimigo()
                    , deslocaX + 245, deslocaY + 60);
                }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            if (objCombate.getUltimaAcaoDoInimigo() != null)
                { 
                    g.drawString("O que ocorreu no último turno:", deslocaX + 245, deslocaY + 90);
                    g.drawString(" "+ objCombate.getUltimaAcaoDoInimigo(), deslocaX + 245, deslocaY + 120); 
                }

            g.setFont(new Font("Arial", Font.PLAIN, 16));
            if(acaoEscolhidaCombate == -1)
                { 
                    g.drawString("USAR ARMA" + " <", deslocaX + 245, deslocaY + 380);
                    g.setColor(Color.GRAY);
                    g.drawString("FUGIR", deslocaX + 245, deslocaY + 420);
                    g.drawString("ATACAR", deslocaX + 245, deslocaY + 400);
                }
            if(acaoEscolhidaCombate == 0)
                { 
                    g.drawString("ATACAR" + " <", deslocaX + 245, deslocaY + 400);
                    g.setColor(Color.GRAY);
                    g.drawString("FUGIR", deslocaX + 245, deslocaY + 420);
                    g.drawString("USAR ARMA", deslocaX + 245, deslocaY + 380);
                }
            if(acaoEscolhidaCombate == 1)
                { 
                    g.drawString("FUGIR" + " <", deslocaX + 245, deslocaY + 420);
                    g.setColor(Color.GRAY);
                    g.drawString("ATACAR", deslocaX + 245, deslocaY + 400);
                    g.drawString("USAR ARMA", deslocaX + 245, deslocaY + 380);
                }
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            g.setColor(Color.WHITE);
            if(temArmaOuNão)
                {g.drawString( "[Tem arma]", deslocaX + 245, deslocaY + 440);}
            if(temMunicaoOuNão)
                {g.drawString( "[Tem munição]", deslocaX + 245, deslocaY + 460);}
            g.setColor(Color.GRAY);
            if(!temArmaOuNão)
                {g.drawString( "[Sem arma]", deslocaX + 245, deslocaY + 440);}
            if(!temMunicaoOuNão)
                {g.drawString( "[Sem munição]", deslocaX + 245, deslocaY + 460);}
        }
        //####################################################################################################
        //####################################################################################################
        if (inventárioAberto) // Mostrar inventário
        {
            Map<String, Integer> mapaContadorLista = new HashMap<>();
            // Usar para contar os items da list Inventário

            for (Item item : player.getInventário()) // Coloca itens no HasMap
            {
                if (item instanceof Alimento) 
                {
                    Alimento alimentoHM = (Alimento) item;
                    String chave = alimentoHM.getNome() + " (Validade: " + alimentoHM.getValidade() + ")";
                    mapaContadorLista.put(chave, mapaContadorLista.getOrDefault(chave, 0) + 1);
                }
                else if (item instanceof Arma) 
                {
                    Arma armaHM = (Arma) item;
                    String chave = armaHM.getNome() + " (Durabilidade: " + armaHM.getDurabilidade() + ")";
                    mapaContadorLista.put(chave, mapaContadorLista.getOrDefault(chave, 0) + 1);
                } 
                else
                {
                    String chaveHM = item.getNome();
                    mapaContadorLista.put(chaveHM, mapaContadorLista.getOrDefault(chaveHM, 0) + 1);
                } 
            }
            int tiposDiferentesDeItem = mapaContadorLista.size();
            cópiaDeMapaContadorLista = mapaContadorLista;

            int alturaInicio = 220 + tiposDiferentesDeItem * 4; // usado para subtrair
            int alturaPosição1 = alturaInicio - 20;
            int alturaPosição2 = alturaInicio - 25;

            g.setColor(Color.DARK_GRAY);
            g.fillRect(getWidth() / 2 + 10, (getHeight() / 2) - alturaInicio - 10, 420, //Fundo do fundo
            140 + tiposDiferentesDeItem * 20);

            g.setColor(Color.BLACK);
            g.fillRect(getWidth() / 2 + 20, (getHeight() / 2) - alturaInicio, 400, 
            120 + tiposDiferentesDeItem * 20);
    
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 16));

            g.drawString("Inventário:", getWidth() / 2 + 30,(getHeight() / 2) - alturaPosição1);
            g.drawString("________________________________________", getWidth() / 2 + 30, (getHeight() / 2) 
            - alturaPosição2);

            // Print cada item
            int index = 1;
            int i = 1;
            for (Map.Entry<String, Integer> entry : mapaContadorLista.entrySet())
            {
                String linha = " x" + entry.getValue() + " [" + index + "]- " + entry.getKey();
                g.drawString(linha, getWidth() / 2 + 30, (getHeight() / 2) - alturaPosição2 + i * 20);
                index++;
                i++;
            }
            g.drawString("Selecionado [ " + itemSelecionadoInventário +" ] ",
             getWidth() / 2 + 30, (getHeight() / 2) - alturaPosição2 + i * 20);

            g.drawString("Aperte (enter) para usar | (r) para remover",
             getWidth() / 2 + 30, (getHeight() / 2) - alturaPosição2 + 17 + i * 20);

            g.drawString("________________________________________", getWidth() / 2 + 30,
             (getHeight() / 2) - alturaPosição2 + 22 + i * 20);
        }
        //####################################################################################################
        //####################################################################################################
        if (playerEstáVivo == false)
        {
            g.setColor(Color.RED);
            g.fillRect(deslocaX + 230, getHeight() / 2 - 10, 420, 70); //Borda
            g.setColor(Color.BLACK);
            g.fillRect(deslocaX + 240, getHeight() / 2, 400, 50); //Fundo
    
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            if (mapa[playerY][playerX] == '~' && player.getEnergia() == 0)
            {
                g.drawString("GAME OVER... Morreu por afogamento", deslocaX + 260, getHeight() / 2 + 30); 
            }
            else { g.drawString("GAME OVER...", deslocaX + 260, getHeight() / 2 + 30); }
        }

        if(vitóriaYouWin)
        {
            g.setColor(Color.WHITE);
            g.fillRect(deslocaX + 230, getHeight() / 2 - 10, 575, 70); //Borda
            g.setColor(Color.BLACK);
            g.fillRect(deslocaX + 240, getHeight() / 2, 555, 50); //Fundo
    
            g.setColor(florestaVerde);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("PARABÉNS! VOCÊ SOBREVIVEU À ÚLTIMA FRONTEIRA!!", deslocaX + 250, getHeight() / 2 + 30);
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
    public void keyPressed(KeyEvent k)
    {
        long tempoAtual = System.currentTimeMillis();
        if (playerEstáVivo && !vitóriaYouWin)
        {
            if (!playerEmCombate && !inventárioAberto)
            {
                switch(k.getKeyCode())
                {
                    case KeyEvent.VK_D:
                        if (player.getEnergia() > 0 && tempoAtual - ultimaAcaoFeita >= delayEmMili)
                        {
                            double[] posiçãoXD = player.getLocalização();
                            posiçãoXD[0] = posiçãoXD[0] + 1;
                            player.setLocalização(posiçãoXD);
                            player.setEnergia(player.getEnergia() - 1);
                            ultimaAcaoFeita = tempoAtual;
                            objGerenciadorDeAmbientes.setPodeVasculhar();
                            ativarChanceEvento();
                        }
                        menuAberto = false;
                        break;
                    case KeyEvent.VK_S:
                        if (player.getEnergia() > 0 && tempoAtual - ultimaAcaoFeita >= delayEmMili)
                        {
                            double[] posiçãoYS = player.getLocalização();
                            posiçãoYS[1] = posiçãoYS[1] - 1;
                            player.setLocalização(posiçãoYS);
                            player.setEnergia(player.getEnergia() - 1);
                            ultimaAcaoFeita = tempoAtual;
                            objGerenciadorDeAmbientes.setPodeVasculhar();
                            ativarChanceEvento();
                        }
                        menuAberto = false;
                        break;
                    case KeyEvent.VK_A:
                        if (player.getEnergia() > 0 && tempoAtual - ultimaAcaoFeita >= delayEmMili)
                        {
                            double[] posiçãoXA = player.getLocalização();
                            posiçãoXA[0] = posiçãoXA[0] - 1;
                            player.setLocalização(posiçãoXA);
                            player.setEnergia(player.getEnergia() - 1);
                            ultimaAcaoFeita = tempoAtual;
                            objGerenciadorDeAmbientes.setPodeVasculhar();
                            ativarChanceEvento();
                        }
                        menuAberto = false;
                        break;
                    case KeyEvent.VK_W:
                        if (player.getEnergia() > 0 && tempoAtual - ultimaAcaoFeita >= delayEmMili)
                        {
                            double[] posiçãoYW = player.getLocalização();
                            posiçãoYW[1] = posiçãoYW[1] + 1;
                            player.setLocalização(posiçãoYW);
                            player.setEnergia(player.getEnergia() - 1);
                            ultimaAcaoFeita = tempoAtual;
                            objGerenciadorDeAmbientes.setPodeVasculhar();
                            ativarChanceEvento();
                        }
                        menuAberto = false;
                        break;
                    case KeyEvent.VK_1: //Vasculhar
                        if (menuAberto && !últimoItemEncontrado.equals("inventário cheio " +
                         "("+ player.getCapacidadeInventário() +")") 
                         && tempoAtual - ultimaAcaoFeita >= delayEmMili
                         && objGerenciadorDeAmbientes.getPodeVasculhar())
                        {
                            //menuAberto = false;
                            objGerenciadorDeAmbientes.addUmaVezVasculhou();
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
                                    Item itemParaAdicionar = itemRecurso.clonarObjItem(itemRecurso);
                                    objInventario.adicionarItem(player, itemParaAdicionar);
                                    if (últimoItemEncontrado.equals(itemParaAdicionar.getNome()))
                                    { 
                                        numeroDeItensEcontradosIguais++;
                                        últimoItemEncontrado = últimoItemEncontrado + " +" + numeroDeItensEcontradosIguais; 
                                    }
                                    else 
                                    { 
                                        numeroDeItensEcontradosIguais = 1;
                                        últimoItemEncontrado = itemParaAdicionar.getNome(); 
                                    }
                                }
                                else { últimoItemEncontrado = "(Nada)"; }
                                if (!objGerenciadorDeAmbientes.getPodeVasculhar())
                                { últimoItemEncontrado = "(Procure em outro lugar)"; }
                                if ( player.getInventário().size() == player.getCapacidadeInventário() )
                                { últimoItemEncontrado = "inventário cheio " + "("+ player.getCapacidadeInventário() +")"; }
                            }
                            player.setEnergia(player.getEnergia() - 1);
                            ultimaAcaoFeita = tempoAtual;
                            passagemDeTurnos();
                        }
                        break;
                    default:
                        break;
                }
                switch  (k.getKeyCode())
                {
                    case KeyEvent.VK_E: //Menu
                        boolean menuAbertoHolder = !menuAberto;
                        menuAberto = menuAbertoHolder;
                        inventárioAberto = false;
                        repaint();
                        break;
                    case KeyEvent.VK_2: //Descansar
                        if (menuAberto && inventárioAberto == false && mapa[playerY][playerX] != '~')
                        {
                            menuAberto = false;
                            player.setFome(player.getFome() - custoDeDescansar);
                            player.setSede(player.getSede() - custoDeDescansar);
                            player.setSanidade(player.getSanidade() + 40);
                            if (sanidadeMáxima < player.getSanidade())
                                { player.setSanidade(sanidadeMáxima); }
                            player.setEnergia(energiaMáxima);
                            ativarChanceEvento();
                        }
                        break;
                    case KeyEvent.VK_3: //Abrir Inventário
                        if (menuAberto)
                        {
                            menuAberto = false;
                            inventárioAberto = true;
                        }
                        break;
                }
            }
            if (inventárioAberto) //Usando o INVENTÁRIO
            {
                switch (k.getKeyCode()) 
                {
                    case KeyEvent.VK_E:
                        inventárioAberto = false;
                        itemSelecionadoInventário = 0;
                        break;
                    case KeyEvent.VK_W:
                        itemSelecionadoInventário = itemSelecionadoInventário + 1;
                        break;
                    case KeyEvent.VK_S:
                        itemSelecionadoInventário = itemSelecionadoInventário - 1;
                        if (itemSelecionadoInventário < 1)
                        { itemSelecionadoInventário = 0;}
                        break;
                    case KeyEvent.VK_ENTER:
                        if (player.getInventário().size() > 0)
                        {
                            String modoDeUso = "enter";
                            escolherItemInventárioIngameUI(itemSelecionadoInventário, modoDeUso);
                            passagemDeTurnos();
                        }
                        break;
                    case KeyEvent.VK_R:
                        if (player.getInventário().size() > 0)
                        {
                            String modoDeUso = "r";
                            escolherItemInventárioIngameUI(itemSelecionadoInventário, modoDeUso);
                            passagemDeTurnos();
                            itemSelecionadoInventário = 0;
                        }
                        break;
                }
            }
            if (playerEmCombate) //////////// COMBATE ////////////
            {
                ///////////// Encontrar item arma e munição no inventário
                Arma armaEncontrada = null;
                Item municaoEncontrada = null;
                for (Item item : player.getInventário()) 
                {
                    if (item instanceof Arma && armaEncontrada == null) 
                        { armaEncontrada = (Arma) item; temArmaOuNão = true;} 
                    else if (item.getNome().equals("Munição") && municaoEncontrada == null) 
                        { municaoEncontrada = item; temMunicaoOuNão = true;}

                    if (armaEncontrada != null && municaoEncontrada != null) 
                        { break; }
                }
                if (armaEncontrada == null)
                    { temArmaOuNão = false; }
                if (municaoEncontrada == null)
                    { temMunicaoOuNão = false; } ////////////////////////

                switch (k.getKeyCode()) 
                {
                    case KeyEvent.VK_E:
                        objCombate.turnoDoInimigo(player);
                        escolherAcaoCombate(acaoEscolhidaCombate, armaEncontrada, municaoEncontrada);
                        acaoEscolhidaCombate = 0;
                        if (objCombate.getVidaInimigo() <= 0 || !objCombate.getEmCombate())
                            { 
                                if (objCombate.getVidaInimigo() <= 0)
                                { 
                                    objCombate.inimigoFoiDerrotado(); 
                                    if (objCombate.getNumeroDeInimigosDerrotados() == 
                                    objCombate.getCondiçãoDeVitóriaInimgosDerrotados())
                                        { vitóriaYouWin = true;}
                                }
                                objCombate.setEmCombate(false); 
                                objGerenciadorDeEventos.setEventoEstáOcorrendo(false);
                                eventoAtual = "";
                                objGerenciadorDeEventos.iniciarTurnosDePazApósCombate();
                                objCombate.setUltimaAcaoDoInimigo("");
                            }
                        passagemDeTurnos();
                        break;
                    case KeyEvent.VK_W:
                        acaoEscolhidaCombate = acaoEscolhidaCombate - 1;
                        break;
                    case KeyEvent.VK_S:
                        acaoEscolhidaCombate = acaoEscolhidaCombate + 1;
                        if (itemSelecionadoInventário < 1)
                        { itemSelecionadoInventário = 0;}
                        break;
                }
                if (acaoEscolhidaCombate < 0)
                    { acaoEscolhidaCombate = -1;}
                if (acaoEscolhidaCombate > 1)
                    { acaoEscolhidaCombate = 1;}
            }
            playerEmCombate = objCombate.getEmCombate(); //Se for EventoCriatura será true

            // Sempre checar isso, evita inventário não estar cheio mas com mensagem de cheio
            if ( player.getInventário().size() < player.getCapacidadeInventário()
             && últimoItemEncontrado.equals("inventário cheio " + "("+ player.getCapacidadeInventário() +")"))
            { últimoItemEncontrado = ""; }
            
            if (player.getVida() <= 0 || player.getFome() <= 0 || 
            player.getSede() <= 0 || player.getSanidade() <= 0)
            { playerEstáVivo = false; }
        }
        repaint();
    }
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
    public void passagemDeTurnos()
    {
        turnoAtual++;
        objGerenciadorDeEventos.passarTurnosDePazApósCombate();
        //Diminui fome e Sede com o passar dos turnos
        if (turnoAtual % 4 == 0 && !menuAberto && !inventárioAberto)
        {
            player.setFome(player.getFome() - 1);
            player.setSede(player.getSede() - 1);
        }
        //Passagem dos dias e diminuir validade itens
        if (turnoAtual % quantosTurnosÉumDia == 0 && turnoAtual != 0 && !menuAberto && !inventárioAberto) 
        { 
            diasSePassaram++;
            if (diasSePassaram == diasSePassaramCondiçãoVitória)
                { vitóriaYouWin = true; }
            List<Item> itensParaRemover = new ArrayList<>(); 
            //remover um item enquanto dentro do for loop gera exceptions
            for (Item item : player.getInventário())
            {
                if (item instanceof Alimento)
                {
                    ((Alimento) item).setValidade(((Alimento) item).getValidade() - 1);
                    if (((Alimento) item).getValidade() < 1)
                    {
                        itensParaRemover.add((Alimento) item);
                    }
                }
            }
            for (Item item : itensParaRemover) //remove se foi adicionado a lista
            { objInventario.removerItem(player, item); }
        } 
    }
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
    // condições: (playerSeMovimentou == true && !menuAberto && !inventárioAberto) // EVENTOS
    public void ativarChanceEvento()
    {
        double[] localização = player.getLocalização();
        int playerX = mapaLargura / 2 + (int)localização[0]; // Em X, tem que ser adição
        int playerY = (mapaAltura / 2) - (int)localização[1]; // Em Y, tem que ser subtração
        String ambienteAtualNome = objGerenciadorDeAmbientes.pegarNomeAmbiente(mapa[playerY][playerX]);
        Ambiente ambienteAtualObj = objGerenciadorDeAmbientes.pegarObjAmbiente(ambienteAtualNome);

        if (objGerenciadorDeEventos.getEventoEstáOcorrendo()) //true
        {
            //Chance de remover o evento
            boolean eventoEstáOcorrendo = objGerenciadorDeEventos.removerEvento(eventoAtual, ambienteAtualObj);
            objGerenciadorDeEventos.setEventoEstáOcorrendo(eventoEstáOcorrendo);
            if (!objGerenciadorDeEventos.getEventoEstáOcorrendo()) // false
            { 
                eventoAtual = "";
            }
        }
        if (ambienteAtualObj != null) 
        {
            if (!objGerenciadorDeEventos.getEventoEstáOcorrendo()) // false
            {
                String eventoSorteado = objGerenciadorDeEventos.sortearEvento(ambienteAtualObj);
                if (!eventoSorteado.equals(""))
                {
                    eventoAtual = eventoSorteado;
                    objGerenciadorDeEventos.aplicarEventoAmbiente(player, ambienteAtualObj, eventoSorteado);
                    if (!objGerenciadorDeEventos.getEventoEstáOcorrendo())
                    { eventoAtual = ""; }
                }
            }
            else if (objGerenciadorDeEventos.getEventoEstáOcorrendo()) //Se já está ocorrendo, executar
                { objGerenciadorDeEventos.aplicarEventoAmbiente(player, ambienteAtualObj, eventoAtual); }
        }
        passagemDeTurnos();
    }
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
    public void escolherAcaoCombate(int acaoEscolhidaCombate, Arma armaEncontrada, Item municaoEncontrada)
    {
        if (acaoEscolhidaCombate == -1)
        {
            if (armaEncontrada != null && municaoEncontrada != null)
            {
                objInventario.removerItem(player, municaoEncontrada); //Gasta uma munição

                objCombate.setPoderDeAtaqueAdicionalArma(armaEncontrada.getPoderDeDano());
                ((Arma)armaEncontrada).usar();
                objCombate.atacar(player);

                if (((Arma)armaEncontrada).getDurabilidade() <= 0)
                    { objInventario.removerItem(player, armaEncontrada); }
            }
            if (armaEncontrada == null)
                { temArmaOuNão = false; }
            else { temArmaOuNão = true; }
            if (municaoEncontrada == null)
                { temMunicaoOuNão = false; }
            else { temMunicaoOuNão = true; }
        }

        if (acaoEscolhidaCombate == 0)
        { 
            objCombate.atacar(player);
        }
        if (acaoEscolhidaCombate == 1)
        { 
            objCombate.fugir();
        }
    }

    public void keyReleased(KeyEvent k) 
    {

    }

    public void keyTyped(KeyEvent k) 
    {

    }
}
