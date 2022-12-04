package branchmining.and.farming.simulator.world;

import branchmining.and.farming.simulator.block.BlockInstance;
import com.github.svegon.utils.math.geometry.vector.Vec3i;

public interface WorldView {
    BlockInstance getBlock(Vec3i pos);

    default boolean setBlock(Vec3i pos, BlockInstance block) {
        return false;
    }
}
