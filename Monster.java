
/**
 * Write a description of class Monster here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Monster extends Creatures {
    private byte Especie;
    /**
     * Constructor for objects of class Monster
     */
    
    public Monster() {
        //la raza 100 es Roca Mal Humorada
        super((byte)100,"/img/ello.png");
        this.Especie = (byte) 100;
    }
    
    public Monster(byte especie, String nombre, String raza) {
        super(especie,"/img/caloriusBOSS.png");
        this.Especie = especie;
        this.Name = nombre;
        
        this.setPos(192,129,640);
        
        atributizar(raza);
    }
    
    public void atributizar(String raza) {
        
        switch(raza) {
            
            case "BOSS":
                switch(this.Name) {
                    case "Calorius":
                    
                        this.setSize(192, 192, 192);
                    
                    
                        this.estadisticas.setMaxHP(1000);
                        this.estadisticas.setHP(1000);
                        this.estadisticas.setDF(500);
                        this.estadisticas.setDMG(100);
                    
                    
                        break;
                }
                break;
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            case "MIERDA":
            
            
                break;
        }
    }
    
    public void setPos(int x, int y, int z) {
        this.setPosicion(x,y);
        this.setZ(z);
    }
    
}
