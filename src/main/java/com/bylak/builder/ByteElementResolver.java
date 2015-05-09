package com.bylak.builder;

import com.bylak.builder.spec.TypeResolver;

public class ByteElementResolver implements TypeResolver {
    @Override
    public Object resolve(Object object) {
        if(object == null){
            return object;
        }

        Byte castedValue = (Byte)object;

        return castedValue & (0xff);
    }
}
