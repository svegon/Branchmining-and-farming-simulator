package branchmining.and.farming.simulator.client.render.entity;

import branchmining.and.farming.simulator.entity.Entity;

public final class InvisibleEntityRenderer extends EntityRenderer {
    public static final InvisibleEntityRenderer INSTANCE = new InvisibleEntityRenderer();

    private InvisibleEntityRenderer() {

    }

    @Override
    public void render(Entity entity) {

    }
}
