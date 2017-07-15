/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.api.usuario;

import com.datec.soadre.core.enums.Estatus;
import com.datec.soadre.core.enums.Perfil;

/**
 *
 * @author piranhaman
 */
public class Usuario {
    private Integer id;
    private String nombre;
    private Perfil perfil;
    private String pass;
    private Estatus status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Estatus getStatus() {
        return status;
    }

    public void setStatus(Estatus status) {
        this.status = status;
    }
    
    
}
