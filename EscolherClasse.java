
public class EscolherClasse extends Personagem
{
    public EscolherClasse(String classeEscolhida)
    {
        String classeEscolhidaLowered = classeEscolhida.toLowerCase();

        if (classeEscolhidaLowered.equals("Escoteiro") || classeEscolhida.equals("1"))
        {
        this.nome = "Escoteiro"; //nota: o this. não é necessário
        this.vida = 180;
        this.fome = 150;
        this.sede = 100;
        this.energia = 100;
        this.sanidade = 200;
        this.inventário = new double[20];
        this.localização = new double [2];
        }

        if (classeEscolhidaLowered.equals("Engenheiro") || classeEscolhida.equals("2"))
        {
        this.nome = "Engenheiro";
        this.vida = 100;
        this.fome = 160;
        this.sede = 100;
        this.energia = 90;
        this.sanidade = 250;
        this.inventário = new double[20];
        this.localização = new double [2];
        }

        if (classeEscolhidaLowered.equals("Veterinário") || classeEscolhida.equals("3"))
        {
        this.nome = "Veterinário";
        this.vida = 100;
        this.fome = 170;
        this.sede = 100;
        this.energia = 90;
        this.sanidade = 200;
        this.inventário = new double[20];
        this.localização = new double [2];
        }

        if (classeEscolhidaLowered.equals("Prisioneiro") || classeEscolhida.equals("4"))
        {
        this.nome = "Prisioneiro";
        this.vida = 80;
        this.fome = 90;
        this.sede = 120;
        this.energia = 65;
        this.sanidade = 80;
        this.inventário = new double[6];
        this.localização = new double [2];
        }

    }
}
