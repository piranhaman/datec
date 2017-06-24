/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.entities;

import com.datec.soadre.core.enums.EstatusCuenta;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author Piranhaman
 */
@Entity
public class Cuenta implements Serializable {

    @Id
    @GeneratedValue
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    private Integer id;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    private Date fecha;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getHoraApertura() { return horaApertura; }
    public void setHoraApertura(Date horaApertura) { this.horaApertura = horaApertura; }
    private Date horaApertura;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getHoraCierre() { return horaCierre; }
    public void setHoraCierre(Date horaCierre) { this.horaCierre = horaCierre; }
    private Date horaCierre;
    
    public Integer getNumeroMesa() { return numeroMesa; }
    public void setNumeroMesa(Integer numeroMesa) { this.numeroMesa = numeroMesa; }
    private Integer numeroMesa;

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    private Double total;
    
    @Enumerated(EnumType.STRING)
    public EstatusCuenta getEstatus() { return estatus; }
    public void setEstatus(EstatusCuenta estatus) { this.estatus = estatus; }
    private EstatusCuenta estatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarioId")
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    private Usuario usuario;
    
    @OneToMany(mappedBy = "cuenta", fetch = FetchType.LAZY)
    public List<DetalleCuenta> getDetallesCuenta() { return detallesCuenta; }
    public void setDetallesCuenta(List<DetalleCuenta> detallesCuenta) { this.detallesCuenta = detallesCuenta; }
    private List<DetalleCuenta> detallesCuenta;
    
}
