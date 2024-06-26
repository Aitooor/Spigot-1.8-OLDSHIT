package net.minecraft.server;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class PacketPlayOutNamedEntitySpawn implements Packet<PacketListenerPlayOut> {

    public int a;
    public UUID b;
    public int c;
    public int d;
    public int e;
    public byte f;
    public byte g;
    public int h;
    public DataWatcher i;
    public List<DataWatcher.WatchableObject> j;

    // SportPaper start - add constructor
    public PacketPlayOutNamedEntitySpawn(int id, UUID uuid, double xPos, double yPos, double zPos, byte yaw, byte pitch, ItemStack heldItem, DataWatcher dataWatcher) {
        this.a = id;
        this.b = uuid;
        this.c = MathHelper.floor(xPos * 32.0D);
        this.d = MathHelper.floor(yPos * 32.0D);
        this.e = MathHelper.floor(zPos * 32.0D);
        this.f = (byte) ((int) (yaw * 256.0F / 360.0F));
        this.g = (byte) ((int) (pitch * 256.0F / 360.0F));
        this.h = heldItem == null ? 0 : Item.getId(heldItem.getItem());
        this.i = dataWatcher;
        this.j = dataWatcher.b();
    }
    // SportPaper end
    public PacketPlayOutNamedEntitySpawn() {}

    public PacketPlayOutNamedEntitySpawn(EntityHuman entityhuman) {
        this.a = entityhuman.getId();
        this.b = entityhuman.getProfile().getId();
        this.c = MathHelper.floor(entityhuman.locX * 32.0D);
        this.d = MathHelper.floor(entityhuman.locY * 32.0D);
        this.e = MathHelper.floor(entityhuman.locZ * 32.0D);
        this.f = (byte) ((int) (entityhuman.yaw * 256.0F / 360.0F));
        this.g = (byte) ((int) (entityhuman.pitch * 256.0F / 360.0F));
        ItemStack itemstack = entityhuman.inventory.getItemInHand();

        this.h = itemstack == null ? 0 : Item.getId(itemstack.getItem());
        this.i = entityhuman.getDataWatcher();
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = packetdataserializer.e();
        this.b = packetdataserializer.g();
        this.c = packetdataserializer.readInt();
        this.d = packetdataserializer.readInt();
        this.e = packetdataserializer.readInt();
        this.f = packetdataserializer.readByte();
        this.g = packetdataserializer.readByte();
        this.h = packetdataserializer.readShort();
        this.j = DataWatcher.b(packetdataserializer);
    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.b(this.a);
        packetdataserializer.a(this.b);
        packetdataserializer.writeInt(this.c);
        packetdataserializer.writeInt(this.d);
        packetdataserializer.writeInt(this.e);
        packetdataserializer.writeByte(this.f);
        packetdataserializer.writeByte(this.g);
        packetdataserializer.writeShort(this.h);
        this.i.a(packetdataserializer);
    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }

}
