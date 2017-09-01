package gotozero;

import java.awt.Color;
/**
 * Write a description of class Magia here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Magia extends Sphere {
    private byte Element;
    
    private int masa;
    private int Fuerza;
    private int Durabilidad;
    
    /**
     *
     * @param t
     */
    public Magia(byte t){
        super();
        //Agua: Propia-0,Lanzar-10   
        //Tierra: Propia-20, Lanzar - 30
        //Electricidad: 40,50
        //Fuego:60,70
        //Aire:80,90
        this.Fuerza = 100;
        this.Element = t;
        this.Durabilidad = -404;
    }
    
    /**
     *
     * @param t
     * @param l
     * @param mx
     * @param my
     */
    public Magia(byte t,lifes l,int mx, int my,int r) {
        super(l.getX()+4, l.getY(), l.getZ(), mx, my, 0, r);
        this.Element = t;
        //this.setVelo(mx,my,0);
        this.Durabilidad = 10;
    }
    
    public void setDurability(int d){
        this.Durabilidad = d;
    }
    
    public byte getElement() {
        return this.Element;
    }
    
    /**
     *
     * @return
     */
    public int getDurabilidad() {
        return this.Durabilidad;
    }
    
    /**
     *
     * @param n
     */
    public void dismDurabilidad (int n) {
        this.Durabilidad -= n;
    }
    
    /**
     *
     * @param e
     */
    @Override
    public void toRect(Rect e) {
        super.toRect(e);
        e.setImg(null);
        e.setSection(1, 1, 1);
        
        switch(this.Element) {
            case 0:
            case 1:
                e.setCol(Color.blue);
                e.setType((byte)0);
                break;
            case 2:
            case 3:
                e.setCol(Color.orange);
                e.setType((byte)0);
                break;
            case 4:
            case 5:
                e.setCol(Color.yellow);
                e.setType((byte)0);
                break;
            case 6:
            case 7:
                e.setCol(Color.red);
                e.setType((byte)0);
                break;
            case 8:
            case 9:
                e.setCol(Color.gray);
                e.setType((byte)0);
                break;
            case 10://Magia monstruo
                e.setCol(Color.darkGray);
                e.setImg(sprite.BalaBoss.getImg());
                e.setType((byte)1);
                break;
            default:
                e.setCol(Color.PINK);
                e.setImg(sprite.BalaBoss.getImg());
                e.setType((byte)1);
        }
    }
}
