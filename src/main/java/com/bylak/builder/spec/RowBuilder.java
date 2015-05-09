package com.bylak.builder.spec;

public interface RowBuilder<T, K> {
    T build(final K row);
}
