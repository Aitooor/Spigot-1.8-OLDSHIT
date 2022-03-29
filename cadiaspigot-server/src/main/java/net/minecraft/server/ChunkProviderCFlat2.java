package net.minecraft.server;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

public class ChunkProviderCFlat2 implements IChunkProvider {

    private World a;
    private final IBlockData odd;
    private final IBlockData even;
    private final IBlockData air;
    private final List<StructureGenerator> e = Lists.newArrayList();
    private final boolean g = false;
    private WorldGenLakes h;
    private WorldGenLakes i;

    public ChunkProviderCFlat2(World world) {
        IBlockData block = Blocks.WOOL.getBlockData();
        odd = block.getBlock().fromLegacyData(0);
        even = block.getBlock().fromLegacyData(3);
        air = Blocks.AIR.getBlockData();
        (this.a = world).b(0);
    }

    public net.minecraft.server.Chunk getOrCreateChunk(int i, int j) {
        ChunkSnapshot chunksnapshot = new ChunkSnapshot();
        boolean even = (i + j)%2 == 0;
        for (int y = 0; y < 256; ++y) {
            if (y <= 1)
            for (int x = 0; x < 16; ++x)
                for (int z = 0; z < 16; ++z)
                    chunksnapshot.a(x, y, z, (even?this.even:this.odd));
            else for (int x = 0; x < 16; ++x)
                    for (int z = 0; z < 16; ++z)
                        chunksnapshot.a(x, y, z, air);
        }
        net.minecraft.server.Chunk chunk = new net.minecraft.server.Chunk(this.a, chunksnapshot, i, j);
        byte[] biomes = chunk.getBiomeIndex();
        for (int z = 0; z < biomes.length; ++z) biomes[z] = (byte) BiomeBase.MUSHROOM_ISLAND.id;
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

    public void c() {}

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
        if ("Stronghold".equals(s)) {
            Iterator iterator = this.e.iterator();

            while (iterator.hasNext()) {
                StructureGenerator structuregenerator = (StructureGenerator) iterator.next();

                if (structuregenerator instanceof WorldGenStronghold) {
                    return structuregenerator.getNearestGeneratedFeature(world, blockposition);
                }
            }
        }

        return null;
    }

    public int getLoadedChunks() {
        return 0;
    }

    public void recreateStructures(net.minecraft.server.Chunk chunk, int i, int j) {
        Iterator iterator = this.e.iterator();

        while (iterator.hasNext()) {
            StructureGenerator structuregenerator = (StructureGenerator) iterator.next();

            structuregenerator.a(this, this.a, i, j, (ChunkSnapshot) null);
        }

    }

    public net.minecraft.server.Chunk getChunkAt(BlockPosition blockposition) {
        return this.getOrCreateChunk(blockposition.getX() >> 4, blockposition.getZ() >> 4);
    }
}
