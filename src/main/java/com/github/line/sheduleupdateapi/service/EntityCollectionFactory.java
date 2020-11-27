package com.github.line.sheduleupdateapi.service;

import java.util.List;

public interface EntityCollectionFactory<T, S, V> {
    List<T> createCollection(S selfReference, List<V> collection);

}
