package readers;

import generators.GenerateTransactionCommand;
import org.apache.commons.cli.CommandLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utility.Tuple;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

public class CommandLineParser {
    private static final Logger logger = LogManager.getLogger(CommandLineParser.class);

    public Optional<GenerateTransactionCommand> parseCommandLine(CommandLine commandLine1) {
        return Optional.ofNullable(commandLine1).map(commandLine -> {
            try {
                GenerateTransactionCommand command = new GenerateTransactionCommand(
                        parseRange(commandLine.getOptionValue("customerIds", "1:5")),
                        parseDateRange(commandLine.getOptionValue("dateRange", LocalDateTime.now() + "-0100:" + LocalDateTime.now().plusDays(1))),
                        parseRange(commandLine.getOptionValue("itemsCount", "1:5")),
                        parseRange(commandLine.getOptionValue("itemsQuantity", "1:5")),
                        Long.parseLong(commandLine.getOptionValue("eventsCount", "100")),
                        commandLine.getOptionValue("itemsFile", "/"),
                        "." + commandLine.getOptionValue("outDir", "/"),
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

    protected GenerateTransactionCommand.FileType parseFileType(String format) {
        return GenerateTransactionCommand.FileType.valueOf(format.toUpperCase());
    }

    public class WrongRangeException extends RuntimeException {
    }

}
