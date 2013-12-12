/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carcassonne;

/**
 *
 * @author Tupi, Aleksi
 */
public class Tile implements Comparable<Tile>
{
    private int id,posX,posY,rotation;
    private char[][] typeMatrix,workerMatrix;
    private TileData workerPositions, typeCoordinates;

    public Tile(int id, int posX, int posY, char[][] workerMatrix, char[][] typeMatrix)
    {
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.rotation=0;
        this.typeMatrix = typeMatrix;
        this.workerMatrix = workerMatrix;
        
    }

    public char[][] getTypeMatrix() {
        return typeMatrix;
    }

    public void setTypeMatrix(char[][] typeMatrix) {
        this.typeMatrix = typeMatrix;
    }

    public char[][] getWorkerMatrix() {
        return workerMatrix;
    }

    public void setWorkerMatrix(char[][] workerMatrix) {
        this.workerMatrix = workerMatrix;
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

    @Override
    public int compareTo(Tile o)
    {
        int lastCmp =Integer.compare(posY, o.posY);
        return (lastCmp != 0 ? lastCmp : Integer.compare(posX, o.posX));
    }
     
    public void rotateTileData()
    {
        int rot=0;
        
        switch(this.rotation)
        {
            case 90: 
                rot=1;
                 break;
            case 180:
                rot=2;
                 break;
            case 270:
                rot=3;
                 break;
            default:
                break;
        }
        System.out.println("Käännetään palaa : "+rotation);

        
        
        for(int k=0;k<rot;++k)
        {
            this.typeMatrix=this.rotateMatrix(this.typeMatrix);
            this.workerMatrix=this.rotateMatrix(this.workerMatrix);
            System.out.println("käännös : "+k);
            
        }


        
    }
    private char[][] rotateMatrix(char[][] matrix)
    {
        char[][] ret = new char[5][5];
        String side="";
            for (int i = 0; i < 5;++i) {
                for (int j = 0; j < 5; ++j) {
                ret[i][j] = matrix[5 - j - 1][i];
                side+=ret[i][j];
                }
                System.out.println(side);
                side="";
            }
            return ret;
    }
}
