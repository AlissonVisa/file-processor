package com.alissonvisa.salesmanapi.domain.repository;

import com.alissonvisa.salesmanapi.domain.Sale;

import java.util.List;

public interface SaleRepository {

    List<Sale> getSales();

    void save(Sale sale);

    Sale getBestSale();
}
