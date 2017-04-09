/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrenciaTest;

/**
 *
 * @author saulc
 */
public class worker {
    public boolean llevaLadrillo;
    private int recorrido;
    private int distanciaDestino;
    
    public worker(int dist){
        this.recorrido = 0;
        this.llevaLadrillo = true;
        this.distanciaDestino = dist;
    }
    
    private void colocarLadrillo(Edificio edifice) {
        this.llevaLadrillo = false;
        this.recorrido = 0;
        synchronized(edifice){edifice.ladrillos++;}
    }
    public void avanzar(Edificio ed){
        ++this.recorrido; 
        
        
        if(this.recorrido >= this.distanciaDestino) {
            this.colocarLadrillo(ed);
        }
        
    }
    
}
