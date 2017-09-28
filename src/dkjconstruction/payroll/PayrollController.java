/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 


package dkjconstruction.payroll;

import dkjconstruction.DbConnection;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
*/
/**
 * FXML Controller class
 *
 * @author ASRAJ
 */
/*
public class PayrollController implements Initializable {
     
    private TableView<PayrollDetails> payrollemployee;
    @FXML
    private JFXTextField payrollallowance;
    @FXML
    private JFXTextField payrollbonus;
    @FXML
    private JFXTextField payrollemployeeid;
    @FXML
    private JFXTextField payrollattendence;
    @FXML
    private JFXTextField payrollsalary;
    @FXML
    private TableColumn<?, ?> pemployeeid;
    @FXML
    private TableColumn<?, ?> pbonus;
    @FXML
    private TableColumn<?, ?> pallowance;
    @FXML
    private TableColumn<?, ?> pattendence;
    @FXML
    private TableColumn<?, ?> psalary;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableColumn<?, ?> pdate;
    @FXML
    private JFXComboBox chooseemployeetype;
    @FXML
    private JFXTextField psearch;
    private ObservableList data;
    
    
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        chooseemployeetype.getItems().addAll("Permenent employee","Contract based");
        
        payrollemployeeid.setVisible(false);
        payrollattendence.setVisible(false);
        payrollbonus.setVisible(false);
        payrollallowance.setVisible(false);
        payrollsalary.setVisible(false);
        
        
        
        
    }  
    
    private void clickEvent(){
    
     payrollemployee.setOnMouseClicked((e) -> {
        
        if(chooseemployeetype.getValue().toString().equals("Permenent employee"))
        {
        
        payrollemployeeid.setVisible(true);
        payrollattendence.setVisible(true);
        }    
            
     });
     
    }
    

    @FXML
    private void CalculateSalary(ActionEvent event) throws SQLException {
        Double salary = 0.0;
        String empID=payrollemployeeid.getText();
        
//        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement("select basicSalary from employee where empID=?");
        
        stmt.setString(1, empID);
        
        ResultSet rs = con.createStatement().executeQuery("select basicSalary from employee where empID=?");
        
        if(rs.next())
        {
         salary = rs.getDouble(1);
        
        
        }
         int atten=Integer.parseInt(payrollattendence.getText());
         
         Double finalSal=salary*atten;
         System.out.print(finalSal);
        
        stmt.setString(1, empID);
        
        
        
    }
    
        

    @FXML
    private void CalculateBasicSalary(ActionEvent event) {
    }

    @FXML
    private void GenerateSalaryReport(ActionEvent event) {
        
    }
    
   //search
    private void searchEmployee() {
       psearch.setOnKeyReleased(e -> {
            if (psearch.getText().equals("")) {
               getDB();
            } else {
                data.clear();
                try {

                   DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM payroll where empId LIKE '%" + psearch.getText() + "%'"
                            + "UNION SELECT * FROM employee where name LIKE '%" + psearch.getText() + "%'"
                            + "UNION SELECT * FROM employee where address LIKE '%" + psearch.getText() + "%'"
                            + "UNION SELECT * FROM employee where nic LIKE '%" + psearch.getText() + "%'"
                            + "UNION SELECT * FROM employee where dob LIKE '%" + psearch.getText() + "%'"
                            + "UNION SELECT * FROM employee where gender LIKE '%" + psearch.getText() + "%' "
                            + "UNION SELECT * FROM employee where position LIKE '%" + psearch.getText() + "%' "
                            + "UNION SELECT * FROM employee where empType LIKE '%" + psearch.getText() + "%' "
                            + "UNION SELECT * FROM employee where basicSalary LIKE '%" + psearch.getText() + "%' ");
                     while(rs.next())
             {
             data.add(new PayrollDetails(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));
             }
             
                    
           // payrollemployee.setItems(data);

                } catch (Exception ex) {
                    System.err.println("Error loading table data " + ex);

                }

               

            }

        });
    
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
         //    data.add(new HRDetails(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),""+ rs.getDate(5), rs.getString(6),""+ rs.getString(7), rs.getString(8), rs.getString(9),""+ rs.getDouble(10)));
             
             }
             
                    
          //  payrollemployee.setItems(data);
        } catch (Exception e) {
        }
        
    }
    
    
    

    
    
     private void RowclickEvent() {
      
         //   payrollemployee.setOnMouseClicked((e) -> {
       //     PayrollDetails t1 = payrollemployee.getItems().get(payrollemployee.getSelectionModel().getSelectedIndex());

          //  payrollemployeeid.setText(t1.getEmpId());
          //  payrollattendence.setText(t1.getAttendance());
          //  payrollbonus.setText(t1.getBonus());
          //  payrollsalary.setText(t1.getSalary());
          //  payrollallowance.setText(t1.getAllowance());
           
          //  payrollchooseemployeetype.setText(t1.ChooseEmployeeType());
            
            
            
      //  });

    
    
     }


}
*/