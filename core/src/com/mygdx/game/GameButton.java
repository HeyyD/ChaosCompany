package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by SamiH on 11.3.2017.
 */

public class GameButton {
    private ChaosCompany        game;
    private TextButton          button;
    private Skin                skin;
    private Stage               stage;

    private String              buttonName;
    private int                 buttonWidth = 100;
    private int                 buttonHeight = 100;
    private float               buttonScale = 0.01f;
    private float               x = 0;
    private float               y = 0;


    public GameButton(ChaosCompany g, float x, float y, String name){
        this.game = g;
        this.x = x;
        this.y = y;
        this.buttonName = name;
        stage = game.getOfficeState().getStage();
        Gdx.input.setInputProcessor(stage);
        create();
    }

    public GameButton(ChaosCompany g, Stage stage, float x, float y, String name){
        this.game = g;
        this.x = x;
        this.y = y;
        this.buttonName = name;
        this.stage = stage;
        Gdx.input.setInputProcessor(stage);
        create();
    }
    private void create(){

        //button creation
        //Setting up skin color and size of button
        skin = new Skin();
        Pixmap pixmap = new Pixmap(buttonWidth, buttonHeight, Pixmap.Format.RGBA8888);
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

        button = new TextButton(""+buttonName, textButtonStyle);
        button.setTransform(true);
        button.setScale(buttonScale);
        button.setPosition(x, y);
        stage.addActor(button);
    }

    public TextButton getButton(){
        return button;
    }
}
