package com.mygdx.game.Cubos.Entidades.bullet;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Cardinal;
import com.mygdx.game.Cubos.Block;

public class Particulas extends Projectile {
    private Vector3 end; //Para particulas de rayo

    public static final Pool<Particulas> particulasPool = new Pool<Particulas>() {
        @Override
        protected Particulas newObject() {
            return new Particulas((byte)0);
        }
    };


    public Particulas(byte b) {
        super();
        switch(b) {
            case 0://Electricidad
                this.despawnDist = 5;
                this.end = new Vector3(8 - MathUtils.random(16f), 32, 8 - MathUtils.random(16f));
                this.type = b;  //El tipo en el objeto de particula sirve para saber de que tipo de particula se trata, lo que sirve
                //para determinar su comportamiento y modelos.
                break;
            case 1://Fuego
                this.despawnDist = 120;
                float variations = 1f;
                this.end = new Vector3( MathUtils.random(-variations, variations),  MathUtils.random(-variations, variations), 1f);
                this.vel.add(end);

                float red = 2;
                this.vel.z /= red;
                this.vel.x /= Math.log(this.despawnDist);
                this.vel.y /= Math.log(this.despawnDist);
                this.type = b;

                this.force.x = - this.vel.x / (this.despawnDist / 1.75f);
                this.force.y = - this.vel.y / (this.despawnDist / 1.75f);

                break;
        }
    }

    @Override
    public void phisics(Block[] input) {
        if(!destroy) {
            if (this.despawnDist != Integer.MAX_VALUE && despawnDist <= 0) {
                this.destroy = true;
            } else if (despawnDist != Integer.MAX_VALUE) {
                this.coord.add(this.vel);
                this.vel.add(this.force);
                --despawnDist;
                if(this.mod != null) {
                    //this.mod.materials.get(0).set(ColorAttribute.createDiffuse(1,1f - ((float) this.despawnDist) / 120f,0,((float) this.despawnDist) / 240f));
                    if(this.despawnDist < 120) {
                        this.mod.materials.get(0).set(Cardinal.fireMaterial[this.despawnDist]);
                        this.mod.transform.scale(1,1,1);
                    }
                }
            }
        }
    }

    @Override
    public boolean colision(Block b) {
        return false;
    }

    @Override
    public void render(ModelBatch b, Environment environment){
        b.render(this.mod,environment);
    }

    @Override
    public void actual(int X, int Y) {
        this.setPosiD(X * (32 * 64) + this.coord.x, Y * (32 * 64) + this.coord.y + this.coord.z/2);

        if(this.mod != null) {
            this.mod.transform.set(this.coord.x/64 + X * 32 + .5f,.5f + this.coord.z/64,(31 - this.coord.y/64) + ((2 - Y) * 32) + .5f,0,0,0,0,2 * (((float)this.despawnDist + 1) / 120),2 * (((float)this.despawnDist + 1) / 120),2 * (((float)this.despawnDist + 1) / 120));
            //2 * (((float)this.despawnDist + 1) / 120)
            // 1 <- 120
            // 0.05 <- 1
        }
    }

    @Override
    public void loadModel(int x, int y){
        switch(this.type) {
            case 0:
                this.mod = new ModelInstance(Cardinal.getProcLightning(10, this.end, this.width));
                this.mod.transform.translate(this.coord.x / 64 + x * 32 + .5f, .5f + this.coord.z / 64, (31 - this.coord.y / 64) + ((2 - y) * 32) + .5f);
                Cardinal.thunderSound.play(5f, 1.25f, 2);
                break;
            case 1:
                this.mod = new ModelInstance(Cardinal.particleSphere);
                this.mod.transform.translate(this.coord.x / 64 + x * 32 + .5f, .5f + this.coord.z / 64, (31 - this.coord.y / 64) + ((2 - y) * 32) + .5f);
                break;
        }
    }

    public void init(int x, int y, int z, byte b) {
        super.coord.x = x;
        super.coord.y = y;
        super.coord.z = z;
        this.destroy = false;
        this.type = b;

        switch (b) {
            case 1://Fuego
                this.despawnDist = 120;
                float variations = 1f;
                this.end = new Vector3( MathUtils.random(-variations, variations),  MathUtils.random(-variations, variations), 1f);
                this.vel.add(end);

                float red = 2;
                this.vel.z /= red;
                this.vel.x /= Math.log(this.despawnDist);
                this.vel.y /= Math.log(this.despawnDist);
                this.type = b;

                this.force.x = - this.vel.x / (this.despawnDist / 1.75f);
                this.force.y = - this.vel.y / (this.despawnDist / 1.75f);

                break;
        }
    }

    public void reset(byte b) {
        this.coord.x = 0;
        this.coord.y = 0;
        this.coord.z = 0;
        this.type = 0;
        this.vel.x = 0;
        this.vel.y = 0;
        this.vel.z = 0;

        this.force.x = 0;
        this.force.y = 0;
        this.force.z = 0;

        this.resistence = 1;
        this.despawnDist = Integer.MAX_VALUE;
        this.destroy = false;


        switch (b) {
            case 1://Fuego
                this.despawnDist = 120;
                float variations = 1f;
                this.end = new Vector3( MathUtils.random(-variations, variations),  MathUtils.random(-variations, variations), 1f);
                this.vel.add(end);

                float red = 2;
                this.vel.z /= red;
                this.vel.x /= Math.log(this.despawnDist);
                this.vel.y /= Math.log(this.despawnDist);
                this.type = b;

                this.force.x = - this.vel.x / (this.despawnDist / 1.75f);
                this.force.y = - this.vel.y / (this.despawnDist / 1.75f);

                break;
        }




    }




}
