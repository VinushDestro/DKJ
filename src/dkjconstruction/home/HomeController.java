/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.home;

import dkjconstruction.DbConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author User
 */
public class HomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ComboBox category;
    @FXML
    private TextField search;
    
    private List myList;
    
    Connection con = DbConnection.getConnection();
    PreparedStatement ps;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        category.getItems().addAll("Users","Tenders","Jobs","Employees","Vehicles","Equipments","Raw Material");
    }    
    
}
