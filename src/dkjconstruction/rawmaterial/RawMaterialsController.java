/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.rawmaterial;

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
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 *
 * @author User
 */
public class RawMaterialsController implements Initializable {
    
    @FXML
    private Label label;
    
   
    
 
   @FXML
   TableView<dkjconstruction.rawmaterial.RawMaterialDetail> rawmaterialTab;
    @FXML
    TableColumn tabType;
    @FXML
    TableColumn tabQuantity;
    @FXML
    TableColumn tabPrice;
    @FXML
    TableColumn tabSupplier;

    
    @FXML
    private JFXTextField Type;
    
    @FXML
    private JFXTextField Quantity;
    
    @FXML
    private JFXTextField Price;
    
    @FXML
    private JFXTextField Supplier;
    
    @FXML
    Button Add;
    
    @FXML
    Button Update;
    
    @FXML
    Button Delete;
    
    @FXML
    Button ViewTenderDetails;
    
    
    
    
    
    @FXML
    private void doAddRawmaterial() throws SQLException, ClassNotFoundException {
       int result=0;
        String type = Type.getText().trim();
        double price = Double.parseDouble(Price.getText().trim());
        int quantity = Integer.parseInt(Quantity.getText().trim());
        String supplier = Supplier.getText().trim();
      
       
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rawmaterial");
        alert.setHeaderText(null);

            if (type.isEmpty() || Price.getText().trim().isEmpty()  || Quantity.getText().trim().isEmpty()  || supplier.isEmpty() ) {
            alert.setContentText("All Fields cannot be empty"); }
            
            else {
                result = dkjconstruction.rawmaterial.RawMaterial.addrawmaterial(type, quantity, price, supplier);
                                     
                if (result == 1) {
                    alert.setContentText("Operation Successful!");
                } else {
                    alert.setContentText("Operation Failed");
                }
            }
       



        alert.show();
        Type.clear();
        Quantity.clear();
        Price.clear();
        
       

    }
    
     
       @FXML
    private void doUpdateRawmaterial(ActionEvent event) throws SQLException, ClassNotFoundException {
        int result = 0 ;
        String type = Type.getText().trim();
        double price = Double.parseDouble(Price.getText().trim());
        int quantity = Integer.parseInt(Quantity.getText().trim());
        String supplier = Supplier.getText().trim();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rawmaterial");
        alert.setHeaderText(null);

        
        
        if (type.isEmpty() &&  Price.getText().trim().isEmpty()&&  Quantity.getText().trim().isEmpty() && supplier.isEmpty()) {
            alert.setContentText("All Fields cannot be empty");
        }
         else if ( Quantity.getText().trim().isEmpty()){
            alert.setContentText("Quantiy field cannot be empty");
        }
        
        else{
                    try{
                          result = dkjconstruction.rawmaterial.RawMaterial.updaterawmaterial(type, price, quantity, supplier);
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
                }   

         alert.show();
        Type.clear();
        Quantity.clear();
        Price.clear();
        
       

    }
    
    @FXML
    private void doDeleteRawmaterial() throws SQLException, ClassNotFoundException {
        String s = Type.getText();
        s = s.trim();

        if (s.isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Rawmaterial");
            alert.setHeaderText(null);
            alert.setContentText("Raw Material Field Cannot be Empty!");
            alert.show();
        }
        else {
            int result =0;
                    result= dkjconstruction.rawmaterial.RawMaterial.deleterawmaterial(Type.getText());

            Type.clear();
            if (result == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Rawmaterial");
                alert.setHeaderText(null);
                alert.setContentText("Rawmaterial Successfully Deleted!");
                alert.show();
            } else if (result==0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Ramwaterial");
                alert.setHeaderText(null);
                alert.setContentText("Deletion Failed! \n Rawmaterial Not Found!");
                alert.show();
            }
        }

    }
  
   
   


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tabType.setCellValueFactory(new PropertyValueFactory<RawMaterialDetail,String>("Type"));
        tabQuantity.setCellValueFactory(new PropertyValueFactory<RawMaterialDetail,String>("Quantity"));
        tabPrice.setCellValueFactory(new PropertyValueFactory<RawMaterialDetail,String>("Price"));
        tabSupplier.setCellValueFactory(new PropertyValueFactory<RawMaterialDetail,Double>("Supplier"));
     



        try {
            rawmaterialTab.setItems(RawMaterial.getRawmaterial());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void showView() throws IOException {
        RawMaterial.showTenderView();
    }
    
}
