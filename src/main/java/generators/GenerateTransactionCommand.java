package generators;


import lombok.Value;
import utility.Tuple;

import java.time.LocalDateTime;

@Value
public class GenerateTransactionCommand {
    private Tuple<Integer, Integer> customerIdRange;
    private Tuple<LocalDateTime, LocalDateTime> dateRange;
    private Tuple<Integer, Integer> generatedItemsRange;
    private Tuple<Integer, Integer> itemsQuantityRange;
    private long eventsCount;
    private String itemsFilePath;
    private String outFilePath;
    private FileType fileType;

    public enum FileType{
        XML,
        JSON,
        YAML
    }

}

