package com.alissonvisa.customerapi.infrastructure.repository.cassandra;

import com.alissonvisa.customerapi.domain.Customer;
import com.alissonvisa.customerapi.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.cql.CqlTemplate;
import org.springframework.stereotype.Component;

@Component
public class CassandraDbCustomerRepository implements CustomerRepository {

    private final CqlTemplate cqlTemplate;

    @Autowired
    public CassandraDbCustomerRepository(CqlTemplate cqlTemplate) {
        this.cqlTemplate = cqlTemplate;
    }

    @Override
    public Long getCustomerCountByArchive(String archiveName) {
        long count = cqlTemplate.queryForObject(
                "SELECT COUNT(*) FROM customer WHERE import_archive = ?", Long.class, archiveName);
        return count;
    }

    @Override
    public void save(Customer customer) {
        cqlTemplate.execute("INSERT INTO customer (cnpj, name, business_area, import_archive) VALUES (?, ?, ?, ?)", customer.getCnpj(), customer.getName(), customer.getBusinessArea(), customer.getImportArchive());
    }
}
