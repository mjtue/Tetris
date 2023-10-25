import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainPanel extends JPanel {
    public int position = 10;
    public static final int SIZE = 30;
    long highscore = 0;
    private Timer loop;
    public boolean[][] grid;
    public boolean[][] currentBlock;
    public int g = 1;
    public int counter = 0;
    public boolean currentBlockKindR = false;
    public boolean currentBlockKindL = false;
    public long score = 0;
    JLabel scoreField = new JLabel("0", SwingConstants.CENTER);

    public MainPanel() {
        
        this.setPreferredSize(new Dimension(1280, 720));
        this.setVisible(true);
        this.setBackground(Color.blue);
        this.setLayout(new BorderLayout());
        
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);

        scoreField.setForeground(Color.white);
        add(scoreField, BorderLayout.EAST);
        scoreField.setFont(new Font("Serif", Font.PLAIN, 60));

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

    }
    public void runGame() {
        
        loop = new Timer();
        loop.schedule(new TimerTask() {

            @Override
            public void run() {
                fillLower();
                repaint();
                
                if (KeyHandler.leftMove) {
                    
                    System.out.println("left");
                    if(leftFree()){
                        grid[g][position + 1] = false;
                        if(currentBlockKindR && g > 0){
                            grid[g - 1][position + 1] = false;
                        }
                        position--;
                    }
                    KeyHandler.leftMove = false;
                }
                if (KeyHandler.rightMove) {
                    System.out.println("right");
                    if(rightFree()){
                        grid[g][position - 1] = false;
                        if(currentBlockKindL && g > 0){
                            grid[g - 1][position - 1] = false;
                        }
                        position++;
                    }
                    KeyHandler.rightMove = false;
                }
                if (KeyHandler.downMove) {
                    System.out.println("down");
                    KeyHandler.downMove = false;
                    if(g > 0){
                        putDown();
                        g = 1;
                        fullRow();
                        if(counter == 0){
                            grid = turn();
                            fullRow();
                        }
                        position = 10;
                        fillFirst();
                    }
                }
            }

        }, 0, 200);

    }

    public void putDown(){
        boolean freeDown = true;
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 3; j++){
                if(currentBlock[i][j]){
                    grid[g + i - 1][position + j - 1] = false;
                }
            }
        }
        for(int i = g + 1; g < 20; g++){
            for(int j = -1; j < 2; j++){
                if(grid[g][position + j]){
                    freeDown = false;
                    break;
                }
            }
            if( !freeDown ){
                break;
            }
        }
        g--;
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 3; j++){
                if(currentBlock[i][j]){
                    grid[g + i - 1][position + j - 1] = true;
                }
            }
        }
    }
    public boolean leftFree(){
        if(position <= 1 || g == 0){
            return false;
        } else if(grid[g][position - 2] || grid[g + 1][position - 2]){ 
            return false;
        } else {
            return true;
        }
    }

    public boolean rightFree(){
        if(position >= 18 || g == 0){
            return false;
        } else if(grid[g][position + 2] || grid[g + 1][position + 2]){ 
            return false;
        } else {
            return true;
        }
    }
    public void fullRow(){
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (!grid[i][j]) {
                    break;
                } else if (j == 19) {
                    score += 100;
                    String str = "";
                    scoreField.setText(str + score);
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
            counter++;
            counter %= 5;
            Block random = new Block();
            boolean[][] anotherOne = random.selectRandom();
            this.currentBlock = anotherOne;
            if(anotherOne[0][0]){
                currentBlockKindL = true;
            }
            if(anotherOne[0][2]){
                currentBlockKindR = true;
            }
            for (int i = 0; i < 2; i++) {
                for (int k = 0; k < 3; k++) {
                    grid[i][(position) - 1 + k] = anotherOne[i][k];
                }
            }
        
        }

    }
    private boolean levelFree() {
        for (int i = 0; i < 3; i++) {
            if (grid[g + 1][(position) - 1 + i]) {
                return false;
            }
        }
        return true;
    }
    private void fillLower() {
        if (levelFree()) {
            if(g < 19){
                for (int i = 0; i < 2; i++){
                    for (int j = 0; j < 3; j++){
                        grid[g + i - 1][position - 1 + j] = false;
                    }
                }
                for (int i = 0; i < 2; i++) {
                    for (int k = 0; k < 3; k++) {
                        grid[g + i][(position) - 1 + k] = currentBlock[i][k];   
                    }
                        
                } 
            }
            g++;
            if (g >= 19 || !levelFree()){
                g = 1;
                fullRow();
                if(counter == 0){
                    grid = turn();
                    fullRow();
                }
                position = 10;
                fillFirst();
            }
        } else {
            score = 0;
            counter = 0;
            cleanGrid();
            System.out.println("You lost");
            fillFirst();
        }
    }
 
    public void cleanGrid(){
        for(int i = 0;  i < 20; i++){
            for(int j = 0; j < 20; j++){
                grid[i][j] = false;
            }
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
                    e.fillRect(k * SIZE + 400, i * SIZE + 40, SIZE, SIZE);
                } 
            }
        }
        Graphics2D d = (Graphics2D) e;
        d.setStroke(new BasicStroke(3));
        d.setColor(Color.white);
        for (int i = 0; i < 20; i++) {
            for (int k = 0; k < 20; k++) {
                if (grid[i][k]) {
                    e.drawLine(k * SIZE + 400, i * SIZE + 40, k * SIZE + 400, i * SIZE + 70);
                    e.drawLine(k * SIZE + 430, i * SIZE + 40, k * SIZE + 430, i * SIZE + 70);
                    e.drawLine(k * SIZE + 400, i * SIZE + 40, k * SIZE + 430, i * SIZE + 40);
                    e.drawLine(k * SIZE + 400, i * SIZE + 70, k * SIZE + 430, i * SIZE + 70);
                    
                } 
            }
     
        }
        e.drawLine(1100, 315, 1300, 315);
        e.drawLine(1100, 315, 1100, 395);
        e.drawLine(1100, 395, 1300, 395);
        d.setColor(Color.black);
        d.fillRect(1100, 315, 200, 80);
    }

}
