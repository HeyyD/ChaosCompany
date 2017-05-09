package com.mygdx.furniture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.UI.AnnouncementBox;
import com.mygdx.game.ChaosCompany;
import com.mygdx.map.Tile;

/**
 * Created by SamiH on 11.3.2017.
 */

public class FurnitureButtons {
    private TextButton      move;
    private Texture         moveImg;
    private Texture         cancelImg;
    private Texture         rotateImg;
    private Texture         sellImg;
    private Texture         buyImg;

    private TextButton      rotate;
    private TextButton      buySell;
    private TextButton      cancel;
    private Furniture       furniture;
    private String          buySellText = "B";
    private Skin            skin;
    private ChaosCompany    game;
    private Stage           stage;
    private Vector3         touch;
    private Tile[][]        tiles;
    private Vector2         screenCoords;
    private Stage           objectStage;
    private SpriteBatch     batch;

    private TextButton.TextButtonStyle textButtonStyle;

    private float           buttonScale = 0.01f;
    public FurnitureButtons(ChaosCompany g, Furniture f){
        game = g;
        furniture = f;
        stage = game.getOfficeState().getMovingUiStage();
        objectStage = game.getOfficeState().getobjectStage();
        tiles = game.getOfficeState().getTileMap().getTiles();
        batch = game.getOfficeState().getSpriteBatch();

        moveImg = new Texture("UI_ButtonMove.png");
        moveImg.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        cancelImg = new Texture("UI_ButtonCancel.png");
        cancelImg.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        rotateImg = new Texture("UI_ButtonRotate.png");
        rotateImg.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        buyImg = new Texture("UI_ButtonSell.png");
        buyImg.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sellImg = new Texture("UI_ButtonBuy.png");
        sellImg.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        //CREATE SKIN
        skin = new Skin();
        Pixmap pixmap = new Pixmap(40,40 , Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();

        skin.add("white", new Texture(pixmap));

        //Setting up skin font of button.
        BitmapFont bfont = new BitmapFont();
        skin.add("default", bfont);

        //Config TextButtonStyle and name it "default"
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.over = skin.newDrawable("white", Color.DARK_GRAY);

        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        //MOVEBUTTON STYLE
        skin.add("move", moveImg);
        skin.add("move", bfont);

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("move", Color.LIGHT_GRAY);
        textButtonStyle.down = skin.newDrawable("move", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("move", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");
        skin.add("move", textButtonStyle);

        //CANCELBUTTON STYLE
        skin.add("cancel", cancelImg);
        skin.add("cancel", bfont);

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("cancel", Color.LIGHT_GRAY);
        textButtonStyle.down = skin.newDrawable("cancel", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("cancel", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");
        skin.add("cancel", textButtonStyle);

        //ROTATEBUTTON STYLE
        skin.add("rotate", rotateImg);
        skin.add("rotate", bfont);

        //Config TextButtonStyle and name it "default"
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("rotate", Color.LIGHT_GRAY);
        textButtonStyle.down = skin.newDrawable("rotate", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("rotate", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");
        skin.add("rotate", textButtonStyle);

        //SELLBUTTON CREATION
        skin.add("sell", sellImg);
        skin.add("sell", bfont);

        //Config TextButtonStyle and name it "default"
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("sell", Color.LIGHT_GRAY);
        textButtonStyle.down = skin.newDrawable("sell", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("sell", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");
        skin.add("sell", textButtonStyle);

        //BUYBUTTON CREATION
        skin.add("buy", buyImg);
        skin.add("buy", bfont);

        //Config TextButtonStyle and name it "default"
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("buy", Color.LIGHT_GRAY);
        textButtonStyle.down = skin.newDrawable("buy", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("buy", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");
        skin.add("buy", textButtonStyle);
        //END OF SKIN CREATION

        touch = new Vector3();
        screenCoords = new Vector2(0,0);
        create();
    }

    public void create(){
        //tell officeState that we are moving object.
        game.getOfficeState().setIsMoving(true);
        //ROTATE BUTTON
        textButtonStyle = skin.get("rotate",TextButton.TextButtonStyle.class);
        rotate = new TextButton("", textButtonStyle);
        rotate.setTransform(true);
        rotate.setScale(buttonScale);
        rotate.setPosition(furniture.getX()+ 0.2f,furniture.getY() -0.5f);
        rotate.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < 64 && y > 0 && y < 64){
                    furniture.rotate();
                    ChaosCompany.soundManager.playSound(ChaosCompany.soundManager.blop);
                }
            }
        });
        stage.addActor(rotate);

        //MOVE BUTTON

        textButtonStyle = skin.get("move",TextButton.TextButtonStyle.class);
        move = new TextButton("", textButtonStyle);
        move.setTransform(true);
        move.setScale(buttonScale);
        move.setPosition(furniture.getX()+1f, furniture.getY());
        move.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                screenCoords = new Vector2(furniture.getX(),furniture.getY());
                screenCoords = objectStage.toScreenCoordinates(screenCoords, objectStage.getBatch().getTransformMatrix());

                touch.set(screenCoords.x, screenCoords.y, 0);
                game.getOfficeState().getCam().unproject(touch);
                touch.mul(game.getOfficeState().getInvIsotransform());

                int indexX;
                int indexY;

                if(touch.y < 0)
                    indexY = 0;
                else
                    indexY = (int) touch.y + 1;

                indexX = (int)touch.x;

                if(!furniture.getIsMoving() && furniture.getBought()) {
                    tiles[indexX][indexY].setIsFull(false);
                    furniture.setTile(null);
                    furniture.setIsMoving(true);
                    System.out.println(tiles[indexX][indexY].getIsFull());
                }

                return true;
            }
            public void touchDragged(InputEvent event, float x, float y, int pointer){

                touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                game.getOfficeState().getCam().unproject(touch);
                touch.mul(game.getOfficeState().getInvIsotransform());


                if((int)touch.x-1 >= 0 && (int)touch.x -1 < tiles.length && (int)touch.y >= 0 && (int)touch.y < tiles[0].length)
                {
                        if(!tiles[(int)touch.x - 1][(int)touch.y].getIsFull()) {
                            furniture.setX(tiles[(int) touch.x - 1][(int) touch.y].getX());
                            furniture.setY(tiles[(int) touch.x - 1][(int) touch.y].getY());
                        }
                }


                move.setPosition(furniture.getX()+1f, furniture.getY());
                cancel.setPosition(furniture.getX(), furniture.getY() + 1f);
                rotate.setPosition(furniture.getX()+0.2f, furniture.getY() -0.5f);
                buySell.setPosition(furniture.getX()+1f, furniture.getY() +1);
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < 40 && y > 0 && y < 40){

                }
            }

        });
        stage.addActor(move);

        //CANCEL BUTTON
        textButtonStyle = skin.get("cancel",TextButton.TextButtonStyle.class);
        cancel = new TextButton("", textButtonStyle);
        cancel.setTransform(true);
        cancel.setScale(buttonScale);
        cancel.setPosition(furniture.getX(), furniture.getY() +1f);

        cancel.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                screenCoords = new Vector2(furniture.getX(),furniture.getY());
                screenCoords = objectStage.toScreenCoordinates(screenCoords, objectStage.getBatch().getTransformMatrix());

                touch.set(screenCoords.x, screenCoords.y, 0);
                game.getOfficeState().getCam().unproject(touch);
                touch.mul(game.getOfficeState().getInvIsotransform());

                int indexX;
                int indexY;

                if(touch.y < 0)
                    indexY = 0;
                else
                    indexY = (int) touch.y + 1;

                    indexX = (int)touch.x;

                if(x > 0 && x < 64 && y > 0 && y < 64){
                    if(!furniture.getBought()) {
                        removeButtons();
                        furniture.remove();
                        game.getOfficeState().setIsMoving(false);
                    }
                    else if(furniture.getBought()&& !tiles[indexX][indexY].getIsFull()
                            && furniture.getIsMoving()) {
                        removeButtons();
                        tiles[indexX][indexY].setIsFull(true);
                        game.getOfficeState().setIsMoving(false);
                        furniture.setIsMoving(false);
                        furniture.setTile(tiles[indexX][indexY]);
                    }
                    else if(furniture.getBought() && !furniture.getIsMoving()){
                        removeButtons();
                        game.getOfficeState().setIsMoving(false);
                    }
                }
            }
        });
        stage.addActor(cancel);


        //BUY / SELL BUTTON
        if(furniture.getBought()) {
            textButtonStyle = skin.get("buy", TextButton.TextButtonStyle.class);
        }else{
            textButtonStyle = skin.get("sell", TextButton.TextButtonStyle.class);
        }
        buySell = new TextButton("", textButtonStyle);
        buySell.setTransform(true);
        buySell.setScale(buttonScale);
        buySell.setPosition(furniture.getX()+1f, furniture.getY() + 1);

        buySell.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                screenCoords = new Vector2(furniture.getX(),furniture.getY());
                screenCoords = objectStage.toScreenCoordinates(screenCoords, objectStage.getBatch().getTransformMatrix());

                touch.set(screenCoords.x, screenCoords.y, 0);
                game.getOfficeState().getCam().unproject(touch);
                touch.mul(game.getOfficeState().getInvIsotransform());
                System.out.println((int)touch.x + " "+ (int)touch.y);

                int indexX;
                int indexY;

                if(touch.y < 0)
                    indexY = 0;
                else
                    indexY = (int) touch.y + 1;

                indexX = (int)touch.x;
                //I don't know why I have to add +1 to y coordinate, but it works.
                if(x > 0 && x < 64 && y > 0 && y < 64){
                    if(furniture.getBought()){
                        removeButtons();
                        furniture.sell();
                        if(!furniture.getIsMoving()) {
                            tiles[indexX][indexY].setIsFull(false);
                            furniture.setTile(null);
                        }
                    }else if(indexX >= 0 && indexX < tiles.length
                            && indexY >= 0 && indexY < tiles[0].length){
                        if(tiles[indexX] [indexY].getIsFull() == false &&
                                game.getManager().getMoney() >= furniture.getPrice()) {
                            removeButtons();
                            furniture.buy();
                            ChaosCompany.soundManager.playSound(ChaosCompany.soundManager.blop);
                            tiles[indexX][indexY].setIsFull(true);
                            furniture.setTile(tiles[indexX][indexY]);
                        }else if(game.getBox() == null){
                            game.setBox(new AnnouncementBox(game, ChaosCompany.myBundle.get("moneyAnnouncement"),
                                    ChaosCompany.officeState.getTextStage()));
                            game.getOfficeState().getStage().addActor(game.getBox());
                        }
                    }
                    game.getOfficeState().setIsMoving(false);
                }
            }
        });
        stage.addActor(buySell);
    }

    public void removeButtons(){
        cancel.remove();
        rotate.remove();
        buySell.remove();
        move.remove();
    }
    public TextButton getMove(){
        return move;
    }
    public TextButton getCancel(){
        return cancel;
    }
    public TextButton getRotate(){
        return rotate;
    }
    public TextButton getBuySell(){
        return buySell;
    }

    public Furniture getFurniture() {
        return furniture;
    }

    public void setFurniture(Furniture furniture) {
        this.furniture = furniture;
    }

    public boolean isButtonsOpen(){
        boolean isOpen = true;
        if(buySell.getStage() == null && cancel.getStage() == null &&
                rotate.getStage() == null && move.getStage() == null){
            isOpen = false;
        }
        return isOpen;
    }
}