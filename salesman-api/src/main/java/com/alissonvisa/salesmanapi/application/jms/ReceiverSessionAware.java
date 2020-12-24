package com.alissonvisa.salesmanapi.application.jms;

import com.alissonvisa.salesmanapi.domain.Sale;
import com.alissonvisa.salesmanapi.domain.SalesmanRanking;
import com.alissonvisa.salesmanapi.domain.SalesmanRankingPosition;
import com.alissonvisa.salesmanapi.domain.service.SaleService;
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
import java.util.List;

@Log4j2
@Component
public class ReceiverSessionAware implements SessionAwareMessageListener<TextMessage> {

    private static final String FINISH_FILE_QUEUE = "finish_file_queue";

    private final SalesmanService salesmanService;

    private final SaleService saleService;

    @Autowired
    public ReceiverSessionAware(SalesmanService salesmanService, SaleService saleService) {
        this.salesmanService = salesmanService;
        this.saleService = saleService;
    }

    @Override
    @JmsListener(destination = FINISH_FILE_QUEUE)
    public void onMessage(TextMessage message, Session session) throws JMSException {
        log.info("finish queue received message='{}'", message.getText());
        final Long countSalesman = salesmanService.countSalesmanByArchive(message.getText());
        final String worstSalesman = this.saleService.getWorstSalesman();
        final Sale bestSale = this.saleService.getBestSale();

        final TextMessage responseMessage = new ActiveMQTextMessage();
        responseMessage.setJMSCorrelationID(message.getJMSCorrelationID());
        responseMessage.setText(String.format(
                "Quantidade de vendedor no arquivo de entrada=%s\n" +
                "ID da venda mais cara=%s\n" +
                "O pior vendedor=%s", countSalesman, bestSale.getId(), worstSalesman));

        final MessageProducer producer = session.createProducer(message.getJMSReplyTo());
        producer.send(responseMessage);
    }
}
