/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.settings;

import dkjconstruction.DbConnection;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;


/**
 * FXML Controller class
 *
 * @author User
 */
public class AdminController {
    
    @FXML
    private JFXTextField userId;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXDatePicker  assignDate;
    @FXML
    private JFXTextField username;
 
    
    @FXML
    private TableView<AdminDetail>  adminTab;
    
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
    private ObservableList<AdminDetail> adminS; 

    
    public void initialize() throws SQLException {
        loadTable();
        doSearchUser();
        RowclickEvent();
        loadTable();
        password.setVisible(false);
    }  

    @FXML
    private void doAddUser() throws SQLException, ClassNotFoundException {
        int result = 0 ;
        
        String addUserId = userId.getText().trim();
        String addUserType;
        LocalDate addDate = assignDate.getValue();
        String addUsername = username.getText().trim();
        String addPassword = addUsername+"@123";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add User");
        alert.setHeaderText(null);
        if (addUserId.isEmpty() || (addDate == null) || addUsername.isEmpty() ) {
            alert.setContentText("All fields should be filled");
        }
        else {
            LocalDate today = LocalDate.now();
            if(addDate.isAfter(today)){
                alert.setContentText("Invalid Entry for Date.\nShould be before current date.");
            }
            else{
                    try{
                        Connection con = DbConnection.getConnection();
            
                        PreparedStatement ps = con.prepareStatement("select position from employee where empid =?");
                        ps.setString(1,addUserId);

                        ResultSet rs = ps.executeQuery();
                        if(rs.next()){
                            addUserType=(rs.getString(1));
                            
                            if ("employee".equals(addUserType.toLowerCase()))
                                addUserType="Admin";
                            result = Admin.addAdmin(addUserId,addUserType,Date.valueOf(addDate),addUsername,addPassword);
                        }
                            
                        if (result == 1) {
                            alert.setContentText("Operation Successful!");
                            clearFields();

                            try {
                                adminTab.setItems(Admin.getAdmin());
                            } catch (IOException | ClassNotFoundException | SQLException e) {
                                e.printStackTrace();
                            }
                        } 
                        else {
                           alert.setHeaderText("Operation Failed");
                        alert.setContentText("User ID should be ID of an Employee");
                        }
                    }
                    catch (SQLException e1) {
                        alert.setHeaderText("Operation Failed");
                        alert.setContentText("User ID should be ID of an Employee");
                        alert.show();
                        
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
        String addPassword = password.getText().trim();
       
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update User");
        alert.setHeaderText(null);
        
        if(addUserId.isEmpty() || addPassword.isEmpty()){
                alert.setContentText("Both User ID and Username should be given to change password.\nClick row to do update.");
                alert.show();
                /*assignDate.getEditor().setText(null);
                username.clear();
                password.clear();*/
        }
        else{
                        result = Admin.updateAdmin(addUserId,addPassword);
                        if (result == 1) {
                            alert.setContentText("Operation Successful!");
                            alert.show();

                            clearFields();
                            
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
                if (result == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Delete User");
                    alert.setHeaderText(null);
                    alert.setContentText("Deleted Successfully!");
                    clearFields();
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
    
    @FXML
    private void doReport(){
        try{DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        String report = "C:\\Users\\Mahesh\\Documents\\NetBeansProjects\\dkjconstructions\\src\\dkjconstruction\\settings\\user.jrxml";
        JasperReport jr = JasperCompileManager.compileReport(report);
        JasperPrint jp = JasperFillManager.fillReport(jr,null,con);
        JasperViewer.viewReport(jp,false);
        }
        catch(Exception e){
            System.out.println(e);
        }
            
    }
    
    private void RowclickEvent() {
        adminTab.setOnMouseClicked((MouseEvent e) -> {
            clearFields();
            password.setVisible(true);

            try{
                AdminDetail t1 = (AdminDetail) adminTab.getItems().get(adminTab.getSelectionModel().getSelectedIndex());
                LocalDate today = LocalDate.now();
                
    
                    
                    userId.setText(t1.getUserId());
                   password.setText(t1.getPassword());
                    assignDate.setValue(t1.getDate().toLocalDate());
                    assignDate.setDisable(true);
                    username.setText(t1.getUsername());
                    username.setDisable(true);
                
            }
            catch(Exception ex){
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Error");
                alert2.setHeaderText(null);
                alert2.setContentText("No row is clicked");
                alert2.show();
            }
        
        });

    }
    private void loadTable(){
        tabId.setCellValueFactory(new PropertyValueFactory<>("UserId"));
        tabType.setCellValueFactory(new PropertyValueFactory<>("UserType"));
	tabDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tabUname.setCellValueFactory(new PropertyValueFactory<>("username"));
        tabPw.setCellValueFactory(new PropertyValueFactory<>("password"));
       
        
        try {
            adminTab.setItems(Admin.getAdmin());
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public void clearFields(){
        userId.clear();
        assignDate.getEditor().setText(null);
        username.clear();
        assignDate.setDisable(false);
        search.clear();
        password.clear();
        password.setVisible(false);
    }
    
    private void doSearchUser() {
        search.setOnKeyReleased(e -> {
            if (search.getText().equals("")) {
                loadTable();

            }
            else{
                
                try {
                    Connection con = DbConnection.getConnection();
                    
                    PreparedStatement pst = con.prepareStatement
                                    ("SELECT * FROM user where userid LIKE '%" + search.getText() + "%'"
                                            + "UNION SELECT * FROM user where usertype LIKE '%" + search.getText() + "%'"
                                                    + "UNION SELECT * FROM user where dateassigned LIKE '%" + search.getText() + "%'"
                                                            + "UNION SELECT * FROM user where username LIKE '%" + search.getText() + "%' ");
                    ResultSet rs = pst.executeQuery();
                    adminS= FXCollections.observableArrayList();
                    while (rs.next()) {
                        adminS.add(new AdminDetail(rs.getString(1),rs.getString(2),rs.getDate(3),rs.getString(4),rs.getString(5))); 
                    }
                    adminTab.setItems(adminS);
                } catch (SQLException ex) {
                    Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                }

                
            }
        });
    }
    
}