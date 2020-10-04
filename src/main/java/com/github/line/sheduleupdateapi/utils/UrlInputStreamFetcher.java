package com.github.line.sheduleupdateapi.utils;

import com.github.line.sheduleupdateapi.exceptions.InvalidHttpCodeOnRequestMethodException;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

public class UrlInputStreamFetcher implements InputStreamFetcher {
    @Override
    public Optional<InputStream> fetch(Object url) {
        if (url == null) {
            throw new NullPointerException("Resource cannot be null value.");
        } else if (!(url instanceof URL)) {
            throw new IllegalArgumentException("Resource must be URL instance.");
        }

        Optional<HttpURLConnection> httpURLConnection = openAndCheckConnection((URL) url);

        return (httpURLConnection.isPresent()) ? httpUrlConnectionToInputStream(httpURLConnection.get()) : Optional.empty();
    }

    private Optional<HttpURLConnection> openAndCheckConnection(URL url) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new InvalidHttpCodeOnRequestMethodException(HttpStatus.resolve(httpURLConnection.getResponseCode()));
            }
            return Optional.of(httpURLConnection);
        } catch (InvalidHttpCodeOnRequestMethodException | IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Optional<InputStream> httpUrlConnectionToInputStream(HttpURLConnection httpURLConnection) {
        try {
            return Optional.of(httpURLConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
