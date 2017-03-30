package gotozero;


/**
 * Write a description of class Usable here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Usable extends Item
{
    // instance variables - replace the example below with your own
    private String name;

    
    
    public static final Usable setArchimagaExplosiva = new Usable ("Set Archimaga Explosiva","setArchimagaExplosiva");
    /**
     * Constructor for objects of class Usable
     */
    public Usable(String n, String ruta) {
        super(n, ruta);
        this.name = n;
    }
}
