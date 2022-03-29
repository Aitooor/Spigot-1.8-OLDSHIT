package net.cadiamc.spigot.thread;

import net.cadiamc.spigot.util.Spigot404Write;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet;
import net.openhft.affinity.AffinityLock;
import net.openhft.affinity.AffinityStrategies;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;

/**
 * Copyright 25/10/2020 Kevin Acaymo
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Kevin Acaymo
 */
public abstract class AbstractThread {

    private boolean running;
    private final int TICK_TIME;
    private Thread t;
    protected Queue<Runnable> packets;

    public AbstractThread(boolean async) {
        this.running = false;
        this.TICK_TIME = 16666666;
        this.packets = new ConcurrentLinkedQueue<>();
        this.running = true;
        if (async) {
            try {
                try (AffinityLock al = AffinityLock.acquireLock()) {
                    System.out.println("[Threads] Lock found.");
                    final Thread thread = new Thread(() -> {
                        try (AffinityLock ignored = al.acquireLock(AffinityStrategies.SAME_SOCKET, AffinityStrategies.ANY)) {
                            System.out.println("[Threads] Thread " + this.t.getId() + " locked");
                            this.loop();
                        }
                    });
                    (this.t = thread).start();
                }
            } catch (Exception ex) {
                (this.t = new Thread(this::loop)).start();
                return;
            }
        }
        (this.t = new Thread(this::loop)).start();
    }

    public void loop() {
        long lastTick = System.nanoTime();
        long catchupTime = 0L;
        while (this.running) {
            final long curTime = System.nanoTime();
            final long wait = this.TICK_TIME - (curTime - lastTick) - catchupTime;
            if (wait > 0L) {
                try {
                    Thread.sleep(wait / 1000000L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                catchupTime = 0L;
            }
            else {
                catchupTime = Math.min(1000000000L, Math.abs(wait));
                this.run();
                lastTick = curTime;
            }
        }
    }


    public abstract void run();

    public void addPacket(final Packet packet, final NetworkManager manager, final GenericFutureListener<? extends Future<? super Void>>[] agenericfuturelistener) {
        this.packets.add(() -> Spigot404Write.writeThenFlush(manager.channel, packet, agenericfuturelistener));
    }

    public Thread getThread() {
        return this.t;
    }
}
