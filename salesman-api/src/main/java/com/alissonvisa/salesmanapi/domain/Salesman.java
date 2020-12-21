package com.alissonvisa.salesmanapi.domain;

import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;

@Log4j2
public class Salesman {

    private String importArchive;
    private String name;
    private String cpf;
    private BigDecimal salary;
    private BigDecimal totalSold;

    public Salesman(Sale sale) {
        this.importArchive = sale.getArchiveName();
        this.name = sale.getSalesmanName();
        this.totalSold = BigDecimal.ZERO;
        this.addSale(sale);
    }

    public Salesman(String importArchive, String name, String cpf, BigDecimal salary) {
        this.importArchive = importArchive;
        this.name = name;
        this.cpf = cpf;
        this.salary = salary;
        this.totalSold = BigDecimal.ZERO;
    }

    public Salesman(String importArchive, String name, String cpf, BigDecimal salary, BigDecimal totalSold) {
        this.importArchive = importArchive;
        this.name = name;
        this.cpf = cpf;
        this.salary = salary;
        this.totalSold = totalSold;
    }

    public boolean addSale(Sale sale) {
        if (!sale.getSalesmanName().equals(this.name) || !sale.getArchiveName().equals(importArchive)) {
            log.warn(String.format("Sale %s is not part of archive %s or not sold by the salesman %s",
                    sale.toString(), this.importArchive, this.name));
            return false;
        }
        this.totalSold = this.getTotalSold().add(sale.getTotalSaleValue());
        return true;
    }

    public String getImportArchive() {
        return importArchive;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public BigDecimal getTotalSold() {
        return totalSold;
    }

    private Salesman() {}

}
