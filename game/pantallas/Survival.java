package com.mygdx.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GoToZero;
import com.mygdx.game.ModosDeJuego.modeSurvival;

public class Survival implements Screen {
    private GoToZero game;
    private Camera camera;
    private SpriteBatch batch;
    private ScreenViewport viewport;
    private BitmapFont font;
    private SpriteBatch batchUI;
    private Viewport staticView;

    private boolean db;
    private modeSurvival mSurvival;

    public Survival(GoToZero gm) {
        this.game = gm;

        this.camera = new OrthographicCamera();
        //camera.update();
        this.viewport = new ScreenViewport(camera);
        this.viewport.apply();

        this.batch = new SpriteBatch();

        this.staticView =  new ExtendViewport(1366, 768);
        this.staticView.apply();

        this.batchUI = new SpriteBatch();

        this.font = new BitmapFont();
        this.font.setColor(Color.CYAN);
        this.db = false;
        this.mSurvival = new modeSurvival();
        this.mSurvival.setPlayer(this.game.mundo.getPlayer());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        Gdx.gl.glClearColor(0.5f, 0.2f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        //batchUI.setProjectionMatrix(camera.combined);
        batch.begin();
        this.game.mundo.drawWorld(batch);
        batch.end();

        this.mSurvival.draw(batchUI, this.camera);
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.game.mundo.addComand(0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.game.mundo.addComand(1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.game.mundo.addComand(2);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.game.mundo.addComand(3);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.I) ){//INVENTARIO
            this.mSurvival.modeInventory();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.P) ){//Personaje
            this.mSurvival.modePersonaje();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.T) ){
            this.game.mundo.addComand(116);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            this.game.mundo.addComand(4);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            this.game.mundo.addComand(5);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
            this.db = !this.db;
            this.game.mundo.resetDB();
        }

        this.game.mundo.tick();
        if(this.db){
            this.batchUI.begin();
            this.font.draw(this.batchUI, this.game.mundo.message,16,this.camera.viewportHeight - 16);
            this.batchUI.end();
        }
        this.recCam();
    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height);
        this.staticView.update(width, height);

        //this.recCam();
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        System.out.println("Tamaño camara w: " + camera.viewportWidth + ", h: " + camera.viewportHeight);
    }

    private void recCam() {
        camera.position.x = this.game.mundo.getFocusX();
        camera.position.y = this.game.mundo.getFocusY();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}