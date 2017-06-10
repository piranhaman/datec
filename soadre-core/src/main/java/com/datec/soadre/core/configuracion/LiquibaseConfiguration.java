/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.configuracion;


import com.datec.soadre.core.configuracion.DataSourceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import liquibase.integration.spring.SpringLiquibase;

/**
 *
 * @author other
 */
@Configuration
public class LiquibaseConfiguration {
    @Autowired
    private DataSourceConfiguration dataSourceConfiguration;

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSourceConfiguration.dataSource());        
        liquibase.setChangeLog("classpath:soadre-database-changelog.xml");
        return liquibase;
    }

}
