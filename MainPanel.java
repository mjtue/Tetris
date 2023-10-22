import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements KeyListener {
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;
    public static final int SIZE = 30;
    public static boolean downMove, leftMove, rightMove;
    private Timer loop;
    public boolean[][] grid;
    public boolean[][] currentBlock;
    public int g = 1;
    public int counter = 0;
    public int positionX = 10;
    public int positionY = 0;

    public MainPanel() {
        
        this.setPreferredSize(new Dimension(1280, 720));
        this.setVisible(true);
        this.setBackground(Color.blue);
        this.setLayout(new BorderLayout());
        
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);

        this.grid = new boolean[20][20];
        for (int i = 0; i < 20; i++) {
            for (int k = 0; k < 20; k++) {
                grid[i][k] = false;
            }
        }

        this.currentBlock = new boolean[2][3];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                currentBlock[i][j] = false;
            }
        }

        loop = new Timer();
        loop.schedule(new TimerTask() {

            @Override
            public void run() {
                positionY += 1;
                fillLower();
                print();
               
                repaint();
                
                if (leftMove) {
                    System.out.println("left");
                    moveLeft();
                    leftMove = false;
                }
                if (rightMove) {
                    System.out.println("right");
                    rightMove = false;
                }
            }

        }, 0, 200);

    }

    public void fullRow(){
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (!grid[i][j]) {
                    break;
                } else if (j == 19) {
                    reduce(i);
                }
            }
        }
    }

    public void reduce(int r){
        for(int i = r; i > 0; i--){
            for(int j = 0; j < 20; j++){
                grid[i][j] = grid[i-1][j];
            }
        }
        for(int j = 0; j < 20; j++){
            grid[0][j] = false;
        }
    }

    public boolean[][] turn(){
        boolean[][] siteGrid = new boolean[20][20];
        for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    siteGrid[i][j] = grid[j][i];
                }
            }
        return fall(siteGrid);
    }

    public boolean[][] fall(boolean[][] fallingGrid){
        int zeros = 0;
        boolean[][] returnGrid = new boolean[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                returnGrid[i][j] = false;
            }
        }
        for (int j = 0; j < 20; j++) {
            for (int i = 0; i < 20; i++)
            {
                if(!fallingGrid[i][j]){
                    zeros++;
                }
            }
            for(int i = zeros; i < 20; i++){
                returnGrid[i][j] = true;
            }
            zeros = 0;
        }
        
        return returnGrid;
    }
    
    public void fillFirst() {
        if (levelFree()) {
            positionX = 10;
            positionY = 0;
            counter++;
            counter %= 5;
            block random = new block();
            boolean[][] anotherOne = random.selectRandom();
            this.currentBlock = anotherOne;
            for (int i = 0; i < 2; i++) {
                for (int k = 0; k < 3; k++) {
                    grid[i][(WIDTH / 2) - 1 + k] = anotherOne[i][k];
                }
            }
        
        }

    }
    private boolean levelFree() {
        for (int i = 0; i < 3; i++) {
            if (grid[g + 1][(WIDTH / 2) - 1 + i]) {
                return false;
            }
        }
        return true;
    }
    private void fillLower() {
        
        if (levelFree()) {
            for (int i = 0; i < 2; i++){
                for (int j = 0; j < 3; j++){
                    grid[g + i - 1][(WIDTH / 2) - 1 + j] = false;
                }
            }

            for (int i = 0; i < 2; i++) {
                for (int k = 0; k < 3; k++) {
                    grid[g + i][(WIDTH / 2) - 1 + k] = currentBlock[i][k];   
                }
                    
            } 
            
            g++;
            if (g == 19 || !levelFree()){
                g = 1;
                fullRow();
                if(counter == 0){
                    grid = turn();
                    fullRow();
                }
                fillFirst();
            }
        } else {
            System.out.println("You lost");
        }
    }
    public void print() {
        for (int i = 2; i < 20; i++){
            for (int k = 0; k < 20; k++){
                if (grid[i][k] == false) {
                    System.out.print("0");
                } else {
                    System.out.print("1");
                }
                
            }
            System.out.println();
        }
        System.out.println("");
    }


    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

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

    public void moveLeft () {
        if (!(positionX==0)){
            for (int i = 0; i < 2; i++) {
                for (int k = 0; k < 3; k++) {
                    grid[positionY][positionX  + k-1] = currentBlock[i][k];
                    grid[positionY+1][positionX + k-1] = false;
                }
                    
            } 
        }
        positionX -=1;
    }

    public void moveRight () {
        if (!(positionX==20)) {

        }
    }
    



    @Override
    protected void paintComponent (Graphics e){
        super.paintComponent(e);
        e.setColor(Color.black);
        e.fillRect(400, 40, 600, 600);

        e.setColor(Color.WHITE);

        for (int i = 0; i < 21; i++) {
            e.drawLine(400, SIZE * i+40, 1000, SIZE * i+40);
        }
        for (int i = 13; i < 34; i++) {
            e.drawLine(i*30+10, 40, i*30+10, 640);
        }
        
        e.setColor(Color.GREEN);
                
        for (int i = 0; i < 20; i++) {
            for (int k = 0; k < 20; k++) {
                if (grid[i][k]) {
                    e.fillRect(k * SIZE + 400, i * SIZE+40, SIZE, SIZE);
                } 
            }
        }
        Graphics2D g = (Graphics2D) e;
        g.setColor(Color.white);
        g.setStroke(new BasicStroke(2));
        for (int i = 0; i < 20; i++) {
            for (int k = 0; k < 20; k++) {
                if (grid[i][k]) {
                    g.drawLine(k * SIZE + 400, i * SIZE + 40, k * SIZE + 400, i * SIZE + 70);
                    g.drawLine(k * SIZE + 430, i * SIZE + 40, k * SIZE + 430, i * SIZE + 70);
                    g.drawLine(k * SIZE + 400, i * SIZE + 40, k * SIZE + 430, i * SIZE + 40);
                    g.drawLine(k * SIZE + 400, i * SIZE + 70, k * SIZE + 430, i * SIZE + 70);
                } 
            }
        } 
    }
    

}
