import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class SoundDesign {
    Clip clip;
    float current = 0;
    float previous = 0;
    FloatControl fc;
    boolean mute = false;
    URL sound = getClass().getResource("resources/Free Music - "
                                        +
                                    "Tetris (Dark Version) (No Copyright Music).wav");

    public SoundDesign() {
        setFile(sound);
    }

    /** Setting the music file.
     * 
     * Setting the audio file which then can be adjusted by the FloatControl to set a specific
     * Volume of music
     * 
     * @param url the audio file with the music
     */
    public void setFile(URL url) {
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(sound);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception e) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "The game was unable to download all the music"
                                                 +
                                            "files, make sure to download all of the game files",
                                        "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    /** When the method is called the music starts playing.
     * 
     * 
     * @param url the audio file which is assigned URL as the parameter 
     *            inside the method is played when the method is called.
     */
    public void play(URL url) {
        clip.setFramePosition(0);
        clip.start();
    }

    public void loop(URL url) {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(URL url) {
        clip.stop();
    }

    /**Method responsible for muting and unmuting the music.
     * 
     * dependent on the value of boolean mute the the music
     * is either muted or when the method is called again 
     * the music is unmuted. 
     * 
     */
    public void soundMute() {
        if (mute) {
            current = previous;;
            fc.setValue(current);
            mute = false;
        } else {
            previous = current;
            current = -80.0f;
            fc.setValue(current);
            mute = true;
        }
    }

    /** Turning the music volume down.
     *
     * Volume of the music is lowered by 3.0f each time the 
     * method is called.
     * 
     */
    public void downVolume() {
        current -= 3.0f;
        fc.setValue(current);
    }

    /** Turning the music volume up.
     * 
     * Each time the method is called volume of the music
     * is increased by 3.0f.
     * 
     */
    public void upVolume() {
        current += 3.0f;
        fc.setValue(current);
    }
    
}
