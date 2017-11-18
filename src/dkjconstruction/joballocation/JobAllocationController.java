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
import java.io.IOException;
//import com.jfoenix.controls.JFXTreeTableView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
//import javafx.scene.input.Key;
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
    private ObservableList<VINU> datavinu;
    private ObservableList<asset> dataasset;
    private ObservableList<Equipment> dataequipment;
    private ObservableList<matTender> dataMatTend;
    private ObservableList<Material> datamat;

    @FXML
    private TableView kishtbl;
    @FXML
    private TableView employeeTable;
    @FXML
    private TableView<VINU> transTable;
    @FXML
    private TableView<asset> assetTable;
    @FXML
    private TableView<EQUI> equipTable;
    @FXML
    private TableView<Equipment> equipmentTable;
    @FXML
    private TableView material;
    @FXML
    private TableView tendMatTbl;

    @FXML
    private JFXTextField empCount;
    @FXML
    private JFXTextField jobAssetCount;
    @FXML
    private JFXTextField KTid;
    @FXML
    private JFXTextField tenderEmployeeId;
    @FXML
    private JFXTextField remainValue;
    @FXML
    private JFXTextField aTenderId;
    @FXML
    private JFXTextField tenderEquipId;
    @FXML
    private JFXTextField equipTender;

    @FXML
    private JFXTextField materialtender;
    @FXML
    private JFXTextField tendermaterialtype;
    @FXML
    private JFXTextField assetType;

    @FXML
    private JFXTextField searchfield;

    @FXML
    private TableColumn kTenderID;
    // @FXML
    //private TableColumn EquimentID;
    @FXML
    private TableColumn equipmentName;
    @FXML
    private TableColumn availableEquipCount;

    @FXML
    private TableColumn reqEmpCount;
    @FXML
    private TableColumn assignedEmpCount;

    @FXML
    private JFXTextField vRegNo;
    @FXML
    private TableColumn assetTender;
    @FXML
    private TableColumn assettype;
    @FXML
    private TableColumn assetReqCount;
    @FXML
    private TableColumn assetAssignCount;

    @FXML
    private TableColumn regNo;
    @FXML
    private TableColumn assName;
    @FXML
    private TableColumn assType;
    @FXML
    private TableColumn employeeId;
    @FXML
    private TableColumn employeeName;
    @FXML
    private TableColumn employeeType;

    @FXML
    private TableColumn eqTender;
    @FXML
    private TableColumn eqName;
    @FXML
    private TableColumn eqReq;
    @FXML
    private TableColumn eqAssign;

    @FXML
    private TableColumn matTendID;
    @FXML
    private TableColumn matTendType;
    @FXML
    private TableColumn matReq;
    @FXML
    private TableColumn matAssign;

    @FXML
    private TableColumn mattype;
    @FXML
    private TableColumn matcount;
    
     
    public static String addTender;
    public static String addEmployee;
     private static Stage primaryStage;
    private static GridPane mainLayout;
    private static Stage stage;
    private static AnchorPane layout;
    //-----------------------------------------------------------------------------
    // function to display Tender
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        con = DbConnection.getConnection();
        dataKISH = FXCollections.observableArrayList();
        dataEqui = FXCollections.observableArrayList();
        datavinu = FXCollections.observableArrayList();
        dataasset = FXCollections.observableArrayList();
        dataASRAJ = FXCollections.observableArrayList();
        dataequipment = FXCollections.observableArrayList();
        dataMatTend = FXCollections.observableArrayList();
        datamat = FXCollections.observableArrayList();

       /*   search();
      setTenderTable();
        loadFromTenderDB();
        setAssetTable();
        loadFromAssetDB();
        setJobEquipmentTable();
        loadFromJobEquipDB();
        setJobAssetTable();
        loadFromJobAssetADB();
        setEmployeeTable();
        loadFromEmployeeDB();
        setEquipmentTable();
        loadFromEquipDB();
        setMaterialTable();
        loadMaterialDB();
        setTenderMaterialTable();
        loadFromTenderMaterialDB();
        // assigningAsset();*/

      /*  RowclickEvent();
        RowclickEvent1();
        RowclickEvent2();
        RowclickEvent3();
        RowclickEvent4();
        RowclickEvent5();
        RowclickEvent6();
        RowclickEvent7();*/

    }

    //--------------------------------------------------------------------------------------------------------------------------
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
     @FXML
    private void pendingClicked(ActionEvent event) throws IOException {
        
      dkjconstruction.DKJConstruction.showPendingEmployee();
        
        
    }
    
     @FXML
    private void employeeClicked(ActionEvent event) throws IOException {
      dkjconstruction.DKJConstruction.showEmployee();
       
        
    }
     
      @FXML
    private void assetClicked(ActionEvent event) throws IOException {
 dkjconstruction.DKJConstruction.showJobAsset();
        
        
        
    }
    
     
      @FXML
    private void equipmentClicked(ActionEvent event) throws IOException {
    dkjconstruction.DKJConstruction.showEquipment();
       
    }
     
     @FXML
    private void materialClicked(ActionEvent event) throws IOException {
        
      dkjconstruction.DKJConstruction.showJobMaterial();
      
    }
       
    
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   /* private void setTenderTable() {
        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

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

            pst = con.prepareStatement("select tenderId,noOfEmployee,assignCount from jobemployee");
            rs = pst.executeQuery();

            while (rs.next()) {
                dataKISH.add(new KISHANTH(rs.getString(1), rs.getInt(2), rs.getInt(3)));
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
            con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            employeeId.setCellValueFactory(new PropertyValueFactory<>("empId"));
            employeeName.setCellValueFactory(new PropertyValueFactory<>("empName"));

            employeeType.setCellValueFactory(new PropertyValueFactory<>("type"));

        } catch (Exception e) {
            System.err.println("Error set employee table data " + e);
            System.err.println("ranjithaemp ");
        }

    }
*/
   /* private void loadFromEmployeeDB() {

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
            System.out.println("ranjithaemp");
            System.err.println("Error loading table data " + e);

        }
        employeeTable.setItems(dataASRAJ);

    }
*/
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 /*   private void setJobAssetTable() {
        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            assetTender.setCellValueFactory(new PropertyValueFactory<>("tenderId"));
            assettype.setCellValueFactory(new PropertyValueFactory<>("type"));

            assetReqCount.setCellValueFactory(new PropertyValueFactory<>("req"));
            assetAssignCount.setCellValueFactory(new PropertyValueFactory<>("assign"));

        } catch (Exception e) {
            System.out.println("ranjithaasset");
            System.err.println("Error set job asset table data " + e);
        }

    }

    public void loadFromJobAssetADB() {
             

        datavinu.clear();
        try {

            Connection con = DbConnection.getConnection();
             PreparedStatement stmt1 = con.prepareStatement("select tenderId,assetType,assetCount,assignCount from jobasset where tenderId=?");
             System.out.println(addTender);
            stmt1.setString(1, addTender);
            stmt1.executeUpdate();

           /* pst = con.prepareStatement("select tenderId,assetType,assetCount,assignCount from jobasset");
            
            rs = pst.executeQuery();

            while (rs.next()) {
                datavinu.add(new VINU(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
                //  dataKISH.add(new KISHANTH(null, null, null));

            }

        } catch (Exception e) {
            System.out.println("ranjithaasset");
            System.err.println("Error loading table data " + e);

        }
        transTable.setItems(datavinu);

    }

    private void setAssetTable() {
        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            regNo.setCellValueFactory(new PropertyValueFactory<>("reg"));
            assName.setCellValueFactory(new PropertyValueFactory<>("name"));

            assType.setCellValueFactory(new PropertyValueFactory<>("type"));

        } catch (Exception e) {
            System.out.println("ranjithasset");
            System.err.println("Error set asset table data " + e);
        }

    }

    private void loadFromAssetDB() {

        dataasset.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("select regNo,name,type from asset where availability='available'");
            rs = pst.executeQuery();

            while (rs.next()) {
                dataasset.add(new asset(rs.getString(1), rs.getString(2), rs.getString(3)));
                //  dataKISH.add(new KISHANTH(null, null, null));

            }

        } catch (Exception e) {
            System.out.println("ranjithaasset");
            System.err.println("Error loading table data " + e);

        }
        assetTable.setItems(dataasset);

    }

    private void assigningAsset() throws SQLException {

        Connection con = DbConnection.getConnection();

        pst = con.prepareStatement("select assetCount,assignCount from jobasset");
        rs = pst.executeQuery();
        while (rs.next()) {
            datavinu.add(new VINU(rs.getInt(1), rs.getInt(2)));
            int x = rs.getInt(1);
            int y = rs.getInt(2);
            if (x == y) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("IMPORTANT!!!");
                alert.setHeaderText(null);
                alert.setContentText("Assigning Complete ");
                alert.show();
            }

        }

    }*/
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*
    private void setJobEquipmentTable() {
        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            eqTender.setCellValueFactory(new PropertyValueFactory<>("tenderId"));
            eqName.setCellValueFactory(new PropertyValueFactory<>("equiName"));

            eqReq.setCellValueFactory(new PropertyValueFactory<>("req"));
            eqAssign.setCellValueFactory(new PropertyValueFactory<>("assign"));

        } catch (Exception e) {
            System.out.println("ranjitha equip");
            System.err.println("Error set job equip table data " + e);
        }

    }

    private void loadFromJobEquipDB() {

        dataEqui.clear();

        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("select tenderId,equipName,count,assignCount from equiptender");
            rs = pst.executeQuery();

            while (rs.next()) {
                dataEqui.add(new EQUI(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
            }

        } catch (Exception e) {
            System.out.println("ranjithaequi");
            System.err.println("Error loading table data " + e);

        }
        equipTable.setItems(dataEqui);

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void setEquipmentTable() {
        try {

            DbConnection.openConnection();
            con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            equipmentName.setCellValueFactory(new PropertyValueFactory<>("equipName"));
            availableEquipCount.setCellValueFactory(new PropertyValueFactory<>("available"));

        } catch (Exception e) {
            System.out.println("ranjitha equip");
            System.err.println("Error set table data " + e);
        }
    }

    private void loadFromEquipDB() {

        dataequipment.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("select name,count from equipment ");
            rs = pst.executeQuery();

            while (rs.next()) {
                dataequipment.add(new Equipment(rs.getString(1), rs.getInt(2)));
                //  dataKISH.add(new KISHANTH(null, null, null));

            }

        } catch (Exception e) {
            System.out.println("ranjithaequi");
            System.err.println("Error loading table data " + e);

        }
        equipmentTable.setItems(dataequipment);

    }
    */

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
    /*
    public void assignTravel(ActionEvent event) throws SQLException {

        try {
            String addTID = aTenderId.getText();
            String addRegno = vRegNo.getText();

            if (addTID.isEmpty() || addRegno.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error in assigning");
                alert.setHeaderText(null);
                alert.setContentText("Any Fields Cannot field cannot be empty");
                alert.show();
            }
            DbConnection.openConnection();
            Connection con2 = DbConnection.getConnection();
            PreparedStatement stmt = con2.prepareStatement("insert into transport (regNo,tenderId) values(?,?)");
            stmt.setString(1, addTID);
            stmt.setString(2, addRegno);
            stmt.executeUpdate();

            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            PreparedStatement stmt_1 = con2.prepareStatement("update asset set availability='available' where regNo = ?");
            stmt_1.setString(1, addRegno);
            stmt_1.executeUpdate();

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
            alert.setContentText("error" + e);
            alert.show();
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    @FXML
    public void addTenderEmployee() throws SQLException {

        try {

          //  addTender = KTid.getText();
          //  addEmployee = tenderEmployeeId.getText();
            System.out.println(addTender);
            System.out.println(addEmployee);

            if (addTender.isEmpty() || addEmployee.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Fields cannot be empty");
                alert.show();
            }

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
            
            // loadFromJobAssetADB();

        } catch (Exception e) {

            System.out.println("error" + e);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("error" + e);
            alert.show();
        }

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*
   @FXML
    public void addTenderAsset() throws SQLException {

        try {

            String addTender = aTenderId.getText();
            String addAsset = vRegNo.getText();
            String addAssetType = assetType.getText();

            DbConnection.openConnection();
            Connection con4 = DbConnection.getConnection();
            PreparedStatement stmt = con4.prepareStatement("insert into assettender (tenderId,regNo) values(?,?)");
            stmt.setString(1, addTender);
            stmt.setString(2, addAsset);
            stmt.executeUpdate();
            System.out.println("success");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SUCCESS");
            alert.setHeaderText(null);
            alert.setContentText("Asset Added Successfully");
            alert.show();

            PreparedStatement stmt2 = con4.prepareStatement("UPDATE asset SET availability = 'assigned' WHERE regNo =?");
            stmt2.setString(1, addAsset);
            stmt2.executeUpdate();

            PreparedStatement stmt3 = con4.prepareStatement("UPDATE jobasset SET assignCount =assignCount+1 WHERE tenderId =? and assetType=?");
            stmt3.setString(1, addTender);
            stmt3.setString(2, addAssetType);
            stmt3.executeUpdate();

          //  loadFromAssetDB();
           // loadFromJobAssetADB();

            /*  pst = con4.prepareStatement("select assetCount,assignCount from jobasset");
             rs = pst.executeQuery();
                     while (rs.next()) {
                     datavinu.add(new VINU(rs.getInt(1),rs.getInt(2)));
                     int x=rs.getInt(1);
                     int y=rs.getInt(2);
                     System.out.println(x);
                     System.out.println(y);  
                 
                 
                if(x==y){
                  Alert alertz = new Alert(Alert.AlertType.INFORMATION);
                        alertz.setTitle("IMPORTANT!!!");
                        alertz.setHeaderText(null);
                        alertz.setContentText("Assigning Complete ");
                        alertz.show();
                }
               
            } 
        } catch (Exception e) {

            System.out.println("error" + e);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("errorxxxxx" + e);
            alert.show();
        }
    }
*/
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   /*
    
    @FXML
    public void addTenderEquipment() throws SQLException {
        try {
            String addTender = equipTender.getText();
            String addEq = tenderEquipId.getText();

            if (addTender.isEmpty() || addEq.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Fields cannot be empty");
                alert.show();
            }

            DbConnection.openConnection();
            Connection con4 = DbConnection.getConnection();
            PreparedStatement stmt = con4.prepareStatement("UPDATE equiptender SET assignCount=assignCount+1 where tenderId=? and equipName=?");
            stmt.setString(1, addTender);
            stmt.setString(2, addEq);
            stmt.executeUpdate();

            PreparedStatement stmt2 = con4.prepareStatement("UPDATE equipment SET count=count-1 where name=?");
            stmt2.setString(1, addEq);
            stmt2.executeUpdate();

            loadFromEquipDB();
            loadFromJobEquipDB();

            System.out.println("success");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Equipment added successfully");
            alert.show();

        } catch (Exception e) {

            System.out.println("error" + e);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("error" + e);
            alert.show();
        }
    }
*/
    private void setTenderMaterialTable() {
        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            matTendID.setCellValueFactory(new PropertyValueFactory<>("matTender"));
            matTendType.setCellValueFactory(new PropertyValueFactory<>("matType"));
            matReq.setCellValueFactory(new PropertyValueFactory<>("reqCount"));
            matAssign.setCellValueFactory(new PropertyValueFactory<>("assignCount"));
        } catch (Exception e) {
            System.out.println("ranjithatendermatset");
            System.err.println("Error set table data " + e);
        }

    }

    private void loadFromTenderMaterialDB() {

        dataMatTend.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("select tenderId,materialType,materialcount,assignCount from materialtender");
            rs = pst.executeQuery();

            while (rs.next()) {
                dataMatTend.add(new matTender(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
                //  dataKISH.add(new KISHANTH(null, null, null));
            }

        } catch (Exception e) {
            System.out.println("ranjithatender");
            System.err.println("Error loading table data " + e);

        }
        tendMatTbl.setItems(dataMatTend);
    }

    private void setMaterialTable() {
        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            mattype.setCellValueFactory(new PropertyValueFactory<>("matType"));
            matcount.setCellValueFactory(new PropertyValueFactory<>("available"));

        } catch (Exception e) {
            System.out.println("ranjithatender");
            System.err.println("Error set table data " + e);
        }

    }

    private void loadMaterialDB() {

        datamat.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("select type,quantity from rawmaterial");
            rs = pst.executeQuery();

            while (rs.next()) {
                datamat.add(new Material(rs.getString(1), rs.getInt(2)));
                //  dataKISH.add(new KISHANTH(null, null, null));

            }

        } catch (Exception e) {
            System.out.println("ranjithatender");
            System.err.println("Error loading table data " + e);

        }
        material.setItems(datamat);

    }

    @FXML
    public void addTenderMaterial() throws SQLException {

        try {

            String addMatTender = materialtender.getText();
            String addMaterial = tendermaterialtype.getText();

            if (addMatTender.isEmpty() || addMaterial.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Fields cannot be empty");
                alert.show();
            }

            DbConnection.openConnection();
            Connection con4 = DbConnection.getConnection();
            PreparedStatement stmt = con4.prepareStatement("UPDATE materialtender SET assignCount=assignCount+1 where tenderId=? and materialType=?");

            stmt.setString(1, addMatTender);
            stmt.setString(2, addMaterial);
            stmt.executeUpdate();
            System.out.println("success adding material");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SUCCESS");
            alert.setHeaderText(null);
            alert.setContentText("Material Added Successfully");
            alert.show();

            PreparedStatement stmt1 = con4.prepareStatement("UPDATE rawmaterial SET quantity = quantity-1 WHERE type =?");
            stmt1.setString(1, addMaterial);
            stmt1.executeUpdate();

            loadFromTenderMaterialDB();
            loadMaterialDB();
        } catch (Exception e) {

            System.out.println("error" + e);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("error adding Material " + e);
            alert.show();
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 /*   public void search() {
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

                    pst = con.prepareStatement("select tenderId,noOfEmployee,assignCount from jobemployee where tenderId  LIKE '%" + searchfield.getText() + "%'");
                    rs = pst.executeQuery();

                    while (rs.next()) {
                        dataKISH.add(new KISHANTH(rs.getString(1), rs.getInt(2), rs.getInt(3)));
                    }

                } catch (Exception ex) {
                    System.err.println("Error loading table data search table jobemployee" + ex);

                }

                kishtbl.setItems(dataKISH);

            }

        });//event
    }

    private void RowclickEvent() {
        kishtbl.setOnMouseClicked((e)
                -> {
            KISHANTH k1 = (KISHANTH) kishtbl.getItems().get(kishtbl.getSelectionModel().getSelectedIndex());
            KTid.setText(k1.getTenderId());
            addTender=k1.getTenderId();
            System.out.println(addTender);
            
            

        });

    }

    private void RowclickEvent1() {
        employeeTable.setOnMouseClicked((e)
                -> {
            ASRAJ k1 = (ASRAJ) employeeTable.getItems().get(employeeTable.getSelectionModel().getSelectedIndex());
            tenderEmployeeId.setText(k1.getEmpId());
            addEmployee=k1.getEmpId();

        });

    }

    private void RowclickEvent2() {
        assetTable.setOnMouseClicked((e)
                -> {
            asset v1 = (asset) assetTable.getItems().get(assetTable.getSelectionModel().getSelectedIndex());
            vRegNo.setText(v1.getReg());

        });

    }

    private void RowclickEvent3() {
        transTable.setOnMouseClicked((e)
                -> {
            VINU v1 = (VINU) transTable.getItems().get(transTable.getSelectionModel().getSelectedIndex());
            aTenderId.setText(v1.getTenderId());
            assetType.setText(v1.getType());

        });

    }

    private void RowclickEvent4() {
        equipTable.setOnMouseClicked((e)
                -> {
            EQUI v1 = (EQUI) equipTable.getItems().get(equipTable.getSelectionModel().getSelectedIndex());
            equipTender.setText(v1.getTenderId());

        });

    }

    private void RowclickEvent5() {
        equipmentTable.setOnMouseClicked((e)
                -> {
            Equipment v1 = (Equipment) equipmentTable.getItems().get(equipmentTable.getSelectionModel().getSelectedIndex());
            tenderEquipId.setText(v1.getEquipName());

        });
    }

    private void RowclickEvent6() {
        tendMatTbl.setOnMouseClicked((e)
                -> {
            matTender v1 = (matTender) tendMatTbl.getItems().get(tendMatTbl.getSelectionModel().getSelectedIndex());
            materialtender.setText(v1.getMatTender());

        });
    }

    private void RowclickEvent7() {
        material.setOnMouseClicked((e)
                -> {
            Material v1 = (Material) material.getItems().get(material.getSelectionModel().getSelectedIndex());
            tendermaterialtype.setText(v1.getMatType());

        });
    }
    
   */

}
