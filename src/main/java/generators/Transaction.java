package generators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    protected long id;
    protected String timeStamp;
    protected long customer_id;
    protected ArrayList<? extends Item> jsonItems;
    protected double sum;
}
