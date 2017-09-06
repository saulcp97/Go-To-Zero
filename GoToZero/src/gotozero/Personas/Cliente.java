package gotozero.Personas;

import gotozero.Block;
import gotozero.NPC;
import gotozero.Rect;

public class Cliente extends NPC {


    public Cliente(){
        super();
    }

    public Cliente(Block spawn) {
        super("img/cliente.png", "Cliente");
        this.x = spawn.getX();
        this.y = spawn.getY();
        this.z = spawn.getZ();
    }


    @Override
    public void toRect(Rect re){
        re.setX(this.x);
        re.setY(this.y - (this.z>>1));
        re.setWidth(this.width);
        re.setHeight((this.deep>>1) + (this.height));
        re.setImg(this.Sprite.getImg());
        re.setSection(1, 1, 1);
    }

}
