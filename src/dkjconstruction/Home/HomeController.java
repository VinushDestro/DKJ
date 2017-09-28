/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.Home;

import dkjconstruction.DKJConstruction;
import dkjconstruction.DbConnection;
import static dkjconstruction.Home.LoginController.currentUser;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author User
 */
public class HomeController {
    private DKJConstruction main;
    
    public static String type;
    
    @FXML
    private Label cUser;
    public void initialize() throws SQLException, ClassNotFoundException {
            cUser.setText(currentUser);
            DbConnection DbConnection= new DbConnection();
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement("select userType from user where username=?");
        stmt.setString(1,currentUser);
        
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            type=rs.getString(1);
        }               
        DbConnection.closeConnection();
        System.out.println(type+"main");
    }    

    @FXML
    private void doTransport() throws IOException {
        System.out.println("tr"+type);
            if (type.equals("assetadmin") || type.equals("supervisor"))
            main.showTransport();
        else
            main.showErrorPage();      
    }
    
    @FXML
    private void doAdmin() throws IOException {
        if (type.equals("supervisor"))
            main.showAdmin();
        else
            main.showErrorPage();
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
        if (type.equals("clerk")||type.equals("supervisor"))
            main.showAccounts();
        else
            main.showErrorPage();
    }
    
    @FXML
    private void doUtilities() throws IOException {
        if (type.equals("clerk")||type.equals("supervisor"))
            main.showUtilities();
        else
            main.showErrorPage();
    }
    
    /*@FXML
    private void doSupplier() throws IOException {
        if (type.equals("materialadmin") || type.equals("supervisor"))
            main.showSupplier();
        else
            main.showErrorPage();
    }*/
    
    @FXML
    private void doMaterial() throws IOException {
        if (type.equals("materialadmin") || type.equals("supervisor"))
            main.showMaterial();
        else
            main.showErrorPage();
    }
    
    @FXML
    private void doTender() throws IOException {
        if (type.equals("supervisor"))
            main.showTender();
        else
            main.showErrorPage();
    }
    @FXML
    private void doAsset() throws IOException {
        if (type.equals("assetadmin") || type.equals("supervisor"))
            main.showAsset();
        else
            main.showErrorPage();
    }
    
    @FXML
    private void doEquip() throws IOException {
        if (type.equals("assetadmin") || type.equals("supervisor"))
            main.showEquip();
        else
            main.showErrorPage();
    }
    
    @FXML
    private void doHr() throws IOException {
        if (type.equals("hradmin") || type.equals("supervisor"))
            main.showHr();
        else
            main.showErrorPage();
    }
    
    @FXML
    private void doPayroll() throws IOException {
       // if (type.equals("hradmin") || type.equals("supervisor"))
          //  main.showPayroll();
       // else
        //    main.showErrorPage();
    }
    
    @FXML
    private void doLogout() {
        main.logout();
    }

    @FXML
    private void doSupplier(ActionEvent event) throws IOException {
        if (type.equals("materialadmin") || type.equals("supervisor"))
            main.showSupplier();
        else
            main.showErrorPage();
        
    }

}
