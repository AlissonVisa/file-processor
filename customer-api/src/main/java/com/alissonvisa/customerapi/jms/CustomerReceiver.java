package com.alissonvisa.customerapi.jms;

import com.alissonvisa.customerapi.domain.Customer;
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
public class CustomerReceiver implements SessionAwareMessageListener<TextMessage> {

    private static final String CUSTOMER_QUEUE = "customer_queue";

    private final CustomerService customerService;

    @Autowired
    public CustomerReceiver(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    @JmsListener(destination = CUSTOMER_QUEUE)
    public void onMessage(TextMessage message, Session session) throws JMSException {

        final TextMessage responseMessage = new ActiveMQTextMessage();
        responseMessage.setJMSCorrelationID(message.getJMSCorrelationID());
        final MessageProducer producer = session.createProducer(message.getJMSReplyTo());
        try {
            log.info("customer received message='{}'", message.getText());
            final Customer customer = new CustomerMessageMapper().map(message.getText());
            this.customerService.create(customer);
            responseMessage.setText(Boolean.TRUE.toString());
        } catch (Exception e) {
            responseMessage.setText(Boolean.FALSE.toString());
        } finally {
            producer.send(responseMessage);
        }
    }
}
