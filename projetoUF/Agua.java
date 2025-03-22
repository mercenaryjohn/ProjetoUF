
public class Agua extends Item
{
    public Agua()
    {
        this.nome = "Água";
        this.peso = 1;
        this.durabilidade = 1;
    }

    public String[] usar(String nomeItem, String inventario[], EscolherClasse classeEscolhida)
    {
        // TODO: ESCREVER METODO AQUI
        classeEscolhida.sede = classeEscolhida.sede + 50; //TODO: número temporário

        return inventario;
    }
}
