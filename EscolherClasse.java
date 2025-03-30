import java.util.ArrayList;

public class EscolherClasse extends Personagem
{
    public EscolherClasse(String classeEscolhida)
    {
        super(null, 0, 0, 0, 0, 0);
        String classeEscolhidaLowered = classeEscolhida.toLowerCase();

        if (classeEscolhidaLowered.equals("Escoteiro") || classeEscolhida.equals("1"))
        {
        setNome ("Escoteiro");
        setVida (180);
        setFome (150);
        setSede (100);
        setSede (100);
        setSanidade (200);
        setInventário (new ArrayList<>(20));
        setLocalização ( new double [2]);
        }

        if (classeEscolhidaLowered.equals("Engenheiro") || classeEscolhida.equals("2"))
        {
        setNome ("Engenheiro");
        setVida (100);
        setFome (160);
        setSede (100);
        setSede (90);
        setSanidade (250);
        setInventário (new ArrayList<>(20));
        setLocalização (new double [2]);
        }

        if (classeEscolhidaLowered.equals("Veterinário") || classeEscolhida.equals("3"))
        {
        setNome  ("Veterinário");
        setVida  (100);
        setFome  (170);
        setSede  (100);
        setEnergia  (90);
        setSanidade  (200);
        setInventário  (new ArrayList<>(20));
        setLocalização  (new double [2]);
        }

        if (classeEscolhidaLowered.equals("Prisioneiro") || classeEscolhida.equals("4"))
        {
        setNome  ("Prisioneiro");
        setVida  (80);
        setFome  (90);
        setSede  (120);
        setEnergia  (65);
        setSanidade  (80);
        setInventário  (new ArrayList<>(8));
        setLocalização  (new double [2]);
        }
    }
}
