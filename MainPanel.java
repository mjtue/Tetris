import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainPanel extends JPanel {
    public static final int WIDTH = 20;
    public static final int HEIGHT = 10;
    public static final int SIZE = 30;
    private Timer loop;
    public boolean[][] grid;
    public boolean[][] currentBlock;
    public int g = 1;


    public MainPanel() {
        
        this.setPreferredSize(new Dimension(1280, 720));
        this.setVisible(true);
        this.setBackground(Color.blue);
        this.setLayout(new BorderLayout());
        
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);

        this.grid = new boolean[10][20];
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 20; k++){
                grid[i][k] = false;
            }
        }
        this.currentBlock = new boolean[2][3];
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 3; j++){
                currentBlock[i][j] = false;
            }
        }
        loop = new Timer();
        loop.schedule(new TimerTask() {

            @Override
            public void run() {
                fillLower();
                print();
                repaint();
            }

        }, 0, 500);

    }

    public void fillFirst() {
        if (levelFree()) {
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
            if (g == 9 || !levelFree()){
                g = 1;
                fillFirst();
            }
        } else {
            System.out.println("You lost");
        }
    }
    public void print() {
        for (int i = 0; i < 10; i++){
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
    protected void paintComponent (Graphics e){
        super.paintComponent(e);
        e.setColor(Color.black);
        e.fillRect(400, 40, 600, 300);

        e.setColor(Color.WHITE);

        for (int i = 0; i < 11; i++) {
            e.drawLine(400, SIZE * i+40, 1000, SIZE * i+40);
        }
        for (int i = 13; i < 34; i++) {
            e.drawLine(i*30+10, 40, i*30+10, 340);
        }
        
        e.setColor(Color.GREEN);
                
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 20; k++) {
                if (grid[i][k]) {
                    e.fillRect(k*SIZE + 400, i*SIZE+40, SIZE, SIZE);
                } 
            }
        } 
    }
    

}
