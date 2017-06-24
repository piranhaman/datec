/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.entities;

import com.datec.soadre.core.enums.AreaProductiva;
import com.datec.soadre.core.enums.Estatus;
import com.datec.soadre.core.enums.Estatus;
import com.datec.soadre.core.enums.TipoUsuario;
import com.datec.soadre.core.enums.UnidadDeMedida;
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
public class ElementoMenu implements Serializable {

    @Id
    @GeneratedValue
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    private Integer id;
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    private String nombre;
    
    public Double getCosto() { return costo; }
    public void setCosto(Double costo) { this.costo = costo; }
    private Double costo;
    
    @Enumerated(EnumType.STRING)
    public AreaProductiva getAreaProductiva() { return areaProductiva; }
    public void setAreaProductiva(AreaProductiva areaProductiva) { this.areaProductiva = areaProductiva; }
    private AreaProductiva areaProductiva;
    
    @Enumerated(EnumType.STRING)
    public Estatus getEstatus() { return estatus; }
    public void setEstatus(Estatus estatus) { this.estatus = estatus; }
    private Estatus estatus;
    
}
