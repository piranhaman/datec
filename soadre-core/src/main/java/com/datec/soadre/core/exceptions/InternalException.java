/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.exceptions;


public class InternalException extends ApplicationException {
    
    public InternalException(String message) {
        super(message);
    }

    public InternalException(String message, Throwable cause) {
        super(message, cause);
    }

}
