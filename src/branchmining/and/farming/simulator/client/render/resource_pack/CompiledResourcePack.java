package branchmining.and.farming.simulator.client.render.resource_pack;

import branchmining.and.farming.simulator.block.Block;
import branchmining.and.farming.simulator.block.Blocks;
import branchmining.and.farming.simulator.client.render.block.BlockRenderer;
import branchmining.and.farming.simulator.client.render.entity.EntityRenderer;
import branchmining.and.farming.simulator.entity.Entity;
import branchmining.and.farming.simulator.entity.EntityType;
import branchmining.and.farming.simulator.entity.EntityTypes;
import branchmining.and.farming.simulator.startup.ClientMain;
import branchmining.and.farming.simulator.util.registry.Identifier;
import com.github.svegon.game.texture.ImageTexture;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.StreamSupport;

public class CompiledResourcePack implements ResourcePack {
    private final Map<Identifier, ImageTexture> otherTextures = Maps.newHashMapWithExpectedSize(ClientMain.getClient()
            .getOptions().defaultResourcePack().otherTextures.size());
    private final Map<Block, BlockRenderer> blockRenderers =
            Maps.newHashMapWithExpectedSize(Blocks.REGISTRY.size());
    private final Map<EntityType<?>, EntityRenderer> entityRenderers =
            Maps.newHashMapWithExpectedSize(EntityTypes.REGISTRY.size());
    private final ImmutableList<FileBasedResourcePack> resourcePacks;

    public CompiledResourcePack(Iterable<FileBasedResourcePack> resourcePacks) {
        this.resourcePacks = StreamSupport.stream(resourcePacks.spliterator(), true).distinct()
                .collect(ImmutableList.toImmutableList());

        for (FileBasedResourcePack resourcePack : this.resourcePacks) {
            otherTextures.putAll(resourcePack.otherTextures);
            blockRenderers.putAll(resourcePack.blockRenderers);
            entityRenderers.putAll(resourcePack.entityRenderers);
        }
    }

    public CompiledResourcePack(FileBasedResourcePack... resourcePacks) {
        this(Arrays.asList(resourcePacks));
    }

    public ImmutableList<FileBasedResourcePack> getResourcePacks() {
        return resourcePacks;
    }

    @Override
    public Set<Path> associatedTextures() {
        return Collections.emptySet();
    }

    @Override
    public ImageTexture getTexture(Identifier id) {
        System.out.println("getting texture " + id + " from " + otherTextures);
        return otherTextures.get(id);
    }

    @Override
    public BlockRenderer getBlockRenderer(Block block) {
        return blockRenderers.get(block);
    }

    @Override
    public <E extends Entity> EntityRenderer getEntityRenderer(EntityType<E> entityType) {
        return entityRenderers.get(entityType);
    }
}
