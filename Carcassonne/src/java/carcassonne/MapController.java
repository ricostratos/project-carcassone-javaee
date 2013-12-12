/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carcassonne;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 *
 * @author Tupi, Ville, Aleksi
 */
@ManagedBean(name = "MapController")
@SessionScoped
public class MapController
{
    private ArrayList<Tile> allTiles=new ArrayList();
    private ArrayList<Tile> viereisetPalat = new ArrayList();
    private ArrayList<Tile> tilesInGame=new ArrayList();
    private int boardWidth,boardHeight;
    private String printGameBoard;
    private int tileMinX=0,tileMaxX=0,tileMinY=0,tileMaxY=0;
    
    private Tile newTile;
    private boolean createTile = true;
    ArrayList<Integer> arl = new ArrayList(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23));
    
    public MapController(){
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        String realPath = ctx.getRealPath("/data/tileData.xml");
        allTiles = XmlParser.parseXml(realPath);
    }
    public ArrayList<Tile> getAllTiles(){

        return allTiles;
    }
    public void setAllTiles(ArrayList<Tile> allTiles){
        this.allTiles = allTiles;
    }
    public ArrayList<Tile> getTilesOnGame(){
        return tilesInGame;
    }
    public void setTilesOnGame(ArrayList<Tile> tilesOnGame){
        this.tilesInGame = tilesOnGame;
    }
    public void prepareGameBoard() {
        if(this.tilesInGame != null && this.tilesInGame.size() > 0) {
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
        }
        boardWidth = Math.abs(tileMinX) + tileMaxX + 1;
        boardHeight = Math.abs(tileMinY) + tileMaxY + 1;
    }
    public String printGameBoardTest() {
        int indexCheck=0;
        boolean tileCheck = false, addImage = false;
        this.printGameBoard = "<table id='gameBoardTable'>";
        
        if(this.tilesInGame.size() > 0) {
            for(int i=-1; i<this.boardHeight+1;i++){
                this.printGameBoard = this.printGameBoard + "<tr id='gameBoardRow"+(this.tileMinY+i)+"'>";
                for(int j=-1; j<this.boardWidth+1;j++) {
                    this.printGameBoard = this.printGameBoard + "<td id='"+(this.tileMinX+j)+"_"+(this.tileMinY+i)+"' class='";

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
                            this.printGameBoard = this.printGameBoard + "newTilePlaceHolder ";
                            addImage = true;
                            break;
                        }
                        } catch(Exception lollero) {}
                    }

                    this.printGameBoard = this.printGameBoard + "tileHolderTD";
                    if(tileCheck) {this.printGameBoard = this.printGameBoard + this.tilesInGame.get(indexCheck).getRotation();}
                    this.printGameBoard = this.printGameBoard + "'>";

                    if(tileCheck) {
                        this.printGameBoard = this.printGameBoard + "<img src='data/tiles/"+this.tilesInGame.get(indexCheck).getId()+".png' alt='loading' />";
                        if(indexCheck < this.tilesInGame.size()-1) {
                            indexCheck++;
                        }
                        tileCheck = false;
                    } else if(addImage) {
                        this.printGameBoard = this.printGameBoard + "<img src='data/tiles/newTilePlaceHolder.png' alt='loading' />";
                        addImage = false;
                    }// else {this.printGameBoard = this.printGameBoard + (j+tileMinX)+"_"+(i+tileMinY);}
                    this.printGameBoard = this.printGameBoard + "</td>";
                }
                this.printGameBoard = this.printGameBoard + "</tr>";
            }
        } else {
            this.printGameBoard = this.printGameBoard + "<tr id='gameBoardRow0'><td id='0_0' class='newTilePlaceHolder tileHolderTD'><img src='data/tiles/newTilePlaceHolder.png' alt='loading' /></td></tr>";
        }
        this.printGameBoard = this.printGameBoard + "</table>";
        
        return this.printGameBoard;
    }
    /*
    *   AJAX -funktiot
    */
    public void createNewTile() {
        int newId = getNewRandomInt();
        System.out.print("Iitee "+newId+" pituus "+tilesInGame.isEmpty());
        
        if (!tilesInGame.isEmpty() && newId != 100) {
            if (createTile) {
                newTile = null;
                System.out.print("Uusi laatta"+newId);
                newTile = new Tile(newId,0,0,this.allTiles.get(newId).getWorkerMatrix(),this.allTiles.get(newId).getTypeMatrix());
            }
            else {
                int newid = this.newTile.getId();
                newTile = null;

                newTile = new Tile(newid,0,0,this.allTiles.get(newid).getWorkerMatrix(),this.allTiles.get(newid).getTypeMatrix());
            }   
        } 
        else if(newId == 100){
            newTile = null;
            System.out.print("Peliloppu"+newId);
        }
        else{
            newTile = null;
            System.out.print("Aloitus"+newId);
            newId = 10;
            newTile = new Tile(newId,0,0,this.allTiles.get(newId).getWorkerMatrix(),this.allTiles.get(newId).getTypeMatrix());
        }
    }
    public void setNewTileX(int newTileX) {
        this.newTile.setPosX(newTileX);
    }
    public void setNewTileY(int newTileY) {
        this.newTile.setPosY(newTileY);
    }
    public void setNewTileRotation(int newTileRotation) {
        this.newTile.setRotation(newTileRotation);
    }
    public int getNewTileX() {
        if(newTile != null) {
            return this.newTile.getPosX();
        } else {return 0;}
    }
    public int getNewTileY() {
        if(newTile != null) {
            return this.newTile.getPosY();
        } else {return 0;}
    }
    public int getNewTileRotation() {
        if(newTile != null) {
            return this.newTile.getRotation();
        } else {return 0;}
    }
    public void addNewTileToGameBoard() {
        
        boolean ok = true;
        viereisetPalat.clear();
        
        for(int i=0; i<this.tilesInGame.size(); i++) {
            if(this.tilesInGame.get(i).getPosX() == this.newTile.getPosX() && this.tilesInGame.get(i).getPosY() == this.newTile.getPosY()) {
                ok = false;
            }
        }
        //Haetaan kaikki uuden palikan viereiset palikat.
        for (Tile item : this.tilesInGame) 
        {
            if ( (this.newTile.getPosX() == (item.getPosX()+1) || this.newTile.getPosX() == (item.getPosX()-1) || this.newTile.getPosX() == item.getPosX()) &&
                    (this.newTile.getPosY() == (item.getPosY()+1) || this.newTile.getPosY() ==(item.getPosY()-1) ||this.newTile.getPosY() ==item.getPosY() ) && 
                    (this.newTile.getPosX() == item.getPosX() || this.newTile.getPosY() == item.getPosY())) 
            {
                viereisetPalat.add(item);
            }
        }
        
        if(compareNeighbours() && ok) 
        {
            this.tilesInGame.add(newTile);
            Collections.sort(tilesInGame);
            createTile = true;
        }
        else {
            createTile = false; 
        }
          String side="";
            System.out.println("uusi pala");
            for (int i = 0; i < 5; ++i) {
                for (int j = 0; j < 5; ++j) {
                side+=this.newTile.getTypeMatrix()[i][j];
                }
                System.out.println(side);
                side="";
            }
    }
    public Tile getNewTile() {
        return newTile;
    }
    public void setNewTile(Tile newTile) {
        this.newTile = newTile;
    }
    public void rotateCW(){
        int rot = this.newTile.getRotation();
        rot += 90;
        if(rot >270)
        {
            rot=0;
        }
        //setNewTileRotation(rot);
        this.newTile.setRotation(rot);
    }
    public void rotateCCW(){
        int rot = this.newTile.getRotation();
        rot -= 90;
        if(rot < 0)
        {
            rot=270;
        }
        this.newTile.setRotation(rot);
    }
    public Boolean compareNeighbours(){
        boolean palaKay=true;
      char[][] viereisetTyypit;
        
        this.newTile.rotateTileData();

        String side = "";
        
        for (Tile item : viereisetPalat) 
        {
            viereisetTyypit = item.getTypeMatrix();
            
            //<editor-fold desc="If else hell">
            if (item.getPosX() < this.newTile.getPosX()) 
            {
                side = "uusi pala viereisen palan oikealle puolelle";
                if (viereisetTyypit[2][4] != this.newTile.getTypeMatrix()[2][0] ) 
                {
                    palaKay=false;
                }
                
            }
            else if (item.getPosX() > this.newTile.getPosX()) 
            {
                side = "uusi pala viereisen palan vasemmalle puolelle";
               if (viereisetTyypit[2][0] != this.newTile.getTypeMatrix()[2][4] ) 
                {
                    palaKay=false;
                }
            }
            else if (item.getPosY() > this.newTile.getPosY()) 
            {
                side = "uusi pala viereisen palan ylä puolelle"; 
                if (viereisetTyypit[0][2] != this.newTile.getTypeMatrix()[4][2] ) 
                {
                    palaKay=false;
                }
            }
            else if (item.getPosY() < this.newTile.getPosY())
            {
                side = "uusi pala viereisen palan ala puolelle";
               if (viereisetTyypit[4][2] != this.newTile.getTypeMatrix()[0][2] ) 
                {
                  palaKay=false;
                }
            }
            if(!palaKay)
            {
            System.out.println(side);
            side="";
            System.out.println("viereinen pala");
            for (int i = 0; i < 5; ++i) {
                for (int j = 0; j < 5; ++j) {
                side+=viereisetTyypit[i][j];
                }
                System.out.println(side);
                side="";
            }
            
            }
            //</editor-fold>
        }

        return palaKay;
    }
    public int getNewRandomInt(){
        Random rnd = new Random();
        long seed = System.nanoTime();
        Collections.shuffle(arl, new Random(seed));
        int i = 0;
        
        try{
            i = arl.get(0);
            arl.remove(0);
        }
        catch(Exception e){
            i = 100;
        }

        return i;
    }
}
