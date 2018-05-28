package generator.writers.xml;

import generator.generators.Item;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@NoArgsConstructor
public class XmlItem extends Item {
    public XmlItem(String name, long quantity, double price) {
        super(name, quantity, price);
    }
}
