package branchmining.and.farming.simulator.entity.entities.handle;

import branchmining.and.farming.simulator.entity.Entity;
import branchmining.and.farming.simulator.entity.EntityType;
import branchmining.and.farming.simulator.entity.EntityTypes;
import branchmining.and.farming.simulator.world.World;
import com.github.svegon.utils.math.geometry.space.Block6d;

public final class MissingNo extends Entity {
    public MissingNo(World world) {
        super(world);
    }

    @Override
    public EntityType<?> getType() {
        return EntityTypes.MISSING_NO;
    }

    @Override
    public Block6d getBoundingBox() {
        return Block6d.EMPTY;
    }
}
