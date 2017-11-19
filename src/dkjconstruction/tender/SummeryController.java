/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.tender;

import com.jfoenix.controls.JFXTextField;
import dkjconstruction.DbConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import dkjconstruction.tendermanagement.TenderReport.Report;
import java.io.IOException;

/**
 * FXML Controller class
 *
 * @author KishBelic
 */
public class SummeryController implements Initializable {

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    private ObservableList<TenderDetails> data;

    @FXML
    private JFXTextField tsearch;
    @FXML
    private TableView<TenderDetails> ttable;
    @FXML
    private TableColumn<?, ?> column_tid;
    @FXML
    private TableColumn<?, ?> column_tname;
    @FXML
    private TableColumn<?, ?> column_tgrade;
    @FXML
    private TableColumn<?, ?> column_tcategory;
    @FXML
    private TableColumn<?, ?> column_tworktype;
    @FXML
    private TableColumn<?, ?> column_tplace;
    @FXML
    private TableColumn<?, ?> column_tclient;
    @FXML
    private TableColumn<?, ?> column_tdate;
    @FXML
    private TableColumn<?, ?> column_tperiod;
    @FXML
    private TableColumn<?, ?> column_bidvalidity;
    @FXML
    private TableColumn<?, ?> column_tcost;
    @FXML
    private Button previousButton;
    @FXML
    private Label s_noOfEmp;
    @FXML
    private Label se_materialCost;
    @FXML
    private Label se_totalCost;
    @FXML
    private Label s_tid;
    @FXML
    private Label sr_materialCost;
    @FXML
    private Label sr_totalCost;
    @FXML
    private Label sr_noOfEmp;
    @FXML
    private Label sr_transportCost;
    @FXML
    private Label sr_hrmCost;
    @FXML
    private Label s_status;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data = FXCollections.observableArrayList();
        assignTableValues();
        loadDBtoTable();
        searchTender();
        rowclick();
    }

    //assign Tenderdetails to table
    public void assignTableValues() {
        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            column_tid.setCellValueFactory(new PropertyValueFactory<>("TenderId"));
            column_tname.setCellValueFactory(new PropertyValueFactory<>("TenderName"));
            column_tgrade.setCellValueFactory(new PropertyValueFactory<>("Grade"));
            column_tcategory.setCellValueFactory(new PropertyValueFactory<>("Category"));
            column_tworktype.setCellValueFactory(new PropertyValueFactory<>("WorkType"));
            column_tplace.setCellValueFactory(new PropertyValueFactory<>("WorkingPlace"));
            column_tclient.setCellValueFactory(new PropertyValueFactory<>("CompanyName"));
            column_tperiod.setCellValueFactory(new PropertyValueFactory<>("Period"));
            column_bidvalidity.setCellValueFactory(new PropertyValueFactory<>("BidValidity"));
            column_tdate.setCellValueFactory(new PropertyValueFactory<>("Tdate"));
            column_tcost.setCellValueFactory(new PropertyValueFactory<>("EstimatedCost"));

        } catch (Exception e) {
            System.err.println("Error set table data " + e);
        }

    }

    //load tenderDB to table
    public void loadDBtoTable() {

        data.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("SELECT * FROM tender where status ='Closed' or status ='Cancelled' ");
            rs = pst.executeQuery();

            while (rs.next()) {
                data.add(new TenderDetails(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(4), rs.getString(7), rs.getString(11), "" + rs.getInt(8), "" + rs.getInt(9), "" + rs.getDate(6), "" + rs.getDouble(10)));
            }

        } catch (Exception e) {
            System.err.println("Error loading table data " + e);

        }
        ttable.setItems(data);

    }

    //search
    private void searchTender() {
        tsearch.setOnKeyReleased(e -> {
            if (tsearch.getText().equals("")) {
                loadDBtoTable();
            } else {
                data.clear();
                try {

                    Connection con = DbConnection.getConnection();

                    pst = con.prepareStatement("SELECT * FROM tender where tenderId LIKE '%" + tsearch.getText() + "%' AND status ='Closed' or status ='Cancelled' "
                            + "UNION SELECT * FROM tender where tenderName LIKE '%" + tsearch.getText() + "%' AND status ='Closed' or status ='Cancelled' "
                            + "UNION SELECT * FROM tender where grade LIKE '%" + tsearch.getText() + "%' AND status ='Closed' or status ='Cancelled' "
                            + "UNION SELECT * FROM tender where category LIKE '%" + tsearch.getText() + "%' AND status ='Closed' or status ='Cancelled' "
                            + "UNION SELECT * FROM tender where estimatedCost LIKE '%" + tsearch.getText() + "%' AND status ='Closed' or status ='Cancelled' "
                            + "UNION SELECT * FROM tender where companyName LIKE '%" + tsearch.getText() + "%' AND status ='Closed' or status ='Cancelled' "
                            + "UNION SELECT * FROM tender where workingPlace LIKE '%" + tsearch.getText() + "%' AND status ='Closed' or status ='Cancelled' ");
                    rs = pst.executeQuery();

                    while (rs.next()) {
                        data.add(new TenderDetails(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(4), rs.getString(7), rs.getString(11), "" + rs.getInt(8), "" + rs.getInt(9), "" + rs.getDate(6), "" + rs.getDouble(10)));
                    }

                } catch (Exception ex) {
                    System.err.println("Error loading table data " + ex);

                }

                ttable.setItems(data);

            }

        });//event
    }

    private void rowclick() {
        ttable.setOnMouseClicked((MouseEvent event) -> {

            TenderDetails t2 = ttable.getItems().get(ttable.getSelectionModel().getSelectedIndex());

            s_tid.setText(t2.getTenderId());
            
            //employee
            s_noOfEmp.setText(Integer.toString(TenderManagement.getEmp(t2.getTenderId(),'e')));
            sr_noOfEmp.setText(Integer.toString(TenderManagement.getEmp(t2.getTenderId(),'a')));
            
             
            se_materialCost.setText(Double.toString(TenderManagement.findCost(t2.getTenderId(), 'e', 'm')));
            se_totalCost.setText(Double.toString(TenderManagement.findCost(t2.getTenderId(), 'e', 'f')));
             
            
           // sr_materialCost.setText(Double.toString(TenderManagement.findCost(t2.getTenderId(), 'a', 'm')));
           // sr_totalCost.setText(Double.toString(TenderManagement.findCost(t2.getTenderId(), 'a', 'f')));
            
            
            //from findcost class
            sr_transportCost.setText(Double.toString(findCost.findcost(t2.getTenderId(), "transport")));
            sr_hrmCost.setText(Double.toString(findCost.findcost(t2.getTenderId(), "hrsum")));
            sr_totalCost.setText(Double.toString(findCost.findcost(t2.getTenderId(), "total")));
            sr_materialCost.setText(Double.toString(findCost.findcost(t2.getTenderId(), "material")));
            
            
            //for status
            try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("SELECT status FROM tender where tenderId = '"+ t2.getTenderId() +"' ");
            rs = pst.executeQuery();

            if (rs.next()) {
                s_status.setText(rs.getString(1));
                
            }

        } catch (Exception e) {
            System.err.println("Error loading table data " + e);

        }
            
            
            
        });

    }

    @FXML
    private void tender_report(ActionEvent event) {
        
        Report.gen_Normal_report("C:\\Users\\Mahesh\\Documents\\NetBeansProjects\\dkjconstructions\\src\\dkjconstruction\\tendermanagement\\TenderReport\\ClosedTender.jrxml");
 
    }

    @FXML
    private void previousClicked(ActionEvent event) throws IOException {
//        Stage s = (Stage) previousButton.getScene().getWindow();
//        s.close();

dkjconstruction.DKJConstruction.showTender_TenderHome();
    }

    @FXML
    private void tender_report1(ActionEvent event) {
        
        Report.gen_Normal_report("C:\\Users\\Mahesh\\Documents\\NetBeansProjects\\dkjconstructions\\src\\dkjconstruction\\tendermanagement\\TenderReport\\CancelledTender.jrxml");
 
    }

}
