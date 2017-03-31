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
    
    public Magia(byte t, Mage mago) {
        super(mago.getX(), mago.getY(), mago.getZ(), 0, 0, 0, 8);
        this.Element = t;
        switch(mago.getGrade()) {
            case 0:
                this.setVelo(0,-20,0);
                break;
            case 15:
                this.setVelo(20,0,0);
                break;
            case 30:
                this.setVelo(0,20,0);
                break;
            case 45:
                this.setVelo(-20,0,0);
                break;
        }
        
        this.Durabilidad = 10;
    }
    
    public Magia(byte t,lifes mago,int mx, int my) {
        super(mago.getX()+4, mago.getY(), mago.getZ(), mx, my, 0, 8);
        this.Element = t;
        //this.setVelo(mx,my,0);
        
        this.Durabilidad = 10;
    }
    
    public int getDurabilidad() {
        return this.Durabilidad;
    }
    
    public void dismDurabilidad (int n) {
        this.Durabilidad -= n;
    }
    
    public void toRect(Rect e) {
        super.toRect(e);
        switch(this.Element) {
            case 0:
            case 1:
                e.setCol(Color.blue);
                break;
            case 2:
            case 3:
                e.setCol(Color.orange);
                break;
            case 4:
            case 5:
                e.setCol(Color.yellow);
                break;
            case 6:
            case 7:
                e.setCol(Color.red);
                break;
            case 8:
            case 9:
                e.setCol(Color.gray);
                break;
        }

        
    }
}
