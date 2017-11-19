/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.hr;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import dkjconstruction.DbConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author ASRAJ
 */
public class HRmanagement implements Initializable {

    @FXML
    private TableView<HRDetails> hremployee;
    @FXML
    private TableColumn<?, ?> hemployeeid;
    @FXML
    private TableColumn<?, ?> hemployeename;
    @FXML
    private TableColumn<?, ?> haddress;
    @FXML
    private TableColumn<?, ?> hcontectno;
    @FXML
    private TableColumn<?, ?> hgender;
    @FXML
    private TableColumn<?, ?> hdob;
    @FXML
    private TableColumn<?, ?> hposition;
    @FXML
    private TableColumn<?, ?> hnic;
    @FXML
    private TableColumn<?, ?> hemployeetype;
    @FXML
    private TableColumn<?, ?> hbasicsalary;
    @FXML
    private JFXTextField hremployeename;
    @FXML
    private JFXTextField hremployeeid;
    @FXML
    private JFXComboBox hrgender;
    @FXML
    private JFXDatePicker hrdob;
    @FXML
    private JFXTextField hraddress;
    @FXML
    private JFXTextField hrcontactno;
    @FXML
    private JFXComboBox hrposition;
    @FXML
    private JFXTextField hrnic;
    @FXML
    private JFXComboBox hremployeetype;
    @FXML
    private JFXTextField hrbasicsalary;
    @FXML
    private JFXTextField hrdaysalary;
    @FXML
    private JFXTextField hsearch;

    private ObservableList data;
    @FXML
    private TableColumn<?, ?> hdaysalary;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        hrgender.getItems().addAll("Male", "Female");
        hrposition.getItems().addAll("Manager","supervisor", "Employee");
        hremployeetype.getItems().addAll("Permanent","Contract based");

        hrbasicsalary.setVisible(false);
        hrdaysalary.setVisible(false);

        data = FXCollections.observableArrayList();
        setTable();
        getDB();

        RowclickEvent();
        searchEmployee();
        hremployeeid.setDisable(true);

    }

    @FXML
    private void empTypeClicked() {

        if (hremployeetype.getValue().equals("Permanent")) {
            hrbasicsalary.setVisible(true);
            hrdaysalary.setText(null);
            hrdaysalary.setVisible(false);

        } else if (hremployeetype.getValue().equals("Contract based")) {
            hrdaysalary.setVisible(true);
            hrbasicsalary.setText(null);
            hrbasicsalary.setVisible(false);
        }

    }

//ADD EMPLOYEE
    public void InsertDB(String empType) {

        try {
            String salType = null;
            double sal = 0;

            if (empType.equals("Permanent")) {
                salType = "basicSalary";
                sal = Double.parseDouble(hrbasicsalary.getText());

            }

            if (empType.equals("Contract based")) {
                salType = "daySalary";
                sal = Double.parseDouble(hrdaysalary.getText());

            }

            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            PreparedStatement stmt = con.prepareStatement("Insert into employee (name,address,gender,contactNo,position,empType,dob,nic," + salType + ") values (?,?,?,?,?,?,?,?,? )");

//            stmt.setString(1, hremployeeid.getText());
            stmt.setString(1, hremployeename.getText());
            stmt.setString(2, hraddress.getText());
            stmt.setString(3, hrgender.getValue().toString());
            stmt.setString(4, hrcontactno.getText());
            stmt.setString(5, hrposition.getValue().toString());
            stmt.setString(6, hremployeetype.getValue().toString());
            stmt.setDate(7, java.sql.Date.valueOf(hrdob.getValue()));
            stmt.setString(8, hrnic.getText());
            stmt.setString(9, "" + sal);

            stmt.executeUpdate();

            alerboxInfo("Add Employee", "Value added successfully");
            System.out.println("success");

        } catch (SQLIntegrityConstraintViolationException r) {

            alertboxWarn("Add Employee", "This details already has been taken");
        } catch (Exception e) {
            System.out.println(" Error");
            System.err.println(e);

        }
    }

    @FXML
    private void AddEmployee(ActionEvent event) throws ClassNotFoundException, SQLException {
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            
            
            if (validatefields()) {
                InsertDB(hremployeetype.getValue().toString());
                
            }
            
            setTable();
            getDB();
        } catch (SQLException ex) {
            Logger.getLogger(HRmanagement.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setTable() {

        try {

            hemployeeid.setCellValueFactory(new PropertyValueFactory<>("EmpId"));
            hemployeename.setCellValueFactory(new PropertyValueFactory<>("Name"));
            haddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
            hnic.setCellValueFactory(new PropertyValueFactory<>("Nic"));
            hdob.setCellValueFactory(new PropertyValueFactory<>("Dob"));
            hgender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
            hcontectno.setCellValueFactory(new PropertyValueFactory<>("ContactNo"));
            hposition.setCellValueFactory(new PropertyValueFactory<>("Position"));
            hemployeetype.setCellValueFactory(new PropertyValueFactory<>("EmpType"));
            hbasicsalary.setCellValueFactory(new PropertyValueFactory<>("BasicSalary"));
            hdaysalary.setCellValueFactory(new PropertyValueFactory<>("DaySalary"));

        } catch (Exception e) {
            System.out.println("Error loading table" + e);
        }

    }

    private void getDB() {
        data.clear();
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("SELECT * from employee");

            while (rs.next()) {
                
                data.add(new HRDetails(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), "" + rs.getDate(5), rs.getString(6), "" + rs.getString(7), rs.getString(8), rs.getString(9), "" + rs.getDouble(10), ""+ rs.getDouble(12)));
            }

            hremployee.setItems(data);
        } catch (Exception e) {
            System.out.println("error loading observable list " + e);
        }

    }
//GENERATE REPORT

    @FXML
    private void GenerateEmployeeReport(ActionEvent event) {

    }
//UPDATE EMPLOYEE

    @FXML
    private void UpdateEmployee(ActionEvent event) {
        
        String s;
        try {
            
            if (validatefields()) {
        
        if(hremployeetype.getValue().equals("Permanent" ))
         {
             System.out.println("basic : " + hrbasicsalary.getText());
            updateEmployee("basicSalary", Double.parseDouble(hrbasicsalary.getText()));
            
            
         }
        
        if(hremployeetype.getValue().equals("Contract based"))
        {
            s = "daySalary";
            System.out.println("cont : " + hrdaysalary.getText());
            updateEmployee(s, Double.parseDouble(hrdaysalary.getText()));
            
        }
         }
        //setTable();
        getDB();
            
        } catch (Exception e) {
            System.out.println("error" + e);
        }
        
        
                }

    public void updateEmployee(String salType,double sal)
    {
    
    

            try {
                DbConnection.openConnection();
               
                Connection con = DbConnection.getConnection();
                
                PreparedStatement stmt = con.prepareStatement("UPDATE employee set empid= '" + hremployeeid.getText() + "',name = '" + hremployeename.getText() + "',address= '" + hraddress.getText() + "',nic= '" + hrnic.getText() + "',dob= '" + hrdob.getValue() + "',gender= '" + hrgender.getValue() + "',contactNo = '" + hrcontactno.getText() + "',position = '" + hrposition.getValue() + "',daySalary = '" + sal + "'  where empId = '" + hremployeeid.getText() + "' ");

                stmt.executeUpdate();
                alerboxInfo("Update Employee", "Value updated successfully");

            } catch (Exception e) {
                System.out.println("Update  error" + e);
            }
       
    
    }

//Alertbox Information
    public void alerboxInfo(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setWidth(500);
        alert.setHeight(300);
        alert.setContentText(message);
        alert.showAndWait();

    }

//Alertbox warning
    public void alertboxWarn(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setWidth(500);
        alert.setHeight(300);
        alert.setContentText(message);
        alert.showAndWait();

    }

//Alertbox Confirmation
    public boolean alertboxConfirm(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("Confirmation");
        alert.setWidth(500);
        alert.setHeight(300);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }

    }

    private void keytyped(javafx.scene.input.KeyEvent event) {

        if (!(event.getCode().isDigitKey())) {

        }

    }

//VALIDATION
    private boolean validatefields() {
        if ( hremployeename.getText().isEmpty() || (hraddress.getText().isEmpty())
                || (hrcontactno.getText().isEmpty()) || (hrdob.getValue() == null) || (hrgender.getValue() == null)
                || (hrposition.getValue() == null)
                || hrnic.getText().isEmpty() || (hremployeetype.getValue() == null)) {
            
            alertboxWarn("Add Employee", "All fields should be filled !");

            return false;

        } 
        
        else if (hremployeetype.getValue().equals("Permanent") && hrbasicsalary.getText() == null) {

            alertboxWarn("Add Employee", "All fields should be filled !");
            return false;
        } 
        
        else if (hremployeetype.getValue().equals("Contract based") && hrdaysalary.getText() == null) {
            
            alertboxWarn("Add Employee", "All fields should be filled !");
            return false;
        } 
        else {
            
            return true;
            
        }

    }

    //search
    private void searchEmployee() {
        hsearch.setOnKeyReleased(e -> {
            if (hsearch.getText().equals("")) {
                getDB();
            } else {
                data.clear();
                try {

                    DbConnection.openConnection();
                    Connection con = DbConnection.getConnection();

                    ResultSet rs = con.createStatement().executeQuery("SELECT * FROM employee where empId LIKE '%" + hsearch.getText() + "%'"
                            + "UNION SELECT * FROM employee where name LIKE '%" + hsearch.getText() + "%'"
                            + "UNION SELECT * FROM employee where address LIKE '%" + hsearch.getText() + "%'"
                            + "UNION SELECT * FROM employee where nic LIKE '%" + hsearch.getText() + "%'"
                            + "UNION SELECT * FROM employee where dob LIKE '%" + hsearch.getText() + "%'"
                            + "UNION SELECT * FROM employee where gender LIKE '%" + hsearch.getText() + "%' "
                            + "UNION SELECT * FROM employee where position LIKE '%" + hsearch.getText() + "%' "
                            + "UNION SELECT * FROM employee where empType LIKE '%" + hsearch.getText() + "%' "
                            + "UNION SELECT * FROM employee where basicSalary LIKE '%" + hsearch.getText() + "%' ");
                    while (rs.next()) {
                        data.add(new HRDetails(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), "" + rs.getDate(5), rs.getString(6), "" + rs.getString(7), rs.getString(8), rs.getString(9), "" + rs.getDouble(10),""+ rs.getDouble(12)));

                    }

                    hremployee.setItems(data);

                } catch (Exception ex) {
                    System.err.println("Error loading table data 11 " + ex);

                }

            }

        });

    }

//DELETE EMPLOYEE
    @FXML
    private void DeleteEmployee(ActionEvent event) {

        if (alertboxConfirm("Delete Employee details !", "Do you really want to Delete ?")) {

            try {
                DbConnection.openConnection();
                Connection con = DbConnection.getConnection();
                PreparedStatement stmt = con.prepareStatement("DELETE from employee where empId = '" + hremployeeid.getText() + " '");
                stmt.executeUpdate();
                System.out.println("success");
                setTable();
                getDB();

            } catch (Exception e) {
                System.out.println("error " + e);
            }

        }

    }
//CLEAR
    @FXML
    private void Clear(ActionEvent event){
      
        hremployeeid.clear();
        hremployeename.clear();    
        hraddress.clear();
        hrcontactno.clear();
        hrgender.setValue(null);
        hrdob.setValue(null);
        hrposition.setValue(null);
        hrnic.clear();
        hremployeetype.setValue(null);
        hrdaysalary.clear();
        hrbasicsalary.clear();
       
    }    

    private void RowclickEvent() {

        hremployee.setOnMouseClicked((e) -> {
            HRDetails t1 = hremployee.getItems().get(hremployee.getSelectionModel().getSelectedIndex());

            hremployeeid.setText(t1.getEmpId());
            hremployeename.setText(t1.getName());
            hraddress.setText(t1.getAddress());
            hrnic.setText(t1.getNic());
            hrdob.setValue(LocalDate.parse(t1.getDob()));
            hrgender.setValue(t1.getGender());
            hrcontactno.setText(t1.getContactNo());
            hrposition.setValue(t1.getPosition());
            hremployeetype.setValue(t1.getEmpType());
            
            if(t1.getEmpType().equals("Permanent"))
            {
            hrdaysalary.setVisible(false);
            hrbasicsalary.setText(t1.getBasicSalary());
            }
            
            if(t1.getEmpType().equals("Contract based"))
            {
                
            hrbasicsalary.setVisible(false);
            hrdaysalary.setText(t1.getDaySalary());
            
            }

        });

    }

}
