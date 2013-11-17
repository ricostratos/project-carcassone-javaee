/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carcassonne;

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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

    public MapController()
    {
        allTiles =new ArrayList();
    }

    public ArrayList<Tile> getAllTiles()
    {

        return allTiles;
    }

    public void setAllTiles(ArrayList<Tile> allTiles)
    {
        this.allTiles = allTiles;
    }

    public String readXml()
    {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        String realPath = ctx.getRealPath("/data/tileData.xml");
        allTiles = XmlParser.parseXml(realPath);
        return null;
    }
}
