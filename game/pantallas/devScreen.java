package com.mygdx.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Cardinal;
import com.mygdx.game.Cubos.Entidades.vida.Avatar;
import com.mygdx.game.GoToZero;
import com.mygdx.game.ModosDeJuego.modeSurvival;
import com.mygdx.game.Shaders.worldBatch;

public class devScreen implements Screen {
    private GoToZero game;
    private worldBatch batch;
    PerspectiveCamera camera;

    private int camHigh;
    private SpriteBatch batchUI;
    private Avatar pl;
    private Environment environment;
    private final float conv = (float)(Math.PI/180);
    private modeSurvival mSurvival;
    private ModelInstance p1;
    private float dist = (16 + 32);

    private int OptionTest = -1;
    private boolean UI = false;
    private int z = 1;

    private Stage stage;

    public devScreen(GoToZero gm) {
        this.game = gm;
        this.pl = this.game.mundo.getPlayer();

        this.camera = new PerspectiveCamera(70, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.near = 0.01f;
        this.camera.far = 3200f;

        this.camera.update();

        this.mSurvival = new modeSurvival();
        this.mSurvival.setPlayer(this.game.mundo.getPlayer());
        this.mSurvival.setToMap(this.game.mundo.getChunks());

        String VERTEX = Gdx.files.internal("data/shader/vertex.glsl").readString();
        String FRAGMENT = Gdx.files.internal("data/shader/fragment.glsl").readString();

        this.batch = new worldBatch(VERTEX,FRAGMENT);
        this.batchUI = new SpriteBatch();
        this.stage = new Stage(new ScreenViewport());



        this.camHigh = 16;
        this.environment = new Environment();
        this.environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        this.environment.add(new DirectionalLight().set(Color.WHITE, -1f, -0.8f, -0.2f));

        this.game.mundo.setAhorro(false);
        this.game.mundo.resetChunk(1,1, 3);
        //Configurar color del cielo
        Gdx.gl.glClearColor(0.4f, 0.59f, 0.95f, 1);

        Actor openOptions = new TextButton("Opciones \bv", Cardinal.getSkin());

        openOptions.setPosition(this.stage.getWidth(), this.stage.getHeight(), Align.topRight);
        this.stage.addActor(openOptions);
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        this.camera.position.set(
                16 + 32 + this.dist * (float)Math.cos(this.pl.getAngle()*conv),
                this.camHigh,
                16 + 32 + this.dist * (float)Math.sin(this.pl.getAngle()*conv)
        );
        this.camera.lookAt(16 + 32,16,16 + 32);

        camera.update();
        //Color del cielo si tiene


        this.stage.act();
        this.stage.draw();

        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL30.GL_COVERAGE_BUFFER_BIT_NV:0));

        worldBatch.begin(camera);
        this.game.mundo.draw3DWorld(this.camera);
        worldBatch.end();

        //this.batch.begin(camera);
        //this.batch.render(p1,this.environment);
        //this.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) { this.pl.incrAngle(1); }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) { this.pl.incrAngle(-1); }

        if(Gdx.input.isKeyPressed(Input.Keys.PAGE_UP)) {
            if(this.camHigh < 48) { this.camHigh += 1; }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN)) {
            if (this.camHigh > 0) { this.camHigh -= 1; }
        }

        this.game.mundo.tick();
        Gdx.graphics.setTitle("Go To Zero; FPS : " + Gdx.graphics.getFramesPerSecond());
    }


    private void reformat() {
        this.stage = new Stage(this.stage.getViewport());
        switch (this.OptionTest) {
            case 1:

                break;
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        System.out.println("Tamaño camara w: " + camera.viewportWidth + ", h: " + camera.viewportHeight);
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