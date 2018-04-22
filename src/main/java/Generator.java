import generators.TransactionGeneratorManager;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import readers.CommandLineParser;
import readers.CommandLineReader;
import readers.CsvFileReader;

public class Generator {
    private static final Logger logger = LogManager.getLogger(Generator.class);

    public static void main(String[] args) throws ParseException{
        CsvFileReader csvFileReader = new CsvFileReader();
        CommandLineReader commandLineReader = CommandLineReader.readCommandLines(args);
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
    }


}
