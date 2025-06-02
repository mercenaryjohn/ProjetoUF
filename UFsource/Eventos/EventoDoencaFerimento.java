
package UFsource.Eventos;

import UFsource.Player.*;

public class EventoDoencaFerimento extends Evento
{
    public void executar(Personagem player, String eventoEscolhido, String[] eventos)
    {
        if(eventoEscolhido.equals("Afogamento"))
        {
            player.setFome(player.getFome() - 1);
            player.setEnergia(player.getEnergia() - 2);
            player.setSanidade(player.getSanidade() - 3);
        }
        else if(eventoEscolhido.equals("Armadilha"))
        {
            player.setVida(player.getVida() - 1);
            player.setEnergia(player.getEnergia() - 2);
            player.setSanidade(player.getSanidade() - 3);
        }
    }
}
