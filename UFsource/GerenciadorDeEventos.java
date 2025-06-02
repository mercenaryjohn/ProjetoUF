
package UFsource;

import java.util.Random;

import UFsource.Player.*;
import UFsource.Ambientes.*;
import UFsource.Eventos.*;

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

    private Combate objCombate = new Combate();
    public Combate getObjCombate()
    { return objCombate;}

    private boolean eventoEstáOcorrendo = false;
    public boolean getEventoEstáOcorrendo()
    { return eventoEstáOcorrendo; }
    public void setEventoEstáOcorrendo(boolean bool)
    { this.eventoEstáOcorrendo = bool; }

    private int turnosDePazApósCombate = 0;
    private int numInicialDePaz = 8;
    public void iniciarTurnosDePazApósCombate()
    { this.turnosDePazApósCombate = numInicialDePaz; }
    public void passarTurnosDePazApósCombate()
    {
        if (turnosDePazApósCombate > 0)
        {
            turnosDePazApósCombate --;
        }
    }

    public void aplicarEventoAmbiente(Personagem player, Ambiente local, String eventoEscolhido)
    {
        String[] eventos = local.getClimasEventos();
        if (local.getNome().equals("Floresta"))
        { // "Lobo", "Urso", "Explorador perdido", "Chuva intensa"
            if (eventoEscolhido.equals(eventos[0]) && turnosDePazApósCombate == 0)
                { objEventoCriatura.executar(player, eventoEscolhido, objCombate); eventoEstáOcorrendo = true;}
            if (eventoEscolhido.equals(eventos[1]) && turnosDePazApósCombate == 0)
                { objEventoCriatura.executar(player, eventoEscolhido, objCombate); eventoEstáOcorrendo = true;}
            if (eventoEscolhido.equals(eventos[2]) && turnosDePazApósCombate == 0)
                { objEventoCriatura.executar(player, eventoEscolhido, objCombate); eventoEstáOcorrendo = true;}
            if (eventoEscolhido.equals(eventos[3]))
                { objEventoClimatico.executar(player, eventoEscolhido); eventoEstáOcorrendo = true;}
        }
        else if (local.getNome().equals("Montanha")) 
        { // "Nevasca Repentina", "Deslizamento", "Descoberta de Caverna"
            if (eventoEscolhido.equals(eventos[0]))
                { objEventoClimatico.executar(player, eventoEscolhido); eventoEstáOcorrendo = true;}
            if (eventoEscolhido.equals(eventos[1]))
                { objEventoClimatico.executar(player, eventoEscolhido); eventoEstáOcorrendo = true;}
            if (eventoEscolhido.equals(eventos[2]))
                {}
        }
        else if (local.getNome().equals("Caverna")) 
        { //"Criatura", "Tunel", "Desmoronamento"
            if (eventoEscolhido.equals(eventos[0]) && turnosDePazApósCombate == 0)
                { objEventoCriatura.executar(player, eventoEscolhido, objCombate); eventoEstáOcorrendo = true;}
            if (eventoEscolhido.equals(eventos[1]))
                {}
            if (eventoEscolhido.equals(eventos[2]))
                { objEventoClimatico.executar(player, eventoEscolhido); eventoEstáOcorrendo = true;}
        }
        else if (local.getNome().equals("Corpo d'água")) 
        { // "Jacaré", "Piranha", "Afogamento"
            if (eventoEscolhido.equals(eventos[0]) && turnosDePazApósCombate == 0)
                { objEventoCriatura.executar(player, eventoEscolhido, objCombate); eventoEstáOcorrendo = true;}
            if (eventoEscolhido.equals(eventos[1]) && turnosDePazApósCombate == 0)
                { objEventoCriatura.executar(player, eventoEscolhido, objCombate); eventoEstáOcorrendo = true;}
            if (eventoEscolhido.equals(eventos[2]))
                { objEventoDoencaFerimento.executar(player, eventoEscolhido); eventoEstáOcorrendo = true;}
        }
        else if (local.getNome().equals("Ruínas Abandonadas"))
        { // "Sobrevivente hostil", "Passagem secreta", "Armadilha"
            if (eventoEscolhido.equals(eventos[0]) && turnosDePazApósCombate == 0)
                { objEventoCriatura.executar(player, eventoEscolhido, objCombate); eventoEstáOcorrendo = true;}
            if (eventoEscolhido.equals(eventos[1]))
                {}
            if (eventoEscolhido.equals(eventos[2]))
                { objEventoDoencaFerimento.executar(player, eventoEscolhido); eventoEstáOcorrendo = true;}
        }
        else if (local.getNome().equals("Planície")) //Nada
        {

        }
    }

    public boolean removerEvento(String evento, Ambiente local) //TODO: talvez melhorar
    {
        if (local == null)
        {
            return false;
        }
        float[] probabilidades = local.getProbabilidadeEventos();
        float randomFloat; 
        for (int i = 0; i < probabilidades.length; i++)
        {
            randomFloat = objRandom.nextFloat();
            if (probabilidades[i] > randomFloat)
            {
                return false;
            }
        }
        return true;
    }
}
