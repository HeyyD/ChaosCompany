package com.mygdx.furniture;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.chaoscompany.ChaosCompany;
import com.mygdx.chaoscompany.StatsManager;

/**
 * All furnitures looks almost exactly like this one.
 * Created by SamiH on 5.5.2017.
 */

public class ArcadeMachine extends WellBeingFurniture {
    /**
     * Manager of the game
     */
    private StatsManager manager;
    /**
     * Game
     */
    private ChaosCompany game;
    /**
     * What happens when Furniture is clicked
     */
    private FurnitureListener   listener;
    /**
     * Texture sheet is stored here temporally
     */
    private TextureRegion[][]   tmp;
    /**
     * Image splitted in array
     */
    private TextureRegion[]     img;
    /**
     * Texture of furniture
     */
    private Texture             tex;

    /**
     * Direction of furniture, called in rotate
     */
    private int                 dir = 0;

    /**
     * Constructor
     * @param g Game
     * @param x Coordinate x
     * @param y Coordinate y
     */
    public ArcadeMachine(ChaosCompany g, float x, float y){
        game = g;
        manager = game.getManager();
        setWellBeing(2500);

        //Setup Textures
        tex = new Texture("arcademachine.png");
        tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        setSheet(tex);
        tmp = TextureRegion.split(getSheet(),
                getSheet().getWidth() / 2,
                getSheet().getHeight() / 2);

        img = new TextureRegion[2*2];
        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                img[index++] = tmp[i][j];
            }
        }

        //Set position, price and sellPrice
        setX(x);
        setY(y);
        System.out.println(getX()+getY());
        setPrice(32000);
        setSellPrice(16000);

        //SetBounds
        setBounds(getX(),getY(),1,1);


        setButtons(new FurnitureButtons(game, this));
        //Add listener
        listener = new FurnitureListener(game, this);
        this.addListener(listener);
    }

    @Override
    public void draw(Batch batch, float alfa) {
        Color c = batch.getColor();
        batch.setColor(c.r, c.g, c.b, getAlpha());
        batch.draw(img[dir], getX(), getY(), 1f, 1f);
        batch.setColor(c.r, c.g, c.b, 1);
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }

    /**
     * Called when furniture is sold
     */
    @Override
    public void sell() {
        //minus benfits
        manager.setWellBeing(manager.getWellBeing()- getWellBeing());
        System.out.println(manager.getWellBeing());
        //add money
        manager.setMoney(manager.getMoney() + getSellPrice());
        //Destroy couch
        remove();
        setBought(false);
    }

    /**
     * Called when furniture is bought
     */
    @Override
    public void buy(){
        manager.setWellBeing((manager.getWellBeing() + getWellBeing()));
        manager.setMoney(manager.getMoney() - getPrice());
        setAlpha(1);
        setBought(true);
    }

    /**
     * Called when Furniture is rotated
     */
    @Override
    void rotate() {
        dir += 1;
        if(dir >3)
            dir = 0;
    }

}
