package com.alissonvisa.salesapi.domain.repository;

import com.alissonvisa.salesapi.domain.Sale;

import java.util.List;

public interface SaleRepository {

    List<Sale> getSales(String archiveName);

    void save(Sale sale);

    Sale getBestSale(String archiveName);
}
