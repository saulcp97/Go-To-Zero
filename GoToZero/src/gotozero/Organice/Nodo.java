/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gotozero.Organice;

/**
 *
 * @author saulc
 * @param <N> Comparar la organizacion
 */
public class Nodo <N extends Actualizable>{
    
    private N data;
    private Nodo sig;
    
    public Nodo(N d, Nodo s){
        this.data = d;
        this.sig = s;
    }

    public void setData(N d){
        this.data.actualizar(d);
    }
    
    public void setSig(Nodo s){
        this.sig = s;
    }
    
    public Nodo getSig(){
        return this.sig;
    }
    
    public N getData() {
        return this.data;
    }
}
