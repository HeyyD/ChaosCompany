package com.mygdx.UI;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.chaoscompany.ChaosCompany;
import com.mygdx.chaoscompany.GameButton;

/**
 * UI Of officeState
 */
public class OfficeStateUI {

    /**
     * Develop menu of game
     */
    public static DevelopMenu developMenu = null;
    /**
     * Progress bar which tells when game is developed completely
     */
    public static ProgressBar developmentTimeBar;

    /**
     * Ui stage of office state
     */
    public Stage uiStage = null;
    /**
     * Develop Button in office state
     */
    public TextButton developButton;
    /**
     * Settings button, bottom left corner
     */
    public TextButton settingsButton;
    /**
     * Scale of buttons
     */
    public float buttonScale = 0.01f;
    /**
     * Button offset
     */
    public float buttonOffset = 0.2f;

    /**
     * Skin of buttons
     */
    private Skin skin = null;

    /**
     * Constructor
     * @param uiStage UiStage of offisceState
     * @param textStage textStage of officeState
     * @param game Game
     */
    public OfficeStateUI(final Stage uiStage, Stage textStage, final ChaosCompany game){

        createSkin();
        this.uiStage = uiStage;
        developmentTimeBar = new ProgressBar(0, 100, 1, false, new Skin(Gdx.files.internal("flat-earth-ui.json")));
        developmentTimeBar.getStyle().background.setMinHeight(20);
        developmentTimeBar.getStyle().knobBefore.setMinHeight(20);
        developmentTimeBar.setPosition(465, 435);
        developmentTimeBar.setValue(0);
        textStage.addActor(developmentTimeBar);

        //develop button
        developButton = new GameButton(game, uiStage, 1.1f, 4.9f,"", new Texture("UI_DevelopBtn.png")).getButton();

        developButton.addListener(new InputListener() {

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                if(x > 0 && x < developButton.getWidth() && y > 0 && y < developButton.getHeight()){
                    if(developMenu == null)
                        developMenu = new DevelopMenu(uiStage);
                    else if(DevelopMenu.currentlyDevelopedGame == null)
                        developMenu.showMenu();
                }
            }
        });

        this.uiStage.addActor(developButton);


        settingsButton = new GameButton(game, uiStage, 0.1f, 0.1f,"", new Texture("UI_SettingsIcon.png")).getButton();
        settingsButton.setScale(0.005f);

        settingsButton.addListener(new InputListener() {

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                if(x > 0 && x < settingsButton.getWidth() && y > 0 && y < settingsButton.getHeight()){
                    game.setScreen(game.mainMenuState);
                }
            }
        });
    }

    /**
     * Creates skin of buttons
     */
    private void createSkin(){
        //Setting up skin color and size of button
        skin = new Skin();
        Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();

        skin.add("white", new Texture("UI_DevelopBtn.png"));

        //Setting up skin font of button.
        BitmapFont bfont = new BitmapFont();
        skin.add("default", bfont);

        //Config TextButtonStyle and name it "default"
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();

        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.over = skin.newDrawable("white", Color.DARK_GRAY);

        textButtonStyle.font = skin.getFont("default");

        skin.add("default", textButtonStyle);
    }

    public static DevelopMenu getDevelopMenu() {
        return developMenu;
    }

    public static void setDevelopMenu(DevelopMenu developMenu) {
        OfficeStateUI.developMenu = developMenu;
    }
}