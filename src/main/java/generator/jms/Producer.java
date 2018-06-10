package generator.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    private final JmsTemplate jmsTemplate;
    private final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    public Producer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Scheduled(fixedRateString = "${producingFixedRate}")
    public void sendMessage() {
        logger.info("Sending the message.");
        jmsTemplate.convertAndSend("simpleDestination", new SimpleMessage("Title", "Test Message"));
    }
}
