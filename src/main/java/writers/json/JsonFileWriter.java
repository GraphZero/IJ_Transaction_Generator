package writers.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import writers.IFileWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JsonFileWriter implements IFileWriter<JsonTransaction> {
    private static final Logger logger = LogManager.getLogger(JsonFileWriter.class);
    private final ObjectMapper objectMapper;

    public JsonFileWriter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void writeValue(String filePath, ArrayList<JsonTransaction> transactionsToSave){
        try {
            objectMapper.writeValue(new File(filePath+ ".json"), transactionsToSave);
            logger.info("Saved JSON transactions.");
        } catch (IOException e) {
            logger.error("Couldnt map JSON transactions...");
            e.printStackTrace();
        }
    }

}
