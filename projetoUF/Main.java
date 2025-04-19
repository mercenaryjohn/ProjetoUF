import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanString = new Scanner(System.in); // inicia o scanner

        String[] listaDeClasses = {"1","Escoteiro","2","Engenheiro","3","Veterinário","4","Prisioneiro"};
        //String listaDeClasses = " 1 - Escoteiro \n 2 - Engenheiro \n 3 - Veterinário \n 4 - Prisioneiro \n";
        
        System.out.print("Escolha o seu sobrevivente:\n");
        for(int i = 0; (i + 1) < listaDeClasses.length; i = i + 2)
        {
            System.out.print(listaDeClasses[i] + " - " + listaDeClasses[i+1] + "\n");
        }

        String escolhaDeClasseDoUsuario;
        while(true) // Garantir que a escolha seja válida
        {
            escolhaDeClasseDoUsuario = scanString.nextLine();
            int podeSair = 0;
            for(int i = 0; i < listaDeClasses.length; i++)
            {
                if (escolhaDeClasseDoUsuario.equals(listaDeClasses[i].toLowerCase()))
                {
                    podeSair = 1;
                    break;
                }
            }
            if (podeSair == 1)
            {
                break;
            }
            else
            {
                System.out.print("Escolha o seu sobrevivente:\n");
                for(int i = 0; (i + 1) < listaDeClasses.length; i = i + 2)
                {
                    System.out.print(listaDeClasses[i] + " - " + listaDeClasses[i+1] + "\n");
                }
            }
        }

        EscolherClasse classeEscolhida = new EscolherClasse(escolhaDeClasseDoUsuario);
        System.out.println("Você escolheu o " + classeEscolhida.getNome() + "!");

        String inputDoUsuario = "default"; // para não dar problema no while
        Escolhas objEscolhas = new Escolhas(); // relaciona um numero a uma escolha
        Movimento objMovimento = new Movimento(); // modifica a posição na array localização de coordenada x,y
        InventarioClasse objInventario = new InventarioClasse();
        Agua objAgua = new Agua(); // DEBUG TODO
        
        ///////////////////////////////////////////////////////////////////////// MAPA
        Ambiente objAmbiente = new Ambiente("nome", "descrição",
         "recursos", null, null);
        char[][] mapa = objAmbiente.fazerMapaMundo();
        //objAmbiente.printVisaoAtualMapa(mapa, classeEscolhida.getLocalização()); 
        /////////////////////////////////////////////////////////////////////////
        
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

                    String listaDeDireções = "\n 1 - Norte \n 2 - Sul \n 3 - Leste \n 4 - Oeste";
                    System.out.println("Para onde?..." + listaDeDireções);
                    
                    inputAqui = scanString.nextLine();
                    while(n < passos)
                    {
                        double[] posição = objMovimento.movimentador(inputAqui,
                        classeEscolhida.getLocalização());
                        
                        classeEscolhida.setLocalização(posição);
                        //System.out.println("X: " + posição[0] + " Y:" + posição[1]); //DEBUG
                        n++;
                    }
                }
                ////////////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////
                if(objEscolhas.escolhas(inputDoUsuario).equals("descansar"))
                {
                    inputFoiAlgoEsperado = 0;
                    classeEscolhida.setEnergia(classeEscolhida.getEnergia() + 100);  //TODO: número temporário
                    System.out.println("Debug_energia: " + classeEscolhida.getEnergia());
                }
                ////////////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////
                if(objEscolhas.escolhas(inputDoUsuario).equals("inventário"))
                {
                    inputFoiAlgoEsperado = 0;
                    //System.out.println(classeEscolhida.getInventário());
                    System.out.println("Inventário:");
                    System.out.println("________________________________________\n");

                    Map<String, Integer> mapaContadorLista = new HashMap<>(); 
                    // Usar para contar os items da list Inventário

                    for (String item : classeEscolhida.getInventário())
                    {
                        mapaContadorLista.put(item, mapaContadorLista.getOrDefault(item, 0) + 1);
                    }

                    //
                    int index = 1;
                    for (Map.Entry<String, Integer> entry : mapaContadorLista.entrySet())
                    {
                        System.out.println(index + "- " + entry.getKey() + " x" + entry.getValue());
                        index++;
                    }
                    System.out.println("0 - Sair");
                    System.out.println("________________________________________");
                    
                    objInventario.adicionarItem(classeEscolhida, "Água"); 
                    // TODO adicionei item pela 1ª vez, DEBUG

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

                    List<String> listaItens = new ArrayList<>(mapaContadorLista.keySet());
                    if (usarItemInventário > 0 && usarItemInventário <= mapaContadorLista.size()) 
                    {
                        String itemEscolhidoParaUsar = listaItens.get(usarItemInventário - 1);
                        System.out.println("Irei usar... " + itemEscolhidoParaUsar);
                        objInventario.usarItem(classeEscolhida, itemEscolhidoParaUsar);
                        objInventario.removerItem(classeEscolhida, itemEscolhidoParaUsar);
                    }
                    if (usarItemInventário == 0)
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
                if(objEscolhas.escolhas(inputDoUsuario).equals("status"))
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
                }
                if(objEscolhas.escolhas(inputDoUsuario).equals("sair"))
                {
                    inputDoUsuario = "sair";
                    break;
                }
            }
        }
        scanString.close();// fecha o scanner
    }
}