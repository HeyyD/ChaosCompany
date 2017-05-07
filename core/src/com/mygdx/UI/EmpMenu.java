package com.mygdx.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
    private TextButton          fireButton;

    private float               buttonScale = .01f;
    private float               buttonOffset = .1f;

    private StatsManager        manager = null;

    //Texts of Employee menu
    private Label               profession = null;
    private Label               salaryText = null;
    private Label               description = null;

    //Bars for Employee menu
    private ProgressBar         professionBar = null;

    public EmpMenu(final Employee employee, Stage uiStage, Stage textStage, float x, float y){
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
                        }else if(ChaosCompany.hireState.getBox() == null){
                            ChaosCompany.hireState.setBox(new AnnouncementBox(game, bundle.get("hireAnnouncement"),
                                    ChaosCompany.hireState.getTextStage()));
                            ChaosCompany.hireState.getStage().addActor(ChaosCompany.hireState.getBox());
                        }
                    }
                }
            });

            uiStage.addActor(hireButton);
        }else{
            fireButton = new TextButton(bundle.get("fire"), skin);
            fireButton.setTransform(true);
            fireButton.setScale(buttonScale);
            fireButton.setPosition(getX() + (getWidth() / 2 - (fireButton.getWidth() / 2 * buttonScale)), cancelButton.getY() + cancelButton.getHeight() * buttonScale + buttonOffset);

            fireButton.addListener(new InputListener() {

                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    //if user is not on top of the button anymore, it dosent do anything
                    if (x > 0 && x < fireButton.getWidth() && y > 0 && y < fireButton.getHeight()) {
                            employee.fire();
                            employee.remove();
                            hideMenu();
                            employee.setMenu(null);
                    }
                }
            });

            uiStage.addActor(fireButton);

        }


        Label.LabelStyle labelStyle = new Label.LabelStyle();
        BitmapFont labelFont = new BitmapFont(Gdx.files.internal("font.fnt"));
        labelStyle.font = labelFont;
        labelStyle.fontColor = Color.WHITE;

        Label.LabelStyle labelStyle2 = new Label.LabelStyle();
        labelStyle2.font = new BitmapFont();
        labelStyle2.fontColor = Color.WHITE;

        String value = null;

        //Add profession skills text
        if(employee.getProfession() == "PROGRAMMER") {
                value = bundle.get("programming");
        }else if(employee.getProfession() == "MARKETING") {
                value = bundle.get("marketing");
        }else{
                value = bundle.get("art");
        }
        profession = new Label(value, labelStyle);
        profession.setPosition(30f, 320f);
        textStage.addActor(profession);

        //Add bar that shows how good the skill is
        professionBar = new ProgressBar(0, 500, 1, false, new Skin(Gdx.files.internal("flat-earth-ui.json")));
        professionBar.getStyle().background.setMinHeight(20);
        professionBar.getStyle().knobBefore.setMinHeight(20);
        professionBar.setPosition(30, 292);
        professionBar.setValue(employee.getSkill()*100);
        textStage.addActor(professionBar);

        value = bundle.get("salary") + " "+employee.getSalary()+"$";
        salaryText = new Label(value, labelStyle);
        salaryText.setPosition(30f, 260f);
        textStage.addActor(salaryText);

        if(employee.getProfession() == "PROGRAMMER") {
            value = bundle.get("pDescription");
        }else if(employee.getProfession() == "MARKETING") {
            value = bundle.get("mDescription");
        }else{
            value = bundle.get("aDescription");
        }

        description = new Label(value, labelStyle2);
        description.setWrap(true);
        description.setWidth(300f);
        description.setPosition(30f, 220f);
        textStage.addActor(description);
    }

    public void hideMenu() {
        cancelButton.remove();
        if(hireButton != null) {
            hireButton.remove();
        }
        profession.remove();
        professionBar.remove();
        salaryText.remove();
        description.remove();
        if(fireButton != null) {
            fireButton.remove();
        }
        remove();
    }
}
