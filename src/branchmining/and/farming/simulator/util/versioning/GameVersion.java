package branchmining.and.farming.simulator.util.versioning;

import com.google.common.base.Preconditions;
import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(BAFSVersionTypeAdapter.class)
public final class GameVersion implements BaFSVersion {
    private final String name;
    private final int protocolVersion;
    private final int saveVersion;
    private final boolean stable;

    public GameVersion(final String name, final int protocolVersion, final int saveVersion, final boolean stable) {
        this.name = Preconditions.checkNotNull(name);
        this.protocolVersion = protocolVersion;
        this.saveVersion = saveVersion;
        this.stable = stable;
    }

    public static GameVersion fromVersionFile() {
        return new GameVersion("0.0.0+build.0", 0, 0, false);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int protocolVersion() {
        return protocolVersion;
    }

    @Override
    public int saveVersion() {
        return saveVersion;
    }

    @Override
    public boolean isStable() {
        return stable;
    }
}
