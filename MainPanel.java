
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainPanel extends JPanel {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 300;
    public static final int SIZE = 30;

    
    public Grid panelGrid = new Grid();
    Timer timer = new Timer();

    public MainPanel() {
        this.setPreferredSize(new Dimension(1280, 720));
        this.setVisible(true);
        this.setBackground(Color.blue);
        this.setLayout(new BorderLayout());

        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);
        

    
    }

    @Override
    public void paintComponent (Graphics e){
        super.paintComponent(e);
        e.setColor(Color.black);
        e.fillRect(400, 40, 600, 300);

        e.setColor(Color.WHITE);

        for (int i = 0; i < 11; i++) {
            e.drawLine(400, SIZE * i+40, 1000, SIZE * i+40);
        }
        for (int i = 13; i < 34; i++) {
            e.drawLine(i*30+10, 40, i*30+10, 340);
        }
        
        e.setColor(Color.GREEN);
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Grid panelGrid = new Grid();
                boolean[][] board = panelGrid.getGrid();
                for (int i = 0; i < 10; i++) {
                    for (int k = 0; k < 20; k++) {
                        if (board[i][k]) {
                            e.fillRect(i*SIZE + 400, k*SIZE+  40, SIZE, SIZE);
                        }
                    }
                }
            }
        }, 0, 1000);


    }

}
