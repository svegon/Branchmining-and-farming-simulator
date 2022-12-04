package branchmining.and.farming.simulator.world.generation;

import branchmining.and.farming.simulator.world.World;
import branchmining.and.farming.simulator.world.chunk.WorldChunk;
import com.github.svegon.utils.math.ExtendedRandom;
import com.github.svegon.utils.math.geometry.vector.Vec3i;

public abstract class ChunkGenerator {
    private final ExtendedRandom random = new ExtendedRandom();
    private final WorldGenerator generator;
    private final World world;
    private final long seed;

    protected ChunkGenerator(WorldGenerator generator, World world, long seed) {
        this.generator = generator;
        this.world = world;
        this.seed = seed;
    }

    public abstract WorldChunk generateChunk(Vec3i chunkPos);

    public ExtendedRandom getRandom() {
        return random;
    }

    public World getWorld() {
        return world;
    }

    public long getSeed() {
        return seed;
    }

    public WorldGenerator getGenerator() {
        return generator;
    }
}
