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

    //Necesarios para el pintado sin parpaedo
    private Image dibujoAux;
    private Graphics gAux;
    private Dimension dimAux;
    private Dimension dimCanvas;

    private Image canvSuelo;
    private boolean Floored;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        PantallaOut frame = new PantallaOut();

                        frame.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    public cam getCam() {
        return Camara;    
    }

    public void setInput(Rect[] in) {
        this.input = in;
    }

    public void setInputMD(Rect[] in) {
        this.inputMD = in;
    }

    public void setWorldSizeX(int x) {
        this.sizeWorldX = x;
    }

    public void setWorldSizeY(int y) {
        this.sizeWorldY = y;
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

        setBounds(0,0,650,700);

        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setUndecorated(true);

        dimCanvas = this.getSize();

        Image canvSuelo = null;
        this.Floored = false;
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override 
    public void paint (Graphics g) {
        //super.paint(g);

        if(gAux == null || dimAux == null || dimCanvas.width != dimAux.width 
        || dimCanvas.height != dimAux.height) {
            dimAux = dimCanvas;
            dibujoAux = createImage(dimAux.width, dimAux.height);
            gAux = dibujoAux.getGraphics();
        }

        gAux.setColor (Color.white);
        gAux.fillRect(0,0,dimAux.width, dimAux.height);

        /*
        gAux.setColor (Color.blue);
        gAux.drawLine (0, 70, 100, 70);
        gAux.drawRect (150, 70, 50, 70);
        gAux.drawRoundRect (250, 70, 50, 70, 6, 6);
        gAux.drawOval (350, 70, 50, 70);
        int [] vx1 = {500, 550, 450};
        int [] vy1 = {70, 120, 120};
        gAux.drawPolygon (vx1, vy1, 3);
         */

        gAux.setColor (Color.blue);

        if (!Floored) {
            Image imgSuelo = sprite.Suelo.getImg();

            canvSuelo = createImage(this.sizeWorldX<<6,this.sizeWorldY<<6);
            Graphics gSuelo = canvSuelo.getGraphics();
            for(int i = 0; i < this.sizeWorldX; ++i) {
                for(int j = 0; j < this.sizeWorldY; ++j) {
                    int x = (i<<6);
                    int y = (j<<6);
                    gSuelo.drawImage(imgSuelo,x,y,null);
                }
            }
            Floored = true;
        }
        int IniRecorteX = Camara.getXRest();
        int IniRecorteY = Camara.getYRest();
        int iniPaintX = Math.max(Camara.getXRest(),0);
        int iniPaintY = Math.max(Camara.getYRest(),0);
        Image res =((BufferedImage) canvSuelo).getSubimage(iniPaintX, iniPaintY, Math.min(dimAux.width,(this.sizeWorldX<<6)-iniPaintX), 
                Math.min(dimAux.height,(this.sizeWorldY<<6)-iniPaintY));

        gAux.drawImage(res,Math.max(-IniRecorteX,0), Math.max(-IniRecorteY,0), null);

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

        /*
        for(int i = 0; i < this.inputMD.length; ++i){
        if(this.inputMD[i] != null){
        int aX = this.inputMD[i].getX()-Camara.getXRest();
        int aY = this.inputMD[i].getY()-Camara.getYRest();
        //se concede el tamaño de 2 cuadros por simpatia
        if(aX + 128 > 0 && aX < dimAux.width && aY + 128 > 0 && aY < dimAux.height) {
        gAux.setColor (this.inputMD[i].getCol());
        gAux.fillRect (aX, aY, this.inputMD[i].getWidth(), this.inputMD[i].getHeight());
        }
        }
        }

        //Temporal

        gAux.setColor (Color.blue);
        gAux.fillRect (0, getHeight() - 32, getWidth(), 32);

        /*
        gAux.setColor (Color.red);
        gAux.fillRect (150, 270, 50, 70);
        gAux.fillRoundRect (250, 270, 50, 70, 6, 6);
        gAux.fillOval (350, 270, 50, 70);
        int [] vx2 = {500, 550, 450};
        int [] vy2 = {270, 320, 320};
        gAux.fillPolygon (vx2, vy2, 3);
         */

        //Interfaz Grafica(Muy provisional)

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
        }

        @Override
        public void keyReleased(KeyEvent e) {
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
        }
    }
}
