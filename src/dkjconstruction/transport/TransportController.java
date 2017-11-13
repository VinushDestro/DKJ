/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.transport;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import dkjconstruction.DbConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author User
 */
public class TransportController implements Initializable {

    @FXML
    private TableView<TransportDetail> transTab;
    @FXML
    private TableColumn tabTripId;
    @FXML
    private TableColumn tabRegNo;
    @FXML
    private TableColumn tabTenderId;
    @FXML
    private TableColumn tabDate;
    @FXML
    private TableColumn tabDestination;
    @FXML
    private TableColumn tabCost;
    @FXML
    private JFXTextField destination;
    @FXML
    private Label tripId;
    @FXML
    private ComboBox tenderId;
    @FXML
    private JFXDatePicker date;
    @FXML
    private JFXTextField cost;
    @FXML
    private ComboBox regNo;
    @FXML
    private TextField search;
    @FXML
    private Label text;

  
    private List myList;
    private ObservableList<TransportDetail> transportS; 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
     
            Connection con = DbConnection.getConnection();
            
            PreparedStatement ps;
        try {
            ps = con.prepareStatement("select tenderId from tender");
        
            ResultSet rs = ps.executeQuery();
            myList= FXCollections.observableArrayList();
            while(rs.next())
                myList.add(rs.getString(1));
            ObservableList data = FXCollections.observableList(myList);
            tenderId.setItems(data);
            } catch (SQLException ex) {
            Logger.getLogger(TransportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            ps = con.prepareStatement("select regNo from asset where availability='available'");
        
            ResultSet rs = ps.executeQuery();
            myList= FXCollections.observableArrayList();
            while(rs.next())
                myList.add(rs.getString(1));
            ObservableList data = FXCollections.observableList(myList);
            regNo.setItems(data);
            } catch (SQLException ex) {
            Logger.getLogger(TransportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        doSearchTransport();
        RowclickEvent();
        loadTable();
    }    

    @FXML
    private void doAddTransport(ActionEvent event) throws SQLException, ClassNotFoundException {
        int result = 0 ;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Transport");
        alert.setHeaderText(null);
        
        try{
            String addRegNo = regNo.getValue().toString().trim();
            String addTenderId = tenderId.getValue().toString().trim();
            String addDestination = destination.getText().trim();
            String addCost = cost.getText().trim();
            LocalDate addDate = date.getValue();


            if (addRegNo==null || addTenderId==null|| addDestination.isEmpty() || addCost==null || addDate==null) {
                alert.setContentText("All fields should be filled");
            }
            else if(Double.parseDouble(addCost) <= 0){
                    alert.setContentText("Invalid value for cost.\nShould be greater than zero.");
                    }
            else{
                LocalDate today = LocalDate.now();
                if(addDate.isBefore(today))
                    alert.setContentText("Invalid value for date.\nShould be after current date.");
                else{
                        result = Transport.addTransport(addRegNo,addTenderId,addDestination,Date.valueOf(addDate),Double.parseDouble(addCost));

                        if (result == 1) {
                            clearFields();
                            alert.setContentText("Operation Successful!");
                            try {
                                transTab.setItems(Transport.getTransport());
                            } catch (IOException | ClassNotFoundException | SQLException e) {
                                e.printStackTrace();
                            }
                        } 
                        else 
                            alert.setContentText("Operation Failed");

                }
            }
        }
        catch(NumberFormatException ee){
            alert.setContentText("Invalid value for cost");
        }
        alert.show();
        
    }

    @FXML
    private void doUpdateTransport(ActionEvent event) throws ClassNotFoundException, SQLException{
        
        int result = 0 ;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update Transport");
        alert.setHeaderText(null);

        try{
            System.out.println(tripId.getText().trim().toLowerCase());
            System.out.println(regNo.getValue());

            if("trip id".equals(tripId.getText().trim().toLowerCase())){
                alert.setContentText("TripId field cannot be empty.\n\nClick a row to do update.");
                alert.show();
            }

            else if (cost.getText().isEmpty() && date.getValue()==null && regNo.getValue()==null){
                alert.setContentText("Either Vehicle No, Cost or date should be given along with tripId for update");
                alert.show();
            }
            else{
                
                String addTripId = tripId.getText().trim();
                String addRegNo = regNo.getValue().toString().trim();
                String addCost = cost.getText().trim();
                LocalDate addDate = date.getValue();
                LocalDate today = LocalDate.now();
                
                if (!addCost.isEmpty() && !(addDate==null) && !(addRegNo==null) && !addTripId.isEmpty()){

                    if(date.getValue().isBefore(today)){
                        alert.setContentText("Invalid value for date.\nShould be after current date.");
                    }
                    else if(Double.parseDouble(cost.getText().trim()) <= 0.0){
                            alert.setContentText("Invalid value for cost.\nShould be greater than zero.");
                    }
                    else{
                        int x1 = Transport.updateTransport(Date.valueOf(addDate),addTripId);          //Date.valueOf(addDate),Double.parseDouble(addCost)
                        int x2 = Transport.updateTransport(Double.parseDouble(addCost),addTripId);  
                        int x3 = Transport.updateTransport(addRegNo,addTripId);  

                        if (x1==1 && x2==1 && x3==1)
                            result=1;
                    }
                }

                else if (addCost.isEmpty() && !(addDate==null) && !(addRegNo==null) && !addTripId.isEmpty()){

                    if(date.getValue().isBefore(today)){
                        alert.setContentText("Invalid value for date.\nShould be after current date.");
                    }
                    else{
                        int x1 = Transport.updateTransport(Date.valueOf(addDate),addTripId);          //Date.valueOf(addDate),Double.parseDouble(addCost)
                        int x3 = Transport.updateTransport(addRegNo,addTripId);  

                        if (x1==1 && x3==1)
                            result=1;
                    }
                }

                else if (!addCost.isEmpty() && (addDate==null) && !(addRegNo==null) && !addTripId.isEmpty()){

                    if(Double.parseDouble(cost.getText().trim()) <= 0.0){
                            alert.setContentText("Invalid value for cost.\nShould be greater than zero.");
                    }
                    else{
                        int x2 = Transport.updateTransport(Double.parseDouble(addCost),addTripId);  
                        int x3 = Transport.updateTransport(addRegNo,addTripId);  

                        if (x2==1 && x3==1)
                            result=1;
                    }
                }

                else if (!addCost.isEmpty() && !(addDate==null) && (addRegNo==null) && !addTripId.isEmpty()){

                    if(date.getValue().isBefore(today)){
                        alert.setContentText("Invalid value for date.\nShould be after current date.");
                    }
                    else if(Double.parseDouble(cost.getText().trim()) <= 0.0){
                            alert.setContentText("Invalid value for cost.\nShould be greater than zero.");
                    }
                    else{
                        int x1 = Transport.updateTransport(Date.valueOf(addDate),addTripId);  
                        int x2 = Transport.updateTransport(Double.parseDouble(addCost),addTripId);  

                        if (x1==1 && x2==1)
                            result=1;
                    }
                }

                else if (!addCost.isEmpty() && (addDate==null) && (addRegNo==null) && !addTripId.isEmpty()){

                    if(Double.parseDouble(cost.getText().trim()) <= 0.0){
                            alert.setContentText("Invalid value for cost.\nShould be greater than zero.");
                    }
                    else{
                        result = Transport.updateTransport(Double.parseDouble(addCost),addTripId);
                    }
                }

                else if (addCost.isEmpty() && !(addDate==null) && (addRegNo==null) && !addTripId.isEmpty()){

                    if(date.getValue().isBefore(today)){
                        alert.setContentText("Invalid value for date.\nShould be after current date.");
                    }
                    else{
                        result = Transport.updateTransport(Date.valueOf(addDate),addTripId); 
                    }
                }

                else if (addCost.isEmpty() && (addDate==null) && !(addRegNo==null) && !addTripId.isEmpty()){
                        result = Transport.updateTransport(addRegNo,addTripId);  

                }

                if (result == 1) {
                    clearFields();
                    alert.setContentText("Operation Successful!");
                    alert.show();
                    try {
                        transTab.setItems(Transport.getTransport());
                    } catch (IOException | ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                    }
                } 
                else if (result==0)
                    alert.setHeaderText("Operation Failed");
                    alert.show();
            }
        }
        catch(NumberFormatException ee){
            alert.setContentText("Invalid value for cost");
            alert.show();
        }
        
}

    @FXML
    private void doDeleteTransport(ActionEvent event) throws SQLException {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Transport");
            alert.setHeaderText(null);
            
            if("trip id".equals(tripId.getText().trim().toLowerCase())){
                alert.setContentText("TripId field cannot be empty.\n\nClick table row to do delete.");
                alert.show();
            }
            else {
                int result = Transport.deleteTransport(tripId.getText().trim());
                
                if (result == 1) {
                    clearFields();
                    alert.setContentText("Deleted Successfully!");
                    alert.show();
                    try {
                        transTab.setItems(Transport.getTransport());
                    } catch (IOException | ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                    }
                }
                else if (result==0){
                    clearFields();
                    alert.setContentText("Deletion Failed!");
                    alert.show();
                }
            }
            
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(TransportController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Error");
                        alert2.setHeaderText(null);
                        alert2.setContentText(ex.getMessage());
                        alert2.show();
        }

    }

    @FXML
    private void doReport(ActionEvent event) {
    }
    
    
    private void RowclickEvent() {
        transTab.setOnMouseClicked((e) -> {
            clearFields();
            
            try{
                TransportDetail t1 = (TransportDetail) transTab.getItems().get(transTab.getSelectionModel().getSelectedIndex());
                LocalDate today = LocalDate.now();
                
                if (t1.getDate().toLocalDate().isBefore(today)){
                    text.setText("Completed transport details cannot be either changed or deleted.");
                    tenderId.setDisable(true);
                    destination.setDisable(true);
                    tripId.setDisable(true);
                    regNo.setDisable(true);
                    cost.setDisable(true);
                    date.setDisable(true);
                }
                else{
                    tenderId.setValue(t1.getTenderId());
                    tenderId.setDisable(true);
                    tripId.setText(t1.getTripId());
                    regNo.setValue(t1.getRegNo());
                    destination.setText(t1.getDestination());
                    destination.setDisable(true);
                    date.setValue(t1.getDate().toLocalDate());
                    cost.setText(t1.getCost().toString());

                    text.setText("Only date, Reg No and cost can be updated");
                }
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
        tabTripId.setCellValueFactory(new PropertyValueFactory<>("TripId"));
        tabRegNo.setCellValueFactory(new PropertyValueFactory<>("RegNo"));
	tabTenderId.setCellValueFactory(new PropertyValueFactory<>("TenderId"));
	tabDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        tabDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tabCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
       
        try {
            transTab.setItems(Transport.getTransport());
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void clearFields(){
        tripId.setText("Trip ID");
        tenderId.getSelectionModel().clearSelection();
        regNo.getSelectionModel().clearSelection();
        destination.clear();
        cost.clear();
        date.getEditor().setText(null);
        text.setText(null);
        tenderId.setDisable(false);
        destination.setDisable(false);
        tripId.setDisable(false);
        regNo.setDisable(false);
        cost.setDisable(false);
        date.setDisable(false);
    }
    
    private void doSearchTransport() {
        search.setOnKeyReleased(e -> {
            if (search.getText().equals("")) {
                loadTable();

            }
            else{
                try {
                    Connection con = DbConnection.getConnection();
                    
                    PreparedStatement pst = con.prepareStatement
                            ("SELECT * FROM transport where tenderId LIKE '%" + search.getText() + "%'"
                            + "UNION SELECT * FROM transport where tripId LIKE '%" + search.getText() + "%'"
                            + "UNION SELECT * FROM transport where regNo LIKE '%" + search.getText() + "%'"
                            + "UNION SELECT * FROM transport where cost LIKE '%" + search.getText() + "%'"
                            + "UNION SELECT * FROM transport where destination LIKE '%" + search.getText() + "%' ");
                    ResultSet rs = pst.executeQuery();
                    transportS= FXCollections.observableArrayList();
                    while (rs.next()) {
                        String tripIdS=rs.getString(1);
                        String RegNoS=rs.getString(2);
                        String tenderIdS=rs.getString(3);
                        String destinationS=rs.getString(4);
                        Date dateS=rs.getDate(5);
                        Double costS=rs.getDouble(6);
                        
                        transportS.add(new TransportDetail(tripIdS,RegNoS,tenderIdS,destinationS,dateS,costS)); 
                        
                        }
                    transTab.setItems(transportS);

                } catch (SQLException ex) {
                    System.err.println("Error loading table data " + ex);

                }
            }
        });
    }
}
