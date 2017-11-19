/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.joballocation;

import com.jfoenix.controls.JFXTextField;
import dkjconstruction.DbConnection;
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
public class AssetAllocationController implements Initializable {

   private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    private ObservableList<JobAsset> datavinu;
    private ObservableList<asset> dataasset;

    @FXML
    private JFXTextField vRegNo;
    @FXML
    private JFXTextField aTenderId;
    @FXML
    private TableView transTable;
    @FXML
    private TableColumn assetTender;
    @FXML
    private TableColumn assettype;
    @FXML
    private TableColumn assetReqCount;
    @FXML
    private TableColumn assetAssignCount;
    @FXML
    private TableView assetTable;
    @FXML
    private TableColumn regNo;
    @FXML
    private TableColumn assName;
    @FXML
    private TableColumn assType;
    @FXML
    private JFXTextField assetType;
    @FXML
    private JFXTextField searchfield;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        datavinu = FXCollections.observableArrayList();
        dataasset = FXCollections.observableArrayList();

        setAssetTable();
        loadFromAssetDB();
        setJobAssetTable();
        loadFromJobAssetADB();
        RowclickEvent2();
        RowclickEvent3();

    }

    private void setJobAssetTable() {
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
            pst = con.prepareStatement("select tenderId,assetType,assetCount,assignCount from jobasset where tenderId IN(select tenderId from tender where status='On progress')");

            rs = pst.executeQuery();

            /* pst = con.prepareStatement("select tenderId,assetType,assetCount,assignCount from jobasset");
            
            rs = pst.executeQuery();*/
            while (rs.next()) {
                datavinu.add(new JobAsset(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
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

    

    @FXML
    public void addTenderAsset() {

        try {

            String addTender = aTenderId.getText();
            String addAsset = vRegNo.getText();
            String addAssetType = assetType.getText();
            
            
            if (addTender.isEmpty() || addAsset.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Fields cannot be empty");
                alert.show();
            }
            else{
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

            loadFromAssetDB();
            loadFromJobAssetADB();
            }

        } catch (Exception e) {

            System.out.println("error" + e);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("errorxxxxx" + e);
            alert.show();
        }
        aTenderId.clear();
        vRegNo.clear();
        assetType.clear();
        loadFromAssetDB();
            loadFromJobAssetADB();
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
            JobAsset v1 = (JobAsset) transTable.getItems().get(transTable.getSelectionModel().getSelectedIndex());
            aTenderId.setText(v1.getTenderId());
            assetType.setText(v1.getType());

            dataasset.clear();
            try {

                String typeMat = assetType.getText();
                PreparedStatement stmt4 = con.prepareStatement("select regNo,name,type from asset where availability='available' and type=?");

                stmt4.setString(1, typeMat);
                rs = stmt4.executeQuery();

                while (rs.next()) {
                    dataasset.add(new asset(rs.getString(1), rs.getString(2), rs.getString(3)));
                    //  dataKISH.add(new KISHANTH(null, null, null));

                }

            } catch (Exception e1) {
                System.out.println("ranjithaasset");
                System.err.println("Error loading table data " + e1);

            }
            assetTable.setItems(dataasset);

        });

    }
    
     @FXML
    private void tender_reportAsset(ActionEvent event) {
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            String report = "C:\\Users\\Ranjitha\\Documents\\NetBeansProjects\\DKJ construction\\src\\dkj\\construction\\joballocation\\AssetAllocation.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(report);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
            JasperViewer.viewReport(jp,false);
        } catch (Exception e) {
            System.out.println("sdas");
        }
    }
    
      public void search() {
        searchfield.setOnKeyReleased(e -> {
            if (searchfield.getText().equals("")) {
                //loadFromTenderDB();
                //loadFromAssetDB();
               // loadFromJobEquipDB();
                loadFromJobAssetADB();
               // loadFromTenderMaterialDB();
            } else {
                datavinu.clear();
                try {

                    Connection con = DbConnection.getConnection();

                    pst = con.prepareStatement("select tenderId,assetType,assetCount,assignCount from jobasset where tenderId  LIKE '%" + searchfield.getText() + "%' ");
                    rs = pst.executeQuery();

                    while (rs.next()) {
                        datavinu.add(new JobAsset(rs.getString(1),rs.getString(2), rs.getInt(3), rs.getInt(4)));
                    }
                    System.out.println("Search clicked");
                } catch (Exception ex) {
                    System.err.println("Error loading table data search table jobemployee" + ex);

                }

                transTable.setItems(datavinu);

            }

        });//event
    }
}
