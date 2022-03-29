package org.bukkit.event.entity;

import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class EnderPearlEvent extends EntityEvent implements Cancellable {
    private static final HandlerList handlers;
    private Reason reason;
    private boolean cancel;
    private Entity hit;

    static {
        handlers = new HandlerList();
    }

    public EnderPearlEvent(final EnderPearl enderPearl, final Reason reason, final Entity hit) {
        super(enderPearl);
        this.reason = reason;
        this.hit = hit;
    }

    @Override
    public EnderPearl getEntity() {
        return (EnderPearl) super.getEntity();
    }

    public Reason getReason() {
        return this.reason;
    }

    @Override
    public HandlerList getHandlers() {
        return EnderPearlEvent.handlers;
    }

    public static HandlerList getHandlerList() {
        return EnderPearlEvent.handlers;
    }

    @Override
    public boolean isCancelled() {
        return this.cancel;
    }

    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }

    public Entity getHit() {
        return this.hit;
    }

    public enum Reason {
        BLOCK("BLOCK", 0),
        ENTITY("ENTITY", 1);

        private Reason(final String s, final int n) {
        }
    }
}
