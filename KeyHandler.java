import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;


public class KeyHandler implements KeyListener {

    public static boolean downMove, leftMove, rightMove;

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_DOWN) {
            downMove = true;
        }
        if (key == KeyEvent.VK_LEFT) {
            leftMove = true;
        }
        if (key == KeyEvent.VK_RIGHT) {
            rightMove = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
       
    }
    
}
