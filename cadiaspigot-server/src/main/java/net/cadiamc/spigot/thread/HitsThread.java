package net.cadiamc.spigot.thread;

/**
 * Copyright 25/10/2020 Kevin Acaymo
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Kevin Acaymo
 */
public class HitsThread extends AbstractThread {

    public HitsThread(boolean async) {
        super(async);
    }

    public void run() {
        while (this.packets.size() > 0) {
            this.packets.poll().run();
        }
    }
}
