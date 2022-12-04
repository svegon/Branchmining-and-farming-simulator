package branchmining.and.farming.simulator.client.input;

import branchmining.and.farming.simulator.client.BaFSClient;
import com.github.svegon.game.window.KeyboardListener;

public class KeyboardInput implements KeyboardListener {
    private final BaFSClient client;

    public KeyboardInput(BaFSClient client) {
        this.client = client;
    }

    @Override
    public void onKey(int key, int scancode, int action, int mods) {

    }
}
