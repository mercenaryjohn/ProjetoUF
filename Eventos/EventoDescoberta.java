
package Eventos;

import java.util.Random;

import Player.*;

public class EventoDescoberta extends Evento
{
    private double[] localHolder;
    private Random randomizador = new Random();
    private int randomIntPositivo;
    private int randomIntNegativo;

    public void executar(Personagem player, String eventoEscolhido)
    {   
        /*
            Não está em operação, as chances dos eventos em suas classes
            Ambientes foram modificadas para 0.00f
            Mais trabalho seria necessário para o evento ser menos confuso
        */
        if(eventoEscolhido.equals("Tunel") ||eventoEscolhido.equals("Passagem secreta"))
        {
            randomIntNegativo = randomizador.nextInt(-80, -8);
            randomIntPositivo = randomizador.nextInt(8, 80);
            localHolder = player.getLocalização();

            if (localHolder[0] < 0) //Se negativo
                { localHolder[0] = localHolder[0] + randomIntPositivo; }
            if (localHolder[0] > 0) //Se positivo
                { localHolder[0] = localHolder[0] + randomIntNegativo; }

            if (localHolder[1] < 0) //Se negativo
                { localHolder[1] = localHolder[1] + randomIntPositivo; }
            if (localHolder[1] > 0) //Se positivo
                { localHolder[1] = localHolder[1] + randomIntNegativo; }

            player.setLocalização(localHolder);
        }
        if(eventoEscolhido.equals("Descoberta de Caverna"))
        {
            
        }
    }
}
