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

public class DevelopMenu extends Menu {

    public static Game currentlyDevelopedGame = null;

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
    private float buttonOffset = .1f;
    private boolean checkForEmployees = true;

    //if there is a game that is currently being developed we need these variables
    private ProgressBar developmentTimeBar;
    private boolean canDevelop = false;
    private Stage textUiStage;

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
        violence.setPosition(getX() + 0.2f ,developButton.getY() + 1.5f);
        violence.setScale(buttonScale);
        uiStage.addActor(violence);

        drugs = new CheckBox("Drugs", skin);
        drugs.setTransform(true);
        drugs.setPosition(violence.getX(), violence.getY() - drugs.getHeight()*buttonScale);
        drugs.setScale(buttonScale);
        uiStage.addActor(drugs);

        fear = new CheckBox("Fear", skin);
        fear.setTransform(true);
        fear.setPosition(drugs.getX(), drugs.getY() - fear.getHeight()*buttonScale);
        fear.setScale(buttonScale);
        uiStage.addActor(fear);

        gambling = new CheckBox("Gambling", skin);
        gambling.setTransform(true);
        gambling.setPosition(fear.getX(), fear.getY() - gambling.getHeight()*buttonScale);
        gambling.setScale(buttonScale);
        uiStage.addActor(gambling);

        sex = new CheckBox("Sex", skin);
        sex.setTransform(true);
        sex.setPosition(violence.getX() + violence.getWidth() * buttonScale + 1, violence.getY());
        sex.setScale(buttonScale);
        uiStage.addActor(sex);

        badLanguage = new CheckBox("Bad Language", skin);
        badLanguage.setTransform(true);
        badLanguage.setPosition(sex.getX(), sex.getY() - gambling.getHeight()*buttonScale);
        badLanguage.setScale(buttonScale);
        uiStage.addActor(badLanguage);

        discrimination = new CheckBox("Discrimination", skin);
        discrimination.setTransform(true);
        discrimination.setPosition(badLanguage.getX(), badLanguage.getY() - discrimination.getHeight()*buttonScale);
        discrimination.setScale(buttonScale);
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

        currentlyDevelopedGame = new Game(100);
        developmentTimeBar = new ProgressBar(0, currentlyDevelopedGame.developmentTime, 1, false, skin);
        developmentTimeBar.getStyle().background.setMinHeight(height);
        developmentTimeBar.getStyle().knobBefore.setMinHeight(height);
        developmentTimeBar.setPosition(210, 350);
        developmentTimeBar.setValue(currentlyDevelopedGame.currentTime);
        currentlyDevelopedGame.setProgressBar(developmentTimeBar);
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