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
import java.sql.SQLException;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ranjitha
 */
public class PendingEquipmentController implements Initializable {

 private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ResultSet rs2 = null;
    private ObservableList<EquipTender> dataEquipending;
    private ObservableList<Equipment> dataequipmentpending;

    @FXML
    private TableView pendingequipmentTable;
    @FXML
    private TableColumn pendingequipmentName;
    @FXML
    private TableColumn pendingavailableEquipCount;
    @FXML
    private JFXTextField pendingequipTender;
    @FXML
    private JFXTextField pendingtenderEquipId;
    @FXML
    private JFXTextField pendingEqCount;
    
    @FXML
    private TableView pendingequipTable;
    @FXML
    private TableColumn pendingeqTender;
    @FXML
    private TableColumn pendingeqName;
    @FXML
    private TableColumn pendingeqReq;
    @FXML
    private TableColumn pendingeqAssign;
    @FXML
    private TextField searchfield;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dataEquipending = FXCollections.observableArrayList();
        dataequipmentpending = FXCollections.observableArrayList();
        
          pendingequipTender.setDisable(true);
            pendingtenderEquipId.setDisable(true);
              
        pendingsetJobEquipmentTable();
        pendingloadFromJobEquipDB();
        pendingsetEquipmentTable();
        pendingloadFromEquipDB();

        RowclickEvent12();
        RowclickEvent13();
        search();

    }

    private void alerboxInfo(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setWidth(500);
        alert.setHeight(300);
        alert.setContentText(message);
        alert.showAndWait();

    }

    private void pendingsetJobEquipmentTable() {
        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            pendingeqTender.setCellValueFactory(new PropertyValueFactory<>("tenderId"));
            pendingeqName.setCellValueFactory(new PropertyValueFactory<>("equiName"));

            pendingeqReq.setCellValueFactory(new PropertyValueFactory<>("req"));
            pendingeqAssign.setCellValueFactory(new PropertyValueFactory<>("assign"));

        } catch (Exception e) {
            System.out.println("ranjitha equip");
            System.err.println("Error set job equip table data " + e);
        }

    }

    private void pendingloadFromJobEquipDB() {

        dataEquipending.clear();

        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("select tenderId,equipName,count,assignCount from equiptender where tenderid IN(select tenderId from jobasset where assignCount > 0 and tenderId IN(select tenderId from tender where status='pending'))");
            rs = pst.executeQuery();

            while (rs.next()) {
                dataEquipending.add(new EquipTender(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
            }

        } catch (Exception e) {
            System.out.println("ranjithaequi");
            System.err.println("Error loading table data " + e);

        }
        pendingequipTable.setItems(dataEquipending);

    }

    private void pendingsetEquipmentTable() {
        try {

            DbConnection.openConnection();
            con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            pendingequipmentName.setCellValueFactory(new PropertyValueFactory<>("equipName"));
            pendingavailableEquipCount.setCellValueFactory(new PropertyValueFactory<>("available"));

        } catch (Exception e) {
            System.out.println("ranjitha equip");
            System.err.println("Error set table data " + e);
        }
    }

    private void pendingloadFromEquipDB() {

        dataequipmentpending.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("select name,(count-assignedCount) from equipment where(count-assignedCount)>0 ");
            rs = pst.executeQuery();

            while (rs.next()) {
                dataequipmentpending.add(new Equipment(rs.getString(1), rs.getInt(2)));
                //  dataKISH.add(new KISHANTH(null, null, null));

            }

        } catch (Exception e) {
            System.out.println("ranjithaequi");
            System.err.println("Error loading table data " + e);

        }
        pendingequipmentTable.setItems(dataequipmentpending);

    }

    @FXML
    public void pendingaddTenderEquipment() throws SQLException {

        try {

            String addTender = pendingequipTender.getText();
            String addEq = pendingtenderEquipId.getText();
            Integer addEqcount = Integer.parseInt(pendingEqCount.getText());

            if (addTender.isEmpty() || addEq.isEmpty()) {
                alerboxInfo("Error", "Fields cannot be empty");
               pendingequipTender.clear();
               pendingtenderEquipId.clear();
               pendingEqCount.clear();
               
            } else if (addEqcount<=0) {
                alerboxInfo("Operation Failed", "You have enterd 0 lesser value for count ");
            }

            PreparedStatement stmt2 = con.prepareStatement("select count from equiptender where tenderid=? and equipName=?");
            stmt2.setString(1, addTender);
            stmt2.setString(2, addEq);
            rs = stmt2.executeQuery();

            while (rs.next()) {
                dataEquipending.add(new EquipTender(rs.getInt(1)));
                //  dataKISH.add(new KISHANTH(null, null, null));
                Integer amount;
                amount = rs.getInt(1);

                if (amount < addEqcount) {
                    System.out.println("amount is" + amount);
                    System.out.println("amount is less");

                    alerboxInfo("Error", "Assigning amount cannot be greater than required amount. Try entering again");

                   
                    pendingEqCount.clear();

                } else {
                    DbConnection.openConnection();
                    Connection con4 = DbConnection.getConnection();
                    PreparedStatement stmt = con4.prepareStatement("UPDATE equiptender SET assignCount=assignCount+? where tenderId=? and equipName=?");
                    stmt.setInt(1, addEqcount);
                    stmt.setString(2, addTender);
                    stmt.setString(3, addEq);
                    stmt.executeUpdate();

                    PreparedStatement stmt3 = con4.prepareStatement("UPDATE equipment SET  assignedCount=assignedCount+? where name=?");
                    stmt3.setInt(1, addEqcount);
                    stmt3.setString(2, addEq);
                    stmt3.executeUpdate();

                    pendingloadFromJobEquipDB();
                    pendingloadFromEquipDB();

                    System.out.println("success");
                    alerboxInfo("Operation Success", "Equipment added successfully");
                }

            }

        } catch (Exception e) {

            System.out.println("error" + e);
            alerboxInfo("Operation failed", "error Adding Equipment");

            pendingequipTender.clear();
            pendingtenderEquipId.clear();
            pendingEqCount.clear();
        }
         pendingequipTender.clear();
            pendingtenderEquipId.clear();
            pendingEqCount.clear();
          pendingloadFromEquipDB();
          pendingloadFromJobEquipDB();
    }

    private void RowclickEvent12() {
        pendingequipTable.setOnMouseClicked((e)
                -> {
            EquipTender v1 = (EquipTender) pendingequipTable.getItems().get(pendingequipTable.getSelectionModel().getSelectedIndex());
            pendingequipTender.setText(v1.getTenderId());
            pendingtenderEquipId.setText(v1.getEquiName());
           

            dataequipmentpending.clear();

            try {
                String addTender = pendingequipTender.getText();
                String nameQ = pendingtenderEquipId.getText();

                PreparedStatement stmt4 = con.prepareStatement("select name,(count-assignedCount) from equipment where name IN(select equipName from equiptender where tenderId=? and equipName=?)");
                stmt4.setString(1, addTender);
                System.out.println(nameQ);
                stmt4.setString(2, nameQ);
                rs = stmt4.executeQuery();
                //nameQ=null;
                while (rs.next()) {
                    dataequipmentpending.add(new Equipment(rs.getString(1), rs.getInt(2)));
                    //  dataKISH.add(new KISHANTH(null, null, null));

                }

            } catch (Exception e1) {
                System.out.println("ranjithaequi");
                System.err.println("Error loading table data " + e1);

            }
            pendingequipmentTable.setItems(dataequipmentpending);

        });

    }

    private void RowclickEvent13() {
        pendingequipmentTable.setOnMouseClicked((e)
                -> {
            Equipment v1 = (Equipment) pendingequipmentTable.getItems().get(pendingequipmentTable.getSelectionModel().getSelectedIndex());
            pendingtenderEquipId.setText(v1.getEquipName());

        });
    }

    @FXML
    private void nextClicked(ActionEvent event) throws IOException {
        dkjconstruction.DKJConstruction.showPendingMaterial();

    }

    @FXML
    private void backClicked(ActionEvent event) throws IOException {
  dkjconstruction.DKJConstruction.showPendingMaterial();
    }
    
     public void search() {
        searchfield.setOnKeyReleased(e -> {
            if (searchfield.getText().equals("")) {
               // loadFromTenderDB();
                //loadFromAssetDB();
               pendingloadFromJobEquipDB();
              //  loadFromJobAssetADB();
                //loadFromTenderMaterialDB();
            } else {
                dataEquipending.clear();
                try {

                    Connection con = DbConnection.getConnection();

                     pst = con.prepareStatement("select tenderId,equipName,count,assignCount from equiptender where tenderId IN(select tenderId from jobasset where assignCount>0) and tenderId  LIKE '%" + searchfield.getText() + "%'");
            rs = pst.executeQuery();

            while (rs.next()) {
                dataEquipending.add(new EquipTender(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
            }
                    System.out.println("Search clicked");
                } catch (Exception ex) {
                    System.err.println("Error loading table data search table jobemployee" + ex);

                }

                pendingequipTable.setItems(dataEquipending);

            }

        });//event
    }
}
