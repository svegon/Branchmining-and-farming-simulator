package branchmining.and.farming.simulator.util.registry;

import it.unimi.dsi.fastutil.objects.*;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class Registry<E> implements Object2ObjectMap<Identifier, E>, Iterable<E> {
    @Override
    public String toString() {
        return "Registry" + entrySet();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public abstract boolean containsKey(Object key);

    @Override
    public abstract boolean containsValue(Object value);

    @Override
    public E get(Object key) {
        return key instanceof Identifier id ? getById(id) : key instanceof Integer ? getByRawId((int) key) : null;
    }

    @Override
    public E put(Identifier key, E value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends Identifier, ? extends E> m) {
        for (Map.Entry<? extends Identifier, ? extends E> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ObjectSet<Identifier> keySet() {
        return new AbstractObjectSet<>() {
            @Override
            public int size() {
                return Registry.this.size();
            }

            @Override
            public boolean contains(Object o) {
                return Registry.this.containsKey(o);
            }

            @Override
            public ObjectIterator<Identifier> iterator() {
                return new ObjectIterator<>() {
                    private final ObjectIterator<Entry<Identifier, E>> entryIterator =
                            object2ObjectEntrySet().iterator();
                    private Identifier current;

                    @Override
                    public boolean hasNext() {
                        return entryIterator.hasNext();
                    }

                    @Override
                    public Identifier next() {
                        return current = entryIterator.next().getKey();
                    }

                    @Override
                    public void remove() {
                        Registry.this.remove(current);
                    }
                };
            }

            @Override
            public boolean remove(Object o) {
                return Registry.this.remove(o) != null;
            }

            @Override
            public boolean retainAll(final Collection<?> c) {
                return removeIf(e -> !c.contains(e));
            }

            @Override
            public boolean removeAll(final Collection<?> c) {
                return removeIf(c::contains);
            }

            @Override
            public void clear() {
                Registry.this.clear();
            }
        };
    }

    @Override
    public ObjectCollection<E> values() {
        return new AbstractObjectCollection<>() {
            @Override
            public int size() {
                return Registry.this.size();
            }

            @Override
            public boolean contains(Object o) {
                return Registry.this.containsKey(o);
            }

            @Override
            public ObjectIterator<E> iterator() {
                return new ObjectIterator<>() {
                    private final ObjectIterator<Entry<Identifier, E>> entryIterator =
                            object2ObjectEntrySet().iterator();
                    private Entry<Identifier, E> ret;

                    @Override
                    public boolean hasNext() {
                        return entryIterator.hasNext();
                    }

                    @Override
                    public E next() {
                        return (ret = entryIterator.next()).getValue();
                    }

                    @Override
                    public void remove() {
                        Registry.this.remove(ret.getKey(), ret.getValue());
                    }
                };
            }

            @Override
            public boolean retainAll(final Collection<?> c) {
                return removeIf(e -> !c.contains(e));
            }

            @Override
            public boolean removeAll(final Collection<?> c) {
                return removeIf(c::contains);
            }

            @Override
            public void clear() {
                Registry.this.clear();
            }
        };
    }

    public <T extends E> T register(Identifier id, T obj) {
        put(id, obj);
        return obj;
    }

    public abstract E getById(Identifier id);

    public abstract E getByRawId(int id);

    public abstract Identifier getId(E e);

    public abstract int getRawId(E e);

    @Override
    public Iterator<E> iterator() {
        return values().iterator();
    }

    @Override
    public Spliterator<E> spliterator() {
        return values().spliterator();
    }

    public Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    public Stream<E> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }

    public E random(Random r) {
        return getByRawId(r.nextInt(size()));
    }
}
