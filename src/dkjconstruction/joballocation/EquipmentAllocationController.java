/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.joballocation;

import com.jfoenix.controls.JFXTextField;
import dkjconstruction.DbConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
public class EquipmentAllocationController implements Initializable {

    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
     private ObservableList<EquipTender> dataEqui;
       private ObservableList<Equipment> dataequipment;
    
    @FXML
    private TableView equipmentTable;
    @FXML
    private TableColumn equipmentName;
    @FXML
    private TableColumn availableEquipCount;
    @FXML
    private JFXTextField equipTender;
    @FXML
    private JFXTextField tenderEquipId;
    @FXML
    private JFXTextField  matCount;
    @FXML
    private JFXTextField  searchfield;
    @FXML
    private TableView equipTable;
    @FXML
    private TableColumn eqTender;
    @FXML
    private TableColumn eqName;
    @FXML
    private TableColumn eqReq;
    @FXML
    private TableColumn eqAssign;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         dataEqui = FXCollections.observableArrayList();
          dataequipment = FXCollections.observableArrayList();
          
          setJobEquipmentTable();
          loadFromJobEquipDB();
          setEquipmentTable();
          loadFromEquipDB();
          RowclickEvent4();
          search();
          //RowclickEvent5();
    } 
    
     public void alerboxInfo(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setWidth(500);
        alert.setHeight(300);
        alert.setContentText(message);
        alert.showAndWait();

    }  
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

            pst = con.prepareStatement("select tenderId,equipName,count,assignCount from equiptender where tenderId IN(select tenderId from tender where status ='On progress')");
            rs = pst.executeQuery();

            while (rs.next()) {
                dataEqui.add(new EquipTender(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
            }

        } catch (Exception e) {
            System.out.println("ranjithaequi");
            System.err.println("Error loading table data " + e);

        }
        equipTable.setItems(dataEqui);

    }
    
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

            pst = con.prepareStatement("select name,(count-assignedCount) from equipment");
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

    
    

    @FXML
    public void addTenderEquipment() throws SQLException {
        try {
            String addTender = equipTender.getText();
            String addEq = tenderEquipId.getText();
            Integer addeqcount= Integer.parseInt(matCount.getText());
            if (addTender.isEmpty() || addEq.isEmpty()) {
                 alerboxInfo("Operation Failed","Fields cannot be empty");
              
            }
            else if(addeqcount==0){
                 alerboxInfo("Operation Failed","you have enterd 0 for count ");
            }
            
             PreparedStatement stmt2 = con.prepareStatement("select count from equiptender where tenderId=? and equipName=?");
           stmt2.setString(1, addTender); 
           stmt2.setString(2, addEq);
           rs = stmt2.executeQuery();

            while (rs.next()) {
                dataEqui.add(new EquipTender( rs.getInt(1)));
                //  dataKISH.add(new KISHANTH(null, null, null));
                 Integer amount;
                amount=rs.getInt(1);
                if (amount<addeqcount){
                 System.out.println("amount is"+amount);
                 System.out.println("amount is less");
                 
                 alerboxInfo("Attention","Amount greater than required amount!! press ok to continue");
                      
            }
                else{   
             DbConnection.openConnection();
                    Connection con4 = DbConnection.getConnection();
                    PreparedStatement stmt = con4.prepareStatement("UPDATE equiptender SET assignCount=assignCount+? where tenderId=? and equipName=?");
                    stmt.setInt(1, addeqcount);
                    stmt.setString(2, addTender);
                    stmt.setString(3, addEq);
                    stmt.executeUpdate();

            PreparedStatement stmt5 = con4.prepareStatement("UPDATE equipment SET assignedCount=assignedCount+? where name=?");
            stmt5.setInt(1, addeqcount);
            stmt5.setString(2, addEq);
            stmt5.executeUpdate();

            loadFromEquipDB();
            loadFromJobEquipDB();

            System.out.println("success");
            alerboxInfo("Operation Success","Equipment added successfully");

           
            }

        }
            }catch (Exception e) {

            System.out.println("error" + e);
            alerboxInfo("Operation Failed","Equipment cannot be added ");
           
        }
        equipTender.clear();
        tenderEquipId.clear();
        loadFromEquipDB();
        loadFromJobEquipDB();
    }
    
    
    
      private void RowclickEvent4() {
         equipTable.setOnMouseClicked((e)
                -> {
            EquipTender v1 = (EquipTender) equipTable.getItems().get(equipTable.getSelectionModel().getSelectedIndex());
            equipTender.setText(v1.getTenderId());
            tenderEquipId.setText(v1.getEquiName());
            
              dataequipment.clear();
        try {
             String addTender = equipTender.getText();
             String nameQ=tenderEquipId.getText();
            
           PreparedStatement stmt4 = con.prepareStatement("select name,(count-assignedCount) from equipment where name IN(select equipName from equiptender where tenderId=? and equipName=?)");
           stmt4.setString(1, addTender); 
            stmt4.setString(2, nameQ); 
           rs = stmt4.executeQuery();
           //nameQ=null;
            while (rs.next()) {
                dataequipment.add(new Equipment(rs.getString(1), rs.getInt(2)));
                //  dataKISH.add(new KISHANTH(null, null, null));
                
            }

            }
        

         catch (Exception e1) {
            System.out.println("ranjithaequi");
            System.err.println("Error loading table data " + e1);

        }
        equipmentTable.setItems(dataequipment);

        
        });

    }
       public void search() {
        searchfield.setOnKeyReleased(e -> {
            if (searchfield.getText().equals("")) {
               // loadFromTenderDB();
                //loadFromAssetDB();
                loadFromJobEquipDB();
              //  loadFromJobAssetADB();
                //loadFromTenderMaterialDB();
            } else {
                dataEqui.clear();
                try {

                    Connection con = DbConnection.getConnection();

                     pst = con.prepareStatement("select tenderId,equipName,count,assignCount from equiptender");
            rs = pst.executeQuery();

            while (rs.next()) {
                dataEqui.add(new EquipTender(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
            }
                    System.out.println("Search clicked");
                } catch (Exception ex) {
                    System.err.println("Error loading table data search table jobemployee" + ex);

                }

                equipTable.setItems(dataEqui);

            }

        });//event
    }
       
        @FXML
    private void tender_reportEquip(ActionEvent event) {
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            String report = "C:\\Users\\Ranjitha\\Documents\\NetBeansProjects\\DKJ construction\\src\\dkj\\construction\\joballocation\\EquipmentAllocation.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(report);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
            JasperViewer.viewReport(jp,false);
        } catch (Exception e) {
            System.out.println("sdas");
        }
    }

}

 
    

