/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.services;

import com.datec.soadre.core.entities.Consumible;
import com.datec.soadre.core.entities.Inventario;
import com.datec.soadre.core.entities.Usuario;
import com.datec.soadre.core.enums.AreaProductiva;
import com.datec.soadre.core.enums.EmptyCollectionCheck;
import com.datec.soadre.core.enums.Estatus;
import com.datec.soadre.core.enums.ExistCheck;
import com.datec.soadre.core.enums.NullCheck;
import com.datec.soadre.core.exceptions.BusinessException;
import static com.datec.soadre.core.frames.PanelDeControl.getFechaMenosCincoHoras;
import java.time.LocalDate;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Piranhaman
 */
@Service
@Lazy
public class InventarioService {

    @Autowired
    private ConsumibleService consumibleService;
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public Inventario buscarInventarioPorId(Integer id, NullCheck nullCheck, ExistCheck existCheck) {
        Inventario inventario;
        if (id == null) {
            switch (nullCheck) {
                case SAFE_NULL:
                    return null;
                case EXCEPTION_IF_NULL:
                    throw new BusinessException("Debe de proporcionar un id de inventario");
            }
        }

        inventario = (Inventario) sessionFactory.getCurrentSession().createQuery("Select i from Inventario i where i.id=:id")
                .setParameter("id", id)
                .uniqueResult();

        if (inventario == null && existCheck == ExistCheck.EXCEPTION_IF_NOT_EXIST) {
            throw new BusinessException("No existe el inventario con el ID: " + id);
        }
        return inventario;
    }

    @Transactional
    public List<Inventario> buscarInventarioPorArenaProductivaYFecha(AreaProductiva areaProductiva, LocalDate fecha, EmptyCollectionCheck emptyCollectionCheck) {
        List<Inventario> inventarios;

        inventarios = (List<Inventario>) sessionFactory.getCurrentSession().createQuery("Select i from Inventario i where i.areaProductiva=:areaProductiva and i.fecha=:fecha")
                .setParameter("areaProductiva", areaProductiva)
                .setParameter("fecha", fecha)
                .list();

        if (inventarios.isEmpty() && emptyCollectionCheck == EmptyCollectionCheck.EXCEPTION_IF_EMPTY) {
            throw new BusinessException("No existen inventario con el area productiva:" + areaProductiva.getDescripcion() + "y la fecha: " + fecha);
        }
        return inventarios;
    }

    @Transactional
    public Inventario buscarInventarioPorArenaProductivaFechaYConsumible(AreaProductiva areaProductiva, LocalDate fecha, Consumible consumible, NullCheck nullCheck, ExistCheck existCheck) {
        Inventario inventario;
        if (areaProductiva == null || consumible == null || fecha == null) {
            switch (nullCheck) {
                case SAFE_NULL:
                    return null;
                case EXCEPTION_IF_NULL:
                    throw new BusinessException("Debe de proporcionar un area productiva, una fecha y un consumible");
            }
        }

        inventario = (Inventario) sessionFactory.getCurrentSession().createQuery("Select i from Inventario i where i.areaProductiva=:areaProductiva and i.fecha=:fecha and i.consumible=:consumible")
                .setParameter("areaProductiva", areaProductiva)
                .setParameter("fecha", fecha)
                .setParameter("consumible", consumible)
                .list();

        if (inventario == null && existCheck == ExistCheck.EXCEPTION_IF_NOT_EXIST) {
            throw new BusinessException("No existe el inventario en el area: " + areaProductiva + "  con la fecha: " + fecha + " y con el consumible: " + consumible.getNombre());
        }
        return inventario;
    }

    @Transactional
    public void inicializarInventarioDeHoy(AreaProductiva areaProductiva) {

        List<Consumible> consumibles = consumibleService.buscarConsumiblesPorArenaProductiva(areaProductiva, EmptyCollectionCheck.EMPTY_COLLECTION);

        LocalDate fechaMenosCincoHoras = getFechaMenosCincoHoras();

        consumibles.stream().forEach(c -> {
            Inventario inventario = buscarInventarioPorArenaProductivaFechaYConsumible(areaProductiva, fechaMenosCincoHoras, c, NullCheck.EXCEPTION_IF_NULL, ExistCheck.NULL_IF_NOT_EXIST);
            if (inventario == null) {
                inventario = new Inventario();
                inventario.setAreaProductiva(areaProductiva);
                inventario.setCantidadInicial(0d);
                inventario.setCantidadFinal(0d);
                inventario.setFecha(fechaMenosCincoHoras);
                inventario.setConsumible(c);
                sessionFactory.getCurrentSession().saveOrUpdate(c);
            }
        });

    }

}
