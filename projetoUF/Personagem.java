import java.util.ArrayList;
import java.util.List;

public class Personagem
{
    // inicia as variáveis
    private String nome;
    private int vida;
    private int fome;
    private int sede;
    private int energia;
    private int sanidade;
    private List<Item> inventário;
    private double[] localização;
    private int capacidadeInventário; // último a ser adicionado

    // Construtor
    public Personagem(String nome, int vida, int fome, int sede, int energia, int sanidade, int capacidadeInventário)
    {
        this.nome = nome;
        this.vida = vida;
        this.fome = fome;
        this.sede = sede;
        this.energia = energia;
        this.sanidade = sanidade;
        this.inventário = new ArrayList<>();
        this.localização = new double [2];
        this.capacidadeInventário = capacidadeInventário;
    }

    // Getters, comandos para pegar os dados de personagem
    public String getNome() 
    { return nome; }
    public int getVida() 
    { return vida; }
    public int getFome() 
    { return fome; }
    public int getSede() 
    { return sede; }
    public int getEnergia() 
    { return energia; }
    public int getSanidade() 
    { return sanidade; }
    public List<Item> getInventário()
    { return inventário; }
    public int getCapacidadeInventário()
    { return capacidadeInventário; }
    public double[] getLocalização()
    { return localização; }

    // Setters, comandos para modificar os dados de personagem
    public void setNome(String nome)
    { this.nome = nome; }
    public void setVida(int vida) 
    { this.vida = vida; }
    public void setFome(int fome) 
    { this.fome = fome; }
    public void setSede(int sede) 
    { this.sede = sede; }
    public void setEnergia(int energia) 
    { this.energia = energia; }
    public void setSanidade(int sanidade) 
    { this.sanidade = sanidade; }
    public void setInventário(List<Item> inventário)
    { this.inventário = inventário; }
    public void setLocalização(double[] localização)
    { this.localização = localização; }
    public void setCapacidadeInventário(int capacidadeInventário)
    { this.capacidadeInventário = capacidadeInventário; }
}