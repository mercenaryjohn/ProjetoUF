
public class Alimento extends Item
{

    private int validade; 
    private int nutrição;
    public Alimento(int validade, int nutrição)
    {
        setNome("");
        setPeso(1);
        setDurabilidade(1);
        this.validade = validade;
        this.nutrição = nutrição;
    }

    public void setValidade(int validade)
    { this.validade = validade; }
    public void setNutrição(int nutrição)
    { this.nutrição = nutrição; }

    // Total alimentos atual 5, de 0 a 4 na array
    private String[] nomesAlimentos = {"Fruta","Carne","Carne em lata","Fruta em lata","Carne seca"};
    public String[] getNomesAlimentos()
    { return nomesAlimentos; }

    public String qualAlimento(int opcao)
    {
        String nomeAlimento;
        String[] nomesAlimentos = getNomesAlimentos();

        switch(opcao)
        {
            case 0:
                nomeAlimento = nomesAlimentos[0];
                return nomeAlimento;
            case 1:
                nomeAlimento = "Carne";
                return nomeAlimento;
            case 2:
                nomeAlimento = "Carne em lata";
                return nomeAlimento;
            case 3:
                nomeAlimento = "Fruta em lata";
                return nomeAlimento;
            case 4:
                nomeAlimento = "Carne seca";
                return nomeAlimento;
            default:
                nomeAlimento = "Fruta";
                return nomeAlimento;
        }
    }

    //TODO array de objAlimentos em inventario??
    public int[] getAlimentoStats(String nomeAlimento)
    {
        String[] nomesAlimentos = getNomesAlimentos();
        int[] stats = {0,0};
        int contador = 0;
        if(nomeAlimento.equals(nomesAlimentos[contador]))
        {
            //Fruta
            //Peso 1
            //Durabilidade 1 //TODO acho melhor só ignorar durabilidade
            //Validade 7
            //Nutrição 30
        }
        contador++;
        if(nomeAlimento.equals(nomesAlimentos[contador]))
        {
            //Carne
            //Peso 4
            //Durabilidade 1
            //Validade 4
            //Nutrição 5
        }
        contador++;
        if(nomeAlimento.equals(nomesAlimentos[contador]))
        {
            //Nome Carne em lata
            //Peso 2
            // Durabilidade 1
            //Validade 730
            //Nutrição 70
        }
        contador++;

        /*
        case 4:
        setNome("Fruta em lata");
        setPeso(2);
        setDurabilidade(1);
        setValidade(730);
        setNutrição(1);

        setNome("Carne seca");
        setPeso(2);
        setDurabilidade(1);
        setValidade(100);
        setNutrição(1);
        */

        return stats;
    }

    //InventarioClasse objInventario = new InventarioClasse();
    public void usar(EscolherClasse classeEscolhida, String nomeAlimento)
    {
        String[] nomesAlimentos = getNomesAlimentos();
        int contador = 0;
        while(contador < (nomesAlimentos.length))
        {
            if(nomeAlimento.equals(nomesAlimentos[contador]))
            {
                classeEscolhida.setFome(classeEscolhida.getFome() + 50); //TODO: número temporário
                break;
            }
            contador++;
        }
    }
}
