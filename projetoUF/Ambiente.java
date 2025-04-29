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
    private String[] recursosDisponíveis;
    private float[] probabilidadeEventos; // não tenho certeza do tipo ainda
    private String[] climas; // não tenho certeza do tipo ainda

    public Ambiente(String nome, String descrição, String[] recursosDisponíveis, 
    float[] probabilidadeEventos, String[] climas)
    {
        this.nome = nome;
        this.descrição = descrição;
        this.recursosDisponíveis = recursosDisponíveis;
        this.probabilidadeEventos = probabilidadeEventos;
        this.climas = climas;
    }

    public String getNome()
    { return nome; }
    public String getDescrição()
    { return descrição; }
    public String[] getRecursosDisponíveis()
    { return recursosDisponíveis; }
    public float[] getProbabilidadeEventos()
    { return probabilidadeEventos; }
    public String[] getClimas()
    { return climas; }


    public void explorar()
    {

    }

    public void gerarEvento()
    {

    }

    public void modificarClima()
    {

    }


    public char[][] fazerMapaMundo()
    {
        int altura = 1000;
        int largura = 1000;
        int chunk = 8; // tamanho dos chunks no mapa

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

                if (n < 0.4) mapa[y][x] = ambientes[0]; // Floresta
                else if (n < 0.45) mapa[y][x] = ambientes[1]; // Montanha
                else if (n < 0.48) mapa[y][x] = ambientes[2]; // Caverna
                else if (n < 0.58) mapa[y][x] = ambientes[3]; // Água
                else if (n < 0.63) mapa[y][x] = ambientes[4]; // Ruínas
                else mapa[y][x] = ambientes[5]; // Planície
            }
        }
        
        mapa[altura/2][largura/2] = ambientes[5]; // Planície

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
        // Mostrar o mapa total
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
        int chunkVisao = 5; //chunkVisao
        int mapaAltura = mapa.length;
        int mapaLargura = mapa[0].length;

        // Posição do player
        int playerX = mapaLargura / 2 + (int)localização[0]; // Em X, tem que ser adição
        int playerY = (mapaAltura / 2) - (int)localização[1]; // Em Y, tem que ser subtração
        //System.out.println(playerX); //DEBUG
        //System.out.println(playerY);

        // Raio de 2 chunks (2 * 5 = 10)
        int raio = chunkVisao * 2;

        // Limites da janela
        int inicioY = Math.max(0, playerY - raio);
        int fimY = Math.min(mapaAltura, playerY + raio + 1);

        int inicioX = Math.max(0, playerX - raio);
        int fimX = Math.min(mapaLargura, playerX + raio + 1);

        // 'F','M','C','~','R','_'
        String RESET = "\u001B[0m";
        String AZUL = "\u001B[44m";
        String VERDE = "\u001B[42m";
        String AMARELO = "\u001B[43m";
        String BRANCO = "\u001B[47m";
        String CINZA = "\u001B[100m";
        String MAGENTA = "\u001B[45m";
        // https://www.tutorialspoint.com/how-to-print-colored-text-in-java-console
        // https://en.wikipedia.org/wiki/ANSI_escape_code

        // Imprimir a área ao redor do player
        for (int y = inicioY; y < fimY; y++) 
        {
            for (int x = inicioX; x < fimX; x++) 
            {
                if(y == playerY && x == playerX)
                {
                    
                    char player = '■';
                    //System.out.print("■" + " ");
                    //https://theasciicode.com.ar/extended-ascii-code/black-square-ascii-code-254.html
                    if (mapa[y][x] == 'F')
                    {
                        System.out.print(VERDE + player + " " + RESET);
                    }
                    else if (mapa[y][x] == 'M') 
                    {
                        System.out.print(BRANCO + player + " " + RESET);
                    }
                    else if (mapa[y][x] == 'C') 
                    {
                        System.out.print(CINZA + player + " " + RESET);
                    }
                    else if (mapa[y][x] == '~') 
                    {
                        System.out.print(AZUL + player + " " + RESET);
                    }
                    else if (mapa[y][x] == 'R') 
                    {
                        System.out.print(MAGENTA + player + " " + RESET);
                    }
                    else if (mapa[y][x] == '_') 
                    {
                        System.out.print(AMARELO + player + " " + RESET);
                    }
                    else
                    {
                        System.out.print(player + " ");
                    }
                }
                else
                {
                    if (mapa[y][x] == 'F')
                    {
                        System.out.print(VERDE + mapa[y][x] + " " + RESET);
                    }
                    else if (mapa[y][x] == 'M') 
                    {
                        System.out.print(BRANCO + mapa[y][x] + " " + RESET);
                    }
                    else if (mapa[y][x] == 'C') 
                    {
                        System.out.print(CINZA + mapa[y][x] + " " + RESET);
                    }
                    else if (mapa[y][x] == '~') 
                    {
                        System.out.print(AZUL + mapa[y][x] + " " + RESET);
                    }
                    else if (mapa[y][x] == 'R') 
                    {
                        System.out.print(MAGENTA + mapa[y][x] + " " + RESET);
                    }
                    else if (mapa[y][x] == '_') 
                    {
                        System.out.print(AMARELO + mapa[y][x] + " " + RESET);
                    }
                    else
                    {
                        System.out.print(mapa[y][x] + " ");
                    }
                }
            }
            System.out.println();
        }
    }
}
