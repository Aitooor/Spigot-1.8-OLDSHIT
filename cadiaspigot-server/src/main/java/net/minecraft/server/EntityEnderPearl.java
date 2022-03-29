package net.minecraft.server;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Gate;

public class EntityEnderPearl extends EntityProjectile {

    private static final BlockFace[] faces;
    private static final ItemStack enderpearl;
    private Location valid;

    public EntityEnderPearl(final World world) {
        super(world);
        this.loadChunks = world.paperSpigotConfig.loadUnloadedEnderPearls;
    }

    public EntityEnderPearl(final World world, final EntityLiving entityliving) {
        super(world, entityliving);
        this.loadChunks = world.paperSpigotConfig.loadUnloadedEnderPearls;
    }

    @Override
    protected void a(final MovingObjectPosition movingobjectposition) {
        EntityLiving entityliving = this.getShooter();

        if (movingobjectposition.entity != null) {
            if (movingobjectposition.entity == this.shooter) {
                return;
            }

            movingobjectposition.entity.damageEntity(DamageSource.projectile(this, entityliving), 0.0F);
        }

        // PaperSpigot start - Remove entities in unloaded chunks
        if (this.inUnloadedChunk && world.paperSpigotConfig.removeUnloadedEnderPearls) {
            this.die();
        }

        this.world.addParticle(EnumParticle.PORTAL, this.locX, this.locY + this.random.nextDouble() * 2.0, this.locZ, this.random.nextGaussian(), 0.0, this.random.nextGaussian(), new int[0]);

        if (!this.world.isClientSide) {
            if (entityliving instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer) entityliving;

                // End

                if (entityplayer.playerConnection.a().g() && entityplayer.world == this.world && !entityplayer.isSleeping()) {
                    CraftPlayer player = entityplayer.getBukkitEntity();

                    Location destination = new Location(player.getWorld(), 0, 0, 0, player.getLocation().getYaw(), player.getLocation().getPitch());

                    Location pearlLocation = getBukkitEntity().getLocation();

                    destination.setX(pearlLocation.getBlockX() + 0.5D);
                    destination.setY(pearlLocation.getBlockY() + 0.1D);
                    destination.setZ(pearlLocation.getBlockZ() + 0.5D);

                    PlayerTeleportEvent teleEvent = new PlayerTeleportEvent(player, player.getLocation(), destination, PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
                    Bukkit.getPluginManager().callEvent(teleEvent);

                    if (!teleEvent.isCancelled() && !entityplayer.playerConnection.isDisconnected()) {
                        if (this.random.nextFloat() < 0.05F && this.world.getGameRules().getBoolean("doMobSpawning")) {
                            EntityEndermite entityendermite = new EntityEndermite(this.world);

                            entityendermite.a(true);
                            entityendermite.setPositionRotation(entityliving.locX, entityliving.locY, entityliving.locZ, entityliving.yaw, entityliving.pitch);
                            this.world.addEntity(entityendermite);
                        }

                        if (entityliving.au()) {
                            entityliving.mount((Entity) null);
                        }

                        entityplayer.playerConnection.teleport(teleEvent.getTo());
                        entityplayer.fallDistance = 0.0F;
                        CraftEventFactory.entityDamage = this;
                        entityliving.damageEntity(DamageSource.FALL, 4.0F);
                        CraftEventFactory.entityDamage = null;
                    }
                    // CraftBukkit end
                }
            } else if (entityliving != null) {
                entityliving.enderTeleportTo(this.locX, this.locY, this.locZ);
                entityliving.fallDistance = 0.0F;
            }

            this.die();
        }
    }

    @Override
    public void t_() {
        final EntityLiving shooter = this.shooter;
        if (shooter != null && !shooter.isAlive()) {
            this.die();
            return;
        }
        final Location location = this.getBukkitEntity().getLocation();
        final org.bukkit.block.Block block = location.getBlock();
        if (block.isEmpty()) {
            this.valid = location;
        }
        if (block.getType().toString().contains("STAIRS") || block.getType().toString().contains("STEP")) {
            this.valid = location;
        }
        if (block.getType() == org.bukkit.Material.FENCE_GATE && ((Gate) block.getState().getData()).isOpen()) {
            this.valid = location;
        }
        super.t_();
    }

    static {
        faces = new BlockFace[]{BlockFace.SOUTH, BlockFace.NORTH, BlockFace.EAST, BlockFace.WEST, BlockFace.SELF};
        enderpearl = new ItemStack(org.bukkit.Material.ENDER_PEARL);
    }
}
