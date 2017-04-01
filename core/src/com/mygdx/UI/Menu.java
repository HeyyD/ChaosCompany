package com.mygdx.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class Menu extends Actor {

    protected Skin skin;
    protected BitmapFont font;
    private Texture background;

    public Menu(float x, float y, float width, float height){
        background = new Texture("darkGreen.png");
        setPosition(x, y);
        setSize(width, height);
        font = new BitmapFont(Gdx.files.internal("font-export.fnt"), false);
        font.getData().setScale(1.1f);
        skin = new Skin(Gdx.files.internal("flat-earth-ui.json"));
    }

    @Override
    public void draw(Batch batch, float alpha) {
        act(Gdx.graphics.getDeltaTime());
        batch.draw(background, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }

}
