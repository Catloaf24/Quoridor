import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Spielfelder extends JPanel {

    Image image;

    Spielfelder() {
        image = new ImageIcon("Quoridor/SpielfeldBildtest.png").getImage();
        setPreferredSize(new Dimension(64, 64));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
}