package branchmining.and.farming.simulator.util.registry;

import branchmining.and.farming.simulator.CommonConstants;
import net.jcip.annotations.Immutable;

import java.util.regex.Pattern;

@Immutable
public final class Identifier {
    public static final Pattern IDENTIFIER_PATTERN = Pattern.compile("[a-z0-9_]+");

    private final String namespace;
    private final String index;

    private Identifier(final String namespace, final String index) {
        this.namespace = namespace;
        this.index = index;
    }

    public static Identifier of(final String namespace, final String index) {
        if (!IDENTIFIER_PATTERN.matcher(namespace).matches()) {
            throw new IllegalArgumentException("illegal namespace: " + namespace);
        }

        if (!IDENTIFIER_PATTERN.matcher(index).matches()) {
            throw new IllegalArgumentException(index);
        }

        return new Identifier(namespace, index);
    }

    public static Identifier fromParts(final String... parts) {
        return switch (parts.length) {
            case 1 -> of(CommonConstants.getNamespace(), parts[0]);
            case 2 -> of(parts[0], parts[1]);
            default -> throw new IllegalArgumentException();
        };
    }

    public static Identifier fromString(String input) {
        return fromParts(input.split(":", 3));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Identifier that)) {
            return false;
        }

        return getNamespace().equals(that.getNamespace()) && getIndex().equals(that.getIndex());
    }

    @Override
    public int hashCode() {
        return 31 * getNamespace().hashCode() + getIndex().hashCode();
    }

    @Override
    public String toString() {
        return getNamespace() + ":" + getIndex();
    }

    public String getNamespace() {
        return namespace;
    }

    public String getIndex() {
        return index;
    }
}
