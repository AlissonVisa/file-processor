package com.alissonvisa.salesmanapi.application.jms;

import com.alissonvisa.salesmanapi.domain.Sale;
import com.alissonvisa.salesmanapi.domain.SaleItem;
import com.alissonvisa.salesmanapi.domain.Salesman;

import javax.jms.JMSException;
import java.math.BigDecimal;
import java.util.List;

public class SaleMessageMapper implements MessageDomainMapper<Sale> {

    private static final int SALE_ID = 1;
    private static final int SALE_ITEM_LIST = 2;
    private static final int SALESMAN_NAME = 3;
    private static final int IMPORT_ARCHIVE = 4;
    private static final String SALE_ITEM_DELIMITER = ",";

    @Override
    public Sale map(String message)  {
        String[] elements = getMessageElements(message, delimiter());
        Sale sale = new Sale(Long.valueOf(
                elements[SALE_ID]),
                restoreOriginalElement(SALESMAN_NAME, elements),
                restoreOriginalElement(IMPORT_ARCHIVE, elements));
        List<SaleItem> saleItems = new SaleItemMessageMapper().map(getSaleItemMessages(elements[SALE_ITEM_LIST]));
        saleItems.forEach((item) -> {
            sale.addSaleItem(item);
        });
        return sale;
    }

    private String restoreOriginalElement(int salesmanName, String[] elements) {
        return elements[salesmanName].replace("\u00E7\u00E7".toUpperCase(), "\u00E7");
    }

    private String[] getMessageElements(String message, String delimiter) {
        return message.split(delimiter);
    }

    private String[] getSaleItemMessages(String element) {
        return removeListBrackets(element).split(SALE_ITEM_DELIMITER);
    }

    private String removeListBrackets(String element) {
        return element.replace("[", "").replace("]", "");
    }
}
