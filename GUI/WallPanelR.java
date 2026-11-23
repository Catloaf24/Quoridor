import java.awt.*;
import javax.swing.*;

public class WallPanelR extends JPanel {

    private Image backgroundImage;

    public WallPanelR(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    public void rotateWall()
    {
        backgroundImage = new ImageIcon("WallRHor.png").getImage();
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

