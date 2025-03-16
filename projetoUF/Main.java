import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanString = new Scanner(System.in); // inicia o scanner

        System.out.print("Escolha a sua classe\n");
        String escolhaDeClasseDoUsuario = scanString.nextLine();
        
        EscolherClasse classeEscolhida = new EscolherClasse(escolhaDeClasseDoUsuario);
        System.out.println("Você escolheu o " + classeEscolhida.nome + "!");

        String inputDoUsuario = "default"; // para não dar problema no while
        Escolhas objEscolhas = new Escolhas(); // relaciona um numero a uma escolha
        Movimento objMovimento = new Movimento(); // modifica a posição na array localização de coordenada x,y
        while(!inputDoUsuario.equals("sair"))
        {
            System.out.println("O que eu devo fazer agora?");
            System.out.println("1 - Andar\n0 - (para sair do jogo)");
            inputDoUsuario = scanString.nextLine();
            System.out.println("inputDoUsuario:" + inputDoUsuario);

            if(objEscolhas.escolhas(inputDoUsuario).equals("andar"))
            {
                int n = 0;
                System.out.println("Quantos passos eu deveria andar?...");
                int passos = Integer.parseInt(scanString.nextLine());
                System.out.println("Para onde?...");
                inputDoUsuario = scanString.nextLine();
                while(n < passos)
                {
                    double[] posição = objMovimento.movimentador(classeEscolhida, inputDoUsuario,
                     classeEscolhida.localização);
                    classeEscolhida.localização = posição;
                    System.out.println("X: " + posição[0] + " Y:" +
                    posição[1]);
                    n++;
                }
            }
            if(objEscolhas.escolhas(inputDoUsuario).equals("sair"))
            {
                inputDoUsuario = "sair";
                break;
            }
        }
        
        scanString.close();// fecha o scanner
    }
}