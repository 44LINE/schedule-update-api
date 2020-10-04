package com.github.line.sheduleupdateapi.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sun.misc.IOUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class UrlInputStreamFetcherTest {

    @Test
    public void fetch_urlNull_throwsNullPointerException() {

        //given
        UrlInputStreamFetcher urlInputStreamFetcher = new UrlInputStreamFetcher();

        //then
        Assertions.assertThrows(NullPointerException.class, () -> urlInputStreamFetcher.fetch(null));
    }

    @Test
    public void fetch_urlInstanceNotOk_throwsIllegalArgumentException() {

        //given
        Object urlInstance = new Object();
        UrlInputStreamFetcher urlInputStreamFetcher = new UrlInputStreamFetcher();

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> urlInputStreamFetcher.fetch(urlInstance));
    }

    @Test
    public void fetch_urlOk_returnsTrue() {

        //given
        UrlInputStreamFetcher urlInputStreamFetcher = new UrlInputStreamFetcher();
        URL urlInstance = null;

        try {
            urlInstance = new URL("https://it.pk.edu.pl/download/23ee17db23fe77872c03180242895ac6/6_INFORMATYKA%20lato%20rozklad%20NIESTACJONARNE%202019_2020.xls");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        File file = new File("src/test/resources/test.xls");
        byte[] expected = null;

        try {
            expected = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //when
        byte[] result = null;
        try {
            result = IOUtils.readAllBytes(urlInputStreamFetcher.fetch(urlInstance).get());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //then
        assertArrayEquals(expected, result);
    }

    @Test
    public void fetch_urlOkDifferentSources_returnsFalse() {

        //given
        UrlInputStreamFetcher urlInputStreamFetcher = new UrlInputStreamFetcher();

        URL urlInstance = null;
        try {
            urlInstance = new URL("https://it.pk.edu.pl/download/cdda61ee117a8cbd3152612e48adcc92/15_INFORMATYKA-lato-rozklad-STACJONARNE-2019_2020.xls");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        File file = new File("src/test/resources/test.xls");
        byte[] expected = null;

        try {
            expected = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //when
        byte[] result = null;
        try {
            result = IOUtils.readAllBytes(urlInputStreamFetcher.fetch(urlInstance).get());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //then
        Assertions.assertFalse(Arrays.equals(expected, result));
    }
}