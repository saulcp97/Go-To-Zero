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
public class hilo extends Thread{
    private int id;
    private int padre;
    private static boolean free = true;
    
    private boolean permision = false;
    private worker Bob;
    public static Edificio citadel = new Edificio();
    
    public hilo(worker e){
       this.id = -1;
       this.padre = -1;
       this.Bob = e;
       
       
       
    }
    
    public hilo(int i) {
        this.id = i;
        this.padre = -1;
    }
    
    public hilo(int i,int p) {
        this.id = i;
        this.padre = p;
    }
    
    public void setId(int n){
        this.id = n;
    }

    public void setPermision(boolean permision) {
        this.permision = permision;
    }
    
    @Override
    public void run(){
        /*
        
            Acciones previas como analisis del entorno
        
        */
        
        
        if(this.Bob != null){
            while(this.Bob.llevaLadrillo){
                this.Bob.avanzar(citadel);
            }
        }
        
        /*
        while(!permision){
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {}
        }
        System.out.println(this.id);
        */ 
    }
    
    public void tenerHijo(int nombre){
        (new hilo(new worker(10))).start();
    }
    
}
