package com.mygdx.game.ModosDeJuego;

import com.badlogic.gdx.Input.TextInputListener;

public class Input implements TextInputListener {

    public Input(){
        super();
    }

    @Override
    public void input (String text) {
    }

    @Override
    public void canceled () {
    }

    /*

     Gdx.input.getTextInput(new Input.TextInputListener() {
            @Override
            public void input (String text) {
                message = "message: " + text + ", touch screen for new dialog";
            }

            @Override
            public void canceled () {
                message = "cancled by user";
            }
        }, "Dialog Title", "Initial Textfield Value", "Hint Value");
     */
}