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

    private ArrayList<int[]> grass, city, road, block, church, wall;

    public TileData()
    {
        grass=new ArrayList();
        city=new ArrayList();
        road=new ArrayList();
        block=new ArrayList();
        church=new ArrayList();
        wall=new ArrayList();
    }

    public ArrayList<int[]> getGrass()
    {
        return grass;
    }

    public void setGrass(ArrayList<int[]> crass)
    {
        this.grass = grass;
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

    public ArrayList<int[]> getBlock()
    {
        return block;
    }

    public void setBlock(ArrayList<int[]> block)
    {
        this.block = block;
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

    public void addGrassCoordinates(int[] grass)
    {
        this.grass.add(grass);
    }

    public void addCityCoordinates(int[] city)
    {
        this.city.add(city);
    }

    public void addRoadCoordinates(int[] road)
    {
        this.road.add(road);
    }
    
    public void addBlockCoordinates(int[] block) {
        this.block.add(block);
    }

    public void addChurchCoordinates(int[] church)
    {
        this.church.add(church);
    }
}
