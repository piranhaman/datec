/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.services;

import com.datec.soadre.core.configuracion.SoadreConfiguration;
import com.datec.soadre.core.entities.Usuario;
import com.datec.soadre.core.enums.EmptyCollectionCheck;
import com.datec.soadre.core.enums.Estatus;
import com.datec.soadre.core.enums.ExistCheck;
import com.datec.soadre.core.enums.NullCheck;
import com.datec.soadre.core.exceptions.BusinessException;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private SoadreConfiguration soadreConfiguration;

    @Transactional
    public Usuario buscarUsuarioPorId(Integer id, NullCheck nullCheck, ExistCheck existCheck) {
        Usuario usuario;
        if (id == null) {
            switch (nullCheck) {
                case SAFE_NULL:
                    return null;
                case EXCEPTION_IF_NULL:
                default:
                    throw new BusinessException("Debe de proporcionar un id de usuario");
            }
        }

        usuario = (Usuario) sessionFactory.getCurrentSession().createQuery("Select u from Usuario u where u.id=:id")
                .setParameter("id", id)
                .uniqueResult();

        if (usuario == null && existCheck == ExistCheck.EXCEPTION_IF_NOT_EXIST) {
            throw new BusinessException("No existe el usuario con el ID: " + id);
        }
        return usuario;
    }

    @Transactional
    public List<Usuario> buscarUsuariosPorEstatus(Estatus estatusUsuario, EmptyCollectionCheck emptyCollectionCheck) {
        List<Usuario> usuario;

        usuario = (List<Usuario>) sessionFactory.getCurrentSession().createQuery("Select u from Usuario u where u.status=:status")
                .setParameter("status", estatusUsuario)
                .list();

        if (usuario.isEmpty() && emptyCollectionCheck == EmptyCollectionCheck.EXCEPTION_IF_EMPTY) {
            throw new BusinessException("No existen usuarios con el estatus:" + estatusUsuario.getDescripcion());
        }
        return usuario;
    }

    @Transactional
    public Usuario buscarUsuarioPorNombre(String nombreUsuario, NullCheck nullCheck, ExistCheck existCheck) {
        Usuario usuario;
        if (nombreUsuario == null) {
            switch (nullCheck) {
                case SAFE_NULL:
                    return null;
                case EXCEPTION_IF_NULL:
                default:
                    throw new BusinessException("Debe de proporcionar un nombre de usuario");
            }
        }

        usuario = (Usuario) sessionFactory.getCurrentSession().createQuery("Select u from Usuario u where u.nombre =:nombre")
                .setParameter("nombre", nombreUsuario)
                .uniqueResult();

        if (usuario == null && existCheck == ExistCheck.EXCEPTION_IF_NOT_EXIST) {
            throw new BusinessException("No existe el usuario con el nombre: '" + nombreUsuario + "'");
        }
        return usuario;
    }

    @Transactional
    public List<Usuario> buscarUsuarios(EmptyCollectionCheck emptyCollectionCheck) {
        List<Usuario> usuario;

        usuario = (List<Usuario>) sessionFactory.getCurrentSession().createQuery("Select u from Usuario u")
                .list();

        if (usuario.isEmpty() && emptyCollectionCheck == EmptyCollectionCheck.EXCEPTION_IF_EMPTY) {
            throw new BusinessException("No hay usuarios registrados");
        }
        return usuario;
    }

    @Transactional
    public Usuario buscarUsuarioEnSession(NullCheck nullCheck, ExistCheck existsCheck) {
        String nombreUsuario;
        if (soadreConfiguration.getWebUser() != null) {
            nombreUsuario = soadreConfiguration.getWebUser();
        } else {
            nombreUsuario = getUsernameEnSession();
        }
        Usuario usuario = buscarUsuarioPorNombre(nombreUsuario, nullCheck, existsCheck);
        return usuario;
    }

    public String getUsernameEnSession() {
        String username = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null) {
            Authentication authentication = securityContext.getAuthentication();
            if (authentication != null) {
                username = authentication.getName();
            }
        }
        return username;
    }

    @Transactional
    public Usuario deserializarUsuario(com.datec.soadre.core.api.usuario.Usuario usuarioJson) {

        Usuario usuario = buscarUsuarioPorId(Integer.SIZE, NullCheck.SAFE_NULL, ExistCheck.EXCEPTION_IF_NOT_EXIST);
        if (usuario == null) {
            usuario = new Usuario();
        }
        usuario.setId(usuarioJson.getId());
        usuario.setPerfil(usuarioJson.getPerfil());
        if (StringUtils.isEmpty(usuarioJson.getNombre())) {
            throw new BusinessException("Debe de proporcionar un nombre de usuario");
        }
        if (StringUtils.isEmpty(usuarioJson.getPass())) {
            throw new BusinessException("Debe de proporcionar una contraseña");
        }
        if (!passwordEncoder.matches(usuarioJson.getPass(), usuario.getPass())) {
            usuario.setPass(passwordEncoder.encode(usuarioJson.getPass()));
        }
        usuario.setNombre(usuarioJson.getNombre());
        return usuario;
    }

    public com.datec.soadre.core.api.usuario.Usuario serializarUsuario(Usuario usuarioEntity) {
        com.datec.soadre.core.api.usuario.Usuario usuarioJson = new com.datec.soadre.core.api.usuario.Usuario();
        BeanUtils.copyProperties(usuarioEntity, usuarioJson, "pass");
        return usuarioJson;
    }

}
