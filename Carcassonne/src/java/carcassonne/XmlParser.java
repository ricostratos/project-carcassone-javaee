/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carcassonne;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Tupi
 */
public class XmlParser
{

    public static ArrayList<Tile> parseXml(String filepath)
    {
        ArrayList<Tile> tiles=new ArrayList();
        try
        {

            File fXmlFile = new File(filepath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            
            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Tile");


            for (int i = 0; i < nList.getLength(); i++)
            {

                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE)
                {

                    Element eElement = (Element) nNode;
                    int id =Integer.parseInt(eElement.getAttribute("id"));
                    String typeCoordinates = eElement.getElementsByTagName("TypeCoordinates").item(0).getTextContent();
                    String workerPosition = eElement.getElementsByTagName("WorkerPosition").item(0).getTextContent();
                    TileData wPositions = parseTileData(workerPosition);
                    TileData tCoordianates =parseTileData(typeCoordinates);
                    Tile t=new Tile(id,0,0,wPositions,tCoordianates);
                    tiles.add(t);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return tiles;
    }

    private static TileData parseTileData(String str)
    {
        TileData tiledata = new TileData();
        String[] sArray = str.split(",");
        for (int j = 0; j < sArray.length; j++)
        {
            String line = sArray[j];
            for (int k = 0; k < line.length(); k++)
            {
                int[] coords = new int[2];
                coords[0] = k;
                coords[1] = j;
                /*
                 * - peltoa
                 * m muuri
                 * l linna
                 * t tie
                 * k kirkko
                 */
                switch (line.charAt(k))
                {
                    case '-':
                        tiledata.addGrassCoordinates(coords);
                        break;
                    case 'm':
                        tiledata.addWallCoordinates(coords);
                        break;
                    case 'l':
                        tiledata.addCityCoordinates(coords);
                        break;
                    case 't':
                        tiledata.addRoadCoordinates(coords);
                        break;
                    case 'k':
                        tiledata.addChurchCoordinates(coords);
                        break;
                    default:
                        break;
                }
            }
        }
        return tiledata;
    }
}
