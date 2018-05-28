package generator.ui;

import generator.commands.FileType;
import generator.commands.GenerateTransactionResponseCommand;
import generator.generators.rest.ResponseTransactionManager;
import generator.generators.Transaction;
import generator.readers.Parser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransactionController {
    private final ResponseTransactionManager responseTransactionManager;
    private final Parser parser;

    @RequestMapping(value = "/transactions", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<? extends Transaction>> getJsonTransactions(@RequestParam final String customerId,
                                                                           @RequestParam final String dateRange,
                                                                           @RequestParam final String itemsCount,
                                                                           @RequestParam final String itemsQuantity,
                                                                           @RequestParam final int eventsCount,
                                                                           @RequestHeader(value="Accept") String mediaType) {
        String[] args = {customerId, dateRange, itemsCount, itemsQuantity};

        GenerateTransactionResponseCommand command;
        switch (mediaType){
            case "application/json":
                command = parser.parseStringArgs(args, eventsCount, FileType.JSON);
                break;

            case "application/xml":
                command = parser.parseStringArgs(args, eventsCount, FileType.XML);
                break;

            case "application/yml":
                command = parser.parseStringArgs(args, eventsCount, FileType.YAML);
                break;

            default:
                command = parser.parseStringArgs(args, eventsCount, FileType.JSON);
                break;
        }
        return ResponseEntity.ok(responseTransactionManager.generateTransactions(command));
    }

}
