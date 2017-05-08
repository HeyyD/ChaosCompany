package com.mygdx.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.ChaosCompany;

/**
 * Created by SamiH on 7.5.2017.
 */

public class AnnouncementBox extends Actor{

    private Texture texture;
    private Label textLabel;
    private Label.LabelStyle labelStyle;
    private String text;
    private ChaosCompany game;

    private float timer = 0;

    public AnnouncementBox(ChaosCompany g, String text, Stage textStage){
        game = g;
        texture = new Texture("announcementBox.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.text = text;
        setPosition(2.3f,4.5f);
        setBounds(getX(),getY(),3.3f,1.5f);

        labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        labelStyle.fontColor = Color.BLACK;

        textLabel = new Label(text, labelStyle);
        textLabel.setWrap(true);
        textLabel.setWidth(160f);
        textLabel.setPosition(getX()*100 / 1.25f+100, getY() *100 /1.25f+50);
        textStage.addActor(textLabel);

        addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                timer = 6;
            }
        });

    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture, getX(), getY(), 3.3f,1.5f);
    }

    @Override
    public void act(float delta){
        super.act(delta);

        //After 5 seconds remove AnnouncementBox
        timer += delta;
        if(timer > 5){
            remove();
            textLabel.remove();
            if(game!= null) {
                if (game.getBox() != null) {
                    game.setBox(null);
                }
            }
            if(ChaosCompany.hireState.getBox() != null) {
                ChaosCompany.hireState.setBox(null);
            }
        }
    }
}
