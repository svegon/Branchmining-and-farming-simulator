package branchmining.and.farming.simulator.util.registry;

public class DefaultedRegistry<E> extends MutableRegistry<E> {
    private final Identifier defaultId;
    private final E defaultEntry;

    public DefaultedRegistry(Identifier defaultId, E defaultEntry) {
        this.defaultId = defaultId;
        this.defaultEntry = defaultEntry;
        register(defaultId, defaultEntry);

        id2entry.defaultReturnValue(defaultEntry);
    }

    public DefaultedRegistry(String defaultId, E defaultEntry) {
        this(Identifier.fromString(defaultId), defaultEntry);
    }

    @Override
    public Identifier getId(E e) {
        Identifier result = super.getId(e);
        return result != null ? result : defaultId;
    }

    @Override
    public E getByRawId(int id) {
        E result = super.getByRawId(id);
        return result != null ? result : defaultEntry;
    }
}
