import java.util.Random;

public class block {
    Random rand = new Random();

    public boolean[][] selectRandom() {
        int random = rand.nextInt(4);
        if (random == 0) {
            return KindsOfBlocks.block0();
        } else if (random == 1) {
            return KindsOfBlocks.block1();
        } else if (random == 2) {
            return KindsOfBlocks.block2();
        } else {
            return KindsOfBlocks.block3();
        }
    }
}

