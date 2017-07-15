package com.datec.soadre.core.util;

import com.datec.soadre.core.bussines.LogAdvertencias;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EasyLog {
    public void logInfo(String message) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, message);
    }
    
    public void logError(String message) {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, message);
    }
    
    public void logError(Exception ex) {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
    }
    
    public void logWarning(Exception ex) {
        logWarning(ex.getMessage(), ex);
    }
    
    public void logWarning(String message, Exception ex) {
        Logger.getLogger(LogAdvertencias.class.getName()).log(Level.WARNING, message, ex);
    }
}
