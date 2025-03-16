public class FibonacciMediana 
{
    public double fibonaciatorMediana (double contador)
    {
        int n = 0; // contador para cada loop do while
        double mediana = 0; // resultado final
        double numeroNaSequencia = 0; // numero atual da sequencia
        double somar = 0; // numero que vai somar com o atual
        double seguraValorAnterior = 0; // segura o valor antigo para atribuí-lo ao somar
        boolean numeroPar = false;

        if (contador% 2 == 0) // se contador for par será true
        {
            numeroPar = true;
        }

        while(n < contador)
        {
            seguraValorAnterior = numeroNaSequencia;
            numeroNaSequencia = numeroNaSequencia + somar;
            somar = seguraValorAnterior;
            //System.out.println(numeroNaSequencia); //DEBUG

            if(numeroNaSequencia == 0) // necessário já que o primeiro número da sequência fibonacci é zero
            {
                somar = 1;
            }
            
            //System.out.println(contador/2); //DEBUG
            //System.out.println(n); //DEBUG
            if (n == contador/2 || n + 0.5 == contador/2) // entra se n chegou na metade da sequencia
            {
                if (numeroPar == true) // entra se contador for par
                {
                    mediana = ((numeroNaSequencia + seguraValorAnterior) / 2);
                    break;
                }
                else // else contador é impar
                {
                    mediana = numeroNaSequencia;
                    break;
                }
            }
            n++;
        }
        return mediana;
    }
}
