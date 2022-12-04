package branchmining.and.farming.simulator.entity;

import branchmining.and.farming.simulator.entity.entities.handle.MissingNo;
import branchmining.and.farming.simulator.entity.entities.player.PlayerEntity;
import branchmining.and.farming.simulator.util.registry.DefaultedRegistry;
import branchmining.and.farming.simulator.util.registry.Identifier;
import branchmining.and.farming.simulator.util.registry.Registry;

public final class EntityTypes {
    public static final EntityType<MissingNo> MISSING_NO = new EntityType<>();
    public static final Registry<EntityType<?>> REGISTRY = new DefaultedRegistry<>("missing_no",
            EntityTypes.MISSING_NO);
    public static final EntityType<PlayerEntity> PLAYER = new EntityType<>();

    private EntityTypes() {
        throw new UnsupportedOperationException();
    }

    private static <E extends Entity, T extends EntityType<E>> T register(Identifier id, T entityType) {
        return REGISTRY.register(id, entityType);
    }

    private static <E extends Entity, T extends EntityType<E>> T register(String id, T entityType) {
        return register(Identifier.fromString(id), entityType);
    }
}
