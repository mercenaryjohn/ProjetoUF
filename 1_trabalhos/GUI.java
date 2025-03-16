import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener
{

    private enum Actions 
    {
        ATACAR,
        CORRER
    }

    private int contador = 0;
    private JLabel rotulo1;
    private JFrame frame;
    private JPanel panel;

    public GUI()
    {
        frame = new JFrame();

        //JButton botao1 = new JButton("Opção 1");
        //botao1.addActionListener(this);

        JButton botao1 = new JButton("Atacar");
        botao1.setActionCommand(Actions.ATACAR.name());
        botao1.addActionListener(this);
        frame.add(botao1);

        JButton botao2 = new JButton("Correr");
        botao1.setActionCommand(Actions.CORRER.name());
        botao1.addActionListener(this);
        frame.add(botao2);

        rotulo1 = new JLabel("Antes de clicar fica esse texto");

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout());
        panel.add(botao1);
        panel.add(botao2);
        panel.add(rotulo1);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Our GUI");
        frame.pack();
        frame.setVisible(true);

    }

    public static void main (String[] args)
    {
        new GUI();
    }

    //public void actionPerformed(ActionEvent e)
    //{
    //    contador++;
    //    rotulo1.setText("Cliques: " + contador);
    //}
    public void actionPerformed(ActionEvent evt) 
    {
        String comando = evt.getActionCommand();
        if (comando.equals("ATACAR"))
        {
            JOptionPane.showMessageDialog(null, "HYAAA");
            contador++;
            rotulo1.setText("Ataques: " + contador);
        } 
        else if (comando.equals("CORRER"))
        {
            JOptionPane.showMessageDialog(null, "AAAAAAA");
        }
    }
}