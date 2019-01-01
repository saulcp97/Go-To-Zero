package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Cubos.Block;
import com.mygdx.game.Cubos.Decoration;
import com.mygdx.game.Cubos.Deformed;
import com.mygdx.game.Cubos.Entidades.Entity;
import com.mygdx.game.Cubos.Entidades.bullet.Magia.Magia;
import com.mygdx.game.Cubos.Entidades.vida.Avatar;
import com.mygdx.game.Cubos.Entidades.vida.Bestia.Wild;
import com.mygdx.game.Cubos.Entidades.vida.NPC.NPC;
import com.mygdx.game.Cubos.Escaleras;
import com.mygdx.game.simulation.Edification.ProcGenerator;

import java.util.ArrayList;
import java.util.Arrays;

public class MundoCb {
    public boolean db;
    public String message;

    private float[] pantalla;
    private boolean mouseIsVisible;
    private byte styleOfMouse;
    private Block mouseIcon;

    //private Chunk[] chunks;
    private final int PunteroX;
    private final int PunteroY;

    private Avatar player;

    private boolean finite;

    private Chunk[][] chunks;
    private int size;


    private ArrayList<Chunk> loading;
    private ArrayList<Chunk> saving;

    private boolean ahorro = true;

    private final Array<Magia> activeBullets = new Array<Magia>();
    private final Pool<Magia> bulletPool = new Pool<Magia>() {
        @Override
        protected Magia newObject() {
            return new Magia();
        }
    };

    private String firm;

    public MundoCb(){ //Unitario
      //  this.chunks = new Chunk[1];
        //this.chunks[0] = new Chunk();
        this.chunks = new Chunk[1][1];
        this.chunks[0][0] = new Chunk();
        this.size = 1;

        this.loading = new ArrayList<Chunk>();
        this.saving = new ArrayList<Chunk>();

        //Esto sera comun para todos los modos cada uno luego seleccionara como editarlo
        this.pantalla = new float[]{0,0};
        this.mouseIsVisible = false;
        this.styleOfMouse = 0;
        this.mouseIcon = new Block(0,0,0,Byte.MIN_VALUE);

        this.PunteroX = 0;
        this.PunteroY = 0;

        this.db = false;
        this.message = "";
        this.finite = true;
        this.firm = "";
        ProcGenerator.GenerateProc(this.chunks[PunteroX][PunteroY]);

    }

    public MundoCb(int ch){ //Cuadrado de todos los lados iguales, preferiblemente impar, por ahora
        this.chunks = new Chunk[ch][ch];
        this.size = ch;
        for(int i = 0; i < ch; ++i) {
            //this.chunks[i] = new Chunk(i % ch,(ch - 1) - i/ch);
            for(int j = 0; j < ch; ++j){
                this.chunks[i][j] = new Chunk(i,(ch - 1) - j);
            }
        }

        this.loading = new ArrayList<Chunk>();
        this.saving = new ArrayList<Chunk>();

        //Esto sera comun para todos los modos cada uno luego seleccionara como editarlo
        this.pantalla = new float[]{0,0};
        this.mouseIsVisible = false;
        this.styleOfMouse = 0;
        this.mouseIcon = new Block(0,0,0,Byte.MIN_VALUE);

        this.PunteroX = ch/2;
        this.PunteroY = ch/2;

        this.edifMundo(this.PunteroX,this.PunteroY);
        this.db = false;
        this.finite = false;



        this.firm = "";
        FileHandle file = Gdx.files.local("data/txt/Chunks.txt");
        if(file.exists()) {
            this.firm = file.readString();
        }
    }


    public void setMouse(byte Style, Texture sp) {
        this.styleOfMouse = Style;
        this.mouseIcon.setTexture(sp);
    }

    public void setPantalla(float width, float height) {
        this.pantalla[0] = width;
        this.pantalla[1] = height;
    }

    public void setMouseIsVisible(boolean is) {
        this.mouseIsVisible = is;
    }

    public void setPlayer(Avatar av) {
        this.player = av;
        this.chunks[this.PunteroX][this.PunteroY].setPlayer(this.player);
        this.chunks[this.PunteroX][this.PunteroY].teleportSpawn();
    }

    private void edifMundo(int px,int py) {
        //House.addPosition(this.chunks[px][py],0,0,0);
        //House.addPosition(this.chunks[px][py],10,20,0);

        for(int i = 0; i < 10; ++i) {
     //       this.chunks[px][py].tryToAddEntity(new Wild(0, 0, 0, "Slime"), (int)(Math.random()*31), (int)(Math.random()*31), 1);
        }

        ProcGenerator.GenerateProc(this.chunks[px][py]);
        ProcGenerator.GenerateProcForest(this.chunks[px + 1][py]);
        ProcGenerator.GenerateProcTerrain(this.chunks[px][py + 1]);

        ProcGenerator.GeneratePreviusMap(this.chunks[px][py - 1],"Draknia", 0, 7);
        ProcGenerator.GeneratePreviusMap(this.chunks[px + 1][py - 1],"Draknia", 1, 7);

        FileHandle fileCh = Gdx.files.local("data/txt/tile0.tmx");
        this.chunks[px + 1][py + 1] = new Chunk(fileCh);
        this.chunks[px + 1][py + 1].setX(px + 1);
        this.chunks[px + 1][py + 1].setY( 1 - py);

        Decoration dec = new Decoration();
        dec.setOrient((byte)3);

        this.chunks[px][py].tryToAddBlock(dec,31,31,1);

        this.chunks[px][py].tryToAddBlock(new Deformed((byte)1,(byte)2),0,26,1);


        //this.chunks[px][py].tryToAddEntity(new NPC("Clamsy"), (int)(Math.random()*31), (int)(Math.random()*31), 1);
        //this.chunks[px][py].tryToAddEntity(new NPC("Zombie"), (int)(Math.random()*31), (int)(Math.random()*31), 1);
        //this.chunks[px][py].tryToAddEntity(new NPC("Irisciel"), 7, 29, 1);
        //this.chunks[px][py].tryToAddEntity(new NPC("Phoenix"), 10, 16, 1);
        //this.chunks[px][py].tryToAddEntity(new NPC("Hideki Seishisai"), 12, 29, 1);

        this.chunks[px][py].tryToAddEntity(new NPC("TEOD"), 10, 16, 1);

        this.chunks[px][py].tryToAddBlock(new Escaleras((byte)0,(byte)2),1,31,1);
        this.chunks[px][py].tryToAddBlock(new Escaleras((byte)0,(byte)3),2,30,1);
        this.chunks[px][py].tryToAddBlock(new Escaleras((byte)0,(byte)1),1,22,3);

        this.chunks[px][py].tryToAdd(2, 22, 3, (byte) 4);
        this.chunks[px][py].tryToAdd(0, 22, 2, (byte) 4);
        this.chunks[px][py].tryToAdd(0, 23, 2, (byte) 4);
        this.chunks[px][py].tryToAdd(1, 24, 1, (byte) 4);

        this.chunks[px][py].tryToAddBlock(new Escaleras((byte)0,(byte)0),1,25,1);

        this.chunks[px][py].tryToAddBlock(new Escaleras((byte)0,(byte)0),0,25,1);
        this.chunks[px][py].tryToAddBlock(new Escaleras((byte)0,(byte)0),0,24,2);

        this.chunks[px][py].tryToAddBlock(new Escaleras((byte)0,(byte)1),5,31,1);
        this.chunks[px][py].tryToAdd(6, 31, 1, (byte) 12);

        this.chunks[px][py].tryToAdd(3, 31, 1, (byte) 12);
        this.chunks[px][py].tryToAdd(2, 31, 1, (byte) 12);
        this.chunks[px][py].tryToAdd(4, 29, 1, (byte) 12);

        Cardinal.readJSON();
    }

    public boolean tryAddBlockFromMouse(Camera cam, byte code){
        //if(this.juego.chunk.tryToAdd(((int)(((int)(((float) Gdx.input.getX() / Gdx.graphics.getWidth()) * camera.viewportWidth)) + camera.position.x - camera.viewportWidth/2)/64), ((31 - ((int)((camera.position.y + camera.viewportHeight/2) - ((int)(((float)Gdx.input.getY() / Gdx.graphics.getHeight()) * camera.viewportHeight))) / 64)))+1,1, (byte)10)) {
        return this.chunks[this.PunteroX][this.PunteroY].tryToAdd(((int)(((int)(((float) Gdx.input.getX() / Gdx.graphics.getWidth()) * cam.viewportWidth)) + cam.position.x - cam.viewportWidth/2)/64),((31 - ((int)((cam.position.y + cam.viewportHeight/2) - ((int)(((float) Gdx.input.getY() / Gdx.graphics.getHeight()) * cam.viewportHeight))) / 64)))+1,1,code);
        //return false;
    }

    public void tick() {
        for (Chunk[] chunk : this.chunks) {
            Arrays.stream(chunk).parallel().forEach(Chunk::phisics);
        }

        for(int i = 0; i < this.chunks.length; ++i) {
            for (int j = 0; j < this.chunks.length; ++j) {
                //this.chunks[i][j].phisics();
                boolean ch = false;

                if(this.chunks[i][j].intentUp.size() != 0) {
                    Entity p;
                    while(this.chunks[i][j].intentUp.size() != 0) {
                        p = this.chunks[i][j].intentUp.remove(0);
                        p.coord.y -= 2048;
                        System.out.println(p.toString());
                        if(j - 1 >= 0) {
                            this.chunks[i][j - 1].addEntity(p);
                        }
                    }
                }

                if(this.chunks[i][j].intentDown.size() != 0) {
                    Entity p;
                    while(this.chunks[i][j].intentDown.size() != 0) {
                        p = this.chunks[i][j].intentDown.remove(0);
                        //System.out.println("Antes tenia la y: " + p.coord.y);
                        p.coord.y += 2048;
                        //System.out.println("Tengo la y: " + p.coord.y);
                        if(i < this.chunks.length && j + 1< this.chunks[i].length) {
                            this.chunks[i][j + 1].addEntity(p);
                        }
                    }
                }

                if(this.chunks[i][j].intentRight.size() != 0) {
                    Entity p;
                    while(this.chunks[i][j].intentRight.size() != 0) {
                        p = this.chunks[i][j].intentRight.remove(0);
                        //System.out.println("Antes tenia la x: " + p.coord.x);
                        p.coord.x -= 2048;
                        //System.out.println("Tengo la x: " + p.coord.x);
                        if(i + 1 < this.chunks.length && j < this.chunks[i].length) {
                            this.chunks[i + 1][j].addEntity(p);
                        }
                    }
                }

                if(this.chunks[i][j].intentLeft.size() != 0) {
                    Entity p;
                    while(this.chunks[i][j].intentLeft.size() != 0) {
                        p = this.chunks[i][j].intentLeft.remove(0);
                        p.coord.x += 2048;
                        if(i - 1 >= 0) {
                            this.chunks[i - 1][j].addEntity(p);
                        }
                    }
                }

                if(this.chunks[i][j].isPlayer()) {
                    if (this.chunks[i][j].inteU) {
                        if (this.finite || this.chunks[i][j - 1].externalColition(this.chunks[i][j].getPlayer(), 0)) {
                            this.chunks[i][j].getPlayer().coord.y = 2047 - this.chunks[i][j].getPlayer().getHeight();
                        } else if (this.chunks[i][j].tUp) {
                            //System.out.println("Posición del Avatar Up: " + this.player.coord.toString());
                            this.move(i, j, 0);
                            ch = true;
                        }
                        this.chunks[i][j].inteU = false;
                        this.chunks[i][j - 1].inteD = false;
                        this.chunks[i][j].tUp = false;
                    } else if (this.chunks[i][j].inteD) {
                        if (this.finite || this.chunks[i][j + 1].externalColition(this.chunks[i][j].getPlayer(), 2)) {
                            this.chunks[i][j].getPlayer().coord.y = 0;
                        } else if (this.chunks[i][j].tDown) {
                            //System.out.println("Posición del Avatar Down: " + this.player.coord.toString());
                            this.move(i, j, 2);
                            ch = true;
                        }
                        this.chunks[i][j].inteD = false;
                        this.chunks[i][j - 1].inteD = false;
                        this.chunks[i][j + 1].inteU = false;
                        this.chunks[i][j].tDown = false;
                    }

                    if (this.chunks[i][j].inteL) {
                        if (this.finite || this.chunks[i - 1][j].externalColition(this.chunks[i][j].getPlayer(), 3)) {
                            this.chunks[i][j].getPlayer().coord.x = 0;
                        } else if (this.chunks[i][j].tLeft && !ch) {
                            this.move(i, j, 3);
                        }
                        this.chunks[i][j].inteL = false;
                        this.chunks[i][j].inteR = false;
                        this.chunks[i + 1][j].inteL = false;
                        this.chunks[i + 1][j].tLeft = false;

                    } else if (this.chunks[i][j].inteR) {
                        if (this.finite || this.chunks[i + 1][j].externalColition(this.chunks[i][j].getPlayer(), 1)) {
                            this.chunks[i][j].getPlayer().coord.x = 2047 - this.chunks[i][j].getPlayer().getWidth();
                        } else if (this.chunks[i][j].tRight && !ch) {
                            this.move(i, j, 1);
                        }
                        //this.chunks[i][j].inteR = false;
                        //this.chunks[i - 1][j].inteR = false;
                        //this.chunks[i - 1][j].tRight = false;
                    }
                }
            }
        }

        if(this.db){
            this.message = "Jugador en el cuadrante: " + this.PunteroX + ", " + this.PunteroY + "\nen las coordenadas: " + this.player.coord.toString() ;
            for(int i = 0; i < this.chunks.length; ++i) {
                for(int j = 0; j < this.chunks.length; ++j) {
                    this.message = this.message.concat("\nChunk " + i + "nombre " + this.chunks[i][j].Name);
                }
            }

        }

        if(this.player.TalkWith != null && this.player.Mark) {
            this.player.TalkWith.Talk(this.player);
        }

        if(this.player.Mark) {
            this.player.Mark = false;
        }


        if(this.saving.size() != 0) {
            for(Chunk ch:this.saving) {
                if(!this.firm.contains(ch.Name)) {
                    FileHandle file = Gdx.files.local("data/txt/Chunks.txt");
                    file.writeString("#" + ch.Name, true);
                    this.firm = this.firm.concat("#" + ch.Name);
                    System.out.println("Creando nuevo apendice");
                }
                FileHandle fileCh = Gdx.files.local("data/Chunks/"+ ch.Name + ".xml");
                fileCh.writeString(ch.parseXML(),false);

                System.out.println("Actualizado Chunk guardado#");
            }
            this.saving.clear();
        }
    }

    private void move(int x, int y,int dir){
        String name;
        switch (dir) {
            case 0:
                System.out.println("Para mov 0 llamada X: " + x + ", Y: " + y);
                this.chunks[x][y].getPlayer().coord.y -= 2048;
               // this.chunks[x][y - 1].setPlayer(this.chunks[x][y].getPlayer());
                //this.PunteroY -= 1;
                this.chunks[x][y].setPlayer(null);

                for(int i = 0; i < this.chunks.length; ++i) {
                    this.saving.add(this.chunks[i][this.chunks.length - 1]); //BIEN
                    System.arraycopy(this.chunks[i], 0, this.chunks[i], 1, this.chunks.length - 1);

                    name = "X" + (this.chunks[i][1].getX() << 5) + "Y" + ((this.chunks[i][1].getY() + 1) << 5);
                    if(this.firm.contains(name)) {
                        FileHandle fileCh = Gdx.files.local("data/Chunks/" + name + ".xml");
                        this.chunks[i][0] = new Chunk(fileCh);
                    } else {
                        this.chunks[i][0] = new Chunk(this.chunks[i][1].getX(), this.chunks[i][1].getY() + 1);
                    }
                    System.out.println("Soy: " + this.chunks[i][0].Name);
                }
                this.chunks[this.PunteroX][this.PunteroY].setPlayer(this.player);
                break;
            case 1:
                this.chunks[x][y].getPlayer().coord.x -= 2048;
                //this.chunks[x - 1][y].setPlayer(this.chunks[x][y].getPlayer());
                this.chunks[x][y].setPlayer(null);
                for(int j = 0; j < this.chunks.length; ++j) {
                    this.saving.add(this.chunks[0][j]); //BIEN
                    for(int i = 1; i < this.chunks.length; ++i) {
                        this.chunks[i - 1][j] = this.chunks[i][j];
                    }
                    //System.out.println("Soy: " + this.chunks[this.chunks.length - 1][j].Name);

                    name = "X" + ((this.chunks[this.chunks.length - 1][j].getX() + 1) << 5) + "Y" + (this.chunks[this.chunks.length - 1][j].getY() << 5);
                    if(this.firm.contains(name)) {
                        FileHandle fileCh = Gdx.files.local("data/Chunks/" + name + ".xml");
                        this.chunks[this.chunks.length - 1][j] = new Chunk(fileCh);
                    } else {
                        this.chunks[this.chunks.length - 1][j] = new Chunk(this.chunks[this.chunks.length - 1][j].getX() + 1,
                                this.chunks[this.chunks.length - 1][j].getY());
                    }
                }
                this.chunks[this.PunteroX][this.PunteroY].setPlayer(this.player);
                //❤
                break;
            case 2:
                System.out.println("Para mov 2 llamada X: " + x + ", Y: " + y);
                this.chunks[x][y].getPlayer().coord.y += 2048;
                //this.chunks[x][y + 1].setPlayer(chunks[x][y].getPlayer());
                //this.PunteroY += 1;
                this.chunks[x][y].setPlayer(null);

                for(int i = 0; i < this.chunks.length; ++i) {
                    this.saving.add(this.chunks[i][0]);
                    System.arraycopy(this.chunks[i], 1, this.chunks[i], 0, this.chunks.length - 1);

                    name = "X" + (this.chunks[i][this.chunks.length - 2].getX() << 5) + "Y" + ((this.chunks[i][this.chunks.length - 2].getY() - 1) << 5);
                    if(this.firm.contains(name)) {
                        FileHandle fileCh = Gdx.files.local("data/Chunks/" + name + ".xml");
                        this.chunks[i][this.chunks.length - 1] = new Chunk(fileCh);
                    } else {
                        this.chunks[i][this.chunks.length - 1] = new Chunk(this.chunks[i][this.chunks.length - 2].getX(),this.chunks[i][this.chunks.length - 2].getY() - 1);
                    }
                    System.out.println("Soy: " + this.chunks[i][this.chunks.length - 1].Name);
                }

                this.chunks[this.PunteroX][this.PunteroY].setPlayer(this.player);
                break;
            case 3:
                System.out.println("Para mov 3 llamada X: " + x + ", Y: " + y);
                this.chunks[x][y].getPlayer().coord.x += 2048;
                //this.chunks[x - 1][y].setPlayer(this.chunks[x][y].getPlayer());
                //this.PunteroX -= 1;
                this.chunks[x][y].setPlayer(null);
                for(int j = 0; j < this.chunks.length; ++j) {
                    this.saving.add(this.chunks[this.chunks.length - 1][j]); //BIEN
                    for(int i = this.chunks.length - 1; i > 0; --i) {
                        //System.out.println("Cambio de: [" + (i - 1) + "," + j +"] a [" + i + "," + j +"]");
                        this.chunks[i][j] = this.chunks[i - 1][j];
                    }

                    name = "X" + ((this.chunks[1][j].getX() - 1) << 5) + "Y" + (this.chunks[1][j].getY() << 5);
                    if(this.firm.contains(name)) {
                        FileHandle fileCh = Gdx.files.local("data/Chunks/" + name + ".xml");
                        this.chunks[0][j] = new Chunk(fileCh);
                    } else {
                        this.chunks[0][j] = new Chunk(this.chunks[1][j].getX() - 1, this.chunks[1][j].getY());

                    }
                }
                this.chunks[this.PunteroX][this.PunteroY].setPlayer(this.player);
                break;
        }

    }

    private int contados = 0;
    private long medida = 0;

    public Avatar getPlayer() { return player; }
    public void draw3DWorld(PerspectiveCamera camera) {
        for (int i = 0; i < this.chunks.length; ++i) {
            for (int j = 0; j < this.chunks[i].length; ++j) {
                if (this.chunks[i][j].loaded
                        && ((this.chunks[i][j].isPlayer())
                            || this.chunks[i][j].isRendereable(camera.frustum))) {
                    this.chunks[i][j].draw3DChunk(camera, this.ahorro);
                }
            }
        }

        if(++this.contados == 60) {
            this.medida = 0;
            this.contados = 0;
        }
    }

    public void drawWorld(SpriteBatch bch) {
        if(this.mouseIsVisible) {
            this.mouseIcon.coord.x = 64 * ((int)(((int)(((float) Gdx.input.getX() / Gdx.graphics.getWidth()) * pantalla[0])) + this.getFocusX() - pantalla[0]/2)/64);
            this.mouseIcon.coord.y = 31*64 - 64*(((31 - ((int)((this.getFocusY() + pantalla[1]/2) - ((int)(((float) Gdx.input.getY() / Gdx.graphics.getHeight()) * pantalla[1]))) / 64)))+1);
            this.mouseIcon.coord.z = 64;

            this.mouseIcon.realign();
            for (int i = 0; i < this.chunks.length; ++i) {
                for (int j = 0; j < this.chunks.length; ++j) {
                    //System.out.println(chunks[i][j].getFocusX() + "," + chunks[i][j].getFocusY());


                    if(chunks[i][j].loaded) {
                        if (this.mouseIcon.coord.x >= chunks[i][j].getX() * 32 * 64 && this.mouseIcon.coord.y >= chunks[i][j].getY() * 32 * 64 && this.mouseIcon.coord.x < chunks[i][j].getX() * 32 * 64 + 32 * 64 && this.mouseIcon.coord.y < chunks[i][j].getY() * 32 * 64 + 32 * 64) {
                            chunks[i][j].drawChunk(bch, this.mouseIcon);
                        } else {
                            chunks[i][j].drawChunk(bch, null);
                        }
                    }
                }
            }
        } else {
            for (Chunk[] aChunked : this.chunks) {
                for (int j = 0; j < this.chunks.length; ++j) {
                    if (aChunked[j].loaded) {
                        aChunked[j].drawChunk(bch, null);
                    }
                }
            }
        }
    }

    public void resetDB() {
        this.db = !db;
    }

    public void addComand(int code) {
        if(code < 5) {
            this.player.advanceIntent[code] = true;
        } else if(code == 116) {
            this.player.Mark = !this.player.Mark;
        } else if(code == 13) {
            System.out.println("Easter Egg Survival Terror: Activado");
        } else if(code == 5) {
            this.chunks[PunteroX][PunteroY].tryToAddEntity(new Magia(this.player, (byte)1));
            System.out.println("SHOOOT");
        } else if(code == 6) {//Avanza hacia la camara
            this.player.FreeCamMove(true);
        } else if(code == 7) {//Retrocede
            this.player.FreeCamMove(false);
        } else if(code == 10) {
            this.player.jump(12.8f);
        }
    }

    public int getPunteroX() { return this.chunks[PunteroX][PunteroY].getX(); }
    public int getPunteroY() { return this.chunks[PunteroX][PunteroY].getY(); }

    public float getFocusX(){
        return this.chunks[this.PunteroX][this.PunteroY].getFocusX();
    }
    public float getFocusY(){
        return this.chunks[this.PunteroX][this.PunteroY].getFocusY();
    }
    public void setAhorro(boolean ahorro) { this.ahorro = ahorro; }
    public Chunk[][] getChunks() { return chunks; }

    public void resetChunk(int i, int j, int ch) { this.chunks[i][j] = new Chunk(i,(ch - 1) - j); }

    public int getSize() { return size; }
}