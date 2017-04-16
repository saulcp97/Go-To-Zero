/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OrdenacionRect;

import gotozero.Block;

/**
 *
 * @author saulc
 */
public class OrdenBlock {
    private static boolean ordenadoBlock = false;
    
    
/*
    public static void main(String[] args) {
        Block b1 = new Block(0, 0, 01,(byte)1);
        Block b2 = new Block(0, 01, 0,(byte)1);
        Block[] ant = new Block[]{b1,b2};

        System.out.println(b1.compareTo(b2));
    }
*/
    public static void changedBlock(){
        ordenadoBlock = false;
    }
    
    
    public static void quicksort(Block A[]){
        if(!ordenadoBlock){
            quickSort(A, 0, A.length - 1);
            ordenadoBlock = true;
        }
    }
    
    private static <T extends Comparable<T>> void quickSort(T[] a, int izq,
                                                            int der) {
        if (izq < der) {
            T pivot = mediana3(a, izq, der);
            int i = izq;
            int j = der - 1;
            for ( ; i < j;) {
                while (pivot.compareTo(a[++i]) > 0){}
                while (pivot.compareTo(a[--j]) < 0){}
                intercambiar(a, i, j);
            }
            intercambiar(a, i, j);        // Deshacer el ultimo cambio
            intercambiar(a, i, der - 1);  // Restaurar el pivote
            quickSort(a, izq, i - 1);     // Ordenar recursivamente los menores
            quickSort(a, i + 1, der);     // Ordenar recursivamente los mayores
        }
    }

    // Metodo para intercambiar dos elementos de un array
    private static <T> void intercambiar(T[] a, int ind1, int ind2) {
        T tmp = a[ind1];
        a[ind1] = a[ind2];
        a[ind2] = tmp;
    }
    
    private static <T extends Comparable<T>> T mediana3(T[] a, int izq,
                                                        int der) {
        int c = (izq + der) / 2;
        if (a[c].compareTo(a[izq]) < 0)   { intercambiar(a, izq, c);   }
        if (a[der].compareTo(a[izq]) < 0) { intercambiar(a, izq, der); }
        if (a[der].compareTo(a[c]) < 0)   { intercambiar(a, c, der);   }
        // ocultar el pivote en la posicion der-1
        intercambiar(a, c, der - 1);
        return a[der - 1];
    }  
}
