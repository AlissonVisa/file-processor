package com.alissonvisa.salesapi.infrastructure.configuration;

import com.alissonvisa.salesapi.SalesApiApplication;
import com.alissonvisa.salesapi.domain.repository.SaleRepository;
import com.alissonvisa.salesapi.domain.service.DomainSaleService;
import com.alissonvisa.salesapi.domain.service.SaleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = SalesApiApplication.class)
public class BeanConfiguration {

    @Bean
    public SaleService salesmanPositionService(final SaleRepository saleRepository) {
        return new DomainSaleService(saleRepository);
    }

}
