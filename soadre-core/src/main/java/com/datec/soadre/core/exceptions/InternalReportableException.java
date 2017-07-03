/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.exceptions;


public class InternalReportableException extends ApplicationException {
    
    public InternalReportableException(String message) {
        super(message);
    }

    public InternalReportableException(String message, Throwable cause) {
        super(message, cause);
    }

}
