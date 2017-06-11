/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.enums;

/**
 *
 * @author Piranhaman
 */
public enum TipoUsuario {
    ADMINISTRADOR("Administrador"),
    CAJERO("Cajero"),
    TAQUERO("Taquero"),
    FUENTE_DE_SODAS("Fuente de Sodas"),
    MESERO("Mesero");

    private final String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    TipoUsuario(String descripcion) {
        this.descripcion = descripcion;
    }

}
