package branchmining.and.farming.simulator.block;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.ListIterator;

public class BlockInstance {
    public static final BlockInstance EMPTY = new BlockInstance();

    private final ImmutableList<Block> blocks;
    private final ImmutableList<BlockData> datas;

    public BlockInstance(ImmutableList<Block> blocks) {
        this.blocks = blocks;
        this.datas = this.blocks.stream().map(Block::createBlockData)
                .collect(ImmutableList.toImmutableList());
    }

    public BlockInstance(List<Block> blocks) {
        this(ImmutableList.copyOf(blocks));
    }

    public BlockInstance(List<Block> blocks, List<BlockData> datas) {
        this(ImmutableList.copyOf(blocks));

        ListIterator<BlockData> itr = datas.listIterator();

        while (itr.hasNext()) {
            getDatas().get(itr.nextIndex()).copyFrom(itr.next());
        }
    }

    public BlockInstance(Block... blocks) {
        this(ImmutableList.copyOf(blocks));
    }

    /**
     * constructs a new empty instance
     */
    private BlockInstance() {
        this(ImmutableList.of());
    }

    public boolean update() {
        for (Block block : getBlocks()) {
            if (block.update(this)) {
                return true;
            }
        }

        return false;
    }

    public ImmutableList<Block> getBlocks() {
        return blocks;
    }

    public ImmutableList<BlockData> getDatas() {
        return datas;
    }
}
