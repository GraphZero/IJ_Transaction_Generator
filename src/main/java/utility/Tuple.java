package utility;

import lombok.Value;

@Value
public class Tuple<T,E>{
    private T first;
    private E second;

    public Tuple(T first, E second) {
        this.first = first;
        this.second = second;
    }
}
