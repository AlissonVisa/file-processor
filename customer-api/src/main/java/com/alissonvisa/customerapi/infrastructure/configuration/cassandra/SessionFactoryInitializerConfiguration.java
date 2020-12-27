package com.alissonvisa.customerapi.infrastructure.configuration.cassandra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.cassandra.SessionFactory;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.cql.session.init.CompositeKeyspacePopulator;
import org.springframework.data.cassandra.core.cql.session.init.ResourceKeyspacePopulator;
import org.springframework.data.cassandra.core.cql.session.init.SessionFactoryInitializer;

@Configuration
public class SessionFactoryInitializerConfiguration extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.keyspaceName}")
    private String keyspaceName;

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
