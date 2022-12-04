package branchmining.and.farming.simulator.util.registry;

import com.github.svegon.game.modelling.ModelSerializer;
import com.github.svegon.utils.ClassUtil;
import com.github.svegon.utils.json.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.function.BiFunction;

public final class RegistryTypeAdapterFactory implements TypeAdapterFactory {
    public static final RegistryTypeAdapterFactory INSTANCE = new RegistryTypeAdapterFactory();

    static {
        ModelSerializer.INSTANCE.hashCode();
        //TextSerializer.INSTANCE.hashCode();
        JsonUtil.DYNAMIC_GSON.registerTypeAdapterFactory(INSTANCE);
    }

    @SuppressWarnings("unchecked")
    public static <E> BiFunction<Identifier, E, ? extends Registry<E>> getRegistryFactory(Class<?> registryClass) {
        if (registryClass == DefaultedRegistry.class) {
            return DefaultedRegistry::new;
        }

        if (registryClass == DynamicRegistry.class) {
            return DynamicRegistry::new;
        }

        if (registryClass == MutableRegistry.class || registryClass == Registry.class) {
            return MutableRegistry::new;
        }

        final Constructor<? extends Registry<E>> constructor;

        if (Modifier.isAbstract(registryClass.getModifiers())) {
            constructor = null;
        } else {
            Constructor<? extends Registry<E>> constructor1;

            try {
                constructor1 = (Constructor<? extends Registry<E>>)
                        registryClass.getDeclaredConstructor(Object.class, Object.class);
            } catch (NoSuchMethodException e) {
                try {
                    constructor1 = (Constructor<? extends Registry<E>>)
                            registryClass.getDeclaredConstructor((Class<?>[]) null);
                } catch (NoSuchMethodException noSuchMethodException) {
                    throw new JsonParseException(e);
                }
            }

            constructor = constructor1;
        }

        if (constructor == null || !constructor.trySetAccessible()) {
            throw new JsonParseException("cannot instantiate registry class " + registryClass + " for serialization");
        }

        return constructor.getParameterCount() == 0 ? (id, e) -> {
            try {
                Registry<E> registry = constructor.newInstance((Object[]) null);
                registry.register(id, e);
                return registry;
            } catch (InstantiationException | IllegalAccessException ex) {
                throw new IllegalStateException(ex);
            } catch (InvocationTargetException ex) {
                throw new RuntimeException(ex.getCause());
            }
        } : (id, e) -> {
            try {
                return constructor.newInstance(id, e);
            } catch (InstantiationException | IllegalAccessException ex) {
                throw new IllegalStateException(ex);
            } catch (InvocationTargetException ex) {
                throw new RuntimeException(ex.getCause());
            }
        };
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Class<?> rawType = typeToken.getRawType();

        if (rawType == Identifier.class) {
            return (TypeAdapter<T>) IdentifierTypeAdapter.INSTANCE;
        }

        if (Registry.class.isAssignableFrom(rawType)) {
            return new RegistryTypeAdapter(gson.getAdapter(TypeToken.get(ClassUtil.getClassTypeArguments(
                    typeToken.getType(), Registry.class)[0])), getRegistryFactory(rawType));
        }

        return null;
    }

    public static final class IdentifierTypeAdapter extends TypeAdapter<Identifier> {
        public static final IdentifierTypeAdapter INSTANCE = new IdentifierTypeAdapter();

        private IdentifierTypeAdapter() {

        }

        @Override
        public void write(JsonWriter out, Identifier identifier) throws IOException {
            out.value(identifier.toString());
        }

        @Override
        public Identifier read(JsonReader in) throws IOException {
            try {
                return Identifier.fromString(in.nextString());
            } catch (IllegalArgumentException e) {
                throw new JsonParseException(e);
            }
        }
    }

    public static final class RegistryTypeAdapter<E, R extends Registry<E>> extends TypeAdapter<R> {
        private final TypeAdapter<E> entryTypeAdapter;
        private final BiFunction<Identifier, E, R> registryFactory;

        public RegistryTypeAdapter(TypeAdapter<E> entryTypeAdapter, BiFunction<Identifier, E, R> registryFactory) {
            this.entryTypeAdapter = entryTypeAdapter;
            this.registryFactory = registryFactory;
        }

        @Override
        public void write(JsonWriter out, R registry) throws IOException {
            out.beginObject();

            for (Map.Entry<Identifier, E> e : registry.entrySet()) {
                out.name(e.getKey().toString());
                entryTypeAdapter.write(out, e.getValue());
            }

            out.endObject();
        }

        @Override
        public R read(JsonReader in) throws IOException {
            in.beginObject();

            Identifier id = Identifier.fromString(in.nextName());
            E entry = entryTypeAdapter.read(in);
            R result = registryFactory.apply(id, entry);

            while (in.peek() != JsonToken.END_OBJECT) {
                result.register(Identifier.fromString(in.nextName()), entryTypeAdapter.read(in));
            }

            in.endObject();

            return result;
        }
    }
}
