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
        
        String addRegNo="";
        String addTenderId="";
        String addDestination="";
        Double addCost=0.0;
        LocalDate addDate=LocalDate.now();;
        try{
            addRegNo = regNo.getValue().toString().trim();
            addTenderId = tenderId.getValue().toString().trim();
            addDestination = destination.getText().trim();
            addCost = Double.parseDouble(cost.getText().trim());
            addDate = date.getValue();
        }
        catch(NumberFormatException ee){
            alert.setContentText("Cost cannot have characters");
        }
        
        if (regNo.getValue()==null || tenderId.getValue()==null|| destination.getText().isEmpty() || cost.getText().trim()==null || date.getValue()==null) {
            alert.setContentText("All fields should be filled");
        }
        else {
            if(Double.parseDouble(cost.getText().trim()) <= 0){
                alert.setContentText("Invalid value for cost.\nShould be greater than zero.");
                }
            else{
                LocalDate today = LocalDate.now();
                if(date.getValue().isAfter(today)){
                    alert.setContentText("Invalid value for date.\nShould be less than current date.");
                }
                else{
                    result = Transport.addTransport(addRegNo,addTenderId,addDestination,Date.valueOf(addDate),addCost);

                    if (result == 1) {
                        tenderId.getItems().clear();
                        regNo.getItems().clear();
                        destination.clear();
                        cost.clear();
                        date.getEditor().setText(null);
                        alert.setContentText("Operation Successful!");
                        try {
                            transTab.setItems(Transport.getTransport());
                        } catch (IOException | ClassNotFoundException | SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        alert.setContentText("Operation Failed");
                    }
                }
            }
        }
        alert.show();
        
    }

    @FXML
    private void doUpdateTransport(ActionEvent event) throws ClassNotFoundException, SQLException{
        
        int result = 0 ;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update Transport");
        alert.setHeaderText(null);

        if (cost.getText().isEmpty() && date.getValue()==null){
            alert.setContentText("Cost or date should be given along with tripId for update");
        }
        else {
            for(int i=0;i<=cost.getLength();i++){
                if(Character.isDigit(cost.getText().charAt(i))==false)
                    alert.setContentText("Cost invalid");
            }
            LocalDate today = LocalDate.now();
            if(date.getValue().isAfter(today)){
                            alert.setContentText("Invalid value for date.\nShould be less than current date.");
                            alert.show();
                        }    
        
            else{
                
                LocalDate addDate;
                Double addCost;
                String addTripId = tripId.getText().trim();
                if (cost.getText().isEmpty()){
                    addDate = date.getValue();
                    addCost = 0.0;
                    
                    if(date.getValue().isAfter(today)){
                        alert.setContentText("Invalid value for date.\nShould be less than current date.");
                    }
                }
                else {
                    if (date.getValue()==null){
                        addCost = Double.parseDouble(cost.getText().trim());
                        addDate = LocalDate.now();
                        if(Double.parseDouble(cost.getText().trim()) <= 0.0){
                            alert.setContentText("Invalid value for cost.\nShould be greater than zero.");
                        }
                    }
                    else{
                        addDate = date.getValue();
                        addCost = Double.parseDouble(cost.getText().trim());
                        
                        if(Double.parseDouble(cost.getText().trim()) <= 0.0){
                            alert.setContentText("Invalid value for cost.\nShould be greater than zero.");
                            alert.show();
                        }
                    }
                }
                try {
                    result = Transport.updateTransport(Date.valueOf(addDate),addCost,addTripId);
                } catch (SQLException ex) {
                    Logger.getLogger(TransportController.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (result == 1) {
                    alert.setContentText("Operation Successful!");
                    alert.show();
                    tripId.setText("");
                    cost.clear();
                    date.getEditor().setText(null);
                    try {
                        transTab.setItems(Transport.getTransport());
                    } 
                    catch (IOException | ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    alert.setContentText("Operation Failed");
                }
            }
        }
        alert.show();
    }

    @FXML
    private void doDeleteTransport(ActionEvent event) throws SQLException {
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
    private void doReport(ActionEvent event) {
    }
    
    
    private void RowclickEvent() {
        transTab.setOnMouseClicked((e) -> {
            try{
                TransportDetail t1 = (TransportDetail) transTab.getItems().get(transTab.getSelectionModel().getSelectedIndex());

                tenderId.setValue(t1.getTenderId());
                tripId.setText(t1.getTripId());
                regNo.setValue(t1.getRegNo());
                destination.setText(t1.getDestination());
                date.setValue(t1.getDate().toLocalDate());
                cost.setText(t1.getCost().toString());

                text.setText("Only either date or cost or both could be changed");
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
