package com.mygdx.game.gameItems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Cardinal;
import com.mygdx.game.simulation.RPG;

public class Equipamiento extends Contenedor {
    protected static Texture characterPage = new Texture(Gdx.files.internal("data/img/UIelements/characterPage.png"));
    protected RPG stadistics;

    public Equipamiento() {
        super(14);
        //0-Casco
        //1-Peto
        //2-brazos
        //3-antebrazos y guantes
        //4-pantalones
        //5-botas
        //6-interno
        //7-manto
        //8,9,10,11 -anillos, amuletos y pulseras
        //15-vinculo mascota
        Fuente.setColor(Color.GOLDENROD);
        this.stadistics = new RPG();
    }

    public Items canAdd(Equipable i){
        Items ant = this.Inventario[i.getType()];
        if(ant != null) {
            this.stadistics.sub(((Equipable)ant).getEstadistics());
        }
        this.Inventario[i.getType()] = i;
        this.stadistics.add(i.getEstadistics());
        return ant;
    }

    @Override
    public void drawContainer(SpriteBatch batch){
        batch.begin();
        batch.draw(characterPage,characterPage.getWidth(),0);
        /*for(int i = 0; i < this.Inventario.length; ++i) {
            if(this.Inventario[i] != null) {
                batch.draw(this.Inventario[i].getTexture(),fondoMuro.getWidth() + celda.getWidth() * (i%4), celda.getHeight() * (i/4),64,64);
                Fuente.draw(batch,"" + this.Instancias[i],fondoMuro.getWidth() +celda.getWidth() * (i%4) + 2, celda.getHeight() * (i/4) + 16);
            }
            batch.draw(celda, fondoMuro.getWidth() + celda.getWidth() * (i%4), celda.getHeight() * (i/4));
        }*/

        batch.draw(celda, characterPage.getWidth() + celda.getWidth() * 1.5f + 3,celda.getHeight() * 5 + 4); //1
        if(this.Inventario[0] != null) {
            batch.draw(this.Inventario[0].getTexture(), characterPage.getWidth() + celda.getWidth() * 1.5f + 3,celda.getHeight() * 5 + 4); //1
        }

        batch.draw(celda, characterPage.getWidth() + celda.getWidth() * 1.5f + 3,celda.getHeight() * 3.75f + 4); //2
        batch.draw(celda, characterPage.getWidth() + celda.getWidth() * .25f + 3,celda.getHeight() * 4.25f + 4); //3
        batch.draw(celda, characterPage.getWidth() + celda.getWidth() * 2.75f + 3,celda.getHeight() * 4.25f + 4); //4
        batch.draw(celda, characterPage.getWidth() + celda.getWidth() * 1.5f + 3,celda.getHeight() * 2.50f + 4); //5
        batch.draw(celda, characterPage.getWidth() + 3, celda.getHeight() * 3 + 4); //6
        batch.draw(celda, characterPage.getWidth() + celda.getWidth() * 3 + 3,celda.getHeight() * 3 + 4); //7
        //batch.draw(celda, fondoMuro.getWidth() + celda.getWidth() * 3, 48 + celda.getHeight()); //7

        batch.draw(celda, characterPage.getWidth() + celda.getWidth() * .75f + 3,celda.getHeight() * 1.25f + 4); //7
        batch.draw(celda, characterPage.getWidth() + celda.getWidth() * 2.25f + 3,celda.getHeight() * 1.25f + 4); //7

        batch.draw(celda, characterPage.getWidth() + celda.getWidth() * .75f + 3,4); //7
        batch.draw(celda, characterPage.getWidth() + celda.getWidth() * 2.25f + 3,4); //7

        //Comienza en 16;
        Fuente.draw(batch,"hola",16,16);

        //Fuerza
        Fuente.draw(batch,Cardinal.language.getT("force") + ": " + this.stadistics.getF(),16,112);
        Fuente.draw(batch,Cardinal.language.getT("agility") + ": " + this.stadistics.getA(),16,96);
        Fuente.draw(batch,Cardinal.language.getT("power") + ": " + this.stadistics.getP(),16,80);
        Fuente.draw(batch,Cardinal.language.getT("resistence") + ": " + this.stadistics.getR(),16,64);
        Fuente.draw(batch,Cardinal.language.getT("inteligence") + ": " + this.stadistics.getI(),16,48);
        Fuente.draw(batch,Cardinal.language.getT("power") + ": " + this.stadistics.getV(),16,32);

        batch.end();
    }


}
