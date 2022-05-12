package src;

import java.util.Random;

public class RandomGenerator implements IRandomGenerator{
    private Random random = new Random();

    @Override
    public int generate(int start, int end) {
        return random.nextInt(end - start + 1) + start;
    }
}
