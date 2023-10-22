import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SettingsFrame extends JFrame  {
    Clip clip;
    Float Sound;
    Float previous;
    FloatControl fc;
    boolean mute = false;

    public SettingsFrame() {
        setTitle("Settings");
        setSize(300, 300);
        setLocationRelativeTo(null);

        JButton volumeUp = new JButton("+");
        JButton volumeDown = new JButton("-");
        JButton silence = new JButton("Mute the Music");
        JButton back = new JButton("back");
        JPanel volumePanel = new JPanel();

        add(volumePanel);
        volumePanel.add(volumeUp);
        volumePanel.add(volumeDown);
        volumePanel.add(silence);
        volumePanel.add(back);

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


    }

    public void setFile(URL url) {
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(sound);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        }
        catch (Exception e) {

        }
        
    }
    public void play (URL url) {
        clip.setFramePosition(0);
        clip.start();
    }
    public void loop (URL url) {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop (URL url) {
        clip.stop();
    }
}
