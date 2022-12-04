package branchmining.and.farming.simulator.util.versioning;

import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(BAFSVersionTypeAdapter.class)
public interface BaFSVersion {
    String getName();

    int protocolVersion();

    int saveVersion();

    boolean isStable();
}
