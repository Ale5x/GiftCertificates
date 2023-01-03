package com.epam.esm.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * The type Development profile configuration is a configuration java class.
 * Used for integration tests with an in-memory embedded database.
 *
 * @author Alexander Pishchala
 */
@SpringBootApplication(scanBasePackages = "com.epam.esm", exclude = HibernateJpaAutoConfiguration.class)
@PropertySource("classpath:application-development.properties")
@EnableTransactionManagement
@Profile("development")
public class DevelopmentConfig {

    private final DataSource dataSource;

    @Autowired
    public DevelopmentConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * LocalSessionFactoryBean bean for Hibernate. It auto-configures dataSource.
     *
     * @return sessionFactory for Hibernate.
     */
    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("com.epam.esm");
        return sessionFactory;
    }

    /**
     * Platform Transaction Manager for Hibernate. This class provides simple access to a database through JDBC.
     *
     * @return the transactionManager.
     */
    @Bean
    public PlatformTransactionManager getPlatformTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }

    /**
     * The password encryptor.
     *
     * @return the BCryptPasswordEncoder entity.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
