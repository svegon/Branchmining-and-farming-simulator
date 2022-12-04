package branchmining.and.farming.simulator.world.chunk;

import branchmining.and.farming.simulator.block.BlockInstance;
import branchmining.and.farming.simulator.world.World;
import branchmining.and.farming.simulator.world.chunk.subchunk.SubChunk;
import branchmining.and.farming.simulator.world.chunk.subchunk.WorldSubChunk;
import com.github.svegon.utils.math.geometry.vector.Vec3i;
import com.google.common.collect.Maps;
import net.jcip.annotations.NotThreadSafe;

import java.util.Map;

@NotThreadSafe
public class WorldChunk implements Chunk {
    private final World world;
    private final Vec3i pos;
    private final Map<Vec3i, SubChunk> subChunkMap = Maps.newHashMap();

    public WorldChunk(World world, Vec3i pos) {
        this.world = world;
        this.pos = pos;
    }

    @Override
    public Vec3i getPos() {
        return pos;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public BlockInstance getBlock(Vec3i pos) {
        SubChunk subChunk = subChunkMap.get(SubChunk.subChunkPos(pos));

        if (subChunk == null) {
            return BlockInstance.EMPTY;
        }

        return subChunk.getBlock(pos);
    }

    @Override
    public boolean setBlock(Vec3i pos, BlockInstance block) {
        SubChunk subChunk = subChunkMap.computeIfAbsent(SubChunk.subChunkPos(pos),
                p -> new WorldSubChunk(this, p));

        return subChunk.setBlock(pos, block);
    }
}
