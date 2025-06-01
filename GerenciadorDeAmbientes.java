
import java.util.Random;
import Ambientes.*;

public class GerenciadorDeAmbientes 
{
    private AmbienteCaverna objCaverna = new AmbienteCaverna();
    private AmbienteFloresta objFloresta = new AmbienteFloresta();
    private AmbienteLagoRio objLagoRio = new AmbienteLagoRio();
    private AmbienteMontanha objMontanha = new AmbienteMontanha();
    private AmbienteRuinas objRuinas = new AmbienteRuinas();

    Random objRandom = new Random();

    public GerenciadorDeAmbientes ()
    {
        
    }

    public String pegarNomeAmbiente(char ambienteChar)
    {
        Ambiente[] arrayAmbientes = {objCaverna,objFloresta,objLagoRio,objMontanha,objRuinas};
        String ambienteAtualNome;
        if (ambienteChar == 'C')
            { ambienteAtualNome = arrayAmbientes[0].getNome(); }
        else if (ambienteChar == 'F') 
            { ambienteAtualNome = arrayAmbientes[1].getNome(); }
        else if (ambienteChar == '~') 
            { ambienteAtualNome = arrayAmbientes[2].getNome(); }
        else if (ambienteChar == 'M') 
            { ambienteAtualNome = arrayAmbientes[3].getNome(); }
        else if (ambienteChar == 'R') 
            { ambienteAtualNome = arrayAmbientes[4].getNome(); }
        else if (ambienteChar == '_') 
            { ambienteAtualNome = "Planície"; }
        else 
            { ambienteAtualNome = "???"; }

        return ambienteAtualNome;
    }

    public String pegarDescriçãoAmbiente(char ambienteChar)
    {
        Ambiente[] arrayAmbientes = {objCaverna,objFloresta,objLagoRio,objMontanha,objRuinas};
        String ambienteAtualDescrição;
        if (ambienteChar == 'C')
            { ambienteAtualDescrição = arrayAmbientes[0].getDescrição(); }
        else if (ambienteChar == 'F') 
            { ambienteAtualDescrição = arrayAmbientes[1].getDescrição(); }
        else if (ambienteChar == '~') 
            { ambienteAtualDescrição = arrayAmbientes[2].getDescrição(); }
        else if (ambienteChar == 'M') 
            { ambienteAtualDescrição = arrayAmbientes[3].getDescrição(); }
        else if (ambienteChar == 'R') 
            { ambienteAtualDescrição = arrayAmbientes[4].getDescrição(); }
        else if (ambienteChar == '_') 
            { ambienteAtualDescrição = "Terra coberta por uma espessa camada de grama. Nada realmente interessante... a menos que você seja uma vaca, é claro"; }
        else 
            { ambienteAtualDescrição = "???"; }

        return ambienteAtualDescrição;
    }

    public Ambiente pegarObjAmbiente (String localNome)
    {
        Ambiente[] arrayAmbientes = {objCaverna,objFloresta,objLagoRio,objMontanha,objRuinas};
        Ambiente ambienteAtual = null;
        if (localNome.equals(arrayAmbientes[0].getNome()))
        { ambienteAtual = arrayAmbientes[0]; }
        else if (localNome.equals(arrayAmbientes[1].getNome())) 
        { ambienteAtual = arrayAmbientes[1]; }
        else if (localNome.equals(arrayAmbientes[2].getNome())) 
        { ambienteAtual = arrayAmbientes[2]; }
        else if (localNome.equals(arrayAmbientes[3].getNome())) 
        { ambienteAtual = arrayAmbientes[3]; }
        else if (localNome.equals(arrayAmbientes[4].getNome()))
        { ambienteAtual = arrayAmbientes[4]; }
        return ambienteAtual;
    }

    private Agua objAgua = new Agua();
    private Alimento objAlimento = new Alimento(0, 0);
    private Remedio objRemédio = new Remedio();
    private Arma objArma = new Arma();
    private Item objItemGenérico = new Item(); //Para itens não específicos

    public Item vasculharAmbiente (Ambiente localAtual)
    {
        int randomInt; 
        String recursoEscolhidoNome = "";
        Item recursoEscolhidoItem = null;
        String localNome = localAtual.getNome();

        if (localNome.equals("Planície")) 
        { return recursoEscolhidoItem; }

        String[] recursos = localAtual.getRecursosDisponíveis();

        randomInt= objRandom.nextInt(recursos.length);
        for (int i = 0; i < recursos.length; i++) // Escolhe um recurso do terreno aleatóriamente
        {
            if (i == randomInt)
            {
                recursoEscolhidoNome = recursos[i];
            }
        }

        if (recursoEscolhidoNome.equals(objAgua.getNome())) //Todos os tipos possíveis de Água
        {
            recursoEscolhidoItem = objAgua;
            return recursoEscolhidoItem;
        }
        if (recursoEscolhidoNome.equals("Água contaminada"))
        {
            recursoEscolhidoItem = objAgua;
            ((Agua) recursoEscolhidoItem).setHidratação(-50);
            recursoEscolhidoItem.setNome("Água contaminada");
            return recursoEscolhidoItem;
        }
        if (recursoEscolhidoNome.equals("Água de degelo"))
        {
            recursoEscolhidoItem = objAgua;
            ((Agua) recursoEscolhidoItem).setHidratação(objAgua.getHidratação());
            recursoEscolhidoItem.setNome("Água de degelo");
            return recursoEscolhidoItem;
        }
        if (recursoEscolhidoNome.equals("Gelo"))
        {
            recursoEscolhidoItem = objAgua;
            ((Agua) recursoEscolhidoItem).setHidratação(objAgua.getHidratação());
            recursoEscolhidoItem.setNome("Gelo");
            return recursoEscolhidoItem;
        }

        String[] nomesAlimentos = objAlimento.getNomesAlimentos();
        for (int i = 0; i < nomesAlimentos.length; i++)
        {
            if (recursoEscolhidoNome.equals(nomesAlimentos[i]))
            {
                recursoEscolhidoItem = objAlimento;
                ((Alimento) recursoEscolhidoItem).setAlimentoStats(recursoEscolhidoNome);
                return recursoEscolhidoItem;
            }
        }

        if (recursoEscolhidoNome.equals("Remédio"))
        {
            recursoEscolhidoItem = objRemédio;
            return recursoEscolhidoItem;
        }

        if (recursoEscolhidoNome.equals("Arma antiga"))
        {
            recursoEscolhidoItem = objArma;
            recursoEscolhidoItem.setNome("Arma antiga");
            recursoEscolhidoItem.setDurabilidade(12);
            return recursoEscolhidoItem;
        }

        boolean chance5050 = objRandom.nextBoolean();
        if (localNome.equals("Corpo d'água") && chance5050 == false) //Maior chance de pegar água
        { recursoEscolhidoItem = objAgua; }
        else
        { 
            objItemGenérico.setNome(recursoEscolhidoNome);
            recursoEscolhidoItem = objItemGenérico; 
        } //Objeto sem classe específica

        return recursoEscolhidoItem;
    }
}
