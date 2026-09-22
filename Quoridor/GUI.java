//Imports
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI
{
    

    public GUI()
    {
        //BuTTON Pannel größe anpassen und filler auch für Optimalen Platz für Buttons (192px x 192px)
        //Buttons
        JPanel buttonManager = new JPanel();
        buttonManager.setBackground(Color.white);
        buttonManager.setLayout(new GridLayout(3,3));
        buttonManager.setPreferredSize(new Dimension(252,252));

        JButton buttonUp = new JButton("Up");
        buttonUp.setPreferredSize(new Dimension(64, 64));
        JButton buttonDown = new JButton("Down");
        buttonDown.setPreferredSize(new Dimension(64, 64));
        JButton buttonLeft = new JButton("Left");
        buttonLeft.setPreferredSize(new Dimension(64, 64));
        JButton buttonRight = new JButton("Right");
        buttonRight.setPreferredSize(new Dimension(64, 64));
        JButton buttonWall = new JButton("Wall");
        buttonWall.setPreferredSize(new Dimension(64, 64));

        //Zeile1
        buttonManager.add(new JLabel());
        buttonManager.add(buttonUp);
        buttonManager.add(new JLabel());

        //Zeile2
        buttonManager.add(buttonLeft);
        buttonManager.add(buttonWall);
        buttonManager.add(buttonRight);
        
        //Zeile3
        buttonManager.add(new JLabel());
        buttonManager.add(buttonDown);
        buttonManager.add(new JLabel());

        JPanel fillBMn = new JPanel();
        fillBMn.setBackground(Color.white);
        JPanel fillBMs = new JPanel();
        fillBMn.setBackground(Color.white);



        JFrame frame = new JFrame();//erstellt Fenster
        frame.setSize(1080, 1080);
        frame.setTitle("Quoridor");//Fenstername
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//fenster schließt bei Kreuz
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());//Borderlayout wird für frame festgelegt

        
        //Frame NSEW Panels
        JPanel north = new JPanel();
        north.setBackground(Color.orange);

        JPanel south = new JPanel();
        south.setBackground(Color.orange);

        JPanel east = new JPanel();
        east.setBackground(Color.cyan);

        JPanel west = new JPanel();
        west.setLayout(new GridLayout(3,1));

        west.add(fillBMs);
        west.add(buttonManager);
        west.add(fillBMn);

        north.setPreferredSize(new Dimension(1080,252));
        south.setPreferredSize(new Dimension(1080,252));
        east.setPreferredSize(new Dimension(252,576));
        west.setPreferredSize(new Dimension(252,576));


        //SpielfeldPanel
        JPanel spielfeldPanel = new JPanel();//SpielfeldPanel erstellen
        spielfeldPanel.setBounds(0, 0, 576, 576);
        spielfeldPanel.setLayout(new GridLayout(9,9));//spielfeldPanel bekommt GridLayout von 9x9
        spielfeldPanel.setPreferredSize(new Dimension(576, 576));

        Spielfelder[] array = new Spielfelder[81];

        for (int i = 0; i < 81; i++)
            {
            array[i] = new Spielfelder();
            System.out.println("Feld" + i);
            spielfeldPanel.add(array[i]);
            }

        //Main Assembly
        frame.add(spielfeldPanel, BorderLayout.CENTER);//spielfeldPanel wird in Center geadded
        frame.add(north, BorderLayout.NORTH);//north wird in NORTH geadded
        frame.add(south, BorderLayout.SOUTH);//south wird in SOUTH geadded
        frame.add(east, BorderLayout.EAST);//east wird in EAST geadded
        frame.add(west, BorderLayout.WEST);//west wird in WEST geadded
        frame.pack();
        frame.setVisible(true);//fenster sichtbar
    }
    public static void main(String[] args)
    {
        GUI gui = new GUI();
    }
}