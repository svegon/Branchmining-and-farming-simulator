package branchmining.and.farming.simulator.util.registry;

public class DynamicRegistry<E> extends MutableRegistry<E> {
    public DynamicRegistry() {

    }

    public DynamicRegistry(Identifier id, E entry) {
        register(id, entry);
    }

    public boolean unregister(E entry) {
        id2entry.inverse().remove(entry);
        entry2rawId.removeInt(entry);
        return rawId2entry.remove(entry);
    }

    public boolean unregister(Identifier id) {
        return unregister(getById(id));
    }
}
