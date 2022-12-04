package branchmining.and.farming.simulator.world.generation;

import branchmining.and.farming.simulator.block.BlockInstance;
import branchmining.and.farming.simulator.block.Blocks;
import branchmining.and.farming.simulator.world.World;
import branchmining.and.farming.simulator.world.chunk.Chunk;
import branchmining.and.farming.simulator.world.chunk.WorldChunk;
import com.github.svegon.utils.collections.ListUtil;
import com.github.svegon.utils.math.geometry.vector.Vec3i;

public final class TestChunkGenerator extends ChunkGenerator {
    TestChunkGenerator(WorldGenerator generator, World world, long seed) {
        super(generator, world, seed);
    }

    @Override
    public WorldChunk generateChunk(Vec3i chunkPos) {
        WorldChunk chunk = new WorldChunk(getWorld(), chunkPos);

        if ((chunkPos.getX() == 0 || chunkPos.getX() == 1) && chunkPos.getY() == 0
                && (chunkPos.getZ() == 0 || chunkPos.getZ() == 1)) {
            final Vec3i corner = Chunk.chunkNorthWestCorner(chunkPos);
            final BlockInstance grassInstance = new BlockInstance(Blocks.GRASS_BLOCK);

            for (Vec3i pos : ListUtil.iterate((x, z) -> corner.add(x, 0, z), 256, 256)) {
                chunk.setBlock(pos, grassInstance);
            }
        }

        return chunk;
    }
}
