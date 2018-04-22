package writers.yaml;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import writers.IFileWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class YamlFileWriter implements IFileWriter<YamlTransaction> {
    private static final Logger logger = LogManager.getLogger(YamlFileWriter.class);
    private final ObjectMapper objectMapper;

    public YamlFileWriter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void writeValue(String filePath, ArrayList<YamlTransaction> transactionsToSave){
        try {
            objectMapper.writeValue(new File(filePath+ ".yaml"), transactionsToSave);
            logger.info("Saved YAML transactions.");
        } catch (IOException e) {
            logger.error("Couldnt map YAML transactions...");
            e.printStackTrace();
        }
    }
}
