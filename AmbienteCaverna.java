public class AmbienteCaverna extends Ambiente
{
    /*
        Um ambiente subterrâneo que pode oferecer abrigo contra o clima, mas esconde
        perigos desconhecidos.

    Atributos adicionais:
        ● Pouca luz: Exige lanterna ou tochas para exploração eficiente.
        ● Presença de criaturas desconhecidas: Pode ser um refúgio seguro ou um
        local perigoso.
        ● Água de gotejamento: Possível fonte de hidratação.

    Recursos disponíveis:
        ● Rochas e minérios raros.
        ● Pequenos lagos subterrâneos (algumas vezes contaminados).
        ● Ossos e vestígios de exploradores antigos.

    Eventos comuns:
        ● Encontro com uma criatura hostil.
        ● Descoberta de um túnel oculto.
        ● Desmoronamento parcial, bloqueando saídas.
     */

    public AmbienteCaverna()
    {
        super(
        "Caverna",
        "Um ambiente subterrâneo que pode oferecer abrigo contra o clima, mas esconde perigos desconhecidos",
        new String[] {"Pedra", "Osso", "Água contaminada"}, //recursos
        new float[] {0.1f, 0.1f, 0.1f}, //probabilidades eventos
        new String[] {"Criatura", "Tunel", "Desmoronamento"} // climas e eventos
        );
    }
}
