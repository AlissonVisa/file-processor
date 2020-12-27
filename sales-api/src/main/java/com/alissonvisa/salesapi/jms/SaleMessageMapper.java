package com.alissonvisa.salesapi.jms;

import com.alissonvisa.salesapi.domain.Sale;
import com.alissonvisa.salesapi.domain.SaleItem;

import java.util.List;

public class SaleMessageMapper implements MessageDomainMapper<Sale> {

    private static final int SALE_ID = 1;
    private static final int SALE_ITEM_LIST = 2;
    private static final int SALESMAN_NAME = 3;
    private static final int IMPORT_ARCHIVE = 4;
    private static final String SALE_ITEM_DELIMITER = ",";

    @Override
    public Sale map(String message)  {
        String[] elements = getMessageElements(message);
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

    private String[] getSaleItemMessages(String element) {
        return removeListBrackets(element).split(SALE_ITEM_DELIMITER);
    }

    private String removeListBrackets(String element) {
        return element.replace("[", "").replace("]", "");
    }
}
