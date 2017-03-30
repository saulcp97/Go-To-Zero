/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gotozero.Data;

/**
 *
 * @author Saúl Cerdá Peris
 * @param <N>
 */
 
public class List <N> {
    
    public Nodo first;
    public int length;
    private int disp;
    
    private Nodo Marcador;
    
    /**
     * 
     * Constructor de la clase List, crea la lista vacia
     * 
     */
    
    public List() {
        this.first = null;
        this.length = 0;
        this.disp = 0;
        this.Marcador = null;
    }
    
    /**
     * 
     * Añade una variable a la lista, creando un nuevo nodo o modificando uno antiguo.
     * 
     * @param variable Variable que utiliza para construir un nodo que la contenga, o si ya existe un nodo, cambiar su valor.
     */
    
    public void add(N variable) {
        if(this.length == 0) {
            if(this.first == null) {
                this.first = new Nodo(variable);
                ++this.disp;
            } else {
                this.first.setData(variable);
            }
        } else {
            Nodo aux = this.first;
            
            for (int i = 0; i < this.length - 1; ++i){
                aux = aux.getNext();
            }
            
            if(this.disp == this.length) {
                aux.setNext(new Nodo(variable));
                ++this.disp;
            } else {
                aux.getNext().setData(variable);
            }
            
        }
        
        
        ++this.length;
    }
    
    /**
     * Añade el valor de "variable" en un la posicion "in" de la lista, siempre que esta pertenezca al rango entre 0 y List.length.
     * @param in posicion en la que crea el nuevo nodo con la "variable" dada.
     * @param variable valor a añadir en la posición indicada.
     */
    
    public void addPos(int in,N variable){
        if(in < 0 || in > this.length){
            System.out.println("Fuera De Rango de admision: " + in);
            return;
        }
        int count = 0;
        Nodo aux = this.first;
        
        while(count < in){
            aux = aux.getNext();
            ++count;
        }
        N trans = (N) aux.getData();
        N trans2;
        aux.setData(variable);
        Nodo aux2 = aux.getNext();
        while(count < this.length - 1){
            trans2 = (N) aux2.getData();
            aux2.setData(trans);
            if(aux2.getNext() != null){
                aux2 = aux2.getNext();
            }
            trans = trans2;
            count++;
        }
        if(this.length == this.disp){
            this.disp++;
            aux2.setNext(new Nodo(trans));
        }
        
        this.length++;
    }
    
    /**
     * Vuelve el Marcador o "Mark" a la primera posición
     */
    public void resetMark() {
        this.Marcador = this.first;
    }
    
    /**
     * Desplaza el Marcador o "Mark" a la posición siguiente.
     */
    public void carryMark() {
        if(this.Marcador.getNext()!= null){
            this.Marcador = this.Marcador.getNext();
        }
    }
    
    
    /**
     * Obtiene el valor en la posición del Marcador o "Mark".
     * @return Valor de data en el Marcador;
     */
    public N getCarryData() {
        return (N) this.Marcador.getData();
    }
    
    /**
     * Modifica el valor del nodo Marcador o "Mark" por el de la variable dada.
     * @param variable Valor por el que se sustituye el del Marcador.
     */
    public void setCarryData(N variable) {
        this.Marcador.setData(variable);
    }
    
    /**
     * Elimina la información del nodo indicado, reduciendo List.length.
     * @param i posición del nodo a borrar.
     */
    public void eliminate(int i){
        if(i >= 0 && i < this.length){
            Nodo aux = this.first;
            for(int j = 0; j < i; ++j) {
                aux = aux.getNext();
            }
            
            N trans = (N) aux.getNext().getData();
            for(int j = i; j < this.length-1; ++j) {
                aux.setData(trans);
                           
                aux = aux.getNext();
                if(aux.getNext() != null){
                    

                    trans = (N) aux.getNext().getData();
                }
            }
            aux.setData(trans);
            if(aux.getNext() != null){
                aux.getNext().setData(null);
            }
            --this.length;
        }
    }
    /**
     * Elimina el nodo indicado, reduciendo List.length y List.disp.
     * @param i Nodo a eliminar.
     */
    public void remove (int i){
        Nodo aux = this.first;
        for(int j = 0; j < i - 1; j++) {
            aux = aux.getNext();
        }
        
        Nodo aux2 = aux.getNext();
        aux.setNext(aux2);
        
        this.length--;
        this.disp--;
    }
    
    public N getDataByIndex(int i) {
        Nodo aux = this.first;
        int j = 0;
        
        while(j < i){
            aux = aux.getNext();
            j++;
        }
       
        return (N) aux.getData();
    }
    
    public boolean listaVacia() {
        return this.length == 0;
    }
    
    public void vaciarLista() {
        this.length = 0;    
    }
    
    public N getDataLast() {
        Nodo aux = this.first;
        int j = 1;
        while(j < this.length){
            aux = aux.getNext();
            ++j;
        }
        return (N) aux.getData();
    }
    
    public void print(){
        Nodo aux = this.first;
        StringBuffer res = new StringBuffer();
        int j = 0;
        while(j < this.length){
            res.append(aux.getData()).append(" ");
            aux = aux.getNext();
            ++j;
        }
        System.out.println(this.disp + "|-|" +this.length);
        
        System.out.println(res);
        System.out.println(this.first.getData());
    }
    
}
