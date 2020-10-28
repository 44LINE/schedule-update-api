package com.github.line.sheduleupdateapi.utils;

import org.jsoup.select.Selector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class CustomExtractorTest {
    private final String VALID_SOURCE_URL = "https://it.pk.edu.pl/?page=rz";
    private final int VALID_ATTRIBUTE_INDEX = 0;

    //url
    private final static String VALID_DOWNLOAD_URL_CSS_QUERY = "div[class=alert alert-light] a[href]";
    private final static String VALID_DOWNLOAD_URL_ATTRIBUTE_KEY = "href";

    //date


    @Test
    public void extractFromHtmlDocument_urlNull_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class,
                () -> CustomExtractor.extractFromHtmlDocument(null, VALID_DOWNLOAD_URL_CSS_QUERY, VALID_DOWNLOAD_URL_ATTRIBUTE_KEY, VALID_ATTRIBUTE_INDEX));
    }

    @Test
    public void extractFromHtmlDocument_urlNotOkUrl_throwsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> CustomExtractor.extractFromHtmlDocument("http:someHost", VALID_DOWNLOAD_URL_CSS_QUERY, VALID_DOWNLOAD_URL_ATTRIBUTE_KEY, VALID_ATTRIBUTE_INDEX));
    }

    @Test
    public void extractFromHtmlDocument_urlOkAndCssQueryNotOk_throwsSelectorParseException() {
        Assertions.assertThrows(Selector.SelectorParseException.class,
                () -> CustomExtractor.extractFromHtmlDocument(VALID_SOURCE_URL, "INVALID", VALID_DOWNLOAD_URL_ATTRIBUTE_KEY, VALID_ATTRIBUTE_INDEX));

    }

    @Test
    public void extractFromHtmlDocument_attributeKeyNull_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class,
                () -> CustomExtractor.extractFromHtmlDocument(VALID_SOURCE_URL, VALID_DOWNLOAD_URL_CSS_QUERY, null, VALID_ATTRIBUTE_INDEX));
    }

    @Test
    public void extractFromHtmlDocument_attributeIndexNegativeValue_throwsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> CustomExtractor.extractFromHtmlDocument(VALID_SOURCE_URL, VALID_DOWNLOAD_URL_CSS_QUERY, VALID_DOWNLOAD_URL_ATTRIBUTE_KEY, -1));
    }

    @Test
    public void extractFromHtmlDocument_attributeIndexNotOk_throwsIndexOutOfBoundsException() {
        Assertions.assertThrows(IndexOutOfBoundsException.class,
                () -> CustomExtractor.extractFromHtmlDocument(VALID_SOURCE_URL, VALID_DOWNLOAD_URL_CSS_QUERY, VALID_DOWNLOAD_URL_ATTRIBUTE_KEY, 9999));
    }

    @Test
    public void extractDownloadUrl_returnsTrue() {

        //when
        URL result = CustomExtractor.extractDownloadUrl().get();

        //then
        URL expected = expectedUrl();

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void extractLatestUpdateDate_returnsTrue() {

        //given
        String dateToParse = "2020-10-19 11:28";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        //when
        LocalDateTime result = CustomExtractor.extractLatestUpdateDate().get();

        //then
        LocalDateTime expected = LocalDateTime.parse(dateToParse, formatter);

        Assertions.assertEquals(expected, result);
    }

    private static URL expectedUrl() {
        try {
            return new URL("https://it.pk.edu.pl/download/f1e1d9719daf893eb195dfd347664005/19102020_8_INFORMATYKA%20zima%20rozklad%20NIESTACJONARNE%202020_2021.xls");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}