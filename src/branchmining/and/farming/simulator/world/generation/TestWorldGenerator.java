package branchmining.and.farming.simulator.world.generation;

import branchmining.and.farming.simulator.world.World;

public final class TestWorldGenerator extends WorldGenerator {
    public static final TestWorldGenerator INSTANCE = new TestWorldGenerator();

    private TestWorldGenerator() {

    }

    @Override
    public ChunkGenerator createChunkGenerator(World world, long seed) {
        return new TestChunkGenerator(this, world, seed);
    }
}
