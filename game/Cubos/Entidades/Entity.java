package com.mygdx.game.Cubos.Entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Cubos.Block;

public class Entity extends Block {
    private static Model model;

    protected Vector3 vel;
    protected Vector3 force;
    protected byte dir;
    public boolean visible;
    public boolean tangible;
    public boolean freeMove;
    protected ModelInstance mod;

    public Entity(int x, int y, int z) {
        super(x, y, z, Byte.MIN_VALUE);
        this.visible = true;
        this.tangible = true;
        this.vel = new Vector3(0,0,0);
        this.force = new Vector3(0,0,-.5f);
        this.dir = (byte)0;
        this.freeMove = true;
    }

    public void accelerate(){
        this.vel.add(force);
        this.coord.add(this.vel);
    }

    public void resetVelo() {
        this.vel.z = 0;
    }

    public float getVeloZ() { return this.vel.z; }
    public float getVeloY() { return this.vel.y; }
    public float getVeloX() { return this.vel.x; }

    public void actual(int X, int Y) {
        this.setPosiD(X * (32 * 64) + this.coord.x - this.width/4, Y * (32 * 64) + this.coord.y + this.coord.z/2);

        if(this.mod != null) {
            Vector3 pos = new Vector3(this.coord.x/64 + X * 32,this.coord.z/64,(31 - this.coord.y/64) + ((2 - Y) * 32));
            Quaternion quaternion = new Quaternion(0,0,0,0);

            this.mod.transform.set(pos,quaternion);
        }
    }

    public byte getDir(){
        return this.dir;
    }

    public void dispose(){}
    public void loadModel(int x, int y){
        if (Entity.model == null) {
            Entity.model = new ModelBuilder().createBox(1f, 2f, 1f,
                    new Material(TextureAttribute.createDiffuse(new Texture(Gdx.files.internal("data/img/3dTexture/muroSimple.png")))),
                    VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
        }
        this.mod = new ModelInstance(model);
        this.mod.transform.translate(this.coord.x/64 + x * 32,this.coord.z/64,(31 - this.coord.y/64) + ((2 - y) * 32));
        System.out.println("Escala:  " + this.mod.transform.getScaleX() + ", " + this.mod.transform.getScaleY() + ", " + this.mod.transform.getScaleZ());
        System.out.println("Modelo Cargado");
    }

    public void setAngle(Vector3 posCam){
        //this.mod.transform.g
        this.mod.transform.rotate(Vector3.Y, .01f);
    }

    public void rotation(Vector3 m, float maximum){
        this.mod.transform.rotate(Vector3.Y, maximum);
    }

    public void render(ModelBatch b, Environment environment){
        b.render(this.mod,environment);
    }
    public boolean is3DRenderable() {
        return this.mod == null;
    }

    public void move(int angle) {
        this.coord.x -= Math.cos(Math.toRadians(angle));
        this.coord.y += Math.sin(Math.toRadians(-angle));
    }

    @Override
    public ModelInstance getModelInstance() {
        return this.mod;
    }
}