import java.util.Random;

public class Ambiente 
{
    /*
    ● Nome: Identificação do ambiente.
    ● Descrição: Texto explicativo sobre as características gerais do local.
    ● Dificuldade de exploração: Define se o ambiente consome mais energia ao
    ser percorrido.
    ● Recursos disponíveis: Lista de itens que podem ser coletados na área.
    ● Probabilidade de eventos: Define a frequência e o tipo de eventos que
    ocorrem no ambiente.
    ● Condições climáticas predominantes: Influencia a jogabilidade (exemplo:
    florestas são úmidas, montanhas podem ser frias, desertos podem ter
    tempestades de areia).
    */
    private String nome;
    private String descrição;
    private String recursosDisponíveis;
    private float[] probabilidade; // não tenho certeza do tipo ainda
    private String[] climas; // não tenho certeza do tipo ainda

    public Ambiente(String nome, String descrição, String recursosDisponíveis, 
    float[] probabilidade, String[] climas)
    {
        this.nome = nome;
        this.descrição = descrição;
        this.recursosDisponíveis = recursosDisponíveis;
        this.probabilidade = probabilidade;
        this.climas = climas;
    }

    public char[][] fazerMapaMundo()
    {
        int altura = 1000;
        int largura = 1000;
        int chunk = 5;

        char [][] mapa = new char[altura][largura];
        char [][] mapaComChunks = new char[altura * chunk][largura * chunk];

        char[] ambientes = {'F','M','C','~','R','_'};
        //Floresta, Montanha, Caverna, Água (Lago e rio), Ruínas, Planície
        // 5 ambientes, 0 a 4

        Random random = new Random();

        double[][] noise = new double[altura][largura];

        // Criar ruído (noise)
        for (int y = 0; y < altura; y++) 
        {
            for (int x = 0; x < largura; x++) 
            {
                noise[y][x] = random.nextDouble();
            }
        }

        // Média das posições vizinhas
        for (int y = 1; y < altura - 1; y++) 
        {
            for (int x = 1; x < largura - 1; x++) 
            {
                noise[y][x] = (
                    noise[y-1][x] + 
                    noise[y+1][x] +
                    noise[y][x-1] + 
                    noise[y][x+1] +
                    noise[y][x]
                ) / 5.0;
            }
        }

        // Converter os números para letras
        for (int y = 0; y < altura; y++) 
        {
            for (int x = 0; x < largura; x++) 
            {
                double n = noise[y][x];

                if (n < 0.3) mapa[y][x] = ambientes[0]; // Floresta
                else if (n < 0.40) mapa[y][x] = ambientes[1]; // Montanha
                else if (n < 0.42) mapa[y][x] = ambientes[2]; // Caverna
                else if (n < 0.55) mapa[y][x] = ambientes[3]; // Água
                else if (n < 0.60) mapa[y][x] = ambientes[4]; // Ruínas
                else mapa[y][x] = ambientes[5]; // Planície
            }
        }

        // Expandir cada letra em um bloco 5x5
        for (int y = 0; y < altura; y++) 
        {
            for (int x = 0; x < largura; x++) 
            {
                char letra = mapa[y][x];
                for (int i = 0; i < chunk; i++) 
                {
                    for (int j = 0; j < chunk; j++) 
                    {
                        mapaComChunks[y * chunk + i][x * chunk + j] = letra;
                    }
                }
            }
        }
        // Mostrar o mapa
        /* 
        for (int y = 0; y < altura; y++) 
        {
            for (int x = 0; x < largura; x++) 
            {
                System.out.print(mapaComChunks[y][x] + " ");
            }
            System.out.println();
        }
        */
        return mapaComChunks;
    }



    public void printVisaoAtualMapa(char[][] mapa, double[] localização)
    {
        int chunkSize = 5;
        int mapaAltura = mapa.length;
        int mapaLargura = mapa[0].length;

        // Posição do player //TODO
        int playerX = mapaLargura / 2 - (int)localização[0]; // Tem que ser subtração
        int playerY = (mapaAltura / 2) - (int)localização[1];
        //System.out.println(playerX); //DEBUG
        //System.out.println(playerY);

        // Raio de 2 chunks (2 * 5 = 10)
        int raio = chunkSize * 2;

        // Limites da janela
        int inicioY = Math.max(0, playerY - raio);
        int fimY = Math.min(mapaAltura, playerY + raio + 1);

        int inicioX = Math.max(0, playerX - raio);
        int fimX = Math.min(mapaLargura, playerX + raio + 1);

        // Imprimir a área ao redor do player
        for (int y = inicioY; y < fimY; y++) 
        {
            for (int x = inicioX; x < fimX; x++) 
            {
                if(y == playerY && x == playerX)
                {
                    System.out.print("o" + " ");
                }
                else
                {
                    System.out.print(mapa[y][x] + " ");
                }
            }
            System.out.println();
        }
    }

}
