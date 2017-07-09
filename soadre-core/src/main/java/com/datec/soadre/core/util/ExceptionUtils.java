/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.util;


import com.datec.soadre.core.exceptions.BusinessException;
import com.datec.soadre.core.exceptions.InternalException;
import com.datec.soadre.core.exceptions.InternalReportableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

public class ExceptionUtils extends EasyLog {    
    
    public enum OpcionPuntoFinal {
        SIN_PUNTO_FINAL, CON_PUNTO_FINAL
    }
    public static String extraerMensaje(Exception ex) {
        return extraerMensaje(ex, false);
    }
    
    public static String extraerMensaje(Exception ex, boolean traeNombreClase) {
        return extraerMensaje(ex, traeNombreClase, null);
    }
    
    public static String extraerMensaje(Exception ex, Class ... claseConocida) {
        return extraerMensaje(ex, false, claseConocida);
    }

    public static String extraerMensaje(Exception ex, boolean traeNombreClase, Class ... clasesConocidas) {
        String mensajes;
        try {
            Throwable th = ex;
            List<String> mensajesList = new ArrayList<String>();
            //StringBuilder mensajesBuilder = new StringBuilder();        
            while (th != null) {            
                String mensaje = th.getMessage();
                final Class clase = th.getClass();
                if (th instanceof InternalException || th.getClass().getName().equals("javax.servlet.ServletException") || th instanceof NullPointerException) {
                    // Salta estas excepciones
                } else if (th instanceof InternalReportableException) {
                        mensajesList.add("Ha ocurrido un error interno, favor de reportarlo al desarrollador");
                } else if (!StringUtils.isEmpty(mensaje)) {                    
                    // Sólo manda mensaje de las excepciones reconocibles
                    if (th instanceof BusinessException ||
                            (clasesConocidas != null && Arrays.asList(clasesConocidas).stream().anyMatch(claseConocida -> claseConocida.isAssignableFrom(clase)))) {
                        if (traeNombreClase) {                            
                            mensaje = mensaje.substring(mensaje.indexOf(": ") + 1);
                        }
                        mensajesList.add(mensaje);                        
                    }
                }
                th = th.getCause();
            }
            if (mensajesList.isEmpty()) {
                mensajes = "Error interno del servidor";
            } else {
                mensajes = StringUtils.join(mensajesList, ". ");
            }            
        } catch (Exception excepcionInesperada) {
            // Esto nunca debería de ocurrir. Es por si acaso, para no romper el flujo
            mensajes = "Error al leer la excepción" + excepcionInesperada.getMessage() != null ? " " + excepcionInesperada.getMessage() : "";
        }
        Logger.getLogger(ExceptionUtils.class.getName()).log(Level.WARNING, mensajes, ex);
        return mensajes;
    }
}
