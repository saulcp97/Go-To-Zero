package gotozero;

import gotozero.Constructor.Negocios.Negocio;
import gotozero.Organice.ListRect;
import gotozero.UI.InterfazUsuario;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Dimension;

import java.awt.Toolkit;

import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
* Write a description of class PantallaOut here.
* 
* @author (your name) 
* @version (a version number or a date)
*/

@SuppressWarnings("serial")
public class PantallaOut extends JFrame{

    private MyPanel contentPane;

    //La camara normal que debe incluir una funcion Zoom (en el futuro)
    private cam Camara;
    private byte EstadoMaquina = 0;
    private byte selected;

    public int teclado = 0b00000000000000000000000000000000;

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


    /*
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
    */

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

    /**
     * Crea la pantalla de visualización, que contendrá el canvas en el que se encuentra el juego.
     */
    public PantallaOut() {
        System.setProperty("sun.java2d.opengl", "true");

        Camara = new cam();
        addKeyListener(new MyKeyListener());
        addMouseListener(new MyMouseListener());
        addMouseWheelListener(new mouseWheelEvent());
        this.sizeWorldX = 0;
        this.sizeWorldY = 0;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = new MyPanel();
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

    class MyPanel extends JPanel {

        @Override
        public void update(Graphics g) {
            try {
                paintComponent(g);
            } catch (ExceptionInInitializerError e) {
                e.printStackTrace();
            }
            //actualizar();
        }

        @Override
        public void paintComponent(Graphics g) {
            if(gAux == null || dimAux == null || dimCanvas.width != dimAux.width
                    || dimCanvas.height != dimAux.height) {
                dimAux = dimCanvas;
                dibujoAux = createImage(dimAux.width, dimAux.height);
                Rect.setPantalla(getWidth(), getHeight());
                gAux = dibujoAux.getGraphics();
            }

            switch(EstadoMaquina) {
                case -34: //Modo Empresa
                    gAux.setColor (Color.pink);
                    gAux.fillRect(0,0,dimAux.width, dimAux.height);
                    ListInput.resetMarcador();
                    ListInput.CarryMarcador();
                    for(int i = 0; i < ListInput.getLength() - 1; ++i){
                        Rect tP = ListInput.getCarryData();

                        int aX = tP.getX()-Camara.getXRest();
                        int aY = tP.getY()-Camara.getYRest();
                        //se concede el tamaño de 2 cuadros por simpatia
                        switch(tP.getType()) {
                            case 0:
                                gAux.setColor (tP.getCol());
                                gAux.fillRect (aX, aY, tP.getWidth(), tP.getHeight());
                                break;
                            case 1:
                                tP.painTo(gAux, aX, aY);
                                break;
                        }
                        ListInput.CarryMarcador();
                    }

                    Negocio.drawCursorCompra(gAux,((Camara.getXRest() + Camara.getMouseX() + 32) / 64) * 5, ((Camara.getMouseY() + Camara.getYRest() + 32) / 64) * 5,5, Camara);

                    if(Menus.InventarioNegocio.getVisibility()) {
                        Menus.drawMenuInventarioNegocio(gAux, Camara);
                    }

                    if(Menus.ContratacionTrabajadores.getVisibility()) {
                        Menus.drawMenuContratacion(gAux, Camara);
                    }

                    //Presupuesto Del cafe
                    gAux.setColor(Color.darkGray);
                    gAux.fillRect(getWidth() - 100,0,100,20);
                    gAux.setColor(Color.LIGHT_GRAY);
                    gAux.drawString("" + Camara.getMago().getEmpresa().getPresupuesto(),getWidth() - 90,16);

                    break;
                case 0:
                    if(Math.random()*100 < 0.5){
                        gAux.setColor (Color.black);
                        gAux.fillRect(0,0,dimAux.width, dimAux.height);
                        gAux.drawImage(menuIniDark, (getWidth() - 500)>>1, (getHeight() - 300)>>1, null);
                    } else {

                        gAux.setColor (Color.white);
                        gAux.fillRect(0,0,dimAux.width, dimAux.height);
                        gAux.drawImage(menuIni, (getWidth() - 500)>>1, (getHeight() - 300)>>1, null);

                        if(selected != 0) {
                            gAux.drawImage(menuIniSelector, (getWidth()>>1)+95, ((getHeight() - 300)>>1) + 55 + selected * 40, null);
                        }
                    }
                    break;
                case 2:
                    gAux.setColor (Color.white);
                    gAux.fillRect(0,0,dimAux.width, dimAux.height);

                    gAux.setColor (Color.blue);

                    ListInput.resetMarcador();
                    ListInput.CarryMarcador();
                    for(int i = 0; i < ListInput.getLength() - 1; ++i){
                        Rect tP = ListInput.getCarryData();

                        int aX = tP.getX()-Camara.getXRest();
                        int aY = tP.getY()-Camara.getYRest();
                        //se concede el tamaño de 2 cuadros por simpatia
                        switch(tP.getType()) {
                            case 0:
                                gAux.setColor (tP.getCol());
                                gAux.fillRect (aX, aY, tP.getWidth(), tP.getHeight());
                                break;
                            case 1:
                                tP.painTo(gAux, aX, aY);
                                break;
                        }
                        ListInput.CarryMarcador();
                    }
                    ListInput.resetMarcador();
                    Rect ply = ListInput.getCarryData();
                    gAux.drawImage(ply.getOutput(), ply.getX()-Camara.getXRest(), ply.getY()-Camara.getYRest(),null);

                    InterfazUsuario.drawInterfazSuperv(gAux, getWidth(), getHeight(), Camara);

                    if(Camara.getConversationMode()) {
                       InterfazUsuario.Conversacion(gAux, getWidth(), getHeight(), Camara);
                    }
                    if(Menus.Inventario.getVisibility()) {
                        Menus.drawMenuInventario(gAux, Camara);
                    }
                    if(Menus.Personaje.getVisibility()) {
                        Menus.drawMenuPersonajes(gAux, Camara);
                    }
                    if(Menus.selectedOpened != null) {
                        Menus.drawSelection(gAux);
                    }
                    if(Menus.InfoObjetos != null) {
                        Menus.drawInfoObjeto(gAux);
                    }
                    break;
                case 99:
                    gAux.setColor (Color.black);
                    gAux.fillRect(0,0,dimAux.width, dimAux.height);
                    break;
            }
            g.drawImage(dibujoAux, 0,0, this);
        }
    }

    public class mouseWheelEvent implements MouseWheelListener {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if(EstadoMaquina == -34) {
                Menus.InventarioNegocio.desp += e.getWheelRotation() * 8;
            }
        }
    }
    
    public class MyMouseListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent me) {
            if(EstadoMaquina == -34) {
                if (Menus.ContratacionTrabajadores.getVisibility()) {
                    Menus.ContratacionTrabajadores.click(Camara,me.getX(),me.getY());
                }
            }
        }
        @Override
        public void mousePressed(MouseEvent me) {
            if(SwingUtilities.isLeftMouseButton(me)){
                if(EstadoMaquina == 2 || EstadoMaquina == -34) {
                    if((teclado & 0b10000) == 0) {
                        teclado += 0b10000;
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            if(SwingUtilities.isLeftMouseButton(me)){
                if(EstadoMaquina == 2 || EstadoMaquina == -34) {
                    if((teclado & 0b10000) == 0b10000) {
                        teclado -= 0b10000;
                    }
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent me) {

        }

        @Override
        public void mouseExited(MouseEvent me) {


        }
    }



    public class MyKeyListener implements KeyListener {
        
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            int pu = e.getKeyCode();
            switch(EstadoMaquina){
                case -34:
                    switch (pu) {
                        case KeyEvent.VK_UP:
                            if((teclado & 0b00000001) == 0) {
                                teclado += 0b00000001;
                            }
                            break;
                        case KeyEvent.VK_RIGHT:
                            if((teclado & 0b00000010) == 0) {
                                teclado += 0b00000010;
                            }
                            break;
                        case KeyEvent.VK_DOWN:
                            if((teclado & 0b00000100) == 0) {
                                teclado += 0b00000100;
                            }
                            break;
                        case KeyEvent.VK_LEFT:
                            if((teclado & 0b00001000) == 0) {
                                teclado += 0b00001000;
                            }
                            break;
                        case KeyEvent.VK_I:
                            if(!Menus.InventarioNegocio.controlCh){
                                Menus.InventarioNegocio.desp = 0;
                                Menus.InventarioNegocio.setVisibility(true);
                                Menus.InventarioNegocio.controlCh = true;
                                Menus.ContratacionTrabajadores.setVisibility(false);
                                Menus.ContratacionTrabajadores.controlCh = false;
                                if(Menus.selectedOpened != null) {
                                    Menus.selectedOpened = null;
                                }
                            }
                            break;
                        case KeyEvent.VK_C:
                            if(!Menus.ContratacionTrabajadores.controlCh) {
                                Menus.ContratacionTrabajadores.desp = 0;
                                Menus.InventarioNegocio.controlCh = false;
                                Menus.InventarioNegocio.setVisibility(false);
                                Menus.ContratacionTrabajadores.setVisibility(true);
                                Menus.ContratacionTrabajadores.controlCh = true;
                                if(Menus.selectedOpened != null) {
                                    Menus.selectedOpened = null;
                                }
                            }
                            break;
                        case KeyEvent.VK_BACK_SPACE:
                            Menus.selectedOpened = null;
                            Menus.ContratacionTrabajadores.controlCh = true;
                            Menus.InventarioNegocio.controlCh = true;

                            if(Menus.InventarioNegocio.getVisibility()) {
                                Menus.InventarioNegocio.setVisibility(false);
                            } else if(Menus.ContratacionTrabajadores.getVisibility()) {
                                Menus.ContratacionTrabajadores.setVisibility(false);
                            }
                            break;
                        case KeyEvent.VK_PAGE_UP:
                            if(Menus.InventarioNegocio.getVisibility()) {
                                Menus.InventarioNegocio.desp -= 8;
                            } else if(Menus.ContratacionTrabajadores.getVisibility()) {
                                Menus.ContratacionTrabajadores.desp -= 8;
                            }
                            break;
                        case KeyEvent.VK_PAGE_DOWN:
                            if(Menus.InventarioNegocio.getVisibility()){
                                Menus.InventarioNegocio.desp += 8;
                            } else if(Menus.ContratacionTrabajadores.getVisibility()) {
                                Menus.ContratacionTrabajadores.desp += 8;
                            }
                            break;
                        case KeyEvent.VK_ESCAPE:
                            System.exit(0);
                            break;
                    }
                    break;
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
                        case 69:
                            //teclado 0b0010 0000
                            if((0b00100000 & teclado) == 0) {
                                teclado += 0b00100000;
                            }
                            break;
                        case 87://w                            
                            if((teclado & 0b1) == 0){
                                teclado -= (0b11000000 & teclado);
                                teclado += 0b00000001;//direccion 0
                            }
                            break;
                        case 68://d                            
                            if((teclado & 0b10) == 0){
                                teclado -= (0b11000000 & teclado);
                                teclado += 0b01000010;//direccion 1
                            }               
                            break;
                        case 83://s                            
                            if((teclado & 0b100) == 0){
                                teclado -= (0b11000000 & teclado);
                                teclado += 0b10000100;//dirección 2
                            }
                            break;
                        case 65://A                            
                            if((teclado & 0b1000) == 0){
                                teclado -= (0b11000000 & teclado);
                                teclado += 0b11001000;
                            }
                            break;
                        case 32://espai                                
                            if((teclado & 0b10000) == 0){
                                teclado += 0b10000;
                            }
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
                    }
                break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch(EstadoMaquina) {
                case -34:
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            if((teclado & 0b00000001) == 0b00000001) {
                                teclado -= 0b00000001;
                            }
                            break;
                        case KeyEvent.VK_RIGHT:
                            if((teclado & 0b00000010) == 0b00000010) {
                                teclado -= 0b00000010;
                            }
                            break;
                        case KeyEvent.VK_DOWN:
                            if((teclado & 0b00000100) == 0b00000100) {
                                teclado -= 0b00000100;
                            }
                            break;
                        case KeyEvent.VK_LEFT:
                            if((teclado & 0b00001000) == 0b00001000) {
                                teclado -= 0b00001000;
                            }
                            break;
                        case KeyEvent.VK_I:
                            Menus.InventarioNegocio.controlCh = false;
                            break;
                        case KeyEvent.VK_C:
                            Menus.ContratacionTrabajadores.controlCh = false;
                            break;
                        case KeyEvent.VK_BACK_SPACE:
                            Menus.ContratacionTrabajadores.controlCh = false;
                            Menus.InventarioNegocio.controlCh = false;
                            break;
                    }
                    break;
                case 0:
                    switch(e.getKeyCode()){
                        case KeyEvent.VK_ENTER:
                            teclado += 0b100000;
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
                            if(selected < 4){
                                ++selected;
                            }
                            break;       
                    }
                break;
                case 2:
                    switch(e.getKeyCode()) {
                        case 69: //E = Talk
                            //teclado 0b0010 0000
                            teclado -= (0b00100000 & teclado);
                            break;
                        case 87://w
                            teclado -= 1;
                            break;
                        case 68:
                            teclado -= 0b10;
                            break;
                        case 83:
                            teclado -= 0b100;
                            break;
                        case 65://A
                            teclado -= 0b1000;
                            break;
                        case 32://Espacio    
                            teclado -= 0b10000;
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
                    }
                break;
            }
        }
    }
}
