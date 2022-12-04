package branchmining.and.farming.simulator.client.render.resource_pack;

import branchmining.and.farming.simulator.block.Block;
import branchmining.and.farming.simulator.block.Blocks;
import branchmining.and.farming.simulator.client.BaFSClient;
import branchmining.and.farming.simulator.client.render.block.BlockRenderer;
import branchmining.and.farming.simulator.client.render.block.BlockRenderers;
import branchmining.and.farming.simulator.client.render.entity.EntityRenderer;
import branchmining.and.farming.simulator.client.render.entity.EntityRenderers;
import branchmining.and.farming.simulator.client.render.screen.Screen;
import branchmining.and.farming.simulator.entity.Entity;
import branchmining.and.farming.simulator.entity.EntityType;
import branchmining.and.farming.simulator.entity.EntityTypes;
import branchmining.and.farming.simulator.util.registry.Identifier;
import branchmining.and.farming.simulator.util.registry.RegistryTypeAdapterFactory;
import com.github.svegon.game.ResourceManager;
import com.github.svegon.game.plain.text.StaticText;
import com.github.svegon.game.plain.text.Text;
import com.github.svegon.game.texture.ImageTexture;
import com.github.svegon.utils.interfaces.function.TriFunction;
import com.github.svegon.utils.json.JsonUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.jcip.annotations.ThreadSafe;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@ThreadSafe
public class FileBasedResourcePack implements ResourcePack {
    protected final Map<Identifier, ImageTexture> otherTextures = Maps.newConcurrentMap();
    protected final Map<Block, BlockRenderer> blockRenderers = Maps.newConcurrentMap();
    protected final Map<EntityType<?>, EntityRenderer> entityRenderers = Maps.newConcurrentMap();
    protected final Set<Path> associatedTextures = Sets.newConcurrentHashSet();
    private final Path path;
    private final Text name;
    private final ImageTexture icon;

    public FileBasedResourcePack(BaFSClient client, Path path) {
        this.path = path;

        RegistryTypeAdapterFactory.INSTANCE.hashCode();

        ResourceManager resourceManager = ResourceManager.INSTANCE;
        Set<CompletableFuture<Void>> loadFutures = Sets.newHashSet();
        Gson gson = JsonUtil.DYNAMIC_GSON.create();
        Path iconPath = path.resolve("textures/icon.png").toAbsolutePath();
        JsonObject info;
        ImageTexture icon0;

        try {
            Path resourcePath = path.resolve("textures/" + Screen.SCREEN_BACKGROUND.getNamespace() + "/"
                    + Screen.SCREEN_BACKGROUND.getIndex() + ".png").toAbsolutePath();
            ImageTexture texture = ResourceManager.INSTANCE.getOrLoadTexture(resourcePath.toString());
            associatedTextures.add(resourcePath);
            otherTextures.put(Screen.SCREEN_BACKGROUND, texture);
        } catch (IllegalArgumentException ignore) {

        }

        try {
            Path resourcePath = path.resolve("textures/" + Screen.LOADING_ICON.getNamespace() + "/"
                    + Screen.LOADING_ICON.getIndex() + ".png").toAbsolutePath();
            ImageTexture texture = ResourceManager.INSTANCE.getOrLoadTexture(resourcePath.toString());
            associatedTextures.add(resourcePath);
            otherTextures.put(Screen.LOADING_ICON, texture);
        } catch (IllegalArgumentException ignore) {

        }

        try {
            info = JsonUtil.parseFileToObject(path.resolve("info.json")).orElseGet(JsonObject::new);
        } catch (NoSuchFileException e) {
            info = new JsonObject();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        try {
            icon0 = resourceManager.getOrLoadTexture(iconPath.toString());
            associatedTextures.add(iconPath);
        } catch (IllegalArgumentException noIcon) {
            icon0 = client.getDefaultResourcePack().getIcon();
        }

        name = JsonUtil.getProperty(info, "name").map(element -> /*gson.fromJson(element, Text.class)*/
                        new StaticText(element.toString()))
                .orElseGet(() -> new StaticText(path.getFileName().toString()));
        icon = icon0;

        for (Identifier id : Screen.TEXTURES) {
            final Path resourcePath = path.resolve("textures/" + id.getNamespace() + "/" + id.getIndex() + ".png")
                    .toAbsolutePath();

            if (Files.isRegularFile(resourcePath)) {
                loadFutures.add(CompletableFuture.runAsync(() -> {
                    try {
                        ImageTexture texture = ResourceManager.INSTANCE.getOrLoadTexture(resourcePath.toString());
                        otherTextures.put(id, texture);
                        associatedTextures.add(resourcePath);
                    } catch (IllegalArgumentException ignore) {

                    }
                }));
            }
        }

        for (Map.Entry<Identifier, Block> entry : Blocks.REGISTRY.entrySet()) {
            final Path resourcePath = path.resolve("block/" + entry.getKey().getNamespace() + "/"
                    + entry.getKey().getIndex() + ".json").toAbsolutePath();

            loadFutures.add(CompletableFuture.runAsync(() -> {
                try {
                    JsonObject resource = JsonUtil.parseFileToObject(resourcePath).orElseGet(JsonObject::new);
                    TriFunction<Block, Gson, JsonObject, BlockRenderer> rendererFactory = JsonUtil.getProperty(resource,
                            "renderer").map(RegistryTypeAdapterFactory.IdentifierTypeAdapter.
                            INSTANCE::fromJsonTree).map(BlockRenderers.REGISTRY).orElse(null);

                    if (rendererFactory != null) {
                        BlockRenderer renderer = rendererFactory.apply(entry.getValue(), gson, resource);

                        if (renderer != null) {
                            blockRenderers.put(entry.getValue(), renderer);
                            associatedTextures.addAll(JsonUtil.getProperty(resource, "texture")
                                    .flatMap(JsonUtil::getAsJsonArray).map((array) -> {
                                        Path[] a = new Path[array.size()];

                                        for (int i = 0; i < a.length; i++) {
                                            a[i] = Path.of(array.get(i).getAsString()).toAbsolutePath();
                                        }

                                        return Arrays.asList(a);
                                    }).orElse(Collections.emptyList()));
                        }
                    }
                } catch (IOException | JsonParseException e) {
                    // skip
                }
            }));
        }

        for (Map.Entry<Identifier, EntityType<?>> entry : EntityTypes.REGISTRY.entrySet()) {
            final Path resourcePath = path.resolve("block/" + entry.getKey().getNamespace() + "/"
                    + entry.getKey().getIndex() + ".json").toAbsolutePath();

            loadFutures.add(CompletableFuture.runAsync(() -> {
                try {
                    JsonObject resource = JsonUtil.parseFileToObject(resourcePath).orElseGet(JsonObject::new);
                    TriFunction<EntityType<?>, Gson, JsonObject, EntityRenderer> rendererFactory =
                            JsonUtil.getProperty(resource, "renderer")
                                    .map(RegistryTypeAdapterFactory.IdentifierTypeAdapter.INSTANCE::fromJsonTree)
                                    .map(EntityRenderers.REGISTRY).orElse(null);

                    if (rendererFactory != null) {
                        EntityRenderer entityRenderer = rendererFactory.apply(entry.getValue(), gson, resource);

                        if (entityRenderer != null) {
                            entityRenderers.put(entry.getValue(), entityRenderer);
                            associatedTextures.addAll(JsonUtil.getProperty(resource, "texture")
                                    .flatMap(JsonUtil::getAsJsonArray).map((array) -> {
                                        Path[] a = new Path[array.size()];

                                        for (int i = 0; i < a.length; i++) {
                                            a[i] = Path.of(array.get(i).getAsString()).toAbsolutePath();
                                        }

                                        return Arrays.asList(a);
                                    }).orElse(Collections.emptyList()));
                        }
                    }
                } catch (IOException | JsonParseException e) {
                    // skip
                }
            }));
        }

        resourceManager.resourceLoadFutures.add(CompletableFuture.allOf(loadFutures.toArray(new CompletableFuture[0])));
    }

    public Path getPath() {
        return path;
    }

    public Text getName() {
        return name;
    }

    public ImageTexture getIcon() {
        return icon;
    }

    @Override
    public Set<Path> associatedTextures() {
        return associatedTextures;
    }

    @Override
    public ImageTexture getTexture(Identifier id) {
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
