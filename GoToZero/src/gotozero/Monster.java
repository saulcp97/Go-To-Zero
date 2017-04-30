package gotozero;


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
        //la raza 100 es Roca Mal Hudmorada
        super((byte)100,"img/ello.png");
        this.Especie = (byte) 100;
    }
    
    public Monster(byte especie, String nombre, String raza) {
        super(especie,"img/caloriusBOSS.png");
        this.Especie = especie;
        this.Name = nombre;
        
        this.setPos(192,192 * 2,640);
        
        this.atributizar(raza);
    }
    
    private void atributizar(String raza) {
        
        switch(raza) {
            case "BOSS":
                switch(this.Name) {
                    case "Calorius":
                        //Calorius es mas ancha que profunda
                        this.setSize(192, 96, 192);
                    
                    
                        this.setSpriteSize(192,192);
                        
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
    
    private void setPos(int x, int y, int z) {
        this.setPosicion(x,y);
        this.setZ(z);
    }
    
    @Override
    public void toRect(Rect re){
        re.setX(this.x);
        re.setY(this.y - (this.TamanyoY>>1) - (this.z>>1));
        re.setWidth(this.TamanyoX);
        re.setHeight(this.TamanyoY);
        re.setImg(this.Sprite.getImg());
        
        re.setSection(1, 1, 1);
        //entry.setX(this.x - (this.TamanyoX>>2));
        //entry.setY(this.y - (this.TamanyoY>>1)-(this.z>>1));
        //entry.setWidth(this.TamanyoX);
        //entry.setHeight(this.TamanyoY);
        //entry.setImg(this.Sprite.getImg());  
    }
    
    public boolean AgroZone(Block obj, int r){
        return (this.x - r)<(obj.x + obj.width) && (this.x + this.width + r) > (obj.x)
            && (this.y - r)<(obj.y + obj.height) && (this.y + this.height + r) > (obj.y)
            && (this.z - r)<(obj.z + obj.deep) && (this.z + this.deep + r) > (obj.z);
    }
    
}
