/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.services;

import com.datec.soadre.core.entities.Consumible;
import com.datec.soadre.core.entities.Usuario;
import com.datec.soadre.core.enums.AreaProductiva;
import com.datec.soadre.core.enums.EmptyCollectionCheck;
import com.datec.soadre.core.enums.Estatus;
import com.datec.soadre.core.enums.ExistCheck;
import com.datec.soadre.core.enums.NullCheck;
import com.datec.soadre.core.exceptions.BusinessException;
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
public class ConsumibleService {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public Consumible buscarConsumiblePorId(Integer id, NullCheck nullCheck, ExistCheck existCheck) {
        Consumible consumible;
        if (id == null) {
            switch (nullCheck) {
                case SAFE_NULL:
                    return null;
                case EXCEPTION_IF_NULL:
                    throw new BusinessException("Debe de proporcionar un id de consumible");
            }
        }

        consumible = (Consumible) sessionFactory.getCurrentSession().createQuery("Select c from Consumible c where c.id=:id")
                .setParameter("id", id)
                .uniqueResult();

        if (consumible == null && existCheck == ExistCheck.EXCEPTION_IF_NOT_EXIST) {
            throw new BusinessException("No existe el consumible con el ID: " + id);
        }
        return consumible;
    }

    @Transactional
    public List<Consumible> buscarConsumiblesPorArenaProductiva(AreaProductiva areaProductiva, EmptyCollectionCheck emptyCollectionCheck) {
        List<Consumible> consumibles;

        consumibles = (List<Consumible>) sessionFactory.getCurrentSession().createQuery("Select c from Consumible c where c.areaProductiva=:areaProductiva")
                .setParameter("areaProductiva", areaProductiva)
                .list();

        if (consumibles.isEmpty() && emptyCollectionCheck == EmptyCollectionCheck.EXCEPTION_IF_EMPTY) {
            throw new BusinessException("No existen Consumibles con el area productiva:" + areaProductiva.getDescripcion());
        }
        return consumibles;
    }

}
