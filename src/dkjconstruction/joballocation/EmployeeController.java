/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.joballocation;

import com.jfoenix.controls.JFXTextField;
import dkjconstruction.DbConnection;
import static dkjconstruction.joballocation.JobAllocationController.addEmployee;
import static dkjconstruction.joballocation.JobAllocationController.addTender;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
 * @author Ranjitha
 */
public class EmployeeController implements Initializable {
    
   private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

      private ObservableList<JobEmployee> dataKISH;
    private ObservableList<EMPLOYEE> dataASRAJ;
    
    @FXML
    private JFXTextField tenderEmployeeId;
    @FXML
    private JFXTextField KTid;
   
    @FXML
    private TableView kishtbl;
    @FXML
    private TableColumn kTenderID;
    @FXML
    private TableColumn reqEmpCount;
    @FXML
    private TableColumn assignedEmpCount;
    @FXML
    private TableView employeeTable;
    @FXML
    private TableColumn employeeId;
    @FXML
    private TableColumn employeeName;
    @FXML
    private TableColumn employeeType;
    @FXML
    private TextField searchfield;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dataKISH = FXCollections.observableArrayList();
        dataASRAJ = FXCollections.observableArrayList();
        
        setTenderTable();
        loadFromTenderDB();
        setEmployeeTable();
        loadFromEmployeeDB();
         RowclickEvent();
        RowclickEvent1();
        search();
        tenderEmployeeId.setDisable(true);
        KTid.setDisable(true);
        
        
    }    

    
    private void setTenderTable() {
        try {
            DbConnection.openConnection();
           Connection con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            kTenderID.setCellValueFactory(new PropertyValueFactory<>("tenderId"));
            reqEmpCount.setCellValueFactory(new PropertyValueFactory<>("empCount"));
            assignedEmpCount.setCellValueFactory(new PropertyValueFactory<>("assigncount"));
        } catch (Exception e) {
            System.out.println("ranjithatender");
            System.err.println("Error set table data " + e);
        }

    }

    private void loadFromTenderDB() {

        dataKISH.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("select tenderId,noOfEmployee,assignCount from jobemployee where tenderId IN (select tenderId from tender where status='On progress')");
            rs = pst.executeQuery();

            while (rs.next()) {
                dataKISH.add(new JobEmployee(rs.getString(1), rs.getInt(2), rs.getInt(3)));
                //  dataKISH.add(new KISHANTH(null, null, null));
            }

        } catch (Exception e) {
            System.out.println("ranjithatender");
            System.err.println("Error loading table data " + e);

        }
        kishtbl.setItems(dataKISH);
    }

    private void setEmployeeTable() {
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            employeeId.setCellValueFactory(new PropertyValueFactory<>("empId"));
            employeeName.setCellValueFactory(new PropertyValueFactory<>("empName"));

            employeeType.setCellValueFactory(new PropertyValueFactory<>("type"));

        } catch (Exception e) {
            System.err.println("Error set employee table data " + e);
            System.err.println("ranjithaemp ");
        }

    }

    private void loadFromEmployeeDB() {

        dataASRAJ.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("select empId,name,empType from employee where availability='available'");
            rs = pst.executeQuery();
             String kish;
            while (rs.next()) {
                dataASRAJ.add(new EMPLOYEE(rs.getString(1), rs.getString(2), rs.getString(3)));
                //  dataKISH.add(new KISHANTH(null, null, null, null, null));
                kish=rs.getString(1);
                 System.err.println(kish);
            }

        } catch (Exception e) {
            System.out.println("ranjithaemp");
            System.err.println("Error loading table data " + e);

        }
        employeeTable.setItems(dataASRAJ);

    }

    
    @FXML
    public void addTenderEmployee(ActionEvent event) {
        
         try {

            addTender = KTid.getText();
            addEmployee = tenderEmployeeId.getText();
            System.out.println(addTender);
            System.out.println(addEmployee);

            if (addTender.isEmpty() || addEmployee.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Fields cannot be empty");
                alert.show();
            }
            else{
            DbConnection.openConnection();
            Connection con4 = DbConnection.getConnection();
            PreparedStatement stmt = con4.prepareStatement("insert into emptender (tenderId,empId) values(?,?)");

            stmt.setString(1, addTender);
            stmt.setString(2, addEmployee);
            stmt.executeUpdate();
            System.out.println("success");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SUCCESS");
            alert.setHeaderText(null);
            alert.setContentText("Employee Added Successfully");
            alert.show();

            PreparedStatement stmt1 = con4.prepareStatement("UPDATE employee SET availability = 'assigned' WHERE empId =?");
            stmt1.setString(1, addEmployee);
            stmt1.executeUpdate();
            
            PreparedStatement stmt3 = con4.prepareStatement("UPDATE jobemployee SET assignCount =assignCount+1 WHERE tenderId =?");
            stmt3.setString(1, addTender);
            stmt3.executeUpdate();
            
            loadFromEmployeeDB();
            loadFromTenderDB();
            }
            

        } catch (Exception e) {

            System.out.println("error" + e);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("error Adding Employee");
            alert.show();
        }
         KTid.clear();
         tenderEmployeeId.clear();
          loadFromEmployeeDB();
            loadFromTenderDB();
    }
    
     private void RowclickEvent() {
        kishtbl.setOnMouseClicked((e)
                -> {
            JobEmployee k1 = (JobEmployee) kishtbl.getItems().get(kishtbl.getSelectionModel().getSelectedIndex());
            KTid.setText(k1.getTenderId());
            addTender=k1.getTenderId();
            System.out.println(addTender);
            
            

        });

    }

    private void RowclickEvent1() {
        employeeTable.setOnMouseClicked((e)
                -> {
            EMPLOYEE k1 = (EMPLOYEE) employeeTable.getItems().get(employeeTable.getSelectionModel().getSelectedIndex());
            tenderEmployeeId.setText(k1.getEmpId());
            addEmployee=k1.getEmpId();

        });

    }
    
     public void search() {
        searchfield.setOnKeyReleased(e -> {
            if (searchfield.getText().equals("")) {
                loadFromTenderDB();
                //loadFromAssetDB();
               // loadFromJobEquipDB();
              //  loadFromJobAssetADB();
               // loadFromTenderMaterialDB();
            } else {
                dataKISH.clear();
                try {

                    Connection con = DbConnection.getConnection();

                    pst = con.prepareStatement("select tenderId,noOfEmployee,assignCount from jobemployee where tenderId  LIKE '%" + searchfield.getText() + "%' ");
                    rs = pst.executeQuery();

                    while (rs.next()) {
                        dataKISH.add(new JobEmployee(rs.getString(1), rs.getInt(2), rs.getInt(3)));
                    }
                    System.out.println("Search clicked");
                } catch (Exception ex) {
                    System.err.println("Error loading table data search table jobemployee" + ex);

                }

                kishtbl.setItems(dataKISH);

            }

        });//event
    }
     @FXML
    private void tender_reportEmployee(ActionEvent event) {
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            String report = "C:\\Users\\Ranjitha\\Documents\\NetBeansProjects\\DKJ construction\\src\\dkj\\construction\\joballocation\\EmployeeAllocation.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(report);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
            JasperViewer.viewReport(jp,false);
        } catch (Exception e) {
            System.out.println("sdas");
        }
    }
    
    
    }

  
    

