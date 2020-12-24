package com.alissonvisa.salesmanapi.domain.service;

import com.alissonvisa.salesmanapi.domain.Salesman;

public interface SalesmanService {

    void create(Salesman salesman);
    Long countSalesmanByArchive(String archiveName);

}
