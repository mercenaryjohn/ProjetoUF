
package UFsource.Itens;

import UFsource.Player.*;

public class Remedio extends Item
{
    private int cura;
    public Remedio()
    {
        setNome("Remédio");
        setPeso(1);
        setDurabilidade(1);
        this.cura = 60;
    }

    public void usar(EscolherClasse classeEscolhida)
    {
        if (classeEscolhida.getNome().equals("veterinário"))
            { classeEscolhida.setVida(classeEscolhida.getVida() + cura + 40); }
        else 
            { classeEscolhida.setVida(classeEscolhida.getVida() + cura); }

    }
}
