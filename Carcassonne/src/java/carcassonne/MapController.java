/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carcassonne;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
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
    private ArrayList<Tile> tilesOnGame;
    public MapController()
    {
        allTiles =new ArrayList();
        tilesOnGame =new ArrayList();
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
        Tile newTile=new Tile(id ,x,y,t.getWorkerPositions(),t.getTypeCoordinates());
        
        tilesOnGame.add(newTile);
    }

    public ArrayList<Tile> getTilesOnGame()
    {
        return tilesOnGame;
    }

    public void setTilesOnGame(ArrayList<Tile> tilesOnGame)
    {
        this.tilesOnGame = tilesOnGame;
    }
}
