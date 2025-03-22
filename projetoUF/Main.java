import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanString = new Scanner(System.in); // inicia o scanner

        String listaDeClasses = " 1 - Escoteiro \n 2 - Engenheiro \n 3 - Veterinário \n 4 - Prisioneiro \n";
        System.out.print("Escolha o seu sobrevivente:\n" + listaDeClasses);

        String escolhaDeClasseDoUsuario = scanString.nextLine();
        
        EscolherClasse classeEscolhida = new EscolherClasse(escolhaDeClasseDoUsuario);
        System.out.println("Você escolheu o " + classeEscolhida.nome + "!");

        String inputDoUsuario = "default"; // para não dar problema no while
        Escolhas objEscolhas = new Escolhas(); // relaciona um numero a uma escolha
        Movimento objMovimento = new Movimento(); // modifica a posição na array localização de coordenada x,y

        while(!inputDoUsuario.equals("sair"))
        {
            int inputFoiAlgoEsperado = 1;
            while (inputFoiAlgoEsperado == 1) // Se o usuário escolher algo não esperado, pergunta novamente
            {
                System.out.println("O que eu devo fazer agora?");
                System.out.println("1 - Andar\n2 - Descansar\n3 - Inventário \n0 - (para sair do jogo)");
                inputDoUsuario = scanString.nextLine();
                System.out.println("_DEBUG_inputDoUsuario:" + inputDoUsuario); // DEBUG

                if(objEscolhas.escolhas(inputDoUsuario).equals("andar"))
                {
                    inputFoiAlgoEsperado = 0;
                    int n = 0;
                    System.out.println("Quantos passos eu deveria andar?...");
                    int passos = Integer.parseInt(scanString.nextLine());

                    String listaDeDireções = "\n 1 - Norte \n 2 - Sul \n 3 - Leste \n 4 - Oeste \n";
                    System.out.println("Para onde?..." + listaDeDireções);
                    
                    inputDoUsuario = scanString.nextLine();
                    while(n < passos)
                    {
                        double[] posição = objMovimento.movimentador(inputDoUsuario,
                        classeEscolhida.localização);
                        
                        classeEscolhida.localização = posição;
                        System.out.println("X: " + posição[0] + " Y:" + posição[1]);
                        n++;
                    }
                }
                if(objEscolhas.escolhas(inputDoUsuario).equals("descansar"))
                {
                    inputFoiAlgoEsperado = 0;
                    classeEscolhida.energia = classeEscolhida.energia + 50; //TODO: número temporário
                    System.out.println("Debug_energia: " + classeEscolhida.energia);
                }
                if(objEscolhas.escolhas(inputDoUsuario).equals("inventário"))
                {
                    inputFoiAlgoEsperado = 0;
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