/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.entities;

import com.datec.soadre.core.enums.AreaProductiva;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Piranhaman
 */
@Entity
public class Inventario implements Serializable {

    @Id
    @GeneratedValue
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    private Integer id;
    
    public Double getCantidadInicial() { return cantidadInicial; }
    public void setCantidadInicial(Double cantidadInicial) { this.cantidadInicial = cantidadInicial; }
    private Double cantidadInicial;
    
    public Double getCantidadFinal() { return cantidadFinal; }
    public void setCantidadFinal(Double cantidadFinal) { this.cantidadFinal = cantidadFinal; }
    private Double cantidadFinal;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    private LocalDate fecha;
    
    @Enumerated(EnumType.STRING)
    public AreaProductiva getAreaProductiva() { return areaProductiva; }
    public void setAreaProductiva(AreaProductiva areaProductiva) { this.areaProductiva = areaProductiva; }
    private AreaProductiva areaProductiva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumibleId")
    public Consumible getConsumible() { return consumible; }
    public void setConsumible(Consumible cliente) { this.consumible = cliente; }
    private Consumible consumible;
    
}
