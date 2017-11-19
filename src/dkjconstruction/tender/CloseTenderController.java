/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.tender;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dkjconstruction.DbConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javax.print.DocFlavor;
import dkjconstruction.tendermanagement.TenderReport.Report;

/**
 * FXML Controller class
 *
 * @author KishBelic
 */
public class CloseTenderController implements Initializable {

    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

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
    private Button btn_closeTender;
    @FXML
    private Button btn_CancelTender;
    @FXML
    private Label c_tender;
    @FXML
    private Label c_tenderText;
    @FXML
    private JFXComboBox c_material;
    @FXML
    private JFXTextField c_matCount;

    private ObservableList rawtypeList;
    private ObservableList hashData;

    @FXML
    private TableView<closeDetails> c_matTable;
    @FXML
    private TableColumn<?, ?> c_matCountCollumn;
    @FXML
    private TableColumn<?, ?> c_matColumn;

    HashMap<String, Integer> hash = new HashMap();

    HashMap<String, Integer> DBmatHash = new HashMap();
    HashMap<String, Integer> DBformatQuantity = new HashMap();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        data = FXCollections.observableArrayList();
        assignTableValues();
        loadDBtoTable();
        searchTender();

        rawtypeList = FXCollections.observableArrayList();
        rawCombo();

        RowclickEventForTID();

        hashData = FXCollections.observableArrayList();

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

            pst = con.prepareStatement("SELECT * FROM tender where status ='On progress' ");
            rs = pst.executeQuery();

            while (rs.next()) {
                data.add(new TenderDetails(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(4), rs.getString(7), rs.getString(11), "" + rs.getInt(8), "" + rs.getInt(9), "" + rs.getDate(6), "" + rs.getDouble(10)));
            }

        } catch (Exception e) {
            System.err.println("Error loading table data " + e);

        }
        ttable.setItems(data);

    }

    //rowclick for tendertable
    private void RowclickEventForTID() {
        ttable.setOnMouseClicked((e) -> {
            TenderDetails t2 = ttable.getItems().get(ttable.getSelectionModel().getSelectedIndex());

            //closeDetails c1 = new closeDetails(t2.getTenderId());
            c_tenderText.setText(t2.getTenderId());
            rawCombo();

            try {
                hash.clear();
                hashData.clear();
                c_matTable.setItems(hashData);

            } catch (Exception ex) {
                System.out.println("error in row click" + ex);
            }

        });

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

                    pst = con.prepareStatement("SELECT * FROM tender where tenderId LIKE '%" + tsearch.getText() + "%' AND status ='On progress' "
                            + "UNION SELECT * FROM tender where tenderName LIKE '%" + tsearch.getText() + "%' AND status ='On progress' "
                            + "UNION SELECT * FROM tender where grade LIKE '%" + tsearch.getText() + "%' AND status ='On progress' "
                            + "UNION SELECT * FROM tender where category LIKE '%" + tsearch.getText() + "%' AND status ='On progress' "
                            + "UNION SELECT * FROM tender where estimatedCost LIKE '%" + tsearch.getText() + "%' AND status ='On progress' "
                            + "UNION SELECT * FROM tender where companyName LIKE '%" + tsearch.getText() + "%' AND status ='On progress' "
                            + "UNION SELECT * FROM tender where workingPlace LIKE '%" + tsearch.getText() + "%' AND status ='On progress' ");
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

    ///raw material combo box drop down
    private void rawCombo() {

        rawtypeList.clear();
        DBmatHash.clear();
        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            pst = con.prepareStatement("SELECT * FROM materialtender where tenderId = '" + c_tenderText.getText().toString() + "' ");
            rs = pst.executeQuery();

            while (rs.next()) {
                rawtypeList.add(rs.getString(2));
                DBmatHash.put(rs.getString(2), Integer.parseInt(rs.getString(4)));

            }
            pst.close();
            rs.close();

        } catch (Exception e) {
            System.out.println("Error loading raw list  " + e);
        }

        c_material.setItems(rawtypeList);

    }

    //hashmap Function
    public void hashMapFunction() {

        c_matColumn.setCellValueFactory(new PropertyValueFactory<>("Material"));
        c_matCountCollumn.setCellValueFactory(new PropertyValueFactory<>("MatCount"));

        hash.put(c_material.getValue().toString(), Integer.parseInt(c_matCount.getText()));

        hashData.clear();

        for (String hkey : hash.keySet()) {
            hashData.add(new closeDetails(c_tenderText.getText(), hkey, hash.get(hkey)));

        }

        c_matTable.setItems(hashData);

    }

    public void c_materialComboclicked() {

    }

    @FXML
    private void tender_report(ActionEvent event) {
        
        Report.gen_Normal_report("/Users/KishBelic/Desktop/ITP/TenderManagement/src/tendermanagement/TenderReport/closed&Cancel.jrxml");
 
        
    }

    @FXML
    private void previousClicked(ActionEvent event) {
        Stage s = (Stage) previousButton.getScene().getWindow();
        s.close();
    }

    @FXML
    private void addClicked(ActionEvent event) {

        if (!validatefields()) {

            return;
        }
        try {

        } catch (Exception e) {
        }
        hashMapFunction();
        c_matCount.clear();

    }

    public void btnClick_closeTender() {
        if (c_tenderText.getText().isEmpty()) {
            TenderManagement.alertboxWarn("Tender Id can not be empty", "Click any tender from table to perform this action");
            return;
        }

        if(TenderManagement.alertboxConfirm("Closing Tender", " has this tender been completed ?    Do you really want to close this tender?") )
        {
        deAllocate_Material();
        deAllocate_Employee();
        deAllocate_Equipment();
        deAllocate_Asset();
        FinishTender("Closed");
        
        //db saamaangal
        assignTableValues();
        loadDBtoTable();
        
        }
        
    }

    public void btnClick_cancelTender() {

        if (c_tenderText.getText().isEmpty()) {
            TenderManagement.alertboxWarn("Tender Id can not be empty", "Click any tender from table to perform this action");
            return;
        }
        
        if(TenderManagement.alertboxConfirm("Cancel Tender", " Do you really want to cancel this tender? ") )
        {
        deAllocate_Material();
        deAllocate_Employee();
        deAllocate_Equipment();
        deAllocate_Asset();
        FinishTender("Cancelled");
        
        
        //db saamaangal
        assignTableValues();
        loadDBtoTable();
        }

    }
    
    public void FinishTender(String status)
    {
    
    try {
        Connection con = DbConnection.getConnection();

                    pst = con.prepareStatement("UPDATE tender set  status = '"+status+"' where tenderId = '" + c_tenderText.getText() + "' ");
                    pst.execute();

                } catch (Exception e) {
                    System.err.println("inner query change availability " + e);
                }

    
    }

    //de Allocate_Equipment
    public void deAllocate_Equipment()
    {
    Connection con = DbConnection.getConnection();
        //de-allocate equipment
        try {

            pst = con.prepareStatement("SELECT eqt.tenderId,eqt.equipName,eqt.assignCount,eq.`count` as 'equipment count',eq.assignedCount as 'equipment assigned count',eq.availableCount as 'equipment available count' from equiptender eqt ,equipment eq where eqt.equipName = eq.`name` and eqt.tenderId = '"+c_tenderText.getText()+ "'  ");
            rs = pst.executeQuery();
            
            

            while (rs.next()) {
                int eqpTenderAssignedcount = rs.getInt(3);
                int eqAssignedcount = rs.getInt(5);
                int eqAvailablecount = rs.getInt(6);
                        
                try {

                    PreparedStatement pst1 = con.prepareStatement("UPDATE equipment set assignedCount = '"+ (eqAssignedcount - eqpTenderAssignedcount ) +"' availableCount ='"+ (eqAvailablecount + eqpTenderAssignedcount ) +"'   "
                            + "where name ='"+ rs.getString(2)+"' ");
                    pst1.execute();

                } catch (Exception e) {
                    System.err.println("inner query change availability equipment" + e);
                }

            }
            System.out.println("equipment re arranged");

        } catch (Exception e) {
            System.err.println("outer query change availability equipment" + e);

        }
        
    
    }
    
    
    
    //de Allocate_Asset
    public void deAllocate_Asset()
    {
    Connection con = DbConnection.getConnection();
        //de-allocate asset
        try {

            pst = con.prepareStatement("SELECT * from assettender where tenderId = '"+c_tenderText.getText()+ "'  ");
            rs = pst.executeQuery();
            
            

            while (rs.next()) {
                int regno = rs.getInt(2);
                
                        
                try {

                    PreparedStatement pst1 = con.prepareStatement("UPDATE asset set availability ='available' where regNo='"+ regno +"' ");
                    pst1.execute();

                } catch (Exception e) {
                    System.err.println("inner query change availability asset" + e);
                }

            }
            System.out.println("asset re arranged");

        } catch (Exception e) {
            System.err.println("outer query change availability asset" + e);

        }
        
    
    }
    
    
    
    //de allocate employee
    public void deAllocate_Employee()
    {
    Connection con = DbConnection.getConnection();
        //de-allocate employee
        try {

            pst = con.prepareStatement("SELECT et.tenderId,e.empId,e.availability FROM employee e ,emptender et where e.empId = et.empId and e.availability ='assigned' and et.tenderId='" + c_tenderText.getText() + "' ");
            rs = pst.executeQuery();

            while (rs.next()) {

                try {

                    PreparedStatement pst1 = con.prepareStatement("UPDATE employee set  availability = 'available' where empId = '" + rs.getString(2) + "' ");
                    pst1.execute();

                } catch (Exception e) {
                    System.err.println("inner query change availability " + e);
                }

            }
            System.out.println("employee re arranged");

        } catch (Exception e) {
            System.err.println("outer query change availability " + e);

        }
        
    
    }
    
    
//de allocate material
    public void deAllocate_Material() {

        int remains;
        Connection con = DbConnection.getConnection();

        //for material
        rawCombo();
        for (String str : hash.keySet()) {
            remains = 0;

            for (String DBmat : DBmatHash.keySet()) {

                if (str.equals(DBmat)) {

                    remains = DBmatHash.get(DBmat) - hash.get(str);

                    //substract from material tender
                    try {

                        pst = con.prepareStatement("UPDATE materialtender set  assignCount ='" + (DBmatHash.get(DBmat) - hash.get(str)) + "' where tenderId = '" + c_tenderText.getText() + "' and materialType='" + DBmat + "'  ");
                        pst.execute();

                        System.out.println("material updated in material tender table");

                    } catch (Exception e) {
                        System.out.println("Update  error" + e);

                    }

                    //add to rawmaterial
                    try {
                        PreparedStatement pst1 = con.prepareStatement("SELECT * from rawmaterial where type = '" + DBmat + "'  ");
                        ResultSet rs1 = pst1.executeQuery();

                        if (rs1.next()) {

                            PreparedStatement pst2 = con.prepareStatement("UPDATE rawmaterial set quantity='" + (rs1.getInt(3) + hash.get(str)) + "' where type = '" + DBmat + "' ");
                            pst2.execute();

                            System.out.println("material updated in rawmaterial table");
                        }
                    } catch (Exception e) {
                        System.out.println("Update  rawmaterial error" + e);

                    }

                }

            }

        }
    }

    //validation
    private boolean validatefields() {

        if (c_tenderText.getText().isEmpty()) {
            TenderManagement.alertboxWarn("Tender Id can not be empty", "Click any tender from table to perform this action");
            return false;

        } else if ((c_material.getValue() == null) || c_material.getValue().equals(null)) {
            TenderManagement.alertboxWarn("Material type not selected", "choose any material from list to perform this action");
            return false;
        } else if (!c_matCount.getText().matches("[0-9]+")) {
            TenderManagement.alertboxWarn("Material count is invalid", "insert valid material count");
            c_matCount.clear();
            return false;
        } else {

            return true;

        }

    }

}
