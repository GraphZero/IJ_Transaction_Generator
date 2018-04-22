package generators;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import readers.CsvFileReader;
import writers.json.JsonFileWriter;
import writers.xml.XmlFileWriter;
import writers.xml.XmlItem;
import writers.xml.XmlTransaction;
import writers.yaml.YamlFileWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class TransactionGeneratorManager {
    private static final Logger logger = LogManager.getLogger(TransactionGeneratorManager.class);
    private final TransactionGenerator transactionGenerator;

    @Setter
    private GenerateTransactionCommand command;

    private TransactionGeneratorManager(CsvFileReader csvFileReader, GenerateTransactionCommand command) {
        this.command = command;
        this.transactionGenerator = new TransactionGenerator(csvFileReader.getItems(command.getItemsFilePath()), command);
    }

    public static TransactionGeneratorManager setUpTransactionGenerator(CsvFileReader csvFileReader, GenerateTransactionCommand command) {
        return new TransactionGeneratorManager(csvFileReader, command);
    }

    public void generateTransactions(){
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
    }

    private void generateJsons(){
        logger.info("Choose JSON file format.");
        transactionGenerator.generateJsons(new JsonFileWriter(new ObjectMapper()));
    }

    private void generateXml(){
        logger.info("Choose XML file format.");
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(XmlTransaction.class, Transaction.class, XmlItem.class, Item.class);
            transactionGenerator.generateXml( new XmlFileWriter(jaxbContext.createMarshaller()));
        } catch (JAXBException e) {
            logger.error("Couldn't initialize jaxb context.");
            e.printStackTrace();
        }
    }

    private void generateYaml(){
        logger.info("Choose YAML file format.");
        transactionGenerator.generateYaml(new YamlFileWriter(new ObjectMapper()));
    }

}
