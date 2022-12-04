package branchmining.and.farming.simulator.world;

import branchmining.and.farming.simulator.block.BlockInstance;
import branchmining.and.farming.simulator.entity.EntityList;
import branchmining.and.farming.simulator.world.chunk.ChunkManager;
import com.github.svegon.game.Tickable;
import com.github.svegon.utils.math.geometry.vector.Vec3i;
import com.github.svegon.utils.multithreading.DedicatedThreadExecutor;

import java.util.concurrent.ThreadFactory;

public abstract class World extends DedicatedThreadExecutor<Runnable> implements WorldView, Tickable {
    private final EntityList entities = new EntityList();

    protected World(String name, ThreadFactory factory) {
        super(name, factory);
    }

    public int getMinHeight() {
        return 0;
    }

    public int getMaxHeight() {
        return 255;
    }

    public EntityList getEntities() {
        return entities;
    }

    public abstract ChunkManager getChunkManager();

    @Override
    protected Runnable createTask(Runnable runnable) {
        return runnable;
    }

    @Override
    public BlockInstance getBlock(Vec3i pos) {
        if (pos.getY() < getMinHeight() || pos.getY() > getMaxHeight()) {
            return BlockInstance.EMPTY;
        }

        return getChunkManager().getBlock(pos);
    }

    @Override
    public boolean setBlock(Vec3i pos, BlockInstance block) {
        if (pos.getY() < getMinHeight() || pos.getY() > getMaxHeight()) {
            return false;
        }

        return getChunkManager().setBlock(pos, block);
    }

    @Override
    public final void tick() {
        submit(this::tickInThread);
    }

    protected void tickInThread() {
        getChunkManager().tick();
        getEntities().tick();
    }
}
