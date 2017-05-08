package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.UI.AnnouncementBox;
import com.mygdx.UI.MiniIcons;
import com.mygdx.furniture.ArcadeMachine;
import com.mygdx.furniture.BookShelf;
import com.mygdx.furniture.CoffeeMachine;
import com.mygdx.furniture.CoffeeMaker;
import com.mygdx.furniture.Computer;
import com.mygdx.furniture.GameComputer;
import com.mygdx.furniture.MoccaMaster;
import com.mygdx.furniture.Plant;
import com.mygdx.furniture.DrawTable;
import com.mygdx.furniture.EsMachine;
import com.mygdx.furniture.Furniture;
import com.mygdx.furniture.Jukebox;
import com.mygdx.furniture.Laptop;
import com.mygdx.furniture.MarketingTable;
import com.mygdx.furniture.Phone;
import com.mygdx.furniture.PowerComputer;
import com.mygdx.furniture.WaterCooler;

import java.util.ArrayList;

public class BuildMenu extends Actor{

    //Game
    private ChaosCompany        game;
    private Stage               stage;
    private Stage               objectStage;
    private Stage               movableUiStage;

    //Bundle
    private I18NBundle bundle = ChaosCompany.myBundle;

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

    //Texture for each furnitures buildbutton
    private Texture              cancelButtonTex;
    private Texture              waterCoolerButtonIco;
    private Texture              desktopButtonIco;
    private Texture              coffeeMachineButtonIco;
    private Texture              laptopIco;
    private Texture              phoneIco;
    private Texture              jukeboxIco;
    private Texture              arcadeIco;
    private Texture              marketingTableIco;
    private Texture              drawTableIco;
    private Texture              esMachineIco;
    private Texture              plantIco;
    private Texture              powercomputerIco;
    private Texture              gamecomputerIco;
    private Texture              bookshelfIco;
    private Texture              coffeemakerIco;
    private Texture              moccamasterIco;

    //Buttons for each furniture
    private ArrayList<ImageButton>   wellBeingButtons;
    private ArrayList<ImageButton>   programmingButtons;
    private ArrayList<ImageButton>   marketingButtons;
    private ArrayList<ImageButton>   computerButtons;

    //Small icons and price texts
    private MiniIcons               icons1;
    private MiniIcons               icons2;
    private MiniIcons               icons3;
    private MiniIcons               icons4;

    //Text of Build menu
    private Label                   text;
    private Label.LabelStyle        labelStyle;


    //Texture for money icon
    private Texture                 moneyIco;
    private Texture                 programmingIco;

    //FURNITURE ID
    private final int               plant =             1;
    private final int               waterCooler =       2;
    private final int               desktop =           3;
    private final int               coffeeMachine =     4;
    private final int               laptop =            5;
    private final int               phone =             6;
    private final int               jukebox =           7;
    private final int               arcademachine =     8;
    private final int               marketingtable =    9;
    private final int               drawtable =         10;
    private final int               esmachine =         11;
    private final int               powercomputer =     12;
    private final int               gamecomputer =      13;
    private final int               bookshelf =         14;
    private final int               coffeemaker =       15;
    private final int               moccamaster =       16;

    private Furniture               furniture = null;

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
        computerButtonTex = new Texture("ui_BuildComputerBtn.png");
        wellBeingButtonTex = new Texture("ui_BuildWellBeingBtn.png");
        marketingButtonTex = new Texture("ui_BuildMarketingBtn.png");
        programmingButtonTex= new Texture("ui_BuildProgrammingBtn.png");
        cancelButtonTex = new Texture("ui_BuildCancelBtn.png");

        plantIco = new Texture("plantIcon.png");
        desktopButtonIco = new Texture("desktopIcon.png");
        waterCoolerButtonIco = new Texture("watercoolerIcon.png");
        coffeeMachineButtonIco = new Texture("coffeeIcon.png");
        laptopIco = new Texture("laptopIcon.png");
        phoneIco = new Texture("phoneIcon.png");
        jukeboxIco = new Texture("jukeboxIcon.png");
        arcadeIco = new Texture("arcadeIcon.png");
        marketingTableIco = new Texture("marketingtableIcon.png");
        drawTableIco = new Texture("drawtableIcon.png");
        esMachineIco = new Texture("esmachineIcon.png");
        powercomputerIco = new Texture("powercomputerIcon.png");
        gamecomputerIco = new Texture("gamecomputerIcon.png");
        bookshelfIco = new Texture("bookshelfIcon.png");
        coffeemakerIco = new Texture("coffeemakerIcon.png");
        moccamasterIco = new Texture("moccamasterIcon.png");

        computerButtonTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        wellBeingButtonTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        marketingButtonTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        programmingButtonTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        cancelButtonTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);

        desktopButtonIco.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        waterCoolerButtonIco.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        coffeeMachineButtonIco.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        laptopIco.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        phoneIco.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        jukeboxIco.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        arcadeIco.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        marketingTableIco.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        drawTableIco.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        esMachineIco.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        plantIco.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        powercomputerIco.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        gamecomputerIco.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        bookshelfIco.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        coffeemakerIco.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        moccamasterIco.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        //Texture for money Icon
        moneyIco = new Texture("moneyico.png");
        programmingIco = new Texture("programmingicon.png");

        moneyIco.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        programmingIco.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        //setup skin
        skin = new Skin();

        labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        labelStyle.fontColor = Color.BLACK;

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
                    if(text != null){
                        text.remove();
                    }
                    addText(bundle.get("buildComp"));
                    removeMiniIcons();
                    icons1 = new MiniIcons(getX()+0.07f, getY()+1, computerButtonTex,
                            moneyIco, ""+1,""+1000);
                    icons2 = new MiniIcons(getX()+0.73f, getY()+1, computerButtonTex,
                            moneyIco, marketingButtonTex, ""+1,""+4000,""+180);
                    icons3 = new MiniIcons(getX()+1.43f, getY()+1, computerButtonTex,
                            moneyIco, programmingIco, ""+1,""+4000,""+180);
                    icons4 = new MiniIcons(getX()+2.13f, getY()+1, computerButtonTex,
                            moneyIco, wellBeingButtonTex, ""+1,""+4000, ""+180);
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
                    removeMiniIcons();
                    addActors(programmingButtons);
                    if(text != null){
                        text.remove();
                    }
                    addText(bundle.get("buildP"));
                    icons1 = new MiniIcons(getX()+0.07f, getY()+1, programmingIco,
                            moneyIco, ""+25,""+1000);
                    icons2 = new MiniIcons(getX()+0.73f, getY()+1, programmingIco,
                            moneyIco, ""+120,""+4000);
                    icons3 = new MiniIcons(getX()+1.43f, getY()+1, programmingIco,
                            moneyIco, ""+600,""+16000);
                    icons4 = new MiniIcons(getX()+2.13f, getY()+1, programmingIco,
                            moneyIco, ""+2500,""+64000);
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
                    removeMiniIcons();
                    addActors(wellBeingButtons);
                    if(text != null){
                        text.remove();
                    }
                    addText(bundle.get("buildWB"));
                    icons1 = new MiniIcons(getX()+0.07f, getY()+1, wellBeingButtonTex,
                            moneyIco, ""+25,""+500);
                    icons2 = new MiniIcons(getX()+0.73f, getY()+1, wellBeingButtonTex,
                            moneyIco, ""+120,""+2000);
                    icons3 = new MiniIcons(getX()+1.43f, getY()+1, wellBeingButtonTex,
                            moneyIco, ""+600,""+8000);
                    icons4 = new MiniIcons(getX()+2.13f, getY()+1, wellBeingButtonTex,
                            moneyIco, ""+2500,""+32000);
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
                    removeMiniIcons();
                    addActors(marketingButtons);
                    if(text != null){
                        text.remove();
                    }
                    addText(bundle.get("buildM"));
                    icons1 = new MiniIcons(getX()+0.07f, getY()+1, marketingButtonTex,
                            moneyIco, ""+25,""+500);
                    icons2 = new MiniIcons(getX()+0.73f, getY()+1, marketingButtonTex,
                            moneyIco, ""+120,""+2000);
                    icons3 = new MiniIcons(getX()+1.43f, getY()+1, marketingButtonTex,
                            moneyIco, ""+600,""+8000);
                    icons4 = new MiniIcons(getX()+2.13f, getY()+1, marketingButtonTex,
                            moneyIco, ""+2500,""+32000);
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
                    removeMiniIcons();
                    if(text != null){
                        text.remove();
                    }
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
        addFurniture(computerButtons,laptopIco, getX()+0.8f,getY() + getHeight() * 0.75f -0.74f,
                "laptop",laptop);
        addFurniture(computerButtons,powercomputerIco, getX()+1.5f,getY() + getHeight() * 0.75f -0.74f,
                "powercomputer",powercomputer);
        addFurniture(computerButtons,gamecomputerIco, getX()+2.2f,getY() + getHeight() * 0.75f -0.74f,
                "gamecomputer",gamecomputer);

        //Add build buttons to wellBeingFurnitures
        addFurniture(wellBeingButtons, plantIco,getX()+buttonOffset, getY() + getHeight() * 0.75f -0.74f,
                "plant", plant);
        addFurniture(wellBeingButtons,waterCoolerButtonIco,getX()+0.8f,getY() + getHeight() * 0.75f -0.74f,
                     "waterCooler", waterCooler);
        addFurniture(wellBeingButtons,jukeboxIco,getX()+1.5f,getY() + getHeight() * 0.75f -0.74f,
                "jukebox", jukebox);
        addFurniture(wellBeingButtons,arcadeIco,getX()+2.2f,getY() + getHeight() * 0.75f -0.74f,
                "arcade", arcademachine);

        //Add build buttons to marketingFurnitures
        addFurniture(marketingButtons,marketingTableIco,getX()+buttonOffset, getY() + getHeight() * 0.75f -0.74f,
                "marketingtable", marketingtable);
        addFurniture(marketingButtons,drawTableIco,getX()+0.8f,getY() + getHeight() * 0.75f -0.74f,
                "drawtable", drawtable);
        addFurniture(marketingButtons,phoneIco,getX()+1.5f, getY() + getHeight() * 0.75f -0.74f,
                "phone", phone);
        addFurniture(marketingButtons,bookshelfIco,getX()+2.2f,getY() + getHeight() * 0.75f -0.74f,
                "bookshelf", bookshelf);

        //Add build buttons to programmingFurnitures
        addFurniture(programmingButtons,coffeemakerIco,getX()+buttonOffset, getY() + getHeight() * 0.75f -0.74f,
                "coffeemaker", coffeemaker);
        addFurniture(programmingButtons,moccamasterIco,getX()+0.8f, getY() + getHeight() * 0.75f -0.74f,
                "moccamaster", moccamaster);
        addFurniture(programmingButtons,coffeeMachineButtonIco,getX()+1.5f, getY() + getHeight() * 0.75f -0.74f,
                "coffeeMachine", coffeeMachine);
        addFurniture(programmingButtons,esMachineIco,getX()+2.2f,getY() + getHeight() * 0.75f -0.74f,
                "esmachine",esmachine);
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
    public void removeMiniIcons() {
        if(icons1 != null&& icons2 != null && icons3 != null && icons4 != null) {
            icons1.removeIcons();
            icons2.removeIcons();
            icons3.removeIcons();
            icons4.removeIcons();
        }
    }

    public void addActors(ArrayList<ImageButton> arrayList){
        for (int i = 0; i < arrayList.size(); i++) {
            if(arrayList.get(i).getStage() == null)
                stage.addActor(arrayList.get(i));
        }
    }

    public void addText(String string){
        text = new Label(string, labelStyle);
        text.setWrap(true);
        text.setWidth(260f);
        text.setPosition(getX()*100/1.25f+10,getY()*100/1.25f+25);
        game.getOfficeState().getTextStage().addActor(text);
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
            if(x > 0 && x < buttonWidth && y > 0 && y < buttonHeight){

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


                //END OF COORDINATES
                switch(furnitureID) {
                    case plant:
                        furniture = new Plant(game, x_pos, y_pos);
                        objectStage.addActor(furniture);
                        break;
                    case waterCooler:
                        furniture = new WaterCooler(game, x_pos, y_pos);
                        objectStage.addActor(furniture);
                        break;
                    case desktop:
                        furniture = new Computer(game, x_pos, y_pos);
                        objectStage.addActor(furniture);
                        break;
                    case coffeeMachine:
                        furniture = new CoffeeMachine(game, x_pos, y_pos);
                        objectStage.addActor(furniture);
                        break;
                    case laptop:
                        furniture = new Laptop(game, x_pos, y_pos);
                        objectStage.addActor(furniture);
                        break;
                    case phone:
                        furniture = new Phone(game, x_pos, y_pos);
                        objectStage.addActor(furniture);
                        break;
                    case jukebox:
                        furniture = new Jukebox(game, x_pos, y_pos);
                        objectStage.addActor(furniture);
                        break;
                    case arcademachine:
                        furniture = new ArcadeMachine(game, x_pos, y_pos);
                        objectStage.addActor(furniture);
                        break;
                    case marketingtable:
                        furniture = new MarketingTable(game,x_pos,y_pos);
                        objectStage.addActor(furniture);
                        break;
                    case drawtable:
                        furniture = new DrawTable(game,x_pos,y_pos);
                        objectStage.addActor(furniture);
                        break;
                    case esmachine:
                        furniture = new EsMachine(game,x_pos,y_pos);
                        objectStage.addActor(furniture);
                        break;
                    case powercomputer:
                        furniture = new PowerComputer(game,x_pos,y_pos);
                        objectStage.addActor(furniture);
                        break;
                    case gamecomputer:
                        furniture = new GameComputer(game,x_pos,y_pos);
                        objectStage.addActor(furniture);
                        break;
                    case bookshelf:
                        furniture = new BookShelf(game,x_pos,y_pos);
                        objectStage.addActor(furniture);
                        break;
                    case coffeemaker:
                        furniture = new CoffeeMaker(game,x_pos,y_pos);
                        objectStage.addActor(furniture);
                        break;
                    case moccamaster:
                        furniture = new MoccaMaster(game,x_pos,y_pos);
                        objectStage.addActor(furniture);
                        break;
                }
                ChaosCompany.officeState.updateDrawingOrder();

                game.getOfficeState().removeButtons();
                game.getOfficeState().setButtons(furniture.getButtons());
                //REMOVE EVERYTHING
                cancelButton.remove();
                computerButton.remove();
                marketingButton.remove();
                wellBeingButton.remove();
                programmingButton.remove();
                removeMiniIcons();

                if(text != null){
                    text.remove();
                }
                removeActors(computerButtons);
                removeActors(marketingButtons);
                removeActors(wellBeingButtons);
                removeActors(programmingButtons);
                remove();


                game.getOfficeState().setIsMoving(true);
                game.getOfficeState().setIsBuildMenuOpen(false);

                //Tutorial
                if(game.getOfficeState().getfBuild() == true) {
                    if (game.getBox() != null) {
                        game.getBox().setTimer(1000);
                    }
                    game.setBox(new AnnouncementBox(game, bundle.get("tutorial2"),
                            game.getOfficeState().getTextStage(), 10));
                    game.getOfficeState().getStage().addActor(game.getBox());
                    game.getOfficeState().setfBuild(false);
                }

            }
            }
        }
    }