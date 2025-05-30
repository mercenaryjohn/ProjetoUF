public class Combate 
{
    private String nomeInimigo;
    private int vidaInimigo;
    private int danoInimigo;
    private boolean emCombate = false; //Iniciar com false, importante

    public void atacar(EscolherClasse player)
    {
        
    }

    public void defender()
    {
        
    }

    public void fugir()
    {

    }

    public String getNomeInimigo()
    { return nomeInimigo; }
    public int getVidaInimigo()
    { return vidaInimigo; }
    public int getDanoInimigo()
    { return danoInimigo; }
    public boolean getEmCombate()
    { return emCombate; }

    public void setNomeInimigo(String nomeInimigo)
    { this.nomeInimigo = nomeInimigo; }
    public void setVidaInimigo(int vidaInimigo)
    { this.vidaInimigo = vidaInimigo; }
    public void setDanoInimigo(int danoInimigo)
    { this.danoInimigo = danoInimigo; }
    public void setEmCombate(boolean emCombate)
    { this.emCombate = emCombate; }
}
