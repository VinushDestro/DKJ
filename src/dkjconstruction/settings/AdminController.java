/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.settings;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;


/**
 * FXML Controller class
 *
 * @author User
 */
public class AdminController {
    
    @FXML
    private JFXTextField userId;
    @FXML
    private ComboBox userType;
    @FXML
    private JFXDatePicker  assignDate;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXTextField  password;
    
    @FXML
    private TableView  adminTab;
    
    @FXML
    private TableColumn  tabId;
    @FXML
    private TableColumn  tabType;
    @FXML
    private TableColumn  tabDate;
    @FXML
    private TableColumn  tabUname;
    @FXML
    private TableColumn  tabPw;
    
    @FXML
    private TextField search;
    
    public void initialize() throws SQLException {
        userType.getItems().addAll("Supervisor","Asset Admin","HR Admin","Material Admin","Clerk");
        

        tabId.setCellValueFactory(new PropertyValueFactory<AdminDetail,String>("UserId"));
        tabType.setCellValueFactory(new PropertyValueFactory<AdminDetail,String>("UserType"));
	tabDate.setCellValueFactory(new PropertyValueFactory<AdminDetail,String>("date"));
        tabUname.setCellValueFactory(new PropertyValueFactory<AdminDetail,Double>("username"));
        tabPw.setCellValueFactory(new PropertyValueFactory<AdminDetail,Integer>("password"));
       
        
        try {
            adminTab.setItems(Admin.getAdmin());
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
    }  

    @FXML
    private void doAddUser() throws SQLException, ClassNotFoundException {
        int result = 0 ;
        
        String addUserId = userId.getText().trim();
       // String addUserType = userType.getValue().toString().toLowerCase().trim();
        LocalDate addDate = assignDate.getValue();
        String addUsername = username.getText().trim();
        String addPassword = password.getText().trim();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add User");
        alert.setHeaderText(null);
        if (addUserId.isEmpty() || (userType.getValue() == null) || (addDate == null) || addUsername.isEmpty() || addPassword.isEmpty()) {
            alert.setContentText("All fields should be filled");
        }
        else {
            LocalDate today = LocalDate.now();
            if(addDate.isAfter(today)){
                alert.setContentText("Invalid Entry for Date.\nShould be before current date.");
            }
            else{
                if (addPassword.length()<=8) {
                    alert.setContentText("Password should contain more than 8 characters.");
                }
                else{
                    try{
                        result = Admin.addAdmin(addUserId, userType.getValue().toString(),Date.valueOf(addDate),addUsername,addPassword);
                        if (result == 1) {
                            alert.setContentText("Operation Successful!");
                            userId.clear();
                            userType.getItems().clear();
                            assignDate.getEditor().setText(null);
                            username.clear();
                            password.clear();
                            
                            try {
                                adminTab.setItems(Admin.getAdmin());
                            } catch (IOException | ClassNotFoundException | SQLException e) {
                                e.printStackTrace();
                            }
                        } 
                        else {
                            alert.setContentText("Operation Failed");
                        }
                    }
                    catch (SQLException e1) {
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Error");
                        alert2.setHeaderText(null);
                        alert2.setContentText(e1.getMessage()+"\nUser ID should be ID of an Employee");
                        alert.setContentText("Operation Failed!");
                        
                        alert2.show();
                        
                    }
                    
                }
            }
        }
        alert.show();
    }

    @FXML
    private void doUpdateUser() throws SQLException, ClassNotFoundException {
        try{
        int result = 0 ;
        String addUserId = userId.getText().trim();
        //String addUserType = (String) userType.getValue().toString().toLowerCase().trim();
        /*
        LocalDate addDate = assignDate.getValue();
        String addUsername = username.getText().trim();
        String addPassword = password.getText().trim();
        */

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update User");
        alert.setHeaderText(null);
        
        if(addUserId.isEmpty() || userType.getValue()==null){
                alert.setContentText("Only User Type can be changed.\nBoth User ID and User Type should be given.");
                alert.show();
                /*assignDate.getEditor().setText(null);
                username.clear();
                password.clear();*/
        }
        else{
                        result = Admin.updateAdmin(addUserId,userType.getValue().toString());
                        if (result == 1) {
                            alert.setContentText("Operation Successful!");
                            alert.show();

                            userId.clear();
                            userType.getItems().clear();
                            
                            try {
                                adminTab.setItems(Admin.getAdmin());
                            } catch (IOException | ClassNotFoundException | SQLException e) {
                                e.printStackTrace();
                            }
                        } 
                        else {
                            alert.setContentText("Operation Failed");
                            alert.show();

                    }
                }
        }catch(SQLException e){
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, e);
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Error");
                        alert2.setHeaderText(null);
                        alert2.setContentText(e.getMessage());
                        alert2.show();
        }
        
    }
    
    @FXML
    private void doDeleteUser() throws SQLException, ClassNotFoundException {
        try {
            //int result = 0 ;
            String addUserId = userId.getText().trim();
            
            if (addUserId.isEmpty() ) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete User");
                alert.setHeaderText(null);
                alert.setContentText("User ID Field should be given.");
                alert.show();
            }
            else {
                int result = Admin.deleteAdmin(addUserId);
                userId.clear();
                if (result == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Delete User");
                    alert.setHeaderText(null);
                    alert.setContentText("Deleted Successfully!");
                    alert.show();
                    try {
                        adminTab.setItems(Admin.getAdmin());
                    } catch (IOException | ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                    }
                }
                else if (result==0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Delete User");
                    alert.setHeaderText(null);
                    alert.setContentText("Deletion Failed! \n User ID Not Found!");
                    alert.show();
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Error");
                        alert2.setHeaderText(null);
                        alert2.setContentText(ex.getMessage());
                        alert2.show();
        }

    }
    
}