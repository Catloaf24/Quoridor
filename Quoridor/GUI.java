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

        //Layers erstellen
        JLayeredPane layers = new JLayeredPane();
        layers.setPreferredSize(new Dimension(1080, 1080));

        //-----------------------------------------------------------------------------------------------------

        //Background NSEW Panels
        JPanel north = new BackgroundPanel("Textures\\\\textureTop.JPG");

        JPanel south = new BackgroundPanel("Textures\\\\textureBottom.JPG");

        JPanel east = new BackgroundPanel("Textures\\\\textureRight.JPG");

        JPanel west = new BackgroundPanel("Textures\\\\textureLeft.JPG");
    
        //NSEW Panels size
        north.setPreferredSize(new Dimension(1080,252));
        south.setPreferredSize(new Dimension(1080,252));
        east.setPreferredSize(new Dimension(252,576));
        west.setPreferredSize(new Dimension(252,576));

        //-------------------------------------------------------------------------------------------------------

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
        
        //-----------------------------------------------------------------------------------------------------

        //SpielerPanel
        JPanel spielerRed = new SpielerPanel("Textures\\\\Quoridor_Player_R.png");
        spielerRed.setOpaque(false);
        spielerRed.setPreferredSize(new Dimension(64,64));
        spielerRed.setBounds(508, 252, 64, 64); //variablen für movement die dann angepasst werden von movement Methode/Klasse, Aktualisierung mit .reprint
        
        JPanel spielerBlue = new SpielerPanel("Textures\\Quoridor_Player_B.png");
        spielerBlue.setOpaque(false);
        spielerBlue.setPreferredSize(new Dimension(64,64));
        spielerBlue.setBounds(508, 764, 64, 64);
        
        //-----------------------------------------------------------------------------------------------------

        //Hintergrund
        JPanel hintergrund = new JPanel();
        hintergrund.setLayout(new BorderLayout());
        hintergrund.setBounds(0, 0, 1080, 1080);

        //Hintergrund main Assembly
        hintergrund.add(spielfeldPanel, BorderLayout.CENTER);
        hintergrund.add(north, BorderLayout.NORTH);
        hintergrund.add(south, BorderLayout.SOUTH);
        hintergrund.add(east, BorderLayout.EAST);
        hintergrund.add(west, BorderLayout.WEST);
        
        //-----------------------------------------------------------------------------------------------------

        //Alles in die Layers adden
        layers.add(hintergrund, Integer.valueOf(1));
        layers.add(spielerRed, Integer.valueOf(2));
        layers.add(spielerBlue, Integer.valueOf(2));

        //Main Assembly
        frame.add(layers);
        frame.pack();
        frame.setVisible(true);//fenster sichtbar

    }
    public static void main(String[] args)
    {
        GUI gui = new GUI();
    }
}
