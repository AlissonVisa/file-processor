package com.alissonvisa.salesapi.domain;

import lombok.ToString;

import java.math.BigDecimal;

@ToString
public class SaleItem {

    private Long itemId;
    private Long itemQuantity;
    private BigDecimal itemPrice;


    public SaleItem(Long itemId, Long itemQuantity, BigDecimal itemPrice) {
        this.itemId = itemId;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
    }

    public BigDecimal getItemTotalPrice() {
        if(this.getItemPrice() == null || this.getItemQuantity() == null) {
            return BigDecimal.ZERO;
        }
        return this.getItemPrice().multiply(BigDecimal.valueOf(this.getItemQuantity()));
    }

    public Long getItemId() {
        return itemId;
    }

    public Long getItemQuantity() {
        return itemQuantity;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    private SaleItem(){}
}
