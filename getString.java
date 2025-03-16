import java.util.Scanner;

public class getString
{
    public String getinatorString()
    {
        Scanner scanString = new Scanner(System.in);// inicia o scanner
        
        String oQueUsuarioDigitou = null;

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
