package branchmining.and.farming.simulator.world.chunk;

import branchmining.and.farming.simulator.block.BlockInstance;
import branchmining.and.farming.simulator.world.World;
import com.github.svegon.utils.math.geometry.vector.Vec3i;

public class EmptyChunk implements Chunk {
    private final World world;
    private final Vec3i pos;

    public EmptyChunk(World world, Vec3i pos) {
        this.world = world;
        this.pos = pos;
    }

    @Override
    public BlockInstance getBlock(Vec3i pos) {
        return BlockInstance.EMPTY;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public Vec3i getPos() {
        return pos;
    }
}
