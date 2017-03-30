package gotozero;

import gotozero.Organice.ListRect;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Dimension;

import java.awt.Toolkit;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
* Write a description of class PantallaOut here.
* 
* @author (your name) 
* @version (a version number or a date)
*/

@SuppressWarnings("serial")
public class PantallaOut extends JFrame{
    private JPanel contentPane;
    //La camara normal que debe incluir una funcion Zoom (en el futuro)
    private cam Camara;
    private byte EstadoMaquina = 0;
    private byte selected;
    //String tecla pulsada
    private String pressed;
    private byte[][] puls = new byte[2][];

    //Numero de casillas a representar
    private int sizeWorldX;
    private int sizeWorldY;

    //Lista de Rect's recibida por larte de Hilo, que indica donde dibujar
    private Rect[] input;

    //Separado del tipo Input Standar de forma provisional hasta futuras actualizaciones
    private Rect[] inputMD;

    private ListRect ListInput;
    
    private Image menuIni;
    private Image menuIniDark;
    private Image menuIniSelector;
    //Necesarios para el pintado sin parpaedo
    private Image dibujoAux;
    private Graphics gAux;
    private Dimension dimAux;
    private Dimension dimCanvas;

    private Image canvSuelo;
    private boolean Floored;

    /**
     * Launch the application.
     * @param args
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PantallaOut frame = new PantallaOut();
                
                frame.setVisible(true);
                System.setProperty("sun.java2d.opengl", "true");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public byte getSelected(){
        return this.selected;
    }
    
    public void clearImages() {
        this.menuIni = null;
        this.menuIniDark = null;
        this.menuIniSelector = null;
    }
    
    public void setEstado(byte m){
        this.EstadoMaquina = m;
    }
    
    public cam getCam() {
        return Camara;    
    }

    public void setInput(Rect[] in) {
        this.input = in;
    }

    public void setInputMD(ListRect in) {
        this.ListInput = in;
    }

    public void setWorldSizeX(int x) {
        this.sizeWorldX = x;
    }

    public void setWorldSizeY(int y) {
        this.sizeWorldY = y;
    }
    
    public void clearPressed(){
        for(int i = 0; i < this.puls.length; ++i){
            for(int j = 0; j < this.puls[i].length; ++j){
                this.puls[i][j] = 0;
            }
        }
        
    }

    public String getPressed() {
        if(this.pressed != null) {  
            return this.pressed;
        }
        else {
            return "";
        }   
    }

    public byte[][] getPulsed(){
        return this.puls;
    }

    /**
     * Create the frame.
     */
    public PantallaOut() {
        Camara = new cam();
        this.pressed = null;
        addKeyListener(new MyKeyListener());

        this.sizeWorldX = 0;
        this.sizeWorldY = 0;

        for (int i = 0; i < this.puls.length; i++) {
            this.puls[i] = new byte[3];
            this.puls[i][0] = 0;
            this.puls[i][1] = 0;
            this.puls[i][2] = 0;
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = new JPanel();
        this.setTitle("Go To Zero");
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        this.setResizable(false); 

        contentPane.setLayout(null);
        contentPane.setBounds(64,64,650,650);

        //setBounds(0,0,650,700);
        setSize(500, 300);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setUndecorated(true);

        dimCanvas = this.getSize();

        this.canvSuelo = null;
        this.Floored = false;
        
        
        this.menuIni = new sprite("img/menuPrincipal.png").getImg();
        this.menuIniDark = new sprite("img/menuPrincipalDark.png").getImg();
        this.menuIniSelector = new sprite("img/selectedMenuP.png").getImg();
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }
    // faltrn lolis
    @Override 
    public void paint (Graphics g) {
        //super.paint(g);
        if(gAux == null || dimAux == null || dimCanvas.width != dimAux.width 
        || dimCanvas.height != dimAux.height) {
            dimAux = dimCanvas;
            dibujoAux = createImage(dimAux.width, dimAux.height);
            gAux = dibujoAux.getGraphics();
        }
        
        switch(this.EstadoMaquina) {
            case 0:
                if(Math.random()*100 < 0.5){
                    gAux.setColor (Color.black);
                    gAux.fillRect(0,0,dimAux.width, dimAux.height);
                    gAux.drawImage(this.menuIniDark, (getWidth() - 500)>>1, (getHeight() - 300)>>1, null);
                } else {
                    
                    gAux.setColor (Color.white);
                    gAux.fillRect(0,0,dimAux.width, dimAux.height);
                    gAux.drawImage(this.menuIni, (getWidth() - 500)>>1, (getHeight() - 300)>>1, null);
                    
                    if(this.selected != 0) {
                        gAux.drawImage(this.menuIniSelector, (getWidth()>>1)+95, ((getHeight() - 300)>>1) + 55 + this.selected * 40, null);
                    }
                    
                }
                break;
            
            case 2:   
                
                gAux.setColor (Color.white);
                gAux.fillRect(0,0,dimAux.width, dimAux.height);


                gAux.setColor (Color.blue);
        /*
                for(int i = 1; i < this.input.length; ++i){
                    if(this.input[i] != null){
                        int aX = this.input[i].getX()-Camara.getXRest();
                        int aY = this.input[i].getY()-Camara.getYRest();
                        //se concede el tamaño de 2 cuadros por simpatia
                        if(aX + this.input[i].getWidth() > 0 && aX < dimAux.width && aY + this.input[i].getHeight() > 0 && aY < dimAux.height) {
                            switch(this.input[i].getType()) {
                                case 0:
                                gAux.setColor (this.input[i].getCol());
                                gAux.fillRect (aX, aY, this.input[i].getWidth(), this.input[i].getHeight());
                                break;
                                case 1:
                                gAux.drawImage(this.input[i].getOutput(),aX, aY,null);
                                break;
                            }
                        }
                    }
                }

                gAux.drawImage(this.input[0].getOutput(), this.input[0].getX()-Camara.getXRest(), this.input[0].getY()-Camara.getYRest(),null);
        */
                this.ListInput.resetMarcador();
                this.ListInput.CarryMarcador();
                for(int i = 0; i < this.ListInput.getLength() - 1; ++i){
                    Rect tP = this.ListInput.getCarryData();
                
                    int aX = tP.getX()-Camara.getXRest();
                    int aY = tP.getY()-Camara.getYRest();
                        //se concede el tamaño de 2 cuadros por simpatia
                   // if(aX + tP.getWidth() > 0 && aX < dimAux.width && aY + tP.getHeight() > 0 && aY < dimAux.height) {
                        switch(tP.getType()) {
                            case 0:
                                gAux.setColor (tP.getCol());
                                gAux.fillRect (aX, aY, tP.getWidth(), tP.getHeight());
                            break;
                            case 1:
                                gAux.drawImage(tP.getOutput(),aX, aY,null);
                            break;
                        }
                    //}
                    this.ListInput.CarryMarcador();
                }
                this.ListInput.resetMarcador();
                Rect ply = this.ListInput.getCarryData();
                gAux.drawImage(ply.getOutput(), ply.getX()-Camara.getXRest(), ply.getY()-Camara.getYRest(),null);

                
                gAux.setColor(Color.red);
                gAux.fillRect (16, getHeight() - 32 - 24, Camara.getMageLife()*((getWidth()>>1) - 16)/100, 24);

                gAux.setColor(Color.cyan);
                gAux.fillRect (getWidth() - 48, getHeight() - 32 - Camara.getMageSed()*64/100, 32, Camara.getMageSed()*64/100);

                gAux.drawImage(sprite.BotellaSed.getImg(),getWidth() - 48-4, getHeight() - 32 +4 - 80,null);

                for (byte i = 0; i < 10; i++){
                    gAux.setColor(Color.darkGray);
                    gAux.fillRect (getWidth() - 72 - 180 + i*20,getHeight() - 32 - 4 - 16, 16, 16);

                    if(i*10 < Camara.getMageHambre()){
                        gAux.setColor(Color.orange);
                        gAux.fillRect (getWidth() - 72 - 180 + i*20 +1 ,getHeight() - 32 - 4 - 15, 14, 14);
                        gAux.drawImage(sprite.ConPollo.getImg(), getWidth() - 72 - 180 + i*20, getHeight() - 32 - 4 - 16, null);
                    } else {
                        gAux.drawImage(sprite.SinPollo.getImg(),getWidth() - 72 - 180 + i*20, getHeight() - 32 - 4 - 16, null);
                    }
                }

                if(Menus.Inventario.getVisibility()) {
                    Rect[] r = Menus.Inventario.toPaint();
                    Menus.Inventario.setFont(Camara.getMago());
                    for(int i = 0; i < r.length; i++) {
                        if(r[i] != null){
                            gAux.drawImage(r[i].getOutput(), r[i].getX(), r[i].getY(), r[i].getWidth(),r[i].getHeight(),null);

                        }
                    }
                }
        
                if(Menus.Personaje.getVisibility()) {
                    Rect[] r = Menus.Personaje.toPaint();
                    for(int i = 0; i < r.length; i++) {
                        if(r[i] != null){
                            gAux.drawImage(r[i].getOutput(), r[i].getX(), r[i].getY(), r[i].getWidth(),r[i].getHeight(),null);
                        }
                    }
                }
        
                if(Menus.selectedOpened != null) {
                    Rect[] r = Menus.selectedOpened.toPaint();
                    for(int i = 0; i < r.length; i++) {
                        if(r[i] != null){
                            gAux.drawImage(r[i].getOutput(), r[i].getX(), r[i].getY(), r[i].getWidth(),r[i].getHeight(),null);
                        }
                    }
                }
                break;
            case 99:
                gAux.setColor (Color.black);
                gAux.fillRect(0,0,dimAux.width, dimAux.height);
                break;
            
        }
        
        g.drawImage(dibujoAux, 0,0, this);
    }

    public class MyKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            pressed = KeyEvent.getKeyText(e.getKeyCode());
            int pu = e.getKeyCode();
            switch(EstadoMaquina){
                case 0:
                    switch(pu){
                        case 27:
                            System.exit(0);
                            break;
                    }   
                    break;
                case 2:
                    switch(pu) {
                        case 37:
                            if(Menus.selectedOpened == null) {
                                if(Menus.Inventario.getVisibility() && Menus.Inventario.getLMove() == -1) {
                                    Menus.Inventario.pLeft();
                                    Menus.Inventario.setLMove(37);
                                }
                                if(Menus.Personaje.getVisibility() && Menus.Personaje.getLMove() == -1) {
                                    Menus.Personaje.pLeft();
                                    Menus.Personaje.setLMove(37);
                                }
                            }   else {
                                Menus.selectedOpened = null;

                            }
                            break;
                        case 38:
                            if(Menus.selectedOpened == null) {
                                if(Menus.Inventario.getVisibility() && Menus.Inventario.getLMove() == -1) {
                                    Menus.Inventario.pUp();    
                                    Menus.Inventario.setLMove(38);
                                }
                                if(Menus.Personaje.getVisibility() && Menus.Personaje.getLMove() == -1) {
                                    Menus.Personaje.pUp();
                                    Menus.Personaje.setLMove(38);
                                }
                            }   else if(Menus.selectedOpened.getLMove() == -1){
                                Menus.selectedOpened.pUp();
                                Menus.selectedOpened.setLMove(38);
                            }
                            break;
                        case 39:
                            if(Menus.selectedOpened == null) {
                                if(Menus.Inventario.getVisibility() && Menus.Inventario.getLMove() == -1) {
                                    Menus.Inventario.pRight();
                                    Menus.Inventario.setLMove(39);
                                }
                                if(Menus.Personaje.getVisibility() && Menus.Personaje.getLMove() == -1) {
                                    Menus.Personaje.pRight();
                                    Menus.Personaje.setLMove(39);
                                }
                            }
                            break;
                        case 40:
                            if(Menus.selectedOpened == null) {
                                if(Menus.Inventario.getVisibility() && Menus.Inventario.getLMove() == -1) {
                                    Menus.Inventario.pDown();
                                    Menus.Inventario.setLMove(40);
                                }
                                if(Menus.Personaje.getVisibility() && Menus.Personaje.getLMove() == -1) {
                                    Menus.Personaje.pDown();
                                    Menus.Personaje.setLMove(40);
                                }
                            } else if(Menus.selectedOpened.getLMove() == -1){
                                Menus.selectedOpened.pDown();
                                Menus.selectedOpened.setLMove(40);
                            }
                            break;
                        case 65://A
                            puls[0][0] = 1;
                            break;
                        case 87://w
                            puls[0][1] = 1;
                            break;
                        case 68:
                            puls[1][0] = 1;
                            break;
                        case 83:
                            puls[1][1] = 1;
                            break; 
                        case 32:    
                            puls[0][2] = 1;
                            break;
                        case 27:
                            System.exit(0);
                            break;
                        case 0://Eliminar caso 0
                        break;

                        case 73: //I

                            if(!Menus.Inventario.controlCh){
                                Menus.Inventario.setVisibility(!Menus.Inventario.getVisibility());
                                Menus.Personaje.setVisibility(false);
                                Menus.Inventario.controlCh = true;
                                if(Menus.selectedOpened != null) {
                                    Menus.selectedOpened = null;
                                }
                            }

                        break;

                        case 80: //P
                        if(Menus.selectedOpened == null) {
                            if(!Menus.Personaje.controlCh){
                                Menus.Personaje.setVisibility(!Menus.Personaje.getVisibility());
                                Menus.Inventario.setVisibility(false);
                                Menus.Personaje.controlCh = true;
                            }
                        }
                        break;

                        case KeyEvent.VK_ENTER: //enter

                            /**
                             *  switch(this.EstadoMaquina){
                             *      case 0:
                             *          this.EstadoMaquina += this.selected;
                             *          break;
                             *      case 1:
                             *          this.EstadoMaquina++;
                             * 
                             * 
                             * 
                             * }
                             */
                        if(Menus.selectedOpened == null) {
                            if(Menus.Inventario.getVisibility()) {
                                Menus.Inventario.Action();
                                System.out.println("INTRO");
                            }
                        } else {
                            Menus.selectedOpened.Action();

                        }
                        break;
                        
                        case KeyEvent.VK_BACK_SPACE:
                            if(Menus.Inventario.getVisibility() || Menus.Personaje.getVisibility()) {
                                Menus.Inventario.setVisibility(false);
                                Menus.Personaje.setVisibility(false);

                                Menus.selectedOpened = null;
                            }
                        break;


                        default:
                        if(pu <= 127) {
                            puls[1][2] = (byte)pu;
                        }

                    }
                break;
            }
        }

        
        
        @Override
        public void keyReleased(KeyEvent e) {
            switch(EstadoMaquina) {
                case 0:
                    switch(e.getKeyCode()){
                        case KeyEvent.VK_ENTER:
                            puls[1][1] = 10;
                            //menuIni = null;
                            //menuIniDark = null;
                        break;
                        
                        case KeyEvent.VK_W:
                        case KeyEvent.VK_UP:
                            if(selected > 1){
                                --selected;
                            }
                        break;
                            
                        case KeyEvent.VK_S:
                        case KeyEvent.VK_DOWN:
                            if(selected < 3){
                                ++selected;
                            }
                            break;       
                    }
                break;
                case 2:
                    switch(e.getKeyCode()) {
                        case 65://A
                        puls[0][0] = 0;
                        break;
                        case 87://w
                        puls[0][1] = 0;
                        break;
                        case 68:
                        puls[1][0] = 0;
                        break;
                        case 83:
                        puls[1][1] = 0;
                        break;
                        case 32:    
                        puls[0][2] = 0;
                        break;
                        case 0://Eliminar caso 0
                        break;

                        case 37:
                        case 38:
                        case 39:
                        case 40:
                            if(Menus.selectedOpened == null) {
                                if(Menus.Inventario.getVisibility()) {
                                    Menus.Inventario.setLMove(-1);
                                }
                                if(Menus.Personaje.getVisibility()) {
                                    Menus.Personaje.setLMove(-1);
                                }
                            }  else {
                                Menus.selectedOpened.setLMove(-1);
                            }
                        break;

                        case 73:
                        Menus.Inventario.controlCh = false;
                        break;

                        case 80:
                        Menus.Personaje.controlCh = false;
                        break;

                        default:
                        puls[1][2] = 0;
                        break;
                    }
                break;
            }
        }
    }
}
