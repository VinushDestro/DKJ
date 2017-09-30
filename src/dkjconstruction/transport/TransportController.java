/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.transport;

import java.sql.SQLException;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.sql.Date;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


/**
 * FXML Controller class
 *
 * @author User
 */
public class TransportController {

    @FXML
    private Label  tripId;
    @FXML
    private JFXTextField  tenderId;
    @FXML
    private JFXTextField  regNo;
    @FXML
    private JFXDatePicker  date;
    @FXML
    private JFXTextField  destination;
    @FXML
    private JFXTextField  cost;
    
    @FXML
    private TableView  transTab;
    
    @FXML
    private TableColumn  tabTripId;
    @FXML
    private TableColumn  tabRegNo;
    @FXML
    private TableColumn  tabTenderId;
    @FXML
    private TableColumn  tabDestination;
    @FXML
    private TableColumn  tabDate;
    @FXML
    private TableColumn  tabCost;
    
    @FXML
    private TextField search;

    public void initialize() {

        tabTripId.setCellValueFactory(new PropertyValueFactory<TransportDetail,String>("TripId"));
        tabRegNo.setCellValueFactory(new PropertyValueFactory<TransportDetail,String>("RegNo"));
	tabTenderId.setCellValueFactory(new PropertyValueFactory<TransportDetail,String>("TenderId"));
	tabDestination.setCellValueFactory(new PropertyValueFactory<TransportDetail,String>("destination"));
        tabDate.setCellValueFactory(new PropertyValueFactory<TransportDetail,Double>("date"));
        tabCost.setCellValueFactory(new PropertyValueFactory<TransportDetail,Integer>("cost"));
       
        try {
            transTab.setItems(Transport.getTransport());
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        RowclickEvent();
        

    }  

                 private void RowclickEvent() {
                 transTab.setOnMouseClicked((e) -> {
            TransportDetail t1 = (TransportDetail) transTab.getItems().get(transTab.getSelectionModel().getSelectedIndex());

            tenderId.setText(t1.getTenderId());
            tripId.setText(t1.getTripId());
            regNo.setText(t1.getRegNo());
            destination.setText(t1.getDestination());
            date.setValue(LocalDate.parse(t1.getDate()));
            cost.setText(t1.getCost());
        
        });

    }

    @FXML
    private void doAddTransport(ActionEvent event) throws SQLException, ClassNotFoundException {
        int result = 0 ;
        String addRegNo = regNo.getText().trim();
        String addTenderId = tenderId.getText().trim();
        String addDestination = destination.getText().trim();
        Double addCost = Double.parseDouble(cost.getText().trim());
        LocalDate addDate = date.getValue();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Transport");
        alert.setHeaderText(null);

        if (addTenderId.isEmpty() || addRegNo.isEmpty() || addDestination.isEmpty() || addCost.toString().isEmpty() || addDate.toString().isEmpty()) {
            alert.setContentText("All fields should be filled");
        }
        else {
            if(addCost <= 0){
                alert.setContentText("Invalid value for cost.\nShould be greater than zero.");
                }
            else{
                LocalDate today = LocalDate.now();
                if(addDate.isAfter(today)){
                    alert.setContentText("Invalid value for date.\nShould be less than current date.");
                }
                else{
                    try{
                        result = Transport.addTransport(addRegNo,addTenderId,addDestination,Date.valueOf(addDate),addCost);
                        if (result == 1) {
                            alert.setContentText("Operation Successful!");
                            tenderId.clear();
                            regNo.clear();
                            destination.clear();
                            cost.clear();
                            date.getEditor().setText(null);
                            
                            try {
                                transTab.setItems(Transport.getTransport());
                            } catch (IOException | ClassNotFoundException | SQLException e) {
                                e.printStackTrace();
                            }
                        } else {
                            alert.setContentText("Operation Failed");
                        }
                    }
                    catch (SQLException e1) {
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Error");
                        alert2.setHeaderText(null);
                        alert2.setContentText(e1.getMessage()+"\nOperation Failed");
                        alert2.show();
                        
                    }
                }
            }
        }
        alert.show();
    }
    
    @FXML
    private void doUpdateTransport(ActionEvent event) throws SQLException, ClassNotFoundException {
        int result = 0 ;
        String addTripId = tripId.getText().trim();
        Double addCost = Double.parseDouble(cost.getText().trim());
        LocalDate addDate = date.getValue();
        

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update Transport");
        alert.setHeaderText(null);

        if (addTripId.isEmpty()){
            alert.setContentText("Trip ID field cannot be empty");
        }
        else {
            if (addCost.toString().isEmpty() && addDate.toString().isEmpty()){
            alert.setContentText("Cost or date should be given for update");
            }
            else{
                if(addCost <= 0){
                    alert.setContentText("Invalid value for cost");
                    }
                else{
                    try{
                        result = Transport.updateTransport(addTripId,Date.valueOf(addDate),addCost);
                        if (result == 1) {
                            alert.setContentText("Operation Successful!");
                            alert.show();
                            cost.clear();
                            date.getEditor().setText(null);
                            try {
                                transTab.setItems(Transport.getTransport());
                            } catch (IOException | ClassNotFoundException | SQLException e) {
                                e.printStackTrace();
                            }
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
            }
        }
    }

    @FXML
    private void doDeleteTransport() throws SQLException {
        try {
            //int result = 0 ;
            String addTripId = tripId.getText().trim();
            
            if (addTripId.isEmpty() ) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Transport");
                alert.setHeaderText(null);
                alert.setContentText("Trip ID Field should be given.");
                alert.show();
            }
            else {
                int result = Transport.deleteTransport(addTripId);
                
                if (result == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Delete Transport");
                    alert.setHeaderText(null);
                    alert.setContentText("Deleted Successfully!");
                    alert.show();
                    try {
                                transTab.setItems(Transport.getTransport());
                            } catch (IOException | ClassNotFoundException | SQLException e) {
                                e.printStackTrace();
                            }
                }
                else if (result==0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Delete Transport");
                    alert.setHeaderText(null);
                    alert.setContentText("Deletion Failed! \n Trip ID Not Found!");
                    alert.show();
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TransportController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Error");
                        alert2.setHeaderText(null);
                        alert2.setContentText(ex.getMessage());
                        alert2.show();
        }

    }

    @FXML
    private void doSearchTransport(ActionEvent event) {
    }
    
}
