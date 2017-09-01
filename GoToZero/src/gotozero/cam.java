package gotozero;

import java.awt.*;

/**
 * Write a description of class cam here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class cam {

    // instance variables - replace the example below with your own
    private int x,y;
    private final int width;
    private final int height;
    private final int altura;
    private Mage objective;

    private boolean conversationMode;

    private Point punto = MouseInfo.getPointerInfo().getLocation();
    private int MouseX=punto.x;
    private int MouseY=punto.y;

    private String dialog;
    private String locutorName;
    private boolean esperaDialogo;
    private Image imagLocutor;

    private final Toolkit t = Toolkit.getDefaultToolkit();
    /**
     * Constructor for objects of class cam
     */
    public cam() {
        // initialise instance variables
        x = 0;
        y = 0;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.width;
        height = screenSize.height;
        altura = 0;
        this.objective = null;
        this.conversationMode = false;
        this.dialog = "";
        this.esperaDialogo = false;
    }
    
    /**
     *
     */
    public void actualMouse() {
        this.punto = MouseInfo.getPointerInfo().getLocation();
        this.MouseX = this.punto.x;
        this.MouseY = this.punto.y;
    }

    public void removeImagLocutor() { this.imagLocutor = null; }
    public Image getImagLocutor() { return this.imagLocutor; }

    public void assignImagLocutor() {
        switch (this.locutorName){
            case "Mimi: Gerente de Neko Maid Coffe":
                this.imagLocutor = new sprite("img/face/locutorImageMaid.png").getImg();
                break;
            default:
                this.imagLocutor = new sprite("img/face/basesParaDibujarPersonajes.png").getImg();
        }
    }

    public int relativeMX() {
        return MouseX - (this.width>>1) + (objective.getTamanyoX()>>1);
    }
    public int relativeMY() {
        return MouseY - (this.height>>1);
    }

    public void setDialog(String st) { this.dialog = st; }
    public String getDialog() { return this.dialog; }
    /**
     * An example of a method - replace this comment with your own
     * @return 
     */
    public int getXRest(){
        return x - (width>>1);
    }
    public int getYRest() {
        return y - (height>>1) - 32;
    }
    
    public int getWidth() {
        return this.width;     
    }
    
    public int getHeight() {
        return this.height;
    }
    
    /**
     *
     */
    public void actualitze() {
        if(this.objective != null) {
            this.x = this.objective.getX() + (this.objective.getTamanyoX()>>1);
            this.y = this.objective.getY() - (this.objective.getZ()>>1) + (this.objective.getTamanyoY()>>1);
        }
    }
    
    public void setFocus(Mage m) {
        this.objective = m;
    }

    public void aument(int i, int j) {
        this.x += i;
        this.y += j;
    }

    public void aumX(int i) { this.x += i;}
    public void aumY(int j) { this.y += j;}

    public int getMouseX() { return MouseX; }
    public int getMouseY() { return MouseY; }

    public byte getMageLife() {
        return this.objective.getLife();
    }
    public byte getMageHambre() {
        return this.objective.getHambre();
    }
    public byte getMageSed() {
        return this.objective.getSed();
    }
    public Mage getMago() {
        return this.objective;
    }

    public boolean getConversationMode() { return this.conversationMode; }
    public void setConversationMode (boolean valor) {this.conversationMode = valor;}

    public boolean getEsperaDialogo() { return this.esperaDialogo; }
    public void setEsperaDialogo(boolean espera) { this.esperaDialogo = espera;}

    public void setLocutorName(String l) { this.locutorName = l; }
    public String getLocutorName() { return this.locutorName; }
}
