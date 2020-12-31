package com.alissonvisa.customerapi.jms;

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
class FinishFileReceiverTest {

    FinishFileReceiver finishFileReceiver;

    @Mock
    CustomerService customerService;

    @Test
    void onMessage(@Mock TextMessage textMessage, @Mock Session session) throws JMSException {

        finishFileReceiver = Mockito.spy(new FinishFileReceiver(customerService));
        Mockito.when(customerService.countCustomerByArchive(any(String.class))).thenReturn(3L);

        Mockito.when(textMessage.getText()).thenReturn("archive_01.dat");
        Mockito.when(textMessage.getJMSCorrelationID()).thenReturn(UUID.randomUUID().toString());
        Mockito.when(textMessage.getJMSReplyTo()).thenReturn(Mockito.mock(Destination.class));
        Mockito.when(session.createProducer(any(Destination.class))).thenReturn(Mockito.mock(MessageProducer.class));

        finishFileReceiver.onMessage(textMessage, session);

        Mockito.verify(customerService).countCustomerByArchive("archive_01.dat");
        Mockito.verify(finishFileReceiver).getResponseMessage(any(TextMessage.class), eq(3l));
        Mockito.verify(session).createProducer(textMessage.getJMSReplyTo());
        Mockito.verify(session.createProducer(textMessage.getJMSReplyTo())).send(any(TextMessage.class));

    }
}