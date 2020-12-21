package com.alissonvisa.batchfilereader.messaging;

import javax.jms.TextMessage;

public interface MessageProducer {
    TextMessage sendAndReceiveMessage(String queueName, String message);
}
