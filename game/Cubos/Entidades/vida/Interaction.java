package com.mygdx.game.Cubos.Entidades.vida;

public class Interaction {
    private int code;
    private int dist;
    private int width;
    private int dir;
    private boolean cone;
    private Life interactuante;

    public Interaction(int c, Life from, int dir, int dist, int width, boolean cone) {
        this.code = c;
        this.interactuante = from;
        this.dir = dist;
        this.dist = dist;
        this.width = width;
        this.cone = cone;
    }



}
