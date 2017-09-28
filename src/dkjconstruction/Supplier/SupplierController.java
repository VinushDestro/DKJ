/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.Supplier;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import dkjconstruction.Supplier.SupplierDetail;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author User
 */
public class SupplierController implements Initializable {
    
   
 
    
   @FXML
   TableView<dkjconstruction.Supplier.SupplierDetail> SupplierTab;
    
    @FXML
    TableColumn tabsupplierid;
    @FXML
    TableColumn tabname;
    @FXML
    TableColumn tabnic;
    @FXML
    TableColumn tabcontact;

    
    @FXML
    private JFXTextField supplierid;
    
    @FXML
    private JFXTextField name;
    
    @FXML
    private JFXTextField nic;
    
    @FXML
    private JFXTextField contact;
    
    @FXML
    Button Add;
    
    @FXML
    Button Update;
    
    @FXML
    Button Delete;
    
   
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tabsupplierid.setCellValueFactory(new PropertyValueFactory<SupplierDetail,String>("id"));
        tabname.setCellValueFactory(new PropertyValueFactory<SupplierDetail,String>("name"));
        tabnic.setCellValueFactory(new PropertyValueFactory<SupplierDetail,String>("nic"));
        tabcontact.setCellValueFactory(new PropertyValueFactory<SupplierDetail,Double>("contact"));
     



        try {
            SupplierTab.setItems(Supplier.getSupplier());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    @FXML
    private void doAddSupplier() throws SQLException, ClassNotFoundException {
       int result=0;
        String SupplierId = supplierid.getText().trim();
        String Name =name.getText().trim();
        String Nic = nic.getText().trim();
        String Contact = contact.getText().trim();
      

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Supplier");
        alert.setHeaderText(null);

            if (SupplierId.isEmpty() && Name.isEmpty()  && Nic.isEmpty()  && Contact.isEmpty() ) {
            alert.setContentText("All Fields cannot be empty"); }
            
            else {
                result = dkjconstruction.Supplier.Supplier.addsupplier(SupplierId, Name, Nic, Contact);
                                     
                if (result == 1) {
                    alert.setContentText("Operation Successful!");
                } else {
                    alert.setContentText("Operation Failed");
                }
                
                try {
            SupplierTab.setItems(Supplier.getSupplier());
        } catch (Exception e) {
            e.printStackTrace();
        }
            }
        



        alert.show();
        supplierid.clear();
        name.clear();
        nic.clear();
        contact.clear();
        
        
        
       

    }
    
     
       @FXML
    private void doUpdateSupplier(ActionEvent event) throws SQLException, ClassNotFoundException {
        int result = 0 ;
        String SupplierId = supplierid.getText().trim();
        String Name =name.getText().trim();
        String Nic = nic.getText().trim();
        String Contact = contact.getText().trim();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rawmaterial");
        alert.setHeaderText(null);

        
        
        if (SupplierId.isEmpty() &&  Name.isEmpty() && Nic.isEmpty() && Contact.isEmpty()) {
            alert.setContentText("All Fields cannot be empty");
        }
         else if ( supplierid.getText().trim().isEmpty()){
            alert.setContentText("Supplier id field cannot be empty");
        }
        
        else{
                    try{
                         result = dkjconstruction.Supplier.Supplier.updatesupplier(SupplierId, Name, Nic, Contact);
                        if (result == 1) {
                            alert.setContentText("Operation Successful!");
                        } else {
                            alert.setContentText("Operation Failed");
                        }
                    }
                    catch (NumberFormatException e1) {
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Error");
                        alert2.setHeaderText(null);
                        alert2.setContentText(e1.getMessage()+"\nOperation Failed");
                        alert2.show();
                    }
                    
                    try {
                        SupplierTab.setItems(Supplier.getSupplier());
                    } catch (Exception e) {
                         e.printStackTrace();
                       }
                }   

          alert.show();
          supplierid.clear();
          name.clear();
          nic.clear();
          contact.clear();
        
       

    }
    
    @FXML
    private void doDeleteSupplier() throws SQLException, ClassNotFoundException {
        String s = supplierid.getText();
        s = s.trim();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Rawmaterial");
            alert.setHeaderText(null);
        if (s.isEmpty() ) {
            
            alert.setContentText("Supplier id Field Cannot be Empty!");
            
        }
        else {
            int result =0;
                    result= dkjconstruction.Supplier.Supplier.deleteSupplier(supplierid.getText().trim());

            supplierid.clear();
            if (result == 1) {  
                alert.setContentText("Rawmaterial Successfully Deleted!");
                
            } else if (result==0){
                alert.setContentText("Deletion Failed! \n Rawmaterial Not Found!");
            }
            
            try {
            SupplierTab.setItems(Supplier.getSupplier());
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        alert.show();

    }
  
   
   


    
    
    
    
    
}
