import generators.TransactionGeneratorManager;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import readers.CommandLineParser;
import readers.CommandLineReader;
import readers.ConfigurationReader;
import readers.CsvFileReader;

import java.io.IOException;
import java.util.Arrays;

public class Generator {
    private static final Logger logger = LogManager.getLogger(Generator.class);

    public static void main(String[] args) throws ParseException{
        CsvFileReader csvFileReader = new CsvFileReader();
        try {
            CommandLineReader commandLineReader = CommandLineReader.readCommandLines(ConfigurationReader.getCommands());
            new CommandLineParser()
                    .parseCommandLine(commandLineReader.getCmd())
                    .ifPresent( generateTransactionCommand-> {
                        try {
                            TransactionGeneratorManager transactionGeneratorManager = TransactionGeneratorManager.setUpTransactionGenerator(csvFileReader, generateTransactionCommand);
                            transactionGeneratorManager.generateTransactions();
                        } catch (CsvFileReader.InputItemFileNotFoundException e) {
                            logger.error("Couldn't find input file");
                        }
                    });
        } catch (IOException e) {
            logger.error("Couldn't find application properties file.");
        }

    }


}
