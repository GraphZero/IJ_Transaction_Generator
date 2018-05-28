package generator.commands;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import generator.utility.Tuple;

import java.time.LocalDateTime;

@Value
@Builder
@AllArgsConstructor
public class GenerateTransactionToFileCommand {
    private Tuple<Integer, Integer> customerIdRange;
    private Tuple<LocalDateTime, LocalDateTime> dateRange;
    private Tuple<Integer, Integer> generatedItemsRange;
    private Tuple<Integer, Integer> itemsQuantityRange;
    private long eventsCount;
    private String itemsFilePath;
    private String outFilePath;
    private FileType fileType;

}

