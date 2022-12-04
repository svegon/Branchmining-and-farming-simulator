package branchmining.and.farming.simulator.client.render.screen;

import branchmining.and.farming.simulator.client.BaFSClient;
import branchmining.and.farming.simulator.util.registry.Identifier;
import com.github.svegon.game.texture.ImageTexture;

public class MainMenuScreen extends Screen {
    private ImageTexture background;

    public MainMenuScreen(BaFSClient client) {
        super(client);
    }

    @Override
    public void render() {
        renderBackground(background);

        super.render();
    }

    @Override
    public void init() {
        super.init();

        background = client.getOptions().getResourcePackInUse()
                .getTexture(Identifier.fromString("main_menu_background"));
    }

    @Override
    public boolean pausesGame() {
        return true;
    }
}
