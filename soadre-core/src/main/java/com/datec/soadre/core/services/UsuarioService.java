/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.services;

import com.datec.soadre.core.entities.Usuario;
import com.datec.soadre.core.enums.EmptyCollectionCheck;
import com.datec.soadre.core.enums.EstatusUsuario;
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
public class UsuarioService {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Transactional
    public Usuario buscarUsuarioPorId(Integer id, NullCheck nullCheck, ExistCheck existCheck){
        Usuario usuario;
        if (id == null) {
            switch (nullCheck) {
                case SAFE_NULL:
                    return null;
                case EXCEPTION_IF_NULL:
                default:
                    throw new BusinessException("Debe de proporcionar un id de la carga");
            }
        }
        
        usuario = (Usuario) sessionFactory.getCurrentSession().createQuery("Select u from Usuario u where u.id=:id")
                .setParameter("id", id)
                .uniqueResult();
        
        if(usuario==null && existCheck == ExistCheck.EXCEPTION_IF_NOT_EXIST){
            throw new BusinessException("No existe el usuario con el ID: "+id);
        }        
        return usuario;
    }
    
    @Transactional
    public List<Usuario> buscarUsuariosPorEstatus(EstatusUsuario estatusUsuario,EmptyCollectionCheck emptyCollectionCheck){
        List<Usuario> usuario;
       
        usuario = (List<Usuario>) sessionFactory.getCurrentSession().createQuery("Select u from Usuario u where u.status=:status")
                .setParameter("status", estatusUsuario)
                .list();
        
        if(usuario.isEmpty() && emptyCollectionCheck == EmptyCollectionCheck.EXCEPTION_IF_EMPTY){
            throw new BusinessException("No existen usuarios con el estatus:"+ estatusUsuario.getDescripcion() );
        }        
        return usuario;
    }
    
    @Transactional
    public List<Usuario> buscarUsuarios(EmptyCollectionCheck emptyCollectionCheck){
        List<Usuario> usuario;
       
        usuario = (List<Usuario>) sessionFactory.getCurrentSession().createQuery("Select u from Usuario u")
                .list();
        
        if(usuario.isEmpty() && emptyCollectionCheck == EmptyCollectionCheck.EXCEPTION_IF_EMPTY){
            throw new BusinessException("No hay usuarios registrados");
        }        
        return usuario;
    }
}
