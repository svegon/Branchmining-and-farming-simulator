package branchmining.and.farming.simulator.world.chunk.subchunk;

import branchmining.and.farming.simulator.block.BlockInstance;
import branchmining.and.farming.simulator.world.World;
import branchmining.and.farming.simulator.world.chunk.WorldChunk;
import com.github.svegon.utils.collections.ArrayUtil;
import com.github.svegon.utils.math.geometry.vector.Vec3i;
import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
public class WorldSubChunk implements SubChunk {
    private final WorldChunk backRef;
    private final Vec3i relativePos;
    private final BlockInstance[] blocks = new BlockInstance[SubChunk.size()];

    public WorldSubChunk(WorldChunk backRef, Vec3i relativePos) {
        this.backRef = backRef;
        this.relativePos = relativePos;

        ArrayUtil.fill(blocks, BlockInstance.EMPTY);
    }

    private static int posToIndex(Vec3i pos) {
        return SubChunk.relativeCoord(pos.getY()) << (2 * POS_BIT_SIZE)
                + SubChunk.relativeCoord(pos.getZ()) << POS_BIT_SIZE + SubChunk.relativeCoord(pos.getX());
    }

    @Override
    public BlockInstance getBlock(Vec3i pos) {
        return getBlock(posToIndex(pos));
    }

    @Override
    public boolean setBlock(Vec3i pos, BlockInstance block) {
        return setBlock(posToIndex(pos), block);
    }

    @Override
    public World getWorld() {
        return getChunk().getWorld();
    }

    @Override
    public Vec3i getPos() {
        return getChunk().getPos();
    }

    @Override
    public WorldChunk getChunk() {
        return backRef;
    }

    @Override
    public Vec3i relativePos() {
        return relativePos;
    }

    public BlockInstance getBlock(int index) {
        return blocks[index];
    }

    public boolean setBlock(int index, BlockInstance block) {
        if (block == getBlock(index)) {
            return false;
        }

        blocks[index] = block;
        return true;
    }
}
