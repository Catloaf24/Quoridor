//Imports
import java.awt.*;
import javax.swing.*;


public class GUI
{
    

    public GUI()
    {
        JFrame frame = new JFrame();//erstellt Fenster
        frame.setSize(1080, 1080);
        frame.setTitle("Quoridor");//Fenstername
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//fenster schließt bei Kreuz
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());//Borderlayout wird für frame festgelegt

        //Frame NSEW Panels
        JPanel north = new BackgroundPanel("Quoridor/textureTop.JPG");

        JPanel south = new BackgroundPanel("Quoridor/textureBottom.JPG");

        JPanel east = new BackgroundPanel("Quoridor/textureRight.JPG");

        JPanel west = new BackgroundPanel("Quoridor/textureLeft.JPG");

        JLayeredPane westLayers = new JLayeredPane();
        westLayers.setPreferredSize(new Dimension(251, 576));
        west.setBounds(0, 0, 252, 576);
        westLayers.add(west, Integer.valueOf(0));
        //controllsWest = new ControllsWest();

        //westLayers.add(ControllsWest, Integer.valueOf(100));
    
        north.setPreferredSize(new Dimension(1080,252));
        south.setPreferredSize(new Dimension(1080,252));
        east.setPreferredSize(new Dimension(252,576));
        //controllsWest.setPreferredSize(new Dimension(252,576));


        //SpielfeldPanel
        JPanel spielfeldPanel = new JPanel();//SpielfeldPanel erstellen
        spielfeldPanel.setBackground(Color.gray);
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
        frame.add(westLayers, BorderLayout.WEST);//west wird in WEST geadded
        frame.pack();
        frame.setVisible(true);//fenster sichtbar
    }
    public static void main(String[] args)
    {
        GUI gui = new GUI();
    }
}
