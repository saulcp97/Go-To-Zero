package com.mygdx.game.Cubos.Entidades.vida;

import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Chunk;
import com.mygdx.game.Cubos.Entidades.Entity;

import java.util.ArrayList;

public class Life extends Entity {

    protected int maxLife;
    protected float actualLife;

    protected byte especie;
    protected byte raza;
    protected int count;
    protected Interaction Intento = null;//normalmente sera null

    public Life(int x, int y, int z, byte t) {
        super(x, y, z);
        this.count = 0;
        switch (t) {
            case 0:
                this.maxLife = 100;
                break;
            case 100:
                this.maxLife = 1000;
                break;
        }
        this.actualLife = maxLife;
    }

    public void move(float x, float y, float z){
        this.coord.x += x;
        this.coord.y += y;
        this.coord.z += z;
    }

    public void teleport(float x, float y, float z) {
        this.coord.x = x;
        this.coord.y = y;
        this.coord.z = z;
    }

    public void Soul(Chunk ch, ArrayList<Entity> entidadesOut, int Index) {}

    public float getActualLife() { return actualLife; }

    protected Vector3 apuntar(Entity e) {
        if(e == null) {
            return new Vector3(0,0,1);
        }
        Vector3 res = e.coord.cpy();
        res.mulAdd(this.coord, -1f);

        float sum = Math.abs(res.x) + Math.abs(res.y) + Math.abs(res.z);
        res.x /= sum;
        res.y /= sum;
        res.z /= sum;

        return res;
    }
}