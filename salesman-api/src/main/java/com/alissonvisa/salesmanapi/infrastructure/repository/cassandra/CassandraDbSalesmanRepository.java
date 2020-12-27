package com.alissonvisa.salesmanapi.infrastructure.repository.cassandra;

import com.alissonvisa.salesmanapi.domain.Salesman;
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
                "SELECT COUNT(*) FROM salesman WHERE import_archive = ?", Long.class, archiveName);
        return count;
    }

    @Override
    public void save(Salesman salesman) {
        cqlTemplate.execute("INSERT INTO salesman (name, cpf, import_archive, salary) VALUES (?, ?, ?, ?)", salesman.getName(), salesman.getCpf(), salesman.getImportArchive(), salesman.getSalary());
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
}
