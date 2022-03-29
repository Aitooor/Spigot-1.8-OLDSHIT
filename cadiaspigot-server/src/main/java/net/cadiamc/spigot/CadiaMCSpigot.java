package net.cadiamc.spigot;

import net.cadiamc.spigot.handler.MovementHandler;
import net.cadiamc.spigot.handler.PacketHandler;
import org.bukkit.Bukkit;

import java.util.HashSet;
import java.util.Set;

/**
 * Copyright 10/06/2020 Kevin Acaymo
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Kevin Acaymo
 */
public enum CadiaMCSpigot {

    INSTANCE;

    private CadiaMCSpigotConfig config;
    private Set<PacketHandler> packetHandlers = new HashSet<>();
    private Set<MovementHandler> movementHandlers = new HashSet<>();

    public CadiaMCSpigotConfig getConfig() {
        return this.config;
    }

    public Set<PacketHandler> getPacketHandlers() {
        return this.packetHandlers;
    }

    public Set<MovementHandler> getMovementHandlers() {
        return this.movementHandlers;
    }

    public void addPacketHandler(PacketHandler handler) {
        Bukkit.getLogger().info("Adding packet handler: " + handler.getClass().getPackage().getName() + "." + handler.getClass().getName());
        this.packetHandlers.add(handler);
    }

    public void addMovementHandler(MovementHandler handler) {
        Bukkit.getLogger().info("Adding movement handler: " + handler.getClass().getPackage().getName() + "." + handler.getClass().getName());
        this.movementHandlers.add(handler);
    }
}
