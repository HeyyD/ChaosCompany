package com.mygdx.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Mini icons in Build menu is made with this class and MiniIcons class combined.
 * Created by SamiH on 6.5.2017.
 */

public class MiniIco extends Actor {

    Texture         texture;

    public MiniIco(Texture texture, float x, float y){
        this.texture = texture;
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture, getX(), getY(), 0.2f, 0.2f);
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }

}
