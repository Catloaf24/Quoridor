import java.awt.*;
import javax.swing.*;

public class SpielerPanel extends JPanel {

    private Image backgroundImage;

    public SpielerPanel(String imagePath) {
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

