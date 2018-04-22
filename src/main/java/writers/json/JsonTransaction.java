package writers.json;

import generators.Item;
import generators.Transaction;

import java.util.ArrayList;

public class JsonTransaction extends Transaction {

    public JsonTransaction(long id, String timeStamp, long customer_id, ArrayList<JsonItem> jsonItems, double sum) {
        super(id, timeStamp, customer_id, jsonItems, sum);
    }
}
