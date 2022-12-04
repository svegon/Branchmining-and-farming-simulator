package branchmining.and.farming.simulator.entity.entities.player;

import branchmining.and.farming.simulator.entity.EntityType;
import branchmining.and.farming.simulator.entity.EntityTypes;
import branchmining.and.farming.simulator.entity.entities.LivingEntity;
import branchmining.and.farming.simulator.world.World;

public abstract class PlayerEntity extends LivingEntity {
    protected PlayerEntity(World world) {
        super(world);
    }

    @Override
    public EntityType<?> getType() {
        return EntityTypes.PLAYER;
    }

    @Override
    protected void updateBoundingBox() {
        setBoundingBox(STANDING_BOX.setInSpace(getPos()));
    }
}
