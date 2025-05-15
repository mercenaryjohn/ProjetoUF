public class AmbienteFloresta extends Ambiente
{
    /*
        Uma área rica em recursos naturais, mas também habitada por predadores.

    Atributos adicionais:
        ● Vegetação densa: Reduz visibilidade e dificulta a movimentação.
        ● Fauna abundante: Possibilidade de caça, mas também de ataques de
        criaturas.
        ● Clima úmido: A umidade dificulta o acendimento de fogueiras.
    Recursos disponíveis:
        ● Frutas, raízes e cogumelos (alguns venenosos).
        ● Madeira para fogueiras e ferramentas.
        ● Pequenos animais para caça.
    Eventos comuns:
        ● Ataque de lobo ou urso.
        ● Encontro com um explorador perdido.
        ● Chuva intensa, dificultando a exploração.
     */

    public AmbienteFloresta()
    {
        super(
        "Floresta",
        "Uma área rica em recursos naturais, mas também habitada por predadores",
                 /*fazer com que Escoteiro saiba se o Cogumelo é venenoso ou não*/
        new String[] {"Fruta", "Raíz", "Cogumelo", "Cogumelo (Venenoso)", "Madeira"}, //recursos
        new float[] {0.1f, 0.1f, 0.1f, 0.1f}, //probabilidades eventos
                                                    /* dificultar exploração se chuva */
        new String[] {"Lobo", "Urso", "Explorador perdido", "Chuva intensa"} // climas e eventos
        );
    }
}
