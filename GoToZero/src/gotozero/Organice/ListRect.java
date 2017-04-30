/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gotozero.Organice;

import gotozero.Rect;
/**
 *
 * @author saulc
 */
public class ListRect implements gotozero.Organice.ListaEnCola{

    private Nodo Primero,Marcador,Ultimo;
    private int length,disponibles;
    
    public ListRect() {
        this.length = 0;
        this.disponibles = 0;
    }
    
    public void resetMarcador(){
        this.Marcador = Primero;
    }
    public void CarryMarcador(){
        this.Marcador = this.Marcador.getSig();
    }
    
    public gotozero.Rect getCarryData(){
        return (Rect) this.Marcador.getData();
    }

    @Override
    public void add(Object i) {
        if(this.length == 0) {
            if(this.disponibles == 0) {
                this.Primero = new Nodo( ((gotozero.Rect)i).clone(), null);
                this.Ultimo = this.Primero;
                ++this.disponibles;
            } else {
                this.Primero.setData((Actualizable) i);    
            }
        } else {
            // PRIMERO CASO BASE
            if(this.disponibles == this.length){
                this.Ultimo.setSig(new Nodo(((gotozero.Rect)i).clone(), null));
                this.Ultimo  = this.Ultimo.getSig();
                ++this.disponibles;
            } else {
                this.Ultimo = this.Ultimo.getSig();
                this.Ultimo.setData((Actualizable) i);
            }
        }
        
        ++this.length;
    }
    
    
    @Override
    public String toString(){
        String res = "";
        this.Marcador = this.Primero;
        int count = 0;
        while(count < this.length){
            res += this.Marcador.getData().toString() + "-";
            this.Marcador = this.Marcador.getSig();
            ++count;
        }
        return res;
    }
    
    @Override
    public void vaciarLista() {
        this.length = 0;
        this.Ultimo = this.Primero;
    }

    @Override
    public Object getElementIndex(int i) {
        if(i == this.length - 1){ return this.Ultimo;}
        this.Marcador = this.Primero;
        int c = 0;
        while(c < i && c < this.length){
            this.Marcador =  this.Marcador.getSig();
            ++c;
        }
        return this.Marcador;
    }

    @Override
    public boolean listaVacia() {
        return this.length == 0;
    }
    
    public gotozero.Rect[] toArrayRect(){
        gotozero.Rect[] aux = new gotozero.Rect[this.length];
        this.Marcador = this.Primero;
        int c = 0;
        while(c < this.length) {
            aux[c] = (gotozero.Rect) this.Marcador.getData();
            this.Marcador = this.Marcador.getSig();
            ++c;
        }
        
        return aux;
    }
 
    public int getLength() {
        return this.length;
    }
}
