package generator.readers;

import generator.readers.items.HttpCsvFileReader;
import org.junit.jupiter.api.Test;

class HttpCsvFileReaderTest {
    private HttpCsvFileReader httpCsvFileReader;

    @Test
    void getJsonItems() {
        // given
        httpCsvFileReader = new HttpCsvFileReader();
        // when
        // then
        httpCsvFileReader.getJsonItems("");
    }

    @Test
    void getXmlItems() {
        // given
        httpCsvFileReader = new HttpCsvFileReader();
        // when
        // then
        //httpCsvFileReader.getXmlItems("");
    }

    @Test
    void getYamlItems() {
        // given
        // when
        // then
    }
}