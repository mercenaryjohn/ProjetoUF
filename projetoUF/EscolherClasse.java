
public class EscolherClasse extends Personagem
{
    public EscolherClasse(String classeEscolhida)
    {
        if (classeEscolhida.equals("cavaleiro"))
        {
        this.nome = "cavaleiro"; //nota: o this. não é necessário
        this.vida = 150;
        this.fome = 150;
        this.sede = 100;
        this.energia = 100;
        this.sanidade = 200;
        this.inventário = new double[20];
        this.localização = new double [2];

        }

        if (classeEscolhida.equals("prisioneiro"))
        {
        this.nome = "prisioneiro";
        this.vida = 90;
        this.fome = 190;
        this.sede = 120;
        this.energia = 65;
        this.sanidade = 100;
        this.inventário = new double[6];
        this.localização = new double [2];
        }
    }
}
