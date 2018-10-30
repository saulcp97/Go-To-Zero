package com.mygdx.game.ModosDeJuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Chunk;
import com.mygdx.game.Cubos.Entidades.vida.Avatar;
import com.mygdx.game.gameItems.Equipable;
import com.mygdx.game.gameItems.Items;

import java.util.ArrayList;

public class modeSurvival {

    private BitmapFont font;
    private final Texture
            fond = new Texture(Gdx.files.internal("data/img/UIelements/dialogBox.png")),
            liveRed = new Texture(Gdx.files.internal("data/img/UIelements/liveYes.png")),
            closeTexture = new Texture(Gdx.files.internal("data/img/UIelements/px16/close.png"));

    private Avatar player;
    private int Puntero;
    private boolean modoInventario, modoPersonaje, modoMapa, gameMenu;
    private Chunk[][] toMap;

    private Stage pantallaInventario,pantallaPersonaje, menuSalida;
    private ArrayList<Page> Paginas;

    public modeSurvival() {
        this.font = new BitmapFont();
        this.font.setColor(Color.RED);
        this.font.getData().setScale(1.15f);
        this.Puntero = 0;
        this.modoInventario = false;
        this.modoPersonaje = false;
        this.modoMapa = false;
        this.gameMenu = false;

        this.menuSalida = new Stage(new ScreenViewport());
        this.pantallaPersonaje = new Stage(new ScreenViewport());
        this.pantallaInventario = new Stage(new ScreenViewport());

        Actor imageButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("data/img/historyCat.png")))));
        imageButton.addListener(
                new ActorGestureListener() {
                    @Override
                    public void tap (InputEvent event, float x, float y, int pointer, int button) {
                        System.out.println("He sido pulsado!");
                    }
                }
        );
        imageButton.setPosition(this.pantallaInventario.getWidth()/2,this.pantallaInventario.getHeight()/2, Align.center);
        this.pantallaInventario.addActor(imageButton);
        Gdx.input.setInputProcessor(pantallaInventario);

        imageButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("data/img/historyCat.png")))));
        imageButton.addListener(
                new ActorGestureListener() {
                    @Override
                    public void tap (InputEvent event, float x, float y, int pointer, int button) {
                        System.out.println("Sesión finalizada");
                        Gdx.app.exit();
                    }
                }
        );
        imageButton.setPosition(0,this.pantallaInventario.getHeight(), Align.topLeft);
        this.menuSalida.addActor(imageButton);


        System.out.println("Nº de Procesadores disponibles: " + Runtime.getRuntime().availableProcessors());
        //USAR el mismo NUMERO DE THREADS!!

        this.Paginas = new ArrayList<Page>();
    }

    public void manageInventario() {
        this.player.getInventario().Click(true);
    }

    public void modeInventory() {
        if ((!this.player.ConversMSG.equals("") && !this.modoInventario) || this.player.ConversMSG.equals("")) {
            this.modoInventario = !this.modoInventario;
        }
    }

    public void modePersonaje() {
        if ((!this.player.ConversMSG.equals("") && !this.modoPersonaje) || this.player.ConversMSG.equals("")) {
            this.modoPersonaje = !this.modoPersonaje;
            this.player.freeMove = !this.modoPersonaje;
        }
    }

    public void modeMap() {
        if ((!this.player.ConversMSG.equals("") && !this.modoMapa) || this.player.ConversMSG.equals("")) {
            this.modoMapa = !this.modoMapa;
        }
    }

    public boolean isModoInventario(){
        return modoInventario;
    }

    public void draw(SpriteBatch batch, Camera cam) {
        //Vida
        if(this.modoInventario){
            this.player.getInventario().drawContainer(batch);
            this.pantallaInventario.draw();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            this.gameMenu = !this.gameMenu;
            if (this.gameMenu) {
                Gdx.input.setInputProcessor(this.menuSalida);
            } else {
                Gdx.input.setInputProcessor(this.pantallaInventario);
            }
        }

        if(this.modoPersonaje){
            this.player.drawEquip(batch);
        }


        if(this.modoMapa && this.toMap != null) {
            batch.begin();
            for (Chunk[] aToMap : this.toMap) {
                for (int j = 0; j < this.toMap.length; ++j) {
                    aToMap[j].drawMap(0, 0, 1, batch);
                }
            }
            batch.end();
        }

        batch.begin();
        for (int i = 0; i < 100; ++i) {
            batch.draw(liveRed, i * liveRed.getWidth(),cam.viewportHeight - liveRed.getHeight());
            if(i < 50) {
                batch.draw(liveRed, i * liveRed.getWidth(),cam.viewportHeight - 2 * liveRed.getHeight()); //Blue para algo como mana
            }
        }
        batch.end();

        if(this.player.getInventario().detalles != null) {
            this.addPage(this.player.getInventario().detalles);
            this.player.getInventario().detalles = null;
        }

        if(this.player.getInventario().Use != null && this.player.getInventario().useCode == 1) {
            //Se deberia tirar al suelo si no se puede...
            this.player.getInventario().canAdd(this.player.equipar((Equipable)this.player.getInventario().Use),1);
            this.player.getInventario().Use = null;
            this.player.getInventario().useCode = 0;
        }

        Page p;
        for (int i = 0; i < this.Paginas.size(); ++i){
            p = this.Paginas.get(i);
            if(p.close){
                p.toRemove();
                System.out.println("Borrado ");
                this.player.getInventario().removeDetalles();
                this.Paginas.remove(i);
            } else {
                p.render(batch, cam);
            }
        }

        //Texto Propio
        if (!this.player.ConversMSG.equals("")) {
            if (this.player.options != null) {
                batch.begin();
                for (int i = 0; i < this.player.options.length; ++i) {
                    if (this.Puntero == i + 1) {
                        batch.draw(fond, 0, cam.viewportHeight - 16 * i - 16, cam.viewportWidth, 16);
                    }
                    font.draw(batch, (i + 1) + ") " + this.player.options[i], 8, cam.viewportHeight - 16 * i);
                }
                //font.draw(batch, "" + this.Puntero, 0, cam.viewportHeight / 2);
                batch.end();

                if (this.player.Option == -1) {
                    if (this.Puntero > 1 && Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                        --this.Puntero;
                    } else if (this.Puntero < this.player.options.length && Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                        ++this.Puntero;
                    } else if (this.Puntero != 0 && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                        this.player.Option = (byte) (this.Puntero - 1);
                        this.Puntero = 0;
                        this.player.Mark = true;
                    }
                }
            }
            //Texto de Otros
            batch.begin();
            batch.draw(fond, 0, 0);
            batch.draw(this.player.TalkWith.getFaceSet(),cam.viewportWidth - this.player.TalkWith.getFaceSet().getWidth(),8);
            font.draw(batch, this.player.ConversMSG, 0, 64);
            batch.end();
        }

        if(this.gameMenu) {
            this.menuSalida.draw();
        }

    }
    public void setPlayer(Avatar player) {  this.player = player; }
    public void setToMap(Chunk[][] toMap) {   this.toMap = toMap; }
    public void addPage(Items it) {this.Paginas.add(new Page(it, this.Paginas.size())); }
    public Stage getPantallaInventario() { return pantallaInventario; }
    public Stage getPantallaPersonaje() { return pantallaPersonaje; }

    private class Page {
        private Vector2 position, size;
        private Items data;
        private final ActorGestureListener closeListener;
        public boolean close;
        private Actor closeButton, dragbar;

        public Page (Items data, int i) {
            this.position = new Vector2(64 + 16 * i,64 + 16 * i);
            this.size = new Vector2(256,272);
            this.close = false;
            this.data = data;
            this.closeListener = new ActorGestureListener() {
                @Override
                public void tap (InputEvent event, float x, float y, int pointer, int button) {
                    System.out.println("He sido pulsado!");
                    close = true;
                    toRemove();
                }
            };

            this.dragbar = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("data/img/UIelements/fondoMuro.png")))));
            this.dragbar.setPosition(this.position.x,this.position.y);

            this.closeButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(closeTexture)));
            this.closeButton.addListener(this.closeListener);
            this.closeButton.setPosition(this.position.x + this.size.x,this.position.y + this.size.y, Align.topRight);

            pantallaInventario.addActor(this.dragbar);
            pantallaInventario.addActor(this.closeButton);
        }

        public void toRemove() {
            this.closeButton.removeListener(this.closeListener);
            this.closeButton.remove();
            this.dragbar.remove();
        }

        public void render (SpriteBatch batch, Camera cam) {
            batch.begin();
            batch.draw(data.getTexture(),this.position.x + this.size.x / 8, this.position.y + this.size.y - 16 - data.getTexture().getHeight() - this.size.x / 8);
            font.draw(batch, data.getName(), this.position.x + this.size.x / 8, this.position.y + this.size.y - 24 - data.getTexture().getHeight() - this.size.x / 8);
            font.draw(batch, data.getDescripcion(), this.position.x + this.size.x / 8 + 8 + data.getTexture().getWidth(), this.position.y + this.size.y - 22);

            //font.draw(batch, "" + this.Puntero, 0, cam.viewportHeight / 2);
            batch.end();
        }
    }
}