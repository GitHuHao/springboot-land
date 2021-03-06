package com.bigdata.boot.chapter13.config;

import com.datastax.driver.core.Session;
import org.cassandraunit.CQLDataLoader;
import org.cassandraunit.dataset.cql.FileCQLDataSet;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;

@Configuration
@PropertySource("classpath:application.properties")
public class CassandraTestConfig extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.keyspace-name}")
    public String keyspace;

    private Logger logger = LoggerFactory.getLogger(CassandraTestConfig.class);

    @Bean
    @Override
    public CassandraClusterFactoryBean cluster() {
        try {
            EmbeddedCassandraServerHelper.startEmbeddedCassandra(EmbeddedCassandraServerHelper.DEFAULT_CASSANDRA_YML_FILE, 1000000L);
            EmbeddedCassandraServerHelper.getCluster().getConfiguration().getSocketOptions().setReadTimeoutMillis(1000000);
            Session session = EmbeddedCassandraServerHelper.getSession();
            CQLDataLoader dataLoader = new CQLDataLoader(session);
            String initScript = getClass().getClassLoader().getResource("setup.cql").getPath();
            dataLoader.load(new FileCQLDataSet(initScript, true, true, getKeyspaceName()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Can't start Embedded Cassandra", e);
        }
        return super.cluster();
    }

    @Override
    protected String getKeyspaceName() {
        return keyspace;
    }

    @Override
    protected int getPort() {
        return 9142;
    }
}