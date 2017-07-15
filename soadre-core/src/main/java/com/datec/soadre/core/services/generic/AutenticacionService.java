/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.services.generic;

import com.datec.soadre.core.entities.Usuario;
import com.datec.soadre.core.enums.ExistCheck;
import com.datec.soadre.core.enums.NullCheck;
import com.datec.soadre.core.services.UsuarioService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutenticacionService {

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    public User autenticar(String nombre) {
        Usuario usuario = usuarioService.buscarUsuarioPorNombre(nombre, NullCheck.SAFE_NULL, ExistCheck.NULL_IF_NOT_EXIST);
        if (usuario == null) {
            throw new UsernameNotFoundException("No existe un usuario con el nombre: "+nombre);
        }
        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        User user = new User(nombre, usuario.getPass(), auths);
        return user;
    }
}
