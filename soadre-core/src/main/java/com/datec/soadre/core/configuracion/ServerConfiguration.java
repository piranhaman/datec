/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.configuracion;

import com.datec.soadre.core.exceptions.InternalException;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.deploy.SecurityCollection;
import org.apache.catalina.deploy.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

@Configuration
public class ServerConfiguration {
    
    @Value("${server.ssl.key-store:}")
    private String keystore;
    @Value("${server.httpsPortRedirect:}")
    private Integer httpsPortRedirect;
    @Value("${server.httpsPortRedirectTo:}")
    private Integer httpsPortRedirectTo;
    
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {                
                container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/pagina_error.xhtml"));
                container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/pagina_error.xhtml"));
                container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/pagina_error.xhtml"));
            }
        };
    }
    
    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = 
                new TomcatEmbeddedServletContainerFactory() {
                    @Override
                    protected void postProcessContext(Context context) {
                        if (!StringUtils.isEmpty(keystore)) {
                            SecurityConstraint securityConstraint = new SecurityConstraint();
                            securityConstraint.setUserConstraint("CONFIDENTIAL");
                            SecurityCollection collection = new SecurityCollection();
                            collection.addPattern("/*");
                            securityConstraint.addCollection(collection);
                            context.addConstraint(securityConstraint);
                        } else {
                            super.postProcessContext(context);
                        }
                    }
                };
        if (httpsPortRedirect != null) {
            if (httpsPortRedirect == null) {
                throw new InternalException("Debe de proporcionar el puerto al cual redirigir");
            }
            Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
            connector.setScheme("http");
            connector.setSecure(false);
            connector.setPort(httpsPortRedirect);
            connector.setRedirectPort(httpsPortRedirectTo);
            tomcat.addAdditionalTomcatConnectors(connector);
        }
        return tomcat;
    }
    
}
