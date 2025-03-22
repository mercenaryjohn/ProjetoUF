
public class Personagem
{
    protected String nome;
    protected int vida;
    protected int fome;
    protected int sede;
    protected int energia;
    protected int sanidade;
    protected double inventário[];
    protected double localização[];

    public Personagem()
    {
        this.nome = "default";
        this.vida = 0;
        this.fome = 0;
        this.sede = 0;
        this.energia = 0;
        this.sanidade = 0;
        this.inventário = new double[2];
        this.localização = new double [2];
    }
}