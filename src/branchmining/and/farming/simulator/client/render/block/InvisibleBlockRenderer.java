package branchmining.and.farming.simulator.client.render.block;

import branchmining.and.farming.simulator.block.BlockInstance;
import branchmining.and.farming.simulator.client.ClientWorld;
import com.github.svegon.utils.math.geometry.vector.Vec3i;

public final class InvisibleBlockRenderer extends BlockRenderer {
    public static final InvisibleBlockRenderer INSTANCE = new InvisibleBlockRenderer();

    private InvisibleBlockRenderer() {

    }

    @Override
    public void render(ClientWorld world, Vec3i pos, BlockInstance instance, int index) {

    }
}
