package com.mygdx.furniture;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.ChaosCompany;
import com.mygdx.game.StatsManager;

/**
 * Created by SamiH on 5.3.2017.
 */

public class Couch extends Furniture {

    private StatsManager        manager;
    private ChaosCompany        game;
    private FurnitureListener   listener;
    private FurnitureButtons    buttons;
    private TextureRegion[][]   tmp;
    private TextureRegion[]     img;

    private int                 welfare = 10;
    private int                 dir = 0;

    public Couch(ChaosCompany g, float x, float y){
        game = g;
        manager = game.getManager();
        buttons = new FurnitureButtons(g,this);

        //Setup Textures
        setSheet(new Texture("couchSheet.png"));
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

        //Add benfits
        manager.setWelfare(manager.getWelfare() + welfare);
        System.out.println(manager.getWelfare());

        //Set position, price and sellPrice
        setX(x);
        setY(y);
        setPrice(100);
        setSellPrice(50);

        manager.setMoney(manager.getMoney() - getPrice());

        //Add listener
        listener = new FurnitureListener(game, this);
        this.addListener(listener);
    }

    @Override
    public void draw(Batch batch, float alfa) {
        batch.draw(img[dir], getX(), getY(), 1f, 1f);
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }

    @Override
    public void sell() {
        //minus benfits
        manager.setWelfare(manager.getWelfare()-welfare);
        System.out.println(manager.getWelfare());
        //add money
        manager.setMoney(manager.getMoney() + getSellPrice());
        //Destroy couch
        remove();
    }
    public void buy(){
        manager.setWelfare((manager.getWelfare()+welfare));
        manager.setMoney(manager.getMoney() + getSellPrice());
        setBought(true);
    }

    @Override
    void rotate() {
        dir += 1;
        if(dir >3)
            dir = 0;
    }
}