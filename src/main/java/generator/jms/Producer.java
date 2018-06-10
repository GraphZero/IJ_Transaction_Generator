package generator.jms;

import generator.Generator;
import generator.generators.rest.ResponseTransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static generator.Generator.ORDER_TOPIC;

@Service
public class Producer {
    private final JmsTemplate jmsTemplate;
    private final Logger logger = LoggerFactory.getLogger(Producer.class);
    private final ResponseTransactionManager responseTransactionManager;

    @Autowired
    public Producer(JmsTemplate jmsTemplate, ResponseTransactionManager responseTransactionManager1) {
        this.jmsTemplate = jmsTemplate;
        this.responseTransactionManager = responseTransactionManager1;
    }

    @Scheduled(fixedRateString = "${producingFixedRate}")
    public void sendMessage() {
        logger.info("Sending the message to topic: " + ORDER_TOPIC);
        ArrayList transactions = responseTransactionManager.generateTransactions(Generator.generateTransactionToFileCommand.toResponseCommand());
        jmsTemplate.convertAndSend(ORDER_TOPIC, transactions);
    }
}
