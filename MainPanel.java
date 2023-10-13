
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainPanel extends JPanel {

    Thread gameThread;

    public MainPanel() {
        this.setPreferredSize(new Dimension(1280, 720));
        this.setVisible(true);
        this.setBackground(Color.blue);
        this.setLayout(new BorderLayout());
        
    }

    @Override
    public void paintComponent (Graphics e){
        super.paintComponent(e);
        e.setColor(Color.black);
        e.fillRect(500, 20, 300, 400);
    }

}
