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
        
        if(ok) 
        {
            ArrayList<String> asd = this.newTile.getTypeCoordinates().getTypes();
                
            System.out.println(asd.get(0)+asd.get(1)+asd.get(2)+asd.get(3)+asd.get(4));
            System.out.println(asd.get(5)+asd.get(6)+asd.get(7)+asd.get(8)+asd.get(9));
            System.out.println(asd.get(10)+asd.get(11)+asd.get(12)+asd.get(13)+asd.get(14));
            System.out.println(asd.get(15)+asd.get(16)+asd.get(17)+asd.get(18)+asd.get(19));
            System.out.println(asd.get(20)+asd.get(21)+asd.get(22)+asd.get(23)+asd.get(24));
            System.out.println("------Finished------");
            
            System.out.println();
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
        
        this.newTile.getTypeCoordinates().setTypes(rotateTileData());
        lista = this.newTile.getTypeCoordinates().getTypes();
        
        
        ArrayList<String> asd = lista;
        
        System.out.println(asd.get(0)+asd.get(1)+asd.get(2)+asd.get(3)+asd.get(4));
        System.out.println(asd.get(5)+asd.get(6)+asd.get(7)+asd.get(8)+asd.get(9));
        System.out.println(asd.get(10)+asd.get(11)+asd.get(12)+asd.get(13)+asd.get(14));
        System.out.println(asd.get(15)+asd.get(16)+asd.get(17)+asd.get(18)+asd.get(19));
        System.out.println(asd.get(20)+asd.get(21)+asd.get(22)+asd.get(23)+asd.get(24));
        System.out.println("-----"+"Rotated"+"-------");
        
        String side = "";
        
        for (Tile item : viereisetPalat) 
        {
            listaold = item.getTypeCoordinates().getTypes();
            
            //<editor-fold desc="If else hell">
            if (item.getPosX() > this.newTile.getPosX()) 
            {
                side = "Right";
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
                if (listaold.get(2).equals(lista.get(22))) 
                {
                    oikeinko.add(true);
                }
                else
                {
                    oikeinko.add(false);
                }
            }
            
            asd = listaold;
            
            System.out.println(asd.get(0)+asd.get(1)+asd.get(2)+asd.get(3)+asd.get(4));
            System.out.println(asd.get(5)+asd.get(6)+asd.get(7)+asd.get(8)+asd.get(9));
            System.out.println(asd.get(10)+asd.get(11)+asd.get(12)+asd.get(13)+asd.get(14));
            System.out.println(asd.get(15)+asd.get(16)+asd.get(17)+asd.get(18)+asd.get(19));
            System.out.println(asd.get(20)+asd.get(21)+asd.get(22)+asd.get(23)+asd.get(24));
            System.out.println("-----"+side+"-------");
            
            
            
            //</editor-fold>
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
    
    public ArrayList<String> rotateTileData()
    {
        
        ArrayList<String> vanhaLista = new ArrayList<String>();
        ArrayList<String> uusiLista = new ArrayList<String>();
        
        vanhaLista = this.newTile.getTypeCoordinates().getTypes();
        
        ArrayList<String> asd = vanhaLista;
        
        System.out.println(asd.get(0)+asd.get(1)+asd.get(2)+asd.get(3)+asd.get(4));
        System.out.println(asd.get(5)+asd.get(6)+asd.get(7)+asd.get(8)+asd.get(9));
        System.out.println(asd.get(10)+asd.get(11)+asd.get(12)+asd.get(13)+asd.get(14));
        System.out.println(asd.get(15)+asd.get(16)+asd.get(17)+asd.get(18)+asd.get(19));
        System.out.println(asd.get(20)+asd.get(21)+asd.get(22)+asd.get(23)+asd.get(24));
        System.out.println("------"+this.newTile.getRotation()+"------");
        
        String[][] listaMatriisi = {
                            {vanhaLista.get(0), vanhaLista.get(1), vanhaLista.get(2),vanhaLista.get(3),vanhaLista.get(4)},
                            {vanhaLista.get(5),vanhaLista.get(6),vanhaLista.get(7),vanhaLista.get(8),vanhaLista.get(9)},
                            {vanhaLista.get(10),vanhaLista.get(11),vanhaLista.get(12),vanhaLista.get(13),vanhaLista.get(14)},
                            {vanhaLista.get(15),vanhaLista.get(16),vanhaLista.get(17),vanhaLista.get(18),vanhaLista.get(19)},
                            {vanhaLista.get(20),vanhaLista.get(21),vanhaLista.get(22),vanhaLista.get(23),vanhaLista.get(24)}
                        };

        switch(this.newTile.getRotation())
        {
            case 0:
                return vanhaLista;
            case 90: 
                listaMatriisi = rotateMatrixRight(listaMatriisi);
                uusiLista = convertMatricetoList(listaMatriisi);
                return uusiLista;
            case 180:
                listaMatriisi = rotateMatrixRight(listaMatriisi);
                listaMatriisi = rotateMatrixRight(listaMatriisi);
                uusiLista = convertMatricetoList(listaMatriisi);
                return uusiLista;
            case 270:
                listaMatriisi = rotateMatrixRight(listaMatriisi);
                listaMatriisi = rotateMatrixRight(listaMatriisi);
                listaMatriisi = rotateMatrixRight(listaMatriisi);
                uusiLista = convertMatricetoList(listaMatriisi);
                return uusiLista;
            default:
                break;
        }
        return vanhaLista;
    }
    
    public ArrayList<String> convertMatricetoList(String[][] matrice)
    {
        ArrayList<String> converted = new ArrayList<String>();
        
        for (String[] rivi : matrice)
        {
            for (String alkio : rivi)
            {
                converted.add(alkio);
            }
        }
        
        return converted;
    }
    
    public String[][] rotateMatrixRight(String[][] matrix)
    {
        /* W and H are already swapped */
        int w = matrix.length;
        int h = matrix[0].length;
        String[][] ret = new String[h][w];
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                ret[i][j] = matrix[w - j - 1][i];
            }
        }
        return ret;
    }
}
