package com.mygdx.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Shows IncomeUI texture in right corner of the screen
 * Created by SamiH on 5.5.2017.
 */

public class IncomeUI extends Actor {

    /**
     * Texture
     */
    private Texture texture;

    public IncomeUI(){
        texture = new Texture("UI_income.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        setPosition(8.7f, 4.75f);
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture, getX(), getY(), 1.2f, 0.6f);
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }
}
