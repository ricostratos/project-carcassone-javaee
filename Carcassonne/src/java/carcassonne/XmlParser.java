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
                    char[][] wPositions = parseTileData(workerPosition);
                    char[][] tCoordianates =parseTileData(typeCoordinates);
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

    private static char[][] parseTileData(String str)
    {
        char[][] tiledata = new char[5][5];
        String[] sArray = str.split(",");
        for (int j = 0; j < sArray.length; j++)
        {
            String line = sArray[j];
            for (int k = 0; k < line.length(); k++)
            {
                tiledata[j][k]=line.charAt(k);
                /*
                 * - peltoa
                 * m muuri
                 * l linna
                 * t tie
                 * e este(tiellä)
                 * k kirkko
                 */
              
            }
        }
        return tiledata;
    }
}
