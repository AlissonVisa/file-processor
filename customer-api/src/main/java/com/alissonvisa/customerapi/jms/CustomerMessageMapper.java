package com.alissonvisa.customerapi.jms;

import com.alissonvisa.customerapi.domain.Customer;

public class CustomerMessageMapper implements MessageDomainMapper<Customer> {

    private static final int CNPJ = 1;
    private static final int NAME = 2;
    private static final int BUSINESS_AREA = 3;
    private static final int IMPORT_ARCHIVE = 4;

    @Override
    public Customer map(String message) {
        String[] elements = getMessageElements(message);
        Customer customer = new Customer(
                elements[CNPJ],
                restoreOriginalElement(NAME, elements),
                restoreOriginalElement(BUSINESS_AREA, elements),
                restoreOriginalElement(IMPORT_ARCHIVE, elements));
        return customer;
    }


}
