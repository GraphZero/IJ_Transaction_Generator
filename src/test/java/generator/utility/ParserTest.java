package generator.utility;

import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import generator.readers.CommandLineReader;
import generator.readers.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {
    private CommandLineReader commandLineReader;
    private Parser converter;

    @BeforeEach
    void setConverter(){
        converter = new Parser();
    }

    @Test
    void shouldConvertCorrectCommands() throws ParseException {
        // given

        String[] args = {
                "-customerIds", "1:20",
                "-dateRange", "2018-03-08T00:00:00.000-0100:2018-03-08T23:59:59.999-0100",
                "-itemsFile", "items.csv",
                "-itemsCount", "5:15",
                "-itemsQuantity", "1:30",
                "-eventsCount", "1000",
                "-outDir", "./output",
        };
        commandLineReader = CommandLineReader.readCommandLines(args);
        // when
        // then
        assertEquals( 1000, converter.parseCommandLine(commandLineReader.getCmd()).get().getEventsCount());
    }

    @Test
    void shouldntThrowWrongRangeExceptionWithIncorrectData() throws ParseException {
        // given
        String[] args = {
                "-customerIds", "-5:-3",
                "-dateRange", "2018-03-08T00:00:00.000-0100:2018-03-08T23:59:59.999-0100",
                "-itemsFile", "items.csv",
                "-itemsCount", "-5:0",
                "-itemsQuantity", "1:30",
                "-eventsCount", "1000",
                "-outDir", "./output",
        };
        commandLineReader = CommandLineReader.readCommandLines(args);
        // when
        // then
        assertThrows(Parser.WrongRangeException.class,
                () -> converter.parseCommandLine(commandLineReader.getCmd()));
    }


}