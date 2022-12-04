package branchmining.and.farming.simulator.entity.entities.player;

import branchmining.and.farming.simulator.client.ClientWorld;
import branchmining.and.farming.simulator.interaction.ClientInteractionManager;

public class ClientPlayerEntity extends AbstractClientPlayerEntity {
    private final ClientInteractionManager interactionManager = new ClientInteractionManager(this);

    public ClientPlayerEntity(ClientWorld world) {
        super(world);
    }

    public ClientInteractionManager getInteractionManager() {
        return interactionManager;
    }

    @Override
    public void tick() {

    }
}
