package com.alissonvisa.salesmanapi.domain.service;

import com.alissonvisa.salesmanapi.domain.Sale;
import com.alissonvisa.salesmanapi.domain.Salesman;
import com.alissonvisa.salesmanapi.domain.SalesmanRankingPosition;
import com.alissonvisa.salesmanapi.domain.repository.SalesmanRepository;

import java.util.Optional;

public class DomainSalesmanService implements SalesmanService {

    private final SalesmanRepository salesmanRepository;

    public DomainSalesmanService(final SalesmanRepository salesmanRepository) {
        this.salesmanRepository = salesmanRepository;
    }

    @Override
    public void create(final Salesman salesman) {
        final Optional<Salesman> persistedSalesman = getPersistedSalesman(salesman);
        if(persistedSalesman.isPresent()) {
            this.salesmanRepository.update(new Salesman(
                    salesman.getImportArchive(),
                    salesman.getName(),
                    salesman.getCpf(),
                    salesman.getSalary(),
                    persistedSalesman.get().getTotalSold()
            ));
            return;
        }
        this.salesmanRepository.save(salesman);
    }

    private Optional<Salesman> getPersistedSalesman(Salesman salesman) {
        return this.salesmanRepository.getByNameAndArchive(salesman.getName(), salesman.getImportArchive());
    }

    @Override
    public Long countSalesmanByArchive(final String archiveName) {
        return this.salesmanRepository.getSalesmanCountByArchive(archiveName);
    }

    @Override
    public String getWorstSalesman(final String archiveName) {
        final SalesmanRankingPosition worstSalesman = this.salesmanRepository.getWorstSalesmanInArchive(archiveName);
        return worstSalesman.getSalesmanName();
    }

    private synchronized Optional<Salesman> getSalesmanBySale(final Sale sale) {
        return this.salesmanRepository.getByNameAndArchive(sale.getSalesmanName(), sale.getArchiveName());
    }
}
