
/**
 * Write a description of class Creature here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Creatures extends lifes{
    private boolean Agresive;
    private byte IntelligenceType;
    
    protected sprite Sprite;
    /**
     * Constructor for objects of class Creature
     */
    public Creatures(byte e,String r) {
        super(e);
        this.Agresive = e > 0;
        this.IntelligenceType = e;
        this.Sprite = new sprite(r);
    }
    
    public void setIntelligence(byte a) {
        this.IntelligenceType = a;
    }
    
    public void IA() {
        
        switch(this.IntelligenceType) {
             case 0:
                this.setPosicion(1,10);
                break;
            
            
            
        }
        
        
    }
    
    public void toRect(Rect re){
        re.setX(this.x);
        re.setY(this.y - (this.height>>1) - (this.z>>1));
        re.setWidth(this.width);
        re.setHeight(this.deep + (this.height>>1));
        re.setImg(this.Sprite.getImg());

        }
    }
    
