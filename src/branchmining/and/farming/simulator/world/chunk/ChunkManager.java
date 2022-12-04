package branchmining.and.farming.simulator.world.chunk;

import branchmining.and.farming.simulator.block.BlockInstance;
import branchmining.and.farming.simulator.world.World;
import branchmining.and.farming.simulator.world.WorldView;
import com.github.svegon.game.Tickable;
import com.github.svegon.utils.math.geometry.vector.Vec3i;
import com.github.svegon.utils.multithreading.DedicatedThreadExecutor;
import com.google.common.collect.Maps;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.locks.ReentrantLock;

public class ChunkManager implements WorldView, Tickable {
    private final ReentrantLock lock = new ReentrantLock();
    private final Map<Vec3i, Chunk> loadedChunks = Maps.newHashMap();
    private final Map<Vec3i, CompletableFuture<Chunk>> loadingChunks = Maps.newHashMap();
    private final Map<Vec3i, ForkJoinTask<Void>> savingChunks = Maps.newConcurrentMap();
    private final ForkJoinPool multithreadingExecutor = new ForkJoinPool();
    private final DedicatedThreadExecutor<?> syncingExecutor;
    private final World world;

    public ChunkManager(DedicatedThreadExecutor<?> syncingLoader, World world) {
        this.syncingExecutor = syncingLoader;
        this.world = world;
    }

    public Chunk getChunk(Vec3i chunkPos) {
        try {
            lock.lock();

            Chunk chunk = loadedChunks.get(chunkPos);

            if (chunk != null) {
                return chunk;
            }

            CompletableFuture<Chunk> loadingFuture = loadingChunks.get(chunkPos);

            if (loadingFuture != null) {
                return loadingFuture.join();
            }

            ForkJoinTask<Void> savingFuture = savingChunks.get(chunkPos);

            if (savingFuture != null) {
                savingFuture.join();
                loadingFuture = tryLoadChunk(chunkPos);

                if (loadingFuture != null) {
                    return loadingFuture.join();
                }
            }

            return generateChunk(chunkPos);
        } finally {
            lock.unlock();
        }
    }

    @Nullable
    private CompletableFuture<Chunk> tryLoadChunk(Vec3i chunkPos) {
        return null;
    }

    private Chunk loadChunk(Vec3i chunkPos) {
        throw new UnsupportedOperationException();
    }

    protected Chunk generateChunk(final Vec3i chunkPos) {
        return new EmptyChunk(getWorld(), chunkPos);
    }

    @Nullable
    @SuppressWarnings({"unchecked"})
    public ForkJoinTask<Void> saveChunk(Vec3i chunkPos) {
        try {
            lock.lock();

            ForkJoinTask<Void> future = savingChunks.get(chunkPos);

            if (future != null) {
                return future;
            }

            Chunk chunk = loadedChunks.remove(chunkPos);

            if (chunk == null) {
                CompletableFuture<Chunk> loadingFuture = loadingChunks.get(chunkPos);

                if (loadingFuture != null) {
                    chunk = loadingFuture.join();
                }
            }

            if (chunk == null) {
                return null;
            }

            final Chunk finalChunk = chunk;
            future = (ForkJoinTask<Void>) multithreadingExecutor.submit(() -> saveChunk(finalChunk));

            savingChunks.put(chunkPos, future);

            return future;
        } finally {
            lock.unlock();
        }
    }

    private void saveChunk(Chunk chunk) {

    }

    public DedicatedThreadExecutor<?> getSyncingExecutor() {
        return syncingExecutor;
    }

    @Override
    public BlockInstance getBlock(Vec3i pos) {
        return getChunk(Chunk.toChunkPos(pos)).getBlock(pos);
    }

    @Override
    public boolean setBlock(Vec3i pos, BlockInstance block) {
        return getChunk(Chunk.toChunkPos(pos)).setBlock(pos, block);
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void tick() {

    }
}
