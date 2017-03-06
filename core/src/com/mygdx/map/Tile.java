package com.mygdx.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tile {

    private boolean isFull = false;
    private TextureRegion texture;

    public Tile(TextureRegion texture){
        this.texture = texture;
    }

    public void draw(SpriteBatch batch, float x, float y, float width, float height){
        batch.draw(texture, x, y, width, height);
    }

}
