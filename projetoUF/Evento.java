
public class Evento 
{
        /* 
        Nome: Identificação do evento.
        Descrição: Texto explicativo sobre o evento.
        Probabilidade de ocorrência: Define a chance de um evento acontecer a
            cada turno.
        Impacto: Indica quais aspectos do jogo serão alterados (vida, fome, sede,
            energia, sanidade, inventário, etc.).
        Condição de ativação: Determina se o evento pode ocorrer (ex.: apenas em
            determinados ambientes).
        */
    protected String nome;
    protected String descrição;
    protected float probabilidadeDeOcorrer;
    protected String[] Impacto;
    protected String condiçãoDeAtivação;

    public executar(EscolherClasse classeEscolhida, Ambiente local)
    {

    }
}
