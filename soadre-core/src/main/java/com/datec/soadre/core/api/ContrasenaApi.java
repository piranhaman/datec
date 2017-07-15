/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author piranhaman
 */
@RestController
public class ContrasenaApi extends AbstractApi{

    @Autowired
    public PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/api/cifrar", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public String cifrarText() {
        return passwordEncoder.encode("123456");
    }

}
