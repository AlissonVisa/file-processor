package com.alissonvisa.salesapi.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sale {

    private Long id;
    private String salesmanName;
    private List<SaleItem> saleItems;
    private BigDecimal totalSaleValue;
    private String archiveName;

    public Sale(Long id, String salesmanName, BigDecimal totalSaleValue, String archiveName) {
        this.id = id;
        this.salesmanName = salesmanName;
        this.totalSaleValue = totalSaleValue;
        this.archiveName = archiveName;
    }

    public Sale(Long id, String salesmanName, BigDecimal totalSaleValue) {
        this.id = id;
        this.salesmanName = salesmanName;
        this.totalSaleValue = totalSaleValue;
    }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return Objects.equals(id, sale.id) && Objects.equals(salesmanName, sale.salesmanName) && Objects.equals(totalSaleValue, sale.totalSaleValue) && Objects.equals(archiveName, sale.archiveName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, salesmanName, totalSaleValue, archiveName);
    }
}
