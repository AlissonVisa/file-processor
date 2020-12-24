package com.alissonvisa.salesmanapi.domain;

import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.util.List;

@Log4j2
public class SalesmanRankingPosition {

    private String salesmanName;
    private BigDecimal totalValueSold;

    public SalesmanRankingPosition(String salesmanName, List<Sale> sales) {
        this.salesmanName = salesmanName;
        this.totalValueSold = BigDecimal.ZERO;
        sales.forEach(sale -> {
            addSale(sale);
        });
    }

    public boolean addSale(Sale sale) {
        if (!sale.getSalesmanName().equals(this.salesmanName)) {
            log.warn(String.format("Sale %s not sold by the salesman %s",
                    sale.toString(), this.salesmanName));
            return false;
        }
        this.totalValueSold = this.getTotalValueSold().add(sale.getTotalSaleValue());
        return true;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public BigDecimal getTotalValueSold() {
        return totalValueSold;
    }

    private SalesmanRankingPosition(){}
}
