/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.api.usuario;

import com.datec.soadre.core.api.AbstractApi;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author edgar
 */
@RestController
public class UsuarioApi extends AbstractApi {

   
    @RequestMapping(value = "/api/prueba", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public String buscarUsuarioEnSesion() {
        return "asdasd";
    }
    
}
