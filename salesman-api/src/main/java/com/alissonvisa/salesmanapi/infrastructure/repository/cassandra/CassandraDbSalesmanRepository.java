package com.alissonvisa.salesmanapi.infrastructure.repository.cassandra;

import com.alissonvisa.salesmanapi.domain.Salesman;
import com.alissonvisa.salesmanapi.domain.SalesmanRankingPosition;
import com.alissonvisa.salesmanapi.domain.repository.SalesmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.cql.CqlTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CassandraDbSalesmanRepository implements SalesmanRepository {

    private final SpringDataCassandraSalesmanRepository cassandraRepository;

    private final CqlTemplate cqlTemplate;

    @Autowired
    public CassandraDbSalesmanRepository(SpringDataCassandraSalesmanRepository cassandraRepository, CqlTemplate cqlTemplate) {
        this.cassandraRepository = cassandraRepository;
        this.cqlTemplate = cqlTemplate;
    }

    @Override
    public Long getSalesmanCountByArchive(String archiveName) {
        long count = cqlTemplate.queryForObject(
                "SELECT COUNT(*) FROM salesman_database.salesman WHERE import_archive = ? ALLOW FILTERING", Long.class, archiveName);
        return count;
    }

    @Override
    public SalesmanRankingPosition getWorstSalesmanInArchive(String importArchive) {
        SalesmanRankingPosition rankingPositions = cqlTemplate.queryForObject(
                "select name, min(total_sold) as total_value_sold from salesman_database.salesman where import_archive = ?",
                (row, rowNum) -> {
                    SalesmanRankingPosition rankingPosition = new SalesmanRankingPosition(
                            row.getString("name"),
                            row.getBigDecimal("total_value_sold"));
                    return rankingPosition;
                }, importArchive);
        return rankingPositions;
    }

    @Override
    public void save(Salesman salesman) {
        cqlTemplate.execute("INSERT INTO salesman_database.salesman (name, cpf, import_archive, salary, total_sold) VALUES (?, ?, ?, ?, ?)", salesman.getName(), salesman.getCpf(), salesman.getImportArchive(), salesman.getSalary(), salesman.getTotalSold());
    }

    @Override
    public void update(Salesman salesman) {
        cqlTemplate.execute("UPDATE salesman_database.salesman SET cpf = ?, salary = ?, total_sold = ? WHERE name = ? and import_archive = ?", salesman.getCpf(), salesman.getSalary(), salesman.getTotalSold(), salesman.getName(), salesman.getImportArchive());
    }

    @Override
    public Optional<Salesman> getByNameAndArchive(String salesmanName, String archiveName) {
        final Optional<SalesmanEntity> salesmanEntity = this.cassandraRepository
                .findSalesmanEntityByImportArchiveAndName(archiveName, salesmanName);
        if(salesmanEntity.isPresent()) {
            return Optional.of(salesmanEntity.get().toDomain());
        }
        return Optional.empty();
    }

    private SalesmanEntity getSalesmanEntity(Salesman salesman) {
        return this.cassandraRepository
                .findSalesmanEntityByImportArchiveAndName(salesman.getImportArchive(), salesman.getName())
                .orElseThrow(() -> new RuntimeException(
                        String.format("Salesman with given salesman: %s and archive: %s doesn't exist",
                                salesman.getName(), salesman.getImportArchive())));
    }
}
