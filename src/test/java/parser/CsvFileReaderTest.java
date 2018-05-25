package parser;

import org.junit.jupiter.api.Test;
import readers.CsvFileReader;
import utility.Tuple;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CsvFileReaderTest {
    private final String fileName = "E:\\Java Produkcyjna\\Generator\\src\\main\\resources\\items.csv";

    @Test
    void shouldReturnItemsFromCsvFileInResourcesFolder(){
        // given
        CsvFileReader csvFileReader = new CsvFileReader();
        // when
        List<Tuple<String, Double>> items = csvFileReader.getItems(fileName);
        // then
        assertEquals(9, items.size());
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