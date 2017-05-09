package com.mygdx.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Tile class is a single tile in the game. The office and job center are built using tiles
 */

public class Tile {

    /**g variable used in a star*/
    public int gCost;
    /**heuristic variable used in a star*/
    public int hCost;

    /**X-coordinate in the tile map*/
    public int mapX;
    /**Y-coordinate in the tile map*/
    public int mapY;

    /**Parent tile of this tile. This is used to calculate paths.*/
    public Tile parent = null;

    /**X-coordinate of the tile on screen*/
    private float x;
    /**Y-coordinate of the tile on screen*/
    private float y;
    /**Tells if there is something on the tile, for example a furniture or an employee*/
    private boolean isFull = false;
    /**Texture of the tile*/
    private TextureRegion texture;

    /**
     *
     * @param texture The texture of the tile
     * @param mapX X-coordinate where the tile is drawn
     * @param mapY X-coordinate where the tile is drawn
     */

    public Tile(TextureRegion texture, int mapX, int mapY){
        this.texture = texture;
        this.mapX = mapX;
        this.mapY = mapY;
    }

    public void draw(SpriteBatch batch, float x, float y, float width, float height){
        batch.draw(texture, x, y, width, height);
    }

    /**
     *
     * @return The sum of gCost and hCost
     */
    public int fCost() {
        return gCost + hCost;
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
