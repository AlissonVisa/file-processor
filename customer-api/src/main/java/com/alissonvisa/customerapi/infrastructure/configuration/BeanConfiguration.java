package com.alissonvisa.customerapi.infrastructure.configuration;

import com.alissonvisa.customerapi.CustomerApiApplication;
import com.alissonvisa.customerapi.domain.repository.CustomerRepository;
import com.alissonvisa.customerapi.domain.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = CustomerApiApplication.class)
public class BeanConfiguration {

    @Bean
    public CustomerService customerService(final CustomerRepository customerRepository) {
        return new DomainCustomerService(customerRepository);
    }

}
