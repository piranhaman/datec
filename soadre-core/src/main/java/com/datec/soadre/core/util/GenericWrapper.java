/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.util;

/**
 *
 * @author edgar
 */
public class GenericWrapper<T> {

    private T object;

    public GenericWrapper() {
    }
    
    public GenericWrapper(T object) {
        this.object = object;
    }
    
    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

}
