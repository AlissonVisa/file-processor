package com.alissonvisa.salesmanapi.infrastructure.repository.cassandra;

import com.alissonvisa.salesmanapi.domain.Sale;
import com.alissonvisa.salesmanapi.domain.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.cql.CqlTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CassandraDbSaleRepository implements SaleRepository {

    private final CqlTemplate cqlTemplate;

    @Autowired
    public CassandraDbSaleRepository(CqlTemplate cqlTemplate) {
        this.cqlTemplate = cqlTemplate;
    }

    @Override
    public List<Sale> getSales() {
        return cqlTemplate.query("select id, totalvalue, salesman from sale",
                (row, rowNum) -> {
                    Sale sale = new Sale(
                            row.getLong("id"),
                            row.getString("salesman"),
                            row.getBigDecimal("totalvalue"));
                    return sale;
                });
    }

    @Override
    public void save(Sale sale) {
        cqlTemplate.execute("INSERT INTO salesman_database.sale (archive_name, totalvalue, salesman, id) VALUES (?, ?, ?, ?)", sale.getArchiveName(), sale.getTotalSaleValue(), sale.getSalesmanName(), sale.getId());
    }

    @Override
    public Sale getBestSale() {
        return cqlTemplate.queryForObject("select id, max(totalValue) as totalvalue, salesman, archive_name from sale",
                (row, rowNum) -> {
                    Sale sale = new Sale(
                            row.getLong("id"),
                            row.getString("salesman"),
                            row.getBigDecimal("totalvalue"),
                            row.getString("archive_name"));
                    return sale;
                });
    }
}
