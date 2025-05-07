import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainGUI
{
    public static void main(String[] args)
    {
        Scanner scanString = new Scanner(System.in); // inicia o scanner

        ///////////////////////////////////////////////////////////////////////// MAPA
        Ambiente objAmbiente = new Ambiente(null, null,
        null, null, null);
        char[][] mapa = objAmbiente.fazerMapaMundo();
        //objAmbiente.printVisaoAtualMapa(mapa, classeEscolhida.getLocalização()); //DEBUG
        /////////////////////////////////////////////////////////////////////////

        GUIframe frame = new GUIframe(mapa);
        frame.setVisible(true);
        GUIscreen tela = frame.getTela();

        //String[] listaDeClasses = {"1","Escoteiro","2","Engenheiro","3","Veterinário","4","Prisioneiro"};
        String[] listaDeClasses = {"Escoteiro","Engenheiro","Veterinário","Prisioneiro"};
        
        System.out.print("Escolha o seu sobrevivente:\n");
        for(int i = 0; (i + 1) < listaDeClasses.length; i = i + 2)
        {
            System.out.print(listaDeClasses[i] + " - " + listaDeClasses[i+1] + "\n");
        }

        EscolherClasse classeEscolhida = tela.mostrarMenuInicial(listaDeClasses);

        tela.setPlayer(classeEscolhida);
        tela.setClasseFoiEscolhida(true);
        GUIescolhas objEscolhasGUI = new GUIescolhas(classeEscolhida); 
        tela.setEscolhas(objEscolhasGUI);
        InventarioClasse objInventario = new InventarioClasse();
        tela.setInventario(objInventario);

        Escolhas objEscolhas = new Escolhas(); //terminal
        //EscolherClasse classeEscolhida = new EscolherClasse(escolhaDeClasseDoUsuario);
        System.out.println("Você escolheu o " + classeEscolhida.getNome() + "!");

        String inputDoUsuario = "default"; // para não dar problema no while
        //Escolhas objEscolhas = new Escolhas(); // relaciona um numero a uma escolha
        Movimento objMovimento = new Movimento(); // modifica a posição na array localização de coordenada x,y
        Agua objAgua = new Agua(); // DEBUG TODO
        Alimento objAlimento = new Alimento(0, 0); // DEBUG TODO
        objAlimento.setAlimentoStats("Fruta");

        int vidaMáxima = classeEscolhida.getVida();
        int energiaMáxima = classeEscolhida.getEnergia();

        String[] opções = {"2 - Descansar","3 - Inventário","4 - Status", "- para voltar [e] -"};
        tela.setListaDeOpcoes(opções);

        objInventario.adicionarItem(classeEscolhida, objAgua);  ////////////////////////////////
        objInventario.adicionarItem(classeEscolhida, objAlimento);
        
        while(!inputDoUsuario.equals("sair"))
        {
            int inputFoiAlgoEsperado = 1;
            while (inputFoiAlgoEsperado == 1) // Se o usuário escolher algo não esperado, pergunta novamente
            {
                objAmbiente.printVisaoAtualMapa(mapa, classeEscolhida.getLocalização());
                //Print da posição atual
                System.out.println("O que eu devo fazer agora?");
                System.out.println("1 - Andar\n2 - Descansar\n3 - Inventário \n4 - Status");
                System.out.println("0 - (para sair do jogo)");
                inputDoUsuario = scanString.nextLine();
                //System.out.println("_DEBUG_inputDoUsuario:" + inputDoUsuario); // DEBUG

                ////////////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////
                if(objEscolhas.escolhas(inputDoUsuario).equals("andar"))
                {
                    inputFoiAlgoEsperado = 0;
                    int n = 0;
                    int passos = 0;
                    String inputAqui;
                    
                    System.out.println("Quantos passos eu deveria andar?...");
                    while (true)
                    {
                        try
                        {
                            passos = Integer.parseInt(scanString.nextLine());
                            break;
                        }
                        catch (NumberFormatException e)
                        {
                            System.out.println("Preciso escolher um número para isso...");
                        }
                    }

                    String listaDeDireções = "\n 1 - Norte [^] \n 2 - Sul [v] \n 3 - Leste [>] \n 4 - Oeste [<]";
                    System.out.println("Para onde?..." + listaDeDireções);
                    
                    inputAqui = scanString.nextLine();
                    while(n < passos)
                    {
                        double[] posição = objMovimento.movimentador(inputAqui,
                        classeEscolhida.getLocalização());
                        
                        classeEscolhida.setLocalização(posição);
                        //System.out.println("X: " + posição[0] + " Y:" + posição[1]); //DEBUG
                        classeEscolhida.setEnergia(classeEscolhida.getEnergia() - 1);
                        //System.out.println("Energia: " + classeEscolhida.getEnergia()); //DEBUG
                        n++;
                    }
                }
                ////////////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////
                if(objEscolhas.escolhas(inputDoUsuario).equals("descansar"))
                {
                    inputFoiAlgoEsperado = 0;
                    System.out.println("1 - Descanso Curto \n2 - Descanso Longo");
                    int inputAqui;
                    while (true)
                    {
                        try
                        {
                            inputAqui = Integer.parseInt(scanString.nextLine());
                            break;
                        }
                        catch (NumberFormatException e)
                        {
                            System.out.println("Preciso escolher um número para isso...");
                        }
                    }
                    if (inputAqui == 1) // curto
                    {
                        classeEscolhida.setEnergia(classeEscolhida.getEnergia() + 50);
                        if (classeEscolhida.getEnergia() > energiaMáxima)
                        {
                            classeEscolhida.setEnergia(energiaMáxima);
                        }
                    }
                    else // longo
                    {
                        classeEscolhida.setEnergia(energiaMáxima);
                    }
                    System.out.println("Debug_energia: " + classeEscolhida.getEnergia());
                }
                ////////////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////
                else if(objEscolhas.escolhas(inputDoUsuario).equals("inventário"))
                {
                    inputFoiAlgoEsperado = 0;
                    //System.out.println(classeEscolhida.getInventário());
                    System.out.println("Inventário:");
                    System.out.println("________________________________________\n");

                    Map<Item, Integer> mapaContadorLista = new HashMap<>(); 
                    // Usar para contar os items da list Inventário

                    for (Item item : classeEscolhida.getInventário()) // Coloca itens no HasMap
                    {
                        mapaContadorLista.put(item, mapaContadorLista.getOrDefault(item, 0) + 1);
                    }

                    // Print cada item
                    int index = 1;
                    for (Map.Entry<Item, Integer> entry : mapaContadorLista.entrySet())
                    {
                        Item item = entry.getKey();
                        if (item instanceof Alimento)
                        {
                            System.out.println(index + "- " + (entry.getKey()).getNome() + " x" + entry.getValue() 
                            + " (Validade: " + ((Alimento) item).getValidade() + ")");
                        }
                        else
                        {
                            System.out.println(index + "- " + (entry.getKey()).getNome() + " x" + entry.getValue());
                        }
                        index++;
                    }
                    System.out.println("0 - Sair");
                    System.out.println("________________________________________");
                    
                    objInventario.adicionarItem(classeEscolhida, objAgua); 
                    objInventario.adicionarItem(classeEscolhida, objAlimento);
                    // TODO adiciona Item para testar, DEBUG

                    System.out.println("Eu devo usar algo?..."); // Escolha de Item para usar
                    int usarItemInventário = 0;
                    while (true)
                    {
                        try
                        {
                            usarItemInventário = Integer.parseInt(scanString.nextLine());
                            break;
                        }
                        catch (NumberFormatException e)
                        {
                            System.out.println("Preciso escolher um número para isso...");
                        }
                    }

                    List<Item> listaItens = new ArrayList<>(mapaContadorLista.keySet());
                    if (usarItemInventário > 0 && usarItemInventário <= mapaContadorLista.size()) 
                    {
                        Item itemEscolhidoParaUsar = listaItens.get(usarItemInventário - 1);
                        System.out.println("Irei usar... " + itemEscolhidoParaUsar.getNome());
                        objInventario.usarItem(classeEscolhida, itemEscolhidoParaUsar);
                        objInventario.removerItem(classeEscolhida, itemEscolhidoParaUsar);
                    }
                    else if (usarItemInventário == 0)
                    {

                    }
                    else 
                    {
                        System.out.println("Não sei o que escolher...");
                    }
                    //objInventario.removerItem(classeEscolhida, "Água"); // remove DEBUG
                    //objAgua.usar(classeEscolhida);
                    //System.out.println(classeEscolhida.getSede()); // DEBUG
                }
                ////////////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////
                else if(objEscolhas.escolhas(inputDoUsuario).equals("status"))
                {
                    double[] mostrarPosição = classeEscolhida.getLocalização();
                    System.out.println("________________________________________\n");
                    System.out.println("Classe: " + classeEscolhida.getNome());
                    System.out.println("Vida: " + classeEscolhida.getVida());
                    System.out.println("Fome: " + classeEscolhida.getFome());
                    System.out.println("Sede: " + classeEscolhida.getSede());
                    System.out.println("Energia: " + classeEscolhida.getEnergia());
                    System.out.println("Sanidade: " + classeEscolhida.getSanidade());
                    if (classeEscolhida.getNome().equals("Escoteiro"))
                    {
                        System.out.println("Posição (X,Y): " +  mostrarPosição[0] +","+ mostrarPosição[1]);
                    }
                    System.out.println("________________________________________");
                    System.out.println("Aperte qualquer tecla para (Voltar)");
                    String ComerInput = scanString.nextLine();
                }
                if (classeEscolhida.getVida() == 0)
                {
                    System.out.println("Você Morreu");
                    break;
                }
                else if(objEscolhas.escolhas(inputDoUsuario).equals("sair"))
                {
                    inputDoUsuario = "sair";
                    break;
                }
            }
        }
        scanString.close();// fecha o scanner
    }
}