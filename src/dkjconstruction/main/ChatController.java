/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import static dkjconstruction.main.LoginController.currentUser;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Giga
 */
public class ChatController implements Initializable {

    
    @FXML
    private GridPane pane;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        String curl = ChatController.class.getResource("chat.html").toExternalForm();
        webEngine.load(curl);
        webEngine.executeScript("USER = '"+currentUser+"'");
        
        pane.getChildren().add(browser);
        
        
    }    
    
}
