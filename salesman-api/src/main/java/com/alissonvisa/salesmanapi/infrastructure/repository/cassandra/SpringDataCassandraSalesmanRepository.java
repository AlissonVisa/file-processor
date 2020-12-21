package com.alissonvisa.salesmanapi.infrastructure.repository.cassandra;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataCassandraSalesmanRepository extends CassandraRepository<SalesmanEntity, String> {

    Optional<SalesmanEntity> findSalesmanEntityByImportArchiveAndName(String importArchive, String salesmanName);

}
