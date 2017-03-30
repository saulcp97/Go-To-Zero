package gotozero;


/**
 * Write a description of class Stadistic here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Stadistic {
    // instance variables - replace the example below with your own
    private int HP;
    private int FM; //Fuerza Magia(comun todas)
    
    private int MaxHP;
    
    private int PlusFire, BlockFire;
    private int PlusAqua, BlockAqua;
    private int PlusTerra, BlockTerra;
    private int PlusThunder, BlockThunder;
    private int PlusAir, BlockAir;
    
    private int DMG, DF; //Defensa y ataque fisico
    
    /**
     * Constructor for objects of class Stadistic
     */
    public Stadistic() {
        // initialise instance variables
        this.HP = 100;
        this.MaxHP = 100;
    }
    
    public void setDMG(int dmg) {
        this.DMG = dmg;
    }
    
    public void setDF(int df) {
        this.DF = df;
    }
    
    public int getDMG() {
        return this.DMG;
    }
    
    public int getDF() {
        return this.DF;
    }
    
    public void haveDamage(int At, int dmg){
        this.HP -= Math.round((75 + Math.random()*25)*((At * dmg)/ (12 * this.DF) + 2));
    }
    
    public void setMaxHP(int m) {
        this.MaxHP = m;
    }
    
    public void setHP(int hp) {
        this.HP = hp;
    }
    
    public byte getHP() {
        return (byte) (this.HP/this.MaxHP * 100);
    }
    
    public void modifyHP(int i) {
        this.HP += i;
    }
}
