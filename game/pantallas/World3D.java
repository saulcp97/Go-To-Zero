package com.mygdx.game.pantallas;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Cubos.Entidades.OrbitCam;
import com.mygdx.game.Cubos.Entidades.vida.Avatar;
import com.mygdx.game.GoToZero;
import com.mygdx.game.ModosDeJuego.modeSurvival;
import com.mygdx.game.Shaders.worldBatch;

public class World3D implements Screen {
    private GoToZero game;
    private ModelBatch batch;
    PerspectiveCamera camera;

    private int camAngle;
    private SpriteBatch batchUI;
    private Avatar pl;
    private Environment environment;
    private final float conv = MathUtils.degreesToRadians;
    private modeSurvival mSurvival;
    private ModelInstance p1;
    private PointLight po;
    //private ShaderProgram program;
    private OrbitCam cami;
    private Vector3 deformationCam;

    //private worldBatch wBatch;
    private boolean h = false;

    public World3D(GoToZero gm) {
        //Block3D.setModel();
        this.game = gm;
        this.pl = this.game.mundo.getPlayer();

        //Hay que añadir funciones chulas de Camara
        this.camera = new PerspectiveCamera(60, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.near = 0.01f;
        this.camera.far = 64f;

        this.camera.update();

        this.mSurvival = new modeSurvival();
        this.mSurvival.setPlayer(this.game.mundo.getPlayer());
        this.mSurvival.setToMap(this.game.mundo.getChunks());

        //read the files into strings
        String VERTEX = Gdx.files.internal("data/shader/vertex.glsl").readString();
        String FRAGMENT = Gdx.files.internal("data/shader/fragment.glsl").readString();

        this.batch = new ModelBatch(VERTEX,FRAGMENT);
        this.batchUI = new SpriteBatch();

        this.camAngle = 0;
        this.environment = new Environment();

        this.environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        this.environment.set(new ColorAttribute(ColorAttribute.Fog, 0.66f, 0.66f, 0.6f, 1f));
        this.environment.add(new DirectionalLight().set(Color.WHITE, -1f, -0.8f, -0.2f));

        ModelLoader loader = new ObjLoader();
        Model Plane = loader.loadModel(Gdx.files.internal("data/img/3DModels/accel-world-blacklotus/source/lotus.obj"));

        this.p1 = new ModelInstance(Plane);
        p1.materials.get(0).set(ColorAttribute.createDiffuse(Color.PURPLE));
        p1.materials.get(1).set(ColorAttribute.createDiffuse(Color.DARK_GRAY));
        p1.materials.get(0).set(new BlendingAttribute(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA));

        float altura  = 1f/2;
        p1.transform.translate(33, altura + 1,65);

        this.deformationCam = new Vector3();

        this.cami = new OrbitCam(camera, this.game.mundo.getSize(), this.pl.coord);

        worldBatch.setEnvironment(this.environment);


        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(
                new InputAdapter() {
                    @Override
                    public boolean scrolled (int amount) {
                        switch (amount){
                            case 1:
                                cami.Zoom(.1f);
                                break;
                            case -1:
                                cami.Zoom(-.1f);
                                break;
                        }
                        return true;
                    }
                }
        );
        multiplexer.addProcessor(mSurvival.getPantallaInventario());
        multiplexer.addProcessor(mSurvival.getPantallaPersonaje());
        Gdx.input.setInputProcessor(multiplexer);


    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        this.pl.adjustAngle();
        this.cami.Tick(this.game.mundo.getPunteroX(), this.game.mundo.getPunteroY());

        camera.update();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(0.4f, 0.59f, 0.95f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL30.GL_COVERAGE_BUFFER_BIT_NV:0));


        /*
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(0.4f, 0.59f, 0.95f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL30.GL_COVERAGE_BUFFER_BIT_NV:0));
        Gdx.gl.glClearDepthf(1f);
        Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glDepthFunc(GL20.GL_LESS);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glDepthMask(true);
        Gdx.gl.glColorMask(false, false, false, false);
    */



        worldBatch.begin(camera);



        this.game.mundo.draw3DWorld(this.camera);


        worldBatch.end();

        this.batch.begin(camera);
        this.batch.render(p1,this.environment);
        this.batch.end();
        this.mSurvival.draw(batchUI, this.camera);

        Gdx.gl.glDisable(GL20.GL_STENCIL_TEST);

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            this.pl.incrAngle(1);
            this.cami.angleHor(1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            this.pl.incrAngle(-1);
            this.cami.angleHor(-1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.game.mundo.addComand(6);
            if(this.cami.getAnglH() != this.pl.getAngle()) {
                this.pl.setAngle(this.cami.getAnglH());
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {//Se puede mover de lado solo antes de W/S
            this.game.mundo.addComand(1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.game.mundo.addComand(7);
            if(this.cami.getAnglH() != this.pl.getAngle()) {
                this.pl.setAngle(this.cami.getAnglH());
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.game.mundo.addComand(3);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            this.game.mundo.addComand(4);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.PAGE_UP)) {
            if(this.camAngle < 75) { this.camAngle += 1; this.cami.angleVer(1);}
        }
        if(Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN)) {
            if(this.camAngle > -75) { this.camAngle -= 1; this.cami.angleVer(-1);}
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.I) ){//INVENTARIO
            this.mSurvival.modeInventory();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.P) ){//Personaje
            this.mSurvival.modePersonaje();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.M) ){//Mapa
            this.mSurvival.modeMap();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.T) ){
            this.game.mundo.addComand(116);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            this.game.mundo.addComand(5);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            this.game.mundo.addComand(10);
        }

        if(!this.mSurvival.isModoInventario()) {
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                this.game.mundo.addComand(5);
            }
        } else {
            this.mSurvival.manageInventario();
        }

        this.game.mundo.tick();
        Gdx.graphics.setTitle("Go To Zero; FPS : " + Gdx.graphics.getFramesPerSecond());
    }

    @Override
    public void resize(int width, int height) {

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
        this.batch.dispose();
    }
}