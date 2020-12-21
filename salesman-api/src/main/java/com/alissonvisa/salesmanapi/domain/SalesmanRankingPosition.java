package com.alissonvisa.salesmanapi.domain;

import java.math.BigDecimal;

public class SalesmanRankingPosition {

    private String salesmanName;
    private BigDecimal totalValueSold;

    public SalesmanRankingPosition(String salesmanName, BigDecimal totalValueSold) {
        this.salesmanName = salesmanName;
        this.totalValueSold = totalValueSold;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public BigDecimal getTotalValueSold() {
        return totalValueSold;
    }

    private SalesmanRankingPosition(){}
}
