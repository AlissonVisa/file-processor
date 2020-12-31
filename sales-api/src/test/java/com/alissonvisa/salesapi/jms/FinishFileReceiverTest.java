package com.alissonvisa.salesapi.jms;

import com.alissonvisa.salesapi.domain.Sale;
import com.alissonvisa.salesapi.domain.service.SaleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jms.*;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class FinishFileReceiverTest {

    FinishFileReceiver finishFileReceiver;

    @Mock
    SaleService saleService;

    @Test
    void onMessage(@Mock TextMessage textMessage, @Mock Session session) throws JMSException {

        Sale bestSale = new Sale(3l, "afonso", "archive_01.dat");

        finishFileReceiver = Mockito.spy(new FinishFileReceiver(saleService));
        Mockito.when(saleService.getBestSale(any(String.class))).thenReturn(bestSale);
        Mockito.when(saleService.getWorstSalesman(any(String.class))).thenReturn("gomes");

        Mockito.when(textMessage.getText()).thenReturn("archive_01.dat");
        Mockito.when(textMessage.getJMSCorrelationID()).thenReturn(UUID.randomUUID().toString());
        Mockito.when(textMessage.getJMSReplyTo()).thenReturn(Mockito.mock(Destination.class));
        Mockito.when(session.createProducer(any(Destination.class))).thenReturn(Mockito.mock(MessageProducer.class));

        finishFileReceiver.onMessage(textMessage, session);

        Mockito.verify(saleService).getBestSale("archive_01.dat");
        Mockito.verify(saleService).getWorstSalesman("archive_01.dat");
        Mockito.verify(finishFileReceiver).getResponseMessage(any(TextMessage.class), eq("gomes"), eq(bestSale));
        Mockito.verify(session).createProducer(textMessage.getJMSReplyTo());
        Mockito.verify(session.createProducer(textMessage.getJMSReplyTo())).send(any(TextMessage.class));

    }
}