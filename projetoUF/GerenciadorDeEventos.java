
import java.util.Random;

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

    public void aplicarEventoAmbiente(Personagem jogador, Ambiente local, String eventoEscolhido)
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
            {}
        }
        else if (local.getNome().equals("Montanha")) 
        {

        }
        else if (local.getNome().equals("Caverna")) 
        {

        }
        else if (local.getNome().equals("Corpo d'água")) 
        {

        }
        else if (local.getNome().equals("Ruínas Abandonadas"))
        {

        }
        else if (local.getNome().equals("Planície")) 
        {

        }
    }

    public void eventoVasculhar(Personagem jogador)
    {
        
    }

    public void removerEvento(Evento evento)
    {
        
    }
}
