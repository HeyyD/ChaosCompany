package com.mygdx.chaoscompany;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.UI.AnnouncementBox;

import java.util.Locale;

/**
 * Class that changes screens and contains main render
 */
public class ChaosCompany extends Game {

    //Screens
    /**
     * Main Menu screen
     */
    public static com.mygdx.chaoscompany.MainMenuState mainMenuState;
    /**
     * Game is played mainly in officeState Screen
     */
    public static com.mygdx.chaoscompany.OfficeState officeState;
    /**
     * Map Screen
     */
    public static MapState        mapState;
    /**
     * Screen where player hires employees
     */
    public static com.mygdx.chaoscompany.HireState hireState;

    //Manager
    /**
     * Manager of stats in the game
     */
    public static com.mygdx.chaoscompany.StatsManager manager;
    /**
     * Sound manager
     */
    public static SoundManager    soundManager;
    /**
     * variable for localisation of game
     */
    public static Locale          locale;
    /**
     * variable for localisation of game
     */
    public static Locale          defaultLocale;
    /**
     * Bundle that contains all texts in game that needs to change when language changes
     */
    public static I18NBundle      myBundle;

    //Timers
    /**
     * Timer for Income ticks
     */
    private float                 timer;
    /**
     * Timer for employees
     */
    private float                 empTimer;
    /**
     * With this we calculate time.
     */
    private float                 delta;

    //AnnouncementBox
    /**
     * AnnouncementBox that pops up many times in game
     */
    private AnnouncementBox         box;

    /**
     * For drawing textures
     */
	protected SpriteBatch   batch;

    //Endgame
    /**
     * This variable is not yet used in our game.
     */
    private boolean endGame = false;

    public SpriteBatch getSpriteBatch() {
        return batch;
    }

	@Override
	public void create () {

        locale = new Locale("en", "UK");
        defaultLocale = Locale.getDefault();
        myBundle = I18NBundle.createBundle(Gdx.files.internal("MyBundle"), locale);

        //Stats Manager
        manager                 = new com.mygdx.chaoscompany.StatsManager();
        soundManager            = new SoundManager();

        //Screens
        batch                   = new SpriteBatch();
        mainMenuState           = new com.mygdx.chaoscompany.MainMenuState(this);
        officeState             = new com.mygdx.chaoscompany.OfficeState(this);
        mapState                = new MapState(this);
        hireState               = new com.mygdx.chaoscompany.HireState(this);

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

        //Pelin lopetus päivitetään peliin ennen messuja.
        if(manager.getMoney() < 0 && endGame == false){
            if(getScreen() == officeState){
                //officeState.getStage().addActor(new EndGameScreen(4, 4, 3, 3, this, officeState.getTextStage()));
            }else if(getScreen() == hireState){

            }else if(getScreen() == mapState){

            }
            endGame = true;
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

    public com.mygdx.chaoscompany.OfficeState getOfficeState(){
        return this.officeState;
    }

    public com.mygdx.chaoscompany.HireState getHireState(){
        return hireState;
    }

    public com.mygdx.chaoscompany.StatsManager getManager(){
        return manager;
    }

    public AnnouncementBox getBox() {
        return box;
    }

    public void setBox(AnnouncementBox box) {
        this.box = box;
    }
}
