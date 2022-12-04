package branchmining.and.farming.simulator.startup;

import branchmining.and.farming.simulator.client.BaFSClient;
import com.github.svegon.utils.collections.ArrayUtil;

public final class ClientMain {
    private static BaFSClient client;

    public static void main(String[] args) {
        Main.main(ArrayUtil.concat(args, "client"));
    }

    public static void main(RunArgs runArgs) {
        client = new BaFSClient(runArgs);

        client.start();

        while (client.isRunning()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public static BaFSClient getClient() {
        return client;
    }
}
