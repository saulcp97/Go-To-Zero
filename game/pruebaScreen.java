package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class pruebaScreen implements Screen {
    SpriteBatch batch;
    Sprite aspectRatios;
    OrthographicCamera camera;
    Viewport viewport;

    public pruebaScreen() {
        this.create();
    }

    public void create () {
        batch = new SpriteBatch();
        aspectRatios = new Sprite(new Texture(Gdx.files.internal("Aspect.jpg")));
        aspectRatios.setPosition(0,0);
        aspectRatios.setSize(100,100);

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(100,100,camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
    }


    public void render () {

    }

    @Override
    public void dispose(){
        aspectRatios.getTexture().dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        aspectRatios.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width,height);
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
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
}