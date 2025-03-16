import java.util.Scanner;

public class MainAprendizado2 
{
    public static void main(String[] args)
    {
        Scanner getDouble = new Scanner(System.in);

        FibonacciMediana objFiboMed = new FibonacciMediana(); // instancia de FibonacciMediana

        System.out.println("Digite o número:");
        //double contador = getDouble.nextDouble(); //DEBUG
        double contador;
        while (!getDouble.hasNextDouble()) // pede um numero para o usuario
        {
            System.out.println("Input Inválido. Digite um número.");
            getDouble.next(); // pega o input inválido
        }
        contador = getDouble.nextDouble();
        double resultado = objFiboMed.fibonaciatorMediana(contador); // usa o numero em objFiboMed

        //System.out.println("Contador:" + contador); //DEBUG
        System.out.println("Resultado: " + resultado);

        getDouble.close();
    }
}
