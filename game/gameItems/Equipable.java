package com.mygdx.game.gameItems;

import com.mygdx.game.simulation.RPG;

public class Equipable extends Items {

    private final byte Type;
    private RPG estadistics;
    public Equipable(String nombre){
        super(nombre);
        this.Type = 0;
        this.estadistics = new RPG();
        this.estadistics.setI(1);
    }

    public byte getType() { return Type; }
    public RPG getEstadistics() { return estadistics; }
}