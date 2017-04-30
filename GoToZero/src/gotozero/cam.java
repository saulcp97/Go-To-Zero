package gotozero;

import java.awt.Point;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.Dimension;
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
    
    private Point punto = MouseInfo.getPointerInfo().getLocation();
    private int MouseX=punto.x;
    private int MouseY=punto.y;
    
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
    }
    
    /**
     *
     */
    public void actualMouse() {
        punto = MouseInfo.getPointerInfo().getLocation();
        MouseX=punto.x;
        MouseY=punto.y;
    }
    
    public int relativeMX() {
        return MouseX - (this.width>>1) + (objective.getTamanyoX()>>1);
    }
    
    public int relativeMY() {
        return MouseY - (this.height>>1);
    }
    
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
    
    public void aumX(int i) { this.x += i;}
    public void aumY(int j) { this.y += j;}
    
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
}
