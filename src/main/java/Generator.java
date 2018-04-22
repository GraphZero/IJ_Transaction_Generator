import generators.TransactionGenerator;
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
                        TransactionGenerator transactionGenerator = TransactionGenerator.createTransactionGeneratorAndParseItems(csvFileReader, generateTransactionCommand);
                        transactionGenerator.generateTransactions();
                    } catch (CsvFileReader.InputItemFileNotFoundException e) {
                        logger.error("Couldn't find input file");
                    }
                });
    }


}
