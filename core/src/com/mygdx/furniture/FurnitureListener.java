package com.mygdx.furniture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.ChaosCompany;

/**
 * Created by SamiH on 9.3.2017.
 */

public class FurnitureListener extends InputListener {

    private ChaosCompany        game;
    private Furniture           furniture;

    public FurnitureListener(ChaosCompany g, Furniture furniture){
            game = g;
            this.furniture = furniture;
    }

    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        return true;
    }

    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
        if(x > 0 && x < 40 && y > 0 && y < 40 && furniture.getButtons().isButtonsOpen() == false) {
            game.getOfficeState().removeButtons();
            furniture.getButtons().create();
            game.getOfficeState().setButtons(furniture.getButtons());
        }
    }
}