//Imports
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI
{
    

    public GUI()
    {
        JPanel buttonManager = new JPanel();
        buttonManager.setLayout(new BorderLayout());
        buttonManager.setPreferredSize(new Dimension(252,252));

        JButton buttonUp = new JButton("HALLO");
        JButton buttonDown = new JButton("HALLO");
        JButton buttonLeft = new JButton("HALLO");
        JButton buttonRight = new JButton("HALLO");
        JButton buttonWall = new JButton("HALLO");

        buttonManager.add(buttonUp, BorderLayout.NORTH);
        buttonManager.add(buttonDown, BorderLayout.SOUTH);
        buttonManager.add(buttonRight, BorderLayout.EAST);
        buttonManager.add(buttonLeft, BorderLayout.WEST);
        buttonManager.add(buttonWall, BorderLayout.CENTER);

        JPanel fillBMn = new JPanel();
        fillBMn.setBackground(Color.white);
        JPanel fillBMs = new JPanel();
        fillBMn.setBackground(Color.white);
        JPanel fillBMw = new JPanel();
        fillBMn.setBackground(Color.white);
        JPanel fillBMe = new JPanel();
        fillBMn.setBackground(Color.white);



        JFrame frame = new JFrame();//erstellt Fenster
        frame.setSize(1080, 1080);
        frame.setTitle("Quoridor");//Fenstername
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//fenster schließt bei Kreuz
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());//Borderlayout wird für frame festgelegt

        //Frame NSEW Panels
        JPanel north = new BackgroundPanel("textureTop.JPG");

        JPanel south = new BackgroundPanel("textureBottom.JPG");

        JPanel east = new BackgroundPanel("textureRight.JPG");

        JPanel west = new BackgroundPanel("textureLeft.JPG");

        JPanel west = new JPanel();
        west.setBackground(Color.cyan);
        west.setLayout(new BorderLayout());
        west.add(buttonManager, BorderLayout.CENTER);

        west.add(fillBMn, BorderLayout.NORTH);
        west.add(fillBMs, BorderLayout.SOUTH);
        west.add(fillBMw, BorderLayout.WEST);
        west.add(fillBMe, BorderLayout.EAST);

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
