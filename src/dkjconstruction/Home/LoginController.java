/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.Home;

import dkjconstruction.DKJConstruction;
import dkjconstruction.DbConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author User
 */
public class LoginController {
    private static DKJConstruction main;
    
    public static String currentUser;
    
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    @FXML
    private void actionLogin(ActionEvent event) throws IOException,SQLException, ClassNotFoundException, InterruptedException{
        String userName = null;
        String passWord =null;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login");
        alert.setHeaderText(null);
        
        if (username.getText().equals("") || password.getText().equals("")){
            alert.setContentText("Enter username and password");
            alert.show();
        }
        else {
            DbConnection DbConnection= new DbConnection();
            DbConnection.openConnection();
            con = DbConnection.getConnection();
            stmt = con.prepareStatement("Select username,password from user where username = ? and password = ?");

            stmt.setString(1,username.getText());
            stmt.setString(2,password.getText());
            rs = stmt.executeQuery();
            if(rs.next()){
                userName = rs.getString("username");
                passWord = rs.getString("password");

                if (username.getText().equals(userName) &&  password.getText().equals(passWord)) {
                    currentUser=userName;
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                    main.showHomePage();
                }
                
            }
            else{
                    System.out.println("error");
                    alert.setContentText("Login Failed.\nInvalid Credentials");
                    alert.show();
                }
            
            username.clear();
            password.clear();
        } 
    }
}