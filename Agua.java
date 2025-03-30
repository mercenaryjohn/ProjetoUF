
public class Agua extends Item
{
    public Agua()
    {
        setNome("Água");
        setPeso(1);
        setDurabilidade(1);
    }

    InventarioClasse objInventario = new InventarioClasse();
    public void usarAgua(EscolherClasse classeEscolhida)
    {
        objInventario.usarItem(classeEscolhida, this.nome);
        //System.out.println(this.nome); //DEBUG this.nome funciona para usar o nome
        objInventario.removerItem(classeEscolhida, this.nome);
    }
}
