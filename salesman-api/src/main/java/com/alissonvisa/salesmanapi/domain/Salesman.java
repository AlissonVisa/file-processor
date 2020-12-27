package com.alissonvisa.salesmanapi.domain;

import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;

@Log4j2
public class Salesman {

    private String importArchive;
    private String name;
    private String cpf;
    private BigDecimal salary;

    public Salesman(String importArchive, String name, String cpf, BigDecimal salary) {
        this.importArchive = importArchive;
        this.name = name;
        this.cpf = cpf;
        this.salary = salary;
    }

    public Salesman(String importArchive, String name, BigDecimal totalSold) {
        this.importArchive = importArchive;
        this.name = name;
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

    private Salesman() {}

}
