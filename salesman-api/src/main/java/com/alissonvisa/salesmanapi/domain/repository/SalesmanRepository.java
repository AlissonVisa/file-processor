package com.alissonvisa.salesmanapi.domain.repository;

import com.alissonvisa.salesmanapi.domain.Salesman;
import com.alissonvisa.salesmanapi.domain.SalesmanRankingPosition;

import java.util.Optional;

public interface SalesmanRepository {

    Long getSalesmanCountByArchive(String archiveName);
    SalesmanRankingPosition getWorstSalesmanInArchive(String importArchive);
    void save(Salesman salesman);
    void update(Salesman salesman);
    Optional<Salesman> getByNameAndArchive(String salesmanName, String archiveName);
}
