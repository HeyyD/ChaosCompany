package com.mygdx.UI;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.mygdx.employees.Employee;
import com.mygdx.game.BuildMenu;
import com.mygdx.game.ChaosCompany;
import com.mygdx.game.GameButton;

import java.util.ArrayList;

public class OfficeStateUI {

    public static DevelopMenu developMenu = null;
    public static ProgressBar developmentTimeBar;

    public Stage uiStage = null;
    public TextButton developButton;
    public float buttonScale = 0.01f;
    public float buttonOffset = 0.2f;
    private Skin skin = null;

    public OfficeStateUI(final Stage uiStage, Stage textStage, ChaosCompany game){

        createSkin();
        this.uiStage = uiStage;
        developmentTimeBar = new ProgressBar(0, 100, 1, false, new Skin(Gdx.files.internal("flat-earth-ui.json")));
        developmentTimeBar.getStyle().background.setMinHeight(20);
        developmentTimeBar.getStyle().knobBefore.setMinHeight(20);
        developmentTimeBar.setPosition(465, 435);
        developmentTimeBar.setValue(0);
        textStage.addActor(developmentTimeBar);

        //develop button
        developButton = new GameButton(game, uiStage, 1.1f, 4.9f,"", new Texture("UI_DevelopBtn.png")).getButton();

        developButton.addListener(new InputListener() {

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                if(x > 0 && x < developButton.getWidth() && y > 0 && y < developButton.getHeight()){
                    if(developMenu == null)
                        developMenu = new DevelopMenu(uiStage);
                    else if(DevelopMenu.currentlyDevelopedGame == null)
                        developMenu.showMenu();
                }
            }
        });

        this.uiStage.addActor(developButton);
    }

    private void createSkin(){
        //Setting up skin color and size of button
        skin = new Skin();
        Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();

        skin.add("white", new Texture("UI_DevelopBtn.png"));

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
    }

    public static DevelopMenu getDevelopMenu() {
        return developMenu;
    }

    public static void setDevelopMenu(DevelopMenu developMenu) {
        OfficeStateUI.developMenu = developMenu;
    }
}