package generator;

import generator.generators.file.FileTransactionGeneratorManager;
import generator.readers.CommandLineReader;
import generator.readers.ConfigurationReader;
import generator.readers.Parser;
import generator.readers.items.CsvFileReader;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Generator {
    private static final Logger logger = LogManager.getLogger(Generator.class);

    public static void generateTransactions(CommandLineReader commandLineReader, CsvFileReader csvFileReader){
        new Parser()
                .parseCommandLine(commandLineReader.getCmd())
                .ifPresent( generateTransactionCommand-> {
                    try {
                        FileTransactionGeneratorManager transactionGeneratorManager
                                = FileTransactionGeneratorManager.setUpTransactionGenerator(csvFileReader, generateTransactionCommand);
                        transactionGeneratorManager.generateTransactions();
                    } catch (CsvFileReader.InputItemFileNotFoundException e) {
                        logger.error("Couldn't find input file");
                    }
                });
    }

    public static void main(String[] args) throws ParseException {
        CsvFileReader csvFileReader = new CsvFileReader();
        CommandLineReader commandLineReader = null;

        commandLineReader = CommandLineReader.readCommandLines(args);

        generateTransactions(commandLineReader, csvFileReader);

        try {
            commandLineReader = CommandLineReader.readCommandLines(ConfigurationReader.getCommands());
            generateTransactions(commandLineReader, csvFileReader);
        } catch (IOException e) {
            logger.error("Couldn't find settings file");
        }
        //SpringApplication.run(Generator.class, args);
    }

}
