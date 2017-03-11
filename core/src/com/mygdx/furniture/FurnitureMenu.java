package com.mygdx.furniture;

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
import com.mygdx.game.ChaosCompany;

/**
 * Created by SamiH on 9.3.2017.
 */

public class FurnitureMenu extends Actor{

    //Game
    private ChaosCompany        game;
    private Stage               stage;
    private Stage               funitureStage;

    //menu
    private Texture menuBackground;
    private float               menuWidth = 2;
    private float               menuHeight = 2;

    private Skin skin;
    private int                 buttonWidth = 100;
    private int                 buttonHeight = 30;
    private float               buttonScale = 0.01f;

    private TextButton          moveButton;
    private TextButton          rotateButton;
    private TextButton          sellButton;
    private TextButton          cancelButton;

    private float               buttonOffset = 0.5f;

    public FurnitureMenu (ChaosCompany g, float x, float y){

        game                    = g;
        stage                   = game.getOfficeState().getStage();
        funitureStage           = game.getOfficeState().getFurnitureStage();

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

        moveButton = new TextButton("MOVE", textButtonStyle);
        moveButton.setTransform(true);
        moveButton.setScale(buttonScale);
        moveButton.setPosition(getX() + (moveButton.getWidth()/2) * buttonScale, getY() + getHeight() * 0.75f);

        moveButton.addListener(new InputListener() {

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

                    funitureStage.addActor(new Couch(game, x_pos, y_pos));
                    //ChaosCompany.officeState.resetBuildMenu();
                }
            }
        });

        rotateButton = new TextButton("CANCEL", textButtonStyle);
        rotateButton.setTransform(true);
        rotateButton.setScale(buttonScale);
        rotateButton.setPosition(moveButton.getX(), moveButton.getY() - buttonOffset);

        rotateButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < buttonWidth && y > 0 && y < buttonHeight){
                    Gdx.input.setInputProcessor(ChaosCompany.officeState);
                    //ChaosCompany.officeState.resetBuildMenu();
                }
            }
        });

        stage.addActor(this);
        stage.addActor(moveButton);
        stage.addActor(rotateButton);

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
