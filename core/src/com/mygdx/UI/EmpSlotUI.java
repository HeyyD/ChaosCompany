package com.mygdx.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by SamiH on 3.5.2017.
 */

public class EmpSlotUI extends Actor {

    private Texture texture;

    public EmpSlotUI(){
        texture = new Texture("UI_EmpSlot.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        setPosition(8.4f, 4.1f);
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture, getX(), getY(), 1.5f, 0.75f);
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }
}
