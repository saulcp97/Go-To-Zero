package com.mygdx.game.Cubos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Cardinal;
import com.mygdx.game.Cubos.Entidades.Entity;
import com.mygdx.game.Cubos.Entidades.vida.NPC.NPC;

import static com.mygdx.game.Cubos.Block3D.textures;

public class Block {

    public static Texture muroSimple = new Texture(Gdx.files.internal("data/img/muroSimple.png"));
    public static Texture texturaBaldosas = new Texture(Gdx.files.internal("data/img/baldosasCeramica.png"));
    public static Texture baseMuroMaidCaffe = new Texture(Gdx.files.internal("data/img/baseMuroMaidCaffe.png"));
    public static Texture baseMuroMaidCaffeExterior = new Texture(Gdx.files.internal("data/img/baseMuroMaidCaffeExterior.png"));
    public static Texture cespedSimple = new Texture(Gdx.files.internal("data/img/sueloSeco.png"));
    public static Texture muroMaidCaffe = new Texture(Gdx.files.internal("data/img/muroMaidCafe.png"));
    public static Texture muroMaidCaffeExterior = new Texture(Gdx.files.internal("data/img/muroMaidCafeExterior.png"));
    public static Texture muroExteriorCasitaMadera = new Texture(Gdx.files.internal("data/img/muroExteriorCasita.png"));
    public static Texture techoMaderaDerecha = new Texture(Gdx.files.internal("data/img/techoDer.png"));
    public static Texture plantMonsterPurple = new Texture(Gdx.files.internal("data/img/plantMonster.png"));
    public static Texture buyCube = new Texture(Gdx.files.internal("data/img/buyCub.png"));

    protected Texture texture;
    public Vector3 coord;

    protected int width;
    protected int height;
    protected int deep;
    public boolean rendered;

    protected byte type;
    protected byte orient;

    private int widthD, heightD;
    private float xdraw,ydraw;

    protected p3D b3D;

    public Block(int x, int y, int z, byte t) {
        this.coord = new Vector3(x,y,z);

        this.width = 64;
        this.height = 64;
        this.deep = 64;

        this.type = t;
        this.orient = (byte) 0;

        this.b3D = null;

        switch(this.type){
            case Byte.MIN_VALUE:
                this.texture = null;
                break;
            case -1:
                this.texture = plantMonsterPurple;
                break;
            case 1:
                this.texture = cespedSimple;
                break;
            case 2:
                this.texture = texturaBaldosas;
                break;
            case 10:
                this.texture = baseMuroMaidCaffeExterior;
                break;
            case 12:
                this.texture = muroExteriorCasitaMadera;
                break;
            case 13:
                this.texture = techoMaderaDerecha;
                break;
            case 14:
                this.texture = muroMaidCaffeExterior;
                break;
            default:
                this.texture = muroSimple;
                break;
        }

        if(this.texture != null) {
            this.xdraw = x;
            this.ydraw = y + z / 2;
            this.widthD = this.width;
            this.heightD = this.height + this.deep / 2;
        }
    }

    public void setTexture(Texture text) {
        if(text != null) {
            this.texture = text;
            this.xdraw = this.coord.x;
            this.ydraw = this.coord.y + this.coord.z/2;
        }
    }
    public byte getOrient() { return orient; }
    public byte getType() { return type; }

    public void setType(byte t) {
        this.type = t;
        switch(this.type){
            case Byte.MIN_VALUE:
                this.texture = null;
                break;
            case -1:
                this.texture = plantMonsterPurple;
                break;
            case 1:
                this.texture = cespedSimple;
                break;
            case 2:
                this.texture = texturaBaldosas;
                break;
            case 10:
                this.texture = baseMuroMaidCaffeExterior;
                break;
            case 12:
                this.texture = muroExteriorCasitaMadera;
                break;
            case 13:
                this.texture = techoMaderaDerecha;
                break;
            case 14:
                this.texture = muroMaidCaffeExterior;
                break;
            default:
                this.texture = muroSimple;
                break;
        }
    }

    public void setDrawSize(float width, float height) {
        this.widthD = (int)width;
        this.heightD = (int)height;
    }

    public float getXdraw() {
        return xdraw;
    }
    public float getYdraw() {
        return ydraw;
    }

    public void setXdraw(float x) { this.xdraw = x; }
    public void setYdraw(float y) { this.ydraw = y; }

    public int getDrawWidth() { return  this.widthD; }
    public int getDrawHeight() { return  this.heightD; }

    public float getXP() { return this.coord.x; }
    public float getYP() { return this.coord.y + this.coord.z/2; }

    public int getHeight() { return height; }
    public int getWidth() { return width; }
    public int getDeep() { return deep; }

    public void setOrient(byte orient) { this.orient = orient; }

    public boolean colision(Block b){
        return this.coord.x < b.coord.x + b.width && this.coord.x + this.width > b.coord.x
                && this.coord.y < b.coord.y + b.height && this.coord.y + this.height > b.coord.y
                && this.coord.z < b.coord.z + b.deep && this.coord.z + this.deep > b.coord.z;
    }

    public boolean colision(float x, float y, float z, int width, int height, int deep) {
        return this.coord.x < x + width && this.coord.x + this.width > x
                && this.coord.y < y + height && this.coord.y + this.height > y
                && this.coord.z < z + deep && this.coord.z + this.deep > z;
    }

    public void realign(){
     //   sprite.setPosition(this.coord.x,this.coord.y + this.coord.z/2);
        this.xdraw = this.coord.x;
        this.ydraw = this.coord.y + this.coord.z/2;
    }

    public void setPosiD(float x, float y) {
        this.xdraw = x;
        this.ydraw = y;
    }

    public void draw(Batch batch) {
        if(this.texture != null) {
            batch.draw(this.texture, xdraw, ydraw);
        }
    }

    public boolean rectify(Block b, byte dir) {
        if(this.colision(b)){
            //Dir 0 +Y, 1 +X, 2 -Y, 3 -X, 4 +Z, 5 -Z
            //se corrige la posicion de "b"
            switch (dir){
                case 0:
                    b.coord.y -= b.coord.y + b.height - this.coord.y;
                    break;
                case 1:
                    b.coord.x -= b.coord.x + b.width - this.coord.x;
                    break;
                case 2:
                    b.coord.y += this.coord.y + this.height - b.coord.y;
                    break;
                case 3:
                    b.coord.x += this.coord.x + this.width - b.coord.x;
                    break;
                case 4:
                    b.coord.z -= b.coord.z + b.deep - this.coord.z;
                    break;
                case 5:
                    b.coord.z += this.coord.z + this.deep - b.coord.z;
                    break;
            }
            return true;
        }
        return false;
    }

    public boolean rectify(Entity ent) {
        if(colision(ent)){
            if(ent instanceof NPC) {
                ent.move(((NPC)ent).getAngle() + 180);
            }
            return true;
        }
        return false;
    }

    public boolean rectify(NPC ent, int dist,boolean a) {
        if(colision(ent)){
            ent.move(ent.getAngle() + 180, (byte)dist,a);
            return true;
        }
        return false;
    }

    public boolean CompareTo(Block b) {
        return (this.coord.z < b.coord.z)
                || (this.coord.y > b.coord.y && this.coord.z == b.coord.z);
    }

    public ModelInstance getModelInstance(){
        return this.b3D.modelInstance;
    }

    public void build3DPart(int x, int y, int z) { //Podriamos añadir variaciones para que un mismo bloque tenga variantes
        this.b3D = new p3D('B', this.type, this.orient, x, y , z);
    }
    public boolean modeled () {return this.b3D != null;}
    public void toRender() { this.rendered = true; }
    public void occultRender() { this.rendered = false; }
    public boolean isVisible(Frustum fr) {
        return this.b3D.isVisible(fr);
    }

    protected class p3D {
        public ModelInstance modelInstance;
        private Vector3 N,P;
        private int chunkX, chunkY, chunkZ;

        public p3D(char B, byte type, byte orient, int X, int Y, int Z){//Se añade el tipo de clase, el tipo y la orientación, para univeralizarlo el conjunto
            this.chunkX = X;
            this.chunkY = Y;
            this.chunkZ = Z;

            switch (B) {
                case 'B': //Bloque normal
                    if(coord.z == 0) {
                        this.modelInstance = new ModelInstance(Cardinal.Floor);
                    } else {
                        this.modelInstance = new ModelInstance(Block3D.bloque);
                    }

                    Material material = this.modelInstance.materials.get(0);
                    switch (type) {
                        case 1:
                            textures[1].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                            material.set(TextureAttribute.createDiffuse(textures[1]));
                            break;
                        case 2:
                            textures[2].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                            material.set(TextureAttribute.createDiffuse(textures[2]));
                            break;
                        case 3:
                            textures[3].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                            material.set(TextureAttribute.createDiffuse(textures[3]));
                            break;
                        case 10:
                            textures[10].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                            material.set(TextureAttribute.createDiffuse(textures[10]));
                            break;
                        case 12:
                            textures[12].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                            material.set(TextureAttribute.createDiffuse(textures[12]));
                            break;
                        case 14:
                            textures[14].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                            material.set(TextureAttribute.createDiffuse(textures[14]));
                            break;
                    }
                    this.posic();
                    break;
                case 'E':
                    this.modelInstance = new ModelInstance(Block3D.modelStairs);
                    material = this.modelInstance.materials.get(0);
                    switch (type) {
                        case 1:
                            textures[1].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                            material.set(TextureAttribute.createDiffuse(textures[1]));
                            break;
                        case 2:
                            textures[2].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                            material.set(TextureAttribute.createDiffuse(textures[2]));
                            break;
                        case 3:
                            textures[3].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                            material.set(TextureAttribute.createDiffuse(textures[3]));
                            break;
                        case 10:
                            textures[10].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                            material.set(TextureAttribute.createDiffuse(textures[10]));
                            break;
                        case 12:
                            textures[12].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                            material.set(TextureAttribute.createDiffuse(textures[12]));
                            break;
                        case 14:
                            textures[14].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                            material.set(TextureAttribute.createDiffuse(textures[14]));
                            break;
                    }
                    this.posic();

                    switch(orient) {
                        case 0:
                            this.modelInstance.transform.rotate(Vector3.Y,90);
                            this.modelInstance.transform.translate(-1,0,0);
                            break;
                        case 3:
                            this.modelInstance.transform.rotate(Vector3.Y,180);
                            this.modelInstance.transform.translate(-1,0,-1);
                            break;
                        case 2:
                            this.modelInstance.transform.rotate(Vector3.Y,-90);
                            this.modelInstance.transform.translate(0,0,-1);
                            break;
                    }
                    break;
                case 'D':
                    switch(orient) {
                        case 1://Antorcha
                            this.modelInstance = new ModelInstance(Cardinal.AntorchaMuro);
                            break;
                        case 3://Arboles
                            this.modelInstance = new ModelInstance(Cardinal.getProcTree((int)(Math.random() * 1000),6,64,64,2,16));
                            break;
                        case 4://Mesa
                            this.modelInstance = new ModelInstance(Cardinal.sillaBar);
                            break;
                        case 5://Mesa
                            this.modelInstance = new ModelInstance(Cardinal.mesaBar);
                            break;
                        case 8://Barriles
                            this.modelInstance = new ModelInstance(Cardinal.Barril);
                            break;
                        case 101://Casa
                            setType((byte)-1);
                            this.modelInstance = new ModelInstance(Cardinal.HouseModel);
                            break;
                        case 31://Piña
                            setType((byte)-1);
                            this.modelInstance = new ModelInstance(Cardinal.getPineapple(MathUtils.random(1,100)));
                            break;
                        default://Las puertas
                            this.modelInstance = new ModelInstance(Cardinal.PuertaReja);
                    }
                    material = this.modelInstance.materials.get(0);
                    switch (type) {
                        case 1:
                            textures[0].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                            material.set(TextureAttribute.createDiffuse(textures[0]));
                            break;
                        case 2:
                            textures[1].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                            material.set(TextureAttribute.createDiffuse(textures[1]));
                            break;
                        case 3:
                            textures[2].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                            material.set(TextureAttribute.createDiffuse(textures[2]));
                            break;
                        case 10:
                            textures[10].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                            material.set(TextureAttribute.createDiffuse(textures[10]));
                            break;
                        case 12:
                            textures[12].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                            material.set(TextureAttribute.createDiffuse(textures[12]));
                            break;
                        case 14:
                            textures[14].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                            material.set(TextureAttribute.createDiffuse(textures[14]));
                            break;
                    }
                    this.posic();
                    break;
                case 'P':
                    this.modelInstance = new ModelInstance(Cardinal.HouseModel);
                    this.posic();
                    break;
            }
        }

        private void posic() {
            this.modelInstance.transform.translate(coord.x/64 + this.chunkX * 32 + .5f,this.chunkZ * 32 + coord.z/64 + .5f,(31 - coord.y/64) + ((2 - this.chunkY) * 32)  - .5f - (height - 64)/64);
            this.N = new Vector3(coord.x/64 + this.chunkX * 32,this.chunkZ * 32 + coord.z/64 + .5f,(31 - coord.y/64) + ((2 - this.chunkY) * 32));
            this.P = new Vector3(coord.x/64 + this.chunkX * 32 + width/64,this.chunkZ * 32 + coord.z/64 + deep/64 + .5f,(31 - coord.y/64) + ((2 - this.chunkY) * 32) + height/64);
        }

        public boolean isVisible(Frustum fr) {
            return fr.boundsInFrustum(N,P);
        }
        public void setModelInstance(ModelInstance modelInstance) {
            this.modelInstance = modelInstance;
            this.posic();
        }
    }
}