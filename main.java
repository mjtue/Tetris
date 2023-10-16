

import java.awt.BorderLayout;

import javax.swing.*;



public class main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("CBL tetris game", null);
        
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        MainPanel panel = new MainPanel();
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);

        Grid grid = new Grid();
        GameManager manager = new GameManager();

        manager.run(grid);
    }
}


