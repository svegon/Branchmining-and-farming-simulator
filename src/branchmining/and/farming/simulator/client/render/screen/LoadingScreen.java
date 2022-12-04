package branchmining.and.farming.simulator.client.render.screen;

import branchmining.and.farming.simulator.client.BaFSClient;
import branchmining.and.farming.simulator.client.render.RenderContext;
import branchmining.and.farming.simulator.util.registry.Identifier;
import com.github.svegon.game.texture.ImageTexture;

import static org.lwjgl.opengl.GL11.*;

public class LoadingScreen extends Screen {
    protected ImageTexture background;
    protected ImageTexture loadingIcon;

    public LoadingScreen(BaFSClient client) {
        super(client);
    }

    @Override
    public void init() {
        super.init();

        background = client.getOptions().getResourcePackInUse().getTexture(SCREEN_BACKGROUND);
        loadingIcon = client.getOptions().getResourcePackInUse().getTexture(Identifier.fromString("loading_icon"));
    }

    @Override
    public void render() {
        final float partialTicks = RenderContext.get().getTickDelta();

        glBegin(GL_QUADS);
        background.bind();
        glVertex2f(0, 0);
        glTexCoord2f(0, 0);
        glVertex2f(1, 0);
        glTexCoord2f(1, 0);
        glVertex2f(1, 1);
        glTexCoord2f(1, 1);
        glVertex2f(0, 1);
        glTexCoord2f(0, 1);

        glPushMatrix();
        loadingIcon.bind();
        glRotatef((client.ticks() + partialTicks) / 20, 1, 1, 0);
        glVertex2f(-8F / 9F, 0);
        glTexCoord2f(0, 0);
        glVertex2f(8F / 9F, 0);
        glTexCoord2f(1, 0);
        glVertex2f(8F / 9F, 1);
        glTexCoord2f(1, 1);
        glVertex2f(-8F / 9F, 1);
        glTexCoord2f(0, 1);
        loadingIcon.unbind();
        glPopMatrix();
        glEnd();
    }

    @Override
    public boolean pausesGame() {
        return true;
    }
}
