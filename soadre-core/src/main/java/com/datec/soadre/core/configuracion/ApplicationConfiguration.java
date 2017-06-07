package com.datec.soadre.core.configuracion;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "pl.dmichalski.reservations.business")
@EnableJpaRepositories(basePackages = "pl.dmichalski.reservations.business.repository")
public class ApplicationConfiguration {
}
