package com.mygdx.development;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.mygdx.UI.DevelopMenu;
import com.mygdx.UI.OfficeStateUI;
import com.mygdx.employees.Artist;
import com.mygdx.employees.Employee;
import com.mygdx.employees.MarketingExecutive;
import com.mygdx.employees.Programmer;
import com.mygdx.furniture.Computer;
import com.mygdx.game.ChaosCompany;
import com.mygdx.game.StatsManager;

import java.util.ArrayList;

public class Game {

    public float developmentTime;
    public float currentTime;
    public int moneyCycles = 3;

    private ProgressBar progressBar = null;
    private StatsManager statsManager;
    private float developmentSpeed;
    private int value = 100;
    private float setMoneyTime = 50;
    private float currentMoneyTime = setMoneyTime;
    private boolean beingDeveloped = true;
    private ArrayList<Employee> employees = new ArrayList<Employee>();
    private ArrayList<Programmer> programmers = new ArrayList<Programmer>();
    private ArrayList<Artist> artists = new ArrayList<Artist>();
    private ArrayList<MarketingExecutive> marketingExecutives = new ArrayList<MarketingExecutive>();

    public Game(float developmentTime, ArrayList<Employee> employees){
        statsManager = ChaosCompany.manager;
        this.developmentTime = developmentTime;
        this.currentTime = 0;
        ArrayList<Employee> temporaryList = employees;

        //clone the programmers list for later use
        for(Employee employee: temporaryList){
            if(employee.getClass() == Programmer.class)
                this.programmers.add((Programmer) employee);
            else if(employee.getClass() == Artist.class)
                this.artists.add((Artist) employee);
            else
                this.marketingExecutives.add((MarketingExecutive) employee);

            this.employees.add(employee);

        }
        developmentSpeed = calculateDevelopmentTime();
        value = calculateValue();
        moneyCycles = calculateMoneyCycles();
        ChaosCompany.officeState.games.add(this);

        System.out.println("developentSpeed: " + developmentSpeed +
                            " value: " + value +
                            " moneyCycles: " + moneyCycles);
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
                progressBar.setValue(0);
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

        for(Employee employee: employees){
            employee.setIsAvailable(true);
            employee.findRandomPlace();
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

    private int calculateMoneyCycles(){
        int moneyCycles = this.moneyCycles;

        for(MarketingExecutive marketer: marketingExecutives){
            moneyCycles += (int) marketer.skill;
        }

        return  moneyCycles;
    }

    private int calculateValue(){
        int value = this.value;

        for(Artist artist: artists){
            value += artist.skill * 10f;
        }

        return value;
    }

    private float calculateDevelopmentTime(){
        float developmentTime = 0;
        for(Programmer programmer: programmers){
            developmentTime += programmer.skill;
        }

        return developmentTime;
    }
}
