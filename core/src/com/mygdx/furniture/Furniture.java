package com.mygdx.furniture;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.map.Tile;

/**
 * Created by SamiH on 4.3.2017.
 */

public abstract class Furniture extends Actor{

    /**
     * Price of furniture
     */
    private int price = 0;
    /**
     * Sell price of furniture
     */
    private int sellPrice = 0;
    /**
     * Transparency of furniture, 0.5 when furniture is not yet bought
     */
    private float alpha = 0.5f;
    /**
     * Textures of furniture
     */
    private Texture sheet;
    /**
     * Is furniture bought or not
     */
    private boolean bought = false;
    /**
     * Is player moving furniture or not
     */
    private boolean isMoving = false;
    /**
     * Tile furniture is in
     */
    private Tile tile;

    /**
     * Buttons that appears around furniture when it is being bough or moved
     */
    private FurnitureButtons    buttons;

    /**
     * check if computer is available or not
     */
    private boolean isAvailable = true;

    /**
     * Furnitures can be sold
     */
    public abstract void sell();

    /**
     * Furnitures can be bought.
     */
    public abstract void buy();

    abstract void rotate();


    public void setPrice(int price){
        this.price = price;
    }

    public int getPrice(){
        return price;
    }

    public void setSellPrice(int sellPrice){
        this.sellPrice = sellPrice;
    }

    public int getSellPrice(){
        return sellPrice;
    }

    public Texture getSheet(){
        return sheet;
    }
    public void setSheet(Texture t){
        this.sheet = t;
    }
    public boolean getBought(){
        return bought;
    }

    public void setBought(boolean bought){
        this.bought = bought;
    }

    public float getAlpha(){
        return alpha;
    }
    public void setAlpha(float alpha){
        this.alpha = alpha;
    }
    public boolean getIsMoving(){
        return isMoving;
    }
    public void setIsMoving(boolean isMoving){
        this.isMoving = isMoving;
    }

    public FurnitureButtons getButtons(){
        return buttons;
    }

    public void setButtons(FurnitureButtons buttons){
        this.buttons = buttons;
    }
    public void setTile(Tile tile){
        this.tile = tile;
    }
    public Tile getTile(){
        return tile;
    }

    public boolean getIsAvailabel(){
        return this.isAvailable;
    }

    public void setIsAvailable(boolean isAvailable){
        this.isAvailable = isAvailable;
    }
}
