package com.alissonvisa.salesmanapi.domain;

import java.math.BigDecimal;
import java.util.Objects;

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

    public Salesman(String importArchive, String name) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Salesman salesman = (Salesman) o;
        return Objects.equals(importArchive, salesman.importArchive) && Objects.equals(name, salesman.name) && Objects.equals(cpf, salesman.cpf) && Objects.equals(salary, salesman.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(importArchive, name, cpf, salary);
    }
}
