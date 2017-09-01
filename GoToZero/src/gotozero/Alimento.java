package gotozero;


import gotozero.ObjectGestion.Item;

import java.io.Serializable;

/**
 * Write a description of class Alimento here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Alimento extends Item {
    // instance variables - replace the example below with your own
    
    
    private byte Calorias;
    private byte Hidratacion;
    
    public static final Alimento Agua = new Alimento("Agua", (byte) 0, (byte) 100, "botellaAgua");
    public static final Alimento Naranja = new Alimento("Naranja", (byte) 50, (byte) 50, "naranjaFresca");
    public static final Alimento Limon = new Alimento("Limon", (byte) 50, (byte) 50, "limon");
    public static final Alimento Huevo = new Alimento("Huevo", (byte) 10, (byte) 2, "huevo");
    public static final Alimento Platano  = new Alimento("Platano", (byte) 25, (byte) 0, "platanoFresco");

    public static final Alimento Sake = new Alimento("Sake", (byte) 0, (byte) 25, "sake");
    public static final Alimento Leche = new Alimento("Leche", (byte) 10, (byte) 100, "leche");
    public static final Alimento Tarta = new Alimento("Tarta", (byte) 100, (byte) 0, "tarta");
    public static final Alimento Ketchup = new Alimento("Ketchup", (byte) 1, (byte) 1, "ketchup");
    public static final Alimento Omelette = new Alimento("Omelette", (byte) 100, (byte) 0, "omelette");

    public static final Alimento Melon = new Alimento("Melon", (byte) 75, (byte) 25, "melon");
    public static final Alimento Flan = new Alimento("Flan", (byte) 33, (byte) 2, "flan");
    public static final Alimento Pizza = new Alimento("Pizza", (byte) 100, (byte) 0, "omelette");
    //Omelette decorada
    //

    /**
     * Constructor for objects of class Alimento
     */
    public Alimento(){
        super();
        this.Calorias = 0;
        this.Hidratacion = 0;

        this.actions = new String[3];
        this.actions[0] = "Consumir";
        this.actions[1] = "Info";
        this.actions[2] = "Cerrar";
    }

    public Alimento(String name, byte cal, byte hidra, String ruta){
        super(name, ruta);
        this.Calorias = cal;
        this.Hidratacion = hidra;

        this.actions = new String[3];
        this.actions[0] = "Consumir";
        this.actions[1] = "Info";
        this.actions[2] = "Cerrar";
    }
    
    public byte getCalorias() {
        return this.Calorias;
    }

    public byte getHidratacion() {
        return this.Hidratacion;
    }
    
    /*public void doAction(int i) {
        if(i == 0) {
            
        }
        
        
    }**/
}
