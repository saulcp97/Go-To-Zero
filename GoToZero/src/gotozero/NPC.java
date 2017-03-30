package gotozero;


/**
 * Write a description of class NPC here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NPC extends Creatures{
    // instance variables - replace the example below with your own
    
    
    /**
     * Constructor for objects of class NPC
     */
    public NPC() {
        super((byte)-50,"");
    }

    public NPC(String ruta) {
        super((byte)1,ruta);
        this.x = 0;
        this.y = 64;
        this.z = 64;
    }
    
}
