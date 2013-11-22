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
        boardWidth = Math.abs(tileMinX) + tileMaxX + 1; // 3 tulee keskimmäisestä paikasta 0_0 ja reunimmaisista, joihin luodaan paikat uusille laatoille
        boardHeight = Math.abs(tileMinY) + tileMaxY + 1;
        
        /*
        int cellCounter=0,currentRow=0;
        for(int i=0; i<this.tilesInGame.size(); i++) {
            
            
            
            cellCounter++;
            if(cellCounter >= this.boardWidth) {
                cellCounter=0;
                currentRow++;
            }
        }*/
    }
    
    public String printGameBoardTest() {
        int indexCheck=0;
        boolean tileCheck = false;
        this.gameBoardTest = "<table id='gameBoardTable'>";
        for(int i=-2; i<this.boardHeight;i++){
            this.gameBoardTest = this.gameBoardTest + "<tr id='gameBoardRow"+(this.tileMinY+i)+"'>";
            for(int j=-2; j<this.boardWidth;j++) {
                this.gameBoardTest = this.gameBoardTest + "<td id='"+(this.tileMinX+j)+"_"+(this.tileMinY+i)+"' class='";
                
                if(this.tilesInGame.get(indexCheck).getPosX() == (j+tileMinX+1) && this.tilesInGame.get(indexCheck).getPosY() == (i+tileMinY+1)) {
                    tileCheck = true;
                }
                
                for(int q=0; q<this.tilesInGame.size(); q++) {
                    try {
                    if(
                            ((this.tilesInGame.get(q).getPosX() == (j+tileMinX) && this.tilesInGame.get(q).getPosY() == (i+tileMinY+1)) ||
                            (this.tilesInGame.get(q).getPosX() == (j+tileMinX+1) && this.tilesInGame.get(q).getPosY() == (i+tileMinY+2)) ||
                            (this.tilesInGame.get(q).getPosX() == (j+tileMinX+2) && this.tilesInGame.get(q).getPosY() == (i+tileMinY+1)) ||
                            (this.tilesInGame.get(q).getPosX() == (j+tileMinX+1) && this.tilesInGame.get(q).getPosY() == (i+tileMinY))) &&
                            !tileCheck
                      ) {
                        this.gameBoardTest = this.gameBoardTest + "newTilePlaceHolder ";
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
                }
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
