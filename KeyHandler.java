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
            System.out.println("down");
        }
        if (key == KeyEvent.VK_LEFT) {
            leftMove = true;
            System.out.println("left");
        }
        if (key == KeyEvent.VK_RIGHT) {
            rightMove = true;
            System.out.println("right");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
       
    }
    
}
