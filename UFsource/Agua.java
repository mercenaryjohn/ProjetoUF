
public class Agua extends Item
{
    private int hidratação;
    public Agua()
    {
        setNome("Água");
        setPeso(1);
        setDurabilidade(1);
        this.hidratação = 50;
    }

    public void setHidratação(int hidratação) 
    { this.hidratação = hidratação; }
    public int getHidratação() 
    { return hidratação; }

    public void usar(EscolherClasse classeEscolhida)
    {
        classeEscolhida.setSede(classeEscolhida.getSede() + hidratação);
    }
}
