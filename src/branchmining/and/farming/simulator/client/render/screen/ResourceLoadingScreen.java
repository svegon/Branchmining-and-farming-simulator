package branchmining.and.farming.simulator.client.render.screen;

import branchmining.and.farming.simulator.client.BaFSClient;
import com.github.svegon.game.ResourceManager;

import java.util.concurrent.CompletableFuture;

public class ResourceLoadingScreen extends LoadingScreen {
    public ResourceLoadingScreen(BaFSClient client) {
        super(client);
    }

    @Override
    public void tick() {
        ResourceManager.INSTANCE.resourceLoadFutures.removeIf(CompletableFuture::isDone);

        if (ResourceManager.INSTANCE.resourceLoadFutures.isEmpty()) {
            client.removeScreen(this);
            client.reloadResources();
        }
    }
}
