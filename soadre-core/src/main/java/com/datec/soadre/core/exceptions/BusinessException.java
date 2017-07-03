/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.exceptions;

import java.util.List;

public class BusinessException extends ApplicationException {
    private List<String> mensajesAdicionales;

    public BusinessException(String message) {
        super(message);        
    }
    
    public BusinessException(String message, List<String> mensajesAdicionales) {
        super(message);
        this.mensajesAdicionales = mensajesAdicionales;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public List<String> getMensajesAdicionales() {
        return mensajesAdicionales;
    }

    public void setMensajesAdicionales(List<String> mensajesAdicionales) {
        this.mensajesAdicionales = mensajesAdicionales;
    }

}
