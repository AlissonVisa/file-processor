package com.alissonvisa.salesapi.jms;

import com.alissonvisa.salesapi.domain.Sale;
import com.alissonvisa.salesapi.domain.service.SaleService;
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
public class SalesReceiver implements SessionAwareMessageListener<TextMessage> {


    private static final String SALES_QUEUE = "sales_queue";

    private final SaleService saleService;

    @Autowired
    public SalesReceiver(SaleService saleService) {
        this.saleService = saleService;
    }

    @Override
    @JmsListener(destination = SALES_QUEUE)
    public void onMessage(TextMessage message, Session session) throws JMSException {

        final TextMessage responseMessage = new ActiveMQTextMessage();
        responseMessage.setJMSCorrelationID(message.getJMSCorrelationID());
        final MessageProducer producer = session.createProducer(message.getJMSReplyTo());
        try {
            log.info("sale received message='{}'", message.getText());
            final Sale sale = new SaleMessageMapper().map(message.getText());
            this.saleService.save(sale);
            responseMessage.setText(Boolean.TRUE.toString());
        } catch (Exception e) {
            responseMessage.setText(Boolean.FALSE.toString());
        } finally {
            producer.send(responseMessage);
        }

    }
}
