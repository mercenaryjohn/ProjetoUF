
public class Item 
{
    protected String nome;
    protected int peso;
    protected int durabilidade;

    public Item()
    {
        this.nome = "default";
        this.peso = 0;
        this.durabilidade = 0;
    }

    public String getNome() 
    { return nome; }
    public int getPeso() 
    { return peso; }
    public int getDurabilidade() 
    { return durabilidade; }

    public void setNome(String nome)
    { this.nome = nome; }
    public void setPeso(int peso)
    { this.peso = peso; }
    public void setDurabilidade(int durabilidade)
    { this.durabilidade = durabilidade; }


    public void Usar()
    {

    }
}
