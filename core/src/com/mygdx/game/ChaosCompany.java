package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.UI.AnnouncementBox;

import java.util.Locale;

public class ChaosCompany extends Game {

    //Screens
    public static MainMenuState   mainMenuState;
    public static OfficeState     officeState;
    public static MapState        mapState;
    public static HireState       hireState;

    //Manager
    public static StatsManager    manager;
    public static SoundManager    soundManager;
    public static Locale          locale;
    public static Locale          defaultLocale;
    public static I18NBundle      myBundle;

    //Timers
    private float                 timer;
    private float                 empTimer;
    private float                 delta;

    //AnnouncementBox
    private AnnouncementBox         box;

	protected SpriteBatch   batch;

    public SpriteBatch getSpriteBatch() {
        return batch;
    }

	@Override
	public void create () {

        locale = new Locale("en", "UK");
        defaultLocale = Locale.getDefault();
        myBundle = I18NBundle.createBundle(Gdx.files.internal("MyBundle"), locale);

        //Stats Manager
        manager                 = new StatsManager();
        soundManager            = new SoundManager();

        //Screens
        batch                   = new SpriteBatch();
        mainMenuState           = new MainMenuState(this);
        officeState             = new OfficeState(this);
        mapState                = new MapState(this);
        hireState               = new HireState(this);

        setScreen(mainMenuState);
    }

    @Override
	public void render () {
        super.render();

        //add delta time to timer and call it every 20 seconds
        delta = Gdx.graphics.getDeltaTime();
        timer += delta;
        empTimer += delta;

        //if in Main Menu keep timer at 0
        if(getScreen() == mainMenuState) {
            timer = 0;
        }
        if(getScreen() == mainMenuState){
            empTimer = 0;
        }

        //Update managers stats
        updateManager();
        soundManager.update();

        //Call everything in here every 20 seconds
        if(timer > 15){
            addIncome();
            //Set timer back to 0
            timer = 0;
        }
        //Create employees every 2 minutes
        if(empTimer > 120){
            hireState.createEmployees();
            if(box == null) {
                box = new AnnouncementBox(this, myBundle.get("empAnnouncement"), officeState.getTextStage());
                officeState.getStage().addActor(box);
            }
            empTimer = 0;
        }


        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            setScreen(officeState);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.C)){
            setScreen(mainMenuState);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.V)){
            setScreen(mapState);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.B)){
            setScreen(hireState);
        }
    }
    @Override
    public void dispose () {
        mainMenuState.dispose();
        officeState.dispose();
        mapState.dispose();
        hireState.dispose();
        batch.dispose();
        //System.exit on väliaikaisratkaisu bugiin, jossa peli ei sulkeudu kunnolla
        //android puhelimilla vaan jää taustalle tuottamaan ongnelmia kun pelin käynnistää
        //uudelleen. Keksimme paremman ratkaisun mikäli aikaa siihen jää.
        System.exit(0);
    }

    @Override
    public void pause(){

    }

    //Methods that calculates and adds income.
    public void addIncome(){
        manager.setMoney(manager.getMoney() + manager.getIncome());
    }
    public void updateManager(){
        manager.setGameValue((int) (200 * (1+((float)manager.getWellBeing() / 300))) );
        manager.setIncome((int)(manager.getGameIncome() * (1 + ((float)manager.getMarketingPower()/600)) + manager.getSalaries()));
    }

    public OfficeState getOfficeState(){
        return this.officeState;
    }

    public HireState getHireState(){
        return hireState;
    }

    public StatsManager getManager(){
        return manager;
    }

    public AnnouncementBox getBox() {
        return box;
    }

    public void setBox(AnnouncementBox box) {
        this.box = box;
    }
}
