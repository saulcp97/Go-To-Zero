package com.mygdx.game.Cubos.CuboData.Cointainers;

import com.mygdx.game.Cubos.Interactuable;
import com.mygdx.game.gameItems.Contenedor;

public class Cofre extends Interactuable{
    private Contenedor Inventario;

    public Cofre(int x, int y, int z, byte t, int width, int height, int deep) {
        super(x, y, z, t, width, height, deep);
        this.Inventario = new Contenedor();
    }

    @Override
    public void interact(int CodeInput){
        switch (CodeInput) {
            case 0: //Actuar en 'E'
                //OPEN
                break;
        }
    }
}
