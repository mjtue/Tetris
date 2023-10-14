import java.util.Random;

public class block {
    Random rand = new Random();

    public boolean[][] selectRandom() {
        int random = rand.nextInt(6);
        if (random == 0) {
            return KindsOfBlocks.block0();
        } else if (random == 1) {
            return KindsOfBlocks.block1();
        } else if (random == 2) {
            return KindsOfBlocks.block2();
        } else if (random == 3) {
            return KindsOfBlocks.block3();
        } else if (random == 4) {
            return KindsOfBlocks.block4();
        } else {
            return KindsOfBlocks.block5();
        }
    }
}

