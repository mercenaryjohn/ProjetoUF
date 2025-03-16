import java.util.Scanner;

public class ImpriminatorLinhasOLD
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
    
    public static void main(String[] args) 
    {
        Scanner scanInt = null;

        try
        {
        scanInt = new Scanner(System.in);

        System.out.println("Digite um número: ");
        
        int numero = scanInt.nextInt();
        System.out.println(); // linha limpa
        imprimirNvezes(numero);
        } 
        finally
        {
            if (scanInt != null)
            {
                scanInt.close();
            }
        }
    }
}

