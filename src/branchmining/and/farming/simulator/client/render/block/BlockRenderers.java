package branchmining.and.farming.simulator.client.render.block;

import branchmining.and.farming.simulator.Registries;
import branchmining.and.farming.simulator.block.Block;
import branchmining.and.farming.simulator.util.registry.Identifier;
import branchmining.and.farming.simulator.util.registry.MutableRegistry;
import branchmining.and.farming.simulator.util.registry.Registry;
import com.github.svegon.utils.interfaces.function.TriFunction;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public final class BlockRenderers {
    public static final Registry<TriFunction<Block, Gson, JsonObject, BlockRenderer>> REGISTRY =
            Registries.register("block_renderers", new MutableRegistry<>());

    static {
        register("invisible", (block, gson, object) -> InvisibleBlockRenderer.INSTANCE);
        register("cuboid", CuboidBlockRenderer::new);
        register("shape", ShapeBlockRenderer::new);
    }

    private BlockRenderers() {
        throw new UnsupportedOperationException();
    }

    private static TriFunction<Block, Gson, JsonObject, BlockRenderer> register(Identifier id,
                                                                                TriFunction<Block, Gson, JsonObject, BlockRenderer> factory) {
        return REGISTRY.register(id, factory);
    }

    private static TriFunction<Block, Gson, JsonObject, BlockRenderer> register(String id,
                                                                                TriFunction<Block, Gson, JsonObject, BlockRenderer> factory) {
        return register(Identifier.fromString(id), factory);
    }
}
