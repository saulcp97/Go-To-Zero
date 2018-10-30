package com.mygdx.game.Cubos.Entidades.bullet.Magia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Cardinal;
import com.mygdx.game.Cubos.Block;
import com.mygdx.game.Cubos.Entidades.bullet.Particulas;
import com.mygdx.game.Cubos.Entidades.bullet.Projectile;
import com.mygdx.game.Cubos.Entidades.vida.NPC.NPC;

import java.util.ArrayList;

public class Magia extends Projectile {

    /*  Tipos de Magia:
    *
    *       0 -> Alma
    *       1 -> Fuego
    *       2 -> Agua
    *       3 -> Rayo
    *       4 -> Tierra
    *       5 -> Aire
    */

    private float power;

    private byte type;
    private byte estate;
    private static Texture MagiaPrueba = new Texture(Gdx.files.internal("data/img/Particles/fireR.png"));
    private static Texture Magia = new Texture(Gdx.files.internal("data/img/Particles/fire.png"));
    private ArrayList<Particulas> particulas;
    public Magia(int x, int y, int z, byte dir, byte type) {
        super(x, y, z);
        this.type = type;
        this.setTexture(MagiaPrueba);
        this.setDrawSize(16,24);
        this.width = 16;
        this.height = 16;
        this.deep = 16;

        this.power = 10;

        switch (dir) {
            case 0:
                this.vel.y = 4;
                break;
            case 1:
                this.vel.x = 4;
                break;
            case 2:
                this.vel.y = -4;
                break;
            case 3:
                this.vel.x = -4;
                break;
        }
    }
    private byte stay;

    public Magia(NPC npc, byte type) {
        super((int)(npc.coord.x - Math.cos(Math.toRadians(npc.getAngle())) * 64), (int)(npc.coord.y - Math.sin(Math.toRadians(npc.getAngle())) * 64), (int)npc.coord.z + 64);
        this.type = type;
        this.setTexture((new TextureRegion(Magia,this.estate*16,0,16,24)).getTexture());
        //  this.sprite.setSize(16,24);
        this.width = 16;
        this.height = 16;
        this.deep = 16;

        this.power = 10;
        this.particulas = new ArrayList<Particulas>();
        this.despawnDist = 100;
        this.stay = 0;


       /*
       switch (npc.getDir()) {
            case 0:
                this.coord.y = npc.coord.y + npc.getHeight() + 4;
                this.vel.y = npc.getVeloY() + 4;
                break;
            case 1:
                this.coord.x = npc.coord.x + npc.getWidth() + 4;
                this.vel.x = npc.getVeloX() + 4;
                break;
            case 2:
                this.coord.y = npc.coord.y - 4 - this.height;
                this.vel.y = npc.getVeloY() - 4;
                break;
            case 3:
                this.coord.x = npc.coord.x - 4 - this.width;
                this.vel.x = npc.getVeloX() - 4;
                break;
        }
        */

        this.vel.set(-(float)Math.cos(Math.toRadians(npc.getAngle())) * 16,-(float)Math.sin(Math.toRadians(npc.getAngle())) * 16,0);
        this.force.x = -this.vel.x / 100;
        this.force.y = -this.vel.y / 100;
        this.force.z = -.001f;

    }

    public Magia() {
        super(0, 0, 0);
    }


    @Override
    public boolean colision(Block b) {
        System.out.println("Altura: " + this.coord.z);
        System.out.println("Cubo: " + (b.coord.x/64) +", " + (b.coord.y/64) + ", " + (b.coord.z/64));
        return this.coord.x <= b.coord.x + b.getWidth() && this.coord.x >= b.coord.x
                && this.coord.y <= b.coord.y + b.getHeight() && this.coord.y >=  b.coord.y
                && this.coord.z <=  b.coord.z + 64 && this.coord.z >=  b.coord.z;
    }

    public void nextEstate() {
        if(this.estate == 3){
            this.estate = 0;
        } else {
            ++this.estate;
        }
        this.setTexture((new TextureRegion(Magia,this.estate*16,0,16,24)).getTexture());
    }

    @Override
    public void phisics(Block[] input) {
        if(!destroy) {
            if (this.despawnDist != Integer.MAX_VALUE && despawnDist <= 0) {
                this.destroy = true;
            } else {
                if (despawnDist != Integer.MAX_VALUE) {
                    --despawnDist;
                }
                for (int i = ((int) this.coord.x / 64 + (31 - ((int) (this.coord.y + this.height) / 64)) * 32 + ((int) (this.coord.z - this.deep / 2) / 64) * 32 * 32); i < ((int) (this.coord.x + this.width) / 64 + (31 - ((int) (this.coord.y + this.height) / 64)) * 32 + ((int) (this.coord.z + this.deep) / 64) * 32 * 32); ++i) {
                    if (i >= 0 && i < input.length && input[i] != null && this.colision(input[i])) {//Corregir lo de la "i"
                        destroy = true;
                        break;
                    }
                }

                if (this.type == 1) {
                    for (Particulas a : this.particulas) {
                        a.phisics(input);
                    }

                    if(this.stay == 0) {
                        Particulas a = Particulas.particulasPool.obtain();
                        a.init((int) this.coord.x, (int) this.coord.y, (int) this.coord.z, (byte) 1);

                        Particulas b = Particulas.particulasPool.obtain();
                        b.init((int) this.coord.x, (int) this.coord.y, (int) this.coord.z, (byte) 1);

                        b.getForce().x = -a.getForce().x;
                        b.getForce().y = -a.getForce().y;

                        b.getVel().x = -a.getVel().x + this.vel.x / 16;
                        b.getVel().y = -a.getVel().y + this.vel.y / 16;

                        a.getVel().x += this.vel.x / 16;
                        a.getVel().y += this.vel.y / 16;

                        //.setVel(b.getVel().cpy().add(this.vel));

                        particulas.add(a);
                        particulas.add(b);
                        this.stay = 2;
                    } else {
                        --this.stay;
                    }

                    this.coord.add(this.vel);
                    this.vel.add(this.force);
                }
            }
        }
    }

    public void draw(Batch b) {
        b.draw((new TextureRegion(Magia,this.estate*16,0,16,24)), this.getXdraw(), this.getYdraw());
    }

    @Override
    public void dispose(){
        for(int i = 0; i < this.particulas.size();) {
            Particulas.particulasPool.free(this.particulas.remove(0));
        }

        this.mod = null;
    }

    @Override
    public void actual(int X, int Y) {
        this.setPosiD(X * (32 * 64) + this.coord.x, Y * (32 * 64) + this.coord.y + this.coord.z/2);
        if(this.mod != null) {
            this.mod.transform.set(this.coord.x/64 + X * 32 + .5f,.5f + this.coord.z/64,(31 - this.coord.y/64) + ((2 - Y) * 32) + .5f,0,0,0,0,2,2,2);
        }
        for (Particulas a : this.particulas) {
            if(a.is3DRenderable()) {
                //Trabajar en esto
                a.loadModel(1, 1);
            }
            a.actual(X, Y);
        }
    }

    @Override
    public void loadModel(int x, int y){
        Model sphere = Cardinal.particleSphere;
        this.mod = new ModelInstance(sphere);
        this.mod.transform.translate(this.coord.x/64 + x * 32 + .5f, .5f + this.coord.z/64,(31 - this.coord.y/64) + ((2 - y) * 32) + .5f);
        this.mod.materials.get(0).set(ColorAttribute.createDiffuse(0,1, 1,1));
    }

    public ArrayList<ModelInstance> getParticulasModel() {
        ArrayList<ModelInstance> res = new ArrayList<ModelInstance>(particulas.size());
        for (Particulas p: particulas) {
            res.add(p.getModelInstance());
        }
        return res;
    }

    public void render(ModelBatch b, Environment environment) {
        b.render(this.mod, environment);
        for (Particulas a : this.particulas) {
            if(!a.is3DRenderable()) {
                a.render(b, environment);
            }
            a.render(b, environment);
        }
    }

    @Override
    public void reset() {
        this.dispose();
        this.coord.x = 0;
        this.coord.y = 0;
        this.coord.z = 0;
        this.type = 0;
        this.vel = new Vector3(0,0,0);
        this.force = new Vector3(0, 0, 0);
        this.resistence = 1;
        this.despawnDist = Integer.MAX_VALUE;
        this.destroy = false;
    }

}