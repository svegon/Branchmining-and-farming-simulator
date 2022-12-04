package branchmining.and.farming.simulator.entity;

import com.github.svegon.game.Tickable;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class EntityList extends AbstractList<Entity> implements Tickable {
    private final ReentrantLock lock = new ReentrantLock();
    private final Int2ObjectMap<Entity> id2Entity = Int2ObjectMaps.synchronize(new Int2ObjectLinkedOpenHashMap<>(),
            lock);
    private final AtomicInteger nextId = new AtomicInteger();

    @Override
    public Entity get(int index) {
        return id2Entity.get(index);
    }

    @Override
    public int size() {
        return id2Entity.size();
    }

    @Override
    public boolean add(Entity entity) {
        int id = nextId.getAndIncrement();

        id2Entity.put(id, entity);
        entity.setId(id);

        return true;
    }

    @Override
    public boolean remove(Object o) {
        return o instanceof Entity entity && id2Entity.remove(entity.getId()) == entity;
    }

    @Override
    public Iterator<Entity> iterator() {
        return id2Entity.values().iterator();
    }

    @Override
    public void tick() {
        for (Entity entity : this) {
            tick(entity);
        }
    }

    private void tick(Entity entity) {
        if (entity.isRemoved()) {
            remove(entity);

            Entity.RemovalContext context = entity.getRemovalContext();

            if (context.shouldSave()) {
                save(entity);
            }
        } else {
            entity.tick();
        }
    }

    private void save(Entity entity) {

    }
}
