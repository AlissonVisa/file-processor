package com.alissonvisa.customerapi.jms;

import com.alissonvisa.customerapi.domain.Customer;
import com.alissonvisa.customerapi.domain.service.CustomerService;
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
class CustomerReceiverTest {
    
    @Mock
    CustomerService customerService;
    
    CustomerReceiver customerReceiver;

    @Test
    void onMessage(@Mock TextMessage message, @Mock Session session) throws JMSException {

        customerReceiver = Mockito.spy(new CustomerReceiver(customerService));

        Mockito.when(message.getText()).thenReturn("002\u00E72345675434544345\u00E7Jose Gon\u00E7alves\u00E7Rural\u00E7ARCHIVE_01.dat");
        Mockito.when(message.getStringProperty("JMSXDeliveryCount")).thenReturn("0");
        Mockito.when(message.getJMSCorrelationID()).thenReturn(UUID.randomUUID().toString());
        Mockito.when(message.getJMSReplyTo()).thenReturn(Mockito.mock(Destination.class));
        Mockito.when(session.createProducer(any(Destination.class))).thenReturn(Mockito.mock(MessageProducer.class));

        customerReceiver.onMessage(message, session);

        Mockito.verify(customerReceiver).reply(eq(Boolean.TRUE), any(TextMessage.class), any(MessageProducer.class));
        Mockito.verify(customerService).create(any(Customer.class));
        Mockito.verify(session).createProducer(message.getJMSReplyTo());
        Mockito.verify(session.createProducer(message.getJMSReplyTo())).send(any(TextMessage.class));

    }

    @Test
    void onMessage_FailForTheLastTime(@Mock TextMessage message, @Mock Session session) throws JMSException {

        customerReceiver = Mockito.spy(new CustomerReceiver(customerService));

        Mockito.when(message.getText()).thenReturn("002\u00E72345675434544345\u00E7Jose Gon\u00E7alves\u00E7Rural");
        Mockito.when(message.getStringProperty("JMSXDeliveryCount")).thenReturn("5");
        Mockito.when(message.getJMSCorrelationID()).thenReturn(UUID.randomUUID().toString());
        Mockito.when(message.getJMSReplyTo()).thenReturn(Mockito.mock(Destination.class));
        Mockito.when(session.createProducer(any(Destination.class))).thenReturn(Mockito.mock(MessageProducer.class));
        Mockito.when(customerReceiver.getMaximumRedelivery()).thenReturn(3);

        customerReceiver.onMessage(message, session);

        Mockito.verify(customerReceiver).reply(eq(Boolean.FALSE), any(TextMessage.class), any(MessageProducer.class));
        Mockito.verify(session).createProducer(message.getJMSReplyTo());
        Mockito.verify(session.createProducer(message.getJMSReplyTo())).send(any(TextMessage.class));

    }
}