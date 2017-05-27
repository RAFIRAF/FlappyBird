package flappyBird;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Rafal on 02017-05-25.
 */
public class Renderer extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        FlappyBird.flappyBird.repaint(g);
    }
}
