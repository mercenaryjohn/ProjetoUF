
public class Movimento
{
    public double[] movimentador (String inputDoUsuario, double localização[])
    {
        String inputLowered = inputDoUsuario.toLowerCase();

        if (inputLowered.equals("norte") || inputDoUsuario.equals("1"))
        {
            localização[1] = localização[1] + 1;
            //System.out.println(localização[1]); //DEBUG
            return localização;
        }
        if (inputLowered.equals("sul") || inputDoUsuario.equals("2"))
        {
            localização[1] = localização[1] - 1;
            //System.out.println(localização[1]); //DEBUG
            return localização;
        }
        if (inputLowered.equals("leste") || inputDoUsuario.equals("3"))
        {
            localização[0] = localização[0] + 1;
            //System.out.println(localização[0]); //DEBUG
            return localização;
        }
        if (inputLowered.equals("oeste") || inputDoUsuario.equals("4"))
        {
            localização[0] = localização[0] - 1;
            //System.out.println(localização[0]); //DEBUG
            return localização;
        }
        return localização;
    }
}
