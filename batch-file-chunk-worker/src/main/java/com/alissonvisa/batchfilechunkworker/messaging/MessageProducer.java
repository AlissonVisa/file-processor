package com.alissonvisa.batchfilechunkworker.messaging;

public interface MessageProducer {
    void sendMessage(String queueName, String message);
}
