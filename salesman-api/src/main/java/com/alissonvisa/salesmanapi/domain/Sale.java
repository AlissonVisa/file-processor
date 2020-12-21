package com.alissonvisa.salesmanapi.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Sale {

    private Long id;
    private String salesmanName;
    private List<SaleItem> saleItems;
    private BigDecimal totalSaleValue;
    private String archiveName;

    public Sale(Long id, String salesmanName, String archiveName) {
        this.id = id;
        this.archiveName = archiveName;
        this.salesmanName = salesmanName;
    }

    public void addSaleItem(SaleItem saleItem) {
        if (this.saleItems == null) {
            this.saleItems = new ArrayList<>();
        }
        this.saleItems.add(saleItem);
        this.totalSaleValue = this.getTotalSaleValue().add(saleItem.getItemTotalPrice());
    }

    public BigDecimal getTotalSaleValue() {
        if (this.totalSaleValue == null) {
            this.totalSaleValue = BigDecimal.ZERO;
        }
        return this.totalSaleValue;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public String getArchiveName() {
        return archiveName;
    }

    public Long getId() {
        return id;
    }

    private Sale() {}
}
