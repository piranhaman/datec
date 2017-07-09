/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.util;

/**
 *
 * @author hugo
 */
@FunctionalInterface
public interface TriConsumer<T, U, S> {
    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @param s the third function argument
     * @return the function result
     */
    void accept(T t, U u, S s);   
}
