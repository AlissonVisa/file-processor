package com.alissonvisa.customerapi.domain;

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
}
