package gotozero.algoritmos;

import gotozero.v3;

/**
 * Created by saulc on 28/06/2017.
 */
public class Accion implements Comparable<Accion> {
    private int HoraInicio;
    private int duracion;
    private v3 Posicion;
    private String name;

    public Accion (int HoraInit, int durada, v3 Pos){
        this.HoraInicio = HoraInit;
        this.duracion = durada;
        this.Posicion = Pos;
    }

    public int compareTo(Accion other) {
        return this.HoraInicio - other.HoraInicio;
    }

    public void doAction() {



    }



}
