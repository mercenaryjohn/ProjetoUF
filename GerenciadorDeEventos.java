
import java.util.Random;

import Ambientes.*;

public class GerenciadorDeEventos 
{
    Random objRandom = new Random();
    EventoClimatico objEventoClimatico = new EventoClimatico();
    EventoCriatura objEventoCriatura = new EventoCriatura();
    EventoDescoberta objEventoDescoberta = new EventoDescoberta();
    EventoDoencaFerimento objEventoDoencaFerimento = new EventoDoencaFerimento();

    public String sortearEvento(Ambiente local)
    {
        float[] probabilidades = local.getProbabilidadeEventos();
        String[] eventos = local.getClimasEventos();

        float randomFloat; 
        String eventoEscolhido = "";
        for (int i = 0; i < probabilidades.length; i++)
        {
            randomFloat= objRandom.nextFloat();
            if (probabilidades[i] > randomFloat)
            {
                eventoEscolhido = eventos[i];
                break;
            }
        }
        return eventoEscolhido;
    }

    public void aplicarEventoAmbiente(Personagem player, Ambiente local, String eventoEscolhido)
    {
        String[] eventos = local.getClimasEventos();

        if (local.getNome().equals("Floresta"))
        { // "Lobo", "Urso", "Explorador perdido", "Chuva intensa"
            if (eventoEscolhido.equals(eventos[0]));
            {}
            if (eventoEscolhido.equals(eventos[1]));
            {}
            if (eventoEscolhido.equals(eventos[2]));
            {}
            if (eventoEscolhido.equals(eventos[3]));
            { objEventoClimatico.executar(player, eventoEscolhido, eventos); }
        }
        else if (local.getNome().equals("Montanha")) 
        { // "Nevasca Repentina", "Deslizamento", "Descoberta de Caverna"
            if (eventoEscolhido.equals(eventos[0]));
            { objEventoClimatico.executar(player, eventoEscolhido, eventos); }
            if (eventoEscolhido.equals(eventos[1]));
            { objEventoClimatico.executar(player, eventoEscolhido, eventos); }
            if (eventoEscolhido.equals(eventos[2]));
            {}
        }
        else if (local.getNome().equals("Caverna")) 
        { //"Criatura", "Tunel", "Desmoronamento"
            if (eventoEscolhido.equals(eventos[0]));
            {}
            if (eventoEscolhido.equals(eventos[1]));
            {}
            if (eventoEscolhido.equals(eventos[2]));
            { objEventoClimatico.executar(player, eventoEscolhido, eventos); }
        }
        else if (local.getNome().equals("Corpo d'água")) 
        { // "Jacaré", "Piranha", "Afogamento"
            if (eventoEscolhido.equals(eventos[0]));
            {}
            if (eventoEscolhido.equals(eventos[1]));
            {}
            if (eventoEscolhido.equals(eventos[2]));
            { objEventoDoencaFerimento.executar(player, eventoEscolhido, eventos); }
        }
        else if (local.getNome().equals("Ruínas Abandonadas"))
        { // "Sobrevivente hostil", "Passagem secreta", "Armadilha"
            if (eventoEscolhido.equals(eventos[0]));
            {}
            if (eventoEscolhido.equals(eventos[1]));
            {}
            if (eventoEscolhido.equals(eventos[2]));
            { objEventoDoencaFerimento.executar(player, eventoEscolhido, eventos); }
        }
        else if (local.getNome().equals("Planície")) 
        {

        }
    }

    public void eventoVasculhar(Personagem player)
    {
        
    }

    public boolean removerEvento(String evento, Ambiente local) //TODO: temp
    {
        if (local == null)
        {
            return false;
        }
        float[] probabilidades = local.getProbabilidadeEventos();
        float randomFloat; 
        for (int i = 0; i < probabilidades.length; i++)
        {
            randomFloat= objRandom.nextFloat();
            if (probabilidades[i] > randomFloat)
            {
                return false;
            }
        }
        return true;
    }
}
