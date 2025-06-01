
public class Arma extends Item
{
    private int poderDeDano;
    public Arma()
    {
        setNome("Arma");
        setPeso(1);
        setDurabilidade(10);
        this.poderDeDano = 20;
    }

    public void setPoderDeDano(int danoPower)
    { this.poderDeDano = danoPower; }
    public int getPoderDeDano()
    { return poderDeDano; }

    public void usar()
    {
        setDurabilidade(getDurabilidade() - 1);
    }
}
