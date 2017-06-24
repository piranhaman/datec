/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.entities;

import com.datec.soadre.core.enums.AreaProductiva;
import com.datec.soadre.core.enums.Estatus;
import com.datec.soadre.core.enums.EstatusDetalleCuenta;
import java.io.Serializable;
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
public class DetalleCuenta implements Serializable {

    @Id
    @GeneratedValue
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    private Integer id;
    
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    private Integer cantidad;

    public Double getCosto() { return costo; }
    public void setCosto(Double costo) { this.costo = costo; }
    private Double costo;
    
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    private Double total;
    
    @Enumerated(EnumType.STRING)
    public EstatusDetalleCuenta getEstatus() { return estatus; }
    public void setEstatus(EstatusDetalleCuenta estatus) { this.estatus = estatus; }
    private EstatusDetalleCuenta estatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuentaId")
    public Cuenta getCuenta() { return cuenta; }
    public void setCuenta(Cuenta cuenta) { this.cuenta = cuenta; }
    private Cuenta cuenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "elementoMenuId")
    public ElementoMenu getElementoMenu() { return elementoMenu; }
    public void setElementoMenu(ElementoMenu elementoMenu) { this.elementoMenu = elementoMenu; }
    private ElementoMenu elementoMenu;
    
}
