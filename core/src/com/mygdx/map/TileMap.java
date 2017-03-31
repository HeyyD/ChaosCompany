package com.mygdx.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public class TileMap {

    public int 				    pickedTileX = -1, pickedTileY = -1;
    public float				tileWidth = 1.0f;
    public float				tileHeight = .5f;

    private SpriteBatch         spriteBatch = null;
    private int[][]             map = null;
    private Texture             textureTileset = null;
    private final int           TILE_WIDTH_PIXELS = 128;
    private final int           TILE_HEIGHT_PIXELS = 64;
    private TextureRegion[][]   tileSetTmp = null;
    private TextureRegion[]     tileSet = null;
    private int                 tileSetWidth = 2;
    private int                 tileSetHeight = 5;
    private Tile[][]            tiles = null;

    public TileMap (int[][] map, SpriteBatch spriteBatch){
        this.map = map;
        tiles = new Tile[map.length][map[0].length];

        this.spriteBatch = spriteBatch;

        textureTileset = new Texture("tileset.png");
        textureTileset.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);

        tileSetTmp = new TextureRegion[tileSetWidth][tileSetHeight];

        for(int j = 0; j < tileSetTmp.length; j++){
            for (int i = 0; i < tileSetTmp[j].length; i++) {
                tileSetTmp[j][i] = new TextureRegion(textureTileset, j * TILE_WIDTH_PIXELS, i * TILE_HEIGHT_PIXELS, TILE_WIDTH_PIXELS, TILE_HEIGHT_PIXELS);
            }
        }

        int k = 0;
        tileSet = new TextureRegion[tileSetWidth * tileSetHeight];

        for (int i = 0; i < tileSetTmp.length; i++) {
            for (int j = 0; j < tileSetTmp[i].length; j++) {
                tileSet[k] = tileSetTmp[i][j];
                k++;
            }
        }

        //Create tiles according to the map
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles[i].length; j++){
                tiles[i][j] = new Tile(tileSet[map[i][j]], i, j);
                //If map ID is not 5, set the tile full
                if(map[i][j] != 5){
                    tiles[i][j].setIsFull(true);
                }
            }
        }
        //give tiles coordinates
        for (int x = 0; x < tiles.length; x++){
            for(int y = tiles[x].length - 1; y >= 0; y--){

                float x_pos = (x * tileWidth /2.0f ) + (y * tileWidth / 2.0f);
                float y_pos = - (x * tileHeight / 2.0f) + (y * tileHeight /2.0f);

                tiles[x][y].setX(x_pos);
                tiles[x][y].setY(y_pos);
            }
        }
    }

    public void renderMap(){
        for (int x = 0; x < tiles.length; x++){
            for(int y = tiles[x].length - 1; y >= 0; y--){

                if(x==pickedTileX && y==pickedTileY)
                    spriteBatch.setColor(1.0f, 0.0f, 0.0f, 1.0f);
                else
                    spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);

                tiles[x][y].draw(spriteBatch, tiles[x][y].getX(), tiles[x][y].getY(), tileWidth, tileHeight);
            }
        }
    }

    public List<Tile> getNeighbours(Tile tile){

        ArrayList<Tile> neighbours = new ArrayList<Tile>();

        for(int x = -1; x <= 1; x++){
            for(int y = -1; y <= 1; y++){
                if(x == 0 && y == 0)
                    continue;

                int checkX = tile.mapX + x;
                int checkY = tile.mapY + y;

                if(checkX >= 0 && checkX < map.length && checkY >= 0 && checkY < map[0].length)
                    neighbours.add(tiles[checkX][checkY]);
            }
        }
        return neighbours;
    }

    public boolean isPickedTileFull(int pickedTileX, int pickedTileY){
        boolean isFull = false;
        if(tiles[pickedTileX][pickedTileY].getIsFull()){
            isFull = true;
        }
        return isFull;
    }
    public Tile[][] getTiles(){
        return tiles;
    }
}