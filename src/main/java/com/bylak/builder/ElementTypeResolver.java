package com.bylak.builder;

import com.bylak.builder.spec.TypeResolver;
import java.util.HashMap;
import java.util.Map;

public final class ElementTypeResolver {

    private final Map<Class<?>, TypeResolver> resolvers;

    public ElementTypeResolver() {
        this.resolvers = new HashMap<>();
        this.resolvers.put(Byte.class, new ByteElementResolver());
    }

    public Object resolveType(final Object objectToResolve) {

        if(objectToResolve == null) {
            return objectToResolve;
        }

        if(resolvers.containsKey(objectToResolve.getClass())) {
            final TypeResolver typeResolver = resolvers.get(objectToResolve.getClass());

            return typeResolver.resolve(objectToResolve);
        }

        return objectToResolve;
    }
}
