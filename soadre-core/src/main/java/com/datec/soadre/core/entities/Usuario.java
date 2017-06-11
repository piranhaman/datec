/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.entities;

import com.datec.soadre.core.enums.EstatusUsuario;
import com.datec.soadre.core.enums.TipoUsuario;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author Piranhaman
 */
@Entity
public class Usuario implements Serializable {

    @Id
    @GeneratedValue
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    private Integer id;
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    private String nombre;
    
    @Enumerated(EnumType.STRING)
    public TipoUsuario getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(TipoUsuario tipoUsuario) { this.tipoUsuario = tipoUsuario; }
    private TipoUsuario tipoUsuario;
    
    public String getPass() { return pass; }
    public void setPass(String pass) { this.pass = pass; }
    private String pass;
    
    @Enumerated(EnumType.STRING)
    public EstatusUsuario getStatus() { return status; }
    public void setStatus(EstatusUsuario status) { this.status = status; }
    private EstatusUsuario status;
    
}
