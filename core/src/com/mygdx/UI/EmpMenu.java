package com.mygdx.UI;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.employees.Employee;
import com.mygdx.game.ChaosCompany;
import com.mygdx.game.StatsManager;

/**
 * Created by SamiH on 2.4.2017.
 */

public class EmpMenu extends Menu {
    private ChaosCompany        game;
    private Employee            employee;
    private Stage               uiStage;
    private I18NBundle          bundle = ChaosCompany.myBundle;

    private TextButton          hireButton;
    private TextButton          cancelButton;

    private float buttonScale = .01f;
    private float buttonOffset = .1f;

    private StatsManager manager = null;
    private BitmapFont teksti = null;

    public EmpMenu(final Employee employee, Stage uiStage, float x, float y){
        super(x, y, 4, 3.5f);

        this.uiStage = uiStage;
        uiStage.addActor(this);
        this.employee = employee;

        manager = ChaosCompany.manager;


        cancelButton = new TextButton(bundle.get("cancel"), skin);
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
                    employee.setMenu(null);
                }
            }
        });

        uiStage.addActor(cancelButton);


        if(!employee.getHired()) {
            hireButton = new TextButton(bundle.get("hire"), skin);
            hireButton.setTransform(true);
            hireButton.setScale(buttonScale);
            hireButton.setPosition(getX() + (getWidth() / 2 - (hireButton.getWidth() / 2 * buttonScale)), cancelButton.getY() + cancelButton.getHeight() * buttonScale + buttonOffset);

            hireButton.addListener(new InputListener() {

                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    //if user is not on top of the button anymore, it dosent do anything
                    if (x > 0 && x < hireButton.getWidth() && y > 0 && y < hireButton.getHeight()) {
                        if (manager.getEmployees() < manager.getEmployeeSlots()) {
                            employee.remove();
                            employee.hire();
                            hideMenu();
                            employee.setMenu(null);
                        }else{

                        }
                    }
                }
            });

            uiStage.addActor(hireButton);
        }
    }

    public void hideMenu() {
        cancelButton.remove();
        if(hireButton != null) {
            hireButton.remove();
        }
        remove();
    }
}
