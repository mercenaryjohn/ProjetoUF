
public class Remedio extends Item
{
    private int cura;
    public Remedio()
    {
        setNome("Remédio");
        setPeso(1);
        setDurabilidade(1);
        this.cura = 60;
    }

    public void usar(EscolherClasse classeEscolhida)
    {
        classeEscolhida.setVida(classeEscolhida.getVida() + cura);
    }
}
