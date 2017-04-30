package gotozero;

//import gotozero.Data.List;
import gotozero.Constructor.Edify;
import gotozero.Organice.ListRect;
import OrdenacionRect.OrdenBlock;
/**
 * Write a description of class world here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public final class world {

    // instance variables - replace the example below with your own
    private int width;
    private int height;
    public static byte PARTES = 5;

    private Mage Player;

    //Para implementaciones mas eficientes usar listas enlazadas con polimorfismo en la variable Data
    private Magia[] hechizos;
    private int MagiaOcupada;
    private Block[] byMap;

    private ListRect out2 = new ListRect();

    private Monster[] LiveForm;
    private NPC[] VillagePeople;

    /**
     * Constructor for objects of class world
     *
     * @param MapL
     * @param height
     */
    /*public world(int width, int height) {
        this.width = width;
        this.height = height;

        this.byMap = new Block[(this.width/world.PARTES)*(this.height/world.PARTES)];
        this.used = 0;
        this.Dynamik = new Block[this.used];
    }*/
    public world(int MapL, int height) {
        this.width = MapL;
        this.height = height;

        this.byMap = new Block[0];
        this.MagiaOcupada = 0;
        this.hechizos = new Magia[0];

        this.LiveForm = new Monster[1];
        this.LiveForm[0] = new Monster((byte) 0, "Calorius", "BOSS");

        this.VillagePeople = new NPC[1];
        this.VillagePeople[0] = new NPC("img/Ciel.png");

        for (int k = 0; k < this.height * 5; ++k) {
            for (int j = 0; j < this.width * 5; ++j) {
                for (int i = 0; i < this.width * 5; ++i) {
                    if (k == 2) {
                        if ((j - 2) % 5 == 0 && (i - 2) % 5 == 0) {
                            
                            if (i > 50 && i < this.width * 5 - 50 && j > 50 && j < this.width * 5 - 50 ) {
                                this.addToWorld(i, j, 2, 4);
                            } else {
                                this.addToWorld(i, j, 2, 5);
                            }
                        }
                    } else if (k >= 7 && (k - 2) % 5 == 0) {
                        if (((j == 2) && (i - 2) % 5 == 0)
                                || ((j == (this.width * 5) - 3) && (i - 2) % 5 == 0)
                                || ((i == 2) && (j - 2) % 5 == 0)
                                || ((i == (this.width * 5) - 3) && (j - 2) % 5 == 0)) {
                            this.addToWorld(i, j, k, 6);
                        } else if (k == 7) {
                            if(i == 27 && j == 27) {
                                addHouse(1000000, i, j, k);
                            }
                        }
                    }
                }
            }
        }
        OrdenBlock.quicksort(byMap);
        System.out.println("Numero de cubos sin Comprimir: " + this.byMap.length);
        this.compactCubes();
        System.out.println("Numero de cubos totales: " + this.byMap.length);
        OrdenBlock.quicksort(byMap);
    }
    
    private void compactCubes(){
        int i = 0;
//        for(int i = 0; i < this.byMap.length; ++i){
        while(i < this.byMap.length) {    
//            for(int j = 0; j < this.byMap.length; ++j){
            int j = 0;
            while(j < this.byMap.length){    
                //if(this.byMap[i].)
                if(this.byMap[i].Compatible(this.byMap[j])){
                    //System.out.println("Oportunidad Perdida");
                    this.byMap[i].fusion(this.byMap[j]);
                    this.byMap[j] = null;
                    this.eliminaNull();
                    i = 0;
                    j = 0;
                }
                ++j;
            }
            ++i;
        }
    }

    private void eliminaNull() {
        Block[] auxiliar = new Block[this.byMap.length - 1];
        int i = 0;
        int j = 0;
        while(i < this.byMap.length) {
            if(this.byMap[i] != null) {
                auxiliar[j] = this.byMap[i];
                ++j;
            }
            ++i;
        }
        this.byMap = auxiliar;
    }
    
    public void addHouse(int Type, int x, int y, int z){
        Edify.addHouse(this, Type, x, y, z);
    }
    
    public final void addToWorld(int x, int y, int z, int Type) {
        if (Type < 1000) {
            Block nextB = new Block(x, y, z, (byte) Type);
            boolean Overload = false;
            for (int i = 0; !Overload && i < this.byMap.length; ++i) {
                Overload = this.byMap[i].equals(nextB);
            }
            if(!Overload){
                Block[] aux = new Block[this.byMap.length + 1];
                for (int i = 0; i < this.byMap.length; ++i) {
                    aux[i] = this.byMap[i];
                }
                aux[this.byMap.length] = nextB;
                this.byMap = aux;
            }
        }
    }

    public void gravedad(lifes m) {
        m.caer(4);
        boolean aux = true;
        for (int i = 0; aux && i < this.byMap.length; i++) {
            if (this.byMap[i] != null) {
                aux = !this.byMap[i].collision(m);
            }
        }
        if (!aux) {
            m.caer(-4);
        }
    }

    public void liveCicle() {
        //OrdenBlock.quicksort(byMap);
        this.gravedad(this.Player);
        if (this.Player.getZ() < 0) {
            this.Player.setZ(0);
        }
        for (int i = 0; i < this.LiveForm.length; ++i) {
            if (this.LiveForm[i] != null) {
                if (this.LiveForm[i].getLife() < 0) {
                    this.LiveForm[i] = null;
                } else {
                    this.gravedad(this.LiveForm[i]);
                    if (this.LiveForm[i].getZ() < 0) {
                        this.LiveForm[i].setZ(0);
                    }
                    
                    //Inteligencia artificial
                    if(this.LiveForm[i].AgroZone(Player, 448)){
                        int objX = (this.Player.getX() - this.LiveForm[i].getX()) >> 7;
                        int objY = (this.Player.getY() - this.LiveForm[i].getY()) >> 7;
                        //int modulo = 100;
                        moveInWorld(this.LiveForm[i], objX, objY);
                    }
                }
            }
        }
    }

    public void moveInWorld(lifes m, int x, int y) {
        m.move(x, y);
        boolean aux = true;
        for (int i = 0; i < this.byMap.length && aux; i++) {
            if (this.byMap[i] != null) {
                aux = !this.byMap[i].collision((Block) m);
            }
        }
        if (!aux) {
            m.move(-x, -y);
        }
    }

    public void setPlayer(Mage A) {
        this.Player = A;
    }

    public int getNumberRX() {
        return this.width / world.PARTES;
    }

    public int getNumberRY() {
        return this.height / world.PARTES;
    }

    public void actualToPaint(int CamX, int CamY, int w, int h) {
        //Tamaño Maximo pantalla(provisional) 2000:1000
        int MaxSizeX = w, MaxSizeY = h;

        this.out2.vaciarLista();
        Rect auxi = new Rect();
        this.Player.toRect(auxi);
        this.out2.add(auxi.clone());

        int aX;
        int aY;

        for (int i = 0; i < this.byMap.length; ++i) {
            this.byMap[i].toRect(auxi);
            aX = auxi.getX() - CamX;
            aY = auxi.getY() - CamY;
            if (aX + auxi.getWidth() > 0 && aX < MaxSizeX && aY + auxi.getHeight() > 0 && aY < MaxSizeY) {
                this.out2.add(auxi);
            }
        }
        for (int i = 0; i < this.hechizos.length; ++i) {
            if (this.hechizos[i] != null) {
                this.hechizos[i].toRect(auxi);
                aX = auxi.getX() - CamX;
                aY = auxi.getY() - CamY;
                if (aX + auxi.getWidth() > 0 && aX < MaxSizeX && aY + auxi.getHeight() > 0 && aY < MaxSizeY) {
                    this.out2.add(auxi);
                }
            }
        }
        for (int i = 0; i < this.LiveForm.length; ++i) {
            if (this.LiveForm[i] != null) {
                this.LiveForm[i].toRect(auxi);
                aX = auxi.getX() - CamX;
                aY = auxi.getY() - CamY;
                if (aX + auxi.getWidth() > 0 && aX < MaxSizeX && aY + auxi.getHeight() > 0 && aY < MaxSizeY) {
                    this.out2.add(auxi);
                }
            }
        }
        for (int i = 0; i < this.VillagePeople.length; ++i) {
            if (this.VillagePeople[i] != null) {
                this.VillagePeople[i].toRect(auxi);
                aX = auxi.getX() - CamX;
                aY = auxi.getY() - CamY;
                if (aX + auxi.getWidth() > 0 && aX < MaxSizeX && aY + auxi.getHeight() > 0 && aY < MaxSizeY) {
                    this.out2.add(auxi);
                }
            }
        }
    }

    public ListRect getOutput() {
        return this.out2;
    }

    public Block[] getByMap() {
        return this.byMap;
    }

    public Magia[] getHechizos() {
        return this.hechizos;
    }

    public void addHechizo(Magia a) {
        Magia[] aux = new Magia[this.hechizos.length + 1];
        System.arraycopy(this.hechizos, 0, aux, 0, this.hechizos.length);

        aux[this.hechizos.length] = a;
        this.hechizos = aux;
        this.MagiaOcupada++;
    }

    public void mveHechizo() {
        for (int i = 0; i < this.hechizos.length; ++i) {
            if (this.hechizos[i] != null) {
                for (int j = 0; j < this.byMap.length; ++j) {
                    if (this.byMap[j].collision(this.hechizos[i])) {
                        this.hechizos[i].dismDurabilidad(1000);
                    } else if (j < this.LiveForm.length && this.LiveForm[j] != null) {
                        if (this.LiveForm[j].collision(this.hechizos[i])) {
                            this.hechizos[i].dismDurabilidad(150);
                            this.LiveForm[j].decrLife();
                            System.out.println("Colisionado con bala!!");
                        }
                    }
                }
                this.hechizos[i].move();
                if (this.hechizos[i].getDurabilidad() != -404) {
                    this.hechizos[i].dismDurabilidad(1);
                    if (this.hechizos[i].getDurabilidad() < 0) {
                        this.hechizos[i] = null;
                        --this.MagiaOcupada;
                    }
                }
            }
        }
    }
}