package com.mygdx.game.Cubos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Cubos.Entidades.Entity;
import com.mygdx.game.Cubos.Entidades.vida.NPC.NPC;

public class Decoration extends Block {
    public static Texture piedrasSuelo = new Texture(Gdx.files.internal("data/img/pierdrasSuelo.png"));

    //Codigo decoraciones
    //Default Puerta
    //1: Antorcha
    //3: Arboles
    //8: Barril

    //101: casas aldeanos

    public Decoration(int x, int y, int z) {
        super(x, y, z, Byte.MIN_VALUE);
        this.setTexture(piedrasSuelo);
        this.orient = -1;
    }

    public Decoration() {
        super(0,0,0,(byte)10);
        this.setTexture(piedrasSuelo);
        this.orient = -1;
    }

    @Override
    public boolean rectify(Block b, byte dir) {
        return false;
    }

    @Override
    public boolean rectify(Entity ent) { return false; }

    @Override
    public boolean rectify(NPC ent, int dist, boolean a) { return false; }

    public void build3DPart(int x, int y, int z) { //Podriamos añadir variaciones para que un mismo bloque tenga variantes
        super.b3D = new p3D('D', this.type, this.orient, x, y , z);
    }
}
