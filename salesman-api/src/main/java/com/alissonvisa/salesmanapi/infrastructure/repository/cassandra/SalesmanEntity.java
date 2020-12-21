package com.alissonvisa.salesmanapi.infrastructure.repository.cassandra;

import com.alissonvisa.salesmanapi.domain.Salesman;
import com.alissonvisa.salesmanapi.infrastructure.repository.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("salesman")
class SalesmanEntity implements Entity<Salesman> {

    @PrimaryKeyColumn(name = "name", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private String name;
    @PrimaryKeyColumn(name = "import_archive", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String importArchive;
    private String cpf;
    private BigDecimal salary;
    @Column("total_sold")
    private BigDecimal totalSold;

    public SalesmanEntity(Salesman salesman) {
        this.importArchive = salesman.getImportArchive();
        this.name = salesman.getName();
        this.cpf = salesman.getCpf();
        this.salary = salesman.getSalary();
        this.totalSold = salesman.getTotalSold();
    }

    @Override
    public Salesman toDomain() {
        return new Salesman(this.importArchive, this.name, this.cpf, this.salary, this.totalSold);
    }
}
