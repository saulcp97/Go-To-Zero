/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gotozero.Organice;

/**
 *
 * @author saulc
 * @param <T>
 */
public interface Actualizable <N>{
    N clone();
    void actualizar(Object i);
    @Override
    String toString();
}
 