package com.alissonvisa.salesapi.domain.service;

import com.alissonvisa.salesapi.domain.Sale;
import com.alissonvisa.salesapi.domain.SalesmanRanking;
import com.alissonvisa.salesapi.domain.SalesmanRankingPosition;
import com.alissonvisa.salesapi.domain.repository.SaleRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DomainSaleService implements SaleService {

    private final SaleRepository saleRepository;

    public DomainSaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public String getWorstSalesman(String archiveName) {
        final List<Sale> sales = saleRepository.getSales(archiveName);
        final Map<String, List<Sale>> salesmanSales = getSalesmanSales(sales);
        final List<SalesmanRankingPosition> positions = getPositions(salesmanSales);
        return new SalesmanRanking(positions).getWorstSalesman();
    }

    @Override
    public Sale getBestSale(String archiveName) {
        return this.saleRepository.getBestSale(archiveName);
    }

    private Map<String, List<Sale>> getSalesmanSales(List<Sale> sales) {
        return sales.stream().collect(Collectors.groupingBy(Sale::getSalesmanName, Collectors.toList()));
    }

    private List<SalesmanRankingPosition> getPositions(Map<String, List<Sale>> salesmanSales) {
        return salesmanSales.entrySet().stream()
                .map(entry -> new SalesmanRankingPosition(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Sale sale) {
        this.saleRepository.save(sale);
    }
}
