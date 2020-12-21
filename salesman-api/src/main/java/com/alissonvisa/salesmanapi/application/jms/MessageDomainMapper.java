package com.alissonvisa.salesmanapi.application.jms;

import javax.jms.JMSException;

public interface MessageDomainMapper<T> {

    String DELIMITER = "\u00E7";

    T map(String message) throws JMSException;

    default String delimiter() {
        return DELIMITER;
    }

}
