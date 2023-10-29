/** In this class we create 6 diffrent kinds of blocks.
 * 
 * These kinds of blocks are later used in the MainPanel class
 * and are randomly selected when filling the first row of the grid.
 * 
 */
public class KindsOfBlocks extends Block {
    
    public static boolean[][] newBlock = new boolean[2][3];

    public static boolean[][] block0() {
        newBlock = new boolean[][] { 
            {false, false, false}, 
            {true, true, true}, };
            return newBlock;
    }

    public static boolean[][] block1() {
        newBlock = new boolean [][]{ 
            {false, true, false},
            {true, true, true}, };
            return newBlock;
    }

    public static boolean[][] block2() {
        newBlock = new boolean [][]{ 
            {false, false, true},
            {true, true, true}, };
            return newBlock;
    }

    public static boolean[][] block3() {
        newBlock = new boolean [][]{ 
            {false, true, true},
            {true, true, true}, };
            return newBlock;
    }

    public static boolean[][] block4() {
        newBlock = new boolean [][]{ 
            {true, true, false},
            {true, true, true}, };
            return newBlock;
    }

    public static boolean[][] block5() {
        newBlock = new boolean [][]{ 
            {true, true, false},
            {true, true, true}, };
            return newBlock;
    }
}
