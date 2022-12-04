package branchmining.and.farming.simulator.client.render.resource_pack;

import branchmining.and.farming.simulator.client.BaFSClient;
import branchmining.and.farming.simulator.util.registry.Identifier;
import com.github.svegon.game.ResourceManager;
import com.github.svegon.game.texture.ImageTexture;

import java.nio.file.Path;

public class DefaultResourcePack extends FileBasedResourcePack {
    private final ImageTexture missingTexture;

    public DefaultResourcePack(BaFSClient client) {
        super(client, client.getRunDirectory().resolve("assets"));

        Path missingTexturePath = client.getRunDirectory().resolve("assets/textures/missing.png").toAbsolutePath();

        ResourceManager.INSTANCE.missingTextureReplacement(missingTexture =
                ResourceManager.INSTANCE.getOrLoadTexture(missingTexturePath.toString()));
        associatedTextures.add(missingTexturePath);
    }

    @Override
    public ImageTexture getTexture(Identifier id) {
        ImageTexture texture = otherTextures.getOrDefault(id, missingTexture);
        System.out.println("getting texture " + texture + " with id " + id + " from " + otherTextures);
        return texture;
    }
}
