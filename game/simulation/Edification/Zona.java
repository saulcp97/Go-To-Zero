package com.mygdx.game.simulation.Edification;

import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Cubos.Entidades.vida.NPC.NPC;

public class Zona {
    public Vector3 coord;
    public int width, height, deep; //caja de colision y dibujado en enteros

    final House linked;

    public Zona(House house,int x, int y, int z) {//En bloques
        this.linked = house;
        this.coord = new Vector3(x,31 - (y + this.linked.blocksH - 1),z);

        this.width = this.linked.blocksW;
        this.height = this.linked.blocksH;
        this.deep = 4;
    }

    public boolean playerIn(NPC player) {
        return player.colision(coord.x*64,this.coord.y*64,coord.z*64,this.width*64,this.height*64,this.deep*64);
    }
}