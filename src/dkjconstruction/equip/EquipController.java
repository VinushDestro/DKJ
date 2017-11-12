/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.equip;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import dkjconstruction.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import dkjconstruction.equip.Equipment;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
//import dkj.assets.management.Equipment.EquipmentDetail;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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
//    @FXML
//    TableColumn tabID;
    @FXML
    TableColumn tabCount;
    @FXML
    TableColumn tabCost;
    @FXML
    TableColumn tabTotalCost;
   
    @FXML
    private JFXComboBox view;
    @FXML
    private JFXTextField equipName;
   @FXML
    private TextField search;
    @FXML
    private JFXTextField cost;
    @FXML
    private JFXTextField count;
    private ObservableList<EquipmentDetail> equipmentSe;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
         view.getItems().addAll("View_All","Available");
         
        tabName.setCellValueFactory(new PropertyValueFactory<EquipmentDetail,String>("Name"));
      //  tabID.setCellValueFactory(new PropertyValueFactory<EquipmentDetail,String>("EquipID"));
        tabCount.setCellValueFactory(new PropertyValueFactory<EquipmentDetail,Integer>("Count"));
        tabCost.setCellValueFactory(new PropertyValueFactory<EquipmentDetail,Double>("Cost"));
        tabTotalCost.setCellValueFactory(new PropertyValueFactory<EquipmentDetail,Double>("TotalCost"));
        
       



        try {
          equipTab.setItems(Equipment.getEquipment());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        RowclickEvent();
        doSearchVehicle();
        
    }    

    
    
    
    
    
    @FXML
    public void doAddEquipment() throws SQLException, ClassNotFoundException {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Equipment Added");
        alert.setHeaderText(null);
        try {
            
             int result=0;
        String name = equipName.getText().trim();
      //  String equipID = eID.getText().trim();
       // Double cst = Double.parseDouble(cost.getText().trim());
          String cst = cost.getText().trim();
           //int cut= Integer.parseInt(count.getText().trim());
        String cut= count.getText().trim();
       // String tCost = totalCost.getText().trim();

        

            if (name.isEmpty()  || cost.getText().trim().isEmpty() || count.getText().trim().isEmpty() ){
            alert.setContentText("Fields cannot be empty"); }
          
             else if (Double.valueOf(cst) <=0) {
            alert.setContentText("Cost cannot be 0"); }
            
            else if (Integer.valueOf(cut) <=0) {
            alert.setContentText("Count cannot be 0"); }
            
            else {
                result =Equipment.addEquipment( equipName.getText(),Integer.parseInt(count.getText()),Double.parseDouble(cost.getText()));
                                           
                if (result == 1) {
                    alert.setContentText("Operation Successful!");
                } else {
                    alert.setContentText("Operation Failed");
                }
            }
            
        }    catch (Exception e){
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("EquipID already exists");
            alert.show();

        }
        finally  {
            alert.show();
       
        equipName.clear();
        count.clear();
        cost.clear();
        }
       
        try {
            equipTab.setItems(Equipment.getEquipment());
        } catch (Exception e) {
            e.printStackTrace();
        }



        
        


    }
    
    
    
     @FXML
    public void doUpdateEquipment() throws SQLException, ClassNotFoundException {
        int result = 0 ;
        String name = equipName.getText().trim();
      
         String cut=count.getText().trim();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update Equipment");
        alert.setHeaderText(null);

        
        
        if (name.isEmpty() || cut.isEmpty()  ){
           
            alert.setContentText("Fields cannot be empty");
        } 
        else if (Integer.valueOf(cut) <=0  ){
            alert.setContentText("Cannot update to Zero or Less than that");
        }
 
        else if (name.isEmpty()){
            alert.setContentText("Equipment ID field cannot be empty");
        }
        else if (cut.isEmpty()){
            alert.setContentText("Count field cannot be empty");
        }
        
        
        else{
                    try{
                        result = Equipment.updateEquipment(String.valueOf(name),Integer.valueOf(cut));
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
       equipName.clear();
        count.clear();
        
        try {
            equipTab.setItems(Equipment.getEquipment());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @FXML
    private void doDeleteEquipment() throws SQLException, ClassNotFoundException {
        String s =  equipName.getText();
        s = s.trim();
      //  int c = Integer.parseInt(count.getText().trim());

      if ( !s.isEmpty() && count.getText().trim().isEmpty()) {
            int result =0;
            result= Equipment.deleteEquipment(s);

             equipName.clear();
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

             equipName.clear();
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
        try {
            equipTab.setItems(Equipment.getEquipment());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    @FXML
   public void doView() {
         try {
             if((String) view.getValue()=="Available"){
            equipTab.setItems(Equipment.getAvailableEquipment());
             }
             else if((String) view.getValue()=="View_All"){
             equipTab.setItems(Equipment.getEquipment());
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
   }
    
    
   
    
    private void RowclickEvent() {
        equipTab.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
          try{      EquipmentDetail tab = equipTab.getItems().get(equipTab.getSelectionModel().getSelectedIndex());
                
               
        equipName.setText(tab.getName());
        equipName.setDisable(true);
       // eID.setText(tab.getEquipID());

       count.setText(tab.getCount());

          cost.setText(tab.getCost());
          cost.setDisable(true);

            }
          
            catch(Exception ex){
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Error");
                alert2.setHeaderText(null);
                alert2.setContentText("No row is clicked");
                alert2.show();
            }
           
        }
    });
                }
    
    
    
private void doSearchVehicle() {
        search.setOnKeyReleased(e -> {
            if (search.getText().equals("")) {
               
                
        tabName.setCellValueFactory(new PropertyValueFactory<EquipmentDetail,String>("Name"));
        tabCount.setCellValueFactory(new PropertyValueFactory<EquipmentDetail,Integer>("Count"));
        tabCost.setCellValueFactory(new PropertyValueFactory<EquipmentDetail,Double>("Cost"));
        tabTotalCost.setCellValueFactory(new PropertyValueFactory<EquipmentDetail,Double>("TotalCost"));
        
       



        try {
          equipTab.setItems(Equipment.getEquipment());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

            }
            else{
                try {
                   Connection con = DbConnection.getConnection();
                    
                    PreparedStatement pst = con.prepareStatement
                             ("select * from equipment where name like '%" + search.getText() + "%'"
                            + "union select * from equipment where count like '%" + search.getText() + "%'");
                          
                          
                    ResultSet rs = pst.executeQuery();
                    equipmentSe= FXCollections.observableArrayList();
                   
                   while(rs.next()) {

            String name=rs.getString("name");
            String cost=rs.getString("cost");
            String count=rs.getString("Count");
            double totalCost=rs.getDouble("totalCost");
            equipmentSe.add(new EquipmentDetail(name,count,cost,totalCost));
                        }
                   equipTab.setItems(equipmentSe);

                } catch (SQLException ex) {
                    System.err.println("Error loading table data " + ex);

                }
                
            }
        });
    }
    
    @FXML
    public void doClear(){
        equipName.clear();
        equipName.setDisable(false);
        count.clear();
        cost.clear();
        cost.setDisable(false);
    }
    
}