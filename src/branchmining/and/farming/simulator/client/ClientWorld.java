package branchmining.and.farming.simulator.client;

import branchmining.and.farming.simulator.world.World;
import branchmining.and.farming.simulator.world.chunk.ClientChunkManager;
import branchmining.and.farming.simulator.world.generation.WorldGenerator;

public class ClientWorld extends World {
    private final ClientChunkManager chunkManager;
    private final BaFSClient client;

    public ClientWorld(final String name, BaFSClient client, WorldGenerator worldGenerator, long seed) {
        super(name, r -> new Thread(BaFSClient.CLIENT_WORLD_THREAD_GROUP, r, name + "-thread"));
        this.client = client;
        chunkManager = new ClientChunkManager(this, this,
                worldGenerator.createChunkGenerator(this, seed));
    }

    public BaFSClient getClient() {
        return client;
    }

    @Override
    protected void tickInThread() {
        super.tickInThread();
    }

    @Override
    public ClientChunkManager getChunkManager() {
        return chunkManager;
    }
}
