
public class Item 
{
    private String nome;
    private int peso;
    private int durabilidade;

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


    public void usar(EscolherClasse classeEscolhida)
    {

    }

    public Item clonarObjItem(Item item) // clonar Obj para adicionar uma nova instância no inventário
    {
        if (item instanceof Alimento) 
        {
            Alimento obj = new Alimento(1,1);
            obj.setAlimentoStats(item.getNome());
            return obj;
        } 
        else if (item instanceof Agua) 
        {
            Agua obj = new Agua();
            obj.setNome(item.getNome());
            obj.setHidratação(((Agua)item).getHidratação());
            return obj;
        }
        else if (item instanceof Arma) 
        {
            //Arma obj = (Arma) item;
            Arma obj = new Arma();
            obj.setNome(item.getNome());
            obj.setPoderDeDano(((Arma)item).getPoderDeDano());
            return obj;
        }
        else
        {
            Item obj = new Item();
            obj.setNome(item.getNome());
            return obj;
        }
    }
}
