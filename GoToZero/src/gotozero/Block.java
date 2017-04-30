package gotozero;

import java.awt.Image;

/**
 * Write a description of class Bock here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Block extends v3 implements Comparable<Block>{
    // instance variables - replace the example below with your own
    protected int width;
    protected int height;
    protected int deep;
    
    private byte Tipo;
    
    //private Rect R;
    /**
     * Constructor for objects of class Bock
     */
    public Block() {
        super();
        this.width = 64;
        this.height = 64;
        this.deep = 64;
        this.Tipo = 1;
        //this.R = new Rect();
    }

    public Block(int i,int j,int k) {
        super(i,j,k);
        this.width = 64;
        this.height = 64;
        this.deep = 64;
        this.Tipo = 1;
        //this.R = new Rect();
    }
    
    public Block(int i, int j,byte t) {
        super();
        this.Tipo = t;
        switch(this.Tipo){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                this.x = (i<<6)/ 5 -128/5;
                this.y = (j<<6)/ 5 -128/5;
                this.z = 0;
                this.width = 64;
                this.height = 64;
                this.deep = 64;
                break;
        }
    }
    
        public Block(int i, int j, int k, byte t) {
        super();
        this.Tipo = t;
        switch(this.Tipo){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                this.x = (i<<6)/ 5 -128/5;
                this.y = (j<<6)/ 5 -128/5;
                this.z = (k<<6)/ 5 -128/5;
                this.width = 64;
                this.height = 64;
                this.deep = 64;
                break;
        }
    }
    
       
        
    //public void setRect(Rect e) {
    //    this.R = e;
    //}
    
    //public Image getOutput() {
    //    return this.R.getOutput();
    //}
    
    public boolean collision(Block o) {
        return (this.z < o.z + o.deep && this.z + this.deep > o.z)
                    && ((this.x < o.x + o.width && this.x + this.width > o.x)
                            &&(this.y < o.y + o.height && this.y + this.height > o.y));
    }

    public boolean collision(Sphere o) {
        boolean res = false;
        
        if(o != null) {
          // if(this.calcDist(o) < o.getR() || this.calcDist(o,this.width,0,0) < o.getR()
           //     || this.calcDist(o,0,this.height,0) < o.getR() || this.calcDist(o,0,0,-this.deep) < o.getR()
            //    || this.calcDist(o,this.width,this.height,0) < o.getR() || this.calcDist(o,this.width,0,-this.deep) < o.getR()
            //    || this.calcDist(o,0,this.height,-this.deep) < o.getR() || this.calcDist(o,this.width,this.height,-this.deep) < o.getR()){
              //  res = true;
           // } else {
                if((this.z < o.getZ() + o.getR() && this.z + this.deep > o.getZ())
                    && (this.x < o.getX() + o.getR() && this.x + this.width > o.getX())
                        && (this.y < o.getY() + o.getR() && this.y + this.height > o.getY())){
                            res = true;   
                      }
            //}
        }
        
        return res;
    }
    
    public void setSize(int w, int h, int d) {
        this.width = w;
        this.height = h;
        this.deep = d;
    }
    
    public void toRect(Rect re){
        re.setX(this.x);
        re.setY(this.y - (this.z>>1));
        re.setWidth(this.width);
        re.setHeight((this.deep>>1) + (this.height));
        re.setType((byte) 0);
        re.setSection(this.width >> 6, this.height >> 6, this.deep >> 6);

        
        switch(this.Tipo){
            case 1:
                re.setImg(sprite.Muro.getImg());
                break;
            case 2:
                re.setImg(sprite.ColumnCoin.getImg());
                break;
            case 3:
                re.setImg(sprite.FloorCoin.getImg());
                break;
            case 4:
                re.setImg(sprite.Cesped.getImg());
                break;
            case 5:
                re.setImg(sprite.MuroMadera.getImg());
                break;
            case 6:
                re.setImg(sprite.MuroBaldosas.getImg());
                break;
        }
    }

   @Override
    public boolean equals(Object o) {
        return (o instanceof Block)
                && this.x == ((Block) o).x
                && this.y == ((Block) o).y
                && this.z == ((Block) o).z
                && this.width == ((Block) o).width
                && this.height == ((Block) o).height
                && this.deep == ((Block) o).deep;
    }
    
    /*
    *Retorna si dos bloques son compatibles para la fusión
    *
    */
    public boolean Compatible(Block other) {
        return this.Tipo == other.Tipo
                && ((this.x + this.width == other.x && this.y == other.y && this.z == other.z && this.height == other.height && this.deep == other.deep)
                || (this.y + this.height == other.y && this.x == other.x && this.z == other.z && this.width == other.width && this.deep == other.deep)
                || (this.z + this.deep == other.z && this.x == other.x && this.y == other.y && this.width == other.width && this.height == other.height));
    }

    public void fusion(Block bloque) {
        if(this.x != bloque.x) {
            this.width += bloque.width;      
        }
        if(this.y != bloque.y) {
            this.height += bloque.height;
        }
        if(this.z != bloque.z) {
            this.deep += bloque.deep;
        }
        
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.width;
        hash = 67 * hash + this.height;
        hash = 67 * hash + this.deep;
        return hash;
    }
    
    @Override
    public int compareTo(Block t) {
        
        if(this.z + this.deep <= t.z) {
            return -1;
        }        
        if(t.z + t.deep <= this.z) {
            return 1;
        }
        if(this.y + this.height <= t.y) {
            return -1;
        }
        if(t.y + t.height <= this.y) {
            return 1;    
        }      
        if(this.x + this.width <= t.x){
            return - 1;
        }       
        if(t.x + t.width <= this.x) {
           return 1;
        }       
        return 0;
    } 
}
