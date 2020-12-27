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
public class FinishFileReceiver implements SessionAwareMessageListener<TextMessage> {

    private static final String FINISH_FILE_QUEUE = "finish_file_sales_queue";

    private final SaleService saleService;

    @Autowired
    public FinishFileReceiver(SaleService saleService) {
        this.saleService = saleService;
    }

    @Override
    @JmsListener(destination = FINISH_FILE_QUEUE)
    public void onMessage(TextMessage message, Session session) throws JMSException {
        final String messageText = message.getText();
        log.info("finish queue received message='{}'", messageText);
        final String worstSalesman = this.saleService.getWorstSalesman(messageText);
        final Sale bestSale = this.saleService.getBestSale(messageText);

        final TextMessage responseMessage = new ActiveMQTextMessage();
        responseMessage.setJMSCorrelationID(message.getJMSCorrelationID());
        responseMessage.setText(String.format(
                "ID da venda mais cara=%s\n" +
                "O pior vendedor=%s", bestSale.getId(), worstSalesman));

        final MessageProducer producer = session.createProducer(message.getJMSReplyTo());
        producer.send(responseMessage);
    }
}
