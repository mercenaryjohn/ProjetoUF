import java.util.Random;

public class Combate 
{
    Random objRandom = new Random();
    private String nomeInimigo;
    private int vidaInimigo;
    private int danoInimigo;
    private int danoSanidade;
    private boolean emCombate = false; //Iniciar com false, importante
    private int numeroDeInimigosDerrotados = 0;
    private int condiçãoDeVitóriaInimgosDerrotados = 60;

    private int poderDeAtaqueInicial = 2;
     private int poderDeAtaqueAdicionalArma = 0;
    private int vidaMáximaInimigo;
    String ultimaAcaoDoInimigo;

    public void atacar(EscolherClasse player)
    {
        int randomIntD20 = objRandom.nextInt(1,20);
        int poderDeAtaque = (poderDeAtaqueInicial * randomIntD20) + poderDeAtaqueAdicionalArma;
        poderDeAtaqueAdicionalArma = 0; //Se arma foi usada, volta a zero

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
    }

    public void turnoDoInimigo(EscolherClasse player)
    {
        boolean randomBoolean5050 = objRandom.nextBoolean();
        if (randomBoolean5050 == true)
            { 
                ultimaAcaoDoInimigo = "Atacou! (" + danoInimigo + " Vida " + danoSanidade + " Sanidade)";
                player.setVida(player.getVida() - danoInimigo);
                player.setSanidade(player.getSanidade() - danoSanidade);
            }
        else{ ultimaAcaoDoInimigo = "(não fez nada)"; }
    }

    public void inimigoFoiDerrotado()
    { numeroDeInimigosDerrotados++; }

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
    public int getNumeroDeInimigosDerrotados()
    { return numeroDeInimigosDerrotados;}
    public int getCondiçãoDeVitóriaInimgosDerrotados()
    { return condiçãoDeVitóriaInimgosDerrotados;}

    public void setNomeInimigo(String nomeInimigo)
    { this.nomeInimigo = nomeInimigo; }
    public void setVidaInimigo(int vidaInimigo)
    { this.vidaInimigo = vidaInimigo; }
    public void setDanoInimigo(int danoInimigo)
    { this.danoInimigo = danoInimigo; }
    public void setDanoSanidade(int danoSanidade)
    { this.danoSanidade = danoSanidade; }
    public void setEmCombate(boolean emCombate)
    { this.emCombate = emCombate; }
    public void setPoderDeAtaqueAdicionalArma(int poderDeAtaqueAdicionalArma)
    { this.poderDeAtaqueAdicionalArma = poderDeAtaqueAdicionalArma; }
    public void setUltimaAcaoDoInimigo(String ultimaAcaoDoInimigo)
    { this.ultimaAcaoDoInimigo = ultimaAcaoDoInimigo; }
    public void setVidaMáximaInimigo(int vidaMáximaInimigo)
    { this.vidaMáximaInimigo = vidaMáximaInimigo; }
}
