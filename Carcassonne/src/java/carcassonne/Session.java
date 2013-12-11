/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carcassonne;

import java.util.ArrayList;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author Java
 */
@WebListener
public class Session implements HttpSessionListener {
    
    private static int count;
    private String masterSession;
    private ArrayList<String> allSessions = new ArrayList();
    private static MapController map;
    private static boolean gameOn = false;
    
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        System.out.println("session created: " + event.getSession().getId());
        if(masterSession == null) {
            masterSession = event.getSession().getId();
            
            map = new MapController();
        }
        allSessions.add(event.getSession().getId());
        count++;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        System.out.println("session destroyed: " + event.getSession().getId());
        allSessions.remove(event.getSession().getId());
        count--;
    }
    
    
    public static void prepareGameBoard() {
        map.prepareGameBoard();
        map.createNewTile();
    }
    
    public static int newTilePosX() {
        return map.getNewTileX();
    }
    public static int newTilePosY() {
        return map.getNewTileY();
    }
    public static int newTileRotation() {
        return map.getNewTileRotation();
    }
    
    public static String printGameBoardTest() {
        return map.printGameBoardTest();
    }
    public static void addNewTileToGameBoard() {
        map.addNewTileToGameBoard();
    }
}
