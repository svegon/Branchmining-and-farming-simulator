package branchmining.and.farming.simulator.entity.entities.player;

import branchmining.and.farming.simulator.client.BaFSClient;
import branchmining.and.farming.simulator.client.ClientWorld;

public abstract class AbstractClientPlayerEntity extends PlayerEntity {
    protected AbstractClientPlayerEntity(ClientWorld world) {
        super(world);
    }

    public BaFSClient getClient() {
        return getWorld().getClient();
    }

    @Override
    public ClientWorld getWorld() {
        return (ClientWorld) super.getWorld();
    }
}
