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
public enum EstatusUsuario {
    ACTIVO("Activo"),
    INACTIVO("Inactivo");

    private final String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    EstatusUsuario(String descripcion) {
        this.descripcion = descripcion;
    }

}
