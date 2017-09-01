package gotozero.algoritmos;


/**
 * Write a description of class cam here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Exponencial {

    public static int exponential(int N, int E){
        int res = 1;
        for(int i = 0; i < E; ++i) {
            res *= N;
        }
        return res;
    }
    
}
