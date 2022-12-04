package branchmining.and.farming.simulator.client.render.world;

import branchmining.and.farming.simulator.client.BaFSClient;
import branchmining.and.farming.simulator.client.ClientWorld;
import branchmining.and.farming.simulator.client.render.RenderContext;
import com.github.svegon.game.space.Camera;

public class WorldRenderer {
    private final BaFSClient client;

    public WorldRenderer(BaFSClient client) {
        this.client = client;
    }

    public void reload() {

    }

    public void renderWorld(RenderContext context, ClientWorld world) {
        if (!client.isPaused() && client.getWindow().isFocused()) {

        }
    }

    public Camera getCamera() {
        return client.getPlayer();
    }

    public BaFSClient getClient() {
        return client;
    }
}
