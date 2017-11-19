/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.settings;

import dkjconstruction.DbConnection;
import static dkjconstruction.main.LoginController.currentUser;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author VINUSH
 */
public class ChangePwController implements Initializable {

    @FXML
    private TextField newpw;
    
    @FXML
    private TextField renewpw;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void doChange(ActionEvent event) {
        String newPw=newpw.getText();
        String reNewPw=renewpw.getText();
        
            
            int i=0;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Change Password");
        
        if (newPw.isEmpty() ||  reNewPw.isEmpty() ) {
            alert.setContentText("Both fields should be filled");
        }
        else if ((i=newPw.length())<8){
            alert.setContentText("Password should contain more than 8 characters.");
        }
        else {
            System.out.println(newPw);
             System.out.println(reNewPw);
            if (newPw.equals(reNewPw)){
                try {
                    
                    DbConnection.openConnection();
                    Connection con = DbConnection.getConnection();
                    String x;
                    PreparedStatement stmt =con.prepareStatement("select empid from employee where name=?");
                    stmt.setString(1, currentUser);
                    ResultSet rs=stmt.executeQuery();

                    if(rs.next()) {
                         x=rs.getString(1);

                            PreparedStatement ps = con.prepareStatement("update user set password = ? where userid = ?");
                            ps.setString(1, newPw);
                            ps.setString(2,x);
                            ps.executeUpdate();
                        }
                    
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(ChangePwController.class.getName()).log(Level.SEVERE, null, ex);
                    alert.setContentText("Update Failed");
                }
                
            }
            else{
                alert.setContentText("Re-enter fields. Password does not match");
            }
        }
        
        alert.show();
    }
}
