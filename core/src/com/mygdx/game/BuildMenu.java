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
import com.mygdx.furniture.Couch;

public class BuildMenu extends Actor{

    //Game
    private ChaosCompany        game;
    private Stage               stage;
    private Stage               furnitureStage;

    //menu
    private Texture             menuBackground;
    private float               menuWidth = 3;
    private float               menuHeight = 3;

    private Skin                skin;
    private int                 buttonWidth = 64;
    private int                 buttonHeight = 64;
    private float               buttonScale = 0.01f;
    private TextButton          buildButton;
    private TextButton          cancelButton;
    private float               buttonOffset = 0.5f;

    public BuildMenu (ChaosCompany g, float x, float y){

        game                    = g;
        stage                   = game.getOfficeState().getStage();
        furnitureStage           = game.getOfficeState().getFurnitureStage();
        game.getOfficeState().setIsBuildMenuOpen(true);

        menuBackground = new Texture("white.jpg");

        setSize(menuWidth, menuHeight);
        setPosition(x, y);
        setBounds(getX(), getY(), getWidth(), getHeight());

        stage.addActor(this);

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
        buildButton.setPosition(getX() + (buildButton.getWidth()/2) * buttonScale, getY() + getHeight() * 0.75f);

        buildButton.addListener(new InputListener() {

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < buttonWidth && y > 0 && y < buttonHeight){
                    float x_pos = (game.getOfficeState().getTileMap().pickedTileX *
                            game.getOfficeState().getTileMap().tileWidth /2.0f )
                            +
                            (game.getOfficeState().getTileMap().pickedTileY *
                                    game.getOfficeState().getTileMap().tileWidth / 2.0f);

                    float y_pos = - (game.getOfficeState().getTileMap().pickedTileX *
                            game.getOfficeState().getTileMap().tileHeight /2.0f )
                            +
                            (game.getOfficeState().getTileMap().pickedTileY *
                                    game.getOfficeState().getTileMap().tileWidth / 4.0f);

                    if(game.getOfficeState().getIsMoving() == false) {
                        furnitureStage.addActor(new Couch(game, 1, 0));
                        ChaosCompany.officeState.updateDrawingOrder();
                        cancelButton.remove();
                        buildButton.remove();
                        remove();
                        game.getOfficeState().setIsMoving(true);
                    }
                    game.getOfficeState().setIsBuildMenuOpen(false);
                }
            }
        });

        cancelButton = new TextButton("X", textButtonStyle);
        cancelButton.setTransform(true);
        cancelButton.setScale(0.005f);
        cancelButton.setPosition(getX() + menuWidth - 0.4f, getY() + (menuHeight - 0.4f));

        cancelButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < buttonWidth && y > 0 && y < buttonHeight){
                    cancelButton.remove();
                    buildButton.remove();
                    remove();
                    game.getOfficeState().setIsBuildMenuOpen(false);
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
