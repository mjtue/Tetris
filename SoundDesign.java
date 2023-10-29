import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * class is responsible for putting sound to the game;
 */
public class SoundDesign {
    Clip clip;
    float current = 0;
    float previous = 0;
    FloatControl fc;
    boolean mute = false;
    URL sound = getClass().getResource("resources/Free Music - Tetris (Dark Version) (No Copyright Music).wav");

    public SoundDesign() {
        setFile(sound);
    }

    public void setFile(URL url) {
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(sound);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        }
        catch (Exception e) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame,"The game was unable to download all the music files, make sure to download all of the game files",
               "ERROR", JOptionPane.ERROR_MESSAGE);
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
    public void SoundMute() {
        if (mute == false) {
            previous = current;
            current = -80.0f;
            fc.setValue(current);
            mute = true;
        } else if (mute == true) {
            current = previous;;
            fc.setValue(current);
            mute = false;
        }
    }
    public void downVolume() {
        current -= 3.0f;
        if (current < - 80.0f) {
            current = -80.0f;
        }
        fc.setValue(current);
    }
    public void upVolume() {
        current += 3.0f;
        if (current > 6.0f) {
            current = 6.0f;
        }
        fc.setValue(current);
    }
    
}
