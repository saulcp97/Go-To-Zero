
/**
 * Write a description of class world here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class world
{
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

    private Monster[] LiveForm;

    public static final byte[][] exampleOfMap = new byte[][]{{0,0,0,0,0},{0,0,0,0,0},{0,0,1,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,1,0,0},{0,0,0,0,0},{0,0,0,0,0}};

    public static byte[][] creaMapBord(int x){
        byte res[][] = new byte[x*5][];
        for(int i = 0; i < x*5; i++){
            res[i] = new byte[x*5];
            for(int j = 0; j < x*5; j++){
                if(i == 2 && j >= 2 && (j <= x*5 - 3) || j == 2 && i >= 2 && (i <= x*5 - 3) || (i == x*5 - 3) && (j <= x*5 - 3) && j >= 2
                || (j == x*5 - 3) && (i <= x*5 - 3) && i >= 2){
                    if((i-2)%5 == 0 && (j-2)%5 == 0){
                        res[i][j] = 1;
                    }
                } else { res[i][j] = 0; }
            }
        }
        return res;
    }

    public static byte[][][] creaPuente(int x,int y) {
        byte res[][][] = new byte[y*5][][];
        for (int i = 0; i < res.length; ++i) {
            res[i] = new byte[x*5][];
            for(int j = 0; j < res[i].length; ++j) {
                res[i][j] = new byte[x*5];
                for(int k = 0; k < res[i][j].length; ++k) {
                    if(i == res.length - 1 && (j - 2) % 5 == 0 && k-2 == 0) {
                        res[i][j][k] = 1;
                    } else { res[i][j][k] = 0;}
                }
            }
        }
        return res;
    }

    public static byte[][][] creaNull(int x,int y) {
        byte res[][][] = new byte[y][][];
        for (int i = 0; i < res.length; ++i) {
            res[i] = new byte[x*5][];
            for(int j = 0; j < res[i].length; ++j) {
                res[i][j] = new byte[x*5];
                for(int k = 0; k < res[i][j].length; ++k) {
                    res[i][j][k] = 0;
                }
            }
        }
        return res;
    }

    public static byte[][] creaMapAll(int x) {
        byte res[][] = new byte[x*5][];
        for(int i = 0; i < x * 5; ++i) {
            res[i] = new byte[x * 5];
            for (int j = 0; j < x * 5; ++j) {
                if ((i - 2) % 5 == 0 && (j - 2) % 5 == 0) {

                    res[i][j] = 1;

                } else { res[i] [j] = 0; }

            }
        }
        System.out.println(res.length);
        return res;
    }

    public static byte[][][] creaMap3D(int x, int y) {
        byte [][][] res = new byte[y * 5][][];
        for (int i = 0; i < res.length; ++i) {
            if(i == 2) {
                res[i] = world.creaMapAll(x);
            } else if((i - 2) % 5 == 0) {
                res[i] = world.creaMapBord(x);
            } else {
                res[i] = new byte[x*5][];
                for(int j = 0; j < x*5; j++){
                    res[i][j] = new byte[x*5];
                    for(int k = 0; k < x*5; k++){
                        res[i][j][k] = 0;
                    }
                }
            }
        }
        System.out.println(res.length);
        return res;
    }

        public static byte[][][] DemoCalorius(int x, int y) {
        byte[][][] res = creaMap3D(x,y);
        if(y > 1) {
            res[7][7][7] = (byte) 2;
            res[7][12][7] = (byte) 2;
            res[7][7][12] = (byte) 2;
            res[7][12][12] = (byte) 2;
            
            res[7][32][7] = (byte)2;
            res[7][32][12] = (byte)2;
            res[7][37][7] = (byte)2;
            res[7][37][12] = (byte)2;
        }
        
        return res;
    }
    
    
    /**
     * Constructor for objects of class world
     */
    public world(int width, int height) {
        this.width = width;
        this.height = height;

        this.byMap = new Block[this.getNumberRX()*this.getNumberRY()];
        this.used = 0;
        this.Dynamik = new Block[this.used];
    }

    public world(byte[][] mApa){
        this.width = mApa.length;
        this.height = mApa[0].length;
        this.byMap = new Block[0];
        this.MagiaOcupada = 0;
        this.hechizos = new Magia[0];
        for(int i = 0; i < mApa.length; i++){
            for(int j = 0; j < mApa[i].length; j++) {
                switch(mApa[i][j]){
                    case 1:
                    Block[] aux = new Block[this.byMap.length + 1];
                    for(int z = 0; z < this.byMap.length; ++z) {
                        aux[z] = this.byMap[z];
                    }
                    aux[this.byMap.length] = new Block(i,j,mApa[i][j]);
                    this.byMap = aux;
                    break;
                }         
            }
        }
    }

    public world(byte[][][] mApa){
        this.width = mApa[0].length;
        this.height = mApa[0][0].length;
        this.byMap = new Block[0];
        this.MagiaOcupada = 0;
        this.hechizos = new Magia[0];

        this.LiveForm = new Monster[1];
        this.LiveForm[0] = new Monster((byte)0,"Calorius","BOSS");

        for(int i = 0; i < mApa.length; i++){
            for(int j = 0; j < mApa[i].length; j++) {
                for(int k = 0; k < mApa[i][j].length; k++) {
                    switch(mApa[i][j][k]){
                        case 1:
                        case 2:
                            Block[] aux = new Block[this.byMap.length + 1];
                            for(int z = 0; z < this.byMap.length; ++z) {
                                aux[z] = this.byMap[z];
                            }
                            aux[this.byMap.length] = new Block(j,k,i,mApa[i][j][k]);
                            this.byMap = aux;
                            break;
                    }         
                }
            }
        }
    }
    
    public void gravedad(lifes m) {
        m.caer(4);
        boolean aux = true;
        for(int i = 0; i < this.byMap.length && aux; i++) { 
            if(this.byMap[i] != null) {
                aux = !this.byMap[i].collision((Block)m);   
            }
        }
        if(!aux) { m.caer(-4);}
    }

    public void liveCicle(){
        this.gravedad(this.Player);
        if(this.Player.getZ() < 0) {   this.Player.setZ(0); }
        for(int i = 0; i < this.LiveForm.length; ++i) {
            if(this.LiveForm[i] != null) {
                this.gravedad(this.LiveForm[i]);
                if(this.LiveForm[i].getZ() < 0) {
                    this.LiveForm[i].setZ(0);
                }
            }
        }
    }

    public void moveInWorld(Mage m,int x, int y) {
        m.move(x,y);
        boolean aux = true;
        for(int i = 0; i < this.byMap.length && aux; i++) { 
            if(this.byMap[i] != null) {
                aux = !this.byMap[i].collision((Block)m);   
            }
        }
        if(!aux){m.move(-x,-y);}
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

    public Rect[] toPaint(){
        Rect[] auxi = new Rect[1 + this.byMap.length + this.hechizos.length + this.LiveForm.length];
        auxi[0] = new Rect(this.Player);
        auxi[0].actualMage(this.Player);
        int toRest = 0;
        
        for(int i = 1; i < 1 + this.byMap.length; ++i){
            auxi[i] = new Rect();
            if(this.byMap[i-1] != null) {
                this.byMap[i-1].toRect(auxi[i]);
            }
        }
        toRest = 1 + this.byMap.length;
        for(int i = toRest; i < toRest + this.hechizos.length; ++i){
            if(this.hechizos[i - toRest] != null) {
                auxi[i] = new Rect();
                this.hechizos[i - toRest].toRect(auxi[i]);
            }
        }
        toRest += this.hechizos.length;
        for(int i = toRest; i < toRest + this.LiveForm.length; ++i) {
            if(this.LiveForm[i - toRest] != null) {
                auxi[i] = new Rect();
                this.LiveForm[i - toRest].toRect(auxi[i]);
            }
        }

        return auxi;
    }

    public Block[] getByMap() {
        return this.byMap;
    }

    public Magia[] getHechizos(){
        return this.hechizos;
    }

    public void addHechizo(Magia a) {
        Magia[] aux = new Magia[this.hechizos.length + 1];
        for(int i = 0; i < this.hechizos.length; i++) {
            aux[i] = this.hechizos[i];
        }
        aux[this.hechizos.length] = a;
        this.hechizos = aux;
        this.MagiaOcupada++;
    }

    public void mveHechizo() {
        for(int i = 0; i < this.hechizos.length; i++) {  
            for(int j = 0; j < this.byMap.length; ++j) {
                if(this.byMap[j].collision(this.hechizos[i])){
                    this.hechizos[i].dismDurabilidad(1000);
                }
                
                if(j < this.LiveForm.length && this.LiveForm[j] != null) {
                    if(this.LiveForm[j].collision(this.hechizos[i])) {
                        this.hechizos[i].dismDurabilidad(150);
                        this.LiveForm[j].decrLife();
                        System.out.println("Colisionado con bala!!");
                    
                        if(this.LiveForm[j].getLife() < 0) {
                            this.LiveForm[j] = null;
                        }
                    }
                }
            }
            if(this.hechizos[i] != null) {
                this.hechizos[i].move();
                if(this.hechizos[i].getDurabilidad() != -404) {
                    this.hechizos[i].dismDurabilidad(1);    
                    if(this.hechizos[i].getDurabilidad() < 0) { 
                        this.hechizos[i] = null; this.MagiaOcupada--;
                    }
                }
            }       
        }
    }
}

