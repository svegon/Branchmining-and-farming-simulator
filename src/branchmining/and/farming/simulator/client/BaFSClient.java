package branchmining.and.farming.simulator.client;

import branchmining.and.farming.simulator.client.input.KeyboardInput;
import branchmining.and.farming.simulator.client.input.MouseInput;
import branchmining.and.farming.simulator.client.input.WindowInput;
import branchmining.and.farming.simulator.client.render.RenderContext;
import branchmining.and.farming.simulator.client.render.resource_pack.FileBasedResourcePack;
import branchmining.and.farming.simulator.client.render.screen.MainMenuScreen;
import branchmining.and.farming.simulator.client.render.screen.ResourceLoadingScreen;
import branchmining.and.farming.simulator.client.render.screen.Screen;
import branchmining.and.farming.simulator.client.render.world.WorldRenderer;
import branchmining.and.farming.simulator.entity.entities.player.ClientPlayerEntity;
import branchmining.and.farming.simulator.startup.RunArgs;
import branchmining.and.farming.simulator.util.crash.CrashException;
import branchmining.and.farming.simulator.util.registry.DynamicRegistry;
import branchmining.and.farming.simulator.util.registry.Identifier;
import branchmining.and.farming.simulator.util.settings.Options;
import branchmining.and.farming.simulator.world.World;
import branchmining.and.farming.simulator.world.generation.TestWorldGenerator;
import com.github.svegon.game.*;
import com.github.svegon.game.plain.text.TextRenderer;
import com.github.svegon.utils.collections.ListUtil;
import com.github.svegon.utils.multithreading.DedicatedThreadExecutor;
import net.jcip.annotations.NotThreadSafe;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static org.lwjgl.opengl.GL11.*;

@NotThreadSafe
public class BaFSClient extends DedicatedThreadExecutor<BaFSClient.Task> implements Tickable {
    public static final ThreadGroup CLIENT_WORLD_THREAD_GROUP;
    private static final ThreadGroup CLIENT_THREAD_GROUP;
    private static final AtomicInteger THREAD_ID = new AtomicInteger();

    static {
        ThreadGroup group = Thread.currentThread().getThreadGroup();

        while (!group.getName().equals("system")) {
            group = group.getParent();
        }

        CLIENT_THREAD_GROUP = new ThreadGroup(group, "client threads");
        CLIENT_WORLD_THREAD_GROUP = new ThreadGroup(CLIENT_THREAD_GROUP, "client worlds");
        ;
    }

    private final Logger logger = LogManager.getLogManager().getLogger(getName());
    private final Window window = RenderThread.getExecutor().submit(() -> new Window(1920, 1080,
            "Branchmining and Farming Simulator")).join();
    private final RenderTickCounter renderTickCounter = new RenderTickCounter(20, 0);
    private final TextRenderer textRenderer = new TextRenderer();
    private final WorldRenderer worldRenderer = new WorldRenderer(this);
    private final MouseInput mouseInput = (MouseInput) getWindow().setMouseListener(new MouseInput(this));
    private final KeyboardInput keyboardInput =
            (KeyboardInput) getWindow().setKeyboardListener(new KeyboardInput(this));
    private final List<Screen> screens = ListUtil.newExposedArrayList();
    private final DynamicRegistry<ClientWorld> worlds = new DynamicRegistry<>();
    private final Options options;
    private final Path runDirectory;
    private boolean running;
    private int ticks;
    private ClientWorld world;
    private ClientPlayerEntity player;

    public BaFSClient(RunArgs args) {
        super("Branchmining and Farming Simulator client", r -> new Thread(CLIENT_THREAD_GROUP, r,
                "client-main-thread-" + THREAD_ID.getAndIncrement()));
        this.runDirectory = args.getWorkingDirectory().toAbsolutePath();
        this.options = new Options(this);

        getWindow().setWindowManipulationListener(new WindowInput(this));

        getThread().setUncaughtExceptionHandler((t, e) -> crash(new CrashException(t, e)));
    }

    public void crash(CrashException report) {
        stop();
        report.printStackTrace();
        report.logException(getLogger());
    }

    public void start() {
        running = true;

        executeTask(new Task(0) {
            @Override
            public void run() {
                BaFSClient.this.run();
            }
        });
    }

    private void run() {
        running = true;

        if (Runtime.getRuntime().availableProcessors() > 4) {
            getThread().setPriority(10);
        }

        world = worlds.register(Identifier.fromString("earthcube"), new ClientWorld("EarthCube", this,
                TestWorldGenerator.INSTANCE, 0));
        player = new ClientPlayerEntity(world);

        world.getEntities().add(player);
        addScreen(new MainMenuScreen(this));

        while (isRunning()) {
            runTasks();

            if (!ResourceManager.INSTANCE.resourceLoadFutures.isEmpty()) {
                if (screens.isEmpty() || !(screens.get(screens.size() - 1) instanceof ResourceLoadingScreen)) {
                    addScreen(new ResourceLoadingScreen(this));
                }
            }

            int ticksToProcess = Math.min(renderTickCounter.beginRenderTick(), 20);

            for (int i = 0; i < ticksToProcess; i++) {
                tick();
            }

            RenderThread.getExecutor().submit(this::render).join();
        }
    }

    public void stop() {
        running = false;
    }

    public void render() {
        getWindow().setContext();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT | GL_TEXTURE_BIT);
        glClearColor(0, 0, 0, 0);

        glEnable(GL_ALPHA_TEST);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);
        glAlphaFunc(GL_GREATER, 0);
        glDepthFunc(GL_LEQUAL);

        if (getWorld() != null) {
            getWorldRenderer().renderWorld(new RenderContext(getRenderTickCounter().getTickDelta()), getWorld());
        }

        glDisable(GL_CULL_FACE);
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        for (Screen screen : screens) {
            screen.render();
        }

        getWindow().render();
    }

    public void setScreen(Screen screen) {
        screens.forEach(Screen::onClose);
        screens.clear();
        addScreen(screen);
    }

    public void addScreen(Screen screen) {
        screen.init();
        screens.add(screen);
    }

    public boolean removeScreen(Screen screen) {
        return screens.remove(screen);
    }

    public boolean clearScreens() {
        boolean ret = !screens.isEmpty();
        screens.forEach(Screen::onClose);
        screens.clear();
        return ret;
    }

    public void reloadResources() {
        screens.forEach(Screen::init);
        getWorldRenderer().reload();
    }

    public List<Screen> screens() {
        return screens;
    }

    public boolean isSingleplayer() {
        return true;
    }

    public boolean isPaused() {
        return isSingleplayer() && screens.parallelStream().anyMatch(Screen::pausesGame);
    }

    public boolean isRunning() {
        return running;
    }

    public int ticks() {
        return ticks;
    }

    public Logger getLogger() {
        return logger;
    }

    public Window getWindow() {
        return window;
    }

    public Options getOptions() {
        return options;
    }

    public TextRenderer getTextRenderer() {
        return textRenderer;
    }

    public WorldRenderer getWorldRenderer() {
        return worldRenderer;
    }

    public KeyboardInput getKeyboardInput() {
        return keyboardInput;
    }

    public MouseInput getMouseInput() {
        return mouseInput;
    }

    public ClientWorld getWorld() {
        return world;
    }

    public ClientWorld getWorld(Identifier id) {
        return worlds.getById(id);
    }

    public ClientPlayerEntity getPlayer() {
        return player;
    }

    @Override
    protected boolean canExecute(Task task) {
        return super.canExecute(task) && task.tickSheduled - ticks() < 2;
    }

    @Override
    public void execute(@NotNull Runnable runnable) {
        super.execute(runnable);
    }

    @Override
    protected Task createTask(Runnable runnable) {
        return new Task(ticks()) {
            @Override
            public void run() {
                runnable.run();
            }
        };
    }

    @Override
    public void tick() {
        ticks++;

        getOptions().tick();

        if (!isPaused()) {
            for (World world : worlds.values()) {
                world.tick();
            }
        }

        for (Screen screen : screens) {
            screen.tick();
        }
    }

    public Path getRunDirectory() {
        return runDirectory;
    }

    public FileBasedResourcePack getDefaultResourcePack() {
        return getOptions().defaultResourcePack();
    }

    public RenderTickCounter getRenderTickCounter() {
        return renderTickCounter;
    }

    public abstract static class Task implements Runnable {
        private final int tickSheduled;

        protected Task(int tickSheduled) {
            this.tickSheduled = tickSheduled;
        }
    }
}
