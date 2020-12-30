package com.alissonvisa.salesapi.infrastructure.configuration.cassandra;

import com.alissonvisa.salesapi.infrastructure.repository.cassandra.CassandraDbSaleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.cassandra.SessionFactory;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.core.cql.session.init.CompositeKeyspacePopulator;
import org.springframework.data.cassandra.core.cql.session.init.ResourceKeyspacePopulator;
import org.springframework.data.cassandra.core.cql.session.init.SessionFactoryInitializer;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableCassandraRepositories(basePackageClasses = CassandraDbSaleRepository.class)
public class SessionFactoryInitializerConfiguration extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.keyspaceName}")
    private String keyspaceName;

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {

        CreateKeyspaceSpecification specification = CreateKeyspaceSpecification.createKeyspace(keyspaceName)
                .ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true);

        return Arrays.asList(specification);
    }

    @Bean
    SessionFactoryInitializer sessionFactoryInitializer(SessionFactory sessionFactory) {

        SessionFactoryInitializer initializer = new SessionFactoryInitializer();
        initializer.setSessionFactory(sessionFactory);

        ResourceKeyspacePopulator populate = new ResourceKeyspacePopulator();
        populate.setSeparator(";");
        populate.setScripts(new ClassPathResource("cassandra-db-schema.cql"));

        initializer.setKeyspacePopulator(new CompositeKeyspacePopulator(populate));

        return initializer;
    }

    @Override
    protected String getKeyspaceName() {
        return keyspaceName;
    }

}
