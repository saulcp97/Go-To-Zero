/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gotozero.Data;

/**
 *
 * @author saulc
 * @param <N>
 */
public class Nodo <N> {
    
    private Nodo next;
    private N DATA;
    public int priority;
    
    public Nodo(N inf) {
        this.next = null;
        this.DATA = inf;
        this.priority = -1;
    }
    
    public N getData() {
        return this.DATA;  
    }
    
    public Nodo getNext(){
        return this.next; 
    }
    
    public void setData(N data) {
        this.DATA = data;
    }
    
    public void setNext(Nodo b){
        this.next = b;
    }
    
}
