package com.mygdx.game.Cubos;

public class Interactuable extends Block {

    public static Interactuable Puerta = new Interactuable(0,0,0,Byte.MIN_VALUE,1,1,2);//Codigo Puerta 101

    public Interactuable(int x, int y, int z, byte t, int width, int height, int deep) {
        super(x, y, z, t);
        this.width = width;
        this.height = height;
        this.deep = deep;
    }

    public void interact(int CodeInput){
        switch (CodeInput) {
            case 0: //Actuar en 'E'

                break;
        }
    }
}
