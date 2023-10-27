import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainPanel extends JPanel {
    public static final int SIZE = 30;
    public boolean[][] grid;
    public boolean[][] currentBlock;
    public int g = 1;
    public int counter = 0;
    public int position = 10;
    public int untilRotation = 5;
    public boolean currentBlockKindR = false;
    public boolean currentBlockKindL = false;
    public int score = 0;
    public boolean lost = false;
    public boolean allowuntilrotation = false;
    private Timer restartLoop;
    private Timer loop;
    File scoreFile = new File("highscores.txt");
    JLabel scoreField = new JLabel("0", SwingConstants.CENTER);
    JLabel rotation = new JLabel("5", SwingConstants.CENTER);
    

    public MainPanel() {
        
        this.setPreferredSize(new Dimension(1280, 720));
        this.setVisible(true);
        this.setBackground(Color.blue);
        this.setLayout(new BorderLayout());
        
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);
        restartGame();
        
        scoreField.setForeground(Color.white);
        add(scoreField, BorderLayout.EAST);
        scoreField.setFont(new Font("Serif", Font.PLAIN, 60));

        rotation.setForeground(Color.white);
        add(rotation, BorderLayout.PAGE_END);
        rotation.setFont(new Font("Serif", Font.PLAIN, 60));


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
                if (lost) {
                    loop.cancel();
                } 

                if (KeyHandler.leftMove) {
                    if (leftFree()) {
                        grid[g][position + 1] = false;
                        if (currentBlockKindR && g > 0) {
                            grid[g - 1][position + 1] = false;
                        }
                        position--;
                    }
                    KeyHandler.leftMove = false;
                }

                if (KeyHandler.rightMove) {
                    if (rightFree()) {
                        grid[g][position - 1] = false;
                        if (currentBlockKindL && g > 0) {
                            grid[g - 1][position - 1] = false;
                        }
                        position++;
                    }
                    KeyHandler.rightMove = false;
                }

                if (KeyHandler.downMove && !lost ) {
                    KeyHandler.downMove = false;
                    if (g > 0) {
                        putDown();
                        g = 1;
                        fullRow();
                        if (counter == 0) {
                            grid = turn();
                            fullRow();
                        }
                        position = 10;
                        fillFirst();
                        untilRotation -= 1;
                        rotation.setText(String.valueOf(untilRotation));
                    }
                } else {
                    KeyHandler.downMove = false;
                }

                
            }

        }, 0, 200);
    }
    public void restartGame() {
        restartLoop = new Timer();
        restartLoop.schedule(new TimerTask() {
            @Override
            public void run() {
                if (KeyHandler.retryGame && lost) {
                    KeyHandler.retryGame = false;
                    lost = false;
                    scoreField.setText("0");
                    untilRotation = 5;
                    rotation.setText("5");
                    KeyHandler.downMove = false;
                    KeyHandler.leftMove = false;
                    KeyHandler.rightMove = false;
                    runGame();
                    cleanGrid();
                    fillFirst();
                    
                } else {
                    KeyHandler.retryGame = false;
                }
            }
        }, 0, 1);
    }

    public void addHighscore(long score) {
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(scoreFile, true));
            output.newLine();
            output.append("" + score);
            output.close();

        } catch (IOException ex1) {
            System.out.printf("ERROR writing score to file: %s\n", ex1);
        }
       
    }
    
    public void fillFirst() {
        if (levelFree()) {
            counter++;
            counter %= 5;
            Block random = new Block();
            boolean[][] anotherOne = random.selectRandom();
            this.currentBlock = anotherOne;
            if (anotherOne[0][0]) {
                currentBlockKindL = true;
            }
            if (anotherOne[0][2]) {
                currentBlockKindR = true;
            }
            for (int i = 0; i < 2; i++) {
                for (int k = 0; k < 3; k++) {
                    grid[i][(position) - 1 + k] = anotherOne[i][k];
                }
            }
        
        }

    }

    private void fillLower() {
        if (levelFree() && !lost) {
            if (g < 19) {
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
                if (counter == 0) {
                    grid = turn();
                    fullRow();
                }
                position = 10;
                fillFirst();
                untilRotation -= 1;
                rotation.setText(String.valueOf(untilRotation));
            }
        } else {
            addHighscore(score);
            score = 0;
            counter = 0;
            lost = true;
        }
    }
    
    public boolean[][] turn() {
        boolean[][] siteGrid = new boolean[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                siteGrid[i][j] = grid[j][i];
           }
        }
        if (allowuntilrotation) {
            untilRotation = 6;
            rotation.setText(String.valueOf(untilRotation));
        }
        allowuntilrotation = true;
        
        
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
                if (!fallingGrid[i][j]) {
                    zeros++;
                }
            }
            for (int i = zeros; i < 20; i++) {
                returnGrid[i][j] = true;
            }
            zeros = 0;
        }

        return returnGrid;
    }

    public void putDown(){
        boolean freeDown = true;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                if (currentBlock[i][j]) {
                    grid[g + i - 1][position + j - 1] = false;
                }
            }
        }
        for (int i = g + 1; g < 20; g++) {
            for (int j = -1; j < 2; j++) {
                if (grid[g][position + j]) {
                    freeDown = false;
                    break;
                }
            }
            if (!freeDown) {
                break;
            }
        }
        g--;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                if (currentBlock[i][j]) {
                    grid[g + i - 1][position + j - 1] = true;
                }
            }
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

    public void reduce(int r) {
        for (int i = r; i > 0; i--) {
            for (int j = 0; j < 20; j++) {
                grid[i][j] = grid[i - 1][j];
            }
        }
        for (int j = 0; j < 20; j++) {
            grid[0][j] = false;
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
    
    public boolean leftFree() {
        if (position <= 1 || g == 0) {
            return false;
        } else if (grid[g][position - 2] || grid[g + 1][position - 2]) { 
            return false;
        } else {
            return true;
        }
    }

    public boolean rightFree(){
        if (position >= 18 || g == 0) {
            return false;
        } else if (grid[g][position + 2] || grid[g + 1][position + 2]) { 
            return false;
        } else {
            return true;
        }
    }
    
    public void cleanGrid(){
        for (int i = 0;  i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                grid[i][j] = false;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics e) {
        super.paintComponent(e);
        e.setColor(Color.black);
        e.fillRect(400, 40, 600, 600);

        e.setColor(Color.WHITE);

        for (int i = 0; i < 21; i++) {
            e.drawLine(400, SIZE * i + 40, 1000, SIZE * i + 40);
        }
        for (int i = 13; i < 34; i++) {
            e.drawLine(i * 30 + 10, 40, i * 30 + 10, 640);
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
        e.drawLine(1100, 285, 1300, 285);
        e.drawLine(1100, 285, 1100, 365);
        e.drawLine(1100, 365, 1300, 365);
        d.setColor(Color.black);
        d.fillRect(1100, 285, 200, 80);

        d.setColor(Color.black);
        d.fillRect(590, 650, 95, 80);
        e.setColor(Color.white);
        e.drawLine(590, 650, 590, 730);
        e.drawLine(590, 650, 685, 650);
        e.drawLine(685, 650, 685, 730);
    }

}
