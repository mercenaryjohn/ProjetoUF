
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

    //InventarioClasse objInventario = new InventarioClasse();
    public void usar(EscolherClasse classeEscolhida)
    {
        //System.out.println(this.nome); //DEBUG this.nome funciona para usar o nome
        classeEscolhida.setSede(classeEscolhida.getSede() + hidratação);
    }
}
