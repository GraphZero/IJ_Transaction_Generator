package generator.generators;

import generator.commands.GenerateTransactionToFileCommand;
import lombok.Setter;
import generator.utility.Tuple;
import generator.writers.json.JsonItem;
import generator.writers.json.JsonTransaction;
import generator.writers.xml.XmlItem;
import generator.writers.xml.XmlTransaction;
import generator.writers.yaml.YamlItem;
import generator.writers.yaml.YamlTransaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

import static generator.generators.TransactionGenerator.RandomDataHelper.getRandomDateTime;
import static generator.generators.TransactionGenerator.RandomDataHelper.getRandomIntWithBound;

public class TransactionGenerator {
    private List<Tuple<String, Double>> rawItems;
    @Setter private GenerateTransactionToFileCommand command;

    public ArrayList<JsonTransaction> generateJsons(List<Tuple<String, Double>> rawItems, GenerateTransactionToFileCommand command){
        this.command = command;
        this.rawItems = rawItems;
        return generateConcreteTransactions(jsonTransactionSupplier(), jsonItemSupplier());
    }

    public ArrayList<XmlTransaction>  generateXml(List<Tuple<String, Double>> rawItems, GenerateTransactionToFileCommand command){
        this.command = command;
        this.rawItems = rawItems;
        return generateConcreteTransactions(xmlTransactionSupplier(), xmlItemSupplier());
    }

    public ArrayList<YamlTransaction>  generateYaml(List<Tuple<String, Double>> rawItems, GenerateTransactionToFileCommand command){
        this.command = command;
        this.rawItems = rawItems;
        return generateConcreteTransactions(yamlTransactionSupplier(), yamlItemSupplier());
    }

    private <T extends Transaction, E extends Item> ArrayList<T> generateConcreteTransactions(TransactionSupplier<T, E> transactionSupplier, BiFunction<Integer, Integer, E> itemsSupplier) {
        ArrayList<T> transactionsToSave = new ArrayList<>();
        for (int i = 1; i <= command.getEventsCount(); i++) {
            transactionsToSave.add(generateSingleTransaction (i, transactionSupplier, itemsSupplier));
        }
        return transactionsToSave;
    }

    private <T extends Transaction, E extends Item> T generateSingleTransaction(int id, TransactionSupplier<T, E> transactionSupplier, BiFunction<Integer, Integer, E> itemsSupplier) {
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

