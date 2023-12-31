import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.*;

public class main {

    MainPanel panel = new MainPanel();
    SoundDesign music = new SoundDesign();
    URL sound = getClass().getResource("resources/Free Music - "
                                        +
                                        "Tetris (Dark Version) (No Copyright Music).wav");

    public main() {
        JFrame frame = new JFrame("CBL tetris game", null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        KeyHandler handler = new KeyHandler();
        frame.add(panel);
        panel.addKeyListener(handler);
        frame.pack();
        frame.setLocationRelativeTo(null);

        // the buttons responsible for showing the score, starting the game,
        // showing the settings, displaying instructions and exiting the game
        // have their functionality adjusted
        JButton scoreboard = new JButton("Scoreboard");
        scoreboard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ScoreBoard scoreBoard = new ScoreBoard();
                scoreBoard.setVisible(true);
                scoreBoard.setLocationRelativeTo(null);
                scoreBoard.setPreferredSize(new Dimension(600, 500));
            }
        });

        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.runGame();
                start.setVisible(false);
            }
        });
        

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JButton instructions = new JButton("Instructions");

        instructions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                InstructionsFrame instructionsFrame = new InstructionsFrame();
                instructionsFrame.setVisible(true);
                instructionsFrame.setLocationRelativeTo(null);
                instructionsFrame.setPreferredSize(new Dimension(500, 500));
            }
        });

        //new panel responsible for storing all of the buttons responsible for adjusting the music
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
        
        JButton settingsButton = new JButton("Settings");
        settingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                volumeUp.setVisible(true);
                volumeDown.setVisible(true);
                silence.setVisible(true);
                back.setVisible(true);
            }
        });
        start.setFocusable(false);
        settingsButton.setFocusable(false);
        instructions.setFocusable(false);
        scoreboard.setFocusable(false);


        // assigning the correct functionality of buttons responsible for
        // adjusting the volume of music and turning off the settings
        volumeUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                music.upVolume();
            }
        });
        volumeDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                music.downVolume();
            }
        });
        silence.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                music.soundMute();
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

        //Adding the the text above the score to display it 
        JPanel scorePanel = new JPanel();
        scorePanel.setBackground(Color.blue);
        frame.add(scorePanel);
        scorePanel.setBounds(1090, 230, 200, 45);
        scorePanel.setVisible(true);
        JLabel score = new JLabel("Your Score: ");
        score.setForeground(Color.white);
        score.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        scorePanel.add(score);

        //Adding the panel which stores the variables that are responsible for displaying
        //the gui components pointing on how many blocks need to fall before the rotation occurs
        JPanel rotationPanel = new JPanel();
        rotationPanel.setBackground(Color.blue);
        frame.add(rotationPanel);
        rotationPanel.setBounds(260, 660, 310, 40);
        JLabel rotationNumber = new JLabel("Blocks until rotation:");
        rotationPanel.add(rotationNumber);
        rotationNumber.setForeground(Color.white);
        rotationNumber.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));

        // Panel responsible for storing all of the buttons
        JPanel settingsPanel = new JPanel();
        settingsPanel.setBounds(150, 300, 100, 200);
        settingsPanel.add(start);
        settingsPanel.add(settingsButton);
        settingsPanel.add(instructions);
        settingsPanel.add(scoreboard);
        settingsPanel.add(exitButton);
        
        settingsPanel.setBackground(Color.blue);
        frame.add(settingsPanel);
        
        // Setting the correct layout of layers while displaying the grid
        JLayeredPane layeredPane = frame.getLayeredPane();
        layeredPane.add(settingsPanel, Integer.valueOf(1));
        layeredPane.add(panel, 0);
        layeredPane.add(volumePanel, Integer.valueOf(2));
        layeredPane.add(scorePanel, Integer.valueOf(2));
        layeredPane.add(rotationPanel, Integer.valueOf(2));
        turnMusic(sound);

    }

    /**Method responsible for turning on the music when the applictation is launched.
     * It also applies the method loop which loops the music
     * 
     * @param url this is the parameter that refers to the location of the audio file.
     * 
     */
    public void turnMusic(URL url) {
        music.play(url);
        music.loop(url);

    }
    
    public static void main(String[] args) {
        new main();
       
    }
}


