package org.spring.data.access.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories("org.spring.data.access.repository")
@EnableTransactionManagement
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.username}")
    private String jdbcUsername;
    @Value("${spring.datasource.password}")
    private String jdbcPassword;
    @Value("${spring.datasource.driver}")
    private String jdbcDriver;

    @Bean("entityManagerFactory")
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        var emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPackagesToScan("org.spring.data.access.model");
        emf.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        emf.setDataSource(dataSource());
        emf.getJpaPropertyMap().put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        emf.getJpaPropertyMap().put("hibernate.show_sql", true);
        emf.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", "create");
        return emf;
    }

    @Bean
    TransactionManager transactionManager (EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    DataSource dataSource() {
        var hikariDatSource = new HikariDataSource();
        hikariDatSource.setJdbcUrl(jdbcUrl);
        hikariDatSource.setUsername(jdbcUsername);
        hikariDatSource.setPassword(jdbcPassword);
        hikariDatSource.setDriverClassName(jdbcDriver);
        return hikariDatSource;
    }
}