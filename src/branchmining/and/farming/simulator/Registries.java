package branchmining.and.farming.simulator;

import branchmining.and.farming.simulator.block.Block;
import branchmining.and.farming.simulator.block.Blocks;
import branchmining.and.farming.simulator.entity.EntityType;
import branchmining.and.farming.simulator.entity.EntityTypes;
import branchmining.and.farming.simulator.util.registry.Identifier;
import branchmining.and.farming.simulator.util.registry.MutableRegistry;
import branchmining.and.farming.simulator.util.registry.Registry;

public final class Registries {
    public static final Registry<Registry<?>> REGISTRY = new MutableRegistry<>();
    public static final Registry<EntityType<?>> ENTITY = register("entity", EntityTypes.REGISTRY);
    public static final Registry<Block> BLOCK = register("block", Blocks.REGISTRY);

    static {
        register("registry", REGISTRY);
    }

    private Registries() {
        throw new UnsupportedOperationException();
    }

    public static <E, R extends Registry<E>> R register(final Identifier id, final R registry) {
        return REGISTRY.register(id, registry);
    }

    public static <E, R extends Registry<E>> R register(final String id, final R registry) {
        return register(Identifier.fromString(id), registry);
    }
}
