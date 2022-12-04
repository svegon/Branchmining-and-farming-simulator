package branchmining.and.farming.simulator.util.registry;

import com.github.svegon.utils.fast.util.objects.objects.LinkedHashBiMap;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.jcip.annotations.ThreadSafe;

import java.util.List;
import java.util.Spliterator;

@ThreadSafe
public class MutableRegistry<E> extends Registry<E> {
    protected final LinkedHashBiMap<Identifier, E> id2entry = new LinkedHashBiMap<>();
    protected final Object2IntMap<E> entry2rawId = new Object2IntOpenHashMap<>();
    protected final List<E> rawId2entry = Lists.newArrayList();

    public MutableRegistry() {
        entry2rawId.defaultReturnValue(-1);
    }

    public MutableRegistry(Identifier identifier, E entry) {
        this();
        register(identifier, entry);
    }

    @Override
    public int size() {
        return rawId2entry.size();
    }

    @Override
    public void defaultReturnValue(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E defaultReturnValue() {
        return null;
    }

    @Override
    public ObjectSet<Entry<Identifier, E>> object2ObjectEntrySet() {
        return id2entry.object2ObjectEntrySet();
    }

    @Override
    public boolean containsKey(Object key) {
        return key instanceof Integer i ? i >= 0 && i < rawId2entry.size() : id2entry.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return entry2rawId.containsKey(value);
    }

    @Override
    public E put(Identifier key, E value) {
        synchronized (id2entry) {
            if (containsKey(key)) {
                throw new IllegalArgumentException("duplicate id's");
            }

            id2entry.put(key, value);
            entry2rawId.put(value, size());
            rawId2entry.add(value);
            return value;
        }
    }

    @Override
    public E getById(Identifier id) {
        return id2entry.get(id);
    }

    @Override
    public E getByRawId(int id) {
        return rawId2entry.get(id);
    }

    @Override
    public Identifier getId(E e) {
        return id2entry.inverse().get(e);
    }

    @Override
    public int getRawId(E e) {
        return entry2rawId.getInt(e);
    }

    @Override
    public Spliterator<E> spliterator() {
        return id2entry.values().spliterator();
    }
}
