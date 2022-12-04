package branchmining.and.farming.simulator.client.input;

import branchmining.and.farming.simulator.client.BaFSClient;
import com.github.svegon.game.window.WindowManipulationListener;

public final class WindowInput implements WindowManipulationListener {
    private final BaFSClient client;

    public WindowInput(BaFSClient client) {
        this.client = client;
    }

    @Override
    public void onResize(int oldWidth, int oldHeight) {

    }

    @Override
    public void onClose() {
        client.stop();
    }
}
