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
public enum UnidadDeMedida {
    KILOGRAMO("Kilogramo", "kg"),
    GRAMO("Gramo", "g"),
    LITRO("Litro", "l"),
    MILILITRO("mililitro", "ml"),
    PIEZA("Pieza", "p"),
    BOTE("Bote", "b"),
    CAJA("Caja", "c");
    

    private final String descripcion;
    private final String simbolo;

    public String getDescripcion() {
        return descripcion;
    }

    public String getSimbolo() {
        return simbolo;
    }

    UnidadDeMedida(String descripcion, String simbolo) {
        this.descripcion = descripcion;
        this.simbolo = simbolo;
    }

}
