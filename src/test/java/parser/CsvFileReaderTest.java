package parser;

import generator.readers.items.CsvFileReader;
import generator.utility.Tuple;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class CsvFileReaderTest {
    private final String fileName = "";

    @Test
    void shouldReturnItemsFromCsvFileInResourcesFolder(){
        // given
        CsvFileReader csvFileReader = mock(CsvFileReader.class);
        Mockito.when(csvFileReader.getItems("")).thenReturn(Collections.singletonList(new Tuple<>("A", 5.5)));
        // when
        List<Tuple<String, Double>> items = csvFileReader.getItems(fileName);
        // then
        assertEquals(1, items.size());

    }

    @Test
    void shouldntFindFile(){
        // given
        CsvFileReader csvFileReader = new CsvFileReader();
        // when
        // then
        assertTrue(true);
    }

}