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
import java.util.Optional;
import java.util.ResourceBundle;
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
    private TableColumn<?, ?> hgenderh;
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
    private JFXTextField hsearch;
    private ObservableList data;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        hrgender.getItems().addAll("Male","Female");
        hrposition.getItems().addAll("Manager","supervisor","Worker");
        hremployeetype.getItems().addAll("Permenent employee","Contract based");
        
        data = FXCollections.observableArrayList();
        setTable();
        getDB();
        
        RowclickEvent();
        searchEmployee();
        
    }    
//ADD EMPLOYEE
    @FXML
    private void AddEmployee(ActionEvent event) {
        if (validatefields()) {
           
        try {      
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement("Insert into employee (empId,name,address,gender,contactNo,position,empType,dob,nic,basicSalary) values (?,?,?,?,?,?,?,?,?,?)");
        
        String empid = hremployeeid.getText();
        String name = hremployeename.getText();
        String address = hraddress.getText();
      //String dob = hrdob.getText();
        String gender = hrgender.getValue().toString();
        String contactNo = hrcontactno.getText();
        String position = hrposition.getValue().toString();
        String emptype = hremployeetype.getValue().toString();
      //double basicSalary = Double.parseDouble(hremployeeid.getText()); 
      
        
       
        stmt.setString(1,empid);
        stmt.setString(2,name);
        stmt.setString(3,address);
      //stmt.setString(4,dob);
        stmt.setString(4,gender);
        stmt.setString(5,contactNo);
        stmt.setString(6,position);
        stmt.setString(7,emptype);
      //stmt.setDouble(9,basicSalary);
        stmt.setDate(8, java.sql.Date.valueOf(hrdob.getValue()));
        stmt.setString(9, hrnic.getText());
        stmt.setDouble(10, Double.parseDouble(hrbasicsalary.getText()));
       
       
        
       stmt.executeUpdate();
        alerboxInfo("Add Employee", "Value added successfully");
       System.out.println("success");
     
        } catch (SQLIntegrityConstraintViolationException r) {

                alertboxWarn("Add Employee", "This Id already has been taken");
            } catch (Exception e) {
                System.out.println(" Error");
                System.err.println(e);

            }
        }
         
         setTable();
        getDB();
        
    }
    
    private void setTable(){
    
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            
            hemployeeid.setCellValueFactory(new PropertyValueFactory<>("EmpId"));
            hemployeename.setCellValueFactory(new PropertyValueFactory<>("Name"));
            haddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
            hnic.setCellValueFactory(new PropertyValueFactory<>("Nic"));
            hdob.setCellValueFactory(new PropertyValueFactory<>("Dob"));
            hgenderh.setCellValueFactory(new PropertyValueFactory<>("Gender"));
            hcontectno.setCellValueFactory(new PropertyValueFactory<>("ContactNo"));
            hposition.setCellValueFactory(new PropertyValueFactory<>("Position"));
            hemployeetype.setCellValueFactory(new PropertyValueFactory<>("EmpType"));
            hbasicsalary.setCellValueFactory(new PropertyValueFactory<>("BasicSalary"));
            
            
            
        } catch (Exception e) {
        }
    
    }
    
    private void getDB()
    {
    data.clear();
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            
            ResultSet rs = con.createStatement().executeQuery("SELECT * from employee");
            
             while(rs.next())
             {
             data.add(new HRDetails(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),""+ rs.getDate(5), rs.getString(6),""+ rs.getString(7), rs.getString(8), rs.getString(9),""+ rs.getDouble(10)));
             
             }
             
                    
            hremployee.setItems(data);
        } catch (Exception e) {
        }
        
    }
//GENERATE REPORT
    @FXML
    private void GenerateEmployeeReport(ActionEvent event) {
        
        
        
        
    }
//UPDATE EMPLOYEE
    @FXML
    private void UpdateEmployee(ActionEvent event) {
         
         if (validatefields()) {
           
       try { DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
         PreparedStatement stmt = con.prepareStatement("UPDATE employee set empid= '" +hremployeeid.getText() + "',name = '" + hremployeename.getText() + "',address= '" + hraddress.getText()  + "',nic= '" +hrnic.getText()+  "',dob= '" + hrdob.getValue()+ "',gender= '" + hrgender.getValue() + "',contactNo = '" + hrcontactno.getText() +  "',position = '" + hrposition.getValue() + "',basicSalary = '" + Double.parseDouble(hrbasicsalary.getText()) + "'  where empType = '" +hremployeetype.getValue()+ "' ");
               
                 
        
            stmt.executeUpdate();

                } catch (Exception e) {
                    System.out.println("Update  error");
                }
                }
                 setTable();
                 getDB();
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
        if ( hremployeeid.getText().isEmpty() || hremployeename.getText().isEmpty() || (hraddress.getText().isEmpty())
                || (hrcontactno.getText().isEmpty()) || (hrdob.getValue() == null) || (hrgender.getValue() == null) 
                || (hrposition.getValue() == null)
                || hrnic.getText().isEmpty()|| (hremployeetype.getValue() == null)
                || hrbasicsalary.getText().isEmpty()) {
            alertboxWarn("Add Employee", "All fields should be filled !");
          
           
            return false;

        } 
        
        else 
        {
           
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
                     while(rs.next())
             {
             data.add(new HRDetails(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),""+ rs.getDate(5), rs.getString(6),""+ rs.getString(7), rs.getString(8), rs.getString(9),""+ rs.getDouble(10)));
             
             }
             
                    
            hremployee.setItems(data);

                } catch (Exception ex) {
                    System.err.println("Error loading table data " + ex);

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
        
        } 
    catch (Exception e) {
            System.out.println("error");
        }
          
         } 
    
        
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
            hrbasicsalary.setText(t1.getBasicSalary());
            
            
            
        });

    }
    

    
    
}
