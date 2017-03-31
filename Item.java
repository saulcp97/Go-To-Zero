import java.awt.Image;
/**
 * Write a description of class Item here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Item {
    // instance variables - replace the example below with your own
    private String Name;
    private Rect Visual;

    private String[] actions;
    
    private Stadistic estadisticas;
    /**
     * Constructor for objects of class Item
     */
    public Item() {
        this.Name = "Cosa";
        this.Visual = new Rect();
        this.Visual.setImg(new sprite("/img/object/anilloDiamanteAzul.png").getImg());
        this.actions = new String[2];
        this.actions[0] = "Info";
        this.actions[1] = "Cerrar";
    }
    
    public Item(String nombre,String ruta) {
        this.Name = nombre;
        this.Visual = new Rect();
        this.Visual.setImg(new sprite("/img/object/" + ruta + ".png").getImg());
        this.actions = new String[2];
        this.actions[0] = "Info";
        this.actions[1] = "Cerrar";
    }
    
    public String getName(){
        return this.Name;
    }
    
    public Image getImage() {
        return this.Visual.getOutput();
    }
    
    public Rect getRect() {
        return this.Visual;
    }
    
    public void setActions(String[] act) {
        this.actions = act;
    }
    
    public String[] getActions() {
        return this.actions;
    }
    
    public void doAction(int i, lifes liv) {
        
    }
}
