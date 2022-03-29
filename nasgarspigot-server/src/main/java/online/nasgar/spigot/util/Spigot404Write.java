package online.nasgar.spigot.util;

import com.google.common.collect.Queues;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.server.Packet;

import java.util.Queue;

/**
 * Copyright 25/10/2020 Kevin Acaymo
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Kevin Acaymo
 */
public class Spigot404Write {

    public Spigot404Write(Channel channel) {
        this.channel = channel;
    }

    public static void writeThenFlush(Channel channel, Packet value, GenericFutureListener[] listener) {
        Spigot404Write writer = new Spigot404Write(channel);
        queue.add(new PacketQueue(value, listener));
        if (tasks.addTask())
            channel.pipeline().lastContext().executor().execute(writer::writeQueueAndFlush);
    }

    public void writeQueueAndFlush() {
        while (tasks.fetchTask()) {
            while (queue.size() > 0) {
                PacketQueue messages = queue.poll();
                if (messages != null) {
                    ChannelFuture future = this.channel.write(messages.getPacket());
                    if (messages.getListener() != null)
                        future.addListeners(messages.getListener());
                    future.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                }
            }
        }
        this.channel.flush();
    }

    private static final Queue<PacketQueue> queue = Queues.newConcurrentLinkedQueue();

    private static final Tasks tasks = new Tasks();

    private final Channel channel;

    private static class PacketQueue {
        private final Packet<?> item;

        private final GenericFutureListener<? extends Future<? super Void>>[] listener;

        private PacketQueue(Packet<?> item, GenericFutureListener<?>[] listener) {
            this.item = item;
            this.listener = (GenericFutureListener<? extends Future<? super Void>>[])listener;
        }

        public Packet<?> getPacket() {
            return this.item;
        }

        public GenericFutureListener<? extends Future<? super Void>>[] getListener() {
            return this.listener;
        }
    }
}
