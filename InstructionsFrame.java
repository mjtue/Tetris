import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InstructionsFrame extends JFrame {
    JPanel instructionsPanel = new JPanel();
    
    public InstructionsFrame() {
        setBackground(Color.gray);
        setSize(600, 500);
        instructionsPanel.setVisible(true);
        add(instructionsPanel);
        
        instructionsPanel.setLayout(new BorderLayout());
        instructionsPanel.setBackground(Color.gray);
        instructionsPanel.setLocation(100, 100);
        JLabel textLabel = new JLabel();
        instructionsPanel.add(textLabel);
        textLabel.setText("<html>In order to start the game click the 'start' button.<html>"
                            +
                           " in order to move the block use the 'a' key to move left and the"
                            +
                           "'d' button to move right. You increase your score "
                           +
                           "each time you fill a row with blocks. "
                           +
                           "However each five blocks that fall the whole board rotates by "
                           +
                           "90 degrees counterclockwise and the blocks fall "
                           +
                           "Although it might be confusing at first its easy to get used to");
        
    }
}
