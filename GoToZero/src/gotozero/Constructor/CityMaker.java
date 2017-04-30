/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gotozero.Constructor;

import gotozero.world;

/**
 *
 * @author saulc
 */
public class CityMaker {
    
    
    public static void CrearCiudad(world w, int Type, int X, int Y, int Z){
        switch(Type) {
            case 1:
                Edify.addHouse(w, 0, X, Y, Z);
                break;       
        }
    }
    
    
}
