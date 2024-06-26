package net.minecraft.server;

import net.md_5.bungee.api.chat.BaseComponent;

import java.io.IOException;

public class PacketPlayOutPlayerListHeaderFooter implements Packet<PacketListenerPlayOut> {

    public net.md_5.bungee.api.chat.BaseComponent[] header, footer; // Paper

    public IChatBaseComponent a;
    public IChatBaseComponent b;

    public PacketPlayOutPlayerListHeaderFooter() {}

    public PacketPlayOutPlayerListHeaderFooter(IChatBaseComponent ichatbasecomponent) {
        this.a = ichatbasecomponent;
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = packetdataserializer.d();
        this.b = packetdataserializer.d();
    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        // Paper start
        if (this.header != null) {
            packetdataserializer.a(net.md_5.bungee.chat.ComponentSerializer.toString(this.header));
        } else {
            packetdataserializer.a(this.a);
        }

        if (this.footer != null) {
            packetdataserializer.a(net.md_5.bungee.chat.ComponentSerializer.toString(this.footer));
        } else {
            packetdataserializer.a(this.b);
        }
        // Paper end
    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }

    // PaperSpigot start - fix compile error
    /*
    public void a(PacketListener packetlistener) {
        this.a((PacketListenerPlayOut) packetlistener);
    }
    */
    // PaperSpigot end


    public void setB(IChatBaseComponent b) {
        this.b = b;
    }

    public void setA(IChatBaseComponent a) {
        this.a = a;
    }

    public IChatBaseComponent getB() {
        return b;
    }

    public IChatBaseComponent getA() {
        return a;
    }

    public BaseComponent[] getFooter() {
        return footer;
    }

    public BaseComponent[] getHeader() {
        return header;
    }
}
