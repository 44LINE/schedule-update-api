package com.github.line.sheduleupdateapi.utils;

import jdk.nashorn.internal.ir.annotations.Immutable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

// class fetches data from html document
@Immutable
public final class CustomExtractor {

    //class variables
    private final static String SOURCE_URL = "https://it.pk.edu.pl/?page=rz";
    private final static int DEFAULT_ATTRIBUTE_INDEX = 0;

    //download url extraction variables
    private final static String DOWNLOAD_URL_CSS_QUERY = "div[class=alert readmetxt alert-light] a[href]";
    private final static String DOWNLOAD_URL_ATTRIBUTE_KEY = "href";

    //latest update date extraction variables
    private final static String LATEST_UPDATE_DATE_CSS_QUERY = "div[class=alert readmetxt alert-light] div[class=text-right sign]";
    private final static String LATEST_UPDATE_DATE_ATTRIBUTE_KEY = "text";
    private final static DateTimeFormatter LATEST_UPDATE_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private CustomExtractor() {
        throw new AssertionError();
    }

    public static Optional<URL> extractDownloadUrl() {
        Optional<String> partialReturnedUrl = extractFromHtmlDocument(
                SOURCE_URL,
                DOWNLOAD_URL_CSS_QUERY,
                DOWNLOAD_URL_ATTRIBUTE_KEY,
                DEFAULT_ATTRIBUTE_INDEX
                );

        if (partialReturnedUrl.isPresent()) {
            String validUrl = partialReturnedUrl.get()
                    //replacing spaces
                    .replace(" ", "%20")
                    //adding protocol and host
                    .replace("../", "https://it.pk.edu.pl/");

            return stringToUrl(validUrl);
        } else {
            return Optional.empty();
        }
    }

    public static Optional<LocalDateTime> extractLatestUpdateDate() {
        Optional<String> returnedText = extractFromHtmlDocument(
                SOURCE_URL,
                LATEST_UPDATE_DATE_CSS_QUERY,
                LATEST_UPDATE_DATE_ATTRIBUTE_KEY,
                DEFAULT_ATTRIBUTE_INDEX
        );

        if (returnedText.isPresent()) {
            //working with string
            String dateToParse = returnedText.get().split(", ", 0)[1];
            dateToParse = dateToParse.replace("]", "");
            return Optional.of(LocalDateTime.parse(dateToParse, LATEST_UPDATE_DATE_FORMATTER));
        } else {
            return Optional.empty();
        }
    }

    //org.jsoup.select.Selector pattern
    public static Optional<String> extractFromHtmlDocument(String url, String cssQuery, String attributeKey, int attributeIndex) throws IndexOutOfBoundsException {
        boolean urlNull = null == url;
        boolean cssQueryNull = null == cssQuery;
        boolean attributeKeyNull = null == attributeKey;
        boolean isAttributeIndexNotValid = (attributeIndex < 0);

        if (urlNull | cssQueryNull | attributeKeyNull) {
            throw new NullPointerException();
        } else if (isAttributeIndexNotValid) {
            throw new IllegalArgumentException();
        }

        Optional<URL> givenUrl = stringToUrl(url);

        if (givenUrl.isPresent()) {
            Optional<Document> document = parseToDocument(givenUrl.get());

            if (document.isPresent()) {
                Elements elements = document.get().select(cssQuery);

                if (!elements.isEmpty()) {

                    if (elements.size() >= attributeIndex ) {
                        Element element = elements.get(attributeIndex);
                        return (attributeKey.equals("text")) ? Optional.of(element.text()) : Optional.of(element.attr(attributeKey));
                    } else {
                        throw new IndexOutOfBoundsException("Collection size: " + elements.size() + "\nGiven index: " + attributeIndex);
                    }
                } else {
                    throw new Selector.SelectorParseException("No such element or invalid CSS query.");
                }
            }
        }
        return Optional.empty();
    }

    private static Optional<Document> parseToDocument(URL url) {
        try {
            return Optional.of(Jsoup.parse(url, 5000));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private static Optional<URL> stringToUrl(String urlStringFormat) {
        try {
            return Optional.of(new URL(urlStringFormat));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
