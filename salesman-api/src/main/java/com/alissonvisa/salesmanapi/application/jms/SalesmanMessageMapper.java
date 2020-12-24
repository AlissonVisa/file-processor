package com.alissonvisa.salesmanapi.application.jms;

import com.alissonvisa.salesmanapi.domain.Salesman;

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
                restoreOriginalElement(IMPORT_ARCHIVE, elements),
                restoreOriginalElement(NAME, elements),
                elements[CPF],
                new BigDecimal(elements[SALARY]));
        return salesman;
    }

    private String restoreOriginalElement(int importArchive, String[] elements) {
        return elements[importArchive].replace("\u00E7\u00E7".toUpperCase(), "\u00E7");
    }

    private String[] getMessageElements(String message) {
        return message.split(delimiter());
    }
}
