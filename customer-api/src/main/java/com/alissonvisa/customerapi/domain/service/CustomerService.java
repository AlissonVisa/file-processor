package com.alissonvisa.customerapi.domain.service;

import com.alissonvisa.customerapi.domain.Customer;

public interface CustomerService {

    void create(Customer customer);
    Long countCustomerByArchive(String archiveName);

}
