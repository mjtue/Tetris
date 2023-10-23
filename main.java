import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.*;



public class main {

    MainPanel panel = new MainPanel();
    SoundDesign Music = new SoundDesign();
    URL sound = getClass().getResource("resources/Free Music - Tetris (Dark Version) (No Copyright Music).wav");

    public main() {
        JFrame frame = new JFrame("CBL tetris game", null);

        KeyHandler handler = new KeyHandler();
        
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        
        frame.add(panel);
        panel.addKeyListener(handler);
        frame.addKeyListener(handler);
        frame.pack();
        frame.setLocationRelativeTo(null);

        JPanel settingsPanel = new JPanel();
        JButton start = new JButton("Start");
        JButton settingsButton = new JButton("Settings");
        JButton exitButton = new JButton("Exit");
        JButton instructions = new JButton("Instructions");

        JPanel volumePanel = new JPanel();
        volumePanel.setBounds(62, 258, 300, 40);
        volumePanel.setBackground(Color.blue);
        JButton volumeUp = new JButton("+");
        JButton volumeDown = new JButton("-");
        JButton silence = new JButton("Mute the Music");
        JButton back = new JButton("back");

        volumePanel.add(volumeUp);
        volumePanel.add(volumeDown);
        volumePanel.add(silence);
        volumePanel.add(back);
        volumeUp.setVisible(false);
        volumeDown.setVisible(false);
        silence.setVisible(false);
        back.setVisible(false);


        frame.add(volumePanel);
        

        
        start.setFocusable(false);
        settingsButton.setFocusable(false);
        instructions.setFocusable(false);

        settingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                volumeUp.setVisible(true);
                volumeDown.setVisible(true);
                silence.setVisible(true);
                back.setVisible(true);
            }
        });
        volumeUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Music.upVolume();
            }
        });
        volumeDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Music.downVolume();
            }
        });
        silence.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Music.SoundMute();
            }
        });
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                volumeUp.setVisible(false);
                volumeDown.setVisible(false);
                silence.setVisible(false);
                back.setVisible(false);
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

        JLayeredPane layeredPane = frame.getLayeredPane();
        layeredPane.add(settingsPanel, Integer.valueOf(1));
        layeredPane.add(panel, 0);
        layeredPane.add(volumePanel, Integer.valueOf(2));
        
        
        turnMusic(sound);
    }
    public void turnMusic(URL url) {
        Music.play(url);
        Music.loop(url);

    }
    
    public static void main(String[] args) {
        new main();
       
    }
}


