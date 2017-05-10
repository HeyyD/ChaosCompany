package com.mygdx.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Shows moneyUI in top right corner of the game
 * Created by SamiH on 26.3.2017.
 */

public class MoneyUi extends Actor{

    /**
     * texture
     */
    private Texture texture;

    public MoneyUi(){
        texture = new Texture("UI_Money.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        setPosition(7.9f,5.2f);
    }

    @Override
    public void draw(Batch batch,float alpha){
        batch.draw(texture, getX(), getY(), 2, 0.68f);
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }
}