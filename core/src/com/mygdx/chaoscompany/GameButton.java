package com.mygdx.chaoscompany;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Adds button to office or hirestate
 * Created by SamiH on 11.3.2017.
 */

public class GameButton {
    /**
     * game
     */
    private ChaosCompany game;
    /**
     * Button itself
     */
    private TextButton          button;
    /**
     * Buttons skin
     */
    private Skin                skin;
    /**
     * Stage where button is added
     */
    private Stage               stage;
    /**
     * Texture of button
     */
    private Texture             texture;

    /**
     * Buttons name
     */
    private String              buttonName;
    private int                 buttonWidth = 100;
    private int                 buttonHeight = 100;
    private float               buttonScale = 0.01f;
    private float               x = 0;
    private float               y = 0;


    /**
     * Constructor
     * @param g Game
     * @param stage Stage where button is added
     * @param x x coordinate
     * @param y y coordinate
     * @param name name of button
     * @param tex Texture of button
     */
    public GameButton(ChaosCompany g, Stage stage, float x, float y, String name, Texture tex){
        this.game = g;
        this.x = x;
        this.y = y;
        this.buttonName = name;
        this.stage = stage;
        texture = tex;
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Gdx.input.setInputProcessor(stage);
        create();
    }

    /**
     * Method that creates button
     */
    private void create(){

        //button creation
        //Setting up skin color and size of button
        skin = new Skin();

        Pixmap pixmap = new Pixmap(buttonWidth, buttonHeight, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();

        if(texture == null) {
            skin.add("white", new Texture(pixmap));
        }
        else{
            skin.add("white", texture);
        }
        //Setting up skin font of button.
        BitmapFont bfont = new BitmapFont();
        skin.add("default", bfont);

        //Config TextButtonStyle and name it "default"
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("white", Color.DARK_GRAY);

        textButtonStyle.font = skin.getFont("default");

        skin.add("default", textButtonStyle);

        button = new TextButton(""+buttonName, textButtonStyle);
        button.setTransform(true);
        button.setScale(buttonScale);
        button.setPosition(x, y);
        button.setSize(buttonWidth,buttonHeight);
        stage.addActor(button);
    }

    public TextButton getButton(){
        return button;
    }
}
