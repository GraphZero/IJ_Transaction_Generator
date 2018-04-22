package parser;

import org.junit.jupiter.api.Test;
import readers.CsvFileReader;
import utility.Tuple;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CsvFileReaderTest {
    private final String fileName = "testItems.csv";

    @Test
    void shouldReturnItemsFromCsvFileInResourcesFolder(){
        // given
        CsvFileReader csvFileReader = new CsvFileReader();
        // when
        List<Tuple<String, Double>> items = csvFileReader.getItems(fileName);
        // then
        assertEquals(3, items.size());
    }

    @Test
    void shouldntFindFile(){
        // given
        CsvFileReader csvFileReader = new CsvFileReader();
        // when
        // then
        assertThrows(CsvFileReader.InputItemFileNotFoundException.class, () -> csvFileReader.getItems("asdasdasd"));
    }

}