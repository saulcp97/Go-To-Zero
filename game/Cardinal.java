package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.XmlReader;
import com.mygdx.game.Cubos.Entidades.bullet.Projectile;
import com.mygdx.game.simulation.Edification.ProcGenerator;
import com.mygdx.game.simulation.Language;

import java.io.IOException;
import java.util.ArrayList;

public class Cardinal {
    public static Sound thunderSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/efects/thunder_2_near.wav"));

    public static final Matrix3 inversionMatrix = new Matrix3(new float[]{0f,0f,-1f,0,0,0,1f,0,0,});

    public static final Pool<Projectile> bulletPool = new Pool<Projectile>() {
        @Override
        protected Projectile newObject() {
            return new Projectile();
        }
    };

    //LowPolyVersion
    public static final Model sphere = new ModelBuilder().createSphere(.25f,    .25f, .25f,4,4,
            new Material(TextureAttribute.createDiffuse(new Texture(Gdx.files.internal("data/img/3dTexture/trans.png")))),
    VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates
    );

    public static  ColorAttribute[] fireMaterial = getFireMaterial(120);
    private static ColorAttribute[] getFireMaterial(int subdivision) {
        ColorAttribute[] res = new ColorAttribute[subdivision + 1];
        for (int i = 0; i < res.length; ++i) {
            res[i] = ColorAttribute.createDiffuse(1,1f - ((float) i) / subdivision,0,((float) i) / (2 * subdivision));
        }
        return res;
    }

    //private TextureArray tileset = new TextureArray(Gdx.files.internal("data/img/3dTexture/trans.png"));
    //Mapa de texturas 64x64
    private Texture tileset = new Texture(Gdx.files.internal("data/img/3dTexture/trans.png"));

    //Props: Objetos sin utilidad a parte de la meramente visual
    public static final Model Barril =
            new ObjLoader().loadModel(Gdx.files.internal("data/img/3DModels/selection/prop_floor_barrel.obj"));
    public static final Model AntorchaMuro =
            new ObjLoader().loadModel(Gdx.files.internal("data/img/3DModels/selection/prop_wall_torch.obj"));
    public static final Model PuertaReja =
            new ObjLoader().loadModel(Gdx.files.internal("data/img/3DModels/selection/prop_wall_door_cell.obj"));
    public static final Model HouseModel =
            new ObjLoader().loadModel(Gdx.files.internal("data/img/3DModels/selection/house.obj"),true);
    public static final Model mesaBar =
            new ObjLoader().loadModel(Gdx.files.internal("data/img/3DModels/selection/mesa.obj"));
    public static final Model sillaBar =
            new ObjLoader().loadModel(Gdx.files.internal("data/img/3DModels/selection/silla.obj"));

    private static Model[] cubos = null;
    public static Skin getSkin() {
        return new Skin(Gdx.files.internal("data/uiskin.json"));
    }

    public static final Model Floor = getPartialCube("00000001");
    public static Model getProcTree(int seed, int div,int width,int heightNode,int minNode, int maxNode){

        int treeLevels = Math.round((maxNode - minNode) * ProcGenerator.RandomS(seed)) + minNode;
        int widthButton = width;
        int widthTop = (int) (widthButton/3 * ProcGenerator.RandomS()) + 2*widthButton/3;

        //System.out.println("Niveles del Arbol: " + treeLevels);
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        MeshPartBuilder meshBuilder;

        float angle = MathUtils.degreesToRadians * (360f / div);
        Matrix3 ma = new Matrix3(new float[]{MathUtils.cos(angle),MathUtils.sin(angle),0,
                -MathUtils.sin(angle),MathUtils.cos(angle),0,
                0,0,1});
        Vector3 p2, p1 = new Vector3(1,0,0);
        p2 = p1.cpy().mul(ma);

        Material simple =  new Material();
        float variationB, variationT = 0;
        float height = ((float)heightNode) / 64;
        float center = ((float)width) / 64f -.5f; //32 = 64 / 2 ahorrar calculo

        for(int i = 0; i < treeLevels; ++i) {
            meshBuilder = modelBuilder.part("Tronco" + i, GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates, simple);
            variationB = (((float)widthButton)/64f);
            variationT = (((float)widthTop)/64f);

            if(i == 0) {
                meshBuilder.rect(variationB, 0, -variationB, variationB, 0, variationB, -variationB, 0, variationB, -variationB, 0, -variationB, 0, -1, 0);
            }
            //meshBuilder.rect(-variationB +center,height * i, -variationB +center, -variationT +center,height * (i + 1), -variationT +center, variationT +center, height * (i + 1), -variationT +center, variationB +center, height * i,-variationB +center, 0, 0, 1);

            for(int j = 0; j < div; ++j) {
                //meshBuilder.rect(1, height * i, -1, 1, height * (i + 1),-1, 1,height * (i + 1), 1,1,height * i,1,0,0,1);
                meshBuilder.rect(p1.x/2 * variationB +center, height * i, p1.y/2 * variationB +center, p1.x/2 * variationT +center, height * (i + 1),p1.y/2 * variationT +center, p2.x/2 * variationT +center,height * (i + 1), p2.y/2 * variationT +center,p2.x/2 * variationB +center,height * i,p2.y/2 * variationB +center,0,0,1);


                p1 = p2.cpy();
                p2.mul(ma);
            }


//            meshBuilder.rect(-variationB +center,height * i, -variationB +center, -variationT +center,height * (i + 1), -variationT +center, variationT +center, height * (i + 1), -variationT +center, variationB +center, height * i,-variationB +center, 0, 0, 1);
  //          meshBuilder.rect(variationB +center, height * i, -variationB +center, variationT +center,height * (i + 1), -variationT +center, variationT +center, height * (i + 1), variationT +center, variationB +center, height * i, variationB +center, 0, 0, 1);
    //        meshBuilder.rect(variationB +center, height * i, variationB +center, variationT +center,height * (i + 1), variationT +center, -variationT +center, height * (i + 1), variationT +center, -variationB +center, height * i, variationB +center, 0, 0, 1);
      //      meshBuilder.rect(-variationB +center,height * i, variationB +center, -variationT +center,height * (i + 1), variationT +center, -variationT +center, height * (i + 1), -variationT +center, -variationB +center, height * i, -variationB +center, 0, 0, 1);

            widthButton = widthTop;
            widthTop = (int) (widthButton/4 * ProcGenerator.RandomS()) + 3*(widthButton + 4)/4;
        }

        meshBuilder = modelBuilder.part("Copa", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates, simple);
        Vector3 v00 = new Vector3(-variationT +center,treeLevels * height,-variationT +center),
                v01 = new Vector3(-variationT +center,treeLevels * height,variationT +center),
                v10 = new Vector3(variationT +center,treeLevels * height,-variationT +center),
                v11 = new Vector3(variationT +center,treeLevels * height,variationT +center),
                cent = new Vector3(center,(treeLevels + 1) * height,center);

        meshBuilder.triangle(v10,v00, cent);
        meshBuilder.triangle(v00,v01, cent);
        meshBuilder.triangle(v01,v11,cent);
        meshBuilder.triangle(v11,v10,cent);

        return modelBuilder.end();
    }
    public static Model PartialCube(String code) {
        if(cubos == null) {
            cubos = new Model[128];
        }
        if(cubos[Integer.parseInt(code, 2)] == null) {
            cubos[Integer.parseInt(code, 2)] = getPartialCube(code);
        }
        return cubos[Integer.parseInt(code, 2)];
    }
    private static Model getPartialCube(String code) {

        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        MeshPartBuilder meshBuilder;
        Material simple =  new Material(TextureAttribute.createDiffuse(new Texture(Gdx.files.internal("data/img/3dTexture/baseMuroMaidCaffeExterior.png"))));
        meshBuilder = modelBuilder.part("Cabeza", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates, simple);

        if(code.length() == 8) {
            if(code.charAt(7) == '1') { //Parte de Arriba
                meshBuilder.rect(1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0);
            }
            if(code.charAt(6) == '1') { //Parte de Norte
                meshBuilder.rect(0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, -1);
            }
            if(code.charAt(5) == '1') { //Parte de Sur
                meshBuilder.rect(1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1);
            }
            if(code.charAt(4) == '1') { //Parte de Este
                meshBuilder.rect(1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0);
            }
            if(code.charAt(3) == '1') { //Parte de Oeste
                meshBuilder.rect(0, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, -1, 0, 0);
            }
            if(code.charAt(2) == '1') { //Parte de Abajo
                meshBuilder.rect(1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, -1, 0);
            }
        } else {
            meshBuilder.rect(1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0);
        }

        return modelBuilder.end();
    }

    private static final Material blackLightning = new Material(ColorAttribute.createSpecular(Color.WHITE),TextureAttribute.createDiffuse(new Texture("data/img/3dTexture/blackLightning.png")), new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
    private static final Material flowerMaterial = new Material(ColorAttribute.createDiffuse(Color.FIREBRICK));
    private static final Material leafMaterial = new Material(ColorAttribute.createDiffuse(Color.CHARTREUSE));
    private static final Material mistMaterial = new Material(ColorAttribute.createDiffuse(Color.GOLD));

    public static Model getProcLightning(int seed, Vector3 end, int rad) {
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        MeshPartBuilder meshBuilder;

        meshBuilder = modelBuilder.part("base", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates, blackLightning);

        sector(0, meshBuilder, Vector3.Zero, Vector3.Zero, end, 6);

        return modelBuilder.end();
    }
    private static void sector (int it, MeshPartBuilder meshBuilder, Vector3 ini, Vector3 absI, Vector3 end, int maxIt) {
        Vector3 dir = end.cpy().sub(ini);
        dir.x /= 1.75;
        dir.y /= 1.75;
        dir.z /= 1.75;

        Vector3 perp = dir.cpy().mul(inversionMatrix);
        perp.x /= perp.len();
        perp.y /= perp.len();
        perp.z /= perp.len();

        float variation = MathUtils.random(2,4);
        perp.x *= variation;
        perp.y *= variation;
        perp.z *= variation;

        Vector3 nextNode = dir.cpy().add(ini).add(perp);
        //System.out.println("Dir: " + dir.toString() +" con origen " + ini.toString() + " con final en " + end.toString() +" es perp: " + perp.toString() +" perpendicular? " + dir.isPerpendicular(perp));
        //System.out.println("siguiente punto: " + nextNode.toString() );

        if (it < maxIt) {
            if(Math.random() < (.9f/maxIt) * it) {
                nextNode.y -= dir.y * .75f;
                sector(it + 1,meshBuilder,nextNode,absI,end,maxIt);

                perp.x *= -1;
                perp.y *= -1;
                perp.z *= -1;

                nextNode = dir.cpy().add(ini).add(perp);
            }

            Vector3 n00 = new Vector3(nextNode.x -2f/64,nextNode.y,nextNode.z),
                    n01 = new Vector3(nextNode.x ,nextNode.y,nextNode.z + 2f/64),
                    n02 = new Vector3(nextNode.x + 2f/64,nextNode.y, nextNode.z),
                    n03 = new Vector3(nextNode.x,nextNode.y,nextNode.z - 2f/64);

            Vector3
                    n10 = new Vector3(end.x - 2f / 64, end.y, end.z),
                    n11 = new Vector3(end.x, end.y, end.z + 2f / 64),
                    n12 = new Vector3(end.x + 2f / 64, end.y, end.z),
                    n13 = new Vector3(end.x, end.y, end.z - 2f / 64);

            meshBuilder.rect(n00,n01,n11,n10,Vector3.Z);
            meshBuilder.rect(n01,n02,n12,n11,Vector3.Z);
            meshBuilder.rect(n02,n03,n13,n12,Vector3.Y);
            meshBuilder.rect(n03,n00,n10,n13,Vector3.Y);

            sector(it + 1,meshBuilder,ini,absI,nextNode,maxIt);

        } else {
            Vector3 n00 = new Vector3(ini.x -2f/64,ini.y,ini.z),
                    n01 = new Vector3(ini.x ,ini.y,ini.z + 2f/64),
                    n02 = new Vector3(ini.x + 2f/64,ini.y, ini.z),
                    n03 = new Vector3(ini.x,ini.y,ini.z - 2f/64);

            Vector3
                    n10 = new Vector3(end.x - 2f / 64, end.y, end.z),
                    n11 = new Vector3(end.x, end.y, end.z + 2f / 64),
                    n12 = new Vector3(end.x + 2f / 64, end.y, end.z),
                    n13 = new Vector3(end.x, end.y, end.z - 2f / 64);

            meshBuilder.rect(n00,n01,n11,n10,Vector3.Z);
            meshBuilder.rect(n01,n02,n12,n11,Vector3.Z);
            meshBuilder.rect(n02,n03,n13,n12,Vector3.Y);
            meshBuilder.rect(n03,n00,n10,n13,Vector3.Y);
        }
    }

    public static Model getPineapple(int seed) {
        ArrayList<Vector3> vList = new ArrayList<Vector3>();

        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        MeshPartBuilder meshBuilder;

        //X -> Oeste/este
        //Z -> Norte/Sur

        float cent =  + ProcGenerator.RandomS(seed) * .75f;
        float hight = cent + ProcGenerator.RandomS() * .5f;
        float lenghtLeaf = hight + ProcGenerator.RandomS() * 1f;

        vList.add(new Vector3(cent,hight,cent));  //Vertice superior
        vList.add(new Vector3(cent,hight / 2,cent * 2)); //Sur               //1
        vList.add(new Vector3(0f,hight / 2,cent)); //Oeste             //2
        vList.add(new Vector3(0f,0,cent * 2));  //SurOeste                   //3

        vList.add(new Vector3(cent,hight / 2,0f)); //Nor               //4
        vList.add(new Vector3(cent * 2,hight / 2,cent)); //Este              //5
        vList.add(new Vector3(cent * 2,0,cent * 2));  //SurEste             //6

        vList.add(new Vector3(cent * 2,0,0f));  //NorEste             //7
        vList.add(new Vector3(0f,0,0f));  //NorOeste            //8

        vList.add(new Vector3(cent,0,cent)); //Abajo              //9

        meshBuilder = modelBuilder.part("base", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates, flowerMaterial);
        //Done
        meshBuilder.rect(vList.get(0),vList.get(2),vList.get(3),vList.get(1),vList.get(3));
        //Done
        meshBuilder.rect(vList.get(0),vList.get(1),vList.get(6),vList.get(5),vList.get(6));
        //Done
        meshBuilder.rect(vList.get(0),vList.get(5),vList.get(7),vList.get(4),vList.get(7));
        //Done
        meshBuilder.rect(vList.get(0),vList.get(4),vList.get(8),vList.get(2),vList.get(8));

        meshBuilder = modelBuilder.part("Leaf", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates, leafMaterial);
        meshBuilder.rect(vList.get(9),vList.get(3),new Vector3(cent,hight / 4,cent + lenghtLeaf),vList.get(6),new Vector3(cent,hight / 4,cent - lenghtLeaf));
        meshBuilder.rect(vList.get(9),vList.get(6),new Vector3(cent + lenghtLeaf,hight / 4,cent),vList.get(7),new Vector3(cent - lenghtLeaf,hight / 4,cent));
        meshBuilder.rect(vList.get(9),vList.get(7),new Vector3(cent,hight / 4,cent - lenghtLeaf),vList.get(8),new Vector3(cent,hight / 4,cent + lenghtLeaf));
        meshBuilder.rect(vList.get(9),vList.get(8),new Vector3(cent - lenghtLeaf,hight / 4,cent),vList.get(3),new Vector3(cent + lenghtLeaf,hight / 4,cent));

        return modelBuilder.end();
    }

    public static final Model particleSphere =
            new ModelBuilder().createSphere(.1f,.1f,.1f,4, 4,
                    new Material(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA),
                            ColorAttribute.createDiffuse(Color.FIREBRICK.r,Color.FIREBRICK.g,Color.FIREBRICK.b,.5f)),
                    VertexAttributes.Usage.Position  | VertexAttributes.Usage.TextureCoordinates);


    public static boolean containsNumber(final byte[] array, final byte v) {
        boolean result = false;
        for(int i : array){
            if(i == v){
                result = true;
                break;
            }
        }
        return result;
    }
    public static float normalProb( int actual, int max) {
        float prob;
        // 1 -> 0.999
        prob = MathUtils.sin((float)Math.acos(((float)actual) / ((float) max) ));
        return prob;
    }

    public static void readJSON() {
        //MyReadJSON def = new MyReadJSON(Gdx.files.local("data/Objects/default.json"));
    }
    public static void loadObjectsFromXML() {
        XmlReader reader = new XmlReader();
        XmlReader.Element root;
        FileHandle input = Gdx.files.local("data/Objects/default.json");
        try {
            root = reader.parse(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Language language = new Language("es");
    public static void setLanguage(String code) {
        Cardinal.language = new Language(code);
    }

    public static Model getSpiralDiv(int div, int hPart, int height) {
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        MeshPartBuilder meshBuilder;
        meshBuilder = modelBuilder.part("spira", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates, leafMaterial);
        Vector3 p = new Vector3(1,0,0);
        float angle = MathUtils.degreesToRadians * (360f / div);
        Matrix3 ma = new Matrix3(new float[]{MathUtils.cos(angle),MathUtils.sin(angle),0,
                -MathUtils.sin(angle),MathUtils.cos(angle),0,
                0,0,1});

        for (int i = 0; i < ((float)height) / hPart; ++i) {
            meshBuilder.box(p.x,(i * hPart)/64f,p.y,0.1f,0.1f,0.1f);
            p.mul(ma);
        }

        return modelBuilder.end();
    }

}
