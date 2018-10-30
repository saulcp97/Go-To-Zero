package com.mygdx.game.Cubos.Entidades.vida.NPC.Trabajador;

import com.mygdx.game.Cubos.Entidades.vida.NPC.NPC;

public class Trabajador extends NPC {

    private String profesion;
    private short horaEntrada;
    private short horaSalida;


    private byte comunicacion;
    private byte habilidad;

    private final byte Excentricidad;
    private int sueldo;


    public Trabajador(){
        this(0,0,0,"Camarera");
    }

    public Trabajador(int x, int y, int z, String p) {
        super(x, y, z, "Waifu");
        this.profesion = p;
        this.horaEntrada = 800;
        this.horaSalida = 1900;

        this.Excentricidad = (byte)Math.round(Math.random() * 10);
        this.comunicacion = (byte)Math.round(Math.random() * 10);
        this.habilidad = (byte)Math.round(Math.random() * 10);

        this.calcularSueldo();
    }


    private void calcularSueldo() {
        this.sueldo = (int)(2.5 * (this.comunicacion + this.habilidad + 1) * (1 + Math.sqrt(this.Excentricidad)));
    }
}
