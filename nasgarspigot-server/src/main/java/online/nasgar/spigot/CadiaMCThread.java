package online.nasgar.spigot;

import online.nasgar.spigot.entity.EntityExecutor;
import online.nasgar.spigot.thread.AbstractThread;
import online.nasgar.spigot.thread.HitsThread;
import online.nasgar.spigot.thread.KnockbackThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Copyright 25/10/2020 Kevin Acaymo
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Kevin Acaymo
 */
public class CadiaMCThread {

    private final ExecutorService service;
    private AbstractThread asyncHitDetection;
    private AbstractThread asyncKnockbackDetection;

    public CadiaMCThread(final int threads) {
        this.service = Executors.newFixedThreadPool(threads);

        EntityExecutor.initExecutor(true, 2);
    }

    public void requestTask(final Runnable runnable) {
        this.service.submit(runnable);
    }

    public void loadAsyncThreads(boolean asyncAffinityThreads) {
        try {
            this.asyncHitDetection = new HitsThread(asyncAffinityThreads);
            this.asyncKnockbackDetection = new KnockbackThread(asyncAffinityThreads);
        } catch (Exception ex) {
            System.out.println(
                    "Could not load async threads! Please set async-hit-detection and async-" +
                            "knockback in config.yml to false! " + ex.getMessage());
        }
    }

    public AbstractThread getAsyncHitDetection() {
        return this.asyncHitDetection;
    }

    public AbstractThread getAsyncKnockbackDetection() {
        return this.asyncKnockbackDetection;
    }
}
