package net.cadiamc.spigot.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Copyright 25/10/2020 Kevin Acaymo
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Kevin Acaymo
 */
public class LockFreeStackWithSize<T> {

    private static final Node<?> tail;
    private final AtomicReference<Node<T>> root;

    static {
        tail = new Node<>();
    }

    public LockFreeStackWithSize() {
        this.root = new AtomicReference<>((Node<T>)LockFreeStackWithSize.tail);
    }

    public boolean add(final T value) {
        final Node<T> newRoot = new Node<T>();
        Node.setPayload(newRoot, value);
        Node<T> oldRoot;
        do {
            oldRoot = this.root.get();
            Node.setNext(newRoot, oldRoot);
            Node.setSize(newRoot, oldRoot.size + 1);
        } while (!this.root.compareAndSet(oldRoot, newRoot));
        return true;
    }

    public int size() {
        return this.root.get().size;
    }

    public List<T> removeAllReversed() {
        final List<T> result = new ArrayList<>(this.size() + 100);
        Node<T> r;
        do {
            r = this.root.get();
        } while (!this.root.compareAndSet(r, (Node<T>)LockFreeStackWithSize.tail));
        while (r != LockFreeStackWithSize.tail) {
            result.add((T)((Node<Object>)r).payload);
            r = (Node<T>)((Node<Object>)r).next;
        }
        return result;
    }

    private static class Node<T> {

        private volatile Node<T> next;
        private int size;
        private T payload;

        static void setPayload(Node node, Object payload) {
            node.payload = payload;
        }

        static void setNext(Node node, Node next) {
            node.next = next;
        }

        static void setSize(Node node, int size) {
            node.size = size;
        }
    }
}
