/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carcassonne;

import java.util.ArrayList;

/**
 *
 * @author Tupi
 */
public class TileData
{

    private ArrayList<int[]> crass, city, road, church, wall;

    public TileData()
    {
        crass=new ArrayList();
        city=new ArrayList();
        road=new ArrayList();
        church=new ArrayList();
        wall=new ArrayList();
    }

    public ArrayList<int[]> getCrass()
    {
        return crass;
    }

    public void setCrass(ArrayList<int[]> crass)
    {
        this.crass = crass;
    }

    public ArrayList<int[]> getCity()
    {
        return city;
    }

    public void setCity(ArrayList<int[]> city)
    {
        this.city = city;
    }

    public ArrayList<int[]> getRoad()
    {
        return road;
    }

    public void setRoad(ArrayList<int[]> road)
    {
        this.road = road;
    }

    public ArrayList<int[]> getChurch()
    {
        return church;
    }

    public void setChurch(ArrayList<int[]> church)
    {
        this.church = church;
    }

    public ArrayList<int[]> getWall()
    {
        return wall;
    }

    public void setWall(ArrayList<int[]> wall)
    {
        this.wall = wall;
    }

    public void addWallCoordinates(int[] wall)
    {
        this.wall.add(wall);
    }

    public void addCrassCoordinates(int[] crass)
    {
        this.crass.add(crass);
    }

    public void addCityCoordinates(int[] city)
    {
        this.city.add(city);
    }

    public void addRoadCoordinates(int[] road)
    {
        this.road.add(road);
    }

    public void addChurchCoordinates(int[] church)
    {
        this.church.add(church);
    }
}
