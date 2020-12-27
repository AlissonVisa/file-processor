package com.alissonvisa.salesapi.infrastructure.configuration.cassandra;

import com.alissonvisa.salesapi.infrastructure.repository.cassandra.CassandraDbSaleRepository;
import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.SessionFactory;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.SessionFactoryFactoryBean;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@EnableCassandraRepositories(basePackageClasses = CassandraDbSaleRepository.class)
public class CassandraConfiguration {

    @Value("${spring.data.cassandra.contactPoints}")
    private String contactPoints;

    @Value("${spring.data.cassandra.keyspaceName}")
    private String keyspaceName;

    @Bean
    public CqlSessionFactoryBean session() {

        CqlSessionFactoryBean session = new CqlSessionFactoryBean();
        session.setContactPoints(contactPoints);
        session.setKeyspaceName(keyspaceName);

        return session;
    }

    @Bean
    public SessionFactoryFactoryBean sessionFactory(CqlSession session, CassandraConverter converter) {

        SessionFactoryFactoryBean sessionFactory = new SessionFactoryFactoryBean();
        sessionFactory.setSession(session);
        sessionFactory.setConverter(converter);
        sessionFactory.setSchemaAction(SchemaAction.CREATE_IF_NOT_EXISTS);

        return sessionFactory;
    }

    @Bean
    public CassandraConverter converter(CassandraMappingContext mappingContext, CqlSession cqlSession) {
        final MappingCassandraConverter mappingCassandraConverter = new MappingCassandraConverter(mappingContext);
        mappingCassandraConverter.setUserTypeResolver(new SimpleUserTypeResolver(cqlSession));
        return mappingCassandraConverter;
    }

    @Bean
    public CassandraOperations cassandraTemplate(SessionFactory sessionFactory, CassandraConverter converter) {
        return new CassandraTemplate(sessionFactory, converter);
    }
}
