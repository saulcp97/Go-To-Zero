package gotozero.ObjectGestion;

import gotozero.Stadistic;


public class Equipo extends Inventary {

    private Stadistic estadisticas;

    public Equipo(Stadistic link) {
        super("Equipamiento",4);
        this.estadisticas = link;
    }

    public void equipar(Equipamiento equipo) {
        //Aun no esta implementada totalmente la función de getPosition
        Equipamiento ant = (Equipamiento) this.getByPosition(equipo.getPosCode());

        if(ant != null) {
            this.estadisticas.addEquipMaxHP(-ant.getEquipMaxHP());
            this.estadisticas.addEquipDF(-ant.getEquipDF());
            this.estadisticas.addEquipDMG(-ant.getEquipDMG());
            this.estadisticas.addEquipFM(-ant.getEquipFM());

            this.estadisticas.addEquipBlockAir(-ant.getEquipBlockAir());
            this.estadisticas.addEquipBlockAqua(-ant.getEquipBlockAqua());
            this.estadisticas.addEquipBlockFire(-ant.getEquipBlockFire());
            this.estadisticas.addEquipBlockTerra(-ant.getEquipBlockTerra());
            this.estadisticas.addEquipBlockThunder(-ant.getEquipBlockThunder());

            this.estadisticas.addEquipPlusAir(-ant.getEquipPlusAir());
            this.estadisticas.addEquipPlusAqua(-ant.getEquipPlusAqua());
            this.estadisticas.addEquipPlusFire(-ant.getEquipPlusFire());
            this.estadisticas.addEquipPlusTerra(-ant.getEquipPlusTerra());
            this.estadisticas.addEquipPlusThunder(-ant.getEquipPlusThunder());
        }

        this.estadisticas.addEquipMaxHP(equipo.getEquipMaxHP());
        this.estadisticas.addEquipDF(equipo.getEquipDF());
        this.estadisticas.addEquipDMG(equipo.getEquipDMG());
        this.estadisticas.addEquipFM(equipo.getEquipFM());

        this.estadisticas.addEquipBlockAir(equipo.getEquipBlockAir());
        this.estadisticas.addEquipBlockAqua(equipo.getEquipBlockAqua());
        this.estadisticas.addEquipBlockFire(equipo.getEquipBlockFire());
        this.estadisticas.addEquipBlockTerra(equipo.getEquipBlockTerra());
        this.estadisticas.addEquipBlockThunder(equipo.getEquipBlockThunder());

        this.estadisticas.addEquipPlusAir(equipo.getEquipPlusAir());
        this.estadisticas.addEquipPlusAqua(equipo.getEquipPlusAqua());
        this.estadisticas.addEquipPlusFire(equipo.getEquipPlusFire());
        this.estadisticas.addEquipPlusTerra(equipo.getEquipPlusTerra());
        this.estadisticas.addEquipPlusThunder(equipo.getEquipPlusThunder());

        this.addItemInPosition(equipo,equipo.getPosCode());
        System.out.println("" + equipo.getEquipMaxHP());
    }

    public void desEquipar(int i) {
        this.addItemInPosition(null,i);
    }

}