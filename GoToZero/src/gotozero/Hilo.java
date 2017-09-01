package gotozero;

import gotozero.Constructor.CityMaker;
import gotozero.Constructor.Negocios.Negocio;

/**
 * Write a description of class Hilo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */ 
public class Hilo extends Thread{
    
    /**
     * EstadoMaquina Indice de valores:
     *
     * -34 Modo Maid
     *
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
    private PantallaOut frame = new PantallaOut();
    
    private cam Camara;
    private Mage player;
    private world World;
    private Rect[] output;
    
    private int countS = 0, s2 = 0, s10 = 0;
    
    private byte ciclosParaFPS;    
        
    public Hilo(int i) {
        this.EstadoMaquina = (byte)0;
        
        this.frame.setEstado(this.EstadoMaquina);
        
        this.Camara = frame.getCam();
        this.World = null;    
                
        this.ciclosParaFPS = 0;
        
        this.player = new Mage((byte)0);

        this.Camara.setFocus(this.player);

        this.output = new Rect[2];
        this.output[0] = new Rect(player);
        this.output[1] = new Rect();
    }

    public void setMap(){
        //this.World = new world(world.DemoCalorius(250,4));
        this.World = new world(100, 2);
        Negocio.inicializeList();
        this.World.setPlayer(this.player);
        this.World.teleportToPoint(CityMaker.InitialPoint.x,CityMaker.InitialPoint.y);
        this.frame.setWorldSizeX(this.World.getNumberRX());
        this.frame.setWorldSizeY(this.World.getNumberRY());
        
        this.frame.setInputMD(this.World.getOutput());
       
        frame.setInput(this.output); 
    }

    public void modoCafe(){
        this.World = new world(100, 2,-34);
        Negocio.inicializeList();
        this.player.setEmpresa(new Negocio());
        this.World.setPlayer(this.player);
        this.World.teleportToPoint(CityMaker.InitialPoint.x,CityMaker.InitialPoint.y);
        this.frame.setWorldSizeX(this.World.getNumberRX());
        this.frame.setWorldSizeY(this.World.getNumberRY());

        this.frame.setInputMD(this.World.getOutput());

        frame.setInput(this.output);
    }

    /**
     * Bucle principal de juego, en el que se llaman a los metodos que modifican al mundo a partir de los eventos, o
     * la misma naturaleza.
     */
    @Override
    public void run(){
        this.frame.setVisible(true);
        while(true){
            try{
                long init = System.nanoTime();
                switch(this.EstadoMaquina){
                    case -34: //Modo MimiCoffe
                        this.Camara.actualMouse();
                        this.World.setInput(this.frame.teclado);
                        this.World.liveCicle();

                        switch((this.frame.teclado & 0b00001111)) {
                            case 1:
                            case 11:
                                this.Camara.aument(0, -2);
                                break;
                            case 2:
                            case 7:
                                this.Camara.aument(2, 0);
                                break;
                            case 3:
                                this.Camara.aument(2, -2);
                                break;
                            case 4:
                            case 14:
                                this.Camara.aument(0, 2);
                                break;
                            case 6:
                                this.Camara.aument(2, 2);
                                break;
                            case 8:
                            case 13:
                                this.Camara.aument(-2, 0);
                                break;
                            case 9:
                                this.Camara.aument(-2, -2);
                                break;
                            case 12:
                                this.Camara.aument(-2, 2);
                                break;
                        }

                        if(this.Camara.getMouseX() > this.frame.getWidth() - 64){
                            this.Camara.aumX(4);
                        }
                        if(this.Camara.getMouseY() > this.frame.getHeight() - 64){
                            this.Camara.aumY(4);
                        }
                        if(this.Camara.getMouseX() < 64){
                            this.Camara.aumX(-4);
                        }
                        if(this.Camara.getMouseY() < 64){
                            this.Camara.aumY(-4);
                        }

                        if((this.frame.teclado & 0b10000) == 0b10000) {
                            Camara.actualMouse();

                            Block insertacion = new Block(((this.Camara.getXRest() + this.Camara.getMouseX() + 32) / 64) * 5, ((this.Camara.getMouseY() + this.Camara.getYRest() + 32) / 64) * 5, 5, (byte)5);
                            //this.World.addNPC(new NPC("img/Ciel.png", "Ciel Iris", (byte)1,((this.Camara.getMouseX())/64)*5,((this.Camara.getMouseY())/64)*5,7));
                            if(this.World.isInsertable(insertacion)) {
                                this.World.addToWorld(insertacion);
                                System.out.println("X: " + ((this.Camara.getXRest() + this.Camara.getMouseX()) / 64) * 5 + " Y: " + ((this.Camara.getMouseY() + this.Camara.getYRest()) / 64) * 5);
                            }
                        }

                        this.World.actualToPaint(this.Camara.getXRest(),this.Camara.getYRest(),this.Camara.getWidth(),this.Camara.getHeight());
                        this.frame.setEstado((byte) -34);
                        break;
                    case 0:
                        if((this.frame.teclado & 0b100000) == 0b100000){
                            this.frame.teclado -= 0b100000;
                            
                            if(this.frame.getSelected() == 1){
                                this.frame.setEstado((byte)99);
                                this.frame.repaint();
                                
                                this.setMap();
                                
                                this.EstadoMaquina = 2;
                                this.frame.clearImages();
                            } else if(this.frame.getSelected() == 4) {
                                this.frame.setEstado((byte)99);
                                this.frame.repaint();

                                this.modoCafe();

                                this.EstadoMaquina = -34;
                                this.frame.clearImages();
                                this.frame.teclado = 0;
                            }
                        } 
                        break;
                    case 2:    
                        this.World.setInput(this.frame.teclado);
                        this.World.mveHechizo();
                        this.World.liveCicle();

                        if((this.frame.teclado & 0b100000000) == 0) {
                            this.player.setDir((byte)((this.frame.teclado & 0b11000000) >> 6));

                            switch (this.frame.teclado & 0b1111) {
                                case 0b1011:
                                case 1:
                                    this.World.moveInWorld(this.player, 0, -4);
                                    break;
                                case 0b111:
                                case 0b10:
                                    this.World.moveInWorld(this.player, 4, 0);
                                    break;
                                case 0b1110:
                                case 0b100:
                                    this.World.moveInWorld(this.player, 0, 4);
                                    break;
                                case 0b1101:
                                case 0b1000:
                                    this.World.moveInWorld(this.player, -4, 0);
                                    break;
                                case 0b11:
                                    this.World.moveInWorld(this.player, 4, -4);
                                    break;
                                case 0b110:
                                    this.World.moveInWorld(this.player, 4, 4);
                                    break;
                                case 0b1100:
                                    this.World.moveInWorld(this.player, -4, 4);
                                    break;
                                case 0b1001:
                                    this.World.moveInWorld(this.player, -4, -4);
                                    break;
                            }
                        }

                        if((this.frame.teclado & 0b10000) == 0b10000) {
                            Camara.actualMouse();
                            this.World.addHechizo(new Magia((byte)1,this.player ,Camara.relativeMX(), Camara.relativeMY(), 8));
                        }
                        if(this.Camara.getConversationMode()) {
                            if(!this.Camara.getEsperaDialogo() && (this.frame.teclado & 0b100000) == 0) {
                                this.Camara.setEsperaDialogo(true);
                            } else if (this.Camara.getEsperaDialogo() && (this.frame.teclado & 0b100000) == 0b100000) {
                                this.World.continueConversation();
                                String dial = this.World.getDialog();
                                if(dial.equals("null")){
                                    this.Camara.setDialog("");
                                    this.Camara.setConversationMode(false);
                                    this.Camara.setEsperaDialogo(false);
                                    this.Camara.removeImagLocutor();
                                    this.World.finishConversation();
                                    this.frame.teclado -= 0b100000;
                                    this.frame.teclado -= 0b100000000;
                                    this.Camara.setLocutorName("");
                                } else {
                                    this.Camara.setDialog(dial);
                                    this.Camara.setEsperaDialogo(false);
                                }
                            }
                        } else {
                            if ((this.frame.teclado & 0b100000) == 0b100000) {
                                if (!this.World.addInteraction()) {
                                    this.frame.teclado -= 0b100000;
                                } else {
                                    this.frame.teclado += 0b100000000;
                                    this.Camara.setLocutorName(this.World.getLocutorName());
                                    this.Camara.assignImagLocutor();
                                    this.Camara.setConversationMode(true);
                                    this.Camara.setDialog(this.World.getDialog());
                                }
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