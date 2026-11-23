//Imports
import java.awt.*;
import javax.swing.*;


public class GUI
{
    SteuerungR steuerungR;
    SteuerungB steuerungB;
    WandSteuerungR wandR;
    WandSteuerungB wandB;

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
        
        //WallPanels
        //Red
        JPanel wallPanelB = new JPanel();
        wallPanelB.setOpaque(false);
        wallPanelB.setLayout(null);
        wallPanelB.setBounds(389, 0, 313, 1080); //389 Start, Offset 34#

        WallPanelB[] walls = new WallPanelB[10];

        for (int i = 0; i < 10; i++) {
            walls[i] = new WallPanelB("Textures\\WallBVert.png");
            walls[i].setOpaque(false);
            walls[i].setBackground(Color.RED);
            walls[i].setBounds(i * 34, 65, 7, 128);
            wallPanelB.add(walls[i]);
        }

        //Blue
        JPanel wallPanelR = new JPanel();
        wallPanelR.setOpaque(false);
        wallPanelR.setLayout(null);
        wallPanelR.setBounds(0, 830, 1080, 313); //389 Start, Offset 34#

        WallPanelR[] walls1 = new WallPanelR[10];

        for (int j = 0; j < 10; j++) {
            walls1[j] = new WallPanelR("Textures\\WallRVert.png");
            walls1[j].setOpaque(false);
            walls1[j].setBackground(Color.RED);
            walls1[j].setBounds(377 + j * 34, 65, 7, 128);
            wallPanelR.add(walls1[j]);
        }

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
        layers.add(wallPanelB, Integer.valueOf(3));
        layers.add(wallPanelR, Integer.valueOf(3));
        
        //Steuerungen erzeugen
        steuerungR = new SteuerungR(layers, spielerRed, spielerBlue);
        steuerungB = new SteuerungB(layers, spielerRed, spielerBlue);
        
        WandBrett wandBrett = new WandBrett();
        wandB = new WandSteuerungB(layers, spielerRed, spielerBlue, wandBrett, walls);
        wandR = new WandSteuerungR(layers, spielerRed, spielerBlue, wandBrett, walls1);
        wandB.setAktiv(true);
        wandR.setAktiv(false);

        //Main Assembly
        frame.add(layers);
        frame.pack();
        frame.setVisible(true);//fenster sichtbar

    }
    
    //---------------------------------------------------------------------------------------------------------

    //Main Methods
    public static void main(String[] args)
    {
        GUI gui = new GUI();
        feldToCords(17);
        cordsToFeld(8, 1);
    }
    
    public static void feldToCords(int index)
    {
        int x;
        int y;
        x = index / 9;
        y = index % 9;
        System.out.println(x);
        System.out.println(y);
    }

    public static void cordsToFeld(int x, int y)
    {
        int index;
        index = y * 9 + x;
        System.out.println(index);
    }

    public static void pixelToFeld(int x, int y)
    {
        int spalte = (x - 252) / 64;
        int zeile = (y - 252) / 64;
    }

    public static void FeldToPixel(int index)
    {
        int Zeile = index / 9;
        int Spalte = index % 9;

        int x = 252 + Spalte * 64;
        int y = 252 + Zeile * 64;
    }

    public void placeWall()
    {
        
    }

}
//brd geht das jetzt?