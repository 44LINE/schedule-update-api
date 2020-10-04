package com.github.line.sheduleupdateapi.utils;
import java.io.InputStream;
import java.util.Optional;

public interface InputStreamFetcher {
    Optional<InputStream> fetch(Object resource);
}
