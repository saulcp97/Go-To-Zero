package com.mygdx.game.gameItems;

public class Alimentos extends Items {
    private final byte[] valores;
    
    public Alimentos(String nombre) {
        super(nombre);
        valores = new byte[]{0,0,0,0,0,0,0};
    }
}
