package com.alissonvisa.customerapi.domain;

import java.util.Objects;

public class Customer {

    private String cnpj;
    private String name;
    private String businessArea;
    private String importArchive;

    public Customer(String cnpj, String name, String businessArea, String importArchive) {
        this.cnpj = cnpj;
        this.name = name;
        this.businessArea = businessArea;
        this.importArchive = importArchive;
    }

    private Customer() {}

    public String getCnpj() {
        return cnpj;
    }

    public String getName() {
        return name;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    public String getImportArchive() {
        return importArchive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(cnpj, customer.cnpj) && Objects.equals(name, customer.name) && Objects.equals(businessArea, customer.businessArea) && Objects.equals(importArchive, customer.importArchive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cnpj, name, businessArea, importArchive);
    }
}
