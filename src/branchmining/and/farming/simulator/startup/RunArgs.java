package branchmining.and.farming.simulator.startup;

import java.nio.file.Path;

public final class RunArgs {
    private final boolean dedicatedServer;
    private final Path workingDirectory;

    public RunArgs(boolean dedicatedServer, Path workingDirectory) {
        this.dedicatedServer = dedicatedServer;
        this.workingDirectory = workingDirectory;
    }

    public boolean isDedicatedServer() {
        return dedicatedServer;
    }

    public Path getWorkingDirectory() {
        return workingDirectory;
    }
}
