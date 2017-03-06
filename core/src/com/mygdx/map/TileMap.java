package com.mygdx.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TileMap {

    public int 				    pickedTileX = -1, pickedTileY = -1;
    public float				tileWidth = 1.0f;
    public float				tileHeight = .5f;

    private SpriteBatch         spriteBatch = null;
    private int[][]             map = null;
    private Texture             textureTileset = null;
    private TextureRegion[]		tileSet = null;
    private int                 tileSetWidth = 4;

    public TileMap (int[][] map, SpriteBatch spriteBatch){
        this.map = map;
        this.spriteBatch = spriteBatch;

        textureTileset = new Texture("tileset.png");
        textureTileset.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        tileSet = new TextureRegion[tileSetWidth];
        for(int x = 0; x < tileSet.length; x++){
            tileSet[x] = new TextureRegion(textureTileset, x*64, 0, 64, 32);
        }
    }

    public void renderMap(){
        for (int x = 0; x < map.length; x++){
            for(int y = map[x].length - 1; y >= 0; y--){

                float x_pos = (x * tileWidth /2.0f ) + (y * tileWidth / 2.0f);
                float y_pos = - (x * tileHeight / 2.0f) + (y * tileHeight /2.0f);

                if(x==pickedTileX && y==pickedTileY)
                    spriteBatch.setColor(1.0f, 0.0f, 0.0f, 1.0f);
                else
                    spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);

                spriteBatch.draw(tileSet[map[x][y]], x_pos, y_pos, tileWidth, tileHeight);
            }
        }
    }

}
