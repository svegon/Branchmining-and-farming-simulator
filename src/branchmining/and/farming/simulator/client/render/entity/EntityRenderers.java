package branchmining.and.farming.simulator.client.render.entity;

import branchmining.and.farming.simulator.Registries;
import branchmining.and.farming.simulator.entity.EntityType;
import branchmining.and.farming.simulator.util.registry.Identifier;
import branchmining.and.farming.simulator.util.registry.MutableRegistry;
import branchmining.and.farming.simulator.util.registry.Registry;
import com.github.svegon.utils.interfaces.function.TriFunction;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public final class EntityRenderers {
    public static final Registry<TriFunction<EntityType<?>, Gson, JsonObject, EntityRenderer>> REGISTRY =
            Registries.register("entity_renderers", new MutableRegistry<>());

    private EntityRenderers() {
        throw new UnsupportedOperationException();
    }

    private static TriFunction<EntityType<?>, Gson, JsonObject, EntityRenderer> register(Identifier id,
                                                                                         TriFunction<EntityType<?>, Gson, JsonObject, EntityRenderer> factory) {
        return REGISTRY.register(id, factory);
    }

    private static TriFunction<EntityType<?>, Gson, JsonObject, EntityRenderer> register(String id,
                                                                                         TriFunction<EntityType<?>, Gson, JsonObject, EntityRenderer> factory) {
        return register(Identifier.fromString(id), factory);
    }
}
