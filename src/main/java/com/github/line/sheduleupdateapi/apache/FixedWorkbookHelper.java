package com.github.line.sheduleupdateapi.apache;

import com.github.line.sheduleupdateapi.utils.TemporaryFile;
import com.github.line.sheduleupdateapi.utils.UrlInputStreamFetcher;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.jdbc.Work;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;

public final class FixedWorkbookHelper extends UrlInputStreamFetcher{

    public Optional<Workbook> fetchWorkbook(Object url) {
        Optional<InputStream> inputStream = super.fetch(url);
        if (inputStream.isPresent()) {
            Optional<File> file = TemporaryFile.writeInputStreamToFile(inputStream.get());

            if (file.isPresent()) {
                try {
                    return Optional.of(WorkbookFactory.create(file.get()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Optional.empty();
    }

    public Map<Integer, BlockingQueue<String>> getTargetColumns(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        sheet.rowIterator();


    }
}
