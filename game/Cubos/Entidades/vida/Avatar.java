package com.mygdx.game.Cubos.Entidades.vida;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Cubos.Entidades.vida.NPC.NPC;
import com.mygdx.game.gameItems.*;

public class Avatar extends NPC {

    protected static Model AvatarModel = new ObjLoader().loadModel(Gdx.files.internal("data/img/3DModels/human/avatar.obj"));
    public boolean[] advanceIntent = new boolean[5];
    public boolean Mark;
    public String ConversMSG;
    public String[] options;
    public NPC TalkWith;
    public byte Option;

    private int countAnim = 0;

    public boolean movi = false;
    public int stateAnim = 0;
    private int delay = 4;
    private boolean incr = false;
    public boolean angleMove;

    private boolean simplex = true;
    //private Texture t = (new TextureRegion(dashSprite[0],)).getTexture();
    private TextureRegion[][] tileset = TextureRegion.split(animatedSkin, 32, 48);
    private Equipamiento equipo;
    private float enti;

    //48 + 48 + 48
    public Avatar() {
        super(0, 0, 64, "YO");
        //this.sprite = new Sprite(NPC.skin);
        this.setXdraw(-16);
        //this.setTexture(this.Tabla2[0]);
        this.advanceIntent[0] = false;
        this.advanceIntent[1] = false;
        this.advanceIntent[2] = false;
        this.advanceIntent[3] = false;
        this.advanceIntent[4] = false;
        this.angleMove = false;

        this.Mark = false;
        this.ConversMSG = "";
        this.options = null;
        this.TalkWith = null;
        this.Option = -1;

        this.setDrawSize(32, 48);

        this.equipo = new Equipamiento();

        this.Inventario.canAdd(new Alimentos("Manzana"),10);
        this.Inventario.canAdd(new Equipable("Casco"),1);
        this.Inventario.canAdd(new Items("Anaconda"),10);
        this.Inventario.canAdd(new Items("Pokemon"),71);

        this.angle = -90;
        this.enti = -90;
        System.out.println("Mi altura: " + this.deep);

        //this.mod = new ModelInstance(Avatar.AvatarModel);
        this.mod = new ModelInstance(NPC.MaleModel);
    }

    @Override
    public void loadModel(int x, int y) {
        //this.mod = new ModelInstance(Avatar.AvatarModel);
        this.mod = new ModelInstance(NPC.MaleModel);
        this.mod.transform.translate(this.coord.x / 64 + x * 32, this.coord.z / 64, (31 - this.coord.y / 64) + ((2 - y) * 32));
        this.mod.materials.get(0).set(TextureAttribute.createDiffuse(new Texture(Gdx.files.internal("data/img/3dTexture/trans.png"))));
        this.mod.materials.get(0).set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));

    }

    @Override
    public void actual(int X, int Y) {
        this.setPosiD(X * (32 * 64) + this.coord.x + this.width / 2 - this.getDrawWidth() / 2, Y * (32 * 64) + this.coord.y + this.coord.z / 2);
        if (this.mod != null) {
            Vector3 pos = new Vector3(this.coord.x / 64 + X * 32 + (((float)40)/64)/2 + .5f, this.coord.z / 64 + .5f, (31 - this.coord.y / 64) + ((2 - Y) * 32) - (((float)20)/64)/2 + .5f);
            Quaternion quaternion = new Quaternion(0, 0, 0, 0);

            this.mod.transform.set(pos, quaternion);
            int variation = 0;
            if(Gdx.input.isKeyPressed(Input.Keys.W)) {
                if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                    variation += 45;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                    variation -= 45;
                }
            }
            this.mod.transform.rotate(Vector3.Y, this.angle - 90 + variation);
        }
    }


    public void draw(SpriteBatch b) {
        switch (this.dir) {
            case 0:
                b.draw(this.tileset[3][this.stateAnim], this.getXdraw(), this.getYdraw(), getDrawWidth(), getDrawHeight());
                break;
            case 1:
                b.draw(this.tileset[2][this.stateAnim], this.getXdraw(), this.getYdraw(), getDrawWidth(), getDrawHeight());
                break;
            case 2:
                b.draw(this.tileset[0][this.stateAnim], this.getXdraw(), this.getYdraw(), getDrawWidth(), getDrawHeight());
                break;
            case 3:
                b.draw(this.tileset[1][this.stateAnim], this.getXdraw(), this.getYdraw(), getDrawWidth(), getDrawHeight());
                break;
        }
    }

    public Contenedor getInventario() {
        return this.Inventario;
    }

    public void isQuiet() {
        if (!(this.advanceIntent[0] || this.advanceIntent[1] || this.advanceIntent[2] || this.advanceIntent[3])) {
            this.movi = false;
            this.delay = 4;
            this.stateAnim = 0;
        }
        this.incr = false;
    }

    public void FreeCamMove(boolean orient){
        if(orient){
            this.advanceIntent[0] = true;
        } else {
            this.advanceIntent[2] = true;
        }
        this.angleMove = true;
    }

    public void move(byte dist, byte dir, boolean plus) {
        //System.out.println("frames disponibles " + countAnim);
        if (this.freeMove) {
            this.dir = dir;
            switch (this.dir) {
                case 0:
                    //countAnim
                    this.coord.y += dist * (plus ? 2 : 1);
                    if (this.movi && !incr) {
                        if (this.delay > 0) {
                            --this.delay;
                            incr = true;
                        } else {
                            ++this.stateAnim;
                            this.delay = 4;
                            if (stateAnim > 3) {
                                this.stateAnim = 0;
                            }
                        }
                    } else {
                        this.movi = true;
                    }
                    //this.setTexture(this.Tabla0[this.stateAnim]);
                    break;
                case 1:
                    this.coord.x += dist * (plus ? 2 : 1);
                    if (this.movi && !incr) {
                        if (this.delay > 0) {
                            --this.delay;
                            incr = true;
                        } else {
                            ++this.stateAnim;
                            this.delay = 4;
                            if (stateAnim > 3) {
                                this.stateAnim = 0;
                            }
                        }
                    } else {
                        this.movi = true;
                    }
                    //         this.setTexture(this.Tabla1[this.stateAnim]);
                    break;
                case 2:
                    this.coord.y -= dist * (plus ? 2 : 1);
                    if (this.movi && !incr) {
                        if (this.delay > 0) {
                            --this.delay;
                            incr = true;
                        } else {
                            ++this.stateAnim;
                            this.delay = 4;
                            if (stateAnim > 3) {
                                this.stateAnim = 0;
                            }
                        }
                    } else {
                        this.movi = true;
                    }
                    //       this.setTexture(this.Tabla2[this.stateAnim]);
                    break;
                case 3:
                    this.coord.x -= dist * (plus ? 2 : 1);
                    if (this.movi && !incr) {
                        if (this.delay > 0) {
                            --this.delay;
                            this.incr = true;
                        } else {
                            ++this.stateAnim;
                            this.delay = 4;
                            if (stateAnim > 3) {
                                this.stateAnim = 0;
                            }
                        }
                    } else {
                        this.movi = true;
                    }
                    //     this.setTexture(this.Tabla3[this.stateAnim]);
                    break;
            }

        }
    }

    public void setSimplex(boolean b) { this.simplex = b;   }

    public float getFocusX() {
        return this.coord.x + 16 + this.width / 2;
    }
    public float getFocusY() {
        return this.coord.y + this.coord.z / 2;
    }

    public Items equipar(Equipable in) { return this.equipo.canAdd(in);}

    @Override
    public ModelInstance getModelInstance() { return this.mod; }

    public void drawEquip(SpriteBatch batch){ this.equipo.drawContainer(batch);}
}