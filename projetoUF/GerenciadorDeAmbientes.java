
import java.util.Random;

public class GerenciadorDeAmbientes 
{
    private AmbienteCaverna objCaverna;
    private AmbienteFloresta objFloresta;
    private AmbienteLagoRio objLagoRio;
    private AmbienteMontanha objMontanha;
    private AmbienteRuinas objRuinas;

    private Agua objAgua = new Agua();

    Random objRandom = new Random();

    public GerenciadorDeAmbientes (AmbienteCaverna objCaverna, AmbienteFloresta objFloresta, 
    AmbienteLagoRio objLagoRio, AmbienteMontanha objMontanha, AmbienteRuinas objRuinas)
    {
        this.objCaverna = objCaverna;
        this.objFloresta = objFloresta;
        this.objLagoRio = objLagoRio;
        this.objMontanha = objMontanha;
        this.objRuinas = objRuinas;
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
        for (int i = 0; i < recursos.length; i++)
        {
            if (i == randomInt)
            {
                recursoEscolhidoNome = recursos[i];
            }
        }

        if (recursoEscolhidoNome.equals(objAgua.getNome()))
        {
            recursoEscolhidoItem = objAgua;
        }
        else if (localNome.equals("Corpo d'água"))
        { recursoEscolhidoItem = objAgua; }
        return recursoEscolhidoItem;
    }
}
