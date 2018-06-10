package generator.readers;

import generator.commands.FileType;
import generator.commands.GenerateTransactionResponseCommand;
import generator.commands.GenerateTransactionToFileCommand;
import org.apache.commons.cli.CommandLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import generator.utility.Tuple;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Parser {
    private static final Logger logger = LogManager.getLogger(Parser.class);

    public Optional<GenerateTransactionToFileCommand> parseCommandLine(CommandLine commandLine1) {
        return Optional.ofNullable(commandLine1).map(commandLine -> {
            try {
                GenerateTransactionToFileCommand command = new GenerateTransactionToFileCommand(
                        parseRange(commandLine.getOptionValue("customerIds", "1:5")),
                        parseDateRange(commandLine.getOptionValue("dateRange", LocalDateTime.now() + "-0100:" + LocalDateTime.now().plusDays(1))),
                        parseRange(commandLine.getOptionValue("itemsCount", "1:5")),
                        parseRange(commandLine.getOptionValue("itemsQuantity", "1:5")),
                        Long.parseLong(commandLine.getOptionValue("eventsCount", "100")),
                        commandLine.getOptionValue("itemsFile", "/"),
                        commandLine.getOptionValue("outDir", "/"),
                        parseFileType(commandLine.getOptionValue("format", "JSON"))
                );
                logger.info("Successfully converted command.");
                return command;
            } catch (NumberFormatException e) {
                logger.error("Wrong command parameters!");
                return null;
            }
        });
    }

    public GenerateTransactionResponseCommand parseStringArgs(String[] args, int quantity, FileType itemsType) {
        try {
            GenerateTransactionResponseCommand command = new GenerateTransactionResponseCommand(
                    parseRange(args[0]),
                    parseDateRange(args[1]),
                    parseRange(args[2]),
                    parseRange(args[3]),
                    quantity,
                    itemsType
            );
            logger.info("Successfully converted command.");
            return command;
        } catch (NumberFormatException e) {
            logger.error("Wrong command parameters!");
            return null;
        }
    }

    public static List<String> getJmsConfigurationOptions(CommandLine commandLine1){
        return Optional.ofNullable(commandLine1).map(commandLine -> {
            List<String> options = new ArrayList<>();
            options.add(commandLine1.getOptionValue("broker", "tcp://192.168.99.100:32768/"));
            options.add(commandLine1.getOptionValue("queue", "transactions-queue"));
            options.add(commandLine1.getOptionValue("topic", "transaction-topics"));
            logger.info("Successfully converted command.");
            return options;
        }).orElseGet(ArrayList::new);
    }

    protected Tuple<Integer, Integer> parseRange(String s) {
        String[] idRange = s.trim().split(":");
        if (Integer.parseInt(idRange[0]) < 0 || Integer.parseInt(idRange[1]) < 0) {
            throw new WrongRangeException();
        }
        return new Tuple<>(Integer.parseInt(idRange[0]), Integer.parseInt(idRange[1]));
    }

    protected Tuple<LocalDateTime, LocalDateTime> parseDateRange(String s) {
        String[] idRange = s.replaceAll("\"", "").trim().split("-0100:");
        return new Tuple<>(LocalDateTime.parse(idRange[0]), LocalDateTime.parse(idRange[1].replaceAll("-0100", "")));
    }

    protected FileType parseFileType(String format) {
        return FileType.valueOf(format.toUpperCase());
    }

    public class WrongRangeException extends RuntimeException {
    }

}
