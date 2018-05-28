package generator.commands;

import generator.utility.Tuple;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@AllArgsConstructor
public class GenerateTransactionResponseCommand {
    private Tuple<Integer, Integer> customerIdRange;
    private Tuple<LocalDateTime, LocalDateTime> dateRange;
    private Tuple<Integer, Integer> generatedItemsRange;
    private Tuple<Integer, Integer> itemsQuantityRange;
    private long eventsCount;
    private FileType fileType;

    public GenerateTransactionToFileCommand toFileCommand(){
        return GenerateTransactionToFileCommand.builder()
                .customerIdRange(customerIdRange)
                .dateRange(dateRange)
                .generatedItemsRange(generatedItemsRange)
                .itemsQuantityRange(itemsQuantityRange)
                .eventsCount(eventsCount)
                .fileType(fileType)
                .itemsFilePath("")
                .outFilePath("")
                .build();
    }

}
