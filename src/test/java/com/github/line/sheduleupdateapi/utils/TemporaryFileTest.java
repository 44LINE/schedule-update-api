package com.github.line.sheduleupdateapi.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TemporaryFileTest {

    @Test
    public void writeInputStreamToFile_inputStreamNull_throwsNullPointerException() {

        //then
        Assertions.assertThrows(NullPointerException.class, () -> TemporaryFile.writeInputStreamToFile(null));
    }

    @Test
    public void writeInputStreamToFile_inputStreamOk_returnsTrue(){

        //given
        File storedFile = new File("src/test/resources/test.xls");
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(storedFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //when
        File returnedFile = TemporaryFile.writeInputStreamToFile(inputStream).get();

        //then
        byte[] expected = null;
        byte[] result = null;

        try {
            expected = Files.readAllBytes(storedFile.toPath());
            result = Files.readAllBytes(returnedFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertArrayEquals(expected,result);
    }

    @Test
    public void writeInputStreamToFile_inputStreamOkDifferentResource_returnsFalse(){

        //given
        File storedFile = new File("src/test/resources/test.xls");
        File differentStoredFile = new File("src/test/resources/test2.xls");
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(storedFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //when
        File returnedFile = TemporaryFile.writeInputStreamToFile(inputStream).get();

        //then
        byte[] expected = null;
        byte[] result = null;

        try {
            expected = Files.readAllBytes(differentStoredFile.toPath());
            result = Files.readAllBytes(returnedFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assertions.assertFalse(Arrays.equals(expected, result));
    }

}