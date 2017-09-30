/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.tender;

import com.jfoenix.controls.JFXTextField;
import dkjconstruction.DbConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author KishBelic
 */
public class ViewTenderController implements Initializable {

    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    @FXML
    private JFXTextField tsearch;
    @FXML
    private AnchorPane centerpane;
    @FXML
    private TableView<TenderDetails> view_ctable;
    @FXML
    private TableColumn<?, ?> c_collumn_tenderId;
    @FXML
    private TableColumn<?, ?> c_collumn_date;
    @FXML
    private TableColumn<?, ?> c_collumn_bidValidity;
    @FXML
    private TableColumn<?, ?> c_collumn_cName;
    @FXML
    private TableColumn<?, ?> c_collumn_materialType;
    @FXML
    private TableColumn<?, ?> c_collumn_materialCount;
    @FXML
    private TableColumn<?, ?> c_collumn_noOfEmployee;
    @FXML
    private Button viewSummery_btn;

    private ObservableList<TenderDetails> cdata;
    private ObservableList<TenderDetails> pdata;
    private ObservableList<TenderDetails> odata;
    @FXML
    private TableView<TenderDetails> view_otable;
    @FXML
    private TableColumn<?, ?> o_collumn_tenderId;
    @FXML
    private TableColumn<?, ?> o_collumn_date;
    @FXML
    private TableColumn<?, ?> o_collumn_bidValidity;
    @FXML
    private TableColumn<?, ?> o_collumn_cName;
    @FXML
    private TableColumn<?, ?> o_collumn_materialType;
    @FXML
    private TableColumn<?, ?> o_collumn_materialCount;
    @FXML
    private TableColumn<?, ?> o_collumn_noOfEmployee;
    @FXML
    private TableView<TenderDetails> view_ptable;
    @FXML
    private TableColumn<?, ?> p_collumn_tenderId;
    @FXML
    private TableColumn<?, ?> p_collumn_date;
    @FXML
    private TableColumn<?, ?> p_collumn_bidValidity;
    @FXML
    private TableColumn<?, ?> p_collumn_cName;
    @FXML
    private TableColumn<?, ?> p_collumn_materialType;
    @FXML
    private TableColumn<?, ?> p_collumn_materialCount;
    @FXML
    private TableColumn<?, ?> p_collumn_noOfEmployee;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        cdata = FXCollections.observableArrayList();
        assignTableValuesClosed();
        loadDBtoTableClosed();
        
        pdata = FXCollections.observableArrayList();
        assignTableValuesPending();
        loadDBtoTablePending();
        
        odata = FXCollections.observableArrayList();
        assignTableValuesOnprogress();
        loadDBtoTableOnprogress();
        
    }

    @FXML
    private void viewSummery(ActionEvent event) {

    }

    @FXML
    private void previousClicked(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Tender.fxml"));
        Scene root_scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(root_scene);

        stage.show();
    }

    private void assignTableValuesClosed() {
        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            c_collumn_tenderId.setCellValueFactory(new PropertyValueFactory<>("TenderId"));
            c_collumn_cName.setCellValueFactory(new PropertyValueFactory<>("CompanyName"));
            c_collumn_bidValidity.setCellValueFactory(new PropertyValueFactory<>("BidValidity"));
            c_collumn_date.setCellValueFactory(new PropertyValueFactory<>("Tdate"));
            
            c_collumn_materialCount.setCellValueFactory(new PropertyValueFactory<>("MaterialCount"));
            c_collumn_materialType.setCellValueFactory(new PropertyValueFactory<>("MaterialType"));
            c_collumn_noOfEmployee.setCellValueFactory(new PropertyValueFactory<>("NoOfEmployee"));

        } catch (Exception e) {
            System.err.println("Error set table data " + e);
        }

    }
    
    
    private void assignTableValuesOnprogress() {
        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            o_collumn_tenderId.setCellValueFactory(new PropertyValueFactory<>("TenderId"));
            o_collumn_cName.setCellValueFactory(new PropertyValueFactory<>("CompanyName"));
            o_collumn_bidValidity.setCellValueFactory(new PropertyValueFactory<>("BidValidity"));
            o_collumn_date.setCellValueFactory(new PropertyValueFactory<>("Tdate"));
            
            o_collumn_materialCount.setCellValueFactory(new PropertyValueFactory<>("MaterialCount"));
            o_collumn_materialType.setCellValueFactory(new PropertyValueFactory<>("MaterialType"));
            o_collumn_noOfEmployee.setCellValueFactory(new PropertyValueFactory<>("NoOfEmployee"));

        } catch (Exception e) {
            System.err.println("Error set table data " + e);
        }

    }
    
    private void assignTableValuesPending() {
        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            p_collumn_tenderId.setCellValueFactory(new PropertyValueFactory<>("TenderId"));
            p_collumn_cName.setCellValueFactory(new PropertyValueFactory<>("CompanyName"));
            p_collumn_bidValidity.setCellValueFactory(new PropertyValueFactory<>("BidValidity"));
            p_collumn_date.setCellValueFactory(new PropertyValueFactory<>("Tdate"));
            
            p_collumn_materialCount.setCellValueFactory(new PropertyValueFactory<>("MaterialCount"));
            p_collumn_materialType.setCellValueFactory(new PropertyValueFactory<>("MaterialType"));
            p_collumn_noOfEmployee.setCellValueFactory(new PropertyValueFactory<>("NoOfEmployee"));

        } catch (Exception e) {
            System.err.println("Error set table data " + e);
        }

    }
    
    
    //load tenderDB to table
    private void loadDBtoTableClosed() {

        cdata.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("Select t.tenderId,t.companyName,t.bidValidity,t.`date`,m.materialType,m.materialCount,e.noOfEmployee\n" +
"from tender t\n" +
"left join jobmaterial m on t.tenderId = m.tenderId\n" +
"left join jobemployee e on t.tenderId = e.tenderId\n" +
"where t.status = 'Closed'");
            rs = pst.executeQuery();

            while (rs.next()) {
                cdata.add(new TenderDetails(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }

        } catch (Exception e) {
            System.err.println("Error loading table data " + e);

        }
        view_ctable.setItems(cdata);

    }
    
    
    private void loadDBtoTableOnprogress() {

        odata.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("Select t.tenderId,t.companyName,t.bidValidity,t.`date`,m.materialType,m.materialCount,e.noOfEmployee\n" +
"from tender t\n" +
"left join jobmaterial m on t.tenderId = m.tenderId\n" +
"left join jobemployee e on t.tenderId = e.tenderId\n" +
"where t.status = 'On Progress'");
            rs = pst.executeQuery();

            while (rs.next()) {
                odata.add(new TenderDetails(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }

        } catch (Exception e) {
            System.err.println("Error loading table data " + e);

        }
        view_otable.setItems(odata);

    }
    
    
    private void loadDBtoTablePending() {

        pdata.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("Select t.tenderId,t.companyName,t.bidValidity,t.`date`,m.materialType,m.materialCount,e.noOfEmployee\n" +
"from tender t\n" +
"left join jobmaterial m on t.tenderId = m.tenderId\n" +
"left join jobemployee e on t.tenderId = e.tenderId\n" +
"where t.status = 'Pending'");
            rs = pst.executeQuery();

            while (rs.next()) {
                pdata.add(new TenderDetails(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }

        } catch (Exception e) {
            System.err.println("Error loading table data " + e);

        }
        view_ptable.setItems(pdata);

    }
    
    /*
    //search
    private void searchTender() {
        tsearch.setOnKeyReleased(e -> {
            if (tsearch.getText().equals("")) {
                loadDBtoTableClosed();
                loadDBtoTablePending();
                loadDBtoTableOnprogress();
            } else {
                cdata.clear();
                pdata.clear();
                odata.clear();
                try {

                    Connection con = DbConnection.getConnection();

                    pst = con.prepareStatement("SELECT * FROM tender where tenderId LIKE '%" + tsearch.getText() + "%'"
                            
                            + "UNION SELECT * FROM tender where companyName LIKE '%" + tsearch.getText() + "%'");
                    rs = pst.executeQuery();

                    while (rs.next()) {
                        data.add(new TenderDetails(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(4), rs.getString(7), rs.getString(11), "" + rs.getInt(8), "" + rs.getInt(9), "" + rs.getDate(6), "" + rs.getDouble(10)));
                    }

                } catch (Exception ex) {
                    System.err.println("Error loading table data " + ex);

                }

                view_ctable.setItems(cdata);
                view_otable.setItems(odata);
                view_ptable.setItems(pdata);

            }

        });//event
    }

    */

}
