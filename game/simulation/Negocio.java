package com.mygdx.game.simulation;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Cubos.Entidades.vida.NPC.Trabajador.Trabajador;

public class Negocio {
    private short Hour;
    private String name;

    private Trabajador[] empleados;
    private Trabajador[] listaContrataciones;
    public int Presupuesto;

    public Negocio(){
        this.Hour = 800;
        this.name = "Mimi's Coffe";
        this.empleados = new Trabajador[] {new Trabajador(64,64,64,"Ayudante De Gerencia")};
        this.listaContrataciones = new Trabajador[] {new Trabajador(), new Trabajador(), new Trabajador(), new Trabajador(), new Trabajador()};
        this.Presupuesto = 1000;
    }

    public Texture[] getEmpleadosFace(){
        Texture[] res = new Texture[this.empleados.length];
        for(int i = 0; i < this.empleados.length; ++i) {
            res[i] = this.empleados[i].getFaceSet();
        }
        return res;
    }

    public Texture[] getCandidateFace(){
        Texture[] res = new Texture[this.listaContrataciones.length];
        for(int i = 0; i < this.listaContrataciones.length; ++i) {
            res[i] = this.listaContrataciones[i].getFaceSet();
        }
        return res;
    }

    public void contratar (Trabajador nuevo) {
        Trabajador[] arr = new Trabajador[this.empleados.length + 1];
        System.arraycopy(this.empleados, 0, arr, 0, this.empleados.length);
        arr[this.empleados.length] = nuevo;
        this.empleados = arr;
    }

}
