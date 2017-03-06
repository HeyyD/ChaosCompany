package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class BuildMenu extends Actor{

    //menu
    private Texture             menuBackground;
    private float               menuWidth = 2;
    private float               menuHeight = 2;

    private Skin                skin;
    private int                 buttonWidth = 200;
    private int                 buttonHeight = 60;
    private float               buttonScale = 0.007f;
    private TextButton          buildButton;
    private TextButton          cancelButton;
    private float               buttonOffset = 0.5f;

    public BuildMenu (Stage stage, float x, float y){

        menuBackground = new Texture("white.jpg");

        setSize(menuWidth, menuHeight);
        setPosition(x, y);
        setBounds(getX(), getY(), getWidth(), getHeight());

        stage.addActor(this);

        Gdx.input.setInputProcessor(stage);

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

        buildButton = new TextButton("BUILD", textButtonStyle);
        buildButton.setTransform(true);
        buildButton.setScale(buttonScale);
        buildButton.setPosition(getX() + getWidth() * 0.2f, getY() + getHeight() * 0.6f);

        buildButton.addListener(new InputListener() {

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < buttonWidth && y > 0 && y < buttonHeight){
                    System.out.println("BUILD");
                }
            }
        });

        cancelButton = new TextButton("CANCEL", textButtonStyle);
        cancelButton.setTransform(true);
        cancelButton.setScale(buttonScale);
        cancelButton.setPosition(buildButton.getX(), buildButton.getY() - buttonOffset);

        cancelButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < buttonWidth && y > 0 && y < buttonHeight){
                    Gdx.input.setInputProcessor(ChaosCompany.officeState);
                    ChaosCompany.officeState.resetBuildMenu();
                }
            }
        });

        stage.addActor(this);
        stage.addActor(buildButton);
        stage.addActor(cancelButton);

    }

    @Override
    public void draw(Batch batch, float alpha){
        act(Gdx.graphics.getDeltaTime());
        batch.draw(menuBackground, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }
}
