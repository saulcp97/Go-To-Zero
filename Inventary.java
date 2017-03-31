
/**
 * Write a description of class Inventary here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Inventary
{
    // instance variables - replace the example below with your own
    private Item[] Dropable;
    private byte[] Probability;
    
    private Item[] InventaryObjects;
    private byte[] Count;
    private lifes link;
    /**
     * Constructor for objects of class Inventary
     */
    public Inventary()
    {
        // initialise instance variables
        this.Dropable = new Item[2];
        this.Dropable[0] = Alimento.Agua;
        this.Dropable[1] = Alimento.Naranja;
        
        this.Probability = new byte[2];
        this.Probability[0] = 1;
        this.Probability[1] = 1;
        
        
        this.InventaryObjects = this.Dropable;
        this.Count = this.Probability;
    }
    
    public Item getByPosition(int i) {
        return this.InventaryObjects[i];
    }
    
    public void makeLink(lifes l) {
        this.link = l;
    }
    
    public int getInventaryLength() {
        return this.InventaryObjects.length;
    }
    
    public void doAction(int i, int act){
        if(act == 0) {
            if(i == 0) {
                link.Alimentacion(Alimento.Agua);
            } else if (i == 1) {
                link.Alimentacion(Alimento.Naranja);
            }
        }
        
    }
    
    public Inventary(String s){
        this.InventaryObjects = new Item[16];
        this.Count = new byte[16];
        for(int i = 0; i < this.Count.length; ++i) {
            this.Count[i] = 0;
        }
        
        this.Dropable = new Item[0];
        this.Probability = new byte[0];
    }
}
