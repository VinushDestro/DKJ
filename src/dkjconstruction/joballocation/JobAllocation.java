/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.joballocation;
import dkjconstruction.DbConnection;
import java.sql.*;

import java.sql.Statement;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


/**
 *
 * @author Ranjitha
 */
public class JobAllocation  extends Application{

    @Override
    public void start(Stage stage) throws Exception {
       
         Parent root = FXMLLoader.load(getClass().getResource("JobAllocation.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
        
    }
  
   public void alerboxInfo(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setWidth(500);
        alert.setHeight(300);
        alert.setContentText(message);
        alert.showAndWait();

    }  
    
    
    
}  
    

