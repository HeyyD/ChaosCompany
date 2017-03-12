package com.mygdx.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tile {

    private float x;
    private float y;
    private boolean isFull = false;
    private TextureRegion texture;

    public Tile(TextureRegion texture){
        this.texture = texture;
    }

    public void draw(SpriteBatch batch, float x, float y, float width, float height){
        batch.draw(texture, x, y, width, height);
    }

    public boolean getIsFull(){
        return isFull;
    }

    public void setIsFull(boolean isFull){
        this.isFull = isFull;
    }

    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }
}
