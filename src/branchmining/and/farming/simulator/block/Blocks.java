package branchmining.and.farming.simulator.block;

import branchmining.and.farming.simulator.util.registry.DefaultedRegistry;
import branchmining.and.farming.simulator.util.registry.Identifier;
import branchmining.and.farming.simulator.util.registry.Registry;

public final class Blocks {
    public static final AirBlock AIR = new AirBlock();
    public static final Registry<Block> REGISTRY = new DefaultedRegistry<>("air", AIR);
    public static final Block GRASS_BLOCK = register("grass_block", new Block());

    private Blocks() {
        throw new UnsupportedOperationException();
    }

    private static <B extends Block> B register(Identifier identifier, B block) {
        return REGISTRY.register(identifier, block);
    }

    private static <B extends Block> B register(String identifier, B block) {
        return register(Identifier.fromString(identifier), block);
    }
}
