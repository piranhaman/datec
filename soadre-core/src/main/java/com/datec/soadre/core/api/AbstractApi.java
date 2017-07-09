/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.api;



import com.datec.soadre.core.exceptions.RestExceptionMessage;
import com.datec.soadre.core.util.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author hugo
 */
public class AbstractApi {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestExceptionMessage handleAppException(Exception ex) {
        return new RestExceptionMessage(ExceptionUtils.extraerMensaje(ex));
    }

}
