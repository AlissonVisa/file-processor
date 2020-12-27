package com.alissonvisa.customerapi.domain.service;

import com.alissonvisa.customerapi.domain.Customer;
import com.alissonvisa.customerapi.domain.repository.CustomerRepository;

public class DomainCustomerService implements CustomerService {

    private final CustomerRepository customerRepository;

    public DomainCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void create(Customer customer) {
        this.customerRepository.save(customer);
    }

    @Override
    public Long countCustomerByArchive(String archiveName) {
        return this.customerRepository.getCustomerCountByArchive(archiveName.toUpperCase());
    }
}
