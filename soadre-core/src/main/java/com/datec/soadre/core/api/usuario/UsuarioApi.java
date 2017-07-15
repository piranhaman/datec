/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.api.usuario;

import com.datec.soadre.core.api.AbstractApi;
import com.datec.soadre.core.enums.ExistCheck;
import com.datec.soadre.core.enums.NullCheck;
import com.datec.soadre.core.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioApi extends AbstractApi {

    @Autowired
    private UsuarioService usuarioService;
   
    @RequestMapping(value = "/api/usuario", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Usuario buscarUsuarioEnSesion() {
        com.datec.soadre.core.entities.Usuario usuarioEnSession = usuarioService.buscarUsuarioEnSession(NullCheck.EXCEPTION_IF_NULL, ExistCheck.EXCEPTION_IF_NOT_EXIST);
        return usuarioService.serializarUsuario(usuarioEnSession);
    }
    
}
