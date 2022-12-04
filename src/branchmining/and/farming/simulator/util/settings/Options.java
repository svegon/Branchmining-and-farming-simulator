package branchmining.and.farming.simulator.util.settings;

import branchmining.and.farming.simulator.client.BaFSClient;
import branchmining.and.farming.simulator.client.render.resource_pack.DefaultResourcePack;
import branchmining.and.farming.simulator.client.render.resource_pack.FileBasedResourcePack;
import branchmining.and.farming.simulator.client.render.resource_pack.ResourcePack;
import com.github.svegon.game.Tickable;
import com.github.svegon.utils.property_map.PropertyMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.jcip.annotations.ThreadSafe;

import java.util.List;

@ThreadSafe
public class Options extends PropertyMap<Object> implements Tickable {
    private final BaFSClient client;
    private final DefaultResourcePack defaultResourcePack;
    private final List<FileBasedResourcePack> resourcePacks = Lists.newArrayList();
    private ResourcePack resourcePackInUse;

    public Options(BaFSClient client) {
        super(Maps.newHashMap());
        this.client = client;
        this.defaultResourcePack = new DefaultResourcePack(client);
        this.resourcePackInUse = defaultResourcePack;

        resourcePacks.add(defaultResourcePack);
    }

    @Override
    public void tick() {

    }

    public ResourcePack getResourcePackInUse() {
        return resourcePackInUse;
    }

    public DefaultResourcePack defaultResourcePack() {
        return defaultResourcePack;
    }
}
