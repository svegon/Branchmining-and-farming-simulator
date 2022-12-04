package branchmining.and.farming.simulator.client.render.resource_pack;

import branchmining.and.farming.simulator.block.Block;
import branchmining.and.farming.simulator.client.render.block.BlockRenderer;
import branchmining.and.farming.simulator.client.render.entity.EntityRenderer;
import branchmining.and.farming.simulator.entity.Entity;
import branchmining.and.farming.simulator.entity.EntityType;
import branchmining.and.farming.simulator.util.registry.Identifier;
import com.github.svegon.game.texture.ImageTexture;

import java.nio.file.Path;
import java.util.Set;

public interface ResourcePack {
    Set<Path> associatedTextures();

    ImageTexture getTexture(Identifier id);

    BlockRenderer getBlockRenderer(Block block);

    <E extends Entity> EntityRenderer getEntityRenderer(EntityType<E> entityType);
}
