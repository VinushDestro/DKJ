/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.joballocation;

import com.jfoenix.controls.JFXTextField;
import com.sun.deploy.ref.AppModel;
import dkjconstruction.DbConnection;
import static dkjconstruction.joballocation.JobAllocationController.addTender;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ranjitha
 */
public class PendingAssetController implements Initializable {

    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    private ObservableList<VINU> datavinupending;
    private ObservableList<asset> dataassetpending;

    @FXML
    private JFXTextField pendingvRegNo;
    @FXML
    private JFXTextField pendingaTenderId;
    @FXML
    private TableView pendingtransTable;
    @FXML
    private TableColumn pendingassetTender;
    @FXML
    private TableColumn pendingassettype;
    @FXML
    private TableColumn pendingassetReqCount;
    @FXML
    private TableColumn pendingassetAssignCount;
    @FXML
    private TableView pendingassetTable;
    @FXML
    private TableColumn pendingregNo;
    @FXML
    private TableColumn pendingassName;
    @FXML
    private TableColumn pendingassType;
    @FXML
    private JFXTextField pendingassetType;
    @FXML
    private JFXTextField searchfield;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        datavinupending = FXCollections.observableArrayList();
        dataassetpending = FXCollections.observableArrayList();

        setpendingJobAssetTable();
        loadFrompendingJobAssetADB();
        setpendingAssetTable();
        loadFrompendingAssetDB();

        RowclickEvent10();
        RowclickEvent11();
    }

    private void setpendingJobAssetTable() {
        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            pendingassetTender.setCellValueFactory(new PropertyValueFactory<>("tenderId"));
            pendingassettype.setCellValueFactory(new PropertyValueFactory<>("type"));

            pendingassetReqCount.setCellValueFactory(new PropertyValueFactory<>("req"));
            pendingassetAssignCount.setCellValueFactory(new PropertyValueFactory<>("assign"));

        } catch (Exception e) {
            System.out.println("ranjithaasset");
            System.err.println("Error set job asset table data " + e);
        }

    }

    public void loadFrompendingJobAssetADB() {

        datavinupending.clear();
        try {

            Connection con = DbConnection.getConnection();
            /* pst = con.prepareStatement("select tenderId,assetType,assetCount,assignCount from jobasset");
            rs = pst.executeQuery();*/

            pst = con.prepareStatement("select tenderId,assetType,assetCount,assignCount from jobasset");

            rs = pst.executeQuery();

            while (rs.next()) {
                datavinupending.add(new VINU(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
                //  dataKISH.add(new KISHANTH(null, null, null));

            }

        } catch (Exception e) {
            System.out.println("ranjithaasset");
            System.err.println("Error loading table data " + e);

        }
        pendingtransTable.setItems(datavinupending);

    }

    private void setpendingAssetTable() {
        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            pendingregNo.setCellValueFactory(new PropertyValueFactory<>("reg"));
            pendingassName.setCellValueFactory(new PropertyValueFactory<>("name"));

            pendingassType.setCellValueFactory(new PropertyValueFactory<>("type"));

        } catch (Exception e) {
            System.out.println("ranjithasset");
            System.err.println("Error set asset table data " + e);
        }

    }

    private void loadFrompendingAssetDB() {

        dataassetpending.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("select regNo,name,type from asset where availability='available'");
            rs = pst.executeQuery();

            while (rs.next()) {
                dataassetpending.add(new asset(rs.getString(1), rs.getString(2), rs.getString(3)));
                //  dataKISH.add(new KISHANTH(null, null, null));

            }

        } catch (Exception e) {
            System.out.println("ranjithaasset");
            System.err.println("Error loading table data " + e);

        }
        pendingassetTable.setItems(dataassetpending);

    }

    @FXML
    private void pendingassignTravel(ActionEvent event) {

        try {
            String addTID = pendingaTenderId.getText();
            String addRegno = pendingvRegNo.getText();

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

    @FXML
    private void pendingaddTenderAsset(ActionEvent event) {

        try {

            String addTender = pendingaTenderId.getText();
            String addAsset = pendingvRegNo.getText();
            String addAssetType = pendingassetType.getText();

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

            //  loadFrompendingJobAssetADB(accessStatic.getTender());
            loadFrompendingAssetDB();

        } catch (Exception e) {

            System.out.println("error" + e);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("errorxxxxx" + e);
            alert.show();
        }
        pendingaTenderId.clear();
        pendingvRegNo.clear();
        pendingassetType.clear();

    }

    private void RowclickEvent10() {
        pendingassetTable.setOnMouseClicked((e)
                -> {
            asset v1 = (asset) pendingassetTable.getItems().get(pendingassetTable.getSelectionModel().getSelectedIndex());
            pendingvRegNo.setText(v1.getReg());

        });

    }

    private void RowclickEvent11() {
        pendingtransTable.setOnMouseClicked((e)
                -> {
            VINU v1 = (VINU) pendingtransTable.getItems().get(pendingtransTable.getSelectionModel().getSelectedIndex());
            pendingaTenderId.setText(v1.getTenderId());
            pendingassetType.setText(v1.getType());

            dataassetpending.clear();
            try {

                String typeMat = pendingassetType.getText();
                PreparedStatement stmt4 = con.prepareStatement("select regNo,name,type from asset where availability='available' and type=?");

                stmt4.setString(1, typeMat);
                rs = stmt4.executeQuery();

                while (rs.next()) {
                    dataassetpending.add(new asset(rs.getString(1), rs.getString(2), rs.getString(3)));
                    //  dataKISH.add(new KISHANTH(null, null, null));

                }

            } catch (Exception e1) {
                System.out.println("ranjithaasset");
                System.err.println("Error loading table data " + e1);

            }
            pendingassetTable.setItems(dataassetpending);
        });

    }

    @FXML
    private void nextClicked(ActionEvent event) throws IOException {
   dkjconstruction.DKJConstruction.showPendingEquipment();
           }

    @FXML
    private void backClicked(ActionEvent event) throws IOException {
  dkjconstruction.DKJConstruction.showPendingEmployee();
    }

}
