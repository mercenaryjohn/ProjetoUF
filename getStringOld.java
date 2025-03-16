import java.util.Scanner;

public class getStringOld
{
    public static String getinatorString()
    {
        Scanner scanString = new Scanner(System.in);
        String oQueUsuarioDigitou = null;
        System.out.println("Debug");

        oQueUsuarioDigitou = scanString.nextLine();
        while (oQueUsuarioDigitou.trim().isEmpty())
        {
            System.out.println("Input vazio. Digite algo:");
            oQueUsuarioDigitou = scanString.nextLine();
        }

        scanString.close();
        return oQueUsuarioDigitou;
    }
}