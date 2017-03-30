/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gotozero.Organice;
import gotozero.*;
/**978119652
 *975101835
 *
 *990074867 
 *984258114
 * 1043612643 
 * 
 * Despues de utilizar Rect
 * 1083729457
 * 
 * Actualizacion a casi final
 * 1097384492
 * (sin el println)
 * 1072555915
 * 
 * Sin optimizar la posicion
 * 1160563558
 * 
 * Optimizando la posicion
 * 1101857236
 * 
 * @author saulc
 */
public class Main {
    public static void main(String[] args) {
        long ini = System.nanoTime();
        ListRect a = new ListRect();
        gotozero.Rect auxiliar1 = new gotozero.Rect(0, 0, 10, 10, 0);
        
        for(int i = 0; i < 10000; ++i) {
            auxiliar1.setPos(i,i*2);
            a.add(auxiliar1.clone());
        }
        a.vaciarLista();
        
        for(int i = 0; i < 100; ++i){
            auxiliar1.setPos(i*2, i*2);
            a.add(auxiliar1);
        }
        System.out.println(a.toString());
        long fin = System.nanoTime();
        
        gotozero.Rect[] ic = new gotozero.Rect[0];
        for(int i = 0; i < 10000; ++i) {
            gotozero.Rect[] aux = new gotozero.Rect[ic.length+1];
            for(int j = 0; j < ic.length; ++j){
                aux[j] = ic[j];
            }
            aux[ic.length] = new gotozero.Rect(i,i*2,10,10,0);
            ic = aux;
        }
        
        gotozero.Rect[] asi = new gotozero.Rect[0];
        for(int i = 0; i < 100; ++i) {
            gotozero.Rect[] aux = new gotozero.Rect[asi.length+1];
            for(int j = 0; j < asi.length; ++j){
                aux[j] = asi[j];
            }
            aux[asi.length] = new gotozero.Rect(i*3,i*2,10,10,0);
            asi = aux;
        }
        
        for(int i = 0; i < asi.length; ++i) {
            System.out.println(" " + asi[i]);
        }
        
        
        long fin2 = System.nanoTime();
        System.out.println("Tiempo empleado     = " + (fin - ini));
        System.out.println("Tiempo empleado 2   = " + (fin2 - fin));

    }
}
