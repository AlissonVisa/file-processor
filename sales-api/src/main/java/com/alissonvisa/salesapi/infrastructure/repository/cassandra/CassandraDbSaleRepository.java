package com.alissonvisa.salesapi.infrastructure.repository.cassandra;

import com.alissonvisa.salesapi.domain.Sale;
import com.alissonvisa.salesapi.domain.repository.SaleRepository;
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
    public List<Sale> getSales(String archiveName) {
        return cqlTemplate.query("select id, totalvalue, salesman from sale where archive_name = ?",
                (row, rowNum) -> {
                    Sale sale = new Sale(
                            row.getLong("id"),
                            row.getString("salesman"),
                            row.getBigDecimal("totalvalue"));
                    return sale;
                }, archiveName);
    }

    @Override
    public void save(Sale sale) {
        cqlTemplate.execute("INSERT INTO sale (archive_name, totalvalue, salesman, id) VALUES (?, ?, ?, ?)", sale.getArchiveName(), sale.getTotalSaleValue(), sale.getSalesmanName(), sale.getId());
    }

    @Override
    public Sale getBestSale(String archiveName) {
        return cqlTemplate.queryForObject("select id, max(totalValue) as totalvalue, salesman, archive_name from sale where archive_name = ?",
                (row, rowNum) -> {
                    Sale sale = new Sale(
                            row.getLong("id"),
                            row.getString("salesman"),
                            row.getBigDecimal("totalvalue"),
                            row.getString("archive_name"));
                    return sale;
                }, archiveName);
    }
}
