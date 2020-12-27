package com.alissonvisa.batchfilechunkworker.messaging;

import javax.jms.TextMessage;

public interface MessageProducer {
    TextMessage sendAndReceiveMessage(String queueName, String message);
}
