package branchmining.and.farming.simulator.startup;

import branchmining.and.farming.simulator.server.ServerMain;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class Main {
    private Main() {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        boolean server = Arrays.stream(args).parallel().anyMatch(arg -> arg.equalsIgnoreCase("server"))
                && Arrays.stream(args).parallel().noneMatch(arg -> arg.equalsIgnoreCase("client"));
        Path workingDirectory = query(args, "directory").map(Path::of)
                .orElseGet(() -> new File("").toPath());

        RunArgs runArgs = new RunArgs(server, workingDirectory);

        if (runArgs.isDedicatedServer()) {
            ServerMain.main(runArgs);
        } else {
            ClientMain.main(runArgs);
        }
    }

    private static Optional<String> query(String[] args, final String argName) {
        final String match = argName + "=";
        List<String> matches = Arrays.stream(args).parallel().filter(arg -> arg.startsWith(match))
                .map(arg -> arg.substring(match.length())).collect(Collectors.toList());

        if (matches.size() > 1) {
            matches.removeIf(String::isBlank);
        }

        if (matches.isEmpty()) {
            return Optional.empty();
        } else if (matches.size() == 1) {
            return Optional.of(matches.get(0));
        } else {
            throw new IllegalArgumentException(argName);
        }
    }
}
