package com.mygdx.game.gameItems;

import com.badlogic.gdx.graphics.Texture;

public class Items {
    protected final String name;
    protected String descripcion;
    private Texture texture;
    private int value;
    public Items(String nombre){
        this.name = nombre;
        this.texture = new Texture("data/img/historyCat.png");
        this.descripcion = "Soy Naruto";
        this.value = 1;
    }

    public Texture getTexture(){ return this.texture; }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Items) && this.name.equals(((Items) o).name);
    }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public int getValue() { return value; }
    public String getName() { return name; }
    public String getDescripcion() {return  descripcion;}
}

