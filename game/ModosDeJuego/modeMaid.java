package com.mygdx.game.ModosDeJuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.simulation.Negocio;

public class modeMaid {
    Image buyCub;
    Image iconConstruccion;
    Image iconConstruccionSel;
    Image contador;


    BitmapFont font;
    public ImageButton botonConstruccion;

    private byte[] selectContructor;
    boolean modoConstruccion;
    boolean modoContratacion;
    boolean modoPlantilla;

    public Negocio negocio;
    private Texture[] Elementos;

    public modeMaid(){
        this.negocio = new Negocio();
        //Altura,modelo(es decir tipo), rotacion
        //Tipo de cambio seleccionado, modelo del cambio, rotacion
        this.selectContructor = new byte[]{1,10,0};

        Texture modConstruccion = new Texture(Gdx.files.internal("data/img/buyCub.png"));
        this.buyCub = new Image(modConstruccion);

        this.iconConstruccion = new Image(new Texture(Gdx.files.internal("data/img/UIelements/modoConstruccion.png")));
        this.iconConstruccionSel = new Image(new Texture(Gdx.files.internal("data/img/UIelements/modoConstruccionSel.png")));
        this.iconConstruccion.setPosition(0,416);
        this.iconConstruccionSel.setPosition(0,416);

        this.contador = new Image(new Texture(Gdx.files.internal("data/img/UIelements/contadorMonedas.png")));
        this.font = new BitmapFont();
        this.font.setColor(Color.WHITE);
        this.font.getData().setScale(1.15f);


        this.botonConstruccion = new ImageButton(iconConstruccion.getDrawable(),this.iconConstruccion.getDrawable(),this.iconConstruccionSel.getDrawable());
        this.botonConstruccion.setPosition(0,416);
        this.botonConstruccion.setSize(64,64);

        this.botonConstruccion.validate();

        this.modoConstruccion = false;
        this.modoContratacion = false;
        this.modoPlantilla = false;
    }

    public void draw(SpriteBatch batch, Camera cam) {
        if(modoPlantilla) {
            batch.begin();
            batch.draw(this.Elementos[0],0,0);
            batch.end();
        }

        batch.begin();
        if(modoConstruccion) {
            this.iconConstruccionSel.draw(batch,1);
            this.contador.setPosition( cam.viewportWidth - 128,cam.viewportHeight - 26);
            this.contador.draw(batch,1);
            this.font.draw(batch,"" + this.negocio.Presupuesto,cam.viewportWidth - 122,cam.viewportHeight - 6);
        } else {
            this.iconConstruccion.draw(batch,1);
        }
        batch.end();
    }

    public void changeModoConstruction() { this.modoConstruccion = !this.modoConstruccion; }
    public boolean isModoConstruccion() { return modoConstruccion; }
    public void changeModoContratacion() { this.modoContratacion = !this.modoContratacion; }
    public boolean isModoContratacion() { return modoContratacion; }


    public void resize(Camera cam) {
        this.iconConstruccion.setPosition(0,cam.viewportHeight - 64);
        this.iconConstruccionSel.setPosition(0,cam.viewportHeight - 64);
    }

    public void changeModoPlantilla() { this.modoPlantilla = !this.modoPlantilla;
        if(this.modoPlantilla) {
            this.Elementos = this.negocio.getEmpleadosFace();
        } else {
            this.Elementos = null;
        }

    }

    public boolean isModoPlantilla() { return modoPlantilla; }

    class ButtonListener extends ClickListener {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            changeModoConstruction();
        }
    }


    public byte getTypeOfBlock(){
        return this.selectContructor[1];
    }

    public void dispose() {
        this.font.dispose();
    }
}