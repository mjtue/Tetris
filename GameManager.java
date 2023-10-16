import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GameManager {
    
    public void run (Grid grid, Graphics e) {
        grid.fillFirst();
        grid.fallingblocks();
        
    }
}
