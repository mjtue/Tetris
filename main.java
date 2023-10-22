import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.*;



public class main {

    MainPanel panel = new MainPanel();
    SettingsFrame Music = new SettingsFrame();

    public main() {
        JFrame frame = new JFrame("CBL tetris game", null);
        
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLayeredPane layeredPane = frame.getLayeredPane();
        
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        JPanel settingsPanel = new JPanel();
        JButton start = new JButton("Start");
        JButton settingsButton = new JButton("Settings");
        JButton exitButton = new JButton("Exit");
        JButton instructions = new JButton("Instructions");

        settingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SettingsFrame settingsFrame = new SettingsFrame();
                settingsFrame.setVisible(true);
            }
        });
    
        settingsPanel.setBounds(150, 300, 100, 200);
        settingsPanel.add(start);
        settingsPanel.add(settingsButton);
        settingsPanel.add(instructions);
        settingsPanel.add(exitButton);
        
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.runGame();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        exitButton.setBounds(0, 0, 100, 100);
        settingsPanel.setBackground(Color.blue);
        frame.add(settingsPanel);
        
        layeredPane.add(settingsPanel, Integer.valueOf(1));
        layeredPane.add(panel, 0);
        
        panel.addKeyListener(panel);
        frame.addKeyListener(panel);
        URL sound = getClass().getResource("resources/Free Music - Tetris (Dark Version) (No Copyright Music).wav");
        turnMusic(sound);
    }
    public void turnMusic(URL url) {
        Music.setFile(url);
        Music.play(url);
        Music.loop(url);

    }
    
    public static void main(String[] args) {
        new main();
       
    }
}


