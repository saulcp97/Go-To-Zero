package gotozero;


/**
 * Write a description of class Alimento here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Alimento extends Item{
    // instance variables - replace the example below with your own
    
    
    private byte Calorias;
    private byte Hidratacion;
    
    public static final Alimento Agua = new Alimento("Agua", (byte) 0, (byte) 100, "botellaAgua");
    public static final Alimento Naranja = new Alimento("Naranja", (byte) 50, (byte) 50, "naranjaFresca");
    /**
     * Constructor for objects of class Alimento
     */
    public Alimento(){
        super();
        this.Calorias = 0;
        this.Hidratacion = 0;
    }

    public Alimento(String name, byte cal, byte hidra, String ruta){
        super(name, ruta);
        this.Calorias = cal;
        this.Hidratacion = hidra;
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
