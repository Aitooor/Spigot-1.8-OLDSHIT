package net.cadiamc.spigot.util;
import java.util.concurrent.atomic.AtomicLong;
import java.util.*;

public strictfp class FastRandom extends Random
{
    protected long seed;

    public FastRandom() {
        this(System.nanoTime());
    }

    public FastRandom(final long seed) {
        this.seed = seed;
    }

    public synchronized strictfp long getSeed() {
        return this.seed;
    }

    @Override
    public synchronized strictfp void setSeed(final long seed) {
        super.setSeed(this.seed = seed);
    }

    public strictfp FastRandom clone() {
        return new FastRandom(this.getSeed());
    }

    @Override
    protected strictfp int next(final int nbits) {
        long x = this.seed;
        x ^= x << 21;
        x ^= x >>> 35;
        x ^= x << 4;
        this.seed = x;
        x &= (1L << nbits) - 1L;
        return (int)x;
    }

    public synchronized strictfp void setSeed(final int[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Array length must be greater than zero");
        }
        this.setSeed(array.hashCode());
    }
}
