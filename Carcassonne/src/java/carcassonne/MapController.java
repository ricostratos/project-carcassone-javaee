/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carcassonne;

import com.google.common.collect.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

/**
 *
 * @author Tupi
 */
@ManagedBean(name = "MapController")
@SessionScoped
public class MapController
{
    private ArrayList<Tile> allTiles;
    private ArrayList<Tile> tilesInGame;
    private int boardWidth,boardHeight;
    private String gameBoardTest;
    private int tileMinX=0,tileMaxX=0,tileMinY=0,tileMaxY=0;
    
    public MapController()
    {
        allTiles =new ArrayList();
        tilesInGame =new ArrayList();
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        String realPath = ctx.getRealPath("/data/tileData.xml");
        allTiles = XmlParser.parseXml(realPath);
        
        // Testidataa....
        //           id x y
        createNewTile(3,0,0);
        createNewTile(3,1,1);
        createNewTile(2,0,1);
        createNewTile(1,1,0);
        createNewTile(7,-1,0);
        createNewTile(5,-1,-1);
        createNewTile(5,-1,-2);
        createNewTile(5,-1,-3);
        createNewTile(5,-1,-4);
        createNewTile(5,-1,-5);
        createNewTile(5,-1,-6);
        createNewTile(5,-1,-7);
        createNewTile(5,-1,-8);
        createNewTile(5,-1,-9);
        createNewTile(5,-1,-10);
        createNewTile(5,-2,-1);
        createNewTile(5,-3,-1);
        createNewTile(5,-4,-1);
        createNewTile(5,-5,-1);
        createNewTile(5,-6,-1);
        createNewTile(5,-7,-1);
        createNewTile(5,-8,-1);
        createNewTile(5,-9,-1);
        createNewTile(5,-10,-1);
        createNewTile(5,-11,-1);
        createNewTile(5,-12,-1);
        Collections.sort(tilesInGame);
    }
    
    public ArrayList<Tile> getAllTiles()
    {

        return allTiles;
    }

    public void setAllTiles(ArrayList<Tile> allTiles)
    {
        this.allTiles = allTiles;
    }

    private void createNewTile(int id,int x,int y)
    {
        Tile t=this.allTiles.get(id);
        Tile newTile=new Tile(id,x,y,t.getWorkerPositions(),t.getTypeCoordinates());
        
        tilesInGame.add(newTile);
        Collections.sort(tilesInGame);
    }

    public ArrayList<Tile> getTilesOnGame()
    {
        return tilesInGame;
    }

    public void setTilesOnGame(ArrayList<Tile> tilesOnGame)
    {
        this.tilesInGame = tilesOnGame;
    }
    
    public void prepareGameBoard() {
        for(int i=0; i<this.tilesInGame.size(); i++) {
            if(this.tilesInGame.get(i).getPosX() < tileMinX){
                tileMinX = this.tilesInGame.get(i).getPosX();
            }
            if(this.tilesInGame.get(i).getPosX() > tileMaxX){
                tileMaxX = this.tilesInGame.get(i).getPosX();
            }
            if(this.tilesInGame.get(i).getPosY() < tileMinY){
                tileMinY = this.tilesInGame.get(i).getPosY();
            }
            if(this.tilesInGame.get(i).getPosY() > tileMaxY){
                tileMaxY = this.tilesInGame.get(i).getPosY();
            }
        }
        boardWidth = Math.abs(tileMinX) + tileMaxX + 1;
        boardHeight = Math.abs(tileMinY) + tileMaxY + 1;
    }
    
    public String printGameBoardTest() {
        int indexCheck=0;
        boolean tileCheck = false, addImage = false;
        this.gameBoardTest = "<table id='gameBoardTable'>";
        for(int i=-1; i<this.boardHeight+1;i++){
            this.gameBoardTest = this.gameBoardTest + "<tr id='gameBoardRow"+(this.tileMinY+i)+"'>";
            for(int j=-3; j<this.boardWidth+3;j++) {
                this.gameBoardTest = this.gameBoardTest + "<td id='"+(this.tileMinX+j)+"_"+(this.tileMinY+i)+"' class='";
                
                if(this.tilesInGame.get(indexCheck).getPosX() == (j+tileMinX) && this.tilesInGame.get(indexCheck).getPosY() == (i+tileMinY)) {
                    tileCheck = true;
                }
                
                for(int q=0; q<this.tilesInGame.size(); q++) {
                    try {
                    if(
                            ((this.tilesInGame.get(q).getPosX() == (j+tileMinX-1) && this.tilesInGame.get(q).getPosY() == (i+tileMinY)) ||
                            (this.tilesInGame.get(q).getPosX() == (j+tileMinX) && this.tilesInGame.get(q).getPosY() == (i+tileMinY+1)) ||
                            (this.tilesInGame.get(q).getPosX() == (j+tileMinX+1) && this.tilesInGame.get(q).getPosY() == (i+tileMinY)) ||
                            (this.tilesInGame.get(q).getPosX() == (j+tileMinX) && this.tilesInGame.get(q).getPosY() == (i+tileMinY-1))) &&
                            !tileCheck
                      ) {
                        this.gameBoardTest = this.gameBoardTest + "newTilePlaceHolder ";
                        addImage = true;
                        break;
                    }
                    } catch(Exception lollero) {}
                }
                
                this.gameBoardTest = this.gameBoardTest + "tileHolderTD'>";
                
                if(tileCheck) {
                    this.gameBoardTest = this.gameBoardTest + "<img src='data/tiles/0.png' alt='loading' />";
                    if(indexCheck < this.tilesInGame.size()-1) {
                        indexCheck++;
                    }
                    tileCheck = false;
                } else if(addImage) {
                    this.gameBoardTest = this.gameBoardTest + "<img src='data/tiles/newTilePlaceHolder.png' alt='loading' />";
                    addImage = false;
                }// else {this.gameBoardTest = this.gameBoardTest + (j+tileMinX)+"_"+(i+tileMinY);}
                this.gameBoardTest = this.gameBoardTest + "</td>";
            }
            this.gameBoardTest = this.gameBoardTest + "</tr>";
        }
        this.gameBoardTest = this.gameBoardTest + "</table>";
        
        return this.gameBoardTest;
    }
    
    public int getGameBoardWidth() {
        return this.boardWidth;
    }
}
