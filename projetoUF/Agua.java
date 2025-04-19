
public class Agua extends Item
{
    public Agua()
    {
        setNome("Água");
        setPeso(1);
        setDurabilidade(1);
    }

    //InventarioClasse objInventario = new InventarioClasse();
    public void usar(EscolherClasse classeEscolhida)
    {
        //System.out.println(this.nome); //DEBUG this.nome funciona para usar o nome
        classeEscolhida.setSede(classeEscolhida.getSede() + 50); //TODO: número temporário
    }
}
