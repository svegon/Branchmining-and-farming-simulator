package branchmining.and.farming.simulator.interaction;

import branchmining.and.farming.simulator.client.BaFSClient;
import branchmining.and.farming.simulator.entity.entities.player.ClientPlayerEntity;
import com.github.svegon.utils.annotations.Unfinished;

@Unfinished
public class ClientInteractionManager extends InteractionManager {
    private final ClientPlayerEntity player;

    public ClientInteractionManager(ClientPlayerEntity player) {
        this.player = player;
    }

    public ClientPlayerEntity getPlayer() {
        return player;
    }

    public BaFSClient getClient() {
        return player.getClient();
    }
}
