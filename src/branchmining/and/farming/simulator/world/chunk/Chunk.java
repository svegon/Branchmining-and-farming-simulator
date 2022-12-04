package branchmining.and.farming.simulator.world.chunk;

import branchmining.and.farming.simulator.world.World;
import branchmining.and.farming.simulator.world.WorldView;
import com.github.svegon.utils.math.geometry.vector.Vec3i;

import java.util.concurrent.ForkJoinTask;

public interface Chunk extends WorldView {
    static Vec3i toChunkPos(int x, int y, int z) {
        return new Vec3i(x >> 8, y >> 8, z >> 8);
    }

    static Vec3i toChunkPos(Vec3i blockPos) {
        return toChunkPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    static Vec3i chunkNorthWestCorner(int x, int y, int z) {
        return new Vec3i(x << 8, y << 8, z << 8);
    }

    static Vec3i chunkNorthWestCorner(Vec3i chunkPos) {
        return chunkNorthWestCorner(chunkPos.getX(), chunkPos.getY(), chunkPos.getZ());
    }

    World getWorld();

    Vec3i getPos();

    default ForkJoinTask<Void> save() {
        return getWorld().getChunkManager().saveChunk(getPos());
    }
}
