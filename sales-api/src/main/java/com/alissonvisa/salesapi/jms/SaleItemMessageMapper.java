package com.alissonvisa.salesapi.jms;

import com.alissonvisa.salesapi.domain.SaleItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SaleItemMessageMapper {

    private static final int ITEM_ID = 0;
    private static final int ITEM_QUANTITY = 1;
    private static final int ITEM_PRICE = 2;
    private static final String DELIMITER = "-";

    public List<SaleItem> map(String[] messages)  {
        List<SaleItem> items = new ArrayList<>();
        addSaleItems(messages, items);
        return items;
    }

    private void addSaleItems(String[] messages, List<SaleItem> items) {
        for (String message : messages) {
            addSaleItem(items, message);
        }
    }

    private void addSaleItem(List<SaleItem> items, String message) {
        String[] elements = getMessageElements(message);
        SaleItem item = new SaleItem(
                Long.valueOf(elements[ITEM_ID]),
                Long.valueOf(elements[ITEM_QUANTITY]),
                new BigDecimal(elements[ITEM_PRICE]));
        items.add(item);
    }

    private String[] getMessageElements(String message) {
        return message.split(DELIMITER);
    }
}
