package com.mygdx.game.Cubos;

public class Props extends Block {

    public Props(){ //Tamaño por calibrar
        //Casa 6, 4, 4
        super(0,0,0,(byte)10);
        this.width = 6 * 64;
        this.height = 4 * 64;
        this.deep = 4 * 64;
    }

    public void build3DPart(int x, int y, int z) { //Podriamos añadir variaciones para que un mismo bloque tenga variantes
        super.b3D = new p3D('P', this.type, this.orient, x, y , z);
    }
}
