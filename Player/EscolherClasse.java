
package Player;

public class EscolherClasse extends Personagem
{
    public EscolherClasse(String classeEscolhida)
    {
        super(null, 1, 1, 1, 1, 1, 1);
        String classeEscolhidaLowered = classeEscolhida.toLowerCase();

        if (classeEscolhidaLowered.equals("escoteiro") || classeEscolhida.equals("1"))
        {
        setNome ("Escoteiro");
        setVida (180);
        setFome (150);
        setSede (100);
        setEnergia  (280);
        setSanidade (200);
        setLocalização ( new double [2]);
        setCapacidadeInventário(30);
        }

        if (classeEscolhidaLowered.equals("engenheiro") || classeEscolhida.equals("2"))
        {
        setNome ("Engenheiro");
        setVida (100);
        setFome (160);
        setSede (90);
        setEnergia  (260);
        setSanidade (250);
        setLocalização (new double [2]);
        setCapacidadeInventário(28);
        }

        if (classeEscolhidaLowered.equals("veterinário") || classeEscolhida.equals("3"))
        {
        setNome  ("Veterinário");
        setVida  (100);
        setFome  (170);
        setSede  (100);
        setEnergia  (240);
        setSanidade  (200);
        setLocalização  (new double [2]);
        setCapacidadeInventário(26);
        }

        if (classeEscolhidaLowered.equals("prisioneiro") || classeEscolhida.equals("4"))
        {
        setNome  ("Prisioneiro");
        setVida  (80);
        setFome  (90);
        setSede  (120);
        setEnergia  (130);
        setSanidade  (80);
        setLocalização  (new double [2]);
        setCapacidadeInventário(10);
        }
    }
}
