package online.nasgar.spigot.chunk;

import net.minecraft.server.PacketPlayOutMultiBlockChange;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Copyright 01/08/2020 Kevin Acaymo
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Kevin Acaymo
 */
public class CraftFakeMultiBlockChange implements FakeMultiBlockChange {

    private final PacketPlayOutMultiBlockChange packet;

    public CraftFakeMultiBlockChange(PacketPlayOutMultiBlockChange packet) {
        this.packet = packet;
    }

    @Override
    public void sendTo(Player player) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(this.packet);
    }
}
