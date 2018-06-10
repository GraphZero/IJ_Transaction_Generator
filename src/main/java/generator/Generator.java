package generator;

import generator.commands.GenerateTransactionToFileCommand;
import generator.generators.file.FileTransactionGeneratorManager;
import generator.readers.CommandLineReader;
import generator.readers.ConfigurationManager;
import generator.readers.Parser;
import generator.readers.items.CsvFileReader;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
@EnableJms
@EnableScheduling
public class Generator {
    private static final Logger logger = LogManager.getLogger(Generator.class);
    public static String ORDER_TOPIC = "transaction-topics";
    public static GenerateTransactionToFileCommand generateTransactionToFileCommand;

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    public static void generateTransactions(CommandLineReader commandLineReader, CsvFileReader csvFileReader){
        new Parser()
                .parseCommandLine(commandLineReader.getCmd())
                .ifPresent( generateTransactionCommand-> {
                    try {
                        generateTransactionToFileCommand = generateTransactionCommand;
                        FileTransactionGeneratorManager transactionGeneratorManager
                                = FileTransactionGeneratorManager.setUpTransactionGenerator(csvFileReader, generateTransactionCommand);
                        transactionGeneratorManager.generateTransactions();
                    } catch (CsvFileReader.InputItemFileNotFoundException e) {
                        logger.error("Couldn't find input file");
                    }
                });
    }


    public static void main(String[] args) throws Exception {
        CsvFileReader csvFileReader = new CsvFileReader();
        CommandLineReader commandLineReader = null;

        commandLineReader = CommandLineReader.readCommandLines(args);

        generateTransactions(commandLineReader, csvFileReader);

        try {
            commandLineReader = CommandLineReader.readCommandLines(ConfigurationManager.getCommands());
            generateTransactions(commandLineReader, csvFileReader);
        } catch (IOException e) {
            logger.error("Couldn't find settings file");
        }

        // JMS
        List<String> config = Parser.getJmsConfigurationOptions(commandLineReader.getCmd());
        ConfigurationManager.modifyJMSAddress(config.get(0));
        ORDER_TOPIC = config.get(1);

        SpringApplication.run(Generator.class, args);
    }

}
