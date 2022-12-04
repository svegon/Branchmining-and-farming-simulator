package branchmining.and.farming.simulator.entity.entities;

import branchmining.and.farming.simulator.entity.Entity;
import branchmining.and.farming.simulator.interaction.DamageSource;
import branchmining.and.farming.simulator.world.World;
import com.github.svegon.utils.math.geometry.space.Block3d;

public abstract class LivingEntity extends Entity {
    protected static final Block3d STANDING_BOX = Block3d.create(0.5, 0.5, 1.8);

    private int health;

    protected LivingEntity(World world) {
        super(world);
    }

    public boolean damage(int value, DamageSource source) {
        return false;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isDead() {
        return getHealth() <= 0;
    }
}
