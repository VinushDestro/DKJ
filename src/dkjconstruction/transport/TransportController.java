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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

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
    @FXML
    private Button update;
    @FXML
    private Button delete;

  
    private List myList;
    private ObservableList<TransportDetail> transportS; 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        setCombo();
        doSearchTransport();
        RowclickEvent();
        loadTable();
    }    

    public void setCombo(){
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
            ps = con.prepareStatement("select regNo from asset where availability='available' and type='transport'");
        
            ResultSet rs = ps.executeQuery();
            myList= FXCollections.observableArrayList();
            while(rs.next())
                myList.add(rs.getString(1));
            ObservableList data = FXCollections.observableList(myList);
            regNo.setItems(data);
            } catch (SQLException ex) {
            Logger.getLogger(TransportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void doAddTransport(ActionEvent event) throws SQLException, ClassNotFoundException {
        int result = 0 ;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Transport");
        alert.setHeaderText(null);
        
        try{
            //String addRegNo = regNo.getValue().toString().trim();
            //String addTenderId = tenderId.getValue().toString().trim();
            String addDestination = destination.getText().trim();
            String addCost = cost.getText().trim();
            //LocalDate addDate = date.getValue();


            if (regNo.getValue()==null || tenderId.getValue()==null|| addDestination.isEmpty() || addCost==null || date.getValue()==null) {
                alert.setContentText("All fields should be filled");
            }
            else if(Double.parseDouble(addCost) <= 0){
                    alert.setContentText("Invalid value for cost.\nShould be greater than zero.");
                    }
            else{
                LocalDate today = LocalDate.now();
                if(date.getValue().isAfter(today))
                    alert.setContentText("Invalid value for date.\nShould be a passed date.");
                else{
                        String addRegNo = regNo.getValue().toString().trim();
                        String addTenderId = tenderId.getValue().toString().trim();
                        LocalDate addDate = date.getValue();
                        Connection con = DbConnection.getConnection();

                        PreparedStatement pst = con.prepareStatement("Select regno,date from transport where regno = ? and date = ? and tenderid=?");

                        pst.setString(1,addRegNo);
                        pst.setDate(2,Date.valueOf(addDate));
                        pst.setString(3,addTenderId);
                        ResultSet rs = pst.executeQuery();
                        if(rs.next()){
//                            String reg = rs.getString("regno");
//                            Date d = rs.getDate("date");
//                            if (addRegNo.toLowerCase().equals(reg.toLowerCase()) &&  Date.valueOf(addDate).equals(d)) 
                            alert.setContentText("Duplicate Entry");
                        }
                        else
                            result = Transport.addTransport(addRegNo,addTenderId,addDestination,Date.valueOf(addDate),Double.parseDouble(addCost));

                        if (result == 1) {
                            PreparedStatement stmt = con.prepareStatement("update asset set availability='assigned' where regno=?");
                            stmt.setString(1,addRegNo);
                            result = stmt.executeUpdate();

                            clearFields();
                            setCombo();
                            alert.setContentText("Operation Successful!");
                            try {
                                transTab.setItems(Transport.getTransport());
                            } catch (IOException | ClassNotFoundException | SQLException e) {
                                e.printStackTrace();
                            }
                        } 
                        else 
                            alert.setHeaderText("Operation Failed");

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
                alert.setContentText("Required fields are empty.\n\nClick a row to do update.");
                alert.show();
            }

            else if (cost.getText().isEmpty() || date.getValue()==null || regNo.getValue()==null){
                alert.setContentText("Destination, Cost and date should be given along with tripId.");
                alert.show();
            }
            
            else{
                String addTripId = tripId.getText().trim();
                String addRegNo = regNo.getValue().toString();
                String addDestination = destination.getText().trim();
                String addCost = cost.getText().trim();
                LocalDate addDate = date.getValue();
                LocalDate today = LocalDate.now();
                Connection con = DbConnection.getConnection();

                    if(date.getValue().isAfter(today)){
                        alert.setContentText("Invalid value for date.\nShould be passed date of a completed transport.");
                    }
                    else if(Double.parseDouble(cost.getText().trim()) <= 0.0){
                            alert.setContentText("Invalid value for cost.\nShould be greater than zero.");
                    }
                    else{
                        result = Transport.updateTransport(addDestination,Date.valueOf(addDate),Double.parseDouble(addCost),addTripId);          //Date.valueOf(addDate),Double.parseDouble(addCost)
                        
                    }
                

                if (result == 1) {
                    PreparedStatement stmt = con.prepareStatement("update asset set availability='available' where regno=?");
                            stmt.setString(1,addRegNo);
                            result = stmt.executeUpdate();
                    clearFields();
                    setCombo();
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
                    Connection con = DbConnection.getConnection();

                    PreparedStatement stmt = con.prepareStatement("update asset set availability='available' where regno=?");
                            stmt.setString(1,regNo.getValue().toString());
                            result = stmt.executeUpdate();
                    setCombo();
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
    private void doReport(){
        try{DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        String report = "C:\\Users\\Mahesh\\Documents\\NetBeansProjects\\dkjconstructions\\src\\dkjconstruction\\transport\\transport.jrxml";
        JasperReport jr = JasperCompileManager.compileReport(report);
        JasperPrint jp = JasperFillManager.fillReport(jr,null,con);
        JasperViewer.viewReport(jp,false);
        }
        catch(Exception e){
            System.out.println(e);
        }
            
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
                    update.setVisible(false);
                    delete.setVisible(false);
                }
                else{
                    update.setVisible(true);
                    delete.setVisible(true);
                    tenderId.setValue(t1.getTenderId());
                    tenderId.setDisable(true);
                    tripId.setText(t1.getTripId());
                    regNo.setValue(t1.getRegNo());
                    destination.setText(t1.getDestination());
                    regNo.setDisable(true);
                    date.setValue(t1.getDate().toLocalDate());
                    cost.setText(t1.getCost().toString());

                    text.setText("Only date,Destination and cost can be updated");
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
        search.setText(null);
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
        update.setVisible(true);
        delete.setVisible(true);
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
                            + "UNION SELECT * FROM transport where date LIKE '%" + search.getText() + "%'"
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
