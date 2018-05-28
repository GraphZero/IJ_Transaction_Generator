package generator.readers.items;

import generator.utility.Tuple;

import java.util.List;

public interface ItemsReader {
    List<Tuple<String, Double>> getItems(String path);
}
