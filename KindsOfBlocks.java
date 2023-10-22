public class KindsOfBlocks extends block{
    
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
