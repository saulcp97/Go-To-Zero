/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrenciaTest;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author saulc
 */
public class test {
    
    public static void main(String[] args){
        hilo ancestro = new hilo(new worker(10));
        for(int i = 0; i < 1000; ++i){
            ancestro.tenerHijo(0);
        }
        ancestro.start();

        
        
        
        
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(hilo.citadel.ladrillos);
        
        /*for(int i = 0; i <= 1000000; ++i){
             System.out.println(i);
             
        }*/
        
        
        
    }
    
}
