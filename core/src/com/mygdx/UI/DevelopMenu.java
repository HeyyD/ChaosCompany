package com.mygdx.UI;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.mygdx.development.Game;
import com.mygdx.employees.Employee;
import com.mygdx.employees.Programmer;
import com.mygdx.furniture.Computer;
import com.mygdx.game.ChaosCompany;

import java.util.ArrayList;

public class DevelopMenu extends Menu {

    public static Game currentlyDevelopedGame = null;
    public static ProgressBar developmentTimeBar;

    private TextButton cancelButton;
    private TextButton developButton;
    private CheckBox violence;
    private CheckBox drugs;
    private CheckBox fear;
    private CheckBox sex;
    private CheckBox badLanguage;
    private CheckBox gambling;
    private CheckBox discrimination;
    private Stage uiStage;
    private Stage objectStage;
    private float buttonScale = .01f;
    private float checkBoxScale = .013f;
    private float buttonOffset = .1f;
    private boolean checkForEmployees = true;

    //if there is a game that is currently being developed we need these variables
    private boolean canDevelop = false;
    private Stage textUiStage;
    private ArrayList<Programmer> programmers = new ArrayList<Programmer>();

    public DevelopMenu(Stage uiStage) {
        super(1, 0.5f, 5, 4.3f);
        this.uiStage = uiStage;
        uiStage.addActor(this);
        objectStage = ChaosCompany.officeState.getobjectStage();
        textUiStage = ChaosCompany.officeState.getTextStage();

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
                    if(currentlyDevelopedGame == null)
                        hideMenu();
                    else
                        hideDevelopingMenu();
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
                    while(checkForEmployees)
                        findEmployee();
                    hideMenu();
                    if(canDevelop) {
                        startDeveloping();
                        canDevelop = false;
                    }
                }
            }
        });

        uiStage.addActor(developButton);

        //checkboxes
        violence = new CheckBox("Violence", skin);
        violence.setTransform(true);
        violence.setPosition(getX() + 0.2f ,developButton.getY() + 3f);
        violence.setScale(checkBoxScale);
        uiStage.addActor(violence);

        drugs = new CheckBox("Drugs", skin);
        drugs.setTransform(true);
        drugs.setPosition(violence.getX(), violence.getY() - drugs.getHeight()*checkBoxScale);
        drugs.setScale(checkBoxScale);
        uiStage.addActor(drugs);

        fear = new CheckBox("Fear", skin);
        fear.setTransform(true);
        fear.setPosition(drugs.getX(), drugs.getY() - fear.getHeight()*checkBoxScale);
        fear.setScale(checkBoxScale);
        uiStage.addActor(fear);

        gambling = new CheckBox("Gambling", skin);
        gambling.setTransform(true);
        gambling.setPosition(fear.getX(), fear.getY() - gambling.getHeight()*checkBoxScale);
        gambling.setScale(checkBoxScale);
        uiStage.addActor(gambling);

        sex = new CheckBox("Sex", skin);
        sex.setTransform(true);
        sex.setPosition(violence.getX(), gambling.getY() - sex.getHeight() * checkBoxScale);
        sex.setScale(checkBoxScale);
        uiStage.addActor(sex);

        badLanguage = new CheckBox("Bad Language", skin);
        badLanguage.setTransform(true);
        badLanguage.setPosition(sex.getX(), sex.getY() - gambling.getHeight()*checkBoxScale);
        badLanguage.setScale(checkBoxScale);
        uiStage.addActor(badLanguage);

        discrimination = new CheckBox("Discrimination", skin);
        discrimination.setTransform(true);
        discrimination.setPosition(badLanguage.getX(), badLanguage.getY() - discrimination.getHeight()*checkBoxScale);
        discrimination.setScale(checkBoxScale);
        uiStage.addActor(discrimination);
    }

    public void showMenu() {

        //background
        uiStage.addActor(this);

        if(currentlyDevelopedGame == null) {
            uiStage.addActor(developButton);
            uiStage.addActor(violence);
            uiStage.addActor(drugs);
            uiStage.addActor(fear);
            uiStage.addActor(sex);
            uiStage.addActor(badLanguage);
            uiStage.addActor(discrimination);
            uiStage.addActor(gambling);

        }else{
            textUiStage.addActor(developmentTimeBar);
        }

        uiStage.addActor(cancelButton);
    }

    public void startDeveloping(){

        int height = 20;

        currentlyDevelopedGame = new Game(100, programmers);
        developmentTimeBar = new ProgressBar(0, currentlyDevelopedGame.developmentTime, 1, false, skin);
        developmentTimeBar.getStyle().background.setMinHeight(height);
        developmentTimeBar.getStyle().knobBefore.setMinHeight(height);
        developmentTimeBar.setPosition(210, 350);
        developmentTimeBar.setValue(currentlyDevelopedGame.currentTime);
        currentlyDevelopedGame.setProgressBar(developmentTimeBar);
        programmers.clear();
    }

    public void hideMenu() {
        cancelButton.remove();
        developButton.remove();
        violence.remove();
        drugs.remove();
        fear.remove();
        sex.remove();
        badLanguage.remove();
        discrimination.remove();
        gambling.remove();
        checkForEmployees = true;
        remove();
    }

    //this is used if there is currently a game being developed
    public void hideDevelopingMenu(){
        developmentTimeBar.remove();
        cancelButton.remove();
        remove();
    }

    public void findEmployee() {
        Array<Actor> actors = objectStage.getActors();

        Employee currentEmployee;
        Computer currentComputer;
        Employee employee = null;
        Computer computer = null;

        for (int i = 0; i < actors.size; i++) {
            if (actors.get(i).getClass() == Programmer.class){
                currentEmployee = (Employee) actors.get(i);
                if(currentEmployee.getIsAvailabel()) {
                    employee = (Employee) actors.get(i);
                }
            }

            else if(actors.get(i).getClass() == Computer.class) {
                currentComputer = (Computer) actors.get(i);
                if(currentComputer.getIsAvailabel())
                    computer = (Computer) actors.get(i);
            }

            if(employee != null && computer != null) {
                employee.giveDestination(computer.getTile());
                if(employee.getPath() == null){
                    computer = null;
                    employee = null;
                    continue;
                }
                else {
                    employee.setIsAvailable(false);
                    computer.setIsAvailable(false);

                    if(employee.getClass() == Programmer.class)
                        programmers.add((Programmer) employee);

                    canDevelop = true;
                    break;
                }
            }
            else if(i == actors.size - 1){
                checkForEmployees = false;
                System.out.println("Couldn't find a employee or a computer");
            }
        }
    }
}