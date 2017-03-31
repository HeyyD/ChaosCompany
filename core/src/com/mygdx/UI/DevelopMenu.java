package com.mygdx.UI;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.mygdx.employees.Programmer;
import com.mygdx.furniture.Computer;
import com.mygdx.game.ChaosCompany;

public class DevelopMenu extends Menu {

    private TextButton cancelButton;
    private TextButton developButton;
    private Stage uiStage;
    private Stage objectStage;
    private float buttonScale = .01f;
    private float buttonOffset = .1f;

    public DevelopMenu(Stage uiStage) {
        super(1, 0.5f, 5, 4.3f);
        uiStage.addActor(this);
        this.uiStage = uiStage;
        objectStage = ChaosCompany.officeState.getobjectStage();

        //cancel button
        cancelButton = new TextButton("CANCEL", skin);
        cancelButton.setTransform(true);
        cancelButton.setScale(buttonScale);
        cancelButton.setPosition(getX() + (getWidth()/2 - (cancelButton.getWidth()/2*buttonScale)), getY() + 0.2f);

        cancelButton.addListener(new InputListener() {

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if (x > 0 && x < cancelButton.getWidth() && y > 0 && y < cancelButton.getHeight()) {
                    hideMenu();
                }
            }
        });

        uiStage.addActor(cancelButton);

        // developButton
        developButton = new TextButton("START DEVELOPING", skin);
        developButton.setTransform(true);
        developButton.setScale(buttonScale);
        developButton.setPosition(getX() + (getWidth()/2 - (developButton.getWidth()/2*buttonScale)), cancelButton.getY() + cancelButton.getHeight() * buttonScale + buttonOffset);

        developButton.addListener(new InputListener() {

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if (x > 0 && x < developButton.getWidth() && y > 0 && y < developButton.getHeight()) {
                    findEmployee();
                    hideMenu();
                }
            }
        });

        uiStage.addActor(developButton);
    }

    public void showMenu() {
        uiStage.addActor(this);
        uiStage.addActor(cancelButton);
        uiStage.addActor(developButton);
    }

    public void hideMenu() {
        cancelButton.remove();
        developButton.remove();
        remove();
    }

    public void findEmployee() {
        Array<Actor> actors = objectStage.getActors();

        Programmer programmer = null;
        Computer computer = null;

        for (int i = 0; i < actors.size; i++) {
            if (actors.get(i).getClass() == Programmer.class)
                programmer = (Programmer) actors.get(i);

            else if(actors.get(i).getClass() == Computer.class)
                computer = (Computer) actors.get(i);

            if(programmer != null && computer != null) {
                programmer.giveDestination(computer.getTile());
                if(programmer.getPath() == null){
                    computer = null;
                    continue;
                }
                else break;
            }
            else if(i == actors.size - 1)
                System.out.println("Couldn't find a employee or a computer");
        }
    }
}