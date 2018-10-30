package com.mygdx.game.Cubos.Entidades.vida.NPC;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.UBJsonReader;
import com.mygdx.game.Chunk;
import com.mygdx.game.Cubos.Block;
import com.mygdx.game.Cubos.Entidades.Entity;
import com.mygdx.game.Cubos.Entidades.bullet.Particulas;
import com.mygdx.game.Cubos.Entidades.bullet.Projectile;
import com.mygdx.game.Cubos.Entidades.vida.Avatar;
import com.mygdx.game.Cubos.Entidades.vida.Life;
import com.mygdx.game.gameItems.Contenedor;
import com.mygdx.game.simulation.Alma.Soul;

import java.util.ArrayList;

public class NPC extends Life {

    protected static Model FemaleModel = new ObjLoader().loadModel(Gdx.files.internal("data/img/3DModels/human/humanFemale.obj"));
    protected static Model MaleModel = new ObjLoader().loadModel(Gdx.files.internal("data/img/3DModels/human/humanMale.obj"));

    protected static Model FemaleModelAnim = new G3dModelLoader(new UBJsonReader()).loadModel(Gdx.files.getFileHandle("data/img/3DModels/human/animated/humanFemaleEvolved5.g3db", Files.FileType.Internal));

    protected static Model PhoenixModel = new ObjLoader().loadModel(Gdx.files.internal("data/img/3DModels/DemoModels/fanservice2.obj"));

    protected Texture faceSet;

    protected final String name;
    //-1 hombre, 0 neutro, 1 Mujer
    private byte sex;

    protected byte hairColor;
    protected byte hairStyle;
    protected byte skinColor;

    private static byte Counter = 1;
    private static byte Turn = 1;
    private final byte Grouped;

    protected static Texture skin = new Texture(Gdx.files.internal("data/img/Sprites/monsubito.png"));
    protected static Texture animatedSkin = new Texture(Gdx.files.internal("data/img/Sprites/MaleSprite.png"));
    private static Texture ClamsySkin = new Texture(Gdx.files.internal("data/img/Sprites/Clamsy.png"));

    private Soul soul;
    protected Contenedor Inventario;
    private AnimationController controller;

    private String path = null;

    protected int angle;

    public NPC(int x, int y, int z, String name) {
        super(x, y, z, (byte) 0);
        this.sex = 1;
        this.name = name;

        this.width = 40;
        this.height = 20;

        this.soul = new Soul(this);
        this.Inventario = new Contenedor();
        this.angle = 0;
        switch (name) {
            case "Irisciel":
                this.setTexture(new Texture(Gdx.files.internal("data/img/Sprites/Ciel.png")));
                this.setDrawSize(128, 128);
                this.width = 32;
                this.deep = 128;
                this.height = 32;
                break;
            case "Zombie":
            case "YO":
            case "Phoenix":
                this.sex = 0;
                this.setTexture(skin);
                this.setDrawSize(64, 64);
                break;
            case "Clamsy":
                this.sex = -1;
                this.setTexture(ClamsySkin);
                this.setDrawSize(64, 64);
                break;
        }
        this.Grouped = Counter;
        Counter *= -1;
    }

    public NPC(String name) {
         this(0,0,0,name);
    }

    @Override
    public void actual(int X, int Y) {
        this.setPosiD(X * (32 * 64) + this.coord.x + this.width / 2 - this.getDrawWidth() / 2, Y * (32 * 64) + this.coord.y + this.coord.z / 2);
        if (this.mod != null) {
            //this.mod.transform.translate(this.coord.x/64 + X * 32,0,(31 - this.coord.y/64) + ((2 - Y) * 32));
            //System.out.println("COordenadas de entidad " + this.coord.toString());

            Vector3 pos = new Vector3(this.coord.x / 64 + X * 32 + .5f, this.coord.z / 64 + .5f, (31 - this.coord.y / 64) + ((2 - Y) * 32) + .5f);
            Quaternion quaternion = new Quaternion(0, 0, 0, 0);

            this.mod.transform.set(pos, quaternion);
            //this.mod.transform.rotate(Vector3.Y, (float) Math.toRadians(this.angle += 3));
            if (this.name.equals("Irisciel")) {
                this.mod.transform.scale(.01f, .01f, .01f);
            }
            this.controller.update(Gdx.graphics.getDeltaTime());
        }
    }

    private void loadFaceSet() {
        this.faceSet = new Texture(Gdx.files.internal("data/img/faceSets/Yuriko.png"));
    }
    public Texture getFaceSet() {
        this.loadFaceSet();
        return faceSet;
    }

    public static void TICK() {
        NPC.Turn *= -1;
    }

    public String getName() {
        return name;
    }

    @Override
    public void Soul(Chunk ch, ArrayList<Entity> entidadesOut, int Index) {
        Avatar Player = ch.getPlayer();
        ArrayList<Entity> entidades = ch.getEntities();
        Block[] input = ch.getBlocks();


        switch (this.name) {
            case "META":
                for (int i = 0; i < entidades.size(); ++i) {
                    if (i != Index && entidades.get(i) instanceof NPC
                            && ((NPC) entidades.get(i)).name.equals("Zombie")
                            && ((NPC) entidades.get(i)).coord.x == this.coord.x
                            && ((NPC) entidades.get(i)).coord.y == this.coord.y ) {
                        if(this.coord.x < 1024) {
                            this.coord.x = 1900;
                        } else {
                            this.coord.x = 128;
                        }
                        break;
                    }
                }
                break;
            case "Zombie":
                if (Turn == this.Grouped) {
                    if (Player != null) {
                        //Detecta si el jugador esta cerca, y en el mismo chunk
                        if ((this.coord.x - (5 << 6)) < Player.coord.x + .25f &&
                                (this.coord.x - (5 << 6)) + (10 << 6) > Player.coord.x &&
                                (this.coord.y - (5 << 6)) < Player.coord.y + .25f &&
                                (this.coord.y - (5 << 6)) + (10 << 6) > Player.coord.y) {
                            //Mirar al protagonista, rotando una
                            //this.rotation(Player.coord,.2f);
                            System.out.println("estamos a unos 5 bloques");
                        }
                        for (int i = 0; i < entidades.size(); ++i) {
                            if (i != Index && entidades.get(i).colision(this)) {
                                if (entidades.get(i) instanceof Projectile) {
                                    ((Projectile) entidades.get(i)).Choca();
                                    System.out.println("Zombie life = " + this.actualLife);
                                }
                                break;
                            }
                        }

                    } else { //Testeo de la inteligencia artificial
                        NPC objView = null;//En el futuro será una coordenada
                        int dist = 10;
                        if (entidades.size() > 1 && (this.path == null || this.path.equals(""))) {
                            if (ch.getPlayer() != null) {
                                objView = ch.getPlayer();
                            } else {
                                for (int i = 0; i < entidades.size(); ++i) {
                                    if (i != Index && entidades.get(i) instanceof NPC && ((NPC) entidades.get(i)).name.equals("META")) {
                                        System.out.println("Existe Meta");
                                        objView = (NPC) entidades.get(i);
                                        this.path = ch.aStar(this.coord, entidades.get(i).coord, dist);
                                        System.out.println("He encontrado una ruta");
                                        break;
                                    }
                                }
                            }

                            if (objView == null) {
                                this.path = "BLOCKED";
                            }
                        }

                        if (path != null && !path.equals("") && !path.equals("BLOCKED")) {
                            String[] paso = this.path.split(",");
                            switch (paso[0]) {
                                case "+x":
                                    this.coord.x += dist;
                                    break;
                                case "+y":
                                    this.coord.y += dist;
                                    break;
                                case "-x":
                                    this.coord.x -= dist;
                                    break;
                                case "-y":
                                    this.coord.y -= dist;
                                    break;
                                case "":
                                    break;
                            }

                            if (paso.length > 1) {
                                String a = "";
                                for (int i = 1; i < paso.length - 1; ++i) {
                                    a = a.concat(paso[i] + ",");
                                }
                                this.path = a.concat(paso[paso.length - 1]);
                            } else {
                                this.path = "";
                            }
                        }
                    }
                }
                break;
            case "Clamsy":
                if (Turn == this.Grouped && Player != null) {
                    if (Player.coord.x > this.coord.x + (1 << 6) && Player.coord.x < this.coord.x + (10 << 6)) {
                        ++this.coord.x;
                        for (int i = 0; i < entidades.size(); ++i) {
                            if (i != Index && entidades.get(i).rectify(this, (byte) 1)) {
                                if (entidades.get(i) instanceof Projectile) {
                                    ((Projectile) entidades.get(i)).Choca();
                                }
                                break;
                            }
                        }
                        for (Block anInput : input) {
                            if (anInput != null && anInput.rectify(this, (byte) 1)) {
                                break;
                            }
                        }
                    } else if (Player.coord.x < this.coord.x - (1 << 6) && Player.coord.x > this.coord.x - (10 << 6)) {
                        --this.coord.x;
                        for (int i = 0; i < entidades.size(); ++i) {
                            if (i != Index && entidades.get(i).rectify(this, (byte) 3)) {
                                if (entidades.get(i) instanceof Projectile) {
                                    ((Projectile) entidades.get(i)).Choca();
                                }
                                break;
                            }
                        }
                        for (Block anInput : input) {
                            if (anInput != null && anInput.rectify(this, (byte) 3)) {
                                break;
                            }
                        }
                    }
                    if (Player.coord.y > this.coord.y + (1 << 6) && Player.coord.y < this.coord.y + (10 << 6)) {
                        ++this.coord.y;
                        for (int i = 0; i < entidades.size(); ++i) {
                            if (i != Index && entidades.get(i).rectify(this, (byte) 0)) {
                                if (entidades.get(i) instanceof Projectile) {
                                    ((Projectile) entidades.get(i)).Choca();
                                }
                                break;
                            }
                        }
                        for (Block anInput : input) {
                            if (anInput != null && anInput.rectify(this, (byte) 0)) {
                                break;
                            }
                        }
                    } else if (Player.coord.y < this.coord.y - (1 << 6) && Player.coord.y > this.coord.y - (10 << 6)) {
                        --this.coord.y;
                        for (int i = 0; i < entidades.size(); ++i) {
                            if (i != Index && entidades.get(i).rectify(this, (byte) 2)) {
                                if (entidades.get(i) instanceof Projectile) {
                                    ((Projectile) entidades.get(i)).Choca();
                                }
                                break;
                            }
                        }

                        for (Block anInput : input) {
                            if (anInput != null && anInput.rectify(this, (byte) 2)) {
                                break;
                            }
                        }
                        for(int i = ((int)this.coord.x / 64 + (31 - ((int)(this.coord.y + this.height) / 64)) * 32 + ((int)this.coord.z / 64) * 32 * 32); i < ((int)(this.coord.x + this.width) / 64 + (31 - ((int)(this.coord.y + this.height)/ 64)) * 32 + ((int)(this.coord.z + this.deep) / 64) * 32 * 32 ); ++i) {
                            if (input[i] != null && input[i].rectify(this, (byte) 2)) {
                                break;
                            }
                        }
                    }
                }
                break;
            case "Irisciel":
                if (Player != null && Player.Mark && Player.TalkWith == null
                        && this.coord.x + this.width / 2 > Player.coord.x
                        && Player.coord.x + Player.width > this.coord.x + this.width / 2
                        && this.coord.y >= Player.coord.y + Player.height
                        && this.coord.y - this.height < Player.coord.y + Player.height) {
                    Player.TalkWith = this;
                    this.controller.setAnimation("Armature|Balloon",-1, 1f, null);
                }
                break;
            case "Phoenix":
                if(Player != null && Math.random() < 0.5) {
                    Particulas a = new Particulas((byte)1);
                    a.coord.x = this.coord.x + this.width/2;
                    a.coord.y = this.coord.y + 96;
                    a.coord.z = this.coord.z;

                    Particulas b = new Particulas((byte)1);
                    b.coord.x = a.coord.x;
                    b.coord.y = a.coord.y;
                    b.coord.z = a.coord.z;

                    b.getForce().x = -a.getForce().x;
                    b.getForce().y = -a.getForce().y;
                    b.getVel().x = -a.getVel().x;
                    b.getVel().y = -a.getVel().y;

                    entidadesOut.add(a);
                    entidadesOut.add(b);
                }
                break;
        }

    }

    public void Talk(Avatar Player) {
        if (this.soul.getMsg().equals("<END>")) {
            Player.ConversMSG = "";
            Player.freeMove = true;
            Player.TalkWith = null;
            this.controller.setAnimation("Armature|BEND",-1, 1f, null);
        } else {
            Player.ConversMSG = this.soul.getMsg();
            if (this.soul.getEmocion() == -1) {
                Player.options = this.soul.getOptions();
                this.soul.asked();
            } else if (this.soul.getEmocion() == -2) {
                if (Player.Option != -1) {
                    Player.options = null;
                    this.soul.resetEmotion();
                    this.soul.selectOption(Player.Option);
                    Player.ConversMSG = this.soul.getMsg();
                    this.soul.incrementState();
                }
            } else {
                this.soul.incrementState();
            }
            Player.freeMove = false;
        }
    }

    public int getAngle() {
        return this.angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public void incrAngle(int incr) {
        this.angle += incr;
    }

    public void adjustAngle() {
        while (this.angle < 0 || this.angle >= 360) {
            if (this.angle < 0) {
                this.angle += 360;
            } else {
                this.angle -= 360;
            }
        }
    }

    public float antX() {
        return this.coord.x - (float) Math.cos(Math.toRadians(angle + 180));
    }

    @Override
    public void loadModel(int x, int y) {
        switch (this.name) {
            case "Irisciel":
                this.mod = new ModelInstance(NPC.FemaleModelAnim);
                this.mod.transform.translate(this.coord.x / 64 + x * 32, this.coord.z / 64 + .5f, (31 - this.coord.y / 64) + ((2 - y) * 32));
                this.mod.materials.get(0).set(TextureAttribute.createDiffuse(new Texture(Gdx.files.internal("data/img/3dTexture/cespedSimple.png"))));
                this.controller = new AnimationController(this.mod);
                System.out.println("Numero animaciones: " + NPC.FemaleModelAnim.animations.get(2).id);
                this.controller.setAnimation("Armature|BEND", -1, 1f, null);
                break;

            case "Phoenix":
                this.mod = new ModelInstance(NPC.PhoenixModel);
                this.mod.transform.translate(this.coord.x / 64 + x * 32, this.coord.z / 64 + .5f, (31 - this.coord.y / 64) + ((2 - y) * 32));
                //this.mod.materials.get(0).set(TextureAttribute.createDiffuse(new Texture(Gdx.files.internal("data/img/3dTexture/cespedSimple.png"))));
                this.controller = new AnimationController(this.mod);
                break;

            default:
                this.mod = new ModelInstance(NPC.MaleModel);
                this.mod.transform.translate(this.coord.x / 64 + x * 32, this.coord.z / 64 + .5f, (31 - this.coord.y / 64) + ((2 - y) * 32));
                this.mod.materials.get(0).set(TextureAttribute.createDiffuse(new Texture(Gdx.files.internal("data/img/3dTexture/cespedSimple.png"))));
                this.controller = new AnimationController(this.mod);
                break;
        }
    }

    public void jump(float force) {
        if(this.vel.z == 0) {
            this.vel.z += force;
        }
    }

    public void move(int angle, byte dist, boolean plus) {
        this.coord.x -= Math.cos(Math.toRadians(angle)) * dist * (plus ? 2 : 1);
        this.coord.y += Math.sin(Math.toRadians(-angle)) * dist * (plus ? 2 : 1);
    }

    @Override
    public ModelInstance getModelInstance() {
        return this.mod;
    }
}