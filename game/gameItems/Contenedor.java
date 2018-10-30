package com.mygdx.game.gameItems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Contenedor {
    protected Items[] Inventario;
    protected int[] Instancias;
    protected int puntero;
    protected int money;
    protected static Texture fondoMuro = new Texture(Gdx.files.internal("data/img/UIelements/fondoMuro.png"));
    protected static Texture celda = new Texture(Gdx.files.internal("data/img/UIelements/casilla.png"));
    protected static BitmapFont Fuente = new BitmapFont();
    protected String[] functions;
    private int columns;
    private boolean antSt;

    public Items detalles;
    public Items Use;
    public byte useCode;
    /*
        0 - Nada
        1 - Equipar
        2 - Guardar

     */

    public Contenedor(int size){
        this.puntero = 0;
        this.Inventario = new Items[size];
        this.Instancias = new int[size];
        this.money = 0;//Esto debe de ir a la mochila del usuario
        this.columns = 4;
        this.detalles = null;
        this.functions = null;
        this.Use = null;
        this.useCode = 0;
        this.antSt = false;
        Fuente.setColor(Color.RED);
        Fuente.getData().setScale(1.15f);
    }

    public Contenedor(){
        this.puntero = 0;
        this.Inventario = new Items[16];
        this.Instancias = new int[16];

        this.columns = 4;
        this.detalles = null;
        this.functions = null;
        this.antSt = false;
        this.Use = null;
        this.useCode = 0;
        Fuente.setColor(Color.RED);
        Fuente.getData().setScale(1.15f);
    }

    public boolean canAdd(Items ob, int count) {
        for (int i = 0; i < this.Inventario.length; ++i) {
            if (this.Inventario[i] != null && this.Inventario[i].equals(ob)) {
                this.Instancias[i] += count;
                return true;
            }
        }

        if (count > 0) {
            for (int i = 0; i < this.Inventario.length; ++i) {
                if (this.Inventario[i] == null) {
                    this.Inventario[i] = ob;
                    this.Instancias[i] = count;
                    return true;
                }
            }
        }
        return false;
    }

    public void setPuntero(int ind) { this.puntero = ind; }
    public int getPuntero() { return puntero; }

    public void setInstancias(int[] instancias) { Instancias = instancias; }
    public int[] getInstancias() { return Instancias; }

    public void setInventario(Items[] inventario) { Inventario = inventario; }
    public Items[] getInventario() { return Inventario; }

    public Items substractOne(int index) {
        Items res = this.Inventario[index];
        if (--this.Instancias[index] == 0) {
            this.Inventario[index] = null;
        }
        return res;
    }

    public void drawContainer(SpriteBatch batch){
        batch.begin();
        batch.draw(fondoMuro,0,0);

        for(int i = 0; i < 16; ++i) {
            if(this.Inventario[i] != null) {
                batch.draw(this.Inventario[i].getTexture(), celda.getWidth() * (i % 4), celda.getHeight() * (i / 4), 64, 64);
                if (this.Instancias[i] > 1) {
                    Fuente.draw(batch, "" + this.Instancias[i], celda.getWidth() * (i % 4) + 2, celda.getHeight() * (i / 4) + 16);
                }
            }
            batch.draw(celda, celda.getWidth() * (i%4), celda.getHeight() * (i/4));
        }

        batch.draw(celda,(this.puntero%4)*64, (this.puntero/4) * 64,32,32);
        if(this.functions != null) {
            if(this.puntero % this.columns != 3) {
                for (int i = 0; i < this.functions.length; ++i) {
                    Fuente.draw(batch, this.functions[i], (this.puntero % 4) * 64 + celda.getWidth(), (this.puntero / 4) * 64 + celda.getHeight() + 16 - 16 * (i));
                }
            } else {
                for (int i = 0; i < this.functions.length; ++i) {
                    Fuente.draw(batch, this.functions[i], (this.puntero % 4) * 64 - celda.getWidth(), (this.puntero / 4) * 64 + celda.getHeight() + 16 - 16 * (i));
                }
            }
        }
        Fuente.draw(batch, ""+this.money,12, 64 * 5);
        batch.end();
    }

    public void Click(boolean prop) {
        if(prop) {
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                //System.out.println(Gdx.input.getX() + ", " + (Gdx.graphics.getHeight() - Gdx.input.getY()));
                if(Gdx.input.getX() > 0 && Gdx.input.getX() <= 64 * 4
                        && Gdx.input.getY() > 0 && (Gdx.graphics.getHeight() - Gdx.input.getY()) <= 64 * 4) {

                    if(this.puntero != (Gdx.input.getX()/64) + ((Gdx.graphics.getHeight() - Gdx.input.getY())/64) * 4) {
                        if(this.functions != null

                                && (//Selecciona la primera o columnas intermedias
                                    this.puntero % this.columns != this.columns - 1 && (
                                        ((this.puntero % 4) * 64 + celda.getWidth() < Gdx.input.getX())
                                        && ((this.puntero % 4) * 64 + celda.getWidth() * 2 >= Gdx.input.getX())
                                        && ((Gdx.graphics.getHeight() - Gdx.input.getY()) / 64) == this.puntero / 4
                                    )
                                    || (//Selecciona la ultima columna
                                            this.puntero % this.columns == this.columns - 1 &&
                                                    ((this.puntero % 4) * 64 >= Gdx.input.getX())
                                        && ((this.puntero % 4) * 64 - celda.getWidth() <= Gdx.input.getX())
                                        && ((Gdx.graphics.getHeight() - Gdx.input.getY()) / 64) == this.puntero / 4
                                    )
                                )

                                ) {
                            //(this.puntero % 4) * 64 + celda.getWidth(), (this.puntero / 4) * 64 + celda.getHeight() + 16 - 16 * (i));

                            //Forma chapucera de definir las secciones de botones
                            int option = 3 - ((Gdx.graphics.getHeight() - Gdx.input.getY()) / 16) % 4;
                            System.out.println("Seleccionada opción: " + option);


                            if(!this.antSt) {
                                //Vender
                                if ((this.Inventario[this.puntero] instanceof Alimentos || this.Inventario[this.puntero] instanceof Equipable) && option == 2
                                        || !(this.Inventario[this.puntero] instanceof Alimentos || this.Inventario[this.puntero] instanceof Equipable) && option == 1) {
                                    this.money += this.Inventario[this.puntero].getValue();
                                    this.substractOne(this.puntero);
                                    if(this.Inventario[this.puntero] == null) {
                                        this.functions = null;
                                        this.detalles = null;
                                    }
                                    this.antSt = true;
                                }
                                //Equipar
                                if(this.Inventario[this.puntero] instanceof Equipable && option == 0){
                                    System.out.println("He intentado equipar: " + this.Inventario[this.puntero].name);
                                    this.Use = this.Inventario[this.puntero];
                                    this.useCode = 1;
                                    this.substractOne(this.puntero);
                                    if(this.Inventario[this.puntero] == null) {
                                        this.functions = null;
                                        this.detalles = null;
                                    }
                                    this.antSt = true;
                                }


                                //Descripción
                                if((this.Inventario[this.puntero] instanceof Alimentos || this.Inventario[this.puntero] instanceof Equipable) && option == 1
                                        || !(this.Inventario[this.puntero] instanceof Alimentos || this.Inventario[this.puntero] instanceof Equipable) && option == 0) {
                                    this.detalles = this.Inventario[this.puntero];
                                    this.antSt = true;
                                }
                            }
                        } else {
                            this.puntero = (Gdx.input.getX() / 64) + ((Gdx.graphics.getHeight() - Gdx.input.getY()) / 64) * 4;
                            this.functions = null;
                            this.antSt = true;
                            this.detalles = null;
                        }
                    } else if (!this.antSt && this.Inventario[this.puntero] != null){
                        if(this.functions == null) {
                            //Puede estar vacia
                            if(this.Inventario[this.puntero] instanceof Alimentos) {
                                this.functions = new String[]{this.Inventario[this.puntero].name, "Consumir", "Detalles", "Vender"};
                            } else if(this.Inventario[this.puntero] instanceof Equipable) {
                                this.functions = new String[]{this.Inventario[this.puntero].name, "Equipar", "Detalles", "Vender"};
                            } else {
                                this.functions = new String[]{this.Inventario[this.puntero].name, "Detalles", "Vender"};
                            }
                            this.antSt = true;
                        } else {
                            this.functions = null;
                            this.antSt = true;
                            this.detalles = null;
                        }
                        //this.substractOne(this.puntero);
                    }
                    //this.puntero = (Gdx.input.getX()/64) + ((Gdx.graphics.getHeight() - Gdx.input.getY())/64) * 4;
                    //System.out.println(this.puntero);
                }
            } else if(this.antSt){
                this.antSt = false;
            }
        }
    }

    public void removeDetalles(){
        this.detalles = null;
    }

}