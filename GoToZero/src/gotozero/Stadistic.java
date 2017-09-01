package gotozero;


import gotozero.algoritmos.Exponencial;

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

    private int Exp, Level, expToNext;

    private int MaxHP;
    
    private int PlusFire, BlockFire;
    private int PlusAqua, BlockAqua;
    private int PlusTerra, BlockTerra;
    private int PlusThunder, BlockThunder;
    private int PlusAir, BlockAir;
    
    private int DMG, DF; //Defensa y ataque fisico



    private int equipFM; //Fuerza Magia(comun todas)

    private int equipMaxHP;

    private int equipPlusFire, equipBlockFire;
    private int equipPlusAqua, equipBlockAqua;
    private int equipPlusTerra, equipBlockTerra;
    private int equipPlusThunder, equipBlockThunder;
    private int equipPlusAir, equipBlockAir;

    private int equipDMG, equipDF; //Defensa y ataque fisico



    /**
     * Constructor for objects of class Stadistic
     */
    public Stadistic() {
        // initialise instance variables
        this.HP = 100;
        this.MaxHP = 100;

        this.Exp = 0;
        this.Level = 1;
        this.expToNext = 100;

        this.BlockAir = 0;
        this.BlockAqua = 0;
        this.BlockFire = 0;
        this.BlockThunder = 0;
        this.BlockTerra = 0;

        this.PlusAir = 0;
        this.PlusAqua = 0;
        this.PlusFire = 0;
        this.PlusTerra = 0;
        this.PlusThunder = 0;

        //Equipamiento
        this.equipMaxHP = 0;
        this.equipFM = 0;
        this.equipBlockAir = this.equipBlockAqua = this.equipBlockFire = this.equipBlockThunder = this.equipBlockTerra = 0;
        this.equipPlusAir = this.equipPlusAqua = this.equipPlusFire = this.equipPlusTerra = this.equipPlusThunder = 0;
        this.equipDF = this.equipDMG = 0;

    }

    public void addExp(int experiencia) {
        this.Exp += experiencia;
        this.tryToLvLUp();
    }

    private void tryToLvLUp() {
        if (this.Exp >= this.expToNext) {
            ++this.Level;
            this.Exp -= this.expToNext;
            this.expToNext = Exponencial.exponential(5,this.Level) * 100;
        }
    }

    public int getLevel() { return this.Level; }
    public int getExp() { return this.Exp; }
    public int getExpToNext() { return this.expToNext; }

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

    public int getVida() { return this.HP; }
    public int getMaxVida() { return (this.MaxHP + this.equipMaxHP); }

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
        return (byte) ((((float)this.HP)  / (this.MaxHP + this.equipMaxHP)) * 100);
    }
    public void modifyHP(int i) {
        this.HP += i;
    }

    public int getBlockAir() { return this.BlockAir + this.equipBlockAir; }
    public int getBlockAqua() { return this.BlockAqua + this.equipBlockAqua; }
    public int getBlockFire() { return this.BlockFire + this.equipBlockFire; }
    public int getBlockTerra() { return this.BlockTerra + this.equipBlockTerra; }
    public int getBlockThunder() { return this.BlockThunder + this.equipBlockThunder; }
    public int getPlusAir() { return this.PlusAir; }
    public int getPlusAqua() { return this.PlusAqua; }
    public int getPlusFire() { return this.PlusFire; }
    public int getPlusTerra() { return this.PlusTerra; }
    public int getPlusThunder() { return this.PlusThunder; }



    public void resetEquipDF() { this.DF = 0; }
    public void resetEquipDMG() { this.DMG = 0; }
    public void resetEquipMaxHP() { this.MaxHP = 0; }
    public void resetEquipFM() { this.FM = 0;}
    public void resetEquipBlockAir() { this.equipBlockAir = 0; }
    public void resetEquipBlockAqua() { this.equipBlockAqua = 0; }
    public void resetEquipBlockFire() { this.equipBlockFire = 0; }
    public void resetEquipBlockTerra() { this.equipBlockTerra = 0; }
    public void resetEquipBlockThunder() { this.equipBlockThunder = 0; }
    public void resetEquipPlusAir() { this.equipPlusAir = 0; }
    public void resetEquipPlusAqua() { this.equipPlusAqua = 0; }
    public void resetEquipPlusFire() { this.equipPlusFire = 0; }
    public void resetEquipPlusTerra() { this.equipPlusTerra = 0; }
    public void resetEquipPlusThunder() { this.equipPlusThunder = 0; }


    public void addEquipDF(int df) { this.equipDF += df; }
    public void addEquipDMG(int dmg) { this.equipDMG += dmg; }
    public void addEquipMaxHP(int hp) { this.equipMaxHP += hp; }
    public void addEquipFM(int fm) { this.equipFM += fm;}
    public void addEquipBlockAir(int bloc) { this.equipBlockAir += bloc; }
    public void addEquipBlockAqua(int bloc) { this.equipBlockAqua += bloc; }
    public void addEquipBlockFire(int bloc) { this.equipBlockFire += bloc; }
    public void addEquipBlockTerra(int bloc) { this.equipBlockTerra += bloc; }
    public void addEquipBlockThunder(int bloc) { this.equipBlockThunder += bloc; }
    public void addEquipPlusAir(int plus) { this.equipPlusAir += plus; }
    public void addEquipPlusAqua(int plus) { this.equipPlusAqua += plus; }
    public void addEquipPlusFire(int plus) { this.equipPlusFire += plus; }
    public void addEquipPlusTerra(int plus) { this.equipPlusTerra += plus; }
    public void addEquipPlusThunder(int plus) { this.equipPlusThunder += plus; }



    public int getEquipDF() { return equipDF; }
    public int getEquipDMG() { return equipDMG; }
    public int getEquipMaxHP() { return equipMaxHP; }
    public int getEquipFM() { return equipFM; }
    public int getEquipBlockAir() { return equipBlockAir; }
    public int getEquipBlockAqua() { return equipBlockAqua; }
    public int getEquipBlockFire() { return equipBlockFire; }
    public int getEquipBlockTerra() { return equipBlockTerra; }
    public int getEquipBlockThunder() { return equipBlockThunder; }
    public int getEquipPlusAir() { return equipPlusAir; }
    public int getEquipPlusAqua() { return equipPlusAqua; }
    public int getEquipPlusFire() { return equipPlusFire; }
    public int getEquipPlusTerra() { return equipPlusTerra; }
    public int getEquipPlusThunder() { return equipPlusThunder; }

}
