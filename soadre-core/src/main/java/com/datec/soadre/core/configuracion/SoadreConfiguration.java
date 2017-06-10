/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.configuracion;

import java.io.Serializable;
import org.springframework.stereotype.Component;


/**
 *
 * @author other
 */

@Component
public class SoadreConfiguration implements Serializable{
    private transient String carpetaBase;
    private transient String archivoConfiguracion;
    private transient String rutaAplicacion;
    private String databaseUser;
    private String databasePassword;
    private String databaseUrl;
    
    public String getCarpetaBase() {
        return carpetaBase;
    }

    public void setCarpetaBase(String carpetaBase) {
        this.carpetaBase = carpetaBase;
    }

    public String getArchivoConfiguracion() {
        return archivoConfiguracion;
    }

    public void setArchivoConfiguracion(String archivoConfiguracion) {
        this.archivoConfiguracion = archivoConfiguracion;
    }

    public String getRutaAplicacion() {
        return rutaAplicacion;
    }

    public void setRutaAplicacion(String rutaAplicacion) {
        this.rutaAplicacion = rutaAplicacion;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public void setDatabaseUser(String databaseUser) {
        this.databaseUser = databaseUser;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }
}
