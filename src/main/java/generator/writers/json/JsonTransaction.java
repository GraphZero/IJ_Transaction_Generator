package generator.writers.json;

import generator.generators.Transaction;

import java.util.ArrayList;

public class JsonTransaction extends Transaction {

    public JsonTransaction( String timeStamp, long customer_id, ArrayList<JsonItem> jsonItems, double sum) {
        super( timeStamp, customer_id, jsonItems, sum);
    }
}
