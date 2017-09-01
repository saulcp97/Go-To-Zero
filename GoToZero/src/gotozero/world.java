package gotozero;

//import gotozero.Data.List;
import gotozero.Bloques.Cofre;
import gotozero.Bloques.Shoop;
import gotozero.Constructor.CityMaker;
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

    public Block[] Interactuable;
    private String Dialog;
    private ListRect out2 = new ListRect();

    private Monster[] LiveForm;
    private NPC[] VillagePeople;

    private NPC Locutor;
    
    private int input;

    private Cofre[] loot;

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
/* Prueba del mundo)
        this.loot = new Cofre[0];
        this.byMap = new Block[0];
        this.Interactuable = new Block[0];
        this.MagiaOcupada = 0;
        this.hechizos = new Magia[0];
        //Creación del Array que contiene a los monstruos.
        this.LiveForm = new Monster[1];
        //Creación de los monstruos.
        //this.LiveForm[2] = new Monster((byte) 0, "Calorius", "BOSS");
        this.LiveForm[0] = new Monster((byte) 0, "Slime", "Borracho1");
        //this.LiveForm[1] = new Monster((byte) 0, "Scarjav", "BOSS");
        //Reposicionamiento de los enemigos.
        //this.LiveForm[2].reubicar(1024, 1024, 600);

        this.Dialog = "";
        this.Locutor = null;
        
        this.VillagePeople = new NPC[0];
        this.addNPC(new NPC("img/Ciel.png", "Ciel Iris"));

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
                        
                        if(i == 27 && j == 27) {
                            addHouse(243563, i, j, k);
                            //addHouse(101, i, j, k);
                        }
                        
                        
                    } else if (k >= 7 && (k - 2) % 5 == 0) {
                        if (((j == 2) && (i - 2) % 5 == 0)
                                || ((j == (this.width * 5) - 3) && (i - 2) % 5 == 0)
                                || ((i == 2) && (j - 2) % 5 == 0)
                                || ((i == (this.width * 5) - 3) && (j - 2) % 5 == 0)) {
                            this.addToWorld(i, j, k, 6);
                        } else if (k == 7) {
                            if(i == 27 && j == 27) {
                                addToWorld(10, 10, 7, 12);
                            }
                        }
                    }
                }
            }
        }
        */

        //OrdenBlock.quicksort(byMap);
        this.resetMap();
        CityMaker.loader.loadByFile(this,"-777");
        System.out.println("Numero de cubos sin Comprimir: " + this.byMap.length);
        this.compactCubes();
        System.out.println("Numero de cubos totales: " + this.byMap.length);


        //CityMaker.zonaTutorial(this);

        //Fijar Bug de index y prioridades
        //OrdenBlock.quicksort(byMap);



    }

    public world(int MapL, int height, int MapaCar) {
        this.width = MapL;
        this.height = height;
        //OrdenBlock.quicksort(byMap);
        this.resetMap();
        CityMaker.loader.loadByFile(this,"" + MapaCar);
        System.out.println("Numero de cubos sin Comprimir: " + this.byMap.length);
        this.compactCubes();
        System.out.println("Numero de cubos totales: " + this.byMap.length);
    }


    public void teleportToPoint(int x, int y) {
        this.Player.setPosicion(x,y);
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

    private void addLooot(Cofre cf){
        Cofre[] lista = new Cofre[this.loot.length + 1];

        for(int i = 0; i < this.loot.length; ++i){
            lista[i] = this.loot[i];
        }
        lista[this.loot.length] = cf;
        this.loot = lista;
    }

    public void addNPC(NPC n) {
        NPC[] listaVip = new NPC[this.VillagePeople.length + 1];
        
        for(int i = 0; i < this.VillagePeople.length; ++i){
            listaVip[i] = this.VillagePeople[i];
        }
        listaVip[this.VillagePeople.length] = n;
        this.VillagePeople = listaVip;

        Block[] lBloques = new Block[this.Interactuable.length + 1];

        for(int i = 0; i < this.Interactuable.length; ++i) {
            lBloques[i] = this.Interactuable[i];
        }
        lBloques[this.Interactuable.length] = n;
        this.Interactuable = lBloques;
        addToWorld(n);
    }

    public void addMonster(Monster n) {
        Monster[] listaVip = new Monster[this.LiveForm.length + 1];

        for(int i = 0; i < this.LiveForm.length; ++i){
            listaVip[i] = this.LiveForm[i];
        }
        listaVip[this.LiveForm.length] = n;
        this.LiveForm = listaVip;
    }

    public void addToWorld(Block bl) {
        Block[] lista = new Block[this.byMap.length + 1];

        for(int i = 0; i < this.byMap.length; ++i){
            lista[i] = this.byMap[i];
        }
        lista[this.byMap.length] = bl;
        this.byMap = lista;
        //this.compactCubes();
        OrdenBlock.changedBlock();
        OrdenBlock.quicksort(this.byMap);
    }

    public boolean isInsertable(Block entrada) {
        for (int i = 0; i < this.byMap.length; ++i){
            if(this.byMap[i].collision(entrada)){
                return false;
            }
        }
        return true;
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
        this.compactCubes();
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

                    Cofre looteo = new Cofre(this.LiveForm[i].getX(),this.LiveForm[i].getY(),this.LiveForm[i].getZ());
                    looteo.addToCofre(this.LiveForm[i].getLoot());
                    this.addLooot(looteo);
                    this.LiveForm[i] = null;

                } else {
                    this.gravedad(this.LiveForm[i]);
                    if (this.LiveForm[i].getZ() < 0) {
                        this.LiveForm[i].setZ(0);
                    }
                    
                    //Inteligencia artificial
                    this.LiveForm[i].IA(this);
                }
            }
        }
    }

    public void moveInWorld(lifes m, int x, int y) {
        m.move(x, y);
        boolean aux = true;
        for (int i = 0; i < this.byMap.length && aux; i++) {
            if (this.byMap[i] != null && !this.byMap[i].equals(m)) {
                aux = !this.byMap[i].collision(m);
            }
            if(i < this.loot.length && this.loot[i] != null && this.loot[i].Tipo == 13 && this.loot[i].collision(m)) {
                this.loot[i].volcarItems(m.getInventario());
                this.loot[i] = null;
            }
        }
        if (!aux) {
            m.move(-x, -y);
        }
    }

    public void resetMap(){
        /*private Magia[] hechizos;
        private int MagiaOcupada;
        private Block[] byMap;

        public Block[] Interactuable;
        private String Dialog;
        private ListRect out2 = new ListRect();

        private Monster[] LiveForm;
        private NPC[] VillagePeople;

        private NPC Locutor;

        private int input;

        private Cofre[] loot;*/

        this.MagiaOcupada = 0;
        this.hechizos = new Magia[0];
        this.byMap = new Block[0];

        this.Interactuable = new Block[0];
        this.Dialog = "";
        this.out2 = new ListRect();

        this.LiveForm = new Monster[0];
        this.VillagePeople = new NPC[0];
        this.loot = new Cofre[0];
        this.Locutor = null;

        this.input = 0;
    }


    public boolean addInteraction() {
        Sphere auxInteract = new Sphere();
        switch(this.Player.getDir()) {
            case 0://w
                auxInteract.setX(this.Player.getX() + (this.Player.getWidth()>>1));
                auxInteract.setY(this.Player.getY() + (this.Player.getHeight()>>1) - 32);
                auxInteract.setZ(this.Player.getZ() + (this.Player.getDeep()>>1));
                break;
            case 1://d
                auxInteract.setX(this.Player.getX() + (this.Player.getWidth()>>1) + 32);
                auxInteract.setY(this.Player.getY() + (this.Player.getHeight()>>1));
                auxInteract.setZ(this.Player.getZ() + (this.Player.getDeep()>>1));
                break;
            case 2://s
                auxInteract.setX(this.Player.getX() + (this.Player.getWidth()>>1));
                auxInteract.setY(this.Player.getY() + (this.Player.getHeight()>>1) + 32);
                auxInteract.setZ(this.Player.getZ() + (this.Player.getDeep()>>1));
                break;
            case 3://a
                auxInteract.setX(this.Player.getX() + (this.Player.getWidth()>>1));
                auxInteract.setY(this.Player.getY() + (this.Player.getHeight()>>1) - 32);
                auxInteract.setZ(this.Player.getZ() + (this.Player.getDeep()>>1));
                break;
        }
        for(int i = 0; i < Interactuable.length; ++i) {
            if(Interactuable[i].collision(auxInteract)){
                //this.Player.move(-64,+64);
                if(this.Interactuable[i] instanceof NPC) {
                    this.Locutor = (NPC)this.Interactuable[i];
                    this.Dialog = this.Locutor.getDialog();
                    return true;
                } else if(this.Interactuable[i] instanceof Shoop) {

                }
            }
        }
        this.Dialog = "";
        return false; //No hay interacción, se puede lanzar otra
    }

    public void finishConversation() {
        this.Locutor = null;
        this.Dialog = "";
    }

    public void continueConversation() {
        this.Dialog = this.Locutor.getDialog();
    }

    public String getLocutorName() { return this.Locutor.getName(); }


    public void setPlayer(Mage A) {
        this.Player = A;
    }

    public Mage getPlayer() {
        return this.Player;
    }
    
    public int getNumberRX() {
        return this.width / world.PARTES;
    }

    public int getNumberRY() {
        return this.height / world.PARTES;
    }

    private void sigDialog() {}
    public String getDialog() { return this.Dialog; }

    public void setDialog(String aux) { this.Dialog = aux; }


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
        for (int i = 0; i < this.loot.length; ++i) {
            if (this.loot[i] != null) {
                this.loot[i].toRect(auxi);
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
    
    public void setInput(int in) {
        this.input = in;
    }

    public int getInput() {
        return this.input;
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
                    } else if(this.hechizos[i].getElement() < 10){
                        if (j < this.LiveForm.length && this.LiveForm[j] != null) {
                            if (this.LiveForm[j].collision(this.hechizos[i])) {
                                this.hechizos[i].dismDurabilidad(150);
                                this.LiveForm[j].decrLife();
                                System.out.println("Enemigo colisionado con bala!!");
                            }
                        }
                    }
                }
                //Cuando chocan con el jugador
                if(this.hechizos[i].getElement() >= 10 && this.Player.collision(this.hechizos[i])){
                    this.hechizos[i].dismDurabilidad(1500);
                    this.Player.decrLife();
                    System.out.println("Me han dado con una bala!!");
                }
                //Mover hechizos individualmente
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