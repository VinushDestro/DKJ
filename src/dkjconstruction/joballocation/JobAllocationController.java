/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.joballocation;
import javafx.scene.input.MouseEvent;
import dkjconstruction.DbConnection;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
//import com.jfoenix.controls.JFXTreeView;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.*;
import com.jfoenix.*;
//import com.jfoenix.controls.JFXTreeTableView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;

/**
 *
 * @author Ranjitha
 */
public class JobAllocationController implements Initializable {

    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    private ObservableList<KISHANTH> dataKISH;
   
    
    private ObservableList<ASRAJ> dataASRAJ;
    private ObservableList<EQUI> dataEqui;
    

    @FXML
    private JFXTextField kempCount;
    @FXML
    private JFXComboBox materialtype;
    
     @FXML
    private JFXComboBox equipmentType;
     
    @FXML
    private JFXTextField equipmentCount;
    
    @FXML
    private JFXTextField jobMaterialCount;
    @FXML
    private JFXTextField jobAssetCount;
    @FXML
    private JFXTextField KTid;
    @FXML
    private TableView kishtbl;
    @FXML
    private TableColumn kID;
  
  
   @FXML
    private TableColumn kTYPE;

 

    @FXML
    private JFXTextField assignTenderId;

    @FXML
    private TableColumn kCAT;

    @FXML
    private TableColumn kcost;

    @FXML
    private TableColumn kPLC;

    
     @FXML
    private TableColumn kDate;
    @FXML
    private JFXComboBox assetType;
    
    @FXML
    private JFXComboBox remainMaterialType;
    
    @FXML
    private JFXTextField remainValue;
    @FXML
    private JFXTextField vIDtext;
    
    @FXML
    private JFXTextField vRegNo;
    
    @FXML
    private JFXTextField vDes;
    
     @FXML
    private JFXTextField eqTENDER;
     
      @FXML
    private JFXTextField eqEQID;
    
    @FXML
    private TableView<VINU> transTable;
    
    
    @FXML
    private TableColumn transREG;
    
    @FXML
    private TableColumn transName;
    
    @FXML
    private TableColumn transType;
    
    
      @FXML
    private TableView<EQUI>equipmentTable;
       @FXML
    private TableColumn equipmentID;
       @FXML
    private TableColumn equipmentName;
      
    
  private ObservableList<VINU> datavinu;
  
   @FXML
    private TableView<ASRAJ>eTABLE;
           
     @FXML
    private TableColumn eID;
     
      @FXML
    private TableColumn eNAME;
      
      @FXML
    private TableColumn eTYPE;
      
      @FXML
    private JFXTextField tenderEmpID;
      
       @FXML
    private DatePicker eqDate;
       
        @FXML
    private DatePicker empDate;
        
         @FXML
    private DatePicker assDate;
    //-----------------------------------------------------------------------------
    // function to display Tender
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        materialtype.getItems().addAll("Asphalt", "Gravel", "Cement","sand","Steel Bar","Sand","Bricks");
         assetType.getItems().addAll("Heavy", "Light", "Transport");
         remainMaterialType.getItems().addAll("Asphalt", "Gravel", "Concrete");
        

        try {
            DbConnection.openConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JobAllocationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(JobAllocationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        con = DbConnection.getConnection();
        
       

        dataKISH = FXCollections.observableArrayList();
        setTenderTable();
        loadFromTenderDB();
        
       dataEqui=FXCollections.observableArrayList();
       setEquipmentTable();
       loadFromEquipmentDB();
        
        
        
        datavinu = FXCollections.observableArrayList();
        setAssetTable();
        loadFromAssetDB();  
        
        dataASRAJ = FXCollections.observableArrayList();
        setEmployeeTable();
        loadFromEmployeeDB();

 
    }
    
   //--------------------------------------------------------------------------------------------------------------------------
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //VIEWING TABLE
    private void setTenderTable() {
        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            kID.setCellValueFactory(new PropertyValueFactory<>("tenderId"));
            kTYPE.setCellValueFactory(new PropertyValueFactory<>("worktype"));

            kCAT.setCellValueFactory(new PropertyValueFactory<>("category"));
            kPLC.setCellValueFactory(new PropertyValueFactory<>("workingPlace"));
            kcost.setCellValueFactory(new PropertyValueFactory<>("estimatedCost"));
            kDate.setCellValueFactory(new PropertyValueFactory<>("tenderDate"));

        } catch (Exception e) {
            System.err.println("Error set table data " + e);
        }

    }

    private void loadFromTenderDB() {

        dataKISH.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("select tenderId, workType, category, workingPlace, cost,date from tender ");
            rs = pst.executeQuery();

            while (rs.next()) {
               dataKISH.add(new KISHANTH(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6)));
                //  dataKISH.add(new KISHANTH(null, null, null, null, null));
               
            }

        } catch (Exception e) {
            System.err.println("Error loading table data " + e);

        }
        kishtbl.setItems(dataKISH);

    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   
     
       private void setEquipmentTable() {
        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            equipmentID.setCellValueFactory(new PropertyValueFactory<>("eQID"));
            equipmentName.setCellValueFactory(new PropertyValueFactory<>("eQNAME"));

            
        } catch (Exception e) {
            System.err.println("Error set table data " + e);
        }

    }
       
        private void loadFromEquipmentDB() {

        dataEqui.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("select equipID, name from equipment ");
            rs = pst.executeQuery();

            while (rs.next()) {
               dataEqui.add(new EQUI(rs.getString(1), rs.getString(2)));
                //  dataKISH.add(new KISHANTH(null, null, null, null, null));
               
            }

        } catch (Exception e) {
            System.err.println("Error loading table data " + e);

        }
        equipmentTable.setItems(dataEqui);

    }
    

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
   

     private void setAssetTable() {
        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            transREG.setCellValueFactory(new PropertyValueFactory<>("RegNo"));
            transName.setCellValueFactory(new PropertyValueFactory<>("Name"));

            transType.setCellValueFactory(new PropertyValueFactory<>("Type"));
            
        } catch (Exception e) {
            System.err.println("Error set table data " + e);
        }

    }

    private void loadFromAssetDB() {

        datavinu.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("select regNo,name,type from asset ");
            rs = pst.executeQuery();

            while (rs.next()) {
                datavinu.add(new VINU(rs.getString(1), rs.getString(2), rs.getString(3)));
               
               
                
            }

        } catch (Exception e) {
            System.err.println("Error loading table data " + e);

        }
        
        transTable.setItems(datavinu);

    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    
     private void setEmployeeTable() {
        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            eID.setCellValueFactory(new PropertyValueFactory<>("empId"));
            eNAME.setCellValueFactory(new PropertyValueFactory<>("empName"));

            eTYPE.setCellValueFactory(new PropertyValueFactory<>("type"));
            

        } catch (Exception e) {
            System.err.println("Error set table data " + e);
        }

    }
       

     private void loadFromEmployeeDB() {

        dataASRAJ.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("select empId,name,empType from employee");
            rs = pst.executeQuery();

            while (rs.next()) {
               dataASRAJ.add(new ASRAJ(rs.getString(1), rs.getString(2), rs.getString(3)));
                //  dataKISH.add(new KISHANTH(null, null, null, null, null));
               
            }

        } catch (Exception e) {
            System.err.println("Error loading table data " + e);

        }
        eTABLE.setItems(dataASRAJ);

    }
     
     ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     
    @FXML
     private void RowclickEvent(ActionEvent event) {
        
    KISHANTH k1 = (KISHANTH) kishtbl.getItems().get(kishtbl.getSelectionModel().getSelectedIndex());
    
    KTid.setText(k1.getTenderId()
    
    );
    
     }

     
   
     
    @FXML
    private void employeeCount(ActionEvent event) throws SQLException {

        try {
            String addtenderId = KTid.getText();
            int addEmpCount = Integer.parseInt(kempCount.getText());
            
           if(addtenderId.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error in assigning");
        alert.setHeaderText(null);
         alert.setContentText("Tender field cannot be empty");
           alert.show();
            }
            else if((addEmpCount==0)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error in assigning");
        alert.setHeaderText(null);
         alert.setContentText("Employee ID cannot be empty or more than 11 characters");
           alert.show();
            }
            
            else{
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement("insert into jobemployee (tenderId,noOfEmployee) values(?,?)");

            stmt.setString(1, addtenderId);
            stmt.setInt(2, addEmpCount);
            stmt.setDate(3, java.sql.Date.valueOf(eqDate.getValue()));

            stmt.executeUpdate();
            
           
            
            
            System.out.println("success");
            
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success in assigning");
        alert.setHeaderText(null);
         alert.setContentText("Employee Number Assigned");
           alert.show();
            }
        } catch (Exception e) {

            System.out.println("error"+ e);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
         alert.setContentText("error" +e);
           alert.show();
        }
        
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public void assignTravel(ActionEvent event) throws SQLException {

        try {
            String addTID = vIDtext.getText();
            String addRegno = vRegNo.getText();
            String addDesti = vDes.getText();
            
             if(addTID.isEmpty() ||addRegno.isEmpty() || addDesti.isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error in assigning");
        alert.setHeaderText(null);
         alert.setContentText("Any Fields Cannot field cannot be empty");
           alert.show();
             }
            DbConnection.openConnection();
            Connection con2 = DbConnection.getConnection();
            PreparedStatement stmt = con2.prepareStatement("insert into transport (regNo,tenderId,destination) values(?,?,?)");

            stmt.setString(1, addTID);
            stmt.setString(2, addRegno);
            stmt.setString(3, addDesti);

            stmt.executeUpdate();
            
            DbConnection.openConnection();
            Connection con6 = DbConnection.getConnection();
            PreparedStatement stmt_3 = con2.prepareStatement("insert into jobtransport (tenderId,destination) values(?,?)");

            stmt_3.setString(1, addTID);
            stmt_3.setString(2, addDesti);
            

            stmt_3.executeUpdate();
            
            
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            PreparedStatement stmt_1 = con2.prepareStatement("update asset set availability='n' where regNo = ?");

            stmt_1.setString(1, addRegno);
            
            
            
            System.out.println("success");
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Assigning Successfull");
        alert.setHeaderText(null);
         alert.setContentText("Assigned to travel ");
           alert.show();
        } catch (Exception e) {

            System.out.println("error" + e);
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
         alert.setContentText("error" +e);
           alert.show();
        }

    }

   ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   @FXML
    private void materialCount(ActionEvent event) throws SQLException {

        try {
            
            String addmaterial = materialtype.getValue().toString();
           
             String addtenderId = KTid.getText();
            int addMatCount = Integer.parseInt(jobMaterialCount.getText());
            
              if(addmaterial.isEmpty() ||addtenderId.isEmpty() || addMatCount==0 ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error in adding mterial");
        alert.setHeaderText(null);
         alert.setContentText("Any Fields Cannot field cannot be empty");
           alert.show();
             }
              

            DbConnection.openConnection();
            Connection con4 = DbConnection.getConnection();
            PreparedStatement stmt = con4.prepareStatement("insert into jobmaterial (tenderId,materialType,materialCount) values(?,?,?)");
            
            stmt.setString(1, addtenderId);
            stmt.setString(2, addmaterial);
            stmt.setInt(3, addMatCount);
            
            stmt.executeUpdate();
            System.out.println("success");
            
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
         alert.setContentText("Material Count Added to Tender ");
           alert.show();
            
            
        } catch (Exception e) {

            System.out.println("error" + e);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
         alert.setContentText("error" +e);
           alert.show();
        }

    }  
    
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     @FXML
    private void assetCount(ActionEvent event) throws SQLException {

        try {
            
            String addasset = assetType.getValue().toString();
           
             String addtenderId = KTid.getText();
            int addAssetCount = Integer.parseInt(jobAssetCount.getText());
            
              if(addasset.isEmpty() ||addtenderId.isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error in adding mterial");
        alert.setHeaderText(null);
         alert.setContentText("Any Fields Cannot field cannot be empty");
           alert.show();
             }
              else if(addAssetCount==0){
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error in adding mterial");
        alert.setHeaderText(null);
         alert.setContentText("Material Count cannot be 0 ");
           alert.show();
              }
              

            DbConnection.openConnection();
            Connection con4 = DbConnection.getConnection();
            PreparedStatement stmt = con4.prepareStatement("insert into jobasset (tenderId,assetType,assetCount) values(?,?,?)");
            
            stmt.setString(1, addtenderId);
            stmt.setString(2, addasset);
            stmt.setInt(3, addAssetCount);
            
            stmt.executeUpdate();
            System.out.println("success");
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
         alert.setContentText("Asset Count Added to Tender ");
           alert.show();
            
        } catch (Exception e) {

            System.out.println("error" + e);
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
         alert.setContentText("error" +e);
           alert.show();
        }

    }  
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    @FXML
    public void addRemain(){
        
         try {
            
            String addRemain = remainMaterialType.getValue().toString();
           
            
            int addMatValue = Integer.parseInt(remainValue.getText());
            
              if(addRemain.isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
         alert.setContentText("Material type Cannot field cannot be empty");
           alert.show();
             }
            
            else if(addMatValue==0){
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
         alert.setContentText("Nno Amount entered");
           alert.show();
            }
              

            DbConnection.openConnection();
            Connection con4 = DbConnection.getConnection();
            PreparedStatement stmt = con4.prepareStatement("update rawmaterial set quantity=quantity+? where type = ?");
            
            stmt.setString(2, addRemain);
            
            stmt.setInt(1, addMatValue);
            
            stmt.executeUpdate();
            System.out.println("success");
            
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
         alert.setContentText("Remains added to the Rawmaterials");
           alert.show();
            
        } catch (Exception e) {

            System.out.println("error" + e);
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
         alert.setContentText("error" +e);
           alert.show();
        }

        
    }
    
    
    
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
   
    @FXML
    public void addTenderEmployee() throws SQLException{
        
         
         try {
            
            String addEmployee = tenderEmpID.getText();
            String addEmployeeTender = assignTenderId.getText();
            
        
            
              if(addEmployee.isEmpty() ||addEmployeeTender.isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
         alert.setContentText("Fields cannot be empty");
           alert.show();
             }
              

            DbConnection.openConnection();
            Connection con4 = DbConnection.getConnection();
            PreparedStatement stmt = con4.prepareStatement("insert into emptender (tenderId,empId,date) values(?,?,?)");
            
            stmt.setString(1, addEmployee);
            
            stmt.setString(2, addEmployeeTender);
            stmt.setDate(3, java.sql.Date.valueOf(empDate.getValue()));
            stmt.executeUpdate();
            System.out.println("success");
        } catch (Exception e) {

            System.out.println("error" + e);
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
         alert.setContentText("error" +e);
           alert.show();
        }

        
    }
    
    
    
     @FXML
    public void addTenderEquipment() throws SQLException{
        
         
         try {
            
            String addEquipmentTender = eqTENDER.getText();
            String addEquipmentID = eqEQID.getText();
            
        
            
              if(addEquipmentTender.isEmpty() ||addEquipmentID.isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
         alert.setContentText("Fields cannot be empty");
           alert.show();
             }
              

            DbConnection.openConnection();
            Connection con4 = DbConnection.getConnection();
            PreparedStatement stmt = con4.prepareStatement("insert into equiptender (tenderId,equipID,date) values(?,?,?)");
            
            stmt.setString(1, addEquipmentTender);
            
            stmt.setString(2, addEquipmentID);
             stmt.setDate(3, java.sql.Date.valueOf(eqDate.getValue()));
            
            stmt.executeUpdate();
            System.out.println("success");
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
         alert.setContentText("Equipment Assigned to tender");
           alert.show();
        } catch (Exception e) {

            System.out.println("error" + e);
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
         alert.setContentText("error" +e);
           alert.show();
        }

        
    }
    
   
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   
    
     @FXML
    public void addTenderAsset() throws SQLException{
        
         
         try {
            
            String addAssetTender = vIDtext.getText();
            String addvehiReg = vRegNo.getText();
            
        
            
              if(addAssetTender.isEmpty() ||addvehiReg.isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
         alert.setContentText("Fields cannot be empty");
           alert.show();
             }
              

            DbConnection.openConnection();
            Connection con4 = DbConnection.getConnection();
            PreparedStatement stmt = con4.prepareStatement("insert into assettender (tenderId,regNo,date) values(?,?,?)");
            
            stmt.setString(1, addAssetTender);
            
            stmt.setString(2, addvehiReg);
             stmt.setDate(3, java.sql.Date.valueOf(assDate.getValue()));
            
            stmt.executeUpdate();
            System.out.println("success");
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
         alert.setContentText("Asset Assigned to tender");
           alert.show();
           
           vIDtext.clear();
           vRegNo.clear();
         
        } catch (Exception e) {

            System.out.println("error" + e);
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
         alert.setContentText("error" +e);
           alert.show();
        }
            
       
    }
   /* 
    
    private void searchTender() {
        tsearch.setOnKeyReleased(e -> {
            if (tsearch.getText().equals("")) {
                loadFromTenderDB();
            } else {
                dataKISH.clear();
                try {

                    Connection con = DbConnection.getConnection();

                    pst = con.prepareStatement("SELECT * FROM tender where KTid LIKE '%" + tsearch.getText()
                            ");
                    rs = pst.executeQuery();

                    while (rs.next()) {
                        data.add(new KISHANTH(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5)));
                    }

                } catch (Exception ex) {
                    System.err.println("Error loading table data " + ex);

                }

                kishtbl.setItems(dataKISH);

            }

        });//event
    }
 */  
    
   
    
}
