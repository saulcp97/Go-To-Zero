package gotozero;


/**
 * Write a description of class Hilo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */ 
public class Hilo extends Thread{
    
    /**
     * EstadoMaquina Indice de valores:
     * 0 MenuPrincipal
     * 
     * 1 Pantalla de selección de fichero de guardado
     * 2 Juego
     * 
     * 3 Menu de Musica
     * 
     * 4 Options
     * 
     */
    private byte EstadoMaquina;

    private final int FPS = 60;
    private float delay;
    PantallaOut frame = new PantallaOut();
    
    private cam Camara;
    private Mage player;
    private world World;
    private Rect[] output;
    
    private int countS = 0, s2 = 0, s10 = 0;
    
    private byte ciclosParaFPS;
    private byte[][] entry;
    
    
    
    public Hilo(int i) {
        this.EstadoMaquina = (byte)0;
        
        this.frame.setEstado(this.EstadoMaquina);
        
        this.Camara = frame.getCam();
        this.World = null;    
                
        this.ciclosParaFPS = 0;
        
        this.player = new Mage((byte)-1);

        this.Camara.setFocus(this.player);

        this.output = new Rect[2];
        this.output[0] = new Rect(player);
        this.output[1] = new Rect();
    }

    public void setMap(){
        //this.World = new world(world.DemoCalorius(250,4));
        this.World = new world(100,4);
        this.World.setPlayer(this.player);
        this.frame.setWorldSizeX(this.World.getNumberRX());
        this.frame.setWorldSizeY(this.World.getNumberRY());
        
        this.frame.setInputMD(this.World.getOutput());
        this.entry = this.frame.getPulsed();
        //Block[] aux = this.World.getByMap();
        //this.output = this.World.toPaint();


        
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

    /**
     *Bucle principal de juego
     */
    @Override
    public void run(){
        this.frame.setVisible(true);
        while(true){
            try{
                long init = System.nanoTime();
                
                switch(this.EstadoMaquina){
                    case 0:
                        if(this.frame.getPulsed()[1][1] == 10){
                            if(this.frame.getSelected() == 1){
                                this.frame.setEstado((byte)0b01100011);
                                this.frame.repaint();
                                
                                this.setMap();
                                
                                this.EstadoMaquina = 2;
                                this.frame.clearImages();
                                
                            }
                            this.frame.clearPressed();
                        } 
                    break;
                    
                    case 2:    
                        this.World.mveHechizo();
                        this.World.liveCicle();
                        
                        
                        
                        if(this.entry[0][0] == 1 && (this.entry[1][0] != 1)){
                            this.World.moveInWorld(this.player,-4,0);
                            this.player.setGrade((byte)0b00101101);
                        } else if(this.entry[1][0] == 1 && (this.entry[0][0] != 1 )){
                            this.World.moveInWorld(this.player,4,0);
                            this.player.setGrade((byte)0b00001111);
                        }
                        
                        if(this.entry[0][1] == 1 && (this.entry[1][1] != 1)){
                            this.World.moveInWorld(this.player,0,-4);
                            this.player.setGrade((byte)0b0);
                        } else if(this.entry[1][1] == 1 && (this.entry[0][1] != 1 )){
                            this.World.moveInWorld(this.player,0,4);
                            this.player.setGrade((byte)0b11110);
                        }
                        
                        if(this.entry[0][2] == 1) {
                            Camara.actualMouse();
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
                
                        this.Camara.actualitze();
                        //this.output = this.World.toPaint();

                        this.World.actualToPaint(this.Camara.getXRest(),this.Camara.getYRest(),this.Camara.getWidth(),this.Camara.getHeight());
                        this.frame.setEstado(this.EstadoMaquina);
                        //frame.setInput(this.output); 
                            
                break;
            }
                         
                //VVVVV Imprescindible VVVVVV
                
                this.frame.repaint();
                
                Hilo.sleep(1000/FPS);
                this.ciclosParaFPS--;
                if (this.ciclosParaFPS == 0){
                        long fin = System.nanoTime();
                        float FPSfinal = 1/((float)(fin-init)/1000000000);
                        System.out.println(FPSfinal);
                        this.ciclosParaFPS = 60;
                    }
            }
            catch (InterruptedException e) {System.out.println(e);}
        }
    }
}