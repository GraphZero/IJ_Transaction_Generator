package writers.yaml;

import generators.Item;
import generators.Transaction;

import java.util.ArrayList;

public class YamlTransaction extends Transaction {
    public YamlTransaction(long id, String timeStamp, long customer_id, ArrayList<? extends Item> jsonItems, double sum) {
        super(id, timeStamp, customer_id, jsonItems, sum);
    }
}
