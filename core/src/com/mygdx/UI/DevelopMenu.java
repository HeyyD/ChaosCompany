package com.mygdx.UI;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.development.Game;
import com.mygdx.employees.Artist;
import com.mygdx.employees.Employee;
import com.mygdx.employees.MarketingExecutive;
import com.mygdx.employees.Programmer;
import com.mygdx.furniture.ComputerFurniture;
import com.mygdx.game.ChaosCompany;

import java.util.ArrayList;

/**
 * Menu where the player decides what warnings he wants the games to have, and where the age restrictions
 * are given.
 */

public class DevelopMenu extends Menu {

    /**
     * The game that the office is currently developing
     */
    public static Game currentlyDevelopedGame = null;

    private TextButton cancelButton;
    private TextButton developButton;
    private I18NBundle bundle = ChaosCompany.myBundle;

    //warnings
    private ArrayList<CheckBox> warnings = new ArrayList<CheckBox>();
    private CheckBox violence;
    private CheckBox drugs;
    private CheckBox fear;
    private CheckBox sex;
    private CheckBox badLanguage;
    private CheckBox gambling;
    private CheckBox discrimination;
    //ages
    private ArrayList<CheckBox> ageButtons = new ArrayList<CheckBox>();
    private CheckBox k3; //karma 1
    private CheckBox k7; // karma 2
    private CheckBox k12; //karma 3
    private CheckBox k16; //karma 4
    private CheckBox k18; //karma 5

    private Stage uiStage;
    private Stage objectStage;
    private float buttonScale = .01f;
    private float checkBoxScale = .013f;
    private float buttonOffset = .1f;
    private boolean checkForEmployees = true;

    //if there is a game that is currently being developed we need these variables
    private boolean canDevelop = false;
    private Stage textUiStage;
    private int gameAgeKarma = 1;
    private ArrayList<Employee> employees = new ArrayList<Employee>();

    /**
     * Tells if develop menu is visible
     */
    private boolean visible = false;

    /**
     * All the checkboxes and buttons are constructed here.
     * @param uiStage The stage where the menu and its buttons are acting
     */
    public DevelopMenu(Stage uiStage) {
        super(1, 0.5f, 7, 4.3f);
        this.uiStage = uiStage;
        uiStage.addActor(this);
        objectStage = ChaosCompany.officeState.getobjectStage();
        textUiStage = ChaosCompany.officeState.getTextStage();

        //cancel button
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
                    if(currentlyDevelopedGame == null)
                        hideMenu();
                    else
                        hideDevelopingMenu();
                }
            }
        });

        uiStage.addActor(cancelButton);

        // developButton
        developButton = new TextButton(bundle.get("startDeveloping"), skin);
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
                    if(canDevelop) {
                        startDeveloping();
                        ChaosCompany.officeState.setDeveloping(true);
                        canDevelop = false;
                    }
                }
            }
        });

        uiStage.addActor(developButton);

        //checkboxes
        violence = new CheckBox(bundle.get("violence"), skin);
        violence.setTransform(true);
        violence.setPosition(getX() + 0.2f ,developButton.getY() + 3f);
        violence.setScale(checkBoxScale);
        warnings.add(violence);
        uiStage.addActor(violence);

        drugs = new CheckBox(bundle.get("drugs"), skin);
        drugs.setTransform(true);
        drugs.setPosition(violence.getX(), violence.getY() - drugs.getHeight()*checkBoxScale);
        drugs.setScale(checkBoxScale);
        warnings.add(drugs);
        uiStage.addActor(drugs);

        fear = new CheckBox(bundle.get("fear"), skin);
        fear.setTransform(true);
        fear.setPosition(drugs.getX(), drugs.getY() - fear.getHeight()*checkBoxScale);
        fear.setScale(checkBoxScale);
        warnings.add(fear);
        fear.setChecked(true);
        uiStage.addActor(fear);

        gambling = new CheckBox(bundle.get("gambling"), skin);
        gambling.setTransform(true);
        gambling.setPosition(fear.getX(), fear.getY() - gambling.getHeight()*checkBoxScale);
        gambling.setScale(checkBoxScale);
        warnings.add(gambling);
        uiStage.addActor(gambling);

        sex = new CheckBox(bundle.get("sex"), skin);
        sex.setTransform(true);
        sex.setPosition(getWidth() * 0.6f, violence.getY());
        sex.setScale(checkBoxScale);
        warnings.add(sex);
        uiStage.addActor(sex);

        badLanguage = new CheckBox(bundle.get("badLanguage"), skin);
        badLanguage.setTransform(true);
        badLanguage.setPosition(sex.getX(), sex.getY() - gambling.getHeight()*checkBoxScale);
        badLanguage.setScale(checkBoxScale);
        warnings.add(badLanguage);
        badLanguage.setChecked(true);
        uiStage.addActor(badLanguage);

        discrimination = new CheckBox(bundle.get("discrimination"), skin);
        discrimination.setTransform(true);
        discrimination.setPosition(badLanguage.getX(), badLanguage.getY() - discrimination.getHeight()*checkBoxScale);
        discrimination.setScale(checkBoxScale);
        warnings.add(discrimination);
        uiStage.addActor(discrimination);


        float ageButtonScale = checkBoxScale * 1.3f;
        float offset = 0.4f;
        //age CheckBoxes
        k3 = new CheckBox("3", skin);
        k3.setChecked(true);
        k3.setTransform(true);
        k3.setPosition(getX() + getWidth()/10, getY() + getHeight()/3);
        k3.setScale(ageButtonScale);
        ageButtons.add(k3);
        k3.addListener(new ToggleListener(k3));
        uiStage.addActor(k3);

        k7 = new CheckBox("7", skin);
        k7.setTransform(true);
        k7.setPosition(k3.getX() + k3.getWidth() * ageButtonScale + offset, k3.getY());
        k7.setScale(ageButtonScale);
        ageButtons.add(k7);
        k7.addListener(new ToggleListener(k7));
        uiStage.addActor(k7);

        k12 = new CheckBox("12", skin);
        k12.setTransform(true);
        k12.setPosition(k7.getX() + k7.getWidth() * ageButtonScale + offset, k7.getY());
        k12.setScale(ageButtonScale);
        ageButtons.add(k12);
        k12.addListener(new ToggleListener(k12));
        uiStage.addActor(k12);

        k16 = new CheckBox("16", skin);
        k16.setTransform(true);
        k16.setPosition(k12.getX() + k7.getWidth() * ageButtonScale + offset, k12.getY());
        k16.setScale(ageButtonScale);
        ageButtons.add(k16);
        k16.addListener(new ToggleListener(k16));
        uiStage.addActor(k16);

        k18 = new CheckBox("18", skin);
        k18.setTransform(true);
        k18.setPosition(k16.getX() + k7.getWidth() * ageButtonScale + offset, k16.getY());
        k18.setScale(ageButtonScale);
        ageButtons.add(k18);
        k18.addListener(new ToggleListener(k18));
        uiStage.addActor(k18);

}

    public void showMenu() {

        //Hide Employee menus
        hideEmpMenus();

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
            uiStage.addActor(k3);
            uiStage.addActor(k7);
            uiStage.addActor(k12);
            uiStage.addActor(k16);
            uiStage.addActor(k18);

        }

        visible = true;
        uiStage.addActor(cancelButton);
    }

    /**
     * Starts the game development. All the employees and base values are given for the games in this
     * method. Also the karma that the game has on the world is calculated here.
     */
    public void startDeveloping(){

        int warningsKarma = 0;

        currentlyDevelopedGame = new Game(100, employees);
        OfficeStateUI.developmentTimeBar.setValue(currentlyDevelopedGame.currentTime);
        currentlyDevelopedGame.setProgressBar(OfficeStateUI.developmentTimeBar);
        for(CheckBox warning: warnings){
            if(warning.isChecked())
                warningsKarma++;
        }
        ChaosCompany.manager.setKarma(gameAgeKarma - warningsKarma);
        employees.clear();
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
        k3.remove();
        k7.remove();
        k12.remove();
        k16.remove();
        k18.remove();
        remove();
        visible = false;
    }

    //this is used if there is currently a game being developed
    public void hideDevelopingMenu(){
        //developmentTimeBar.remove();
        cancelButton.remove();
        remove();
    }

    /**
     * Looks for all the employees and computers from the office. All the employees are given a path to a computer
     * (if there actually is a path).
     */
    public void findEmployee() {

        Array<Actor> actors = objectStage.getActors();
        ArrayList<Employee> workers = new ArrayList<Employee>();
        ArrayList<ComputerFurniture> computers = new ArrayList<ComputerFurniture>();

        // Collecting all the computers and employees to separate lists
        for(Actor actor: actors){
            if(actor.getClass().getSuperclass() == ComputerFurniture.class)
                computers.add((ComputerFurniture) actor);
            else if(actor.getClass().getSuperclass() == Employee.class)
                workers.add((Employee) actor);
        }

        //Try to get a employee for each computer
        for(ComputerFurniture computer: computers){
            if(computer.getIsAvailabel() && computer.getBought() && !computer.getIsMoving()){
                //set the tile free for a moment so the pathfinding can possibly find a path
                boolean isBlocked = true;
                computer.getTile().setIsFull(false);

                for(Employee worker: workers){
                    if(worker.getIsAvailabel()){
                        if(worker.getPathfinding().Path(worker.getCurrentTile(), computer.getTile()) != null) {
                            employees.add(worker);
                            canDevelop = true;
                            isBlocked = false;
                            worker.getLastDestination().setIsFull(false);
                            worker.giveDestination(computer.getTile());
                            worker.setIsAvailable(false);
                            computer.setIsAvailable(false);
                            computer.getTile().setIsFull(true);
                            break;
                        }
                    }
                }
                if(isBlocked)
                    System.out.println("No way to reach a computer");

                //set the tile with the computer full again
                computer.getTile().setIsFull(true);
            }
        }

        System.out.println("Done checking computers");
    }

    /**
     * Sets all other age checkboxes to false, but the "true checkbox".
     * @param trueCheckBox The checkbox we want to keep on.
     */
    public void UnCheckOtherCheckBoxes(CheckBox trueCheckBox){
        for(CheckBox checkBox: ageButtons){
            if(checkBox != trueCheckBox)
                checkBox.setChecked(false);
        }

        //set karma
        if(trueCheckBox == k3)
            gameAgeKarma = 1;
        else if(trueCheckBox == k7)
            gameAgeKarma = 2;
        else if(trueCheckBox == k12)
            gameAgeKarma = 3;
        else if(trueCheckBox == k16)
            gameAgeKarma = 4;
        else
            gameAgeKarma = 5;
    }

    private void hideEmpMenus(){
        //Find all employees of officeState and set their menu as null
        for (int i = 0;
             i < ChaosCompany.officeState.getobjectStage().getActors().size; i++) {
            if(ChaosCompany.officeState.getobjectStage().getActors().get(i).getClass() == Programmer.class ||
                    ChaosCompany.officeState.getobjectStage().getActors().get(i).getClass() == Artist.class ||
                    ChaosCompany.officeState.getobjectStage().getActors().get(i).getClass() == MarketingExecutive.class){
                Employee temp = (Employee)ChaosCompany.officeState.getobjectStage().getActors().get(i);
                if(temp.getMenu() != null) {
                    temp.getMenu().hideMenu();
                    temp.setMenu(null);
                }
            }
        }
    }

    public boolean getVisible() {
        return visible;
    }

    private class ToggleListener extends InputListener{

        private CheckBox checkBox;

        public ToggleListener(CheckBox checkBox){
            this.checkBox = checkBox;
        }

        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            checkBox.toggle();
            checkBox.setChecked(true);
            DevelopMenu.this.UnCheckOtherCheckBoxes(checkBox);
        }
        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            return true;
        }
    }


}