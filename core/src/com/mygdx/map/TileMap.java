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
    private final int           TILE_WIDTH_PIXELS = 64;
    private final int           TILE_HEIGHT_PIXELS = 32;
    private TextureRegion[]		tileSet = null;
    private int                 tileSetWidth = 4;
    private Tile[][]            tiles = null;

    public TileMap (int[][] map, SpriteBatch spriteBatch){
        this.map = map;
        tiles = new Tile[map.length][map[0].length];

        this.spriteBatch = spriteBatch;

        textureTileset = new Texture("tileset.png");
        textureTileset.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        tileSet = new TextureRegion[tileSetWidth];
        for(int x = 0; x < tileSet.length; x++){
            tileSet[x] = new TextureRegion(textureTileset, x * TILE_WIDTH_PIXELS, 0, TILE_WIDTH_PIXELS, TILE_HEIGHT_PIXELS);
        }

        //Create tiles according to the map
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles[i].length; j++){
                tiles[i][j] = new Tile(tileSet[map[i][j]]);
            }
        }
    }

    public void renderMap(){
        for (int x = 0; x < tiles.length; x++){
            for(int y = tiles[x].length - 1; y >= 0; y--){

                float x_pos = (x * tileWidth /2.0f ) + (y * tileWidth / 2.0f);
                float y_pos = - (x * tileHeight / 2.0f) + (y * tileHeight /2.0f);

                if(x==pickedTileX && y==pickedTileY)
                    spriteBatch.setColor(1.0f, 0.0f, 0.0f, 1.0f);
                else
                    spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);

                tiles[x][y].draw(spriteBatch, x_pos, y_pos, tileWidth, tileHeight);
            }
        }
    }

}
