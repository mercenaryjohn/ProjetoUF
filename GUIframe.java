


import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class GUIframe extends JFrame
{
    private GUIscreen tela;

    public GUIframe (char[][] mapa)
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Última Fronteira");
        setSize(1100,650); //1100,650
        setResizable(true);

        iniciarFrame(mapa);
    }

    public GUIscreen getTela()
    {
        return tela;
    }

    public void iniciarFrame(char[][] mapa)
    {
        setLocationRelativeTo(null);

        setLayout(new GridLayout(1,1,getWidth(),getHeight()));

        tela = new GUIscreen(mapa);

        tela.setPreferredSize(new Dimension(1100,650));
        add(tela);

        pack();
        setVisible(true);
    }
}
