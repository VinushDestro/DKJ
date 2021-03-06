/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.main;

import dkjconstruction.DKJConstruction;
import dkjconstruction.DbConnection;
import static dkjconstruction.main.LoginController.currentUser;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class MainController {
    private DKJConstruction main;
    
    public static String type;
    @FXML
    private Button minimizeButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button homeBtn;
    @FXML
    private Label cUser;
    
    public void initialize() throws SQLException, ClassNotFoundException, IOException {

        cUser.setText(currentUser.toUpperCase());
        DbConnection DbConnection= new DbConnection();
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement("select userType from user where userid in (select empid from employee where name=?)");
        stmt.setString(1,currentUser.toLowerCase());
        System.out.println(currentUser);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            System.out.println("rs get :"+rs.getString("userType"));
            type=rs.getString(1);
        }               
        System.out.println(type+"main");
        //homeBtn.pressedProperty(); //  fire()
        //doHome();
        
    }
    
    @FXML
    public void handleCloseBtn(ActionEvent event){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        //alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Do you want to close the window?");
        alert.setContentText("Unsaved changes would be discarded. ");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    public void handleMinimizeBtn(ActionEvent event){
            Stage stage = (Stage) minimizeButton.getScene().getWindow();
            stage.setIconified(true);
    }
    
    @FXML
    private void doHome() throws IOException {
        System.out.println("tr"+type);
            main.showHome();
    }
    
    @FXML
    private void doTransport() throws IOException {
        System.out.println("tr"+type);
        if (type.equals("supervisor"))
            main.showTransport();
        else
           main.showErrorPage();      
    }
    
    @FXML
    private void doSetting() throws IOException {
        if (type.equals("supervisor"))
            main.showAdmin();
        else
            main.showChangePw();
    }
    
    @FXML
    private void doJob() throws IOException {
        if (type.equals("supervisor"))
            main.showJobAllocation();
        else
            main.showErrorPage();
    }
    
    @FXML
    private void doAccounts() throws IOException {
        if (type.equals("manager")||type.equals("supervisor"))
            main.showAccounts();
        else
            main.showErrorPage();
    }
    
    @FXML
    private void doUtilities() throws IOException {
        if (type.equals("manager")||type.equals("supervisor"))
            main.showUtilities();
        else
            main.showErrorPage();
    }
    @FXML
    private void doSupplier() throws IOException {
        if (type.equals("admin") || type.equals("supervisor"))
            main.showSupplier();
       else
            main.showErrorPage();
        
    }
    
    @FXML
    private void doMaterial() throws IOException {
       if (type.equals("admin") || type.equals("supervisor"))
            main.showMaterial();
        else
            main.showErrorPage();
    }
    
    @FXML
    private void doTender() throws IOException {
        System.out.println("user "+type);
        if (type.equals("manager") || type.equals("supervisor"))
            main.showTender_TenderHome();
        else
            main.showTender_TenderHome();
    }
    @FXML
    private void doAsset() throws IOException {
        if (type.equals("admin") || type.equals("supervisor"))
            main.showAsset();
        else
            main.showErrorPage();
    }
    
    @FXML
    private void doEquip() throws IOException {
        if (type.equals("admin") || type.equals("supervisor"))
            main.showEquip();
        else
            main.showErrorPage();
    }
    
    @FXML
    private void doHr() throws IOException {
        if (type.equals("admin") || type.equals("supervisor"))
            main.showHr();
        else
            main.showErrorPage();
    }
    
    @FXML
    private void doPayroll() throws IOException {
        if (type.equals("manager") || type.equals("supervisor"))
            main.showPayroll();
        else
            main.showErrorPage();
    }
    
    @FXML
    private void showChat() throws IOException {
        if (type.equals("supervisor"))
            main.showChat();
        else
            main.showErrorPage();
    }
    
    @FXML
    private void doLogout() {
        main.logout();
    }
}
