package com.mygdx.game.simulation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;

import java.io.IOException;

public class Language {
    private XmlReader.Element mapa;

    public Language(String code) {
        XmlReader reader = new XmlReader();
        XmlReader.Element root;
        if(Gdx.files.internal("data/text/language.xml").exists()) {
            try {
                root = reader.parse(Gdx.files.internal("data/text/language.xml"));
                this.mapa = root.getChildByName(code);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File Not Exists");
        }
    }

    public String getT(String code) {
        return mapa.getChildByName(code).getText();
    }

}
