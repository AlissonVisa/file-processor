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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class SalesReceiverTest {

    @Mock
    SaleService saleService;

    SalesReceiver salesReceiver;

    @Test
    void onMessage(@Mock TextMessage message, @Mock Session session) throws JMSException {

        salesReceiver = Mockito.spy(new SalesReceiver(saleService));

        Mockito.when(message.getText()).thenReturn("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro GonçaloçARCHIVE_01.dat");
        Mockito.when(message.getStringProperty("JMSXDeliveryCount")).thenReturn("0");
        Mockito.when(message.getJMSCorrelationID()).thenReturn(UUID.randomUUID().toString());
        Mockito.when(message.getJMSReplyTo()).thenReturn(Mockito.mock(Destination.class));
        Mockito.when(session.createProducer(any(Destination.class))).thenReturn(Mockito.mock(MessageProducer.class));

        salesReceiver.onMessage(message, session);

        Mockito.verify(salesReceiver).reply(eq(Boolean.TRUE), any(TextMessage.class), any(MessageProducer.class));
        Mockito.verify(saleService).save(any(Sale.class));
        Mockito.verify(session).createProducer(message.getJMSReplyTo());
        Mockito.verify(session.createProducer(message.getJMSReplyTo())).send(any(TextMessage.class));

    }

    @Test
    void onMessage_FailForTheLastTime(@Mock TextMessage message, @Mock Session session) throws JMSException {

        salesReceiver = Mockito.spy(new SalesReceiver(saleService));

        Mockito.when(message.getText()).thenReturn("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro Gonçalo");
        Mockito.when(message.getStringProperty("JMSXDeliveryCount")).thenReturn("5");
        Mockito.when(message.getJMSCorrelationID()).thenReturn(UUID.randomUUID().toString());
        Mockito.when(message.getJMSReplyTo()).thenReturn(Mockito.mock(Destination.class));
        Mockito.when(session.createProducer(any(Destination.class))).thenReturn(Mockito.mock(MessageProducer.class));
        Mockito.when(salesReceiver.getMaximumRedelivery()).thenReturn(3);

        salesReceiver.onMessage(message, session);

        Mockito.verify(salesReceiver).reply(eq(Boolean.FALSE), any(TextMessage.class), any(MessageProducer.class));
        Mockito.verify(session).createProducer(message.getJMSReplyTo());
        Mockito.verify(session.createProducer(message.getJMSReplyTo())).send(any(TextMessage.class));

    }
}