import java.util.Scanner;

public class ImprimirLinhasNumero
{
    public static void imprimirNvezes(int n)
    {
        for (int i = 1; i <= n; i++) 
        {
            for (int j = 1; j <= i; j++) 
            {
                System.out.print(i);
            }
            System.out.println();
        }
    }

    public static int GetInt()
    {
        Scanner scanInt = null;
        int numero = 0;

        try
        {
        scanInt = new Scanner(System.in);

        while (!scanInt.hasNextInt()) // garantir que o usuário digite uma integer
        {
            System.out.println("Input inválido. Digite um número inteiro:");
            scanInt.next(); // Pede pelo próximo input
        }

        numero = scanInt.nextInt();
        }
        finally
        {
            if (scanInt != null)
            {
                scanInt.close();
            }
        }
        return numero;
    }

    public static void main(String[] args) 
    {
        System.out.println("Digite um número: ");
        int numero = ImprimirLinhasNumero.GetInt();
        System.out.println(); // linha limpa
        imprimirNvezes(numero);
    }
}
