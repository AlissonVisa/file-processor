package com.alissonvisa.salesapi.infrastructure.configuration.cassandra;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CreateKeyspaceConfiguration extends AbstractCassandraConfiguration implements BeanClassLoaderAware {

    @Value("${spring.data.cassandra.keyspaceName}")
    private String keyspaceName;

    @Override
    protected String getKeyspaceName() {
        return keyspaceName;
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {

        CreateKeyspaceSpecification specification = CreateKeyspaceSpecification.createKeyspace(keyspaceName)
                .ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true);

        return Arrays.asList(specification);
    }

}
