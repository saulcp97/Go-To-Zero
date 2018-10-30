package com.mygdx.game.Cubos.Entidades.bullet;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Cardinal;
import com.mygdx.game.Cubos.Block;
import com.mygdx.game.Cubos.Entidades.Entity;

public class Projectile extends Entity implements Pool.Poolable {

    protected byte resistence;
    protected int despawnDist;

    public boolean destroy = false;

    protected Projectile(int x, int y, int z) {
        super(x, y, z);
        this.vel = new Vector3(0, 0, 0);
        this.force = new Vector3(0, 0, 0);
        this.resistence = 1;
        this.despawnDist = Integer.MAX_VALUE;

        this.destroy = false;
        this.width = 16;
        this.height = 16;
        this.deep = 16;
    }

    public Projectile(){
        this(0,0,0);
    }

    public void phisics(Block[] input) {
        if(!destroy) {
            if (this.despawnDist != Integer.MAX_VALUE && despawnDist <= 0) {
                this.destroy = true;
            } else if (despawnDist != Integer.MAX_VALUE) {
                --despawnDist;
                for(int i = ((int)this.coord.x / 64 + (31 - ((int)(this.coord.y + this.height) / 64)) * 32 + ((int)(this.coord.z - this.deep/2) / 64) * 32 * 32); i < ((int)(this.coord.x + this.width) / 64 + (31 - ((int)(this.coord.y + this.height)/ 64)) * 32 + ((int)(this.coord.z + this.deep) / 64) * 32 * 32 ); ++i) {
                    if (i >= 0 && i < input.length && input[i] != null && this.colision(input[i])) {//Corregir lo de la "i"
                        destroy = true;
                        break;
                    }
                }
                /*for (int i = 0; i < input.length; ++i) {
                    if (input[i] != null && this.colision(input[i])) {
                        destroy = true;
                        break;
                    }
                }*/
                this.coord.add(this.vel);
                this.vel.add(this.force);
            }
        }
    }

    @Override
    public boolean colision(Block b) {
        //System.out.println("sice: " + this.width + " ," + this.height + " ," + this.deep);
        return this.coord.x - this.width/2 < b.coord.x + b.getWidth() && this.coord.x  + this.width/2 > b.coord.x
                && this.coord.y - this.height/2 < b.coord.y + b.getHeight() && this.coord.y + this.height/2 >  b.coord.y
                && this.coord.z - this.deep/2 <  b.coord.z + b.getDeep() && this.coord.z + this.deep/2 >  b.coord.z;
    }

    public void Choca() {
        this.destroy = true;
    }

    public void setForce(Vector3 force) { this.force = force; }
    public void setVel(Vector3 vel) { this.vel = vel; }
    public Vector3 getForce() { return this.force; }
    public Vector3 getVel() { return this.vel; }
    public void setDespawnDist(int despawnDist) { this.despawnDist = despawnDist; }

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

    @Override
    public void loadModel(int x, int y){
        /*
        Model Plane = new ModelBuilder().createBox(.25f, .25f, .25f,
                new Material(TextureAttribute.createDiffuse(new Texture(Gdx.files.internal("data/img/3dTexture/muroSimple.png")))),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates
        );
*/
        Model sphere = Cardinal.sphere;
/*
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        MeshPartBuilder meshBuilder;

        //meshBuilder = modelBuilder.part("part1", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal, new Material());
        //meshBuilder.cone(1, 1, 1, 10);
        //Node node = modelBuilder.node();
        //node.translation.set(0,1,0);
        //meshBuilder = modelBuilder.part("part2", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal, new Material());
        //meshBuilder.sphere(1, 1, 1, 10, 10);
        Material simple =  new Material(TextureAttribute.createDiffuse(new Texture(Gdx.files.internal("data/img/3dTexture/baseMuroMaidCaffeExterior.png"))));
        meshBuilder = modelBuilder.part("Cabeza", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates, simple);
        meshBuilder.rect(0,0,1, 0,1,1, 0,1,0, 0,0,0, -1,0,0);
        meshBuilder.rect(1,0,1, 1,1,1, 0,1,1, 0,0,1, 0,0,1);
        meshBuilder.rect(1,0,0, 1,1,0, 1,1,1, 1,0,1, 1,0,0);
        meshBuilder.rect(0,0,0, 0,1,0, 1,1,0, 1,0,0, 0,0,-1);
        //Up
        meshBuilder.rect(1,1,1, 1,1,0, 0,1,0, 0,1,1, 0,1,0);
        //Down
        meshBuilder.rect(1,0,0, 1,0,1, 0,0,1, 0,0,0, 0,-1,0);

        Model model = modelBuilder.end();
*/
        this.mod = new ModelInstance(sphere);
        //ColorAttribute attr = ColorAttribute.createDiffuse(.9f, .1f,.1f, 1);
        //mod.materials.get(0).set(attr);
        //mod.materials.get(0).set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
        this.mod.transform.translate(this.coord.x/64 + x * 32 + .5f, .5f + this.coord.z/64,(31 - this.coord.y/64) + ((2 - y) * 32) + .5f);
        this.mod.transform.scale(2,2,2);
    }

    public void init(int x, int y, int z) {
        super.coord.x = x;
        super.coord.y = y;
        super.coord.z = z;
        this.destroy = false;
        this.type = 0;
    }

    @Override
    public void reset() {
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