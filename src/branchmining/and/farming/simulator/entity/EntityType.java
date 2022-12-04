package branchmining.and.farming.simulator.entity;

import branchmining.and.farming.simulator.Registries;
import branchmining.and.farming.simulator.util.registry.Identifier;

public class EntityType<E extends Entity> {
    public Identifier getId() {
        return Registries.ENTITY.getId(this);
    }
}
