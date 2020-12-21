package com.alissonvisa.salesmanapi.infrastructure.configuration;

import com.alissonvisa.salesmanapi.SalesmanApiApplication;
import com.alissonvisa.salesmanapi.domain.repository.SalesmanRepository;
import com.alissonvisa.salesmanapi.domain.service.DomainSalesmanService;
import com.alissonvisa.salesmanapi.domain.service.SalesmanService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = SalesmanApiApplication.class)
public class BeanConfiguration {

    @Bean
    public SalesmanService salesmanService(final SalesmanRepository salesmanRepository) {
        return new DomainSalesmanService(salesmanRepository);
    }

}
