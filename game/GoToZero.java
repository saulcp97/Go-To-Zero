package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Cubos.Entidades.vida.Avatar;
import com.mygdx.game.pantallas.MainMenu;

public class GoToZero extends Game {
    //Versión: V.0.1.0 Aqua Currency
    //public Chunk[] Mundo;
    //public int PlayerIn;

    public static Options options = new Options();
    private static final int MegaBytes = 10241024;
    public Chunk chunk;
    public byte modo;
    private FPSLogger fpsLogger;

    public MundoCb mundo;
    private double freeMemory;
    private double TotalMemory;
    private double maxMemory;

    private byte count;
    public boolean modoDialogo;

    @Override
    public void create() {
        this.modo = 0;
        this.chunk = null;
        this.modoDialogo = false;
        this.mundo = null;
        this.count = 0;
        this.fpsLogger = new FPSLogger();
        //Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        this.setScreen(new MainMenu(this));
    }

    public void initial(int mode){
        switch (mode){
            case 1:
                this.mundo = new MundoCb();
                Avatar propietario = new Avatar();
                propietario.visible = false;
                propietario.tangible = false;
                this.mundo.setPlayer(propietario);
                break;
            case 2:
                this.mundo = new MundoCb(options.getLoadChunks());
                Chunk.VIEWDIST = options.getRenderDist();
                this.mundo.setPlayer(new Avatar());
                break;
        }
        this.chunk = new Chunk();
    }

    @Override
    public void render() {
        super.render(); //important!
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            System.out.println("Menú de opciones");
            this.dispose();
            Gdx.app.exit();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.F1)){
            System.out.println("Sistema de ayuda Activado");
            switch(this.modo){
                case 0:
                    System.out.println("Estas en el menu inicial, usa los botones para seleccionar un modo, o en su defecto\nlas teclas numericas");
                    break;
                case 1:
                    System.out.println("Te encuentras en una de las modalidades de supervivencia o aventura, diviertete");
                    break;
                case 2:
                    System.out.println("Estas usando el sistema de gestión y Construcción en Chunks");
                    System.out.println("Controles:\n-B: Activar/Desactivar construcción");
                    break;
            }
        }

        if(0 == this.count--) {
            this.freeMemory = ((double)Runtime.getRuntime().freeMemory())/ MegaBytes;
            this.maxMemory = Runtime.getRuntime().maxMemory() / MegaBytes;
            this.TotalMemory = ((double)Runtime.getRuntime().totalMemory())/MegaBytes;
            System.out.println("Memoria utilizada en MB: " + (this.TotalMemory - this.freeMemory) +"MB");
            this.count = Byte.MAX_VALUE;
        }
        if(0 == this.count--) {
            this.fpsLogger.log();
            this.count = Byte.MAX_VALUE;
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public static class Options {
        private int renderDist;
        private int loadChunks;

        public Options() {
            this.renderDist = 32;
            this.loadChunks = 3;
        }

        public int getLoadChunks() { return loadChunks; }
        public int getRenderDist() { return renderDist; }

        public void setLoadChunks(int loadChunks) { this.loadChunks = loadChunks; }
        public void setRenderDist(int renderDist) { this.renderDist = renderDist; }

        public void printOptions(SpriteBatch batchUI, BitmapFont font) {
            font.draw(batchUI, "Radio de Visión: " + GoToZero.options.getRenderDist(), 0, 20);
            font.draw(batchUI, "Chunks cargados linea: " + GoToZero.options.getLoadChunks(), 0, 45);

            if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && GoToZero.options.getLoadChunks() < 33){
                GoToZero.options.setLoadChunks(GoToZero.options.getLoadChunks() + 2);
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && GoToZero.options.getLoadChunks() > 3){
                GoToZero.options.setLoadChunks(GoToZero.options.getLoadChunks() - 2);
            }

            if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && GoToZero.options.getRenderDist() < 1024){
                GoToZero.options.setRenderDist(GoToZero.options.getRenderDist() + 4);
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && GoToZero.options.getRenderDist() > 8){
                GoToZero.options.setRenderDist(GoToZero.options.getRenderDist() - 4);
            }
        }
    }

}