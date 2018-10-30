package com.mygdx.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Cubos.Block;
import com.mygdx.game.GoToZero;
import com.mygdx.game.ModosDeJuego.modeMaid;

public class MaidCafeView implements Screen {
    GoToZero juego;
    ShapeRenderer drawer;
    SpriteBatch batch;
    SpriteBatch uiRenderer;
    BitmapFont font;

    Viewport viewport;
    Camera camera;

    Viewport staticView;

    private modeMaid controlModeMaid;

    public MaidCafeView(GoToZero game) {
        this.juego = game;
        this.drawer = new ShapeRenderer();
        this.batch = new SpriteBatch();
        this.uiRenderer = new SpriteBatch();
        this.font = new BitmapFont();

        font.setColor(com.badlogic.gdx.graphics.Color.RED);
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(640,480,camera);
        viewport.apply();
        camera.position.set(320,240,0);

        staticView = new ExtendViewport(640,480);
        staticView.apply();
        staticView.getCamera().position.set(640,240,0);

        controlModeMaid = new modeMaid();

        this.juego.mundo.setMouse((byte)0, Block.buyCube);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        staticView.getCamera().update();
        Gdx.gl.glClearColor(0.75f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        uiRenderer.setProjectionMatrix(staticView.getCamera().combined);

        batch.begin();
        this.juego.mundo.drawWorld(batch);
        batch.end();

        uiRenderer.begin();
        font.draw(uiRenderer,"Posicion Raton calculada" + (((float) Gdx.input.getX() / Gdx.graphics.getWidth()) * camera.viewportWidth) + ", " + (31*64 - (31 - ((int)((((int)(((float) Gdx.input.getY() / Gdx.graphics.getHeight()) * camera.viewportHeight))) + camera.position.y  + camera.viewportHeight/2) / 64)) * 64),0,98);
        font.draw(uiRenderer,"Posicion calculada" + ((int)((((int)(((float) Gdx.input.getX() / Gdx.graphics.getWidth()) * 640)) + camera.position.x - 320)/64)*64) + ", " + Gdx.graphics.getHeight(),0,84);
        font.draw(uiRenderer,"Tamany conegut pantalla" + Gdx.graphics.getWidth() + ", " + Gdx.graphics.getHeight(),0,70);
        font.draw(uiRenderer,"Posicio real Mouse" + (Gdx.input.getX() +  ", " +  Gdx.input.getY()),0,56);
        font.draw(uiRenderer,"Posición real de la Camara:" + camera.position.x + ", " + viewport.getCamera().position.y,0,42);
        font.draw(uiRenderer,"Hola camarada! Estas en :" + (Gdx.input.getX() + (camera.position.x - 320))+ ", " + ((viewport.getCamera().position.y + 240) - Gdx.input.getY()),0,28);
        font.draw(uiRenderer,"Estas en el cuadrante :" + ((int)(Gdx.input.getX() + (viewport.getCamera().position.x - 320)) / 64) + ", " + (31*64 - ((31 - ((int)((camera.position.y + camera.viewportHeight/2) - ((int)(((float) Gdx.input.getY() / Gdx.graphics.getHeight()) * camera.viewportHeight))) / 64)))*64),0,14);
        uiRenderer.end();

        controlModeMaid.draw(uiRenderer, this.camera);

        if(controlModeMaid.botonConstruccion.isPressed()){
            this.controlModeMaid.changeModoConstruction();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            this.juego.mundo.addComand(1);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            this.juego.mundo.addComand(3);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            this.juego.mundo.addComand(0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            this.juego.mundo.addComand(2);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            this.controlModeMaid.changeModoConstruction();
            this.juego.mundo.setMouseIsVisible(this.controlModeMaid.isModoConstruccion());
            this.juego.mundo.setPantalla(this.camera.viewportWidth, this.camera.viewportHeight);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.V)) {
            this.controlModeMaid.changeModoPlantilla();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
            this.juego.mundo.resetDB();
        }

        this.juego.mundo.tick();

        if(this.juego.mundo.db) {
            uiRenderer.begin();
            font.draw(uiRenderer,this.juego.mundo.message,0,120);
            uiRenderer.end();
        }

        if(controlModeMaid.isModoConstruccion()) {
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && this.controlModeMaid.negocio.Presupuesto >= 5) {
                if(this.juego.mundo.tryAddBlockFromMouse(camera,this.controlModeMaid.getTypeOfBlock())){
                    this.controlModeMaid.negocio.Presupuesto -= 5;
                }
            }
        }
        this.recCam();
    }

    private void recCam(){
        camera.position.x = this.juego.mundo.getFocusX();
        camera.position.y = this.juego.mundo.getFocusY();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
        staticView.update(width, height);
        staticView.getCamera().position.set(camera.viewportWidth / 2,staticView.getCamera().viewportHeight / 2,0);
        controlModeMaid.resize(camera);
        this.juego.mundo.setPantalla(width,height);
    }

    @Override
    public void pause() {   }

    @Override
    public void resume() {  }

    @Override
    public void hide() {    }

    @Override
    public void dispose() {
        this.controlModeMaid.dispose();
        this.drawer.dispose();
        this.batch.dispose();
        this.uiRenderer.dispose();
        this.font.dispose();
    }
}