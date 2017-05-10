package com.mygdx.UI;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.chaoscompany.ChaosCompany;

/**
 * This class is not in game yet, but will be shown when game can actually end (moneys get below 0)
 * Created by SamiH on 9.5.2017.
 */

public class EndGameScreen extends Menu {
    private ChaosCompany game;
    private I18NBundle bundle = ChaosCompany.myBundle;

    public EndGameScreen(float x, float y, float width, float height, ChaosCompany g, Stage textStage) {
        super(x, y, width, height);
        game = g;
        if (game.getBox() != null) {
            game.getBox().setTimer(1000);
        }
        game.setBox(new AnnouncementBox(game, bundle.get("theEnd"), textStage, 1000));

        if (game.getScreen() == game.officeState) {
            game.officeState.getStage().addActor(game.getBox());
        } else if (game.getScreen() == game.hireState) {

        } else if (game.getScreen() == game.mapState) {

        }
    }
}