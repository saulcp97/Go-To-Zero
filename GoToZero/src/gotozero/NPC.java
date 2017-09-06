package gotozero;


/**
 * Write a description of class NPC here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NPC extends Creatures{
    // instance variables - replace the example below with your own
    private String dialog;
    private int dialogNode;

    /**
     * Constructor for objects of class NPC
     */
    public NPC() {
        super((byte)1,"");
    }

    public NPC(String ruta, String nombre) {
        super((byte)1,ruta);
        this.x = 0;
        this.y = 0;
        this.z = 128;
        this.dialog = "";
        this.Name = nombre;
    }
    
    public NPC(String ruta, String nombre,byte t,int i, int j, int k) {
        super((byte)t,ruta);
        this.Name = nombre;
        this.x = (i<<6) / 5 -128/5;
        this.y = (j<<6) / 5 -128/5;
        this.z = (k<<6) / 5 -128/5;
        this.dialog = "";
    }
    
    public boolean toSpeak(byte dir, int dist, Mage player) {
        switch(dir){
            case 0: 
                return (this.x) < (player.x + player.width) && (this.x + this.width) > (player.x)
                    && (this.y - dist) < (player.y + player.height) && (this.y + this.height) > (player.y)
                    && (this.z) < (player.z + player.deep) && (this.z + this.deep) > (player.z);
            case 1: 
                return (this.x) < (player.x + player.width) && (this.x + this.width + dist) > (player.x)
                    && (this.y) < (player.y + player.height) && (this.y + this.height) > (player.y)
                    && (this.z) < (player.z + player.deep) && (this.z + this.deep) > (player.z);
            case 2: 
                return (this.x) < (player.x + player.width) && (this.x + this.width) > (player.x)
                    && (this.y) < (player.y + player.height) && (this.y + this.height + dist) > (player.y)
                    && (this.z) < (player.z + player.deep) && (this.z + this.deep) > (player.z);
            case 3: 
                return (this.x - dist) < (player.x + player.width) && (this.x + this.width) > (player.x)
                    && (this.y) < (player.y + player.height) && (this.y + this.height) > (player.y)
                    && (this.z) < (player.z + player.deep) && (this.z + this.deep) > (player.z);
            default:
                return false;
        }       
    }
    
    @Override
    public void IA(world w){
    
    }

    public String getDialog() {
        switch (this.dialogNode) {
            case 0:
                this.dialog = "Hola";
                break;
            case 1:
                this.dialog = "Mi nombre no importa, mi raza tampoco.";
                break;
            case 2:
                this.dialog = "Lo único que importa es lo que busco, la Perfect Soul";
                break;
            case 3:
                this.dialog = "la unión de las almas del mundo, las almas de los señores.";
                break;
            case 4:
                this.dialog = "Para obtener las grandes almas, se deben matar a los 3 señores";
                break;
            case 5:
                this.dialog = "El ave de la oscuridad, caballero de la resurrección oscura que renace cada vez que es derrotado.";
                break;
            case 6:
                this.dialog = "El arbol del poder, el señor mas poderoso de todos, poder inconmensurable pero alejada de lo que mas quiere.";
                break;
            case 7:
                this.dialog = "El dragón cristalino, la bestia inmortal, puede ser destruido pero nunca morir, lo que puede ser una mayor tortura aun.";
                break;
            case 8:
                this.dialog = "Si se lograra reunir la Perfect Soul se podria dominar este y cualquier mundo incluso el 4to mundo.";
                break;
            case 9:
                this.dialog = "Bueno me tengo que ir, aun no estoy terminada de programar";
                break;
            case 10:
                this.dialog = "null";
                this.dialogNode = 0;
                break;
        }
            ++this.dialogNode;
        return this.dialog;
    }

    public void selectRoute(int option) {




    }

    @Override
    public void toRect(Rect re){
        re.setX(this.x + ((this.width - this.TamanyoX) >> 1));
        re.setY(this.y - (this.deep>>1) + (64 - this.TamanyoY));
        re.setWidth(this.TamanyoX);
        re.setHeight(this.TamanyoY);
        re.setImg(this.Sprite.getImg());
        re.setSection(1, 1, 1);
    } 
}
