package carcassonne;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Ville
 */
@ManagedBean(name = "SessionController")
@SessionScoped
public class SessionController implements Serializable {
    
    public SessionController() {
        
    }
    
    public void prepareGameBoard() {
        Session.prepareGameBoard();
    }
    
    public int newTilePosX() {
        return Session.newTilePosX();
    }
    public int newTilePosY() {
        return Session.newTilePosY();
    }
    public int newTileRotation() {
        return Session.newTileRotation();
    }
    
    public String printGameBoardTest() {
        return Session.printGameBoardTest();
    }
    public void addNewTileToGameBoard() {
        Session.addNewTileToGameBoard();
    }
}