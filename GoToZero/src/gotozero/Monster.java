package gotozero;


import gotozero.ObjectGestion.Item;
import gotozero.ObjectGestion.ManejadorDeItems;

/**
 * Write a description of class Monster here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Monster extends Creatures {
    private byte Especie;
    private int Grado;
    /**
     * Constructor for objects of class Monster
     */
    
    public Monster() {
        //la raza 100 es Roca Mal Hudmorada
        super((byte)100,"img/ello.png");
        this.Especie = (byte) 100;
    }
    
    public Monster(byte especie, String nombre, String raza) {
            super(especie, "img/" + nombre + raza + ".png");
            if(raza.equals("Cebolla")) {
                this.Sprite = new sprite("img/" + nombre + raza + ".png",4);
            }
        this.Especie = especie;
        this.Name = nombre;
        this.Grado = 0;
        this.setPos(192,192 * 2,640);
        
        this.atributizar(raza);
    }
    
    private void atributizar(String raza) {
        switch(raza) {
            case "BOSS":
                switch(this.Name) {
                    case "Calorius":
                        //Calorius es mas ancha que profunda
                        this.setSize(192, 96, 192);
                        this.setSpriteSize(192,192);
                        
                        this.estadisticas.setMaxHP(1000);
                        this.estadisticas.setHP(1000);
                        this.estadisticas.setDF(500);
                        this.estadisticas.setDMG(100);
                        break;
                    case "Scarjav":
                        this.setSize(320, 160, 320);
                        this.setSpriteSize(320,320);

                        this.estadisticas.setMaxHP(1000);
                        this.estadisticas.setHP(1000);
                        this.estadisticas.setDF(500);
                        this.estadisticas.setDMG(100);
                        break;
                }
                break;
            case "Borracho1":
                switch(this.Name){
                    case "Slime":
                        this.setSize(32,32,32);
                        this.setSpriteSize(64,64);
                        
                        this.estadisticas.setMaxHP(10);
                        this.estadisticas.setHP(10);
                        this.estadisticas.setDF(1);
                        this.estadisticas.setDMG(1);
                        break;
                }
                break;
            case "Cebolla":
                switch (this.Name){
                    case "Guerrero":
                        this.setSize(32,32,32);
                        this.setSpriteSize(64,64);

                        this.estadisticas.setMaxHP(50);
                        this.estadisticas.setHP(50);
                        this.estadisticas.setDF(1);
                        this.estadisticas.setDMG(1);
                        break;
                }
                break;
            case "MIERDA":
                break;
        }
    }

    public void reubicar(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setPos(int x, int y, int z) {
        this.setPosicion(x,y);
        this.setZ(z);
    }
    
    @Override
    public void toRect(Rect re){
        re.setX(this.x);
        re.setY(this.y - (this.TamanyoY>>1) - (this.z>>1));
        re.setWidth(this.TamanyoX);
        re.setHeight(this.TamanyoY);
        re.setImg(this.Sprite.getImg());
        
        re.setSection(1, 1, 1);
        //entry.setX(this.x - (this.TamanyoX>>2));
        //entry.setY(this.y - (this.TamanyoY>>1)-(this.z>>1));
        //entry.setWidth(this.TamanyoX);
        //entry.setHeight(this.TamanyoY);
        //entry.setImg(this.Sprite.getImg());  
    }
    
    public boolean AgroZone(Block obj, int r){
        return (this.x - r)<(obj.x + obj.width) && (this.x + this.width + r) > (obj.x)
            && (this.y - r)<(obj.y + obj.height) && (this.y + this.height + r) > (obj.y)
            && (this.z - r)<(obj.z + obj.deep) && (this.z + this.deep + r) > (obj.z);
    }

    public Item getLoot() {
        return ManejadorDeItems.getHistoryCat();
    }

    @Override
    public void IA(world w){
        if(cooldown != 0){
            --cooldown;
        }
        switch(this.Name) {
            case "Calorius":
                if(AgroZone(w.getPlayer(),1000)){
                    if(this.cooldown == 0) {
                        this.AtackDir(w);
                    }
                }
                break;
            case "Slime":
                if(AgroZone(w.getPlayer(),1000)){
                    if(!AgroZone(w.getPlayer(), 16)){
                        w.moveInWorld(this, -(x - w.getPlayer().x) >> 7, -(y - w.getPlayer().y) >> 7);  
                    } else {
                        if(this.cooldown == 0) {
                            this.Atack(w);
                        }
                    }
                }
                break;
            case "Scarjav":
                if(AgroZone(w.getPlayer(),1000)){
                    if(this.cooldown == 0) {
                        this.Atack(w);
                    }
                }
                break;
            case "Guerrero":
                if(AgroZone(w.getPlayer(),1000)){
                    if(!AgroZone(w.getPlayer(), 16)){
                        //w.moveInWorld(this, -(x - w.getPlayer().x) >> 7, -(y - w.getPlayer().y) >> 7);
                        int ModuloX = 0;
                        int ModuloY = 0;
                        if(x + width/2 != w.getPlayer().getX() + w.getPlayer().getWidth()) {
                            ModuloX = x + width / 2 > w.getPlayer().getX() + w.getPlayer().getWidth() ? -1 : 1;
                        }
                        if(y + height/2 != w.getPlayer().getY() + w.getPlayer().getHeight()) {
                            ModuloY = y + height / 2 > w.getPlayer().getY() + w.getPlayer().getHeight() ? -1 : 1;
                        }

                        if(ModuloX > 0) {
                            this.setDir((byte)1);
                        } else if (ModuloX < 0) {
                            this.setDir((byte)3);
                        }else{
                            if(ModuloY > 0) {
                                this.setDir((byte)2);
                            } else if (ModuloY < 0) {
                                this.setDir((byte)0);
                            }
                        }



                        w.moveInWorld(this, ModuloX,ModuloY);

                        //direccion = Modulo
                    } else {
                        if(this.cooldown == 0) {
                            this.Atack(w);
                        }
                    }
                }
        }
    }
    
    private void Atack(world w){
       Magia aux = new Magia((byte) 10, this, -(x - w.getPlayer().x), -(y - w.getPlayer().y) , 20);
       aux.setVelo(-((x - w.getPlayer().x)>>6), -((y - w.getPlayer().y)>>6), 0);
       aux.setDurability(60);
       w.addHechizo(aux);
       this.cooldown = (byte)30;
    }

    private void AtackDir(world W) {
        Magia[] magiaDerecha = new Magia[8];
        Magia[] magiaIzquierda = new Magia[8];

        for(int i = 0; i < magiaDerecha.length; ++i) {
            magiaIzquierda[i] = new Magia((byte) 10, this, 0, 0, 20);
            magiaIzquierda[i].setVelo((int)((8 - i) * Math.cos(((this.Grado + i * 8) * Math.PI) / 180)), (int)((8 - i) * Math.sin(((this.Grado + i * 8) * Math.PI) / 180)), 0);

            magiaDerecha[i] = new Magia((byte) 10, this, 0, 0, 20);
            magiaDerecha[i].setX(this.x + this.width - magiaIzquierda[i].getR()/2);
            magiaDerecha[i].setVelo((int)(-(8 - i) * Math.cos(((this.Grado + i * 8) * Math.PI) / 180)), (int)((8 - i) * Math.sin(((this.Grado + i * 8) * Math.PI) / 180)), 0);

            magiaIzquierda[i].setDurability(1000 - (20 - i * 10));
            magiaDerecha[i].setDurability(1000 - (20 - i * 10));
            W.addHechizo(magiaIzquierda[i]);
            W.addHechizo(magiaDerecha[i]);
        }
        this.cooldown = (byte)90;

        this.Grado += 3;
    }

    @Override
    public void setDir(byte i){
        if(dir != i) {
            this.dir = i;
            this.Sprite.setOutput(i);
        }
    }

}