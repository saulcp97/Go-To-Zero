package gotozero.ObjectGestion;


import gotozero.Rect;
import gotozero.Stadistic;
import gotozero.sprite;

/**
 * Write a description of class Equipamiento here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Equipamiento extends Item {

    private int Tipo;
    /*
        0 -> Casco
        1 -> Peto
        2 -> Musleras
        3 -> Botas
     */

    private Stadistic estadisticas;
    private Rect Equiped;
    // instance variables - replace the example below with your own

    /**
     * Constructor for objects of class Equipamiento
     */
    public Equipamiento(String str, int tipo, String ruta, String rutaEquipado) {
        super(str, ruta);
        this.Tipo = tipo;
        this.estadisticas = new Stadistic();
        this.Equiped = new Rect();
        this.Equiped.setImg(new sprite("img/object/sprite/"+ rutaEquipado +".png").getImg());
    }

    public int getPosCode() { return this.Tipo; }

    public void setEstadisticas(Stadistic est) { this.estadisticas = est; }
    public Stadistic getEstadisticas() { return estadisticas; }

    public int getEquipDF() { return this.estadisticas.getEquipDF(); }
    public int getEquipDMG() { return this.estadisticas.getEquipDMG(); }
    public int getEquipMaxHP() { return this.estadisticas.getEquipMaxHP(); }
    public int getEquipFM() { return this.estadisticas.getEquipFM(); }

    public int getEquipBlockAir() { return this.estadisticas.getEquipBlockAir(); }
    public int getEquipBlockAqua() { return this.estadisticas.getEquipBlockAqua(); }
    public int getEquipBlockFire() { return this.estadisticas.getEquipBlockFire(); }
    public int getEquipBlockTerra() { return this.estadisticas.getEquipBlockTerra(); }
    public int getEquipBlockThunder() { return this.estadisticas.getEquipBlockThunder(); }
    public int getEquipPlusAir() { return this.estadisticas.getEquipPlusAir(); }
    public int getEquipPlusAqua() { return this.estadisticas.getEquipPlusAqua(); }
    public int getEquipPlusFire() { return this.estadisticas.getEquipPlusFire(); }
    public int getEquipPlusTerra() { return this.estadisticas.getEquipPlusTerra(); }
    public int getEquipPlusThunder() { return this.estadisticas.getEquipPlusThunder(); }
}
