import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedWriter;
import java.io.File;
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
    public int untilRotation = 6;
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

    /**Method responsible for running the game.
     * 
     * After calling this method the loop responsible for blocks falling is
     * initiated. Each time the loop is passed the repaint() and fillLower() methods
     * are called which result in checking which blocks in the grid are occupied (elements
     * in the grid array which are true) and painting a rectangle in an appropriate space as 
     * well as calling the methods responsible for moving the blocks left right and down.
     * 
     * Then we check if any of keyboard's buttons was pressed;
     * If 'S' was presses we are taking block to the bottom of the grid;
     * if 'A' was pressed we are taking block one square left;
     * if 'D' was pressed we are taking block one square right;
     */
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

                if (KeyHandler.downMove && !lost) {
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

                
            }

        }, 0, 200);
    }

    /** After losing the game, when player click the 'r' key the game restarts.
     * 
     * There is another timer (restartLoop) created which has very low period.
     * This method is called in the constructor as the loop never stops.
     * The functionality of the method essentially lies in the fact that after losing the game
     * the main loop (loop) responsible for the game running is canceled. Then when variable
     * lost is true (meaning that the game was lost) the player is enabled to click the
     * restart button which results in calling the runGame() method, setting score to 0,
     * clearing the grid etc.
     * 
     */
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

    /**Adding the highscore after losing a game to the highscores.txt file.
     * 
     * After losing the game this method is invoked and it is responsible for adding
     * the parameter score to a new line in the highscores.txt file.
     * 
     * @param score  stores the value of the player's score after losing the game.
     * 
     */
    public void addHighscore(int score) {
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(scoreFile, true));
            output.newLine();
            output.append("" + score);
            output.close();

        } catch (IOException ex1) {
            System.out.printf("ERROR writing score to file: %s\n", ex1);
        }
       
    }

    /* This method is resposible for uploading block to the grid;
     * We are doing only when under our block we have empty space;
     * We are checking this in method levelFree();
     * We add one to counter, which is responsible for showing 
     * how many blocks we are having to grid rotation;
     * currentBlockL and currentBlockR are saying us 
     * if we block is full in top left or top right corner;
     * In last part of the method we are just uplaoding block to the grid;
     */
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

    /*
     * This method is responsible for putting block one level lower;
     * We are doing method only when there are no blocks under our block and we did not lose the game;
     * We are checking condition in method levelFree() and in boolean lost;
     * Next we are doing to loops but only when we are not in the lowest level;
     * In these 2 loops we are puting block one level lower;
     * Then we are checking if we are on the lowest level or under our block there is no free space;
     * If conditions are true we put our level on top;
     * We are checking if any row is full;
     * If counter is equal zero we turn the grid;
     * Then we put the next block in method fillFirst;
     * 
     * If the first condition is false it means that we lost;
     */
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
                untilRotation -=1;
                rotation.setText(String.valueOf(untilRotation));
            }
        } else {
            addHighscore(score);
            score = 0;
            counter = 0;
            lost = true;
        }
    }
    
    /*
     * This method is responsible for rotating the grid 90 degrees;
     * Method swap square with coordinates (i, j) to square with coordinates (j, i);
     * After doing this we put every block on the ground in method fall;
     */
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

    /*
     * In this method we put block down if under it is free space;
     * Firstly we are counting how many free squares are in column j;
     * Then we fulfill on the bottom 20 - zeros squares in column j;
     */
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

    /*
     * In this method we are puting the block directly on the bottom;
     * In first loop we are canceling the block;
     * Then we are checking every row if the 3 squares of block's coordinates are empty;
     * If at least one square is full we put there the block;
     */
    public void putDown(){
        boolean freeDown = true;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                if (currentBlock[i][j]) {
                    grid[g + i - 1][position + j - 1] = false;
                }
            }
        }
        for (g = g + 1; g < 20; g++) {
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
    
    /*
     * This method checks if any row is full;
     * To do so method if there exist a row with 19 occupied squares;
     * If the row is full we add 100 points to our score and we cancel the row in method reduce;
     */
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

    /*
    * This method is resposible for canceling row r;
    * Method cancel blocks from row r and puts there blocks from row r-1
    */
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
    
    /*
     * This method checks if under current block is free space;
     * To do so method is checking free squares under the current block;
     */
    private boolean levelFree() {
        for (int i = 0; i < 3; i++) {
            if (grid[g + 1][(position) - 1 + i]) {
                return false;
            }
        }
        return true;
    }
    
    /**Checks whether the current falling block can be moved to the right.
     * 
     * @return true if a block can be moved right or false if it cannot.
     * 
     */
    public boolean leftFree() {
        if (position <= 1 || g == 0) {
            return false;
        } else if (grid[g][position - 2] || grid[g + 1][position - 2]) { 
            return false;
        } else {
            return true;
        }
    }

    /**Checks whether the current falling block can be moved to the right.
     * 
     * @return returns true if a block can be moved right and false if it cannot.
     */
    public boolean rightFree() {
        if (position >= 18 || g == 0) {
            return false;
        } else if (grid[g][position + 2] || grid[g + 1][position + 2]) { 
            return false;
        } else {
            return true;
        }
    }
    
    /**Assigns all of the values of grid array to false.
     * 
     * Essentially it is responsible for clearing the grid of all the blocks
     * which are being currently present inside the grid 
     * 
     */
    public void cleanGrid() {
        for (int i = 0;  i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                grid[i][j] = false;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics e) {
        super.paintComponent(e);


        // drawing the grid
        e.setColor(Color.black);
        e.fillRect(400, 40, 600, 600);
        e.setColor(Color.WHITE);
        for (int i = 0; i < 21; i++) {
            e.drawLine(400, SIZE * i + 40, 1000, SIZE * i + 40);
        }
        for (int i = 13; i < 34; i++) {
            e.drawLine(i * 30 + 10, 40, i * 30 + 10, 640);
        }
        
        // draw the rectangle in the location inside the grid which is occupied
        // (where the value of grid array is true)
        e.setColor(Color.GREEN);
        for (int i = 0; i < 20; i++) {
            for (int k = 0; k < 20; k++) {
                if (grid[i][k]) {
                    e.fillRect(k * SIZE + 400, i * SIZE + 40, SIZE, SIZE);
                } 
            }
        }

        // Drawing the wider lines around the rectangles inside the grid
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

        //Draw the frame for the user's current score
        e.drawLine(1100, 285, 1300, 285);
        e.drawLine(1100, 285, 1100, 365);
        e.drawLine(1100, 365, 1300, 365);
        d.setColor(Color.black);
        d.fillRect(1100, 285, 200, 80);

        // Draw the frame for the display of blocks that need to 
        // fall before the rotation occurs
        d.setColor(Color.black);
        d.fillRect(590, 650, 95, 80);
        e.setColor(Color.white);
        e.drawLine(590, 650, 590, 730);
        e.drawLine(590, 650, 685, 650);
        e.drawLine(685, 650, 685, 730);
    }

}
