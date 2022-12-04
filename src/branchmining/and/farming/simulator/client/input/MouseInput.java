package branchmining.and.farming.simulator.client.input;

import branchmining.and.farming.simulator.client.BaFSClient;
import com.github.svegon.game.window.MouseListener;

public class MouseInput implements MouseListener {
    private final BaFSClient client;

    public MouseInput(BaFSClient client) {
        this.client = client;
    }

    @Override
    public void onMouseClick(int button, int action, int mods) {

    }

    @Override
    public void onMouseMove(double xOffset, double yOffset) {

    }

    @Override
    public void onScroll(double xPos, double yPos) {

    }

    @Override
    public void onCursorEnter(boolean entered) {

    }
}
