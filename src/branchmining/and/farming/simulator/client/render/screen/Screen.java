package branchmining.and.farming.simulator.client.render.screen;

import branchmining.and.farming.simulator.client.BaFSClient;
import branchmining.and.farming.simulator.client.render.screen.element.Element;
import branchmining.and.farming.simulator.client.render.screen.element.ParentElement;
import branchmining.and.farming.simulator.util.registry.Identifier;
import com.github.svegon.game.RenderUtil;
import com.github.svegon.game.Tickable;
import com.github.svegon.game.texture.ImageTexture;
import com.github.svegon.utils.fast.util.objects.immutable.ImmutableObjectSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

import static org.lwjgl.opengl.GL11.*;

public abstract class Screen implements ParentElement, Tickable {
    public static final Identifier SCREEN_BACKGROUND = Identifier.fromString("screen_background");
    public static final Identifier LOADING_ICON = Identifier.fromString("loading_icon");
    public static final ImmutableObjectSet<Identifier> TEXTURES;

    static {
        Set<Identifier> builder = Sets.newHashSetWithExpectedSize(3);

        builder.add(SCREEN_BACKGROUND);
        builder.add(LOADING_ICON);
        builder.add(Identifier.fromString("main_menu_background"));

        TEXTURES = ImmutableObjectSet.copyOf(builder);
    }

    public final BaFSClient client;
    private final List<Element> children = Lists.newArrayList();
    private Element focused;
    private Element selected;

    protected Screen(BaFSClient client) {
        this.client = client;
    }

    public static void renderBackground(ImageTexture background) {
        glEnable(GL_TEXTURE_2D);
        background.bind();
        glBegin(GL_QUADS);
        RenderUtil.texRectVertexesF(-1, -1, 1, 1);
        glEnd();
        background.unbind();
    }

    public void init() {
        children.clear();
        focused = null;
        selected = null;
    }

    public void onClose() {

    }

    public boolean pausesGame() {
        return false;
    }

    @Override
    public List<? extends Element> children() {
        return children;
    }

    @Override
    public Element getFocused() {
        return focused;
    }

    @Override
    public void setFocused(Element element) {
        focused = element;
    }

    @Override
    public Element getSelected() {
        return selected;
    }

    @Override
    public void setSelected(Element element) {
        selected = element;
    }

    @Override
    public void tick() {

    }
}
