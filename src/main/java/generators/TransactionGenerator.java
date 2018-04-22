package generators;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import readers.CsvFileReader;
import utility.Tuple;
import writers.IFileWriter;
import writers.json.JsonFileWriter;
import writers.json.JsonItem;
import writers.json.JsonTransaction;
import writers.xml.XmlFileWriter;
import writers.xml.XmlItem;
import writers.xml.XmlTransaction;
import writers.yaml.YamlFileWriter;
import writers.yaml.YamlItem;
import writers.yaml.YamlTransaction;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

import static generators.TransactionGenerator.RandomDataHelper.getRandomDateTime;
import static generators.TransactionGenerator.RandomDataHelper.getRandomIntWithBound;

/**
 * TODO: Wrap random file generators
 */
public class TransactionGenerator {
    private static final Logger logger = LogManager.getLogger(TransactionGenerator.class);
    private final List<Tuple<String, Double>> rawItems;
    private IFileWriter fileWriter;
    @Setter private GenerateTransactionCommand command;

    private TransactionGenerator(CsvFileReader csvFileReader, GenerateTransactionCommand command) {
        this.command = command;
        this.rawItems = csvFileReader.getItems(command.getItemsFilePath());
    }

    public static TransactionGenerator createTransactionGeneratorAndParseItems(CsvFileReader csvFileReader, GenerateTransactionCommand command) {
        return new TransactionGenerator(csvFileReader, command);
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

    protected void generateJsons(){
        logger.info("Choose JSON file format.");
        fileWriter = new JsonFileWriter(new ObjectMapper());
        generateConcreteTransactions(jsonTransactionSupplier(), jsonItemSupplier());
    }

    protected void generateXml(){
        logger.info("Choose XML file format.");
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(XmlTransaction.class, Transaction.class, XmlItem.class, Item.class);
            fileWriter = new XmlFileWriter(jaxbContext.createMarshaller());
            generateConcreteTransactions(xmlTransactionSupplier(), xmlItemSupplier());
        } catch (JAXBException e) {
            logger.error("Couldn't initialize jaxb context.");
            e.printStackTrace();
        }
    }

    protected void generateYaml(){
        logger.info("Choose YAML file format.");
        fileWriter = new YamlFileWriter(new ObjectMapper());
        generateConcreteTransactions(yamlTransactionSupplier(), yamlItemSupplier());
    }

    protected  <T extends Transaction, E extends Item> void generateConcreteTransactions(TransactionSupplier<T, E> transactionSupplier, BiFunction<Integer, Integer, E> itemsSupplier) {
        ArrayList<T> transactionsToSave = new ArrayList<>();
        for (int i = 1; i <= command.getEventsCount(); i++) {
            transactionsToSave.add(generateSingleTransaction (i, transactionSupplier, itemsSupplier));
        }
        fileWriter.writeValue(command.getOutFilePath(), transactionsToSave);
    }

    protected <T extends Transaction, E extends Item> T generateSingleTransaction(int id, TransactionSupplier<T, E> transactionSupplier, BiFunction<Integer, Integer, E> itemsSupplier) {
        int quantityOfItemsGenerated = getRandomIntWithBound(command.getGeneratedItemsRange().getFirst(), command.getGeneratedItemsRange().getSecond());
        ArrayList<E> items = generateItems(quantityOfItemsGenerated, itemsSupplier);
        return transactionSupplier.supply(id, items);
    }

    private <T extends Item> ArrayList<T> generateItems(int quantity, BiFunction<Integer, Integer, T> itemsSupplier) {
        ArrayList<T> items = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            int quantityOfItem = getRandomIntWithBound(command.getItemsQuantityRange().getFirst(), command.getItemsQuantityRange().getSecond());
            int randomItem = new Random().nextInt(rawItems.size());
            items.add( itemsSupplier.apply(quantityOfItem, randomItem) );
        }
        return items;
    }

    private BiFunction<Integer, Integer, JsonItem> jsonItemSupplier(){
        return (quantityOfItem, randomItem) -> new JsonItem(rawItems.get(randomItem).getFirst(), quantityOfItem, rawItems.get(randomItem).getSecond() );
    }

    private TransactionSupplier<JsonTransaction, JsonItem> jsonTransactionSupplier(){
        return (id, items) -> new JsonTransaction(
                id,
                getRandomDateTime(command.getDateRange()).toString(),
                getRandomIntWithBound(command.getCustomerIdRange().getFirst(), command.getCustomerIdRange().getSecond()),
                items,
                items.stream().mapToDouble(x -> x.getQuantity() * x.getPrice()).sum());
    }

    private BiFunction<Integer, Integer, XmlItem> xmlItemSupplier(){
        return (quantityOfItem, randomItem) -> new XmlItem(rawItems.get(randomItem).getFirst(), quantityOfItem, rawItems.get(randomItem).getSecond() );
    }

    private TransactionSupplier<XmlTransaction, XmlItem> xmlTransactionSupplier(){
        return (id, items) -> new XmlTransaction(
                id,
                getRandomDateTime(command.getDateRange()).toString(),
                getRandomIntWithBound(command.getCustomerIdRange().getFirst(), command.getCustomerIdRange().getSecond()),
                items,
                items.stream().mapToDouble(x -> x.getQuantity() * x.getPrice()).sum());
    }

    private BiFunction<Integer, Integer, YamlItem> yamlItemSupplier(){
        return (quantityOfItem, randomItem) -> new YamlItem(rawItems.get(randomItem).getFirst(), quantityOfItem, rawItems.get(randomItem).getSecond() );
    }

    private TransactionSupplier<YamlTransaction, YamlItem> yamlTransactionSupplier(){
        return (id, items) -> new YamlTransaction(
                id,
                getRandomDateTime(command.getDateRange()).toString(),
                getRandomIntWithBound(command.getCustomerIdRange().getFirst(), command.getCustomerIdRange().getSecond()),
                items,
                items.stream().mapToDouble(x -> x.getQuantity() * x.getPrice()).sum());
    }

    @FunctionalInterface
    interface TransactionSupplier<T extends Transaction, E extends Item>{
        T supply(int id, ArrayList<E> items );
    }

    static class RandomDataHelper {
        private static final Random r = new Random();

        public static int getRandomIntWithBound(int lower, int upper) {
            return r.nextInt(upper - lower) + lower;
        }

        public static LocalDateTime getRandomDateTime(Tuple<LocalDateTime, LocalDateTime> ldtt) {
            return LocalDateTime.now();
        }

    }

}

