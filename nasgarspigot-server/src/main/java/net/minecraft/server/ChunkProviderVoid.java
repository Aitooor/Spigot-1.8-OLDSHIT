package net.minecraft.server;

import java.util.List;

public class ChunkProviderVoid implements IChunkProvider {

    private World a;
    private final IBlockData air;

    public ChunkProviderVoid(World world) {
        air = Blocks.AIR.getBlockData();
        (this.a = world).b(0);
    }

    public net.minecraft.server.Chunk getOrCreateChunk(int i, int j) {
        ChunkSnapshot chunksnapshot = new ChunkSnapshot();
        for (int y = 0; y < 256; ++y) {
            for (int x = 0; x < 16; ++x)
                for (int z = 0; z < 16; ++z)
                    chunksnapshot.a(x, y, z, air);
        }
        net.minecraft.server.Chunk chunk = new net.minecraft.server.Chunk(this.a, chunksnapshot, i, j);
        byte[] biomes = chunk.getBiomeIndex();
        for (int z = 0; z < biomes.length; ++z)
            biomes[z] = (byte) BiomeBase.MUSHROOM_ISLAND.id;
        chunk.initLighting();
        return chunk;
    }

    public boolean isChunkLoaded(int i, int j) {
        return true;
    }

    public void getChunkAt(IChunkProvider ichunkprovider, int i, int j) {

    }

    public boolean a(IChunkProvider ichunkprovider, net.minecraft.server.Chunk chunk, int i, int j) {
        return false;
    }

    public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate) {
        return true;
    }

    public void c() {
    }

    public boolean unloadChunks() {
        return false;
    }

    public boolean canSave() {
        return true;
    }

    public String getName() {
        return "FlatLevelSource";
    }

    public List<BiomeBase.BiomeMeta> getMobsFor(EnumCreatureType enumcreaturetype, BlockPosition blockposition) {
        return BiomeBase.MUSHROOM_ISLAND.getMobs(enumcreaturetype);
    }

    public BlockPosition findNearestMapFeature(World world, String s, BlockPosition blockposition) {
        return null;
    }

    public int getLoadedChunks() {
        return 0;
    }

    public void recreateStructures(net.minecraft.server.Chunk chunk, int i, int j) {

    }

    public net.minecraft.server.Chunk getChunkAt(BlockPosition blockposition) {
        return this.getOrCreateChunk(blockposition.getX() >> 4, blockposition.getZ() >> 4);
    }
}
