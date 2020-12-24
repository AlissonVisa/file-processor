package com.alissonvisa.salesmanapi.domain.service;

import com.alissonvisa.salesmanapi.domain.Sale;

public interface SaleService {

    String getWorstSalesman();
    Sale getBestSale();
    void save(Sale sale);

}
