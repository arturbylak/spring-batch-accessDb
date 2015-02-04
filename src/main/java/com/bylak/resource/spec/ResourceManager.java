package com.bylak.resource.spec;

import com.bylak.resource.exception.ResourceException;

public interface ResourceManager<T> {
    void open() throws ResourceException;
    void close() throws ResourceException;
    T getResource();
}
