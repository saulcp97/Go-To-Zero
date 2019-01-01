package com.mygdx.game.Cubos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class Block3D {

    public static Texture[] textures = {
            new Texture(Gdx.files.internal("data/img/3dTexture/muroSimple.png")),
//          new Texture(Gdx.files.internal("data/img/3dTexture/cespedSimple.png")),
            new Texture(Gdx.files.internal("data/img/3dTexture/m1.png")),
            new Texture(Gdx.files.internal("data/img/3dTexture/baldosasCeramica.png")),
            new Texture(Gdx.files.internal("data/img/3dTexture/yuri.png")),
            new Texture(Gdx.files.internal("data/img/3dTexture/abysmFloor.png")),
            //Puede ayudar en el futuro: https://stackoverflow.com/questions/26518562/how-to-animate-textures-in-a-3d-model
            null, null, null, null, null,
            new Texture(Gdx.files.internal("data/img/3dTexture/baseMuroMaidCaffe.png")),
            null,
            new Texture(Gdx.files.internal("data/img/3dTexture/muroExteriorCasita.png")),
            null,
            new Texture(Gdx.files.internal("data/img/3dTexture/baseMuroMaidCaffeExterior.png"))
    };

    public static Texture getTexture(int index){
        return textures[index];
    }

   /* private static Model bloque = new ModelBuilder().createBox(1f, 1f, 1f,
            new Material(TextureAttribute.createDiffuse(textures[0])),
            VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
    */


    public static Model bloque = getCompanyCube();

    public static Model modelStairs =
            new ObjLoader().loadModel(Gdx.files.internal("data/img/3DModels/escaleras/escalerasRare.obj"));

    //private static Model bloque = new ObjLoader().loadModel(Gdx.files.internal("data/img/3DModels/selection/normal.obj"));

    public static Model getCompanyCube() {
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        MeshPartBuilder meshBuilder;
        Material simple =  new Material(TextureAttribute.createDiffuse(new Texture(Gdx.files.internal("data/img/3dTexture/baseMuroMaidCaffeExterior.png"))));
        meshBuilder = modelBuilder.part("Cabeza", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates, simple);
        meshBuilder.rect(0,0,1, 0,1,1, 0,1,0, 0,0,0, -1,0,0);
        meshBuilder.rect(1,0,1, 1,1,1, 0,1,1, 0,0,1, 0,0,1);
        meshBuilder.rect(1,0,0, 1,1,0, 1,1,1, 1,0,1, 1,0,0);
        meshBuilder.rect(0,0,0, 0,1,0, 1,1,0, 1,0,0, 0,0,-1);
        meshBuilder.rect(1,1,1, 1,1,0, 0,1,0, 0,1,1, 0,1,0);
        meshBuilder.rect(1,0,0, 1,0,1, 0,0,1, 0,0,0, 0,-1,0);
        return modelBuilder.end();
    }

    /*
    public boolean rendered;
    public ModelInstance modelInstance;
    private Vector3 N,P;

    private int chunkX, chunkY, chunkZ;
    public Block3D(int x, int y, int z, byte t) {
        this.modelInstance = null;
        this.chunkX = 0;
        this.chunkY = 0;
        this.chunkZ = 0;
        this.rendered = false;
    }

    public Block3D(Block base, int x, int y, int z){
        this.chunkX = x;
        this.chunkY = y;
        this.chunkZ = z;

        this.rendered = false;
        if(base instanceof Escaleras) {
            asignModel(modelStairs);
            switch(0) {
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


            }
        } else if(base instanceof Decoration) {
            switch(base.getOrient()) {
                case 1://Antorcha
                    asignModel(Cardinal.AntorchaMuro);
                    break;
                case 3://Arboles
                    asignModel(Cardinal.getProcTree((int)(Math.random() * 1000),64,64,2,31));
                    break;
                case 4://Mesa
                    setType((byte)-1);
                    asignModel(Cardinal.sillaBar);
                    break;
                case 5://Mesa
                    setType((byte)-1);
                    asignModel(Cardinal.mesaBar);
                    break;
                case 8://Barriles
                    asignModel(Cardinal.Barril);
                    break;
                case 101://Casa
                    setType((byte)-1);
                    asignModel(Cardinal.HouseModel);
                    break;
                case 31://Piña
                    setType((byte)-1);
                    asignModel(Cardinal.getPineapple(MathUtils.random(1,100)));
                    break;
                default://Las puertas
                    asignModel(Cardinal.PuertaReja);
            }
        } else {
            if (this.coord.z != 0) {
                asignModel(bloque);
            } else {
                asignModel(Cardinal.Floor);
            }
        }
    }

    private void asignModel(Model modelo){
        this.modelInstance = new ModelInstance(modelo);
        Material material = this.modelInstance.materials.get(0);

        switch(this.type){
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
            default:
                //Model Plane = loader.loadModel(Gdx.files.internal("data/img/3DModels/accel-world-blacklotus/source/lotus.obj"));
                //this.p1 = new ModelInstance(Plane);
                //p1.materials.get(0).set(ColorAttribute.createDiffuse(Color.PURPLE));
                //p1.materials.get(1).set(ColorAttribute.createDiffuse(Color.DARK_GRAY));
                //p1.materials.get(0).set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
               /* textures[14].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                System.out.println("Materiales de las escaleras: " + this.modelInstance.materials.size);
                material.set(TextureAttribute.createDiffuse(textures[14]));
                material.set(TextureAttribute.createReflection(textures[1]));
                //material.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
                break;

        }
        this.modelInstance.transform.translate(this.coord.x/64 + this.chunkX * 32 + .5f,this.chunkZ * 32 + this.coord.z/64 + .5f,(31 - this.coord.y/64) + ((2 - this.chunkY) * 32)  - .5f);
        this.N = new Vector3(this.coord.x/64 + this.chunkX * 32,this.chunkZ * 32 + this.coord.z/64 + .5f,(31 - this.coord.y/64) + ((2 - this.chunkY) * 32));
        this.P = new Vector3(this.coord.x/64 + this.chunkX * 32 + this.width/64,this.chunkZ * 32 + this.coord.z/64 + this.deep/64 + .5f,(31 - this.coord.y/64) + ((2 - this.chunkY) * 32) + this.height/64);

        if(modelo.equals(modelStairs)) {
            System.out.println("Las texturas son: " + this.modelInstance.materials.size);
            switch (this.orient) {
                case 0:
                    this.modelInstance.transform.rotateRad(Vector3.Y,(float)Math.toRadians(90));
                    break;
                case 1:
                    break;
                case 2:
                    this.modelInstance.transform.rotateRad(Vector3.Y,(float)Math.toRadians(-90));
                    break;
                case 3:
                    this.modelInstance.transform.rotateRad(Vector3.Y,(float)Math.toRadians(180));
                    break;
            }
        }


    }

    public boolean isVisible(Frustum fr) {
        return fr.boundsInFrustum(N,P);
    }
*/
}
