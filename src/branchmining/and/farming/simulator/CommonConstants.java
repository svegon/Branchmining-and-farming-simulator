package branchmining.and.farming.simulator;

import branchmining.and.farming.simulator.util.versioning.BaFSVersion;
import branchmining.and.farming.simulator.util.versioning.GameVersion;

public final class CommonConstants {
    private static final BaFSVersion VERSION = GameVersion.fromVersionFile();
    private static final String NAMESPACE = "bafs";

    private CommonConstants() {
        throw new UnsupportedOperationException();
    }

    public static BaFSVersion getVersion() {
        return VERSION;
    }

    public static String getNamespace() {
        return NAMESPACE;
    }
}
