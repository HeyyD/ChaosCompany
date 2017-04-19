package com.mygdx.development;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.mygdx.UI.DevelopMenu;
import com.mygdx.UI.OfficeStateUI;
import com.mygdx.employees.Employee;
import com.mygdx.employees.Programmer;
import com.mygdx.furniture.Computer;
import com.mygdx.game.ChaosCompany;
import com.mygdx.game.StatsManager;

import java.util.ArrayList;

public class Game {

    public float developmentTime;
    public float currentTime;
    public int moneyCycles = 5;

    private ProgressBar progressBar = null;
    private StatsManager statsManager;
    private float developmentSpeed;
    private int value = 100;
    private float setMoneyTime = 50;
    private float currentMoneyTime = setMoneyTime;
    private boolean beingDeveloped = true;
    private ArrayList<Programmer> programmers = new ArrayList<Programmer>();

    public Game(float developmentTime, ArrayList<Programmer> programmers){
        statsManager = ChaosCompany.manager;
        this.developmentTime = developmentTime;
        this.currentTime = 0;
        ArrayList<Programmer> temporaryList = programmers;

        //clone the programmers list for later use
        for(Programmer programmer: temporaryList){
            this.programmers.add(programmer);
        }
        developmentSpeed = calculateDevelopmentTime();
        ChaosCompany.officeState.games.add(this);
    }

    public void update(){
        if(beingDeveloped) {
            if (currentTime < developmentTime) {
                currentTime += Gdx.graphics.getDeltaTime() * developmentSpeed;
                progressBar.setValue(currentTime);
            }
            else{
                DevelopMenu.currentlyDevelopedGame = null;
                OfficeStateUI.developMenu.hideDevelopingMenu();
                freeEmployeesAndComputers();
                beingDeveloped = false;
            }

        } else{
            if(moneyCycles > 0){
                if(currentMoneyTime > 0){
                    currentMoneyTime -= Gdx.graphics.getDeltaTime() * developmentSpeed;
                }else {
                    currentMoneyTime = setMoneyTime;
                    statsManager.setMoney(statsManager.getMoney() + value);
                    moneyCycles--;
                }
            }
        }
    }

    public void freeEmployeesAndComputers(){

        for(Programmer programmer: programmers){
            programmer.setIsAvailable(true);
            programmer.findRandomPlace();
        }

        for (Actor actor: ChaosCompany.officeState.getobjectStage().getActors()){
            if(actor.getClass() == Computer.class){
                Computer computer = (Computer) actor;
                computer.setIsAvailable(true);
            }

        }
    }

    public void setProgressBar(ProgressBar bar){
        this.progressBar = bar;
    }

    private float calculateDevelopmentTime(){
        float developmentTime = 0;
        for(Programmer programmer: programmers){
            developmentTime += programmer.skill;
        }

        return developmentTime;
    }
}
