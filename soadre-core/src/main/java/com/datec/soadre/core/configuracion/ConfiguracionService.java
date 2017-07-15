/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.configuracion;


import com.datec.soadre.core.exceptions.BusinessException;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.StringUtils;

/**
 *
 * @author other
 */
@Configuration
@Lazy (true)
public class ConfiguracionService {

    @Value("${app.name:}")
    private String appName;
    private final String CARPETA_BASE = "/archivos/archivos-aplicaciones";

    @Bean
    @Lazy(true)
    public SoadreConfiguration configuration() {
        SoadreConfiguration configuracionSistema = null;
        if (StringUtils.isEmpty(appName)) {
            throw new BusinessException("No se ha configurado el nombre de la aplicación");
        }
        String archivoConfiguracion = CARPETA_BASE + "/" + appName + ".config";
        String rutaAplicacion = CARPETA_BASE + "/" + appName;
        try {
            configuracionSistema = (SoadreConfiguration) new Gson().fromJson(new FileReader(archivoConfiguracion), SoadreConfiguration.class);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataSourceConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
        configuracionSistema.setArchivoConfiguracion(archivoConfiguracion);
        configuracionSistema.setRutaAplicacion(rutaAplicacion);
        configuracionSistema.setCarpetaBase(CARPETA_BASE);
        return configuracionSistema;
    }
}
