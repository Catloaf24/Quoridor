import java.awt.*;
import javax.swing.*;

public class WallPanelB extends JPanel {

    private Image backgroundImage;

    public WallPanelB(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    public void rotateWall()
    {
        backgroundImage = new ImageIcon("WallBHor.png").getImage();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(
            backgroundImage,
            0, 0,
            getWidth(), getHeight(),
            this
        );
    }
}

