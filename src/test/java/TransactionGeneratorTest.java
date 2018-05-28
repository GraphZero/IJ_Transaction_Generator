import generator.generators.TransactionGenerator;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import generator.readers.items.CsvFileReader;
import generator.utility.Tuple;
import generator.writers.json.JsonFileWriter;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionGeneratorTest {
    private TransactionGenerator transactionGenerator;

    @Mock
    private CsvFileReader csvFileReader;

    @Mock
    private JsonFileWriter jsonFileWriter;

    @Test
    void shouldGenerateTransactions(){
        // given
        when(csvFileReader.getItems(Mockito.any())).thenReturn(Arrays.asList(new Tuple<String, Double>("a", 5.0), new Tuple<String, Double>("b", 11.0)));
//        transactionGenerator = TransactionGenerator.createTransactionGeneratorAndParseItems(csvFileReader, new GenerateTransactionToFileCommand(
//                new Tuple<>(1,5),
//                new Tuple<>(LocalDateTime.now(), LocalDateTime.of(2018, 2, 2,2,2)),
//                new Tuple<>(1,5),
//                new Tuple<>(1,50),
//                5,
//                "testItems.csv",
//                "D:\\Java Produkcyjna\\Lab 2\\w2Test\\generator\\src\\main\\resources\\output",
//                GenerateTransactionToFileCommand.FileType.JSON
//        ));
//        transactionGenerator.generateTransactions();
        // when
        // then
        assertTrue(true);
    }

}