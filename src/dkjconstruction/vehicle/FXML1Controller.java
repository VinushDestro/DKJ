package dkjconstruction.vehicle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
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
import dkjconstruction.vehicle.DKJASSETSMANAGEMENT;
import dkjconstruction.vehicle.VehicleDetail;
//import dkj.assets.management.DKJASSETSMANAGEMENT.Vehicle;
//import dkj.assets.management.DKJASSETSMANAGEMENT.VehicleDetail;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * FXML Controller class
 *
 * @author VINUSH
 */
public class FXML1Controller implements Initializable {

    
    @FXML
    TableView<VehicleDetail> vehicleTab;
    @FXML
    TableColumn tabName;
    @FXML
    TableColumn tabRegNo;
    @FXML
    TableColumn tabType;
    @FXML
    TableColumn tabCost;
    @FXML
    TableColumn tabLifeTime;
    @FXML
    TableColumn tabBoughtDate;
    @FXML
    TableColumn tabCondition;

    @FXML
    private JFXTextField vehName;
    
    @FXML
    private JFXTextField regNo;
    
    @FXML
    private JFXTextField liTime;
    
    @FXML
    private JFXTextField cost;
    
    @FXML
    private JFXTextField bouDate;
    
    @FXML
    private JFXTextField condi;
    
    @FXML
    private JFXComboBox type;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        type.getItems().addAll("Heavy","Light","Transport");
        
        
        tabName.setCellValueFactory(new PropertyValueFactory<VehicleDetail,String>("Name"));
        tabRegNo.setCellValueFactory(new PropertyValueFactory<VehicleDetail,String>("RegNo"));
        tabType.setCellValueFactory(new PropertyValueFactory<VehicleDetail,String>("Type"));
        tabCost.setCellValueFactory(new PropertyValueFactory<VehicleDetail,Double>("Cost"));
        tabLifeTime.setCellValueFactory(new PropertyValueFactory<VehicleDetail,Integer>("LifeTime"));
        tabBoughtDate.setCellValueFactory(new PropertyValueFactory<VehicleDetail,String>("BoughtDate"));
        tabCondition.setCellValueFactory(new PropertyValueFactory<VehicleDetail,String>("Condition"));
       



        try {
            vehicleTab.setItems(DKJASSETSMANAGEMENT.getVehicle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    


    
    @FXML
    private void doAddVehicle() throws SQLException, ClassNotFoundException {
       int result=0;
        String name = vehName.getText().trim();
        String rNo = regNo.getText().trim();
        String typ = type.getPromptText().trim();
      //  Double cst = Double.parseDouble(cost.getText().trim());
      String cst = cost.getText().trim();
        String bDate = bouDate.getText().trim();
      //  int lifT=Integer.parseInt(liTime.getText().trim());
        String lifT=liTime.getText().trim();
        String cond = condi.getText().trim();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Vehicle Added");
        alert.setHeaderText(null);
        
        try{
            if (name.isEmpty() || rNo.isEmpty() || typ.isEmpty() || cst.isEmpty() || bDate.isEmpty() || lifT.isEmpty() || cond.isEmpty()) {
            alert.setContentText("Fields cannot be empty"); }
            
            else if (Double.valueOf(cst) <=0) {
            alert.setContentText("Cost cannot be 0"); }
            
            else if (Integer.valueOf(lifT) <=0) {
            alert.setContentText("Life Time cannot be 0"); }
            
            else {
                result = DKJASSETSMANAGEMENT.addVehicle( regNo.getText(),vehName.getText(),type.getValue().toString(),Double.parseDouble(cost.getText()),bouDate.getText(), Integer.parseInt(liTime.getText()),condi.getText());
                                           // regno,name,type,cost,boughtDate,lifeTime,condition
                if (result == 1) {
                    alert.setContentText("Operation Successful!");
                } else {
                    alert.setContentText("Operation Failed");
                }
            }}
       catch (Exception e){
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Entry\n"+e.getMessage());
            alert.show();

        }
        



        alert.show();
        regNo.clear();
        vehName.clear();
        type.valueProperty().set(null);
        cost.clear();
        bouDate.clear();
        liTime.clear();
        condi.clear();


    }
    
    @FXML
    private void doUpdateVehicle(ActionEvent event) throws SQLException, ClassNotFoundException {
        int result = 0 ;
       // String name = vehName.getText().trim();
        String rNo = regNo.getText().trim();
        String lifT= liTime.getText().trim();
        String cond = condi.getText().trim();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update Vehicle");
        alert.setHeaderText(null);


        if ( rNo.isEmpty() || lifT.isEmpty() || cond.isEmpty()) {
            alert.setContentText("Fields cannot be empty");
        } else if ( lifT.isEmpty() && cond.isEmpty() && !rNo.isEmpty()) {
            alert.setContentText("You have to enter at least one field to update details!");
        } else if (rNo.isEmpty()){
            alert.setContentText("Registration Number field cannot be empty");
        }
 
        
                    if(!lifT.isEmpty()){
                        result = DKJASSETSMANAGEMENT.updateVehicle(String.valueOf(rNo),Integer.valueOf(lifT));
                        if (result == 1) {
                            alert.setContentText("Operation Successful!");
                        } else if( result == 0){
                            alert.setContentText("Operation Failed");
                        }
                    }
                    else if(!cond.isEmpty()){
                        result = DKJASSETSMANAGEMENT.updateVehicle(String.valueOf(rNo),String.valueOf(cond));
                        if (result == 1) {
                            alert.setContentText("Operation Successful!");
                        } else if( result == 0){
                            alert.setContentText("Operation Failed");
                        }
                    }
                    else if (!rNo.isEmpty() && !lifT.isEmpty() && !cond.isEmpty() ){
                        result = DKJASSETSMANAGEMENT.updateVehicle(String.valueOf(rNo),Integer.valueOf(lifT),String.valueOf(cond));
                        if (result == 1) {
                            alert.setContentText("Operation Successful!");
                        } else if( result == 0){
                            alert.setContentText("Operation Failed");
                        }
                    }
                    
                  
                       
                    
                   

        alert.show();
        regNo.clear();
        liTime.clear();
        condi.clear();

    }
    
    @FXML
    public void doDeleteVehicle() throws SQLException, ClassNotFoundException {
        String s = regNo.getText();
        s = s.trim();

        if (s.isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Vehicle");
            alert.setHeaderText(null);
            alert.setContentText("Registration Number Field Cannot be Empty!");
            alert.show();
        }
        else {
            int result =0;
                    result= DKJASSETSMANAGEMENT.deleteVehicle(regNo.getText());

            regNo.clear();
            if (result == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Vehicle");
                alert.setHeaderText(null);
                alert.setContentText("Vehicle Successfully Deleted!");
                alert.show();
            } else if (result==0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Vehicle");
                alert.setHeaderText(null);
                alert.setContentText("Deletion Failed! \n Vehicle Not Found!");
                alert.show();
            }
        }

    }
  
    
   @FXML
   public void doViewAvailable() {
         try {
            vehicleTab.setItems(DKJASSETSMANAGEMENT.getAvailableVehicle());
        } catch (Exception e) {
            e.printStackTrace();
        }
   }
    
    
    @FXML 
    public void doAllVehicle(){
            try {
            vehicleTab.setItems(DKJASSETSMANAGEMENT.getVehicle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
