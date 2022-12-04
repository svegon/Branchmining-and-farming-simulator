package branchmining.and.farming.simulator.entity;

import branchmining.and.farming.simulator.client.ClientWorld;
import branchmining.and.farming.simulator.world.World;
import com.github.svegon.game.GameObject;
import com.github.svegon.utils.ConditionUtil;
import com.github.svegon.utils.math.geometry.space.Block6d;
import com.github.svegon.utils.math.geometry.vector.Vec3d;
import org.intellij.lang.annotations.MagicConstant;

public abstract class Entity extends GameObject {
    private final World world;
    private RemovalContext removalContext;
    private Vec3d velocity = Vec3d.ZERO;
    private Block6d boundingBox = Block6d.EMPTY;

    protected Entity(World world) {
        this.world = world;
    }

    public RemovalContext getRemovalContext() {
        return removalContext;
    }

    public boolean isRemoved() {
        return getRemovalContext() != null;
    }

    public void remove(RemovalContext context) {
        this.removalContext = context;
    }

    public abstract EntityType<?> getType();

    public Block6d getBoundingBox() {
        return boundingBox;
    }

    protected void setBoundingBox(Block6d boundingBox) {
        this.boundingBox = boundingBox;
    }

    public World getWorld() {
        return world;
    }

    public void setVelocity(double x, double y, double z) {
        setVelocity(new Vec3d(x, y, z));
    }

    protected void updateBoundingBox() {

    }

    @Override
    public void setPos(Vec3d pos) {
        if (!pos.equals(getPos())) {
            super.setPos(pos);
            updateBoundingBox();
        }
    }

    @Override
    public Vec3d getVelocity() {
        return velocity;
    }

    public void setVelocity(Vec3d velocity) {
        this.velocity = velocity;
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    @Override
    public void render() {
        ((ClientWorld) getWorld()).getClient().getOptions().getResourcePackInUse().getEntityRenderer(getType())
                .render(this);
    }

    @Override
    public void tick() {

    }

    public static class RemovalContext {
        public static final byte SAVE = 1;

        public static final RemovalContext UNLOADED = new RemovalContext(SAVE);
        public static final RemovalContext DESTROYED = new RemovalContext(0);

        @MagicConstant(flags = {SAVE})
        private final int flags;

        protected RemovalContext(int flags) {
            this.flags = flags;
        }

        public boolean shouldSave() {
            return ConditionUtil.hasFlag(flags, SAVE);
        }
    }
}
