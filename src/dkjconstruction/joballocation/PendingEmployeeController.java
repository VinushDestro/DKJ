/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.joballocation;

import com.jfoenix.controls.JFXTextField;
import dkjconstruction.DbConnection;
import java.io.IOException;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ranjitha
 */
public class PendingEmployeeController implements Initializable {
    
      private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    
     private ObservableList<JobEmployee> dataKISHpending;
    private ObservableList<EMPLOYEE> dataASRAJpending;
     //private ObservableList<Tender> dataTenderpending;

    @FXML
    private JFXTextField pendingtenderEmployeeId;
    @FXML
    private  JFXTextField pendingKTid;
    @FXML
    private  JFXTextField searchfield;
    @FXML
    private TableView pendingkishtbl;
    @FXML
    private TableColumn pendingkTenderID;
    @FXML
    private TableColumn pendingreqEmpCount;
    @FXML
    private TableColumn pendingassignedEmpCount;
    @FXML
    private TableView pendingemployeeTable;
    @FXML
    private TableColumn pendingemployeeId;
    @FXML
    private TableColumn pendingemployeeName;
    @FXML
    private TableColumn pendingemployeeType;
    public static String tender; 
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dataKISHpending = FXCollections.observableArrayList();
        dataASRAJpending = FXCollections.observableArrayList();
      //  dataTenderpending = FXCollections.observableArrayList();
      
      pendingtenderEmployeeId.setDisable(true);
      pendingKTid.setDisable(true);
    
        
        setTenderTablepending();
        loadFromTenderDBpending();
        setEmployeeTablepending();
        loadFromEmployeeDBpending();
        RowclickEvent8();
        RowclickEvent9();
        search();
        
        String pendingaddTender = pendingKTid.getText();
       
            tender=pendingaddTender;
    }    

     private void setTenderTablepending() {
        try {
            DbConnection.openConnection();
           Connection con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            pendingkTenderID.setCellValueFactory(new PropertyValueFactory<>("tenderId"));
            pendingreqEmpCount.setCellValueFactory(new PropertyValueFactory<>("empCount"));
            pendingassignedEmpCount.setCellValueFactory(new PropertyValueFactory<>("assigncount"));
        } catch (Exception e) {
            System.out.println("ranjithatender");
            System.err.println("Error set table data " + e);
        }

    }

    private void loadFromTenderDBpending() {

        dataKISHpending.clear();
        try {

            Connection con = DbConnection.getConnection();
                Connection con4 = DbConnection.getConnection();
             pst = con.prepareStatement("select tenderId,noOfEmployee,assignCount from jobemployee where tenderId IN (select tenderId from tender where status='Pending')");
             rs = pst.executeQuery();
                while (rs.next()) {
                dataKISHpending.add(new JobEmployee(rs.getString(1), rs.getInt(2), rs.getInt(3)));
                //  dataKISH.add(new KISHANTH(null, null, null));
                
            }
             
        } catch (Exception e) {
            System.out.println("ranjithatender");
            System.err.println("Error loading table data " + e);

        }
        pendingkishtbl.setItems(dataKISHpending);
    }

   
    private void setEmployeeTablepending() {
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            pendingemployeeId.setCellValueFactory(new PropertyValueFactory<>("empId"));
            pendingemployeeName.setCellValueFactory(new PropertyValueFactory<>("empName"));

            pendingemployeeType.setCellValueFactory(new PropertyValueFactory<>("type"));

        } catch (Exception e) {
            System.err.println("Error set pending employee table data " + e);
            System.err.println("ranjithaemp ");
        }

    }

    private void loadFromEmployeeDBpending() {

        dataASRAJpending.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("select empId,name,empType from employee where availability='available'");
            rs = pst.executeQuery();
             String kish;
            while (rs.next()) {
                dataASRAJpending.add(new EMPLOYEE(rs.getString(1), rs.getString(2), rs.getString(3)));
                //  dataKISH.add(new KISHANTH(null, null, null, null, null));
                kish=rs.getString(1);
                 System.err.println(kish);
            }

        } catch (Exception e) {
            System.out.println("ranjithaemp");
            System.err.println("Error loading pending employee table data " + e);

        }
        pendingemployeeTable.setItems(dataASRAJpending);

    }
    

    
    
    
    @FXML
    public void pendingaddTenderEmployee(ActionEvent event) {
        
         try {
              String pendingaddTender = pendingKTid.getText();
            String pendingaddEmployee = pendingtenderEmployeeId.getText();
            tender=pendingaddTender;
           
            
            System.out.println(pendingaddTender);
            System.out.println(pendingaddEmployee);

            if (pendingaddTender.isEmpty() || pendingaddEmployee.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Fields cannot be empty");
                alert.show();
                pendingKTid.clear();
                pendingtenderEmployeeId.clear();
            }
            else{
            DbConnection.openConnection();
            Connection con4 = DbConnection.getConnection();
            PreparedStatement stmt = con4.prepareStatement("insert into emptender (tenderId,empId) values(?,?)");

            stmt.setString(1, pendingaddTender);
            stmt.setString(2, pendingaddEmployee);
            stmt.executeUpdate();
            System.out.println("success");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SUCCESS");
            alert.setHeaderText(null);
            alert.setContentText("Employee Added Successfully");
            alert.show();

            PreparedStatement stmt1 = con4.prepareStatement("UPDATE employee SET availability = 'assigned' WHERE empId =?");
            stmt1.setString(1, pendingaddEmployee);
            stmt1.executeUpdate();
            
            PreparedStatement stmt3 = con4.prepareStatement("UPDATE jobemployee SET assignCount =assignCount+1 WHERE tenderId =?");
            stmt3.setString(1, pendingaddTender);
            stmt3.executeUpdate();
            
           /*  PreparedStatement stmt2 = con4.prepareStatement("UPDATE tender SET status = 'on going' WHERE tenderId=?");
            stmt2.setString(1, pendingaddTender);
            stmt2.executeUpdate();*/
            
            }

        } catch (Exception e) {

            System.out.println("error" + e);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("error" + e);
            alert.show();
        }
         pendingKTid.clear();
         pendingtenderEmployeeId.clear();
         loadFromEmployeeDBpending();
         loadFromTenderDBpending();
    }
    
     private void RowclickEvent8() {
        pendingkishtbl.setOnMouseClicked((e)
                -> {
            JobEmployee v1 = (JobEmployee) pendingkishtbl.getItems().get(pendingkishtbl.getSelectionModel().getSelectedIndex());
            pendingKTid.setText(v1.getTenderId());

           // joballocation.loadFromAssetDB();
            
        });

    }

    private void RowclickEvent9() {
        pendingemployeeTable.setOnMouseClicked((e)
                -> {
            EMPLOYEE v1 = (EMPLOYEE) pendingemployeeTable.getItems().get(pendingemployeeTable.getSelectionModel().getSelectedIndex());
            pendingtenderEmployeeId.setText(v1.getEmpId());

        });
    }
     @FXML
     private void nextClicked(ActionEvent event) throws IOException {
  dkjconstruction.DKJConstruction.showPendingAsset();
       
    }
     
     @FXML
     private void backClicked(ActionEvent event) throws IOException {
      dkjconstruction.DKJConstruction.showJobAllocation();

     }  
     
     public void search() {
        searchfield.setOnKeyReleased(e -> {
            if (searchfield.getText().equals("")) {
              //  loadFromTenderDB();
                loadFromTenderDBpending();
            } else {
                dataKISHpending.clear();
                try {

                    Connection con = DbConnection.getConnection();

                    pst = con.prepareStatement("select tenderId,noOfEmployee,assignCount from jobemployee where tenderId  LIKE '%" + searchfield.getText() + "%' ");
                    rs = pst.executeQuery();

                    while (rs.next()) {
                        dataKISHpending.add(new JobEmployee(rs.getString(1), rs.getInt(2), rs.getInt(3)));
                    }
                    System.out.println("Search clicked");
                } catch (Exception ex) {
                    System.err.println("Error loading table data search table jobemployee" + ex);

                }

                pendingkishtbl.setItems(dataKISHpending);

            }

        });//event
    }
}
