import java.util.Scanner;

public class getInt
{
    public static String getinatorString()
    {
        Scanner scanString = null;
        String oQueUsuarioDigitou;

        try
        {
            scanString = new Scanner(System.in);

            while (true) // garantir que o usuário digite uma integer
            {
                oQueUsuarioDigitou = scanString.nextLine();
                if (!oQueUsuarioDigitou.equals(""))
                {
                    break;
                }
                System.out.println("Input inválido. Digite algo válido:");
            }
        }
        finally
        {
            if (scanString != null)
            {
                scanString.close();
            }
        }
        return oQueUsuarioDigitou;
    }
}