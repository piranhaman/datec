/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.exceptions;

import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;

/**
 *
 * @author Piranhaman
 */
public class BusinessException extends InternalException{

    public BusinessException(String mensaje) {
        super(mensaje);
    }

}
