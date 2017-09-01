package gotozero.Bloques;

import gotozero.Block;
import gotozero.ObjectGestion.Inventary;
import gotozero.ObjectGestion.Item;
import gotozero.Rect;
import gotozero.sprite;

/**
 * Created by saulc on 15/07/2017.
 */
public class Cofre extends Block {

    private Inventary Conjunto;

    public Cofre(){
        super();
        this.Conjunto = new Inventary("Objetos", 16);
    }

    public Cofre(int x, int y, int z){
        super();
        this.x = x;
        this.y = y;
        this.z = z;

        this.width = 16;
        this.height = 16;
        this.deep = 16;
        this.Tipo = 13;

        this.Conjunto = new Inventary("Cadaver", 4);
    }

    public void addToCofre(Item it){
        this.Conjunto.addItem(it);
    }

    public Item substracItemIndex(int index) {
        return this.Conjunto.substractByPosition(index);
    }

    public void volcarItems(Inventary objetivo) {
        if(objetivo != null) {
            Item auxiliar;
            for (int i = 0; i < this.Conjunto.getInventaryLength(); ++i) {
                auxiliar = this.Conjunto.getByPosition(i);
                if(auxiliar != null) {
                    objetivo.addItem(auxiliar);
                }
            }
        }
    }

    @Override
    public void toRect(Rect re){
        re.setX(this.x);
        re.setY(this.y - (16));
        re.setWidth(16);
        re.setHeight(24);
        re.setImg(sprite.altarLoot.getImg());

        re.setSection(1, 1, 1);
    }

}
