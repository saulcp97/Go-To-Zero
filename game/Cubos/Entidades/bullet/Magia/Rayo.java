package com.mygdx.game.Cubos.Entidades.bullet.Magia;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Cardinal;
import com.mygdx.game.Cubos.Block;
import com.mygdx.game.Cubos.Entidades.vida.NPC.NPC;

public class Rayo extends Magia{

    private Vector3 end;
    //End es relativo a la coordenada principal, sirve además para construir el modelo.
    public Rayo(){
        super();
        this.end = new Vector3(0,0,0);
    }

    public Rayo(NPC npc) {
        super(npc,(byte)3);
        this.end = new Vector3(9.0f,4.0f,5.0f);
        this.despawnDist = 1;
    }

    @Override
    //De momento intacto
    public void phisics(Block[] input) {
        if(!destroy) {
            if (this.despawnDist != Integer.MAX_VALUE && despawnDist <= 0) {
                this.destroy = true;
            } else if (despawnDist != Integer.MAX_VALUE) {
                --despawnDist;
            }
        }
    }

    @Override
    public boolean colision(Block b) {
        System.out.println("Altura: " + this.coord.z);
        System.out.println("Cubo: " + (b.coord.x/64) +", " + (b.coord.y/64) + ", " + (b.coord.z/64));
        return this.coord.x <= b.coord.x + b.getWidth() && this.coord.x >= b.coord.x
                && this.coord.y <= b.coord.y + b.getHeight() && this.coord.y >=  b.coord.y
                && this.coord.z <=  b.coord.z + 64 && this.coord.z >=  b.coord.z;
    }

    @Override
    public void loadModel(int x, int y){
        this.mod = new ModelInstance(Cardinal.getProcLightning(10,this.end,this.width));
        this.mod.transform.translate(this.coord.x/64 + x * 32 + .5f, .5f + this.coord.z/64,(31 - this.coord.y/64) + ((2 - y) * 32) + .5f);
    }


    @Override
    public void render(ModelBatch b, Environment environment){
        b.render(this.mod,environment);
    }

    @Override
    public void actual(int X, int Y) {
        this.setPosiD(X * (32 * 64) + this.coord.x, Y * (32 * 64) + this.coord.y + this.coord.z/2);

        if(this.mod != null) {
            /*Vector3 pos = new Vector3(this.coord.x/64 + X * 32 + .5f,.5f + this.coord.z/64,(31 - this.coord.y/64) + ((2 - Y) * 32) + .5f);
            Quaternion quaternion = new Quaternion(0,0,0,0);
            this.mod.transform.set(pos,quaternion);*/
            this.mod.transform.set(this.coord.x/64 + X * 32 + .5f,.5f + this.coord.z/64,(31 - this.coord.y/64) + ((2 - Y) * 32) + .5f,0,0,0,0,2,2,2);
        }
    }
}
