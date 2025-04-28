
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

    public int getValidade()
    { return validade; }
    public int getNutrição()
    { return nutrição; }

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
                nomeAlimento = nomesAlimentos[1];
                return nomeAlimento;
            case 2:
                nomeAlimento = nomesAlimentos[2];
                return nomeAlimento;
            case 3:
                nomeAlimento = nomesAlimentos[3];
                return nomeAlimento;
            case 4:
                nomeAlimento = nomesAlimentos[4];
                return nomeAlimento;
            default:
                nomeAlimento = nomesAlimentos[0];
                return nomeAlimento;
        }
    }

    public void setAlimentoStats(String nomeAlimento)
    {
        String[] nomesAlimentos = getNomesAlimentos();
        int contador = 0;
        if(nomeAlimento.equals(nomesAlimentos[contador]))
        {
            setNome("Fruta");
            setPeso(1);
            setDurabilidade(1);
            setValidade(7);
            setNutrição(30);
            //Fruta
            //Peso 1
            //Durabilidade 1
            //Validade 7
            //Nutrição 30
        }
        contador++;
        if(nomeAlimento.equals(nomesAlimentos[contador]))
        {
            setNome("Carne");
            setPeso(4);
            setDurabilidade(1);
            setValidade(3);
            setNutrição(20);
            //Carne
            //Peso 4
            //Durabilidade 1
            //Validade 4
            //Nutrição 5
        }
        contador++;
        if(nomeAlimento.equals(nomesAlimentos[contador]))
        {
            setNome("Carne em lata");
            setPeso(4);
            setDurabilidade(2);
            setValidade(730);
            setNutrição(80);
            //Nome Carne em lata
            //Peso 2
            //Durabilidade 1
            //Validade 730
            //Nutrição 70
        }
        contador++;
        if(nomeAlimento.equals(nomesAlimentos[contador]))
        {
            setNome("Fruta em lata");
            setPeso(2);
            setDurabilidade(1);
            setValidade(730);
            setNutrição(60);
        }
        contador++;
        if(nomeAlimento.equals(nomesAlimentos[contador]))
        {
            setNome("Carne seca");
            setPeso(2);
            setDurabilidade(1);
            setValidade(100);
            setNutrição(200);
        }
    }

    //InventarioClasse objInventario = new InventarioClasse();
    public void usar(EscolherClasse classeEscolhida)
    {
        String[] nomesAlimentos = getNomesAlimentos();
        int contador = 0;
        String alimentoNome = getNome();
        while(contador < (nomesAlimentos.length))
        {
            if(alimentoNome.equals(nomesAlimentos[contador]))
            {
                classeEscolhida.setFome(classeEscolhida.getFome() + getNutrição()); //TODO: número temporário
                break;
            }
            contador++;
        }
    }
}
