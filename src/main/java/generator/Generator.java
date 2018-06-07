package generator;

import generator.generators.file.FileTransactionGeneratorManager;
import generator.readers.CommandLineReader;
import generator.readers.Parser;
import generator.readers.items.CsvFileReader;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Generator {
    private static final Logger logger = LogManager.getLogger(Generator.class);

    public static void main(String[] args) throws ParseException {
        CsvFileReader csvFileReader = new CsvFileReader();
        CommandLineReader commandLineReader = CommandLineReader.readCommandLines(args);

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
        //SpringApplication.run(Generator.class, args);
    }

}
