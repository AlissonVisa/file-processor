package com.alissonvisa.salesmanapi.application.jms;

import com.alissonvisa.salesmanapi.domain.Salesman;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.math.BigDecimal;

public class SalesmanMessageMapper implements MessageDomainMapper<Salesman> {

    private static final int IMPORT_ARCHIVE = 4;
    private static final int CPF = 1;
    private static final int NAME = 2;
    private static final int SALARY = 3;

    @Override
    public Salesman map(String message) {
        String[] elements = getMessageElements(message);
        Salesman salesman = new Salesman(
                elements[IMPORT_ARCHIVE],
                elements[NAME],
                elements[CPF],
                new BigDecimal(elements[SALARY]));
        return salesman;
    }

    private String[] getMessageElements(String message) {
        return message.split(delimiter());
    }
}
