import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScoreBoard extends JFrame {
    File file = new File("highscores.txt");
    ArrayList<Integer> scores = new ArrayList<>();

    public ScoreBoard() {
        setSize(500, 500);
        setTitle("Scoreboard");
        JPanel panel = new JPanel();
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                    add(new JPanel() {
                    @Override
                    public Dimension getPreferredSize() {
                            return new Dimension(100, 50);
                        }
                    });
                add(panel, BorderLayout.CENTER);
            } 
        });
        panel.setLayout(new GridLayout(0, 1));
        

        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.readLine();
            String line = reader.readLine();
            
            int counter = 0;
            while (line!=null) {
                scores.add(Integer.parseInt(line.trim()));
                line = reader.readLine();
                counter += 1;
            }
        } catch (IOException ex) {
            System.out.println("ERROR while reading the files from the file");
        }

        for (int i = 0; i < scores.size(); i++) {
            for (int current = 0; current < scores.size() - 1 - i; current++) {
                if (scores.get(current) < scores.get(current + 1)) {
                    int temp = scores.get(current);
                    scores.set(current, scores.get(current + 1));
                    scores.set(current + 1, temp);
                }
            }
        }
        if (scores.size() < 5) {
            for (int i  = 0; i < scores.size(); i++) {
                String score = String.valueOf(scores.get(i));
                JLabel label  = new JLabel((i + 1) + ". " + score);
                label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
                panel.add(label);   
            }
        } else {
            for (int i  = 0; i < 5; i++) {
                String score = String.valueOf(scores.get(i));
                JLabel label  = new JLabel((i + 1) + ". " + score);
                label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
                panel.add(label);   
            }
        }
    }

    
}
