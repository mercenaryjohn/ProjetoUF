package Ambientes;

public class AmbienteLagoRio extends Ambiente
{
    /*
        Regiões ricas em água, mas que podem esconder riscos como afogamento ou criaturas aquáticas.

    Atributos adicionais:
        ● Água abundante: Pode ser potável ou precisar de purificação.
        ● Possibilidade de pesca: Peixes podem ser uma excelente fonte de alimento.
        ● Terreno lamacento: Pode dificultar a movimentação.
    Recursos disponíveis:
        ● Peixes e algas comestíveis.
        ● Água doce (algumas vezes contaminada).
        ● Vegetação ribeirinha útil para fabricação de cordas e armadilhas.
    Eventos comuns:
        ● Ataque de criatura aquática (como piranhas ou jacarés).
        ● Tempestade, aumentando o nível da água.
        ● Encontro de um barco abandonado.
    */

    public AmbienteLagoRio()
    {
        super(
        "Corpo d'água",
        "Regiões ricas em água, mas que podem esconder riscos como afogamento ou criaturas aquáticas",
        new String[] {"Peixe", "Alga comestível", "Fibra-selvagem"}, //recursos
        new float[] {0.06f, 0.06f, 0.05f}, //probabilidades eventos
        new String[] {"Jacaré", "Piranha", "Afogamento"} // climas e eventos
        );
    }
}
