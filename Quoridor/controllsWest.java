import java.awt.*;
import javax.swing.*;

public class controllsWest {

    public void ControllsWest()
    {
        JPanel controllsWest = new JPanel();
        controllsWest.setLayout(new GridLayout(3,1));

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

        controllsWest.add(fillBMs);
        controllsWest.add(buttonManager);
        controllsWest.add(fillBMn);
    }
}
