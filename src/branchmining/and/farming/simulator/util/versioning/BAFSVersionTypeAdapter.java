package branchmining.and.farming.simulator.util.versioning;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public final class BAFSVersionTypeAdapter extends TypeAdapter<BaFSVersion> {
    public static final BAFSVersionTypeAdapter INSTANCE = new BAFSVersionTypeAdapter();

    @Override
    public void write(JsonWriter out, BaFSVersion value) throws IOException {
        out.beginObject();

        out.name("name");
        out.value(value.getName());

        out.name("protocolVersion");
        out.value(value.protocolVersion());

        out.name("saveVersion");
        out.value(value.saveVersion());

        out.name("stable");
        out.value(value.isStable());

        out.endObject();
    }

    @Override
    public GameVersion read(JsonReader in) throws IOException {
        JsonElement element = TypeAdapters.JSON_ELEMENT.read(in);

        if (!element.isJsonObject()) {
            throw new JsonParseException("next element is not a Branchmining and Farming Simulator version!");
        }

        JsonObject object = element.getAsJsonObject();
        String name = object.get("name").getAsString();
        int protocolVersion = object.get("protocolVersion").getAsInt();
        int saveVersion = object.get("saveVersion").getAsInt();
        boolean stable = object.get("stable").getAsBoolean();

        return new GameVersion(name, protocolVersion, saveVersion, stable);
    }
}
