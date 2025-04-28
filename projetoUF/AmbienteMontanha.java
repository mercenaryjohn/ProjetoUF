public class AmbienteMontanha extends Ambiente
{
    /*
    Uma região de difícil acesso, mas rica em minérios e pedras preciosas.

    Atributos adicionais:
        ● Terreno acidentado: Exige mais energia para ser explorado.
        ● Clima instável: Nevascas e ventos fortes podem ocorrer repentinamente.
        ● Baixa vegetação: Pouca disponibilidade de alimentos naturais.
    Recursos disponíveis:
        ● Minérios e pedras preciosas.
        ● Água de degelo, mas precisa ser purificada.
        ● Refúgios naturais em cavernas.
    Eventos comuns:
        ● Nevasca repentina, reduzindo drasticamente a temperatura.
        ● Deslizamento de pedras, causando ferimentos.
        ● Descoberta de uma caverna segura.
     */

    public AmbienteMontanha()
    {
        super(
        "Montanha",
        "Uma região de difícil acesso, mas rica em minérios e pedras preciosas",
        new String[] {"pedra", "gelo"}, //TEMP
        new float[] {0.2f, 0.3f}, //TEMP
        new String[] {"Nevasca Repentina", "Deslizamento", "DescobertaCaverna"} 
        );
    }
    
}
