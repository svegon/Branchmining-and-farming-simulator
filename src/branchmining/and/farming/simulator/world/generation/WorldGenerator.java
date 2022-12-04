package branchmining.and.farming.simulator.world.generation;

import branchmining.and.farming.simulator.world.World;

public abstract class WorldGenerator {
    public abstract ChunkGenerator createChunkGenerator(World world, long seed);
}
