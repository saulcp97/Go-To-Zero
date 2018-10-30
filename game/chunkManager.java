package com.mygdx.game;

public class chunkManager extends Thread {

    private Chunk input;
    private Chunk output;
    private Chunk[][] chunks;
    private int x,y;
    private int comand;

    /*
    * 0-Crear
    * 1-Cargar
    * 2-Guardar/Intercmbiar
     */

    public chunkManager(Chunk[][] chunks, int x,int y){
        this.chunks = chunks;
        this.input = chunks[x][y];
        this.output = new Chunk();
        this.output.loaded = false;
        this.x = x;
        this.y = y;
        this.comand = 0;
    }

    public void setComand(int i){
        this.comand = i;
    }

    /*
    @Override
    public void run() {
        if(comand == 0) {
            this.output.setX(this.input.getX());
            this.output.setY(this.input.getY());

            this.chunks[this.x][this.y] = this.output;
            this.chunks[this.x][this.y].loaded = true;
        }
    }
    */
}