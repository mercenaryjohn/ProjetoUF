public class Escolhas 
{
    public String escolhas(String escolha)
    {
        String escolhaEmTexto = null;
        String escolhaEmCasoDeInputErrado = "";
        switch(escolha)
        {
            case "1": //movimento
                escolhaEmTexto = "andar";
                return escolhaEmTexto;
            case "2": //descansar
                escolhaEmTexto = "descansar";
                return escolhaEmTexto;
            case "3":
                escolhaEmTexto = "inventário";
                return escolhaEmTexto;
            case "4":
                escolhaEmTexto = "status";
                return escolhaEmTexto;
            case "0":
                escolhaEmTexto = "sair";
                return escolhaEmTexto;
            default:
                return escolhaEmCasoDeInputErrado;

        }
    }
}
