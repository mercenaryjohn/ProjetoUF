
package Itens;

import Player.EscolherClasse;

public class Ferramenta extends Item
{
    public void usar(EscolherClasse player)
    {
        if (getNome().equals("Abrigo"))
        {
            player.setVida((player.getVida() + 50));
            player.setSanidade((player.getSanidade() + 300));
            player.setEnergia(player.getEnergia() + 300);
        }
    }
}
