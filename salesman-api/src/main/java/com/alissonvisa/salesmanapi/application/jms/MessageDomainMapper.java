package com.alissonvisa.salesmanapi.application.jms;

import javax.jms.JMSException;

public interface MessageDomainMapper<T> {

    String DELIMITER = "\u00E7";

    T map(String message) throws JMSException;

    default String restoreOriginalElement(int elementIndex, String[] elements) {
        return elements[elementIndex].replace(getDelimiterCharReplacement(), DELIMITER);
    }

    default String getDelimiterCharReplacement() {
        return (DELIMITER + DELIMITER).toUpperCase();
    }

    default String[] getMessageElements(String message) {
        return getCleanLine(message).split(DELIMITER);
    }

    default String getCleanLine(String line) {
        return line.replaceAll("\u00E7+([a-z])", getDelimiterCharReplacement() + "$1");
    }

}
