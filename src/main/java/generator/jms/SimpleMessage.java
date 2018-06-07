package generator.jms;

import lombok.Value;

@Value
public class SimpleMessage {
    private String title;
    private String content;
}
