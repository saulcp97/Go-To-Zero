package gotozero.ObjectGestion;


public class ManejadorDeItems {

    public static Equipamiento getEquipBase(int tipo) {

        Equipamiento res;
        switch (tipo) {
            case 0:
                res = new Equipamiento("Casco Maldito", 0, "cascoGuerreroMalditoVG1", "circle");
                res.getEstadisticas().addEquipMaxHP(100);
                res.getEstadisticas().addEquipBlockAir(500);
                res.getEstadisticas().addEquipBlockThunder(10);
                res.getEstadisticas().addEquipBlockFire(50);
                res.getEstadisticas().addEquipBlockAqua(80);
                res.getEstadisticas().addEquipBlockTerra(100);
                break;
            case 1:
                res = new Equipamiento("Peto de Caballero Negro", 1, "petoCaballeroNegroVG1", "circle");
                res.getEstadisticas().addEquipMaxHP(25);
                break;
            case 2:
                res = new Equipamiento("Musleras de Caballero Negro", 2, "muslerasCaballeroNegroVG1", "circle");
                res.getEstadisticas().addEquipMaxHP(15);
                break;
            case 3:
                res = new Equipamiento("Botas de Caballero Negro", 3, "botasCaballeroNegroVG1", "circle");
                res.getEstadisticas().addEquipMaxHP(10);
                break;
            default:
                res = new Equipamiento("Anillo De Reina", 0, "anilloDiamanteAzul", "circle");
                break;
        }
        return res;
    }

    public static Item getHistoryCat() {
        return new Item("History Cat", "historyCat");
    }

    public static String getDescription(String objectName){
        String resource = "";

        return resource;
    }
}
