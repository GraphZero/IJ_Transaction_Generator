package readers;
import generators.TransactionGenerator;
import lombok.Getter;
import org.apache.commons.cli.*;
import org.apache.commons.cli.CommandLineParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Getter
public class CommandLineReader {
    private static final Logger logger = LogManager.getLogger(CommandLineReader.class);
    private final Options options = new Options();
    private final CommandLineParser parser = new DefaultParser();
    private CommandLine cmd;

    private CommandLineReader(String[] args) throws ParseException  {
        options.addOption("customerIds", true, "gets customer id");
        options.addOption("dateRange", true, "date range");
        options.addOption("itemsFile", true, "item source file");
        options.addOption("itemsCount", true, "number of items generated ");
        options.addOption("itemsQuantity", true, "quantity of item");
        options.addOption("eventsCount", true, "number of transactions");
        options.addOption("outDir", true, "destination file");
        options.addOption("format", true, "format of file");
        try{
            cmd = parser.parse( options, args);
        } catch(UnrecognizedOptionException e){
            logger.error("UnrecognizedOptionException, what are you doing?");
        }

    }

    public static CommandLineReader readCommandLines(String[] args) throws ParseException {
        return new CommandLineReader(args);
    }

}
