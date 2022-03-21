package view;

import javax.swing.*;
import java.awt.*;

public class JPanelBackground extends JPanel {
    private final Image img;

    public JPanelBackground(String img) {
        this(new ImageIcon(img).getImage());
    }

    public JPanelBackground(Image img) {
        this.img = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        int width = this.getSize().width;
        int height = this.getSize().height;
        g.drawImage(img, 0, 0,width,height, null);
    }
}

