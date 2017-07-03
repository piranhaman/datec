/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.services.generic;


import org.springframework.security.core.userdetails.User;

public interface AutenticacionService {
    User autenticar(String username);
}
