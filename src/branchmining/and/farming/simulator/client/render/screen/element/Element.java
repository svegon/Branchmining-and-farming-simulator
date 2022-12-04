package branchmining.and.farming.simulator.client.render.screen.element;

import com.github.svegon.game.Drawable;

public interface Element extends Drawable {
    default boolean onMouseClick(int button, int action, int mods) {
        return false;
    }

    default boolean keyPressed(int key, int scancode, int action, int mods) {
        return false;
    }

    default boolean mouseMoved(double xOffset, double yOffset) {
        return false;
    }

    default boolean onScroll(double xPos, double yPos) {
        return false;
    }
}
