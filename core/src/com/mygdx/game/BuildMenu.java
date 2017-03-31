package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.furniture.Computer;
import com.mygdx.furniture.Couch;
import com.mygdx.furniture.WaterCooler;

import java.util.ArrayList;

public class BuildMenu extends Actor{

    //Game
    private ChaosCompany        game;
    private Stage               stage;
    private Stage               objectStage;
    private Stage               movableUiStage;

    //menu
    private Texture             menuBackground;
    private float               menuWidth = 3.4f;
    private float               menuHeight = 3;

    private Skin                skin;
    private int                 buttonWidth = 64;
    private int                 buttonHeight = 64;
    private float               buttonScale = 0.01f;
    private OrthographicCamera  camera = null;

    //Button Textures
    private Texture             compBtn;

    //Buttons to open differen furniture categories
    private ImageButton          computerButton;
    private ImageButton          wellBeingButton;
    private ImageButton          programmingButton;
    private ImageButton          marketingButton;
    private ImageButton          cancelButton;

    private Texture              computerButtonTex;
    private Texture              wellBeingButtonTex;
    private Texture              marketingButtonTex;
    private Texture              programmingButtonTex;
    private Texture              cancelButtonTex;
    private Texture              waterCoolerButtonIco;
    private Texture              desktopButtonIco;

    //Buttons for each furniture
    private ArrayList<ImageButton>   wellBeingButtons;
    private ArrayList<ImageButton>   programmingButtons;
    private ArrayList<ImageButton>   marketingButtons;
    private ArrayList<ImageButton>   computerButtons;

    //FURNITURE ID
    private final int               couch =             1;
    private final int               waterCooler =       2;
    private final int               desktop =          3;

    private float                   buttonOffset = 0.1f;

    public BuildMenu (ChaosCompany g, float x, float y){

        game                  = g;
        stage                 = game.getOfficeState().getStage();
        objectStage           = game.getOfficeState().getobjectStage();
        game.getOfficeState().setIsBuildMenuOpen(true);
        camera                = ChaosCompany.officeState.getCam();

        //Build buttonlists
        wellBeingButtons = new ArrayList<ImageButton>();
        programmingButtons = new ArrayList<ImageButton>();
        computerButtons = new ArrayList<ImageButton>();
        marketingButtons = new ArrayList<ImageButton>();


        menuBackground = new Texture("white.png");
        setSize(menuWidth, menuHeight);
        setPosition(x, y);
        setBounds(getX(), getY(), getWidth(), getHeight());

        stage.addActor(this);

        //Setup textures
        computerButtonTex = new Texture("UI_BuildComputerBtn.png");
        wellBeingButtonTex = new Texture("UI_BuildWellBeingBtn.png");
        marketingButtonTex = new Texture("UI_BuildMarketingBtn.png");
        programmingButtonTex= new Texture("UI_BuildProgrammingBtn.png");
        cancelButtonTex = new Texture("UI_BuildCancelBtn.png");

        desktopButtonIco = new Texture("DesktopIcon.png");
        waterCoolerButtonIco = new Texture("WaterCoolerIcon.png");

        computerButtonTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        wellBeingButtonTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        marketingButtonTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        programmingButtonTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        cancelButtonTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);

        desktopButtonIco.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        waterCoolerButtonIco.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        //setup skin
        skin = new Skin();

        skin.add("computer", computerButtonTex);
        skin.add("wellBeing", wellBeingButtonTex);
        skin.add("marketing", marketingButtonTex);
        skin.add("programming", programmingButtonTex);
        skin.add("cancel", cancelButtonTex);

        //Setting up skin font of button.
        BitmapFont bfont = new BitmapFont();
        skin.add("default", bfont);

        //Config TextButtonStyle and name it "default"
        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.up = skin.newDrawable("computer", Color.LIGHT_GRAY);
        imageButtonStyle.down = skin.newDrawable("computer", Color.DARK_GRAY);

        skin.add("computer", imageButtonStyle);

        //Button to load Computer Array
        computerButton = new ImageButton(imageButtonStyle);
        computerButton.setTransform(true);
        computerButton.setScale(buttonScale);
        computerButton.setPosition(getX()+buttonOffset, getY() + getHeight() * 0.75f);

        computerButton.addListener(new InputListener() {

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < buttonWidth && y > 0 && y < buttonHeight){
                    removeActors(marketingButtons);
                    removeActors(wellBeingButtons);
                    removeActors(programmingButtons);
                    addActors(computerButtons);
                }
            }
        });

        stage.addActor(computerButton);

        //Button to load Programming array
        imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.up = skin.newDrawable("programming", Color.LIGHT_GRAY);
        imageButtonStyle.down = skin.newDrawable("programming", Color.DARK_GRAY);

        skin.add("programming", imageButtonStyle);

        programmingButton = new ImageButton(imageButtonStyle);
        programmingButton.setTransform(true);
        programmingButton.setScale(buttonScale);
        programmingButton.setPosition(getX()+0.8f,getY() + getHeight() * 0.75f);

        programmingButton.addListener(new InputListener() {

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < buttonWidth && y > 0 && y < buttonHeight){
                    removeActors(marketingButtons);
                    removeActors(wellBeingButtons);
                    removeActors(computerButtons);
                    addActors(programmingButtons);
                }
            }
        });

        stage.addActor(programmingButton);

        imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.up = skin.newDrawable("wellBeing", Color.LIGHT_GRAY);
        imageButtonStyle.down = skin.newDrawable("wellBeing", Color.DARK_GRAY);

        //Button to load wellBeing array
        skin.add("wellBeing", imageButtonStyle);

        wellBeingButton = new ImageButton(imageButtonStyle);
        wellBeingButton.setTransform(true);
        wellBeingButton.setScale(buttonScale);
        wellBeingButton.setPosition(getX()+1.5f,getY() + getHeight() * 0.75f);

        wellBeingButton.addListener(new InputListener() {

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < buttonWidth && y > 0 && y < buttonHeight){
                    removeActors(marketingButtons);
                    removeActors(programmingButtons);
                    removeActors(computerButtons);
                    addActors(wellBeingButtons);
                }
            }
        });
        stage.addActor(wellBeingButton);

        imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.up = skin.newDrawable("marketing", Color.LIGHT_GRAY);
        imageButtonStyle.down = skin.newDrawable("marketing", Color.DARK_GRAY);

        //Button to load marketing array
        skin.add("marketing", imageButtonStyle);

        marketingButton = new ImageButton(imageButtonStyle);
        marketingButton.setTransform(true);
        marketingButton.setScale(buttonScale);
        marketingButton.setPosition(getX()+2.2f,getY() + getHeight() * 0.75f);

        marketingButton.addListener(new InputListener() {

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < buttonWidth && y > 0 && y < buttonHeight){
                    removeActors(wellBeingButtons);
                    removeActors(programmingButtons);
                    removeActors(computerButtons);
                    addActors(marketingButtons);
                }
            }
        });
        stage.addActor(marketingButton);

        imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.up = skin.newDrawable("cancel", Color.LIGHT_GRAY);
        imageButtonStyle.down = skin.newDrawable("cancel", Color.DARK_GRAY);

        //Button to load marketing array
        skin.add("cancel", imageButtonStyle);

        cancelButton = new ImageButton(imageButtonStyle);
        cancelButton.setTransform(true);
        cancelButton.setScale(buttonScale);
        cancelButton.setPosition(getX() + menuWidth - 0.4f, getY() + (menuHeight - 0.4f));

        cancelButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < buttonWidth && y > 0 && y < buttonHeight){
                    cancelButton.remove();
                    computerButton.remove();
                    marketingButton.remove();
                    programmingButton.remove();
                    wellBeingButton.remove();
                    removeActors(marketingButtons);
                    removeActors(programmingButtons);
                    removeActors(wellBeingButtons);
                    removeActors(computerButtons);
                    remove();
                    game.getOfficeState().setIsBuildMenuOpen(false);
                }
            }
        });

        stage.addActor(cancelButton);

        create();
    }
    private void create(){

        //Add build buttons to computerFurnitures
        addFurniture(computerButtons,desktopButtonIco, getX()+buttonOffset, getY() + getHeight() * 0.75f -0.74f,
                     "desktop",desktop);

        //Add build buttons to wellBeingFurnitures
        addFurniture(wellBeingButtons,waterCoolerButtonIco,getX()+buttonOffset, getY() + getHeight() * 0.75f -0.74f,
                     "waterCooler", waterCooler);
        addFurniture(wellBeingButtons,wellBeingButtonTex,getX()+0.8f,getY() + getHeight() * 0.75f -0.74f,
                     "couch", couch);
        //Add build buttons to marketingFurnitures
        //Add build buttons to programmingFurnitures
    }

    @Override
    public void draw(Batch batch, float alpha){
        act(Gdx.graphics.getDeltaTime());
        batch.draw(menuBackground, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }


    private void addFurniture(ArrayList<ImageButton> arrayList, Texture texture, float x, float y,
                              String furnitureName, int furnitureID){
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        skin.add(furnitureName,texture);
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = skin.newDrawable(furnitureName, Color.LIGHT_GRAY);
        style.down = skin.newDrawable(furnitureName, Color.DARK_GRAY);
        skin.add(furnitureName,style);

        arrayList.add(new ImageButton(style));
        int index = arrayList.size() -1;

        arrayList.get(index).setTransform(true);
        arrayList.get(index).setScale(buttonScale);
        arrayList.get(index).setPosition(x,y);
        arrayList.get(index).addListener(new Listener(furnitureID));
    }



    public void removeActors(ArrayList<ImageButton> arrayList){
        for (int i = 0; i < arrayList.size(); i++) {
            if(arrayList.get(i).getStage() != null)
                arrayList.get(i).remove();
        }
    }

    public void addActors(ArrayList<ImageButton> arrayList){
        for (int i = 0; i < arrayList.size(); i++) {
            if(arrayList.get(i).getStage() == null)
                stage.addActor(arrayList.get(i));
        }
    }

    //INPUT LISTENER FOR EVERY BUILD BUTTON
    private class Listener extends InputListener{
        private int furnitureID;

        public Listener(int id){
            furnitureID = id;
        }
        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            return true;
        }
        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

            //if user is not on top of the button anymore, it dosent do anything
            if(x > 0 && x < buttonWidth && y > 0 && y < buttonHeight &&
                    game.getOfficeState().getIsMoving() == false){

                //COORDINATES
                float x_pos = (game.getOfficeState().getTileMap().pickedTileX *
                        game.getOfficeState().getTileMap().tileWidth /2.0f )
                        +
                        (game.getOfficeState().getTileMap().pickedTileY *
                                game.getOfficeState().getTileMap().tileWidth / 2.0f);

                if((int)x_pos < 0) {
                    x_pos = 0;
                }

                float y_pos = - (game.getOfficeState().getTileMap().pickedTileX *
                        game.getOfficeState().getTileMap().tileHeight /2.0f )
                        +
                        (game.getOfficeState().getTileMap().pickedTileY *
                                game.getOfficeState().getTileMap().tileWidth / 4.0f);
                if((int)y_pos < 0) {
                    y_pos = 0;
                }
                //END OF COORDINATES


                switch(furnitureID) {
                    case couch:
                        objectStage.addActor(new Couch(game, x_pos, y_pos));
                        break;
                    case waterCooler:
                        objectStage.addActor(new WaterCooler(game, x_pos, y_pos));
                        break;
                    case desktop:
                        objectStage.addActor(new Computer(game, x_pos, y_pos));
                        break;
                }
                ChaosCompany.officeState.updateDrawingOrder();

                //REMOVE EVERYTHING
                cancelButton.remove();
                computerButton.remove();
                marketingButton.remove();
                wellBeingButton.remove();
                programmingButton.remove();

                removeActors(computerButtons);
                removeActors(marketingButtons);
                removeActors(wellBeingButtons);
                removeActors(programmingButtons);
                remove();


                game.getOfficeState().setIsMoving(true);
                game.getOfficeState().setIsBuildMenuOpen(false);
            }

            }
        }
    }