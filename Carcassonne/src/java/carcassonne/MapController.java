/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carcassonne;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 *
 * @author Tupi, Ville
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
    private Random rnd = new Random(System.currentTimeMillis());
    
    public MapController()
    {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        String realPath = ctx.getRealPath("/data/tileData.xml");
        allTiles = XmlParser.parseXml(realPath);
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
        if(newTile != null) {
            newTile = null;
        }
        int newId = rnd.nextInt(24);
        newTile = new Tile(newId,0,0,this.allTiles.get(newId).getWorkerPositions(),this.allTiles.get(newId).getTypeCoordinates());
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
        if (compareNeighbours()) 
        {
            ok = true;
        }
        else
        {
            ok = false;
        }
        
        
        if(ok) {
            System.out.println("Uusi palikka lisätty "+this.newTile.getPosX()+", "+this.newTile.getPosY()+" ,"+this.newTile.getRotation());
            this.tilesInGame.add(newTile);
            Collections.sort(tilesInGame);
        }
    }

    public Tile getNewTile() {
        return newTile;
    }

    public void setNewTile(Tile newTile) {
        this.newTile = newTile;
    }
    
    public void rotateCW()
    {
        int rot = getNewTileRotation();
        rot =+ 90;
        setNewTileRotation(rot);
    }
    
    public void rotateCCW()
    {
        int rot = getNewTileRotation();
        rot =- 90;
        setNewTileRotation(rot);
    }
    
    public Boolean compareNeighbours()
    {
        ArrayList<String> lista = new ArrayList<String>();
        ArrayList<String> listaold = new ArrayList<String>();
        ArrayList<Boolean> oikeinko = new ArrayList<Boolean>();
        Boolean ok = true;
        
        TileData oldTileData = new TileData();
        TileData newTileData = new TileData();
        newTileData = this.newTile.getTypeCoordinates();
        lista = newTileData.getTypes();
        
        String side = "";
        
        for (Tile item : viereisetPalat) 
        {
            oldTileData = item.getTypeCoordinates();
            listaold = oldTileData.getTypes();
            
            switch(this.newTile.getRotation())
            {

                case 0:
                    //<editor-fold desc="Rot 0">
                    if (item.getPosX() > this.newTile.getPosX()) 
                    {
                        side = "Right";
                        System.out.println(this.newTile.getRotation()+" "+side+" "+listaold.get(10)+"|"+lista.get(14));
                        if (listaold.get(10).equals(lista.get(14))) 
                        {
                            oikeinko.add(true);
                        }
                        else
                        {
                            oikeinko.add(false);
                        }
                    }
                    if (item.getPosX() < this.newTile.getPosX()) 
                    {
                        side = "Left";
                        System.out.println(this.newTile.getRotation()+" "+side+" "+listaold.get(14)+"|"+lista.get(10));
                        if (listaold.get(14).equals(lista.get(10))) 
                        {
                            oikeinko.add(true);
                        }
                        else
                        {
                            oikeinko.add(false);
                        }
                    }
                    if (item.getPosY() < this.newTile.getPosY()) 
                    {
                        side = "Top"; 
                        System.out.println(this.newTile.getRotation()+" "+side+" "+listaold.get(22)+"|"+lista.get(2));
                        if (listaold.get(22).equals(lista.get(2))) 
                        {
                            oikeinko.add(true);
                        }
                        else
                        {
                            oikeinko.add(false);
                        }
                    }
                    if (item.getPosY() > this.newTile.getPosY()) 
                    {
                        side = "Bottom";
                        System.out.println(this.newTile.getRotation()+" "+side+" "+listaold.get(2)+"|"+lista.get(22));
                        if (listaold.get(2).equals(lista.get(22))) 
                        {
                            oikeinko.add(true);
                        }
                        else
                        {
                            oikeinko.add(false);
                        }
                    }
                    //</editor-fold>
                    break;
                case 90:
                    //<editor-fold desc="Rot 90">
                    if (item.getPosX() > this.newTile.getPosX()) 
                    {
                        side = "Right";
                        System.out.println(this.newTile.getRotation()+" "+side+" "+listaold.get(10)+"|"+lista.get(2));
                        if (listaold.get(10).equals(lista.get(2))) 
                        {
                            oikeinko.add(true);
                        }
                        else
                        {
                            oikeinko.add(false);
                        }
                    }
                    if (item.getPosX() < this.newTile.getPosX()) 
                    {
                        side = "Left";
                        System.out.println(this.newTile.getRotation()+" "+side+" "+listaold.get(14)+"|"+lista.get(22));
                        if (listaold.get(14).equals(lista.get(22))) 
                        {
                            oikeinko.add(true);
                        }
                        else
                        {
                            oikeinko.add(false);
                        }
                    }
                    if (item.getPosY() < this.newTile.getPosY()) 
                    {
                        side = "Top"; 
                        System.out.println(this.newTile.getRotation()+" "+side+" "+listaold.get(22)+"|"+lista.get(10));
                        if (listaold.get(22).equals(lista.get(10))) 
                        {
                            oikeinko.add(true);
                        }
                        else
                        {
                            oikeinko.add(false);
                        }
                    }
                    if (item.getPosY() > this.newTile.getPosY()) 
                    {
                        side = "Bottom";
                        System.out.println(this.newTile.getRotation()+" "+side+" "+listaold.get(2)+"|"+lista.get(14));
                        if (listaold.get(2).equals(lista.get(14))) 
                        {
                            oikeinko.add(true);
                        }
                        else
                        {
                            oikeinko.add(false);
                        }
                    }
                    //</editor-fold>
                    break;
                case 180:
                    //<editor-fold desc="Rot 180">
                    if (item.getPosX() > this.newTile.getPosX()) 
                    {
                        side = "Right";
                        System.out.println(this.newTile.getRotation()+" "+side+" "+listaold.get(10)+"|"+lista.get(10));
                        if (listaold.get(10).equals(lista.get(10))) 
                        {
                            oikeinko.add(true);
                        }
                        else
                        {
                            oikeinko.add(false);
                        }
                    }
                    if (item.getPosX() < this.newTile.getPosX()) 
                    {
                        side = "Left";
                        System.out.println(this.newTile.getRotation()+" "+side+" "+listaold.get(14)+"|"+lista.get(14));
                        if (listaold.get(14).equals(lista.get(14))) 
                        {
                            oikeinko.add(true);
                        }
                        else
                        {
                            oikeinko.add(false);
                        }
                    }
                    if (item.getPosY() < this.newTile.getPosY()) 
                    {
                        side = "Top"; 
                        System.out.println(this.newTile.getRotation()+" "+side+" "+listaold.get(22)+"|"+lista.get(22));
                        if (listaold.get(22).equals(lista.get(22))) 
                        {
                            oikeinko.add(true);
                        }
                        else
                        {
                            oikeinko.add(false);
                        }
                    }
                    if (item.getPosY() > this.newTile.getPosY()) 
                    {
                        side = "Bottom";
                        System.out.println(this.newTile.getRotation()+" "+side+" "+listaold.get(2)+"|"+lista.get(2));
                        if (listaold.get(2).equals(lista.get(2))) 
                        {
                            oikeinko.add(true);
                        }
                        else
                        {
                            oikeinko.add(false);
                        }
                    }
                    //</editor-fold>
                    break;
                case 270:
                    //<editor-fold desc="Rot 270">
                    if (item.getPosX() > this.newTile.getPosX()) 
                    {
                        side = "Right";
                        System.out.println(this.newTile.getRotation()+" "+side+" "+listaold.get(10)+"|"+lista.get(22));
                        if (listaold.get(10).equals(lista.get(22))) 
                        {
                            oikeinko.add(true);
                        }
                        else
                        {
                            oikeinko.add(false);
                        }
                    }
                    if (item.getPosX() < this.newTile.getPosX()) 
                    {
                        side = "Left";
                        System.out.println(this.newTile.getRotation()+" "+side+" "+listaold.get(14)+"|"+lista.get(2));
                        if (listaold.get(14).equals(lista.get(2))) 
                        {
                            oikeinko.add(true);
                        }
                        else
                        {
                            oikeinko.add(false);
                        }
                    }
                    if (item.getPosY() < this.newTile.getPosY()) 
                    {
                        side = "Top"; 
                        System.out.println(this.newTile.getRotation()+" "+side+" "+listaold.get(22)+"|"+lista.get(14));
                        if (listaold.get(22).equals(lista.get(14))) 
                        {
                            oikeinko.add(true);
                        }
                        else
                        {
                            oikeinko.add(false);
                        }
                    }
                    if (item.getPosY() > this.newTile.getPosY()) 
                    {
                        side = "Bottom";
                        System.out.println(this.newTile.getRotation()+" "+side+" "+listaold.get(2)+"|"+lista.get(10));
                        if (listaold.get(2).equals(lista.get(10))) 
                        {
                            oikeinko.add(true);
                        }
                        else
                        {
                            oikeinko.add(false);
                        }
                    }
                    //</editor-fold>
                    break;
                default: break;
            }
            System.out.println(this.newTile.getRotation());
        }
        
        for (Boolean item : oikeinko) 
        {
            if (item == false) {
                ok = false;
            }
        }
        oikeinko.clear();
        
        return ok;
    }
}
