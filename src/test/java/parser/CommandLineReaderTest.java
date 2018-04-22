package parser;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import readers.CommandLineReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandLineReaderTest {
    private CommandLineReader commandLineReader;


    @BeforeEach
    public void setUpCommandsHandler() throws ParseException{
        String[] args = {
                "-customerIds", "1:20"
        };
        commandLineReader = CommandLineReader.readCommandLines(args);
    }

    @Test
    void shouldParseCommandLine() {
        // given
        CommandLine commandLine = commandLineReader.getCmd();
        // when
        // then
        assertEquals("1:20", commandLine.getOptionValue("customerIds"), "CommandLineReader didn't parse argument");
    }
}