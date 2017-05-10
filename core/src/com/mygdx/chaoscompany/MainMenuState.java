package com.mygdx.chaoscompany;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.UI.DevelopMenu;
import com.mygdx.UI.OfficeStateUI;

/**
 * Main menu state of game
 */
public class MainMenuState implements Screen {
    /**
     * Skin of buttons
     */
    private Skin skin;
    /**
     * Game
     */
    private ChaosCompany game;
    /**
     * Stage where all the actors is added
     */
    private Stage stage;
    /**
     * Batch for drawing textures
     */
    private SpriteBatch batch;
    /**
     * Camera
     */
    private OrthographicCamera camera;
    /**
     * Bundle for texts
     */
    private I18NBundle bundle = ChaosCompany.myBundle;

    //graphics
    /**
     * BackgroundImage
     */
    private Texture backgroundImage;
    /**
     * earth that is spinning in main menu
     */
    private Sprite earth;
    /**
     * earthRim that is spinning around earth.
     */
    private Sprite earthRim;
    /**
     * "Chaos Company" logo
     */
    private Texture title;

    /**
     * Width of screen
     */
    private final float SCREEN_WIDTH = 800;
    /**
     * Height of screen
     */
    private final float SCREEN_HEIGHT = 480;
    /**
     * Width of buttons
     */
    private final int BUTTON_WIDTH = 200;
    /**
     * Height of buttons
     */
    private final int BUTTON_HEIGHT = 50;
    /**
     * offset ofbuttons
     */
    private int offset = 10;
    /**
     * How fast pictures rotates
     */
    private float rotationSpeed = 0.10f;
    private CheckBox.CheckBoxStyle muteButtonStyle;
    private CheckBox muteButton;

    /**
     * Constructor
     * @param g
     */
    public MainMenuState(ChaosCompany g){
        this.game = g;
        create();
    }

    /**
     * Creates all the buttons and pictures in main menu
     */
    public void create(){
        batch =             new SpriteBatch();
        stage =             new Stage(new FitViewport(SCREEN_WIDTH,SCREEN_HEIGHT));
        camera =            new OrthographicCamera();
        backgroundImage =   new Texture("TitleScreen/background.png");
        earth =             new Sprite(new Texture("TitleScreen/earth.png"));
        earthRim =          new Sprite(new Texture("TitleScreen/earthRim.png"));
        title =             new Texture("TitleScreen/title.png");

        earthRim.setOriginCenter();
        earthRim.setPosition(100, -SCREEN_HEIGHT/1.5f);
        earth.setOriginCenter();
        earth.setPosition(earthRim.getX() + 100, earthRim.getY() + 100);

        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        batch.setProjectionMatrix(camera.combined);

        //Setting up skin color and size of button
        skin = new Skin(Gdx.files.internal("flat-earth-ui.json"));
        muteButtonStyle = createCheckboxStyle();

        //Create TextButton three TextButtons
        final TextButton playBtn = new TextButton(bundle.get("play"),skin);
        final TextButton exitBtn = new TextButton(bundle.get("exit"), skin);
        final TextButton newGameBtn = new TextButton(bundle.get("newGame"), skin);

        playBtn.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        exitBtn.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        newGameBtn.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        //PLAYBUTTON
        playBtn.setPosition(SCREEN_WIDTH/2 - BUTTON_WIDTH/2 , SCREEN_HEIGHT/4 - BUTTON_HEIGHT /2);
        //Add InputListener to playBtn
        playBtn.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //System.out.println("X: "+ x + "Y: " + y);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < BUTTON_WIDTH && y > 0 && y < BUTTON_HEIGHT){
                    game.setScreen(game.getOfficeState());
                    playBtn.setY(SCREEN_HEIGHT/4 - BUTTON_HEIGHT /2 + BUTTON_HEIGHT + offset);
                    playBtn.setText(bundle.get("continue"));
                    stage.addActor(newGameBtn);
                }
            }
        });
        stage.addActor(playBtn);

        //NEW GAME BUTTON
        newGameBtn.setPosition(SCREEN_WIDTH/2 - BUTTON_WIDTH/2 , SCREEN_HEIGHT/4 - BUTTON_HEIGHT /2);

        newGameBtn.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //System.out.println("X: "+ x + "Y: " + y);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < BUTTON_WIDTH && y > 0 && y < BUTTON_HEIGHT){
                    game.getManager().resetManager();
                    newGame();
                    game.setScreen(game.officeState);
                    DevelopMenu.currentlyDevelopedGame = null;
                    OfficeStateUI.developMenu = null;
                }
            }
        });


        //EXIT BUTTON
        exitBtn.setPosition(playBtn.getX(), playBtn.getY() - (playBtn.getHeight() + offset));
        //Add InputListener to exitBtn
        exitBtn.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //System.out.println("X: "+ x + "Y: " + y);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < BUTTON_WIDTH && y > 0 && y < BUTTON_HEIGHT){
                    Gdx.app.log("", "Exiting..");
                    Gdx.app.exit();
                }
            }
        });
        stage.addActor(exitBtn);

        //MUTE BUTTON
        float muteScale = 0.5f;
        float offset = 5f;
        muteButton = new CheckBox("", muteButtonStyle);
        muteButton.setTransform(true);
        muteButton.setScale(muteScale);
        muteButton.setPosition(0 + offset, (camera.viewportHeight - muteButton.getHeight() * muteScale) - offset);

        muteButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                ChaosCompany.soundManager.mute(muteButton.isChecked());
            }
        });

        stage.addActor(muteButton);
    }


    /**
     * Called when player starts new game
     */
    public void newGame(){
        game.officeState.dispose();
        game.mapState.dispose();
        game.hireState.dispose();


        game.officeState = new OfficeState(game);
        game.hireState = new HireState(game);
        game.mapState = new MapState(game);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        //Clear screen
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Draw everything
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        batch.begin();
        batch.draw(backgroundImage, 0, 0, camera.viewportWidth, camera.viewportHeight);
        earthRim.draw(batch);
        earthRim.rotate(rotationSpeed);
        earth.draw(batch);
        earth.rotate(-rotationSpeed/2);
        batch.draw(title, 80, SCREEN_HEIGHT/2);
        batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    public ChaosCompany getGame(){
        return game;
    }

    private CheckBox.CheckBoxStyle createCheckboxStyle(){

        CheckBox.CheckBoxStyle style;
        SpriteDrawable off = new SpriteDrawable(new Sprite(new Texture("TitleScreen/NonMute.png")));
        SpriteDrawable on = new SpriteDrawable(new Sprite(new Texture("TitleScreen/Mute.png")));
        BitmapFont font = new BitmapFont(Gdx.files.internal("font.txt"));

        style = new CheckBox.CheckBoxStyle(off, on, font, Color.WHITE);
        return style;

    }
}
