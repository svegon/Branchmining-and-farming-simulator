package branchmining.and.farming.simulator.client.render.block;

import branchmining.and.farming.simulator.block.BlockInstance;
import branchmining.and.farming.simulator.client.ClientWorld;
import com.github.svegon.utils.math.geometry.vector.Vec3i;

public abstract class BlockRenderer {
    public abstract void render(ClientWorld world, Vec3i pos, BlockInstance instance, int index);
}
