package com.alissonvisa.customerapi.jms;

import com.alissonvisa.customerapi.domain.service.CustomerService;
import lombok.extern.log4j.Log4j2;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

@Log4j2
@Component
public class FinishFileReceiver implements SessionAwareMessageListener<TextMessage> {

    private static final String FINISH_FILE_QUEUE = "finish_file_customer_queue";

    private final CustomerService customerService;

    @Autowired
    public FinishFileReceiver(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    @JmsListener(destination = FINISH_FILE_QUEUE)
    public void onMessage(TextMessage message, Session session) throws JMSException {
        final String messageText = message.getText();
        log.info("finish queue received message='{}'", messageText);
        final Long countCustomer = this.customerService.countCustomerByArchive(messageText);

        final TextMessage responseMessage = getResponseMessage(message, countCustomer);

        final MessageProducer producer = session.createProducer(message.getJMSReplyTo());
        producer.send(responseMessage);
    }

    protected TextMessage getResponseMessage(TextMessage message, Long countCustomer) throws JMSException {
        final TextMessage responseMessage = new ActiveMQTextMessage();
        responseMessage.setJMSCorrelationID(message.getJMSCorrelationID());
        responseMessage.setText(String.format(
                "Quantidade de clientes no arquivo de entrada=%s", countCustomer));
        return responseMessage;
    }
}
