package com.alissonvisa.salesmanapi.domain.repository;

import com.alissonvisa.salesmanapi.domain.Salesman;

import java.util.Optional;

public interface SalesmanRepository {

    Long getSalesmanCountByArchive(String archiveName);
    void save(Salesman salesman);
    Optional<Salesman> getByNameAndArchive(String salesmanName, String archiveName);
}
