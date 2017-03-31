
/**
 * Write a description of class Sphere here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sphere extends v3{
    
    protected int r;
    
    protected v3 velo;
    /**
     * Constructor for objects of class Sphere
     */
    public Sphere() {
		super();
        this.r = 5;
        
        this.velo = new v3();
    }

    public Sphere(int X, int Y, int Z, int oX, int oY, int oZ, int R) {
        super(X,Y,Z);

        this.r = R;
    
        this.velo = new v3(oX, oY, oZ);
        this.toDist(10);
    }
    
    public int getR() {
        return this.r;
    }
    
    public void setVelo(int v1, int v2, int v3){
        this.velo.setX(v1);
        this.velo.setY(v2);
        this.velo.setZ(v3);
    }
    
    public void move(){
        this.x += this.velo.getX();
        this.y += this.velo.getY();
        this.z += this.velo.getZ();
    }
    
    public void toRect(Rect e){
        e.setX(this.x - this.r);
        e.setY(this.y - this.r -(this.z>>1));
        e.setWidth(this.r<<1);
        e.setHeight(this.r<<1);
    }
    
    public void toDist( int dist) {
        this.velo.setX((int)Math.round(this.velo.getX() / dist));
        this.velo.setY((int)Math.round(this.velo.getY() / dist));
        //uni.setZ(uni.getZ() / uni.getModule() * dist);
    }
    
}
