package com.alissonvisa.customerapi.domain.repository;

import com.alissonvisa.customerapi.domain.Customer;

public interface CustomerRepository {

    Long getCustomerCountByArchive(String archiveName);
    void save(Customer customer);

}
