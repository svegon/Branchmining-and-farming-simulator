package branchmining.and.farming.simulator.client.render.screen.element;

import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public interface ParentElement extends Element {
    List<? extends Element> children();

    Element getFocused();

    void setFocused(Element element);

    Element getSelected();

    void setSelected(Element element);

    @Override
    default void render() {
        for (Element element : children()) {
            element.render();
        }
    }

    @Override
    default boolean onMouseClick(int button, int action, int mods) {
        if (action == GLFW_RELEASE) {
            setSelected(null);

            for (Element child : children()) {
                if (child.onMouseClick(button, action, mods)) {
                    return true;
                }
            }
        } else {
            Element focused = getFocused();

            if (focused != null) {
                if (focused.onMouseClick(button, action, mods)) {
                    setSelected(focused);
                    return true;
                }

                for (Element child : children()) {
                    if (child != focused && child.onMouseClick(button, action, mods)) {
                        setSelected(child);
                        return true;
                    }
                }
            } else {
                for (Element child : children()) {
                    if (child.onMouseClick(button, action, mods)) {
                        setSelected(child);
                        return true;
                    }
                }

            }
        }

        return false;
    }

    @Override
    default boolean keyPressed(int key, int scancode, int action, int mods) {
        Element selected = getSelected();

        if (selected != null) {
            if (selected.keyPressed(key, scancode, action, mods)) {
                return true;
            }

            if (action == GLFW_RELEASE) {
                setSelected(null);

                for (Element child : children()) {
                    if (child != selected && child.keyPressed(key, scancode, action, mods)) {
                        return true;
                    }
                }
            } else {
                for (Element child : children()) {
                    if (child != selected && child.keyPressed(key, scancode, action, mods)) {
                        setSelected(child);
                        return true;
                    }
                }
            }
        } else {
            if (action == GLFW_RELEASE) {
                for (Element child : children()) {
                    if (child.keyPressed(key, scancode, action, mods)) {
                        return true;
                    }
                }
            } else {
                for (Element child : children()) {
                    if (child.keyPressed(key, scancode, action, mods)) {
                        setSelected(child);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    default boolean mouseMoved(double xOffset, double yOffset) {
        setFocused(null);

        for (Element child : children()) {
            if (child.mouseMoved(xOffset, yOffset)) {
                setFocused(child);
                return true;
            }
        }

        return false;
    }

    @Override
    default boolean onScroll(double xPos, double yPos) {
        setFocused(null);

        for (Element child : children()) {
            if (child.onScroll(xPos, yPos)) {
                setFocused(child);
                return true;
            }
        }

        return true;
    }
}
