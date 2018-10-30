package com.mygdx.game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlWriter;
import com.mygdx.game.Cubos.Block;
import com.mygdx.game.Cubos.Decoration;
import com.mygdx.game.Cubos.Deformed;
import com.mygdx.game.Cubos.Entidades.Entity;
import com.mygdx.game.Cubos.Entidades.bullet.Magia.Magia;
import com.mygdx.game.Cubos.Entidades.bullet.Magia.Rayo;
import com.mygdx.game.Cubos.Entidades.bullet.Projectile;
import com.mygdx.game.Cubos.Entidades.vida.Avatar;
import com.mygdx.game.Cubos.Entidades.vida.Bestia.Wild;
import com.mygdx.game.Cubos.Entidades.vida.Life;
import com.mygdx.game.Cubos.Entidades.vida.NPC.NPC;
import com.mygdx.game.Cubos.Escaleras;
import com.mygdx.game.Shaders.worldBatch;
import com.mygdx.game.simulation.Alma.Path;
import com.mygdx.game.simulation.Edification.Zona;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Chunk {

    public static Texture[] textures = null;
    public static TextureRegion[] texturesR = null;

    public String Name;
    private int x;
    private int y;
    public static int VIEWDIST = 128;
    private Avatar player;
    int contadorConsole = 120;
    private BoundingBox box;
    //Indicaddores de carga de entidades en chunks vecinos
    public boolean tDown, inteD;
    public boolean tUp, inteU;
    public boolean tLeft, inteL;
    public boolean tRight, inteR;

    private boolean assignedR;

    public ArrayList<Entity> intentUp, intentDown, intentLeft, intentRight;

    public boolean loaded;
    private ArrayList<Zona> en;

    private boolean drawedP;
    private Block[] blocks;
    private boolean d3Print = false;
    //Chunks 32 * 32 * 32
    //private Entity[] entities;
    //Conjunto de entidades

    private ArrayList<Entity> entities;
    private ArrayList<Entity> Postentities;

    public Chunk() {
        this.x = 0;
        this.y = 0;

        this.Name = "Base1";
        this.loaded = true;

        this.blocks = new Block[32 * 32 * 32];
        //this.entities = new Entity[0];
        this.entities = new ArrayList<Entity>();
        this.Postentities = new ArrayList<Entity>();

        this.intentUp = new ArrayList<Entity>();
        this.intentDown = new ArrayList<Entity>();
        this.intentLeft = new ArrayList<Entity>();
        this.intentRight = new ArrayList<Entity>();
        this.assignedR = false;

        //Construccion de un suelo, el resto se queda vacio a la espera de llenarse
        for (int i = 0; i < 32; ++i) {
            for (int j = 0; j < 32; ++j) {
                this.blocks[i + j * 32] = new Block(i * 64, 31 * 64 - j * 64, 0, (byte) 1);
            }
        }

        this.en = new ArrayList<Zona>();

        this.player = null;
        this.drawedP = false;

        this.tDown = false;
        this.tUp = false;
        this.tLeft = false;
        this.tRight = false;

        this.inteD = false;
        this.inteL = false;
        this.inteR = false;
        this.inteU = false;
        this.realign();
    }

    public Chunk(int x, int y) {
        this.x = x;
        this.y = y;
        this.Name = "X" + (x << 5) + "Y" + (y << 5);
        this.loaded = true;

        this.blocks = new Block[32 * 32 * 32];
        this.entities = new ArrayList<Entity>();
        this.Postentities = new ArrayList<Entity>();

        this.intentUp = new ArrayList<Entity>();
        this.intentDown = new ArrayList<Entity>();
        this.intentLeft = new ArrayList<Entity>();
        this.intentRight = new ArrayList<Entity>();

        //Construccion de un suelo, el resto se queda vacio a la espera de llenarse
        for (int i = 0; i < 32; ++i) {
            for (int j = 0; j < 32; ++j) {
                this.blocks[i + j * 32] = new Block(i * 64, 31 * 64 - j * 64, 0, (byte) 1);
            }
        }
        this.assignedR = false;
        this.en = new ArrayList<Zona>();
        this.drawedP = false;
        this.player = null;
        this.realign();
    }

    public Chunk(String seed, int x, int y) {
        this(x, y);
        for (int i = 0; i < this.blocks.length; ++i) {
            if (this.blocks[i] != null) {
                this.blocks[i].setType((byte) 0);
            }
        }

        int auxiliar = seed.hashCode();
        int xA1 = auxiliar << 2;
        int yA1 = auxiliar << 4;
        int zA1 = auxiliar << 8;
        this.realign();
    }

    //Carga desde un HTML
    public Chunk (FileHandle input){
        this.loaded = true;
        this.entities = new ArrayList<Entity>();
        this.intentUp = new ArrayList<Entity>();
        this.intentDown = new ArrayList<Entity>();
        this.intentLeft = new ArrayList<Entity>();
        this.intentRight = new ArrayList<Entity>();

        System.out.println("Extensión: " + input.extension());
        if(input.extension().equals("xml")) {
            XmlReader reader = new XmlReader();
            XmlReader.Element root;
            try {
                root = reader.parse(input);
                this.x = Integer.parseInt(root.getAttribute("X"));
                this.y = Integer.parseInt(root.getAttribute("Y"));
                this.Name = "X" + (this.x << 5) + "Y" + (this.y << 5);

                Array<XmlReader.Element> Blocks = root.getChildByName("Parte").getChildrenByName("B");
                this.blocks = new Block[32 * 32 * 32];
                this.Postentities = new ArrayList<Entity>();
                int x, y, z;
                byte T, O;
                for (XmlReader.Element block : Blocks) {
                    x = Integer.parseInt(block.getAttribute("x"));
                    y = Integer.parseInt(block.getAttribute("y"));
                    z = Integer.parseInt(block.getAttribute("z"));

                    T = Byte.parseByte(block.getAttribute("T"));
                    O = Byte.parseByte(block.getAttribute("O"));

                    //System.out.println("Type = " + T);
                    //System.out.println("Leidos: " + x + ", " + y +", " + z);
                    this.blocks[x + 32 * (31 - y) + 32 * 32 * z] = new Block(x * 64, y * 64, z * 64, T);
                    this.blocks[x + 32 * (31 - y) + 32 * 32 * z].setOrient(O);
                }

                this.assignedR = false;
                this.en = new ArrayList<Zona>();
                this.drawedP = false;
                this.player = null;
                this.realign();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(input.extension().equals("tmx")) {
            XmlReader reader = new XmlReader();
            XmlReader.Element root;
            this.Name = input.name();
            this.x = 0;
            this.y = 0;
            this.blocks = new Block[32 * 32 * 32];
            this.Postentities = new ArrayList<Entity>();
            this.assignedR = false;
            this.en = new ArrayList<Zona>();
            this.drawedP = false;
            this.player = null;
            int x,y,z = 0;
            try {
                root = reader.parse(input);
                Array<XmlReader.Element> layers = root.getChildrenByName("layer");
                XmlReader.Element data;
                for (XmlReader.Element layer : layers) {
                    data = layer.getChildByName("data");
                    String[] nivel = data.getText().split(",");
                    for(int i = 0; i < nivel.length; ++i) {
                        int valor = Integer.parseInt(nivel[i].replaceAll("\\W", ""));
                        if(valor != 0 && valor < 128) {
                            x = i % 32;
                            y = i / 32;
                            this.blocks[x + 32 * (31 - y) + 32 * 32 * z] = new Block(x * 64, 31 * 64  - y * 64, z * 64, (byte)valor);
                        }
                    }
                    ++z;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.realign();
        }
    }

    private void realign() {
        //Reposiciona los sprites cuando cambia la posición del chunk o se carga
        //this.x * 32 * 64; Es multiplicat per 2048 es a dir (this.x<<11)
        for (int i = 0; i < 32768; ++i) {
            if (this.blocks[i] != null) {
                //this.blocks[i].sprite.setPosition((this.x << 11) + this.blocks[i].getXP(), (this.y << 11) + this.blocks[i].getYP());
                this.blocks[i].setPosiD((this.x << 11) + this.blocks[i].getXP(), (this.y << 11) + this.blocks[i].getYP());
            }
        }
        for (Entity i : this.entities) {
            i.actual(this.x, this.y);
        }

        this.box = new BoundingBox(new Vector3(this.x * 32 - 1, -1, (2 - this.y) * 32 - 1), new Vector3((this.x + 1) * 32 + 1, 32 + 1, (2 - this.y + 1) * 32 + 1));
    }

    public void addZona(Zona z) {
        this.en.add(z);
    }

    public void tryToAddEntity(Entity ent, int x, int y, int z) {
        ent.coord.x = x << 6;
        ent.coord.y = (31 - y) << 6;
        ent.coord.z = z << 6;

        this.entities.add(ent);

        this.realign();
    }

    public void tryToAddEntity(Entity ent) {
        this.entities.add(ent);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
        this.realign();
    }

    public void setY(int y) {
        this.y = y;
        this.realign();
    }

    public void setPlayer(Avatar player) {
        this.player = player;
    }

    public Avatar getPlayer() {
        return this.player;
    }

    public Block[] getBlocks() { return blocks; }

    public void teleportSpawn() {
        this.player.coord.x = 64;
        this.player.coord.y = 64;
    }

    public boolean isD3Print() { return d3Print; }

    public void draw3DChunk(PerspectiveCamera camera, boolean ahorro) {
        if (!this.d3Print) {
            System.out.println("he generado los cubos y soy " + this.Name);
            this.d3Print = true;
            for (int i = 0; i < this.blocks.length; ++i) {
                if (this.blocks[i] != null) {
                    this.blocks[i].build3DPart(this.x, this.y, 0);
                }
            }

            for (int k = 0; k < 32; ++k) {
                for (int i = 31; i >= 0; --i) {
                    for (int j = 0; j < 32; ++j) {
                        if (this.blocks[k * 32 * 32 + j * 32 + i] != null
                                && !((i + 1 < 32 && this.blocks[k * 32 * 32 + j * 32 + i + 1] != null
                                && i - 1 > 0 && this.blocks[k * 32 * 32 + j * 32 + i - 1] != null
                                && j + 1 < 32 && this.blocks[k * 32 * 32 + (j + 1) * 32 + i] != null
                                && j - 1 > 0 && this.blocks[k * 32 * 32 + (j - 1) * 32 + i] != null
                                && k + 1 < 32 && this.blocks[(k + 1) * 32 * 32 + j * 32 + i] != null
                                && k - 1 > 0 && this.blocks[(k - 1) * 32 * 32 + j * 32 + i] != null))) {
                            this.blocks[k * 32 * 32 + j * 32 + i].toRender();
                        }
                    }
                }
            }
            this.assignedR = true;
            if(this.player != null) {
                this.player.loadModel(this.x, this.y);
            }
        }

        //instance.transform
        Frustum f = camera.frustum;
        Vector3 auxiliar = new Vector3();
        //If direcciones, para organizar y aplicar

        if(ahorro) {
            for (int k = 0; k < 32; ++k) {
                for (int i = 0; i < 32; ++i) {
                    for (int j = 0; j < 32; ++j) {
                        if (this.blocks[k * 32 * 32 + j * 32 + i] != null
                                && this.blocks[k * 32 * 32 + j * 32 + i].rendered
                                && this.blocks[k * 32 * 32 + j * 32 + i].isVisible(f)
                                && this.blocks[k * 32 * 32 + j * 32 + i].getModelInstance().transform.getTranslation(auxiliar).dst(camera.position) < VIEWDIST
                                ) {
                            worldBatch.renderG(this.blocks[k * 32 * 32 + j * 32 + i].getModelInstance());
                        }
                    }
                }
            }
        } else {
            for (int k = 0; k < 32; ++k) {
                for (int i = 0; i < 32; ++i) {
                    for (int j = 0; j < 32; ++j) {
                        if (this.blocks[k * 32 * 32 + j * 32 + i] != null){
                            worldBatch.renderG(this.blocks[k * 32 * 32 + j * 32 + i].getModelInstance());
                        }
                    }
                }
            }
        }


        if(this.player != null) {
            worldBatch.renderG(player.getModelInstance());
        }

        for (Entity entidad: this.entities) {
            if(entidad.is3DRenderable()) {
                entidad.loadModel(this.x, this.y);
            }

            worldBatch.renderG(entidad.getModelInstance());
            if(entidad instanceof Magia) {
                worldBatch.render(((Magia)entidad).getParticulasModel());
            }
        }
    }

    public boolean isRendereable(Frustum frustum) {
        return frustum.boundsInFrustum(this.box);
    }

    public void drawChunk(SpriteBatch b, Block mouse) {

        boolean drawedI = false, In = false;
        if (this.player != null && this.player.visible) {
            for (int c = 0; !In && c < this.en.size(); ++c) {
                if (this.en.get(c).playerIn(this.player)) {
                    In = true;
                    this.drawZone(b, mouse, this.en.get(c));
                    this.drawedP = false;
                }
            }

            if (!In) {
                int j = 0;
                for (int i = 0; i < this.blocks.length; ++i) {
                    if (this.blocks[i] != null) {
                        if (!this.drawedP) {
                            if (((this.player.coord.z < this.blocks[i].coord.z)
                                    || (this.player.coord.y > this.blocks[i].coord.y && this.player.coord.z == this.blocks[i].coord.z))) {
                                //x ->  i % 32 * 64 |@| y -> i / 32 *64 |@| z -> i / 32*32

                                while (j < this.entities.size()
                                        && ((this.entities.get(j).coord.z < this.player.coord.z)
                                        || (this.entities.get(j).coord.y > this.player.coord.y
                                        && this.entities.get(j).coord.z == this.player.coord.z))) {

                                    this.entities.get(j).draw(b);
                                    if (this.entities.get(j) instanceof Magia || this.entities.get(j) instanceof Rayo) {
                                        ((Magia) this.entities.get(j)).nextEstate();
                                    }
                                    ++j;
                                }

                                this.player.actual(this.x, this.y);
                                this.player.draw(b);
                                this.drawedP = true;
                            }
                        }
                        if (j < this.entities.size()
                                && ((this.entities.get(j).coord.z < this.blocks[i].coord.z)
                                || (this.entities.get(j).coord.y > this.blocks[i].coord.y
                                && this.entities.get(j).coord.z == this.blocks[i].coord.z))) {
                            this.entities.get(j).draw(b);
                            if (this.entities.get(j) instanceof Magia || this.entities.get(j) instanceof Rayo) {
                                ((Magia) this.entities.get(j)).nextEstate();
                            }
                            ++j;
                        }
                        if (mouse != null && !drawedI && ((mouse.coord.z < this.blocks[i].coord.z) || (mouse.coord.z < this.blocks[i].coord.z && mouse.coord.z == this.blocks[i].coord.z))) {
                            mouse.draw(b);
                            drawedI = true;
                        }
                        //this.blocks[i].sprite.draw(b);
                        this.blocks[i].draw(b);
                    }
                }

                for (int i = j; j < this.entities.size(); ++j) {
                    this.entities.get(i).draw(b);
                }

                if (!this.drawedP) {
                    this.player.actual(this.x, this.y);
                    this.player.draw(b);
                }
                if (mouse != null) {
                    mouse.draw(b);
                }
                this.drawedP = false;
            }

        } else {
            drawEmptyChunk(b, mouse);
        }
    }

    private void drawZone(SpriteBatch b, Block mouse, Zona z) {
        int index;
        int j = 0;
        for (int k = 0; k < z.deep; ++k) {
            for (int J = 0; J < z.height; ++J) {
                for (int i = 0; i < z.width; ++i) {
                    index = (k + (int) z.coord.z) * 32 * 32 + (J + 32 - ((int) z.coord.y + z.height)) * 32 + (i + (int) z.coord.x);
                    if (this.blocks[index] != null) {
                        if (!this.drawedP && ((this.player.coord.z < this.blocks[index].coord.z)
                                || (this.player.coord.y > this.blocks[index].coord.y && this.player.coord.z == this.blocks[index].coord.z))) {
                            //x ->  i % 32 * 64 |@| y -> i / 32 *64 |@| z -> i / 32*32
                            this.player.actual(this.x, this.y);
                            this.player.draw(b);
                            this.drawedP = true;
                        }

                        if (j < this.entities.size()
                                && ((this.entities.get(j).coord.z < this.blocks[index].coord.z)
                                || (this.entities.get(j).coord.y > this.blocks[index].coord.y
                                && this.entities.get(j).coord.z == this.blocks[index].coord.z))) {
                            this.entities.get(j).draw(b);
                            if (this.entities.get(j) instanceof Magia || this.entities.get(j) instanceof Rayo) {
                                ((Magia) this.entities.get(j)).nextEstate();
                            }
                            ++j;
                        }
                        this.blocks[index].draw(b);
                    }
                }
            }
        }
    }

    private void drawEmptyChunk(SpriteBatch b, Block mouse) {
        boolean drawedI = false;
        int j = 0;
        for (int i = 0; i < this.blocks.length; ++i) {
            if (this.blocks[i] != null) {
                if (mouse != null && !drawedI && ((mouse.coord.z < this.blocks[i].coord.z) || (mouse.coord.y > this.blocks[i].coord.y && mouse.coord.z == this.blocks[i].coord.z))) {
                    mouse.draw(b);
                    drawedI = true;
                }
                if (j < this.entities.size() && ((this.entities.get(j).coord.z < this.blocks[i].coord.z) || (this.entities.get(j).coord.y > this.blocks[i].coord.y && this.entities.get(j).coord.z == this.blocks[i].coord.z))) {
                    this.entities.get(j++).draw(b);
                }
                this.blocks[i].draw(b);
            }
        }
        for (Entity i : this.entities) {
            i.draw(b);
        }
        if (mouse != null && !drawedI) {
            mouse.draw(b);
        }
    }

    public boolean tryToAddEntityType(int x, int y, int z, byte code, byte[] args) {
        switch (code) {
            case 0:
                return true;
        }

        return false;
    }

    public boolean externalColition(Entity b, int dir) {
        switch (dir) {//Forma Ineficiente de hacerlo
            case 0: //Up
                for (int i = 0; i < this.blocks.length; ++i) {
                    if (this.blocks[i] != null && b != null) {
                        if (this.blocks[i].colision(b.coord.x, (b.coord.y + 2) - 2048, b.coord.z, b.getWidth(), b.getHeight(), b.getDeep())) {
                            return true;
                        }
                    }
                }
                break;
            case 1: //Right
                for (int i = 0; i < this.blocks.length; ++i) {
                    if (this.blocks[i] != null && b != null) {
                        if (this.blocks[i].colision(b.coord.x + 2 - 2048, b.coord.y, b.coord.z, b.getWidth(), b.getHeight(), b.getDeep())) {
                            return true;
                        }
                    }
                }
                break;
            case 2://Down
                for (int i = 0; i < this.blocks.length; ++i) {
                    if (this.blocks[i] != null && b != null) {
                        if (this.blocks[i].colision(b.coord.x, 2047, b.coord.z, b.getWidth(), b.getHeight(), b.getDeep())) {
                            return true;
                        }
                    }
                }
                break;
            case 3:
                for (int i = 0; i < this.blocks.length; ++i) {
                    if (this.blocks[i] != null && b != null) {
                        if (this.blocks[i].colision(2048 + b.coord.x, b.coord.y, b.coord.z, b.getWidth(), b.getHeight(), b.getDeep())) {
                            return true;
                        }
                    }
                }
                break;
        }
        return false;
    }

    public boolean tryToAdd(int x, int y, int z, byte t) {
        if (x >= 0 && x <= 31 && y >= 0 && y <= 31 && z >= 0 && z <= 31 && this.blocks[x + 32 * y + 32 * 32 * z] == null) {
            this.blocks[x + 32 * y + 32 * 32 * z] = new Block(x * 64, 31 * 64 - y * 64, z * 64, t);
            //this.blocks[i + j * 32] = new Block(x * (32*64) + i * 64, y * (32*64) + 31 * 64 - j * 64, 0,(byte)1);

            this.realign();
            return true;
        } else {
            return false;
        }
    }

    public boolean tryToAddBlock(Block b, int x, int y, int z) {
        if (x >= 0 && x <= 31 && y >= 0 && y <= 31 && z >= 0 && z <= 31 && this.blocks[x + 32 * y + 32 * 32 * z] == null) {
            b.coord.x = x * 64;
            b.coord.y = 31 * 64 - y * 64;
            b.coord.z = z * 64;
            b.realign();
            this.blocks[x + 32 * y + 32 * 32 * z] = b;
            this.realign();
            return true;
        } else {
            return false;
        }
    }
    public boolean tryToAddStructure(Block b, int x, int y, int z) {
        if (x >= 0 && x <= 31 && y >= 0 && y <= 31 && z >= 0 && z <= 31 && this.blocks[x + 32 * y + 32 * 32 * z] == null) {
            b.coord.x = x * 64;
            b.coord.y = 31 * 64 - y * 64;
            b.coord.z = z * 64;
            b.realign();

            //this.blocks[x + 32 * y + 32 * 32 * z] = b;

            for(int i = 0; i <= b.getWidth() / 64; ++i) {
                for(int j = 0; j <= b.getHeight() / 64; ++j) {//Se debe de sustituir por un bloque de computación 0
                    for(int k = 0; k <= b.getDeep() / 64; ++k) {
                        this.blocks[x + i + 32 * (y - j) + 32 * 32 * (z + k)] = b;
                    }
                }
            }

            this.realign();
            return true;
        } else {
            return false;
        }
    }

    public boolean tryToSetBlock(int x, int y, int z, byte t, byte ori) {
        if (x >= 0 && x <= 31 && y >= 0 && y <= 31 && z >= 0 && z <= 31 && this.blocks[x + 32 * y + 32 * 32 * z] != null) {
            this.blocks[x + 32 * y + 32 * 32 * z].setType(t);
            this.blocks[x + 32 * y + 32 * 32 * z].setOrient(ori);
            this.realign();
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Entity> getEntities() { return entities; }

    public float getFocusX() {
        return 2048 * this.x + this.player.getFocusX();
    }

    public float getFocusY() {
        return 2048 * this.y + this.player.getFocusY();
    }

    public void addEntity(Entity e) {
        this.entities.add(e);
    }

    public void phisics() {
        if (this.player != null) {
            this.player.isQuiet();
            if(!this.player.angleMove) {
                for (byte i = 0; i < this.player.advanceIntent.length - 1; ++i) {
                    if (this.player.advanceIntent[i]) {
                        this.player.move((byte) 2, i, this.player.advanceIntent[4]);
                        boolean chocado = false;
                        if(this.player.tangible) {
                            for (Block block : this.blocks) {
                                if (block != null && block.rectify(this.player, i)) {
                                    chocado = true;
                                    break;
                                }
                            }
                            if(!chocado) {
                                for (Entity entity : this.entities) {
                                    if (entity.rectify(this.player, i)) {
                                        break;
                                    }
                                }
                            }
                        }
                        this.player.advanceIntent[i] = false;
                    }
                }
            } else {
                if (this.player.advanceIntent[0]) {

                    if(this.player.advanceIntent[3]) {
                        this.player.setAngle(this.player.getAngle() + 45);
                    }
                    if(this.player.advanceIntent[1]) {
                        this.player.setAngle(this.player.getAngle() - 45);
                    }

                    this.player.move(this.player.getAngle(),(byte)3, this.player.advanceIntent[4]);
                    boolean chocado = false;
                    if(this.player.tangible) {
                        for(int j = ((int)this.player.coord.x / 64 + (31 - ((int)(this.player.coord.y + this.player.getHeight()) / 64)) * 32 + ((int)this.player.coord.z / 64) * 32 * 32); j < ((int)(this.player.coord.x + this.player.getWidth()) / 64 + (31 - ((int)(this.player.coord.y + this.player.getHeight())/ 64)) * 32 + ((int)(this.player.coord.z + this.player.getDeep()) / 64) * 32 * 32 ); ++j) {
                            if (this.blocks[j] != null && this.blocks[j].rectify(this.player, (byte) 3, this.player.advanceIntent[4])) {
                                chocado = true;
                                break;
                            }
                        }
                        if(!chocado) {
                            for (Entity entity : this.entities) {
                                if (entity.rectify(this.player, (byte) 3, this.player.advanceIntent[4])) {
                                    break;
                                }
                            }
                        }
                    }

                    if(this.player.advanceIntent[3]) {
                        this.player.setAngle(this.player.getAngle() - 45);
                        this.player.advanceIntent[3] = false;
                    }
                    if(this.player.advanceIntent[1]) {
                        this.player.setAngle(this.player.getAngle() + 45);
                        this.player.advanceIntent[1] = false;
                    }

                    this.player.advanceIntent[0] = false;
                } else if (this.player.advanceIntent[2]) {
                    this.player.move(this.player.getAngle() + 180);
                    this.player.advanceIntent[2] = false;

                } else if(this.player.advanceIntent[1]){
                    //this.player.move((byte) 2, (byte)1, this.player.advanceIntent[4]);

                   // this.player.move(this.player.getAngle() + 270);
                    this.player.setAngle(this.player.getAngle() + 270);

                    this.player.move(this.player.getAngle(), (byte)2, this.player.advanceIntent[4]);
                    boolean chocado = false;
                    for(int j = ((int)this.player.coord.x / 64 + (31 - ((int)(this.player.coord.y + this.player.getHeight()) / 64)) * 32 + ((int)this.player.coord.z / 64) * 32 * 32); j < ((int)(this.player.coord.x + this.player.getWidth()) / 64 + (31 - ((int)(this.player.coord.y + this.player.getHeight())/ 64)) * 32 + ((int)(this.player.coord.z + this.player.getDeep()) / 64) * 32 * 32 ); ++j) {
                        if (this.blocks[j] != null && this.blocks[j].rectify(this.player, (byte) 2, this.player.advanceIntent[4])) {
                            chocado = true;
                            break;
                        }
                    }
                    for (int j = 0; !chocado && j < this.entities.size(); ++j) {
                        if (this.entities.get(j).rectify(this.player, (byte) 2, this.player.advanceIntent[4])) {
                            break;
                        }
                    }
                    this.player.setAngle(this.player.getAngle() - 270);
                    this.player.advanceIntent[1] = false;

                } else if(this.player.advanceIntent[3]){
                    //this.player.move((byte) 2, (byte)3, this.player.advanceIntent[4]);
                    //this.player.move(this.player.getAngle() + 90);
                    this.player.setAngle(this.player.getAngle() + 90);

                    this.player.move(this.player.getAngle(), (byte)2, this.player.advanceIntent[4]);
                    boolean chocado = false;
                    for(int j = ((int)this.player.coord.x / 64 + (31 - ((int)(this.player.coord.y + this.player.getHeight()) / 64)) * 32 + ((int)this.player.coord.z / 64) * 32 * 32); j < ((int)(this.player.coord.x + this.player.getWidth()) / 64 + (31 - ((int)(this.player.coord.y + this.player.getHeight())/ 64)) * 32 + ((int)(this.player.coord.z + this.player.getDeep()) / 64) * 32 * 32 ); ++j) {
                        if (this.blocks[j] != null && this.blocks[j].rectify(this.player, (byte) 2, this.player.advanceIntent[4])) {
                            chocado = true;
                            break;
                        }
                    }
                    for (int j = 0; !chocado && j < this.entities.size(); ++j) {
                        if (this.entities.get(j).rectify(this.player, (byte) 2, this.player.advanceIntent[4])) {
                            break;
                        }
                    }
                    this.player.setAngle(this.player.getAngle() - 90);
                    this.player.advanceIntent[3] = false;
                }
            }

            if (this.player.coord.y + this.player.getWidth() >= 2048) {
                this.inteU = true;
            } else if (this.player.coord.y < 0) {
                this.inteD = true;
            }
            if (this.player.coord.x + this.player.getWidth() >= 2048) {
                this.inteR = true;
            } else if (this.player.coord.x < 0) {
                this.inteL = true;
            }

            this.player.advanceIntent[4] = false;
            this.tDown = (this.player.coord.y < 0);
            this.tLeft = (this.player.coord.x < 0);
            this.tRight = (this.player.coord.x >= 2048);
            this.tUp = (this.player.coord.y >= 2048);

            if (!this.tLeft && !this.tUp && !this.tDown && !this.tRight && !this.inteU && this.player.tangible) {
                this.player.accelerate();
                for (Block block : this.blocks) {
                    if (block != null && block.rectify(this.player, (byte) 5)) {
                        this.player.resetVelo();
                        break;
                    }
                }
                if (this.player.coord.z < 0) {
                    this.player.coord.z = 0;
                }
            }
            //Lo mismo con cada entidad
            int count = 0;
            for (Entity i : this.entities) {
                if (i != null) {
                    /*if (i.coord.z < 0) {
                        i.coord.z = 0;
                    }*/
                    if (i instanceof NPC || i instanceof Wild) {
                        if (((Life) i).getActualLife() > 0) {
                            ((Life) i).Soul(this, this.Postentities, count);
                        }
                    }else if (i instanceof Projectile) {
                        ((Projectile) i).phisics(this.blocks);
                    }
                    i.actual(this.x, this.y);
                }
                ++count;
            }


            this.entities.addAll(this.Postentities);
            this.Postentities.clear();
            //ArrayList<Entity>
            this.player.actual(this.x,this.y);

            NPC.TICK();

            for (int i = 0; i < this.entities.size(); ++i) {
                if ((((this.entities.get(i) instanceof Projectile) || (this.entities.get(i) instanceof Magia) || (this.entities.get(i) instanceof Rayo)) && ((Projectile) this.entities.get(i)).destroy)
                        || (this.entities.get(i) instanceof NPC && ((NPC) this.entities.get(i)).getActualLife() <= 0)) {
                    this.entities.remove(i--).dispose();
                } else {
                    if (this.entities.get(i).coord.y >= 2048) {
                        this.intentUp.add(this.entities.remove(i--));
                    } else if (this.entities.get(i).coord.y < 0) {
                        this.intentDown.add(this.entities.remove(i--));
                    } else if ((this.entities.get(i).coord.x + this.entities.get(i).getWidth()) > 2048) {
                        this.intentRight.add(this.entities.remove(i--));
                    } else if ((this.entities.get(i).coord.x + this.entities.get(i).getHeight()) <= 0) {
                        this.intentLeft.add(this.entities.remove(i--));
                    }
                }
            }
            //System.out.println("Elementos (particulas incluidas) " + this.entities.size());

            //QuickSort.QuickSort(this.entities,0,this.entities.length - 1);
        } else {
            int count = 0;
            for (Entity i : this.entities) {
                if (i != null) {
                    if (i.coord.z < 0) {
                        i.coord.z = 0;
                    }
                    if (i instanceof NPC) {
                        ((NPC) i).Soul(this, this.Postentities, count);
                    } else if (i instanceof Projectile) {
                        ((Projectile) i).phisics(this.blocks);
                    }
                    i.actual(this.x, this.y);
                }
                ++count;
            }
            this.entities.addAll(this.Postentities);
            this.Postentities.clear();

            NPC.TICK();
            for (int i = 0; i < this.entities.size(); ++i) {
                if (this.entities.get(i) instanceof Projectile && ((Projectile) this.entities.get(i)).destroy) {
                    this.entities.get(i).dispose();
                    Cardinal.bulletPool.free((Projectile)this.entities.get(i));
                    this.entities.remove(i--);
                } else {
                    if (this.entities.get(i).coord.y >= 2048) {
                        this.intentUp.add(this.entities.remove(i--));
                    } else if (this.entities.get(i).coord.y < 0) {
                        this.intentDown.add(this.entities.remove(i--));
                    } else if ((this.entities.get(i).coord.x + this.entities.get(i).getWidth()) > 2048) {
                        this.intentRight.add(this.entities.remove(i--));
                    } else if ((this.entities.get(i).coord.x) < 0) {
                        this.intentLeft.add(this.entities.remove(i--));
                    }
                }
            }
        }
    }

    public boolean isPlayer(){
        return this.player != null;
    }

    public String parseXML(){
        StringWriter writer = new StringWriter();
        XmlWriter xml = new XmlWriter(writer);
        try {
            xml.element("Chunk").attribute("X", this.x).attribute("Y", this.y)
                    .element("Parte")
                        .attribute("Tipo", "Bloques");

            for (Block block : this.blocks) {
                if (block != null) {
                    if (block instanceof Escaleras) {
                        xml.element("E")
                                .attribute("x", (byte) (block.coord.x / 64))
                                .attribute("y", (byte) (block.coord.y / 64))
                                .attribute("z", (byte) (block.coord.z / 64))
                                .attribute("W", (byte) (block.getWidth() / 64))
                                .attribute("H", (byte) (block.getHeight() / 64))
                                .attribute("D", (byte) (block.getHeight() / 64))
                                .attribute("T", block.getType())
                                .attribute("O", block.getOrient())
                                .pop();
                    } else if (block instanceof Decoration) {
                        xml.element("Dec")
                                .attribute("x", (byte) (block.coord.x / 64))
                                .attribute("y", (byte) (block.coord.y / 64))
                                .attribute("z", (byte) (block.coord.z / 64))
                                .attribute("W", (byte) (block.getWidth() / 64))
                                .attribute("H", (byte) (block.getHeight() / 64))
                                .attribute("D", (byte) (block.getHeight() / 64))
                                .attribute("T", block.getType())
                                .attribute("O", block.getOrient())
                                .pop();

                    } else if (block instanceof Deformed) {
                        xml.element("Def")
                                .attribute("x", (byte) (block.coord.x / 64))
                                .attribute("y", (byte) (block.coord.y / 64))
                                .attribute("z", (byte) (block.coord.z / 64))
                                .attribute("T", block.getType())
                                .attribute("O", block.getOrient())
                                .attribute("v", Arrays.toString(((Deformed) block).getHeights()))
                                .pop();
                    } else {
                        xml.element("B")
                                .attribute("x", (byte) (block.coord.x / 64))
                                .attribute("y", (byte) (block.coord.y / 64))
                                .attribute("z", (byte) (block.coord.z / 64))
                                .attribute("T", block.getType())
                                .attribute("O", block.getOrient())
                                .pop();
                    }
                }
            }

            xml.pop().element("Parte")
                .attribute("Tipo", "Entidades");
            for (Entity e: this.entities) {
                xml.element("Ent")
                    .attribute("T",e.getClass().getSimpleName())
                    .attribute("Info", 0)
                        .pop();
            }
            xml.pop()
            .pop();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    public void drawMap(float x, float y, int px, SpriteBatch b){

    }
    public void resetModels () {
        this.d3Print = false;
    }

    public String aStar(Vector3 start, Vector3 obj, int move){
        ArrayList<Path> Paths = new ArrayList<Path>();
        ArrayList<Vector3> Closed = new ArrayList<Vector3>();

        if (start.equals(obj)){
            return "";
        } else {
            Path p = new Path(start);
            Paths.add(p);
            boolean close;

            while(Paths.size() != 0) {
                p = Paths.remove(0);
                Path[] kids = p.getKids(4,move, obj);

                for(int i = 0; i < kids.length; ++i) {
                    if(kids[i].fin.x < 0 || kids[i].fin.x > 2048 ||
                            kids[i].fin.y < 0 || kids[i].fin.y > 2048 ||
                            kids[i].fin.z < 0 || kids[i].fin.z > 2048 || this.blocks[(((int)kids[i].fin.x)/64)
                                + (31 - ((int)kids[i].fin.y)/64) * 32 + (((int)kids[i].fin.z)/64) * 32 * 32] != null){
                        kids[i] = null;
                    }
                }

                for(int i = 0; i < kids.length; ++i){ //Extends
                    if(kids[i] != null) {
                        if (kids[i].fin.equals(obj)) {
                            return kids[i].getPathString();
                        }
                        close = false;
                        for (int j = 0; j < Closed.size(); ++j) {
                            if (Closed.get(j).equals(kids[i].fin)) {
                                close = true;
                                break;
                            }
                        }
                        if (!close) {
                            boolean ended = true;
                            for(int k = 0; k < Paths.size(); ++k) {
                                if(Paths.get(k).value > kids[i].value){
                                    Paths.add(k,kids[i]);
                                    ended = false;
                                    break;
                                }
                            }
                            if(ended) {
                                Paths.add(Paths.size(),kids[i]);
                            }
                        }
                    }
                }
                Closed.add(p.fin);

                if(Paths.size() >= 1300) {
                    break;
                }
            }
            System.out.println("NO he encontrado un camino porque soy un inepto");
            System.out.println("Yo en " + start.toString() + " y el en " + obj.toString());

            return "";
        }
    }
}