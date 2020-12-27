package com.alissonvisa.salesapi.domain.service;

import com.alissonvisa.salesapi.domain.Sale;

public interface SaleService {

    String getWorstSalesman(String archiveName);
    Sale getBestSale(String archiveName);
    void save(Sale sale);

}
