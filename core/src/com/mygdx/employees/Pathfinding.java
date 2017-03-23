package com.mygdx.employees;

import com.mygdx.map.Tile;
import com.mygdx.map.TileMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Pathfinding {

    private TileMap tileMap;

    public Pathfinding(TileMap tileMap){
        this.tileMap = tileMap;
    }

    public ArrayList<Tile> Path(Tile startPos, Tile targetPos){

        ArrayList<Tile> openSet = new ArrayList<Tile>();
        HashSet<Tile> closedSet = new HashSet<Tile>();
        openSet.add(startPos);

        while(openSet.size() > 0){
            Tile currentNode = openSet.get(0);
            for(int i = 1; i < openSet.size(); i++){
                if(openSet.get(i).fCost() < currentNode.fCost() || (openSet.get(i).fCost() == currentNode.fCost() && openSet.get(i).hCost < currentNode.hCost)){
                    currentNode = openSet.get(i);
                }
            }

            openSet.remove(currentNode);
            closedSet.add(currentNode);

            if(currentNode == targetPos){
                return RetracePath(startPos, targetPos);
            }

            for (Tile neighbour : tileMap.getNeighbours(currentNode)) {
                if(neighbour.getIsFull() || closedSet.contains(neighbour)){
                    continue;
                }

                int newMovementCostToNeighbour = currentNode.gCost + getDistance(currentNode, neighbour);

                if(newMovementCostToNeighbour < neighbour.gCost || !openSet.contains(neighbour)){
                    neighbour.gCost = newMovementCostToNeighbour;
                    neighbour.hCost = getDistance(neighbour, targetPos);
                    neighbour.parent = currentNode;

                    if(!openSet.contains(neighbour))
                        openSet.add(neighbour);
                }
            }
        }
        System.out.println("No path found");
        return null;
    }

    private ArrayList<Tile> RetracePath(Tile startTile, Tile endTile) {
        ArrayList<Tile> path = new ArrayList<Tile>();
        Tile currentTile = endTile;

        while (currentTile != startTile) {
            path.add(currentTile);
            currentTile = currentTile.parent;
        }

        Collections.reverse(path);
        return path;
    }

    private int getDistance(Tile currentTile, Tile targetTile){
        int distanceX = Math.abs(currentTile.mapX - targetTile.mapX);
        int distanceY = Math.abs(currentTile.mapY - targetTile.mapY);

        if(distanceX > distanceY)
            return 14 * distanceY + 10 * (distanceX - distanceY);

        return 14 * distanceX + 10 * (distanceY - distanceX);
    }
}
