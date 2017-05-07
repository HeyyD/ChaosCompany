package com.mygdx.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.ChaosCompany;

/**
 * Created by SamiH on 6.5.2017.
 */

public class MiniIcons {
    private Label       textLabel1;
    private Label       textLabel2;
    private Label       textLabel3;
    private MiniIco     icon1;
    private MiniIco     icon2;
    private MiniIco     icon3;

    public MiniIcons(float x, float y, Texture tex, Texture tex2, String text1, String text2){

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        labelStyle.fontColor = Color.BLACK;

        icon1 = new MiniIco(tex, x, y);

        textLabel1 = new Label(text1,labelStyle);
        textLabel1.setPosition(x*100/1.25f+20,y*100/1.25f);

        icon2 = new MiniIco(tex2, x, y+0.25f);

        textLabel2 = new Label(text2, labelStyle);
        textLabel2.setPosition(x*100/1.25f+20,(y+0.25f)*100/1.25f);
        ChaosCompany.officeState.getStage().addActor(icon1);
        ChaosCompany.officeState.getTextStage().addActor(textLabel1);
        ChaosCompany.officeState.getStage().addActor(icon2);
        ChaosCompany.officeState.getTextStage().addActor(textLabel2);
    }

    public MiniIcons(float x, float y, Texture tex, Texture tex2, Texture tex3,
                     String text1, String text2, String text3){

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        labelStyle.fontColor = Color.BLACK;

        icon1 = new MiniIco(tex, x, y);

        textLabel1 = new Label(text1,labelStyle);
        textLabel1.setPosition(x*100/1.25f+20,y*100/1.25f);

        icon2 = new MiniIco(tex2, x, y+0.25f);

        textLabel2 = new Label(text2, labelStyle);
        textLabel2.setPosition(x*100/1.25f+20,(y+0.25f)*100/1.25f);

        icon3 = new MiniIco(tex3, x, y - 0.25f);

        textLabel3 = new Label(text3, labelStyle);
        textLabel3.setPosition(x*100/1.25f+20,(y-0.25f)*100/1.25f);

        ChaosCompany.officeState.getStage().addActor(icon1);
        ChaosCompany.officeState.getTextStage().addActor(textLabel1);
        ChaosCompany.officeState.getStage().addActor(icon2);
        ChaosCompany.officeState.getTextStage().addActor(textLabel2);
        ChaosCompany.officeState.getStage().addActor(icon3);
        ChaosCompany.officeState.getTextStage().addActor(textLabel3);
    }

    public void removeIcons(){
        if(textLabel1 != null && textLabel2 != null && icon1 != null && icon2 != null) {
            textLabel1.remove();
            textLabel2.remove();
            icon1.remove();
            icon2.remove();
        }
        if(textLabel3!= null && icon3 != null){
            icon3.remove();
            textLabel3.remove();
        }
    }
}
