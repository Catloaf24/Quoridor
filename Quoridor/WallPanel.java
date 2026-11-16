import java.awt.*;
import javax.swing.*;

public class WallPanel extends JPanel {

    private Image backgroundImage;

    public WallPanel(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
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

