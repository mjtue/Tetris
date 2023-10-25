import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;


public class KeyHandler implements KeyListener {

    public static boolean leftMove;
    public static boolean rightMove;
    public static boolean downMove;

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            leftMove = true;
        }
        if (key == KeyEvent.VK_D) {
            rightMove = true;
        }
        if (key == KeyEvent.VK_S) {
            downMove = true;
        }
        

    }

    @Override
    public void keyReleased(KeyEvent e) {
       
    }
    
}
