package com.alissonvisa.salesmanapi.application.jms;

import com.alissonvisa.salesmanapi.domain.Sale;
import com.alissonvisa.salesmanapi.domain.service.DomainSalesmanService;
import com.alissonvisa.salesmanapi.domain.service.SalesmanService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class Receiver {

    private static final String SALESMAN_QUEUE = "salesman_queue";
    private static final String CUSTOMER_QUEUE = "customer_queue";
    private static final String SALES_QUEUE = "sales_queue";

    private final SalesmanService salesmanService;

    @Autowired
    public Receiver(SalesmanService salesmanService) {
        this.salesmanService = salesmanService;
    }

    @JmsListener(destination = SALESMAN_QUEUE)
    public void onReceiveSalesman(String message) {
        log.info("salesman received message='{}'", message);
        salesmanService.create(new SalesmanMessageMapper().map(getCleanLine(message)));
    }

    @JmsListener(destination = SALES_QUEUE)
    public void onReceiveSale(String message) {
        log.info("sale received message='{}'", message);
        final Sale sale = new SaleMessageMapper().map(getCleanLine(message));
        // TODO save sale
    }

    @JmsListener(destination = CUSTOMER_QUEUE)
    public void onReceiveCustomer(String message) {
        log.info("customer received message='{}'", message);
        // TODO save customer
    }

    private String getCleanLine(String line) {
        return line.replaceAll("\u00E7+([a-z])", "\u00E7\u00E7".toUpperCase() + "$1");
    }
}
