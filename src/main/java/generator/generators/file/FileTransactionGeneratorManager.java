package generator.generators.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import generator.commands.GenerateTransactionToFileCommand;
import generator.generators.Item;
import generator.generators.Transaction;
import generator.generators.TransactionGenerator;
import generator.readers.items.CsvFileReader;
import generator.writers.IFileWriter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import generator.utility.Tuple;
import generator.writers.json.JsonFileWriter;
import generator.writers.xml.XmlFileWriter;
import generator.writers.xml.XmlItem;
import generator.writers.xml.XmlTransaction;
import generator.writers.yaml.YamlFileWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.List;

public class FileTransactionGeneratorManager {
    private static final Logger logger = LogManager.getLogger(FileTransactionGeneratorManager.class);
    private final TransactionGenerator transactionGenerator;
    private boolean isReadyToCreateTransactions = false;
    private IFileWriter fileWriter;
    private List<Tuple<String, Double>> items;

    @Setter
    private GenerateTransactionToFileCommand command;

    private FileTransactionGeneratorManager(CsvFileReader csvFileReader, GenerateTransactionToFileCommand command) {
        this.command = command;
        items = csvFileReader.getItems(command.getItemsFilePath());
        if ( items.size() > 0  ){
            this.transactionGenerator = new TransactionGenerator();
            isReadyToCreateTransactions = true;
            logger.info("Transaction generator is ready.");
        } else{
            this.transactionGenerator = null;
            isReadyToCreateTransactions = false;
            logger.warn("Transaction generator couldn't parse items, it won't generate transactions.");
        }
    }

    public static FileTransactionGeneratorManager setUpTransactionGenerator(CsvFileReader csvFileReader, GenerateTransactionToFileCommand command) {
        return new FileTransactionGeneratorManager(csvFileReader, command);
    }

    public void generateTransactions(){
        if ( isReadyToCreateTransactions ){
            switch (command.getFileType()){
                case JSON:
                default:
                    generateJsons();
                    break;

                case XML:
                    generateXml();
                    break;

                case YAML:
                    generateYaml();
                    break;
            }
        } else{
            logger.info("Transaction generator is not ready!");
        }
    }

    private void generateJsons(){
        logger.info("Chose JSON file format.");
        fileWriter = new JsonFileWriter(new ObjectMapper());
        fileWriter.writeValue(command.getOutFilePath(), transactionGenerator.generateJsons(items, command));
    }

    private void generateXml(){
        logger.info("Chose XML file format.");
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(XmlTransaction.class, Transaction.class, XmlItem.class, Item.class);
            fileWriter = new XmlFileWriter(jaxbContext.createMarshaller());
            fileWriter.writeValue(command.getOutFilePath(), transactionGenerator.generateXml(items, command));
        } catch (JAXBException e) {
            logger.error("Couldn't initialize jaxb context.");
            e.printStackTrace();
        }
    }

    private void generateYaml(){
        logger.info("Chose YAML file format.");
        fileWriter = new YamlFileWriter(new ObjectMapper());
        fileWriter.writeValue(command.getOutFilePath(), transactionGenerator.generateYaml(items, command));
    }


}
