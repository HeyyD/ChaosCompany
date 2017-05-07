package com.mygdx.furniture;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.ChaosCompany;
import com.mygdx.game.StatsManager;

/**
 * Created by SamiH on 5.3.2017.
 */

public class CoffeeMachine extends ProgrammingFurniture {

    private StatsManager        manager;
    private ChaosCompany        game;
    private FurnitureListener   listener;
    private TextureRegion[][]   tmp;
    private TextureRegion[]     img;
    private Texture             tex;

    private int                 dir = 0;

    public CoffeeMachine(ChaosCompany g, float x, float y){
        game = g;
        manager = game.getManager();
        setProgrammingPower(600);

        //Setup Textures
        tex = new Texture("coffeemachine.png");
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
        setPrice(8000);
        setSellPrice(4000);

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

    @Override
    public void sell() {
        //minus benfits
        manager.setProgrammingPower(manager.getProgrammingPower()- getProgrammingPower());
        System.out.println(manager.getWellBeing());
        //add money
        manager.setMoney(manager.getMoney() + getSellPrice());
        //Destroy couch
        remove();
        setBought(false);
    }

    @Override
    public void buy(){
        manager.setProgrammingPower((manager.getProgrammingPower() + getProgrammingPower()));
        manager.setMoney(manager.getMoney() - getPrice());
        setAlpha(1);
        setBought(true);
    }

    @Override
    void rotate() {
        dir += 1;
        if(dir >3)
            dir = 0;
    }
}