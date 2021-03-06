package generator.writers.yaml;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import generator.writers.IFileWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class YamlFileWriter implements IFileWriter<YamlTransaction> {
    private static final Logger logger = LogManager.getLogger(YamlFileWriter.class);
    private final ObjectMapper objectMapper;

    public YamlFileWriter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void writeValue(String filePath, ArrayList<YamlTransaction> transactionsToSave, int id){
        try {
            if ( !Files.exists(Paths.get(filePath ))){
                Files.createDirectories(Paths.get(filePath));
            }
            objectMapper.writeValue(new File(filePath + "/transactionsYaml" + id + ".yaml"), transactionsToSave);
            logger.info("Saved YAML transactions.");
        } catch (IOException e) {
            logger.error("Couldnt map YAML transactions...");
            e.printStackTrace();
        }
    }
}
