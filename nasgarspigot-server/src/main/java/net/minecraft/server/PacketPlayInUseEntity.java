
package net.minecraft.server;

import lombok.Data;

import java.io.IOException;

public class PacketPlayInUseEntity implements Packet<PacketListenerPlayIn> {
	public int a;
	public EnumEntityUseAction action;
	public Vec3D c;

	public PacketPlayInUseEntity() {
	}

	public void a(PacketDataSerializer var1) throws IOException {
		this.a = var1.e();
		this.action = (EnumEntityUseAction)var1.a(EnumEntityUseAction.class);
		if(this.action == EnumEntityUseAction.INTERACT_AT) {
			this.c = new Vec3D((double)var1.readFloat(), (double)var1.readFloat(), (double)var1.readFloat());
		}

	}

	public void b(PacketDataSerializer var1) throws IOException {
		var1.b(this.a);
		var1.a(this.action);
		if(this.action == EnumEntityUseAction.INTERACT_AT) {
			var1.writeFloat((float)this.c.a);
			var1.writeFloat((float)this.c.b);
			var1.writeFloat((float)this.c.c);
		}

	}

	public void a(PacketListenerPlayIn var1) {
		var1.a(this);
	}

	public Entity a(World var1) {
		return var1.a(this.a);
	}

	public EnumEntityUseAction a() {
		return this.action;
	}

	public Vec3D b() {
		return this.c;
	}

	public void setA(int a) {
		this.a = a;
	}

	public EnumEntityUseAction getAction() {
		return action;
	}

	public void setC(Vec3D c) {
		this.c = c;
	}

	public Vec3D getC() {
		return c;
	}

	public void setAction(EnumEntityUseAction action) {
		this.action = action;
	}

	public int getA() {
		return this.a;
	}

	public static enum EnumEntityUseAction {
		INTERACT,
		ATTACK,
		INTERACT_AT;

		private EnumEntityUseAction() {
		}
	}
}
