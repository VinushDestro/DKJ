/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.equip;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
//import dkj.assets.management.Equipment.EquipmentDetail;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author VINUSH
 */
public class EquipController implements Initializable {

    @FXML
    TableView<EquipmentDetail> equipTab;
    @FXML
    TableColumn tabName;
    @FXML
    TableColumn tabID;
    @FXML
    TableColumn tabCount;
    @FXML
    TableColumn tabCost;
   
    
    @FXML
    private JFXTextField equipName;
    @FXML
    private JFXTextField eID;
    @FXML
    private JFXTextField cost;
    @FXML
    private JFXTextField count;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
         
        tabName.setCellValueFactory(new PropertyValueFactory<EquipmentDetail,String>("Name"));
        tabID.setCellValueFactory(new PropertyValueFactory<EquipmentDetail,String>("EquipID"));
        tabCount.setCellValueFactory(new PropertyValueFactory<EquipmentDetail,Integer>("Count"));
        tabCost.setCellValueFactory(new PropertyValueFactory<EquipmentDetail,Double>("Cost"));
        
       



        try {
          equipTab.setItems(Equipment.getEquipment());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    

    
    
    
    
    
    @FXML
    public void doAddEquipment() throws SQLException, ClassNotFoundException {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Equipment Added");
        alert.setHeaderText(null);
        try {
            
             int result=0;
        String name = equipName.getText().trim();
        String equipID = eID.getText().trim();
       // Double cst = Double.parseDouble(cost.getText().trim());
          String cst = cost.getText().trim();
        //int cut= Integer.parseInt(count.getText().trim());
        String cut= count.getText().trim();
        

        

            if (name.isEmpty() || equipID.isEmpty() || cost.getText().trim().isEmpty() || count.getText().trim().isEmpty()){
            alert.setContentText("Fields cannot be empty"); }
          
             else if (Double.valueOf(cst) <=0) {
            alert.setContentText("Cost cannot be 0"); }
            
            else if (Integer.valueOf(cut) <=0) {
            alert.setContentText("Count cannot be 0"); }
            
            else {
                result =Equipment.addEquipment( eID.getText(),equipName.getText(),Integer.parseInt(count.getText()),Double.parseDouble(cost.getText()));
                                           
                if (result == 1) {
                    alert.setContentText("Operation Successful!");
                } else {
                    alert.setContentText("Operation Failed");
                }
            }
            
        } catch (Exception e) {
            System.err.println("add error");
        } 
        finally  {
            alert.show();
        eID.clear();
        equipName.clear();
        count.clear();
        cost.clear();
        }
       
        



        
        


    }
    
    
    
     @FXML
    public void doUpdateEquipment() throws SQLException, ClassNotFoundException {
        int result = 0 ;
        String equipID = eID.getText().trim();
     //   int cut= Integer.parseInt(count.getText().trim());
         String cut=count.getText().trim();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update Equipment");
        alert.setHeaderText(null);

        
        
        if (equipID.isEmpty() && cut.isEmpty()) {
            alert.setContentText("Fields cannot be empty");
        } 
 
        else if (equipID.isEmpty()){
            alert.setContentText("Equipment ID field cannot be empty");
        }
        
        else{
                    try{
                        result = Equipment.updateEquipment(String.valueOf(equipID),Integer.valueOf(cut));
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
        eID.clear();
        count.clear();
        
        
    }
    
    @FXML
    private void doDeleteEquipment() throws SQLException, ClassNotFoundException {
        String s = eID.getText();
        s = s.trim();
      //  int c = Integer.parseInt(count.getText().trim());

      if ( !s.isEmpty() && count.getText().trim().isEmpty()) {
            int result =0;
            result= Equipment.deleteEquipment(s);

            eID.clear();
            count.clear();
            if (result == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Equipment");
                alert.setHeaderText(null);
                alert.setContentText("Equipment Successfully Deleted!");
                alert.show();
            } else if (result==0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Equipment");
                alert.setHeaderText(null);
                alert.setContentText("Deletion Failed! \n Equipment Not Found!");
                alert.show();
            }
        }

      else if (s.isEmpty() || count.getText().trim().isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Equipment");
            alert.setHeaderText(null);
            alert.setContentText(" Field Cannot be Empty!");
            alert.show();
        }
        else if (!s.isEmpty() && !count.getText().trim().isEmpty()) {
              int c = Integer.parseInt(count.getText().trim());
            int result =0;
                    result= Equipment.deleteEquipment(s,c);

            eID.clear();
            count.clear();
            if (result == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Equipment");
                alert.setHeaderText(null);
                alert.setContentText("Equipment Successfully Deleted!");
                alert.show();
            } else if (result==0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Equipment");
                alert.setHeaderText(null);
                alert.setContentText("Deletion Failed! \n Equipment Not Found!");
                alert.show();
            }
        }
        
    }
    
    
    
    @FXML
   public void doViewAvailable() {
         try {
            equipTab.setItems(Equipment.getAvailableEquipment());
        } catch (Exception e) {
            e.printStackTrace();
        }
   }
    
    
    @FXML 
    public void doAllEquipment(){
            try {
            equipTab.setItems(Equipment.getEquipment());
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    
    
    
    
}
}