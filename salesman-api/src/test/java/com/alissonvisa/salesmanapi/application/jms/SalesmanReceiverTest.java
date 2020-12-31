package com.alissonvisa.salesmanapi.application.jms;

import com.alissonvisa.salesmanapi.domain.Salesman;
import com.alissonvisa.salesmanapi.domain.service.SalesmanService;
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
class SalesmanReceiverTest {

    @Mock
    SalesmanService salesmanService;

    SalesmanReceiver salesmanReceiver;

    @Test
    void onMessage(@Mock TextMessage message, @Mock Session session) throws JMSException {

        salesmanReceiver = Mockito.spy(new SalesmanReceiver(salesmanService));

        Mockito.when(message.getText()).thenReturn("001\u00E73245678865434\u00E7Paulo\u00E740000.99\u00E7ARCHIVE_01.dat");
        Mockito.when(message.getStringProperty("JMSXDeliveryCount")).thenReturn("0");
        Mockito.when(message.getJMSCorrelationID()).thenReturn(UUID.randomUUID().toString());
        Mockito.when(message.getJMSReplyTo()).thenReturn(Mockito.mock(Destination.class));
        Mockito.when(session.createProducer(any(Destination.class))).thenReturn(Mockito.mock(MessageProducer.class));

        salesmanReceiver.onMessage(message, session);

        Mockito.verify(salesmanReceiver).reply(eq(Boolean.TRUE), any(TextMessage.class), any(MessageProducer.class));
        Mockito.verify(salesmanService).create(any(Salesman.class));
        Mockito.verify(session).createProducer(message.getJMSReplyTo());
        Mockito.verify(session.createProducer(message.getJMSReplyTo())).send(any(TextMessage.class));

    }

    @Test
    void onMessage_FailForTheLastTime(@Mock TextMessage message, @Mock Session session) throws JMSException {

        salesmanReceiver = Mockito.spy(new SalesmanReceiver(salesmanService));

        Mockito.when(message.getText()).thenReturn("001\u00E73245678865434\u00E7Paulo\u00E740000.99");
        Mockito.when(message.getStringProperty("JMSXDeliveryCount")).thenReturn("5");
        Mockito.when(message.getJMSCorrelationID()).thenReturn(UUID.randomUUID().toString());
        Mockito.when(message.getJMSReplyTo()).thenReturn(Mockito.mock(Destination.class));
        Mockito.when(session.createProducer(any(Destination.class))).thenReturn(Mockito.mock(MessageProducer.class));
        Mockito.when(salesmanReceiver.getMaximumRedelivery()).thenReturn(3);

        salesmanReceiver.onMessage(message, session);

        Mockito.verify(salesmanReceiver).reply(eq(Boolean.FALSE), any(TextMessage.class), any(MessageProducer.class));
        Mockito.verify(session).createProducer(message.getJMSReplyTo());
        Mockito.verify(session.createProducer(message.getJMSReplyTo())).send(any(TextMessage.class));

    }
}