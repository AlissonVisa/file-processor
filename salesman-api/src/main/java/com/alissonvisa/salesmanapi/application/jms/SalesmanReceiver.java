package com.alissonvisa.salesmanapi.application.jms;

import com.alissonvisa.salesmanapi.domain.service.SalesmanService;
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
public class SalesmanReceiver implements SessionAwareMessageListener<TextMessage> {

    private static final String SALESMAN_QUEUE = "salesman_queue";

    private final SalesmanService salesmanService;

    @Autowired
    public SalesmanReceiver(SalesmanService salesmanService) {
        this.salesmanService = salesmanService;
    }

    @Override
    @JmsListener(destination = SALESMAN_QUEUE)
    public void onMessage(TextMessage message, Session session) throws JMSException {
        final TextMessage responseMessage = new ActiveMQTextMessage();
        responseMessage.setJMSCorrelationID(message.getJMSCorrelationID());
        final MessageProducer producer = session.createProducer(message.getJMSReplyTo());
        try {
            log.info("salesman received message='{}'", message.getText());
            this.salesmanService.create(new SalesmanMessageMapper().map(message.getText()));
            responseMessage.setText(Boolean.TRUE.toString());
        } catch (Exception e) {
            responseMessage.setText(Boolean.FALSE.toString());
        } finally {
            producer.send(responseMessage);
        }
    }
}
