import javax.swing.*;

public class main {



    public static void main(String[] args) {

        JFrame frame = new JFrame("new tetris game", null);
        
        
        frame.setLocationRelativeTo(null);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JButton button = new JButton("start", null);

        button.setBounds(100, 100, 50, 50);
        

        frame.add(panel);
        panel.add(button);


    }
}


