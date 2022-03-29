package online.nasgar.spigot.entity;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Multimap;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import net.minecraft.server.*;

import java.util.*;


/**
 * Optimized world tile entity list implementation, provides
 * an iterator of tile entities that need to be ticked based
 * on the world time to reduce needed iteration/checks.
 */
public final class OptimizedWorldEntitySet extends AbstractSet<Entity> {

    /** Map of tile classes with modified tick intervals. */
    private static final Object2LongMap<Class<? extends Entity>> CUSTOM_TICK_INTERVALS =
            new Object2LongOpenHashMap<Class<? extends Entity>>() {{
                this.put(Entity.class, 20L);
                this.put(EntityChicken.class, 80L);
            }};

    /** Multimap of all registered tile entities. */
    private final Multimap<Class<? extends Entity>, Entity> registeredTiles = HashMultimap.create();

    @Override
    public int size() {
        return this.registeredTiles.size();
    }

    @Override
    public boolean add(Entity tile) {
        if (tile == null) {
            return false;
        }

        if (this.registeredTiles.containsValue(tile)) {
            return false;
        }

        return this.registeredTiles.put(tile.getClass(), tile);
    }

    @Override
    public boolean remove(Object object) {
        if (object == null) {
            return false;
        }

        return this.registeredTiles.remove(object.getClass(), object);
    }

    @Override
    public boolean contains(Object object) {
        if (object == null) {
            return false;
        }

        return this.registeredTiles.containsEntry(object.getClass(), object);
    }

    @Override
    public void clear() {
        this.registeredTiles.clear();
    }

    @Override
    public Iterator<Entity> iterator() {
        return this.registeredTiles.values().iterator();
    }

    public Iterator<Entity> tickIterator(long worldTime) {
        LinkedList<Iterator<Entity>> tileIterators = new LinkedList<>();
        for (Class<? extends Entity> tileClassToTick : this.getTileClassesToTick(worldTime)) {
            Collection<Entity> tileBucket = this.registeredTiles.get(tileClassToTick);
            if (tileBucket != null) {
                tileIterators.add(tileBucket.iterator());
            }
        }

        return Iterators.concat(tileIterators.iterator());
    }

    private List<Class<? extends Entity>> getTileClassesToTick(long worldTime) {
        List<Class<? extends Entity>> tilesToTick = new LinkedList<>();
        for (Class<? extends Entity> registeredTileClass : this.registeredTiles.keySet()) {
            long customTickInterval = OptimizedWorldEntitySet.CUSTOM_TICK_INTERVALS.getLong(registeredTileClass);
            if (customTickInterval != 0) { // Troves non-existent value is 0.
                if (customTickInterval > 0 && (worldTime == 0 || worldTime % customTickInterval == 0)) {
                    tilesToTick.add(registeredTileClass);
                }
                continue;
            }
            tilesToTick.add(registeredTileClass);
        }
        return tilesToTick;
    }
}