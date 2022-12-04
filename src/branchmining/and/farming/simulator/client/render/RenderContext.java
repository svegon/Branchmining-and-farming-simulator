package branchmining.and.farming.simulator.client.render;

public class RenderContext {
    private static RenderContext current;

    private final float tickDelta;

    public RenderContext(float tickDelta) {
        current = this;

        this.tickDelta = tickDelta;
    }

    public static RenderContext get() {
        return current;
    }

    public float getTickDelta() {
        return tickDelta;
    }
}
