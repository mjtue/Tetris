import java.util.Timer;
import java.util.TimerTask;


public class Grid {
    boolean[][] grid;
    static final int WIDTH = 20; 
    static final int HEIGHT = 10;
    boolean[][] currentBlock;
    public int g = 1;
    Timer timer = new Timer();

    public Grid() {
        this.grid = new boolean[10][20];
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 20; k++){
                grid[i][k] = false;
            }
        }
    }
    public boolean[][] getGrid() {
        return this.grid;
    }
    public void print() {
        for (int i = 0; i<10; i++){
            for (int k = 0; k < 20; k++){
                if (grid[i][k] == false) {
                    System.out.print("0");
                } else {
                    System.out.print("1");
                }
                
            }
            System.out.println();
        }
    }
    public void fillFirst() {
        if (levelFree()) {
            block random = new block();
            boolean[][] anotherOne = random.selectRandom();
            currentBlock = anotherOne;
            for (int i = 0; i < 2; i++) {
                for (int k = 0; k < 3; k++) {
                    grid[i][(WIDTH/2)-1+k] = anotherOne[i][k];
                }
            }
        }
    }

    boolean levelFree(){
        for(int i = 0; i < 3; i++){
            if(grid[g+1][(WIDTH / 2) - 1 + i]){
                return false;
            }
        }
        return true;
    }
    public void fillLower() {
        
        if(levelFree()){
            for(int i = 0; i < 2; i++){
                for(int j = 0; j < 3; j++){
                    grid[g + i - 1][(WIDTH / 2) - 1 + j] = false;
                }
            }

            for (int i = 0; i < 2; i++) {
                for (int k = 0; k < 3; k++) {
                    grid[g + i][(WIDTH / 2)- 1 + k] = currentBlock[i][k];   
                }
                    
            }
            
            g++;
            if(g == 9 || !levelFree()){
                g = 1;
                fillFirst();
            }
        }else{
            System.out.println("You lost");
        }
    }

    public void fallingblocks() {
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
               fillLower();
            }
        }, 0, 1000);
    }
}
