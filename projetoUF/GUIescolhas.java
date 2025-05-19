public class GUIescolhas // obsoleto
{
    private int vidaMáxima;
    private int energiaMáxima;
    public GUIescolhas (EscolherClasse player)
    {
        this.vidaMáxima = player.getVida();
        this.energiaMáxima = player.getEnergia();
    }

    public void escolhas(String escolha, EscolherClasse player)
    {
        //System.out.println("Fez escolha"); // DEBUG
        
        if (escolha.equals("2")) //Descansar
        {
            player.setEnergia(energiaMáxima);
            //System.out.println("Debug_energia: " + player.getEnergia());
        }
        if (escolha.equals("3")) // Inventário
        {

        }
    }
}
