package com.mygdx.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public abstract class Menu extends Actor {

    protected Skin skin;
    private Texture background;

    public Menu(float x, float y, float width, float height){
        background = new Texture("white.png");
        setPosition(x, y);
        setSize(width, height);
        //textButtonSkin = createTextButtonSkin();
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

    private Skin createTextButtonSkin(){
        //Setting up skin color and size of button
        Skin skin = new Skin();
        Pixmap pixmap = new Pixmap(200, 60, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();

        skin.add("white", new Texture(pixmap));

        //Setting up skin font of button.
        BitmapFont bfont = new BitmapFont();
        skin.add("default", bfont);

        //Config TextButtonStyle and name it "default"
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();

        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.over = skin.newDrawable("white", Color.DARK_GRAY);

        textButtonStyle.font = skin.getFont("default");

        skin.add("default", textButtonStyle);

        return skin;
    }

}
