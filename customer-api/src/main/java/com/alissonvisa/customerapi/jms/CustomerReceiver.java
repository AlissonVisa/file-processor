package com.alissonvisa.customerapi.jms;

import com.alissonvisa.customerapi.domain.Customer;
import com.alissonvisa.customerapi.domain.service.CustomerService;
import lombok.extern.log4j.Log4j2;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${app.jms.queue.maximumRedelivery}")
    private Integer maximumRedelivery;

    private final CustomerService customerService;

    @Autowired
    public CustomerReceiver(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    @JmsListener(destination = CUSTOMER_QUEUE, concurrency = "1-8")
    public void onMessage(TextMessage message, Session session) throws JMSException {
        final Integer redeliveryCount = Integer.valueOf(message.getStringProperty("JMSXDeliveryCount"));
        final TextMessage responseMessage = new ActiveMQTextMessage();
        responseMessage.setJMSCorrelationID(message.getJMSCorrelationID());
        final MessageProducer producer = session.createProducer(message.getJMSReplyTo());
        try {
            log.info("customer received message='{}' on '{}'", message.getText(), Thread.currentThread().getName());
            final Customer customer = new CustomerMessageMapper().map(message.getText());
            this.customerService.create(customer);
            reply(Boolean.TRUE, responseMessage, producer);
            message.acknowledge();
        } catch (Exception e) {
            log.error("customer failing, exception='{}', message='{}' on '{}'", e.getMessage(), message.getText(), Thread.currentThread().getName());
            if(redeliveryCount > getMaximumRedelivery()) {
                log.warn("customer failing for the last time, exception='{}', message='{}' on '{}'", e.getMessage(), message.getText(), Thread.currentThread().getName());
                reply(Boolean.FALSE, responseMessage, producer);
            }
            session.recover();
        }
    }

    protected void reply(Boolean status, TextMessage responseMessage, MessageProducer producer) throws JMSException {
        responseMessage.setText(status.toString());
        producer.send(responseMessage);
    }

    protected Integer getMaximumRedelivery() {
        return maximumRedelivery;
    }
}
