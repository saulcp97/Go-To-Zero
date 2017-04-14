package gotozero;

//import gotozero.Data.List;
import gotozero.Constructor.Edify;
import gotozero.Organice.ListRect;

/**
 * Write a description of class world here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class world {

    // instance variables - replace the example below with your own
    private int width;
    private int height;
    public static byte PARTES = 5;

    private Mage Player;

    //Para implementaciones mas eficientes usar listas enlazadas con polimorfismo en la variable Data
    private Magia[] hechizos;
    private int MagiaOcupada;

    private Block[] byMap;

    private Block[] Dynamik;
    private int used;

    private Rect[] out;

    private ListRect out2 = new ListRect();

    private Monster[] LiveForm;
    private NPC[] VillagePeople;

    public static byte[][] creaMapBord(int x) {
        byte res[][] = new byte[x * 5][];
        for (int i = 0; i < x * 5; i++) {
            res[i] = new byte[x * 5];
            for (int j = 0; j < x * 5; j++) {
                if (i == 2 && j >= 2 && (j <= x * 5 - 3) || j == 2 && i >= 2 && (i <= x * 5 - 3) || (i == x * 5 - 3) && (j <= x * 5 - 3) && j >= 2
                        || (j == x * 5 - 3) && (i <= x * 5 - 3) && i >= 2) {
                    if ((i - 2) % 5 == 0 && (j - 2) % 5 == 0) {
                        res[i][j] = 6;
                    }
                } else {
                    res[i][j] = 0;
                }
            }
        }
        return res;
    }

    public static byte[][][] creaPuente(int x, int y) {
        byte res[][][] = new byte[y * 5][][];
        for (int i = 0; i < res.length; ++i) {
            res[i] = new byte[x * 5][];
            for (int j = 0; j < res[i].length; ++j) {
                res[i][j] = new byte[x * 5];
                for (int k = 0; k < res[i][j].length; ++k) {
                    if (i == res.length - 1 && (j - 2) % 5 == 0 && k - 2 == 0) {
                        res[i][j][k] = 1;
                    } else {
                        res[i][j][k] = 0;
                    }
                }
            }
        }
        return res;
    }

    public static byte[][][] creaNull(int x, int y) {
        byte res[][][] = new byte[y][][];
        for (int i = 0; i < res.length; ++i) {
            res[i] = new byte[x * 5][];
            for (int j = 0; j < res[i].length; ++j) {
                res[i][j] = new byte[x * 5];
                for (int k = 0; k < res[i][j].length; ++k) {
                    res[i][j][k] = 0;
                }
            }
        }
        return res;
    }

    public static byte[][] creaMapAll(int x) {
        byte res[][] = new byte[x * 5][];
        for (int i = 0; i < x * 5; ++i) {
            res[i] = new byte[x * 5];
            for (int j = 0; j < x * 5; ++j) {
                if ((i - 2) % 5 == 0 && (j - 2) % 5 == 0) {

                    res[i][j] = 1;

                } else {
                    res[i][j] = 0;
                }

            }
        }
        System.out.println(res.length);
        return res;
    }

    public static byte[][][] creaMap3D(int x, int y) {
        byte[][][] res = new byte[y * 5][][];
        for (int i = 0; i < res.length; ++i) {
            if (i == 2) {
                res[i] = world.creaMapAll(x);
            } else if ((i - 2) % 5 == 0) {
                res[i] = world.creaMapBord(x);
            } else {
                res[i] = new byte[x * 5][];
                for (int j = 0; j < x * 5; j++) {
                    res[i][j] = new byte[x * 5];
                    for (int k = 0; k < x * 5; k++) {
                        res[i][j][k] = 0;
                    }
                }
            }
        }
        System.out.println(res.length);
        return res;
    }

    public static byte[][][] DemoCalorius(int x, int y) {
        byte[][][] res = creaMap3D(x, y);
        if (y > 1) {
            res[7][7][7] = (byte) 2;
            res[7][12][7] = (byte) 2;
            res[7][7][12] = (byte) 2;
            res[7][12][12] = (byte) 2;

            res[7][32][7] = (byte) 2;
            res[7][32][12] = (byte) 2;
            res[7][37][7] = (byte) 2;
            res[7][37][12] = (byte) 2;
        }
        if (y > 0) {
            for (int i = 2; i < res[2].length; i += 5) {
                for (int j = 2; j < res[0][i].length; j += 5) {
                    res[2][i][j] = 5;
                }
            }
        }
        return res;
    }

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
                                this.addToWorld(i, j, k, 4);
                            } else {
                                this.addToWorld(i, j, k, 5);
                            }
                        }
                    } else if (k >= 7 && (k - 2) % 5 == 0) {
                        if (((j == 2) && (i - 2) % 5 == 0)
                                || ((j == (this.width * 5) - 3) && (i - 2) % 5 == 0)
                                || ((i == 2) && (j - 2) % 5 == 0)
                                || ((i == (this.width * 5) - 3) && (j - 2) % 5 == 0)) {
                            this.addToWorld(i, j, k, 6);
                        } else if (k == 7) {
                            if ((i == 7 || i == 12 || i == 32 || i == 37) && (j == 7 || j == 12)) {
                                this.addToWorld(i, j, k, 2);
                            }                         
                            if(i == 22 && j == 32) {
                                addHouse(1000000, i, j, k);
                            }
                        }
                    }
                }
            }
        }
    }

    public world(byte[][] mApa) {
        this.width = mApa.length;
        this.height = mApa[0].length;
        this.byMap = new Block[0];
        this.MagiaOcupada = 0;
        this.hechizos = new Magia[0];
        for (int i = 0; i < mApa.length; i++) {
            for (int j = 0; j < mApa[i].length; j++) {
                switch (mApa[i][j]) {
                    case 1:
                        Block[] aux = new Block[this.byMap.length + 1];
                        for (int z = 0; z < this.byMap.length; ++z) {
                            aux[z] = this.byMap[z];
                        }
                        aux[this.byMap.length] = new Block(i, j, mApa[i][j]);
                        this.byMap = aux;
                        break;
                }
            }
        }
    }

    public world(byte[][][] mApa) {
        this.width = mApa[0].length;
        this.height = mApa[0][0].length;
        this.byMap = new Block[0];
        this.MagiaOcupada = 0;
        this.hechizos = new Magia[0];

        this.LiveForm = new Monster[1];
        this.LiveForm[0] = new Monster((byte) 0, "Calorius", "BOSS");

        for (int i = 0; i < mApa.length; i++) {
            for (int j = 0; j < mApa[i].length; j++) {
                for (int k = 0; k < mApa[i][j].length; k++) {
                    switch (mApa[i][j][k]) {
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                            Block[] aux = new Block[this.byMap.length + 1];
                            for (int z = 0; z < this.byMap.length; ++z) {
                                aux[z] = this.byMap[z];
                            }

                            aux[this.byMap.length] = new Block(j, k, i, mApa[i][j][k]);
                            this.byMap = aux;
                            break;
                    }
                }
            }
        }
    }

    
    public void addHouse(int Type, int x, int y, int z){
        Edify.addHouse(this, Type, x, y, z);
    }
    
    public final void addToWorld(int x, int y, int z, int Type) {

        if (Type < 1000) {
            Block[] aux = new Block[this.byMap.length + 1];
            for (int i = 0; i < this.byMap.length; ++i) {
                aux[i] = this.byMap[i];
            }

            aux[this.byMap.length] = new Block(x, y, z, (byte) Type);
            this.byMap = aux;
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
                    int objX = (this.Player.getX() - this.LiveForm[i].getX()) >> 7;
                    int objY = (this.Player.getY() - this.LiveForm[i].getY()) >> 7;

                    //int modulo = 100;
                    moveInWorld(this.LiveForm[i], objX, objY);

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

    /*  
    public Rect[] toPaint(){
        this.out= new Rect[1 + this.byMap.length + this.hechizos.length + this.LiveForm.length];
        this.out[0] = new Rect(this.Player);
        this.out[0].actualMage(this.Player);
        int toRest;
        
        for(int i = 1; i < 1 + this.byMap.length; ++i){
            this.out[i] = new Rect();
            if(this.byMap[i-1] != null) {
                this.byMap[i-1].toRect(this.out[i]);
            }
        }
        toRest = 1 + this.byMap.length;
        for(int i = toRest; i < toRest + this.hechizos.length; ++i){
            if(this.hechizos[i - toRest] != null) {
                this.out[i] = new Rect();
                this.hechizos[i - toRest].toRect(this.out[i]);
            }
        }
        toRest += this.hechizos.length;
        for(int i = toRest; i < toRest + this.LiveForm.length; ++i) {
            if(this.LiveForm[i - toRest] != null) {
                this.out[i] = new Rect();
                this.LiveForm[i - toRest].toRect(this.out[i]);
            }
        }
        return this.out;
    }*/

    public Rect[] toPaint() {
        Rect auxi = new Rect();

        this.Player.toRect(auxi);
        this.out2.add(auxi.clone());

        for (int i = 0; i < this.byMap.length; ++i) {
            this.byMap[i].toRect(auxi);
            this.out2.add(auxi.clone());
        }

        for (int i = 0; i < this.hechizos.length; ++i) {
            if (this.hechizos[i] != null) {
                this.hechizos[i].toRect(auxi);
                this.out2.add(auxi.clone());
            }
        }
        for (int i = 0; i < this.LiveForm.length; ++i) {
            if (this.LiveForm[i] != null) {
                this.LiveForm[i].toRect(auxi);
                this.out2.add(auxi.clone());
            }
        }
        for (int i = 0; i < this.VillagePeople.length; ++i) {
            if (this.VillagePeople[i] != null) {
                this.VillagePeople[i].toRect(auxi);
                this.out2.add(auxi.clone());
            }
        }

        Rect[] a = this.out2.toArrayRect();
        this.out2.vaciarLista();
        return a;
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