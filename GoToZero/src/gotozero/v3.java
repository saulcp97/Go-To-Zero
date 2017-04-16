package gotozero;


/**
 * Write a description of class v3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class v3 {
    // instance variables - replace the example below with your own
    protected int x;
    protected int y;
    protected int z;
    /**
     * Constructor for objects of class v3
     */
    public v3() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }
    
    public v3(int X, int Y, int Z) {
        this.x = X;
        this.y = Y;
        this.z = Z;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getZ() {
        return this.z;
    }
    
    public void setX(int v) {
        this.x = v;
    }
    
    public void setY(int v) {
        this.y = v;
    }
    
    public void setZ(int v) {
        this.z = v;
    }
    
    public double getModule() {
        return Math.sqrt(this.x*this.x + this.y*this.y + this.z*this.z);
        
    }
    
    public double calcDist(v3 other) {
        return Math.sqrt(Math.pow(this.x-other.x,2) + Math.pow(this.y-other.y,2) + Math.pow(this.z-other.z,2));
    }
    
    public double calcDist(v3 other, int mX, int mY, int mZ) {
        return Math.sqrt(Math.pow(this.x-other.x + mX,2) + Math.pow(this.y-other.y + mY,2)
                    + Math.pow(this.z-other.z + mZ,2));
    }
}
