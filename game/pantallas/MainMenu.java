package com.mygdx.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Cardinal;
import com.mygdx.game.GoToZero;


public class MainMenu implements Screen {
    private GoToZero game;
    private double[] angles;
    private Stage stage;
    private BitmapFont font;
    private SpriteBatch batchUI;

    public MainMenu(GoToZero g) {
        this.game = g;
        this.angles = new double[3];
        this.font = new BitmapFont();
        this.font.setColor(Color.RED);
        this.font.getData().setScale(1.15f);
        this.batchUI = new SpriteBatch();
        this.stage = new Stage(new ScreenViewport());
        //Cambio de Idioma
        Actor languageButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("data/img/UIelements/px16/esFlag.png")))),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("data/img/UIelements/px16/enFlag.png")))),new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("data/img/UIelements/px16/enFlag.png")))));
        languageButton.addListener(
                new ActorGestureListener() {
                    String language = "es";
                    @Override
                    public void tap (InputEvent event, float x, float y, int pointer, int button) {
                        if(language.equals("es")){
                            this.language = "en";
                        } else {
                            this.language = "es";
                        }
                        Cardinal.setLanguage(this.language);
                    }
                }
        );
        languageButton.setPosition(12,this.stage.getHeight() - 12, Align.topLeft);
        this.stage.addActor(languageButton);

        Actor imageButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("data/img/UIelements/Icon32.png")))));
        imageButton.addListener(
                new ActorGestureListener() {
                    @Override
                    public void tap (InputEvent event, float x, float y, int pointer, int button) {
                        game.initial(2);
                        game.modo = -24;
                        game.setScreen(new World3D(game));
                        dispose();
                    }
                }
        );
        imageButton.setPosition(this.stage.getWidth()/2,this.stage.getHeight()/2, Align.center);
        this.stage.addActor(imageButton);

        TextButton.TextButtonStyle Style = new TextButton.TextButtonStyle();
        Style.font = new BitmapFont();
        Actor ControlConexion = new TextButton("Conexión", Style);
        ControlConexion.addListener(
                new ActorGestureListener() {
                    @Override
                    public void tap (InputEvent event, float x, float y, int pointer, int button) {
                        System.out.println("Iniciando procedimientos de conexión remota");

                        Actor InputCode = new TextButton("Introducir Codigo", Style);
                        InputCode.addListener(
                                new ActorGestureListener() {
                                    @Override
                                    public void tap(InputEvent event, float x, float y, int pointer, int button) {
                                        System.out.println("Yo tambien funciono: CODIGO_" + (InputCode.hashCode()));

                                        TextField.TextFieldStyle Style = new TextField.TextFieldStyle();
                                        Style.font = new BitmapFont();
                                        Actor textBox = new TextField("InputCode", Cardinal.getSkin());

                                        textBox.setPosition(stage.getWidth() - InputCode.getWidth() - 5,stage.getHeight() - 20, Align.topRight);
                                        textBox.setSize(100, 20);
                                        stage.addActor(textBox);
                                        InputCode.removeListener(this);
                                    }});

                        InputCode.setPosition(stage.getWidth() - 5,stage.getHeight() - 20, Align.topRight);
                        stage.addActor(InputCode);
                        ControlConexion.removeListener(this);
                    }
                }
        );
        ControlConexion.setPosition(this.stage.getWidth() - 5,this.stage.getHeight() - 5, Align.topRight);
        this.stage.addActor(ControlConexion);

        Actor M4 = new TextButton(Cardinal.language.getT("mode") + " Aventura de Texto #Experimental#", Cardinal.getSkin());
        M4.addListener(
                new ActorGestureListener() {
                    @Override
                    public void tap(InputEvent event, float x, float y, int pointer, int button) {

                    }
                }
        );

        M4.setPosition(this.stage.getWidth() - 5,95, Align.bottomRight);
        this.stage.addActor(M4);


        Actor M1 = new TextButton("Modo Gestion Maid Caffe", Cardinal.getSkin());
        M1.addListener(
                new ActorGestureListener() {
                    @Override
                    public void tap(InputEvent event, float x, float y, int pointer, int button) {
                        game.initial(1);
                        game.modo = -34;
                        game.setScreen(new MaidCafeView(game));
                        dispose();
                    }
                }
        );
        M1.setWidth(M4.getWidth());
        M1.setPosition(this.stage.getWidth() - 5,5, Align.bottomRight);
        this.stage.addActor(M1);


        Actor M2 = new TextButton("Modo juego 2D Normal", Cardinal.getSkin());
        M2.addListener(
                new ActorGestureListener() {
                    @Override
                    public void tap(InputEvent event, float x, float y, int pointer, int button) {
                        game.initial(2);
                        game.modo = -29;
                        game.setScreen(new Survival(game));
                        dispose();
                    }
                }
        );

        M2.setPosition(this.stage.getWidth() - 5,35, Align.bottomRight);
        this.stage.addActor(M2);

        Actor M3 = new TextButton("Modo Aventura", Cardinal.getSkin());
        M3.addListener(
                new ActorGestureListener() {
                    @Override
                    public void tap(InputEvent event, float x, float y, int pointer, int button) {
                        game.initial(2);
                        game.modo = -24;
                        game.setScreen(new World3D(game));
                        dispose();
                    }
                }
        );
        M3.setPosition(this.stage.getWidth() - 5,65, Align.bottomRight);
        this.stage.addActor(M3);


        Actor M5 =  new TextButton("Modo Desarrollador", Cardinal.getSkin());
        M5.addListener(
                new ActorGestureListener() {
                    @Override
                    public void tap(InputEvent event, float x, float y, int pointer, int button) {
                        game.initial(2);
                        game.modo = -24;
                        game.setScreen(new devScreen(game));
                        dispose();
                    }
                }
        );
        M5.setPosition(16,65, Align.bottomLeft);
        this.stage.addActor(M5);

        Gdx.input.setInputProcessor(stage);
        this.angles[0] = Math.random();
        this.angles[1] = Math.random();
        this.angles[2] = Math.random();
    }



    private void inicializarStage(){
        //Por comodidad y limpieza

    }



    @Override
    public void show() {

    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor((float)Math.sin(this.angles[0]), (float)Math.sin(this.angles[1]), (float)Math.sin(this.angles[2]), 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        batchUI.begin();
        GoToZero.options.printOptions(batchUI,font);
        batchUI.end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
            this.game.initial(1);
            this.game.modo = -34;
            game.setScreen(new MaidCafeView(this.game));
            this.dispose();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)){
            this.game.initial(2);
            this.game.modo = -29;
            game.setScreen(new Survival(this.game));
            this.dispose();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)){
            this.game.initial(2);
            this.game.modo = -24;
            game.setScreen(new World3D(this.game));
            this.dispose();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)){ //Modo secreto
            this.game.initial(2);
            this.game.modo = -24;
            game.setScreen(new devScreen(this.game));
            this.dispose();
        }

        this.angles[0] += Math.random()/100;
        this.angles[1] += Math.random()/100;
        this.angles[2] += Math.random()/100;
    }
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        this.stage.dispose();
    }
}
