package com.github.line.sheduleupdateapi.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;

public final class TemporaryFile {
    private TemporaryFile() {
        throw new AssertionError();
    }

    public static Optional<File> writeInputStreamToFile(InputStream inputStream) {
        if (Objects.isNull(inputStream)) {
            throw new NullPointerException("InputStream cannot be null value.");
        }

        File file = newTemporaryFile()
                .orElseThrow(IllegalStateException::new);

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int lenght = 0;
            while ((lenght = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, lenght);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(file);
    }

    private static Optional<File> newTemporaryFile() {
        try {
            File file = File.createTempFile("temp", null);
            file.deleteOnExit();
            return Optional.of(file);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
