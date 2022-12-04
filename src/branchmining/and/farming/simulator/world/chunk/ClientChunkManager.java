package branchmining.and.farming.simulator.world.chunk;

import branchmining.and.farming.simulator.world.World;
import branchmining.and.farming.simulator.world.generation.ChunkGenerator;
import com.github.svegon.utils.math.geometry.vector.Vec3i;
import com.github.svegon.utils.multithreading.DedicatedThreadExecutor;

public class ClientChunkManager extends ChunkManager {
    private final ChunkGenerator chunkGenerator;

    public ClientChunkManager(DedicatedThreadExecutor<?> syncingLoader, World world, ChunkGenerator chunkGenerator) {
        super(syncingLoader, world);
        this.chunkGenerator = chunkGenerator;
    }

    public ChunkGenerator getChunkGenerator() {
        return chunkGenerator;
    }

    @Override
    protected Chunk generateChunk(final Vec3i chunkPos) {
        return getSyncingExecutor().submit(() -> getChunkGenerator().generateChunk(chunkPos)).join();
    }
}
