import java.util.Random;

public class Combate 
{
    Random objRandom = new Random();
    private String nomeInimigo;
    private int vidaInimigo;
    private int danoInimigo;
    private boolean emCombate = false; //Iniciar com false, importante

    private int poderDeAtaqueInicial = 2;
     private int poderDeAtaqueAdicional = 0;
    private int vidaMáximaInimigo;
    String ultimaAcaoDoInimigo;

    public void atacar(EscolherClasse player)
    {
        int randomIntD20 = objRandom.nextInt(1,20);
        int poderDeAtaque = (poderDeAtaqueInicial + poderDeAtaqueAdicional) * randomIntD20;
        //System.out.println(poderDeAtaque);
        vidaInimigo = vidaInimigo - poderDeAtaque;
    }

    public void fugir()
    {
        int randomIntVidaInimigo = 1;
        if (vidaMáximaInimigo > 0) 
        { randomIntVidaInimigo = objRandom.nextInt(0,vidaMáximaInimigo); }

        int randomIntD20 = objRandom.nextInt(1,20);
        //Chance de fugir aumenta inversamente proporcional a vida do inimigo
        if (randomIntVidaInimigo + randomIntD20 > vidaInimigo) 
            { emCombate = false; }
        //System.out.println(randomIntVidaInimigo);
        //System.out.println(randomIntD20);
        //System.out.println(vidaInimigo);
        //System.out.println("############");
    }

    public void turnoDoInimigo(EscolherClasse player)
    {
        boolean randomBoolean5050 = objRandom.nextBoolean();
        if (randomBoolean5050 == true)
            { 
                ultimaAcaoDoInimigo = "atacou (" + danoInimigo + " de Dano)"; 
                player.setVida(player.getVida() - danoInimigo);
            }
        else{ ultimaAcaoDoInimigo = "(não fez nada)"; }
    }

    public String getNomeInimigo()
    { return nomeInimigo; }
    public int getVidaInimigo()
    { return vidaInimigo; }
    public int getDanoInimigo()
    { return danoInimigo; }
    public boolean getEmCombate()
    { return emCombate; }
    public String getUltimaAcaoDoInimigo()
    { return ultimaAcaoDoInimigo; }
    public int getVidaMáximaInimigo()
    { return vidaMáximaInimigo; }

    public void setNomeInimigo(String nomeInimigo)
    { this.nomeInimigo = nomeInimigo; }
    public void setVidaInimigo(int vidaInimigo)
    { this.vidaInimigo = vidaInimigo; }
    public void setDanoInimigo(int danoInimigo)
    { this.danoInimigo = danoInimigo; }
    public void setEmCombate(boolean emCombate)
    { this.emCombate = emCombate; }
    public void setVidaMáximaInimigo(int vidaMáximaInimigo)
    { this.vidaMáximaInimigo = vidaMáximaInimigo; }
}
