/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.configuracion;

import com.datec.soadre.core.exceptions.BusinessException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 *
 * @author other
 */
@Configuration
@Lazy(true)
public class HibernateConfiguration {

    @Autowired
    private DataSourceConfiguration dataSourceConfiguration;

    @Bean
    @Lazy(value = true)
    public SessionFactory sessionFactory() {
        org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean factoryBean = new org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean();
        factoryBean.setDataSource(dataSourceConfiguration.dataSource());
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.setProperty("hibernate.show_sql", Boolean.toString(false));
        factoryBean.setHibernateProperties(properties);
        factoryBean.setPackagesToScan("com.datec.soadre");
        try {
            factoryBean.afterPropertiesSet();
        } catch (Exception ex) {
            Logger.getLogger(HibernateConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (SessionFactory) factoryBean.getObject();
    }
}
