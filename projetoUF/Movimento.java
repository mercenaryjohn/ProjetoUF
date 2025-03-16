
public class Movimento
{
    public double[] movimentador (EscolherClasse classeEscolhida, String inputDoUsuario, double localização[])
    {
        if (inputDoUsuario.equals("norte"))
        {
            localização[1] = localização[1] + 1;
            System.out.println(localização[1]);
            return localização;
        }
        if (inputDoUsuario.equals("sul"))
        {
            localização[1] = localização[1] - 1;
            System.out.println(localização[1]);
            return localização;
        }
        if (inputDoUsuario.equals("leste"))
        {
            localização[0] = localização[0] + 1;
            System.out.println(localização[0]);
            return localização;
        }
        if (inputDoUsuario.equals("oeste"))
        {
            localização[0] = localização[0] - 1;
            System.out.println(localização[0]);
            return localização;
        }
        return localização;
    }
}
