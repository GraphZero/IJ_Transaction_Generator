package generator.writers.xml;

import generator.generators.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
@NoArgsConstructor
@Getter
@Setter
public class XmlTransaction extends Transaction {

    public XmlTransaction(String timeStamp, long customer_id, ArrayList<XmlItem> items, double sum) {
        super(timeStamp, customer_id, items, sum);
    }



}
