package com.mygdx.employees;

import com.mygdx.map.Tile;
import com.mygdx.map.TileMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * The path finding is build using A* algorithm. This allows the employees to find their way in the office
 * without going trough the furniture. A* allows us to find the shortest path for our employees, although we simplified
 * it a bit because we have animations only for four directions, so we do not allow diagonal movement.
 */

public class Pathfinding {

    private TileMap tileMap;

    /**
     * @param tileMap The tile map that we are in and where we calculate the paths.
     */
    public Pathfinding(TileMap tileMap){
        this.tileMap = tileMap;
    }

    /**
     * Building the path that is given to the employees.
     * @param startPos The tile that we start from
     * @param targetPos The tile where we want to find our way
     * @return List of the tiles that build the path, unless the path could not be build, in which case null is returned
     */

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

                    if(!openSet.contains(neighbour)) {
                        openSet.add(neighbour);
                    }
                }
            }
        }
        System.out.println("No path found");
        return null;
    }

    /*
     * When the algorithm finds the target tile, we make a list of all the parent tiles on which actually
     * builds our final path that is given to the employee.
     * @param startTile The starting position of the path
     * @param endTile The target tile of the path
     * @return List of tiles which make our path.
     */

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

    /*
     * Calculates the distance from current tile to the target tile so the shortest path can be built.
     * @param currentTile
     * @param targetTile
     * @return Distance from current tile to the target tile
     */

    private int getDistance(Tile currentTile, Tile targetTile){
        int distanceX = Math.abs(currentTile.mapX - targetTile.mapX);
        int distanceY = Math.abs(currentTile.mapY - targetTile.mapY);

        if(distanceX > distanceY)
            return 14 * distanceY + 10 * (distanceX - distanceY);

        return 14 * distanceX + 10 * (distanceY - distanceX);
    }
}
