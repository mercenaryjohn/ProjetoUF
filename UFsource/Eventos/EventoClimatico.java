
package UFsource.Eventos;

import UFsource.Player.*;

public class EventoClimatico extends Evento
{
    public void executar(Personagem player, String eventoEscolhido)
    {
        if(eventoEscolhido.equals("Chuva intensa"))
        {
            player.setFome(player.getFome() - 1);
            player.setEnergia(player.getEnergia() - 1);
            player.setSanidade(player.getSanidade() - 1);
        }
        else if(eventoEscolhido.equals("Nevasca Repentina"))
        {
            player.setFome(player.getFome() - 1);
            player.setEnergia(player.getEnergia() - 1);
            player.setSanidade(player.getSanidade() - 1);
        }
        else if(eventoEscolhido.equals("Deslizamento"))
        {
            player.setVida(player.getVida() - 2);
            player.setEnergia(player.getEnergia() - 1);
            player.setSanidade(player.getSanidade() - 1);
        }
    }
}
