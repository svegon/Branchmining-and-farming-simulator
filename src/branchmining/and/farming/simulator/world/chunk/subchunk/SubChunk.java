package branchmining.and.farming.simulator.world.chunk.subchunk;

import branchmining.and.farming.simulator.world.chunk.Chunk;
import com.github.svegon.utils.math.geometry.vector.Vec3i;

public interface SubChunk extends Chunk {
    int POS_BIT_SIZE = 4;
    int CUBED_SIZE = 1 << (3 * POS_BIT_SIZE);

    static int size() {
        return CUBED_SIZE;
    }

    static Vec3i subChunkPos(Vec3i pos) {
        return new Vec3i(pos.getX() >> POS_BIT_SIZE, pos.getY() >> POS_BIT_SIZE, pos.getZ() >> POS_BIT_SIZE);
    }

    static Vec3i fromSubChunkPos(Vec3i pos) {
        return new Vec3i(pos.getX() << POS_BIT_SIZE, pos.getY() << POS_BIT_SIZE, pos.getZ() << POS_BIT_SIZE);
    }

    static int relativeCoord(int coord) {
        return coord & ((1 << POS_BIT_SIZE) - 1);
    }

    Chunk getChunk();

    Vec3i relativePos();
}
