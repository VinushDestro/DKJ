/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.home;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 *
 * @author Giga
 */
public class ChatController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private GridPane pane;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        WebView view = new WebView();
        WebEngine engine = view.getEngine();
        final String chatUrl = getClass().getResource("chat.html").toExternalForm();
        engine.setUserAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");       
        engine.load(chatUrl);
        pane.getChildren().add(view);
        
        
       
    }    
    
}
