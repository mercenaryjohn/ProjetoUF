


import Player.*;
import Ambientes.*;

public class MainGUI
{
    public static void main(String[] args)
    {
        ///////////////////////////////////////////////////////////////////////// MAPA
        Ambiente objAmbiente = new Ambiente(null, null,
        null, null, null);
        char[][] mapa = objAmbiente.fazerMapaMundo();
        //objAmbiente.printVisaoAtualMapa(mapa, classeEscolhida.getLocalização()); //DEBUG
        /////////////////////////////////////////////////////////////////////////

        GUIframe frame = new GUIframe(mapa);
        frame.setVisible(true);
        GUIscreen tela = frame.getTela();

        String[] listaDeClasses = {"Escoteiro","Engenheiro","Veterinário","Prisioneiro"};

        EscolherClasse classeEscolhida = tela.mostrarMenuInicial(listaDeClasses);
        tela.setPlayer(classeEscolhida);
        tela.setSprites();
        tela.setClasseFoiEscolhida(true);
        InventarioClasse objInventario = new InventarioClasse();
        tela.setInventario(objInventario);

        //Agua objAgua = new Agua(); // DEBUG
        //objInventario.adicionarItem(classeEscolhida, objAgua);
        //Alimento objAlimento = new Alimento(0, 0); // DEBUG
        //objAlimento.setAlimentoStats("Fruta");
        //objInventario.adicionarItem(classeEscolhida, objAlimento);

        String[] opções = {"1 - Vasculhar", "2 - Descansar ( - fome e sede)","3 - Inventário",/*"4 - Status",*/"- para voltar [e] -"};
        tela.setListaDeOpcoes(opções);

        /*
        for (int i = 1; i < 20; i++) //TESTE TAMANHO INVENTÁRIO
        {Agua objAgua = new Agua();
            objAgua.setNome(""+i);
        objInventario.adicionarItem(classeEscolhida, objAgua);} */
    }
}