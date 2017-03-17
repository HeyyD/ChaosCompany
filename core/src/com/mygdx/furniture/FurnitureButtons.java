package com.mygdx.furniture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
    private Texture         sellBuyImg;

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
    private Stage           furnitureStage;
    private SpriteBatch     batch;

    private TextButton.TextButtonStyle textButtonStyle;

    private float           buttonScale = 0.01f;
    public FurnitureButtons(ChaosCompany g, Furniture f){
        game = g;
        furniture = f;
        stage = game.getOfficeState().getStage();
        furnitureStage = game.getOfficeState().getFurnitureStage();
        tiles = game.getOfficeState().getTileMap().getTiles();
        batch = game.getOfficeState().getSpriteBatch();

        moveImg = new Texture("UI_ButtonMove.png");
        moveImg.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        cancelImg = new Texture("UI_ButtonCancel.png");
        cancelImg.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

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

        //Config TextButtonStyle and name it "default"
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("move", Color.LIGHT_GRAY);
        textButtonStyle.down = skin.newDrawable("move", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("move", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");
        skin.add("move", textButtonStyle);

        //CANCELBUTTON STYLE
        skin.add("cancel", cancelImg);
        skin.add("cancel", bfont);

        //Config TextButtonStyle and name it "default"
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("cancel", Color.LIGHT_GRAY);
        textButtonStyle.down = skin.newDrawable("cancel", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("cancel", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");
        skin.add("cancel", textButtonStyle);
        //END OF SKIN CREATION

        touch = new Vector3();
        screenCoords = new Vector2(0,0);
        create();
    }

    public void create(){
        //tell officeState that we are moving object.
        game.getOfficeState().setIsMoving(true);
        //ROTATE BUTTON
        textButtonStyle = skin.get("default",TextButton.TextButtonStyle.class);
        rotate = new TextButton("R", textButtonStyle);
        rotate.setTransform(true);
        rotate.setScale(buttonScale);
        rotate.setPosition(furniture.getX()+ 0.5f,furniture.getY() -0.5f);
        rotate.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < 40 && y > 0 && y < 40){
                    furniture.rotate();
                }
            }
        });
        stage.addActor(rotate);

        //MOVE BUTTON

        textButtonStyle = skin.get("move",TextButton.TextButtonStyle.class);
        move = new TextButton("", textButtonStyle);
        move.setTransform(true);
        move.setScale(buttonScale);
        move.setPosition(furniture.getX()+1f, furniture.getY() +1f );
        move.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                screenCoords = new Vector2(furniture.getX(),furniture.getY());
                screenCoords = furnitureStage.toScreenCoordinates(screenCoords, furnitureStage.getBatch().getTransformMatrix());

                touch.set(screenCoords.x, screenCoords.y, 0);
                game.getOfficeState().getCam().unproject(touch);
                touch.mul(game.getOfficeState().getInvIsotransform());

                if(!furniture.getIsMoving() && furniture.getBought()) {
                    tiles[(int) touch.x][(int) touch.y + 1].setIsFull(false);
                    furniture.setIsMoving(true);
                    System.out.println(tiles[(int) touch.x][(int) touch.y + 1].getIsFull());
                }

                return true;
            }
            public void touchDragged(InputEvent event, float x, float y, int pointer){

                touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                game.getOfficeState().getCam().unproject(touch);
                touch.mul(game.getOfficeState().getInvIsotransform());

                if((int)touch.x >= 0 && (int)touch.x < tiles.length && (int)touch.y >= 0 && (int)touch.y < tiles[0].length)
                {
                        if(!tiles[(int)touch.x][(int)touch.y].getIsFull()) {
                            furniture.setX(tiles[(int) touch.x][(int) touch.y].getX());
                            furniture.setY(tiles[(int) touch.x][(int) touch.y].getY());
                        }
                }


                move.setPosition(furniture.getX()+1f, furniture.getY() + 1f);
                cancel.setPosition(furniture.getX(), furniture.getY() + 1f);
                rotate.setPosition(furniture.getX()+0.5f, furniture.getY() -0.5f);
                buySell.setPosition(furniture.getX()+1f, furniture.getY());
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
        cancel.setPosition(furniture.getX(), furniture.getY() +1f );

        cancel.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                screenCoords = new Vector2(furniture.getX(),furniture.getY());
                screenCoords = furnitureStage.toScreenCoordinates(screenCoords, furnitureStage.getBatch().getTransformMatrix());

                touch.set(screenCoords.x, screenCoords.y, 0);
                game.getOfficeState().getCam().unproject(touch);
                touch.mul(game.getOfficeState().getInvIsotransform());

                if(x > 0 && x < 64 && y > 0 && y < 64){
                    if(!furniture.getBought()) {
                        removeButtons();
                        furniture.remove();
                        game.getOfficeState().setIsMoving(false);
                    }
                    else if(furniture.getBought()&& !tiles[(int)touch.x][(int)touch.y+1].getIsFull()
                            && furniture.getIsMoving()) {
                        removeButtons();
                        tiles[(int) touch.x][(int) touch.y + 1].setIsFull(true);
                        game.getOfficeState().setIsMoving(false);
                        furniture.setIsMoving(false);
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
        textButtonStyle = skin.get("default",TextButton.TextButtonStyle.class);
        buySell = new TextButton(buySellText, textButtonStyle);
        buySell.setTransform(true);
        buySell.setScale(buttonScale);
        buySell.setPosition(furniture.getX()+1f, furniture.getY() );

        buySell.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                screenCoords = new Vector2(furniture.getX(),furniture.getY());
                screenCoords = furnitureStage.toScreenCoordinates(screenCoords, furnitureStage.getBatch().getTransformMatrix());

                touch.set(screenCoords.x, screenCoords.y, 0);
                game.getOfficeState().getCam().unproject(touch);
                touch.mul(game.getOfficeState().getInvIsotransform());
                System.out.println((int)touch.x + " "+ (int)touch.y);

                //I don't know why I have to add +1 to y coordinate, but it works.
                if(x > 0 && x < 40 && y > 0 && y < 40){
                    if(furniture.getBought()){
                        removeButtons();
                        furniture.sell();
                        buySellText = "B";
                        if(!furniture.getIsMoving()) {
                            tiles[(int) touch.x][(int) touch.y + 1].setIsFull(false);
                        }
                    }else if((int)touch.x >= 0 && (int)touch.x < tiles.length
                            && (int)touch.y+1 >= 0 && (int)touch.y+1 < tiles[0].length){
                        if(tiles[(int)touch.x] [(int) touch.y+1].getIsFull() == false) {
                            removeButtons();
                            furniture.buy();
                            buySellText = "S";
                            tiles[(int) touch.x][(int) touch.y+1].setIsFull(true);
                        }
                    }
                    game.getOfficeState().setIsMoving(false);
                }
            }
        });
        stage.addActor(buySell);
    }

    private void removeButtons(){
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

    public boolean isButtonsOpen(){
        boolean isOpen = true;
        if(buySell.getStage() == null && cancel.getStage() == null &&
                rotate.getStage() == null && move.getStage() == null){
            isOpen = false;
        }
        return isOpen;
    }
}