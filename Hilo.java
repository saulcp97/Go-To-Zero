
/**
 * Write a description of class Hilo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */ 
public class Hilo extends Thread{
    private int id;
    private final int FPS = 60;
    private float delay;
    PantallaOut frame = new PantallaOut();
    private boolean vis;
    private cam Camara;
    private Mage player;
    private world World;
    private Rect[] output;
    
    private int countS = 0, s2 = 0, s10 = 0;
    
    private byte ciclosParaFPS;
    
    public Hilo(int i) {
        this.vis = false;
        this.id = i;
        this.Camara = frame.getCam();
        this.World = null;    
                
        this.ciclosParaFPS = 0;
        
        this.player = new Mage((byte)-1);

        this.Camara.setFocus(this.player);

        this.output = new Rect[2];
        this.output[0] = new Rect(player);
        this.output[1] = new Rect();
        frame.setInput(this.output); 
    }

    public void setMap(){
        this.World = new world(world.DemoCalorius(10,2));
        
        this.frame.setWorldSizeX(this.World.getNumberRX());
        this.frame.setWorldSizeY(this.World.getNumberRY());
        
        //Block[] aux = this.World.getByMap();
        
        this.World.setPlayer(this.player);
        this.output = this.World.toPaint();


        
        /**
        for(int i = 0; i < aux.length; i++) {
            if(aux[i] != null){
                this.output[i + 1] = new Rect();
                aux[i].toRect(this.output[1 + i]);
            }
            else{ this.output[i+1]=null; }
        }
        */
       
        frame.setInput(this.output); 
    }

    public void run(){
        while(true){
            try{
                long init = System.nanoTime();
                if(id < 10) {
                    id++;
                    System.out.println(id);
                }
                else {
                    if(!vis){
                        frame.setVisible(true);
                        vis = true;
                    }
                    else {
                        this.World.mveHechizo();
                        this.World.liveCicle();
                        
                        
                        /**
                        Magia[] dynamic = this.World.getHechizos();
                        Rect[] sent = new Rect[dynamic.length];
                        for (int i = 0; i < dynamic.length; i++) {
                                sent[i] = new Rect();
                            if (dynamic[i] != null) {    
                                dynamic[i].toRect(sent[i]);
                            }
                            else{sent[i] = null;}
                        }
                        frame.setInputMD(sent);
                        */
                        
                        
                       
                        if(frame.getPulsed()[0][0] == 1 && !(frame.getPulsed()[1][0] == 1)){
                            this.World.moveInWorld(this.player,-4,0);
                            this.player.setGrade((byte)45);
                        } else if(frame.getPulsed()[1][0] == 1 && !(frame.getPulsed()[0][0] == 1 )){
                            this.World.moveInWorld(this.player,4,0);
                            this.player.setGrade((byte)15);
                        }
                        
                        
                        if(frame.getPulsed()[0][1] == 1 && !(frame.getPulsed()[1][1] == 1)){
                            this.World.moveInWorld(this.player,0,-4);
                            this.player.setGrade((byte)0);
                        } else if(frame.getPulsed()[1][1] == 1 && !(frame.getPulsed()[0][1] == 1 )){
                            this.World.moveInWorld(this.player,0,4);
                            this.player.setGrade((byte)30);
                        }
                        
                        if(frame.getPulsed()[0][2] == 1) {
                            this.World.addHechizo(new Magia((byte)1,this.player ,Camara.relativeMX(), Camara.relativeMY()));
                        }
                        
                        /*if(frame.getPulsed()[1][2] == 73){
                            if(!Menus.Inventario.controlCh){
                                Menus.Inventario.setVisibility(!Menus.Inventario.getVisibility());
                                Menus.Inventario.controlCh = true;
                            }
                        }*/
                        /*
                        if(frame.getPulsed()[1][2] == -73) {
                            Menus.Inventario.controlCh = false;
                        }
                        */
                        /*
                        if(Menus.Inventario.getVisibility()) {
                            if(Menus.Inventario.getLMove() == -1) {
                                switch(frame.getPulsed()[1][2]) {
                                    case 37:
                                        Menus.Inventario.pLeft();
                                        Menus.Inventario.setLMove(37);
                                        break;
                                        
                                    case 38:
                                        Menus.Inventario.pUp();    
                                        Menus.Inventario.setLMove(38);
                                        break;
                                    
                                    case 39:
                                        Menus.Inventario.pRight();
                                        Menus.Inventario.setLMove(39);
                                        break;
                                    
                                    case 40:
                                        Menus.Inventario.pDown();
                                        Menus.Inventario.setLMove(40);
                                        break;
                                    
                                    
                                }
                            }
                            
                            if(frame.getPulsed()[1][2] == -37 || frame.getPulsed()[1][2] == -38 || frame.getPulsed()[1][2] == -39 || frame.getPulsed()[1][2] == -40) {
                                Menus.Inventario.setLMove(-1);
                                
                                
                            }
                        }
                        /*
                            if(this.player.getPosX() < 0) {
                                this.player.setPosX(0);
                            }
                            if(this.player.getPosY() < 0) {
                                this.player.setPosY(0);
                            }
                        */
                       
                       

                        Camara.actualitze();
                        this.output = this.World.toPaint();
                        
                        //this.output= null;
                        //Camara.aumX(5);
                        frame.setInput(this.output); 
                        frame.repaint();
                    }
                }
                
                if (countS == 0){
                    if(this.player.getSed() == 0) {
                        this.player.decrLife();
                    }
                    if(s2 == 0){
                        if(this.player.getSed() > 0) {
                            this.player.decrSed();
                        } else {
                            this.player.decrLife();
                        }
                        if(s10 == 0){
                            if(this.player.getHambre()> 0) {
                                this.player.decrHambre();
                            } else {
                                this.player.decrLife();
                            }
                            s10 = 5;
                        }
                        --s10;
                        s2 = 2;
                    }
                    --s2;
                    countS = 60;
                }
                --countS;
                
                this.sleep(1000/FPS);
                this.ciclosParaFPS--;
                if (this.ciclosParaFPS == 0){
                        long fin = System.nanoTime();
                        float FPSfinal = 1/((float)(fin-init)/1000000000);
                        System.out.println(FPSfinal);
                        this.frame.setTitle("Go To Zero | FPS: " + FPSfinal);
                        this.ciclosParaFPS = 50;
                    }
            }
            catch (InterruptedException e) {System.out.println(e);}
        }
    }
}