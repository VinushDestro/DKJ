/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.supplier;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import dkjconstruction.DbConnection;
import dkjconstruction.supplier.SupplierDetail;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
 *
 * @author User
 */
public class SupplierController implements Initializable {
    
   
 
    
   @FXML
   TableView<dkjconstruction.supplier.SupplierDetail> SupplierTab;
    
    @FXML
    TableColumn tabsupplierid;
    @FXML
    TableColumn tabname;
    @FXML
    TableColumn tabnic;
    @FXML
    TableColumn tabcontact;

    
    @FXML
    private JFXTextField supplierid;
    
      @FXML
    private TextField searchr;
    
    @FXML
    private JFXTextField name;
    
    @FXML
    private JFXTextField nic;
    
    @FXML
    private JFXTextField contact;
    
    @FXML
    Button Add;
    
    @FXML
    Button Update;
    
    @FXML
    Button Delete;
    
     @FXML
    Button clear;
     
      @FXML
    Button report;
    
   
      private ObservableList<SupplierDetail> getSupplier;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tabsupplierid.setCellValueFactory(new PropertyValueFactory<>("supplierid"));
        tabname.setCellValueFactory(new PropertyValueFactory<>("name"));
        tabnic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        tabcontact.setCellValueFactory(new PropertyValueFactory<>("contact"));
     



        try {
            SupplierTab.setItems(Supplier.getSupplier());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
         RowclickEvent();
         doSearchSupplier();
         supplierid.setDisable(true);
    }
    
 
    
    
    
      @FXML
    private void doclearSupplier() throws SQLException, ClassNotFoundException {
        supplierid.clear();
        name.clear();
        nic.clear();
        contact.clear();
          supplierid.setDisable(true);

       
       
         
    }
    
    
      public void loadTable() {
        tabsupplierid.setCellValueFactory(new PropertyValueFactory<>("supplierid"));
        tabname.setCellValueFactory(new PropertyValueFactory<>("name"));
        tabnic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        tabcontact.setCellValueFactory(new PropertyValueFactory<>("contact"));
       

        try {
            SupplierTab.setItems(Supplier.getSupplier());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    @FXML
    private void doAddSupplier() throws SQLException, ClassNotFoundException, IOException {
        
         
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Supplier");
        alert.setHeaderText(null);
        try {
              
       

            if (  name.getText().isEmpty()  || nic.getText().isEmpty()  || contact.getText().isEmpty() )  {
            alert.setContentText("All Fields cannot be empty"); 
            alert.show();
            System.out.println("alert");
            
            
 }else {
                String regexStr,regexnic,regname ;
                        regexStr = "^[0-9]{10}$";
                         regexnic = "^[0-9]{9}[v|V]$";  
                         regname = "^^[a-zA-Z\\\\s]*$";
                  if ((contact.getText().matches(regexStr))&&(nic.getText().matches(regexnic)) && (name.getText().matches(regname))){

            
        int result=0;
    
        String Name =name.getText();
        String Nic = nic.getText();
        String Contact = contact.getText();
        
                result = dkjconstruction.supplier.Supplier.addsupplier( Name, Nic, Contact);
                                     
                if (result == 1) {
                    alert.setContentText("Operation Successful!");
                     alert.showAndWait();
                     loadTable();
                       supplierid.setDisable(false);

                } else {
                    alert.setContentText("Operation Failed");
                      alert.showAndWait();

                } 
                
               
            SupplierTab.setItems(Supplier.getSupplier());
                }
                  else{
                alert.setContentText("Enter the appropriate input in the fields ");
                     alert.showAndWait();
                  
                  }
            
            
            }
            
        }
  catch (Exception e) {
            System.err.println("error"+e);
             alert.setContentText("You have entered an existing keyword");
             alert.showAndWait();

        }
  
        


    }
    
     
    
    
    
    
    
      @FXML
    private void doUpdateSupplier() throws SQLException, ClassNotFoundException, IOException {
        
         
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Supplier");
        alert.setHeaderText(null);
        try {
              
       

            if ( supplierid.getText().isEmpty() || name.getText().isEmpty()  || nic.getText().isEmpty()  || contact.getText().isEmpty()  ) {
            alert.setContentText("All Fields cannot be empty"); 
            alert.show();
            System.out.println("alert");
            
            }
            else {
                 int result=0;
        int Supplierid = Integer.parseInt(supplierid.getText());
        String Name =name.getText();
        String Nic = nic.getText();
        String Contact = contact.getText();
        
                result = dkjconstruction.supplier.Supplier.updatesupplier(Supplierid, Name, Nic, Contact);
                                     
                if (result == 1) {
                    alert.setContentText("Operation Successful!");
                     alert.showAndWait();
                     loadTable();
                       supplierid.setDisable(false);

                } else {
                    alert.setContentText("Operation Failed");
                      alert.showAndWait();

                }
                
               
            SupplierTab.setItems(Supplier.getSupplier());
                }
        


 } catch (Exception e) {
            System.err.println("abc"+e);
        }
  
        
       

    }
    
    
    
    
     /*  @FXML
    private void doUpdateSupplier(ActionEvent event) throws SQLException, ClassNotFoundException {
        int result = 0 ;
      int Supplierid = Integer.parseInt(supplierid.getText().trim());
        String Name =name.getText().trim();
        String Nic = nic.getText().trim();
        String Contact = contact.getText().trim();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Supplier");
        alert.setHeaderText(null);

        
        
        if ( supplierid.getText().trim().isEmpty() &&  Name.isEmpty() && Nic.isEmpty() && Contact.isEmpty()) {
            alert.setContentText("All Fields cannot be empty");
        }
         else if ( supplierid.getText().trim().isEmpty()){
            alert.setContentText("Supplier id field cannot be empty");
        }
        
        else{
                    try{
                         result = dkjconstruction.supplier.Supplier.updatesupplier(Supplierid, Name, Nic, Contact);
                        if (result == 1) {
                            alert.setContentText("Operation Successful!");
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
                    
                    try {
                        SupplierTab.setItems(Supplier.getSupplier());
                    } catch (Exception e) {
                         e.printStackTrace();
                       }
                }   


    }*/
    
   /* @FXML
    private void doDeleteSupplier() throws SQLException, ClassNotFoundException {
       int Supplierid = Integer.parseInt(supplierid.getText().trim());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Supplier");
            alert.setHeaderText(null);
        if ( supplierid.getText().trim().isEmpty() ) {
            
            alert.setContentText("Supplier id Field Cannot be Empty!");
            alert.showAndWait();
            
        }
        else {
            int result =0;
                    result= dkjconstruction.supplier.Supplier.deleteSupplier(Supplierid);

        //    supplierid.clear();
            if (result == 1) {  
                alert.setContentText("Supplier Successfully Deleted!");
                  alert.showAndWait();
                     loadTable();
                
            } else if (result==0){
                alert.setContentText("Deletion Failed! \n Supplier Not Found!");
            }
            
            try {
            SupplierTab.setItems(Supplier.getSupplier());
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        alert.show();

    }
  */
    
    
    /*
    @FXML
    private void doDeleteSupplier() throws SQLException, ClassNotFoundException {
       int Supplierid = Integer.parseInt(supplierid.getText().trim());
       

        if (supplierid.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Supplier");
            alert.setHeaderText(null);
            alert.setContentText("supplierid Field Cannot be Empty!");
         //   alert.show();
             alert.showAndWait();
                    
        } else {
            int result = 0;
         result = dkjconstruction.supplier.Supplier.deleteSupplier(Supplierid);

      //      Type.clear();
            if (result == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Supplier");
                alert.setHeaderText(null);
                alert.setContentText("Supplier Successfully Deleted!");
          //      alert.show();
                 alert.showAndWait();
                     loadTable();
            } else if (result == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Supplier");
                alert.setHeaderText(null);
                alert.setContentText("Deletion Failed! \n Supplier Not Found!");
                alert.show();
            }
             
            
        }
       

        
    }*/
    
    
    
    
    
    
     @FXML
    private void doDeleteSupplier() throws SQLException, ClassNotFoundException, IOException {
        
         
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Supplier");
        alert.setHeaderText(null);
        try {
              
       

            if ( supplierid.getText().isEmpty() || name.getText().isEmpty()  || nic.getText().isEmpty()  || contact.getText().isEmpty()  ) {
            alert.setContentText("supplierid cannot be empty"); 
            alert.show();
            System.out.println("alert");
            
            }
            else {
                 int result=0;
        int Supplierid = Integer.parseInt(supplierid.getText());
      
                result = dkjconstruction.supplier.Supplier.deleteSupplier(Supplierid);
                                     
                if (result == 1) {
                    alert.setContentText("Operation Successful!");
                     alert.showAndWait();
                     loadTable();
                       supplierid.setDisable(false);
                       doclearSupplier();

                } else {
                    alert.setContentText("Operation Failed");
                      alert.showAndWait();

                }
                
               
            SupplierTab.setItems(Supplier.getSupplier());
                }
        


 } catch (Exception e) {
            System.err.println("abc"+e);
        }
  
        
       

    }
    
    
   
   //rowclick for suppliertable
    @FXML
    public void RowclickEvent() {
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("RowclickEvent");
        alert.setHeaderText(null);  
           
          
         SupplierTab.setOnMouseClicked((e) -> {
            try{

            SupplierDetail r1 = (SupplierDetail)SupplierTab.getItems().get(SupplierTab.getSelectionModel().getSelectedIndex());

            supplierid.setText(String.valueOf(r1. getSupplierid()));
            name.setText(String.valueOf(r1.getName()));
            nic.setText(String.valueOf(r1.getNic()));
            contact.setText(String.valueOf(r1.getContact()));
            nic.setDisable(true);
            
            supplierid.setDisable(true);
            
            }catch (ArrayIndexOutOfBoundsException ex) {
                     
            
            alert.setContentText("no row is selected");
                    alert.showAndWait();
                 
                 }
            });
         
                
                 
        


    
    }
    
     public void  doSearchSupplier() {
        searchr.setOnKeyReleased(e -> {
            if (searchr.getText().equals("")) {
               
                
         tabsupplierid.setCellValueFactory(new PropertyValueFactory<SupplierDetail,Integer>("supplierid"));
                    tabname.setCellValueFactory(new PropertyValueFactory<SupplierDetail,String>("name"));
                    tabnic.setCellValueFactory(new PropertyValueFactory<SupplierDetail,String>("nic"));
                    tabcontact.setCellValueFactory(new PropertyValueFactory<SupplierDetail,String>("contact"));
                  



        try {
          SupplierTab.setItems(Supplier.getSupplier());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

            }
            else{
                try {
                   Connection con = DbConnection.getConnection();
                    
                    PreparedStatement pst = con.prepareStatement
                             ("select * from supplier where supplierid like '%" + searchr.getText() + "%'"
                            + "union select * from supplier where name  like '%" + searchr.getText() + "%'");
                          
                          
                    ResultSet rs = pst.executeQuery();
                    getSupplier= FXCollections.observableArrayList();
                   
                   while(rs.next()) {
     
                       int  supplierid=rs.getInt("supplierid");
                        String name=rs.getString("name");
                         String nic=rs.getString("nic");
                          String contact=rs.getString("contact");
                          
                             getSupplier.add(new SupplierDetail( supplierid,name,nic,contact));

                        }
                   SupplierTab.setItems(getSupplier);

                } catch (SQLException ex) {
                    System.err.println("Error loading table data " + ex);

                }
                
            }
        });
    }

    
    
     @FXML
    private void showReportSupplier(){// throws JRException, SQLException, ClassNotFoundException {
     try{   DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        String report = "C:\\Users\\Mahesh\\Documents\\NetBeansProjects\\dkjconstructions\\src\\dkjconstruction\\supplier\\Supplier.jrxml";
        JasperReport jr = JasperCompileManager.compileReport(report);
        JasperPrint jp = JasperFillManager.fillReport(jr,null,con);
        JasperViewer.viewReport(jp,false);
        
     }
     catch (Exception ex){System.out.println("error"+ ex);}
    }


    
    
    
    
}
