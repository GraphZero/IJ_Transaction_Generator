package generators;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import readers.CsvFileReader;
import utility.Tuple;
import writers.json.JsonFileWriter;
import writers.xml.XmlFileWriter;
import writers.xml.XmlItem;
import writers.xml.XmlTransaction;
import writers.yaml.YamlFileWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.List;

public class TransactionGeneratorManager {
    private static final Logger logger = LogManager.getLogger(TransactionGeneratorManager.class);
    private final TransactionGenerator transactionGenerator;
    private boolean isReadyToCreateTransactions = false;

    @Setter
    private GenerateTransactionCommand command;

    private TransactionGeneratorManager(CsvFileReader csvFileReader, GenerateTransactionCommand command) {
        this.command = command;
        List<Tuple<String, Double>> items = csvFileReader.getItems(command.getItemsFilePath());
        if ( items.size() > 0  ){
            this.transactionGenerator = new TransactionGenerator(items, command);
            isReadyToCreateTransactions = true;
            logger.info("Transaction generator is ready.");
        } else{
            this.transactionGenerator = null;
            isReadyToCreateTransactions = false;
            logger.warn("Transaction generator couldn't parse items, it won't generate transactions.");
        }
    }

    public static TransactionGeneratorManager setUpTransactionGenerator(CsvFileReader csvFileReader, GenerateTransactionCommand command) {
        return new TransactionGeneratorManager(csvFileReader, command);
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
        transactionGenerator.generateJsons(new JsonFileWriter(new ObjectMapper()));
    }

    private void generateXml(){
        logger.info("Chose XML file format.");
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
        logger.info("Chose YAML file format.");
        transactionGenerator.generateYaml(new YamlFileWriter(new ObjectMapper()));
    }

}
