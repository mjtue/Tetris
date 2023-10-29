import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**Class responsible for displaying the instructions for the user on how to play the game.
 * 
 */
public class InstructionsFrame extends JFrame {
    JPanel instructionsPanel = new JPanel();
    
    /**displaying the instructions for the user on how to play the game.
     * 
     * There is created a panel to which a label is added which contatins
     * the necessary information needed to play the game effectively. 
     * 
     */
    public InstructionsFrame() {
        setBackground(Color.gray);
        setSize(600, 500);
        setTitle("Instructions");
        instructionsPanel.setVisible(true);
        add(instructionsPanel);
        
        instructionsPanel.setLayout(new BorderLayout());
        instructionsPanel.setBackground(Color.gray);
        instructionsPanel.setLocation(100, 100);
        JLabel textLabel = new JLabel();
        instructionsPanel.add(textLabel);
        textLabel.setText("<html>In order to start the game click the 'start' button.<html>"
                            +
                           " To move the block use the 'a' key to move left and the"
                            +
                           " 'd' key to move right. Also by clicking 's' you can make a block"
                           + 
                           " immidietaly fall down the grid. You increase your score "
                           +
                           "each time you fill a full row with blocks. "
                           +
                           "However each five blocks that fall the whole board rotates by "
                           +
                           "90 degrees counterclockwise and the blocks that are rotated fall. "
                           +
                           "After losing, to try again click the 'r' button on your keyboard. "
                           +
                           "Additionally by clicking Scoreboard button you will see "
                           +
                           "five of your best scores.");
        textLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        
    }
}
