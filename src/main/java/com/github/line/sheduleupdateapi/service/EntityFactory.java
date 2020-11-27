package com.github.line.sheduleupdateapi.service;

import java.util.Optional;

public interface EntityFactory<T, S, V> {
    Optional<T> create(S selfReference, V argument);
}
