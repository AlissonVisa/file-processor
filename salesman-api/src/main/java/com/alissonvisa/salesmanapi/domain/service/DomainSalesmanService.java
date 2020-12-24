package com.alissonvisa.salesmanapi.domain.service;

import com.alissonvisa.salesmanapi.domain.Salesman;
import com.alissonvisa.salesmanapi.domain.repository.SalesmanRepository;

public class DomainSalesmanService implements SalesmanService {

    private final SalesmanRepository salesmanRepository;

    public DomainSalesmanService(final SalesmanRepository salesmanRepository) {
        this.salesmanRepository = salesmanRepository;
    }

    @Override
    public void create(final Salesman salesman) {
        this.salesmanRepository.save(salesman);
    }

    @Override
    public Long countSalesmanByArchive(final String archiveName) {
        return this.salesmanRepository.getSalesmanCountByArchive(archiveName.toUpperCase());
    }
}
