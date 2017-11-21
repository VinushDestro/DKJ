/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package dkjconstruction.payroll;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import dkjconstruction.DbConnection;

import com.jfoenix.controls.JFXTextField;
import dkjconstruction.tendermanagement.TenderReport.Report;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author ASRAJ
 */
public class PayrollController implements Initializable {

    @FXML
    private JFXTextField a_totalovertime;
    @FXML
    private Label a_employeeid;
    private JFXTextField p_attendence;
    @FXML
    private ComboBox a_search;
    
    private ComboBox pp_search;
    @FXML
    private JFXTextField a_rateperhour;
    @FXML
    private Label a_basicsalary;
    @FXML
    private Label a_employeename;
    @FXML
    private TableColumn<?, ?> A_allowance;
    @FXML
    private TableColumn<?, ?> A_deduction;
    @FXML
    private TableColumn<?, ?> A_overtime;
    @FXML
    private TableColumn<?, ?> A_employeeid;
    @FXML
    private TableColumn<?, ?> A_salary;
    @FXML
    private TableColumn<?, ?> A_rate;
    @FXML
    private TableColumn<?, ?> A_total;
    @FXML
    private Label a_totalamount;
    private Label p_employeeid;
    private Label p_employeename;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane salary;
    @FXML
    private TableView<PayrollDetails> payrollpemployee;
    @FXML
    private Label a_totalamountlabel;
    @FXML
    private JFXTextField a_allowance;
    @FXML
    private JFXTextField a_deduction;
    @FXML
    private Label c_employeeid;
    @FXML
    private JFXTextField c_daysalary;
    @FXML
    private Label c_employeename;
    @FXML
    private JFXTextField c_bonus;
    @FXML
    private ComboBox c_search;
    @FXML
    private Label c_totalamount;
    @FXML
    private JFXTextField c_allowance;
    @FXML
    private JFXTextField c_deduction;
    @FXML
    private Label c_totalamountlabel;
    @FXML
    private TableView<PayrollDetails> payrollcemployee;
    @FXML
    private TableColumn<?, ?> C_overtime;
    @FXML
    private TableColumn<?, ?> C_allowance;
    @FXML
    private TableColumn<?, ?> C_totalAmount;
    @FXML
    private TableColumn<?, ?> C_employeeid;
    @FXML
    private TableColumn<?, ?>C_deduction;
    @FXML
    private javafx.scene.control.Tab p_sal;
    @FXML
    private javafx.scene.control.Tab C_sal;
    @FXML
    private JFXTextField c_totalDays;
    @FXML
    private JFXTextField p_bonus;
    
    
    private ObservableList<PayrollDetails> data;
    private ObservableList<PayrollDetails> data1;
    
    private List<String>  IdList;
    
    private final Double persentage =0.08;
    @FXML
    private AnchorPane p_search;
    
    
     
    
   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       searchEmployee();
       
        data = FXCollections.observableArrayList();
        data1 = FXCollections.observableArrayList();
        p_setTable();
        c_setTable();
        getDB();
        C_getDB();
        
        
         
         
        a_deduction.setDisable(true);
        a_allowance.setDisable(true);
        a_rateperhour.setDisable(true);
        a_totalovertime.setDisable(true);
        p_bonus.setDisable(true);
        
        c_deduction.setDisable(true);
        c_allowance.setDisable(true);
        c_totalDays.setDisable(true);
        c_bonus.setDisable(true);
        c_daysalary.setDisable(true);
       
       
        
       
    }  
    
  @FXML
  private void p_handler() {
      
  String name = (String) a_search.getValue();
    a_deduction.setDisable(false);
    a_allowance.setDisable(false);
    a_rateperhour.setDisable(false);
    a_totalovertime.setDisable(false);
    p_bonus.setDisable(false);
    
  //outputTextArea.appendText("ComboBox Action (selected: " + selectedPerson + ")\n");
  //System.out.println("selected id is " + id);
   
   this.retrieve();
 } 
    
  
  @FXML
  private void c_handler() {
  String name = (String) c_search.getValue();
    c_deduction.setDisable(false);
    c_allowance.setDisable(false);
    c_totalDays.setDisable(false);
    c_bonus.setDisable(false);
    c_daysalary.setDisable(true);
   
  //outputTextArea.appendText("ComboBox Action (selected: " + selectedPerson + ")\n");
  //System.out.println("selected id is " + id);
   
   this.retrieve1();
 } 
  
   
  private void pp_handler() {
  String id = (String)pp_search.getValue();
   
  //outputTextArea.appendText("ComboBox Action (selected: " + selectedPerson + ")\n");
  //System.out.println("selected id is " + id);
   
   this.retrieve2();
 } 
  
  private void retrieve(){
    
       try {

            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
         
           
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM employee where name = '"+ a_search.getValue() +"'" );
                     while(rs.next())
           {
                 
                 a_employeeid.setText(rs.getString(1));
                 a_employeename.setText(rs.getString(2));
                 a_basicsalary.setText(rs.getString(10));
                 
               
                 
                 
            }
             
            } catch (Exception ex) {
                    System.out.println("Error loading table retaaa " + ex);
            }
     
  }
  
  private void retrieve1(){
    
       try {

            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
         
           
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM employee where name = '"+ c_search.getValue() +"'" );
                     while(rs.next())
           {
                 
                 c_employeeid.setText(rs.getString(1));
                 c_employeename.setText(rs.getString(2));
                 c_daysalary.setText(rs.getString("daySalary"));
              }
             
            } catch (Exception ex) {
                    System.out.println("Error loading table re1 " + ex);
            }
     
  }
  
  
   private void retrieve2(){
    
       try {

            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
         
           
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM employee where name = '"+ pp_search.getValue() +"'" );
                     while(rs.next())
           {
                 
                 p_employeeid.setText(rs.getString(1));
                 p_employeename.setText(rs.getString(2));
                 
              }
             
            } catch (Exception ex) {
                    System.out.println("Error loading table re2 " + ex);
            }
     
  }
   
   private void searchEmployeeclicked() {
       
        

        if (a_search.getValue().equals("Permenent")) {
            a_search.setVisible(true);
            a_search.setValue(null);
            a_search.setVisible(false);

        } else if (c_search.getValue().equals("Contract based")) {
            c_search.setVisible(true);
            c_search.setValue(null);
            c_search.setVisible(false);
        }
            
   }
  
   //search
    
    private void searchEmployee() {
        
        
        
           try {
               
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
        
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM employee" );
                     while(rs.next())
           {
  
                 pp_search.getItems().addAll(rs.getString(1));
                
             }
                     
             
            } catch (Exception ex) {
                    System.out.println("Error loading search " + ex);
            }
           
           
           try {

            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
        
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM employee where empType = 'permanent'  " );
                     while(rs.next())
           {            
                 a_search.getItems().addAll(rs.getString(2));
              
             }
                     
             
            } catch (Exception ex) {
                    System.out.println("Error loading table asearch " + ex);
            }
           
           
           try {
               
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
        
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM employee where empType = 'Contract based' " );
                     while(rs.next())
           {
          
                 c_search.getItems().addAll(rs.getString(2));
              
             }
                     
             
            } catch (Exception ex) {
                    System.out.println("Error loading table csearcg " + ex);
            }
           
      
    } 
    
    
    @FXML
    private void p_calculate(){
        
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Transport");
        alert.setHeaderText(null);
       if(a_basicsalary.getText().isEmpty() || a_deduction.getText().isEmpty() ||a_rateperhour.getText().isEmpty() ||a_totalovertime.getText().isEmpty() ||a_allowance.getText().isEmpty() ||p_bonus.getText().isEmpty())
       {
       alert.setContentText("All fields should be filled");
       }
     
       
       else{
       int basicsalary = Integer.parseInt(a_basicsalary.getText());
       int deduction = Integer.parseInt(a_deduction.getText()); 
       int rph = Integer.parseInt(a_rateperhour.getText()); 
       int tothours = Integer.parseInt(a_totalovertime.getText());
       
       double ot =  rph * tothours ; 
       float alowance = Float.parseFloat(a_allowance.getText());
       int bonus= Integer.parseInt(p_bonus.getText());
       
       double totalamount = alowance + basicsalary - deduction + bonus +ot ;
      
       a_totalamountlabel.setText(Double.toString(totalamount));
       
      
       }
   
    }
          
    @FXML
    private void c_calculate(){
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Transport");
        alert.setHeaderText(null);
        
       if(c_daysalary.getText().isEmpty() ||c_deduction.getText().isEmpty() ||c_totalDays.getText().isEmpty() ||c_allowance.getText().isEmpty() )
       {
            alert.setContentText("All fields should be filled");
           
       }
       
       
       
       int daysalary = Integer.parseInt(c_daysalary.getText()); 
       int deduction = Integer.parseInt(c_deduction.getText());
       int tod=Integer.parseInt(c_totalDays.getText());
     
        
       int totalsalary = daysalary * tod; 
       float alowance = Float.parseFloat(c_allowance.getText());
       alowance = alowance * tod;
       int bonus = Integer.parseInt(c_bonus.getText());
       double  finalsalary = alowance + totalsalary -deduction + bonus;
       
       c_totalamountlabel.setText(Double.toString(finalsalary));
       
    
    }
    
     

    @FXML
    private void P_Save(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Transport");
        alert.setHeaderText(null);
       if(a_basicsalary.getText().isEmpty() || a_deduction.getText().isEmpty() ||a_rateperhour.getText().isEmpty() ||a_totalovertime.getText().isEmpty() ||a_allowance.getText().isEmpty() ||p_bonus.getText().isEmpty())
       {
       alert.setContentText("All fields should be filled");
       }
     
       
       else{
           
       int basicsalary = Integer.parseInt(a_basicsalary.getText());
       int deduction = Integer.parseInt(a_deduction.getText()); 
       int rph = Integer.parseInt(a_rateperhour.getText()); 
       int tothours = Integer.parseInt(a_totalovertime.getText());
       double ot =  rph * tothours ; 
       float alowance = Float.parseFloat(a_allowance.getText());
       int bonus= Integer.parseInt(p_bonus.getText()); 
       double totalamount = alowance + basicsalary - deduction + bonus +ot ;
       
 
      
          try{
            System.out.println("date " );
            System.out.println("date " +LocalDate.now());
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement("Insert into payroll(date,empId,allowance,deduction,salary,attendance,overTime,hourlyRate,finalSalary) values ('"+ LocalDate.now()+"',?,?,?,?,?,?,?,?)");
            stmt.setString(1, a_employeeid.getText());
            stmt.setString(2,Double.toString(alowance));
            stmt.setString(3,a_deduction.getText());
            stmt.setString(4, Integer.toString(basicsalary));
            stmt.setString(5,""+2);
            stmt.setString(6, Double.toString(ot));
            stmt.setString(7, Integer.toString(rph));
            stmt.setString(8, Double.toString(totalamount));
            
            stmt.executeUpdate();
            System.out.println("success");
            
       
            
        }  catch (SQLIntegrityConstraintViolationException r) {
            System.out.println("payroll for employeeid already exist");
            
        } catch (Exception e) {
            System.out.println(" Error"+ e);
        }
          finally {
             getDB();
              p_setTable();
          }
       
       }
    }
    
    
    
    @FXML
    private void C_Save(ActionEvent event) { 
       
       int daysalary = Integer.parseInt(c_daysalary.getText()); 
       int deduction = Integer.parseInt(c_deduction.getText());
       int tod = Integer.parseInt(c_totalDays.getText());      
       int totalsalary = daysalary * tod; 
       float alowance = Float.parseFloat(c_allowance.getText());
       alowance = alowance * tod;
       int bonus = Integer.parseInt(c_bonus.getText());
       double finalsalary = alowance + totalsalary - deduction + bonus;
       
       c_totalamountlabel.setText(Double.toString(finalsalary));
          try{
              
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement("Insert into payroll(empId,allowance,deduction,attendance,salary,finalSalary,date) values (?,?,?,?,?,?,'"+ LocalDate.now()+"')");
            stmt.setString(1, c_employeeid.getText());
            String deduc=Integer.toString(deduction);
            stmt.setDouble(2,alowance);
            stmt.setString(3,deduc);
            stmt.setString(4,Integer.toString(tod));
            stmt.setString(5, Integer.toString(totalsalary));
            stmt.setDouble(6,finalsalary);
            
            stmt.executeUpdate();
            System.out.println("success");
        
            } 
            catch (SQLIntegrityConstraintViolationException r) {
                System.out.println("payroll for employeeid already exist");
            
            } 
          catch (Exception e) {
            System.out.println(" Error");
            System.err.println(e);

        } finally {
              C_getDB();
              c_setTable();
          }
          
     
    }
    

    
     private void p_setTable() {

        

            A_employeeid.setCellValueFactory(new PropertyValueFactory<>("Empid"));
            A_overtime.setCellValueFactory(new PropertyValueFactory<>("OverTime"));
            A_rate.setCellValueFactory(new PropertyValueFactory<>("HourlyRate"));
            A_salary.setCellValueFactory(new PropertyValueFactory<>("Salary"));
            A_total.setCellValueFactory(new PropertyValueFactory<>("FinalSalary"));
            A_allowance.setCellValueFactory(new PropertyValueFactory<>("Allowance"));
            A_deduction.setCellValueFactory(new PropertyValueFactory<>("Deduction"));
         
            try {
                     payrollpemployee.setItems(data);
        } catch (Exception e) {
            
           
            System.out.println("Error loading table" + e);
        }

    }
     
     
     private void c_setTable() {

            C_employeeid.setCellValueFactory(new PropertyValueFactory<>("Empid"));
            C_overtime.setCellValueFactory(new PropertyValueFactory<>("OverTime"));
            C_deduction.setCellValueFactory(new PropertyValueFactory<>("Deduction"));
            C_allowance.setCellValueFactory(new PropertyValueFactory<>("Allowance"));
            C_totalAmount.setCellValueFactory(new PropertyValueFactory<>("FinalSalary"));
           
           
            try {
                
                  payrollcemployee.setItems(data1);
                 
               
                  
        } catch (Exception e) {
            System.out.println("Error loading table" + e);
        }

    }
     
     
      private void getDB() {
        data.clear();
        try {
           // payrollpemployee.setItems(data);
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("SELECT * from Payroll where empId in (select empId from employee where empType = 'permanent')");

            while (rs.next()) {
                
                data.add(new PayrollDetails(rs.getString(1), rs.getString(7), rs.getString(8), rs.getString(6), rs.getString(9), rs.getString(3), rs.getString(4) ));
            }

            payrollpemployee.setItems(data);
        } catch (Exception e) {
            System.out.println("error loading observable list " + e);
        }

    }
      
      
        private void C_getDB() {
        data1.clear();
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("SELECT * from Payroll  where empId in (select empId from employee where empType = 'Contract based')");

            while (rs.next()) {
             //   System.out.println(rs.getString("allowance"));
                data1.add(new PayrollDetails(rs.getString(1), rs.getString(7), rs.getString(4), rs.getString(3), rs.getString(9)));
                
            }

            //payrollcemployee.setItems(data1);
        }   catch (Exception e) {
            System.out.println("error loading observable list " + e);
        }

    }
      
    @FXML
    private void P_Clear(ActionEvent event) {    
        
        a_deduction.setText("");
        a_allowance.setText("");
        a_totalovertime.setText("");
        p_bonus.setText("");
        a_rateperhour.setText("");
        a_totalamountlabel.setText("");
        a_employeeid.setText("");
        a_employeename.setText("");
        a_basicsalary.setText("");
        a_search.setValue("Search");
        
        
                
        a_deduction.setDisable(false);
        a_allowance.setDisable(false);
        a_rateperhour.setDisable(false);
        a_totalovertime.setDisable(false);
        p_bonus.setDisable(false);

        
              
    }

    

    
    @FXML
    private void C_Clear(ActionEvent event) {
   
        c_employeeid.setText("");   
        c_employeename.setText("");
        c_daysalary.setText("");
        c_deduction.setText("");
        c_allowance.setText("");
        c_totalDays.setText("");
        c_bonus.setText("");
        c_totalamountlabel.setText("");
        c_search.setValue("Search");
    
        
        c_deduction.setDisable(false);
        c_allowance.setDisable(false);
        c_totalDays.setDisable(false);
        c_bonus.setDisable(false);
        c_daysalary.setDisable(false);
        
        
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
        if (a_deduction.getText().isEmpty() || a_allowance.getText().isEmpty() || (a_rateperhour.getText().isEmpty())
                || (a_totalovertime.getText().isEmpty()) ||(p_bonus.getText().isEmpty()) || (a_search.getValue() == null)
                || c_daysalary.getText().isEmpty()||c_bonus.getText().isEmpty() || c_totalDays.getText().isEmpty() 
                || c_allowance.getText().isEmpty() ||c_totalamountlabel.getText().isEmpty() ||a_totalamountlabel.getText().isEmpty() || c_deduction.getText().isEmpty() || (c_search.getValue() == null)) {
            
            alertboxWarn("Add Employee", "All fields should be filled !");

            return false;

        } 
        
        else if (c_search.getValue().equals("Permenent ")) {

            alertboxWarn("Add Employee", "All fields should be filled !");
            return false;
        } 
        
        else if (a_search.getValue().equals("Contract based")) {
            
            alertboxWarn("Add Employee", "All fields should be filled !");
            return false;
        } 
        else {
            
            return true;
            
        }

    }

    @FXML
    private void P_Report(ActionEvent event) {
        
        Report.gen_Normal_report("C:\\Users\\Mahesh\\Documents\\NetBeansProjects\\dkjconstructions\\src\\dkjconstruction\\payroll\\payroll.jrxml");

    }

    @FXML
    private void C_Report(ActionEvent event) {
        
        Report.gen_Normal_report("C:\\Users\\Mahesh\\Documents\\NetBeansProjects\\dkjconstructions\\src\\dkjconstruction\\payroll\\p_payroll.jrxml");

    }
   
public  void gen_Normal_report(String report) {
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            JasperReport jr = JasperCompileManager.compileReport(report);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
            JasperViewer.viewReport(jp,false);
        
        } catch (Exception e) {
            System.out.println("error in j report" + e);
        }


    }
}


