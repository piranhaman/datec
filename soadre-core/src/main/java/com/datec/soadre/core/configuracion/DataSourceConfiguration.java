/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.configuracion;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author other
 */
@Configuration
@EnableTransactionManagement
@Lazy(true)
public class DataSourceConfiguration {

    @Autowired
    private SoadreConfiguration configuration;

    @Bean
    @Lazy(value = true)
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl(configuration.getDatabaseUrl());
        dataSource.setUser(configuration.getDatabaseUser());
        dataSource.setPassword(configuration.getDatabasePassword());
        // Para cuando se desconecte por mucho tiempo de la base se vuelva a reconectar
        dataSource.setTestConnectionOnCheckout(true);
        try {
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
        } catch (PropertyVetoException ex) {
            Logger.getLogger(DataSourceConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dataSource;
    }

    @Bean
    @Lazy
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }
}
