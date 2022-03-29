package net.cadiamc.spigot.entity;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class EntityExecutor {

    public static ThreadPoolExecutor EXECUTOR;

    public static void initExecutor(boolean fixed, int size) {
        if (fixed)
            EXECUTOR = (ThreadPoolExecutor) Executors.newFixedThreadPool(size);
        else
            EXECUTOR = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    public static void stopExecutor() {
        if (EXECUTOR != null) EXECUTOR.shutdown();
    }
}