package UFsource.Ambientes;

public class AmbienteRuinas extends Ambiente
{
    /*
        Restos de antigas construções que podem conter suprimentos valiosos ou armadilhas.

    Atributos adicionais:
        ● Estruturas instáveis: O local pode desmoronar a qualquer momento.
        ● Presença de outros sobreviventes: Algumas ruínas podem estar ocupadas.
        ● Baixo risco climático: Normalmente oferecem abrigo contra o clima.
    Recursos disponíveis:
        ● Ferramentas antigas e munição.
        ● Alimentos enlatados ainda comestíveis.
        ● Mapas e pistas sobre o ambiente ao redor.
    Eventos comuns:
        ● Encontrar um grupo de sobreviventes (podem ser aliados ou hostis).
        ● Armadilhas deixadas por antigos ocupantes.
        ● Descoberta de uma passagem secreta para outra área.
    Relacionamento entre Ambientes, Personagens e Eventos:
            Os ambientes interagem diretamente com personagens e eventos, criando um
            ecossistema dinâmico.
        ● Cada ambiente tem eventos específicos que podem ocorrer apenas em
        determinadas áreas.
        ● Personagens podem ter vantagens ou desvantagens em certos
        ambientes, dependendo de suas habilidades.
        ● Mudanças climáticas afetam a jogabilidade, tornando alguns ambientes
        mais ou menos perigosos com o tempo.
    */

    public AmbienteRuinas()
    {
        super(
        "Ruínas Abandonadas",
        "Restos de antigas construções que podem conter suprimentos valiosos ou armadilhas",
        new String[] {"Arma antiga", "Munição", "Carne em lata", "Fruta em lata", "Abrigo", "Remédio"}, //recursos
        new float[] {0.05f, 0.02f, 0.07f}, //probabilidades eventos
        new String[] {"Sobrevivente hostil", "Passagem secreta", "Armadilha"} // climas e eventos
        );
    }
}
