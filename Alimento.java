
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
    private String[] nomesAlimentos = {"Fruta","Carne","Carne em lata","Fruta em lata","Carne seca",
                                        "Raíz", "Cogumelo", "Cogumelo (Venenoso)", "Peixe", "Alga comestível"};
    public String[] getNomesAlimentos()
    { return nomesAlimentos; }

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
        }
        contador++; //1
        if(nomeAlimento.equals(nomesAlimentos[contador]))
        {
            setNome("Carne");
            setPeso(4);
            setDurabilidade(1);
            setValidade(3);
            setNutrição(20);
        }
        contador++; //2
        if(nomeAlimento.equals(nomesAlimentos[contador]))
        {
            setNome("Carne em lata");
            setPeso(4);
            setDurabilidade(2);
            setValidade(730);
            setNutrição(80);
        }
        contador++; //3
        if(nomeAlimento.equals(nomesAlimentos[contador]))
        {
            setNome("Fruta em lata");
            setPeso(2);
            setDurabilidade(1);
            setValidade(730);
            setNutrição(70);
        }
        contador++; //4
        if(nomeAlimento.equals(nomesAlimentos[contador]))
        {
            setNome("Carne seca");
            setPeso(2);
            setDurabilidade(1);
            setValidade(100);
            setNutrição(100);
        }
        contador++; //5
        if(nomeAlimento.equals(nomesAlimentos[contador]))
        {
            setNome("Raíz");
            setPeso(2);
            setDurabilidade(1);
            setValidade(10);
            setNutrição(60);
        }
        contador++; //6
        if(nomeAlimento.equals(nomesAlimentos[contador]))
        {
            setNome("Cogumelo");
            setPeso(1);
            setDurabilidade(1);
            setValidade(20);
            setNutrição(60);
        }
        contador++; //7
        if(nomeAlimento.equals(nomesAlimentos[contador]))
        {
            setNome("Cogumelo (Venenoso)");
            setPeso(1);
            setDurabilidade(1);
            setValidade(30);
            setNutrição(-50); //venenoso
        }
        contador++; //8
        if(nomeAlimento.equals(nomesAlimentos[contador]))
        {
            setNome("Peixe");
            setPeso(3);
            setDurabilidade(1);
            setValidade(30);
            setNutrição(70);
        }
        contador++; //9
        if(nomeAlimento.equals(nomesAlimentos[contador]))
        {
            setNome("Alga comestível");
            setPeso(1);
            setDurabilidade(1);
            setValidade(30);
            setNutrição(25);
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
                classeEscolhida.setFome(classeEscolhida.getFome() + getNutrição());
                break;
            }
            contador++;
        }
    }
}
