/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carcassonne;

import java.util.TreeMap;


/**
 *
 * @author Tupi
 */
public class Tile
{
    private int id,posX,posY,rotation;
    private TileData workerPositions, typeCoordinates;

    public Tile(int id, int posX, int posY, TileData workerPositions, TileData typeCoordinates)
    {
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.rotation=0;
        this.workerPositions = workerPositions;
        this.typeCoordinates = typeCoordinates;
        
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getPosX()
    {
        return posX;
    }

    public void setPosX(int posX)
    {
        this.posX = posX;
    }

    public int getPosY()
    {
        return posY;
    }

    public void setPosY(int posY)
    {
        this.posY = posY;
    }

    public int getRotation()
    {
        return rotation;
    }

    public void setRotation(int rotation)
    {
        this.rotation = rotation;
    }

    public TileData getWorkerPositions()
    {
        return workerPositions;
    }

    public void setWorkerPositions(TileData workerPositions)
    {
        this.workerPositions = workerPositions;
    }

    public TileData getTypeCoordinates()
    {
        return typeCoordinates;
    }

    public void setTypeCoordinates(TileData typeCoordinates)
    {
        this.typeCoordinates = typeCoordinates;
    }
            
}
